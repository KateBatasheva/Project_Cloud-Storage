import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.Channel;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class QuerySender {

    public static void uploadFile (Path path, Channel channel) throws IOException {

        ByteBuf bb = null;
        // write command
        bb.writeByte(Commands.UPLOAD.getCommand());
        channel.writeAndFlush(bb);

        // write file length
        byte [] fileName = path.getFileName().toString().getBytes(StandardCharsets.UTF_8);
        bb.writeInt(fileName.length);
        channel.writeAndFlush(bb);

        // write file name
        bb.writeBytes(fileName);
        channel.writeAndFlush(bb);

        // write file length
        bb.writeLong(Files.size(path));
        channel.writeAndFlush(bb);

        // write file
        byte [] bFile = Files.readAllBytes(path);
        bb.writeBytes(bFile);
        channel.writeAndFlush(bb);
    }
}
