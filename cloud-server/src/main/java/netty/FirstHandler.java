package netty;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;

public class FirstHandler extends ChannelInboundHandlerAdapter {
    private int nameLen;
    private long fileLen;
    private long currentFileLen;
    private BufferedOutputStream outFile;
    private Status currentStatus = Status.WAIT;

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("Client connected");
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("Client disconnected");
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf bb = (ByteBuf) msg;
        if (bb.getByte(0) == 0 || bb.getByte(bb.capacity()) != 31) {
            throw new RuntimeException("Package not full");
        }

        while (bb.readableBytes() > 0) {
            byte firstByte = bb.readByte();
            switch (currentStatus) {
                case WAIT:
//                        case UPLOAD.getCommand():    // почему тут не получается таким образом, получить как-то константу из энама
                    currentStatus = Status.NAMELEN;
                case NAMELEN:
                    if (bb.readableBytes() >= 4) {
                        nameLen = bb.readInt();
                        currentStatus = Status.NAME;
                    }
                case NAME:
                    if (bb.readableBytes() >= nameLen) {
                        byte[] fileName = new byte[nameLen];
                        bb.readBytes(fileName);
                        switch (firstByte) {
                            case (byte) 30:
                                outFile = new BufferedOutputStream(new FileOutputStream("clients " + new String(fileName))); // тут будет имя клиента
                                currentStatus = Status.FILELEN;
                            case (byte) 40:
                            case (byte) 50:
                            case (byte) 20:
                        }
                    }
                case FILELEN:
                    switch (firstByte) {
                        case (byte) 20:
                        case (byte) 30:
                            if (bb.readableBytes() >= 8) {
                                fileLen = bb.readLong();
                                currentStatus = Status.FILE;
                            }
                        case (byte) 40:
                        case (byte) 50:
                    }
                case FILE:
                    switch (firstByte) {
//                                case (byte) 20:
                        case (byte) 30:
                            while (bb.readableBytes() > 0) {
                                outFile.write(bb.readByte());
                                currentFileLen++;
                                if (currentFileLen == fileLen) {
                                    currentStatus = Status.WAIT;
                                    outFile.close();
                                    break;
                                }
                            }
                        case (byte) 40:
                        case (byte) 50:
                    }
            }
            if (bb.readableBytes() == 0) {
                bb.release();
            }
        }
    }
}
