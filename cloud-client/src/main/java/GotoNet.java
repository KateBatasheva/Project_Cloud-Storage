import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GotoNet {


    private static GotoNet clientConnect = new GotoNet();
    private static final Logger LOG = LoggerFactory.getLogger(GotoNet.class);
    private Channel currentChannel;

    public static GotoNet getClientConnect() {
        return clientConnect;
    }


    public Channel getCurrentChannel() {
        return currentChannel;
    }

    public void start (){
        new Thread(()-> {
            EventLoopGroup group = new NioEventLoopGroup();
            EventLoopGroup auth = new NioEventLoopGroup(1);
            try {
                ServerBootstrap bootstrap = new ServerBootstrap();
                bootstrap.group(auth, group)
                        .channel(NioServerSocketChannel.class)
                        .childHandler(new ChannelInitializer<SocketChannel>() {
                            @Override
                            protected void initChannel(SocketChannel channel) throws Exception {
                                channel.pipeline().addLast( new ClientHandlerIn() // пока отсутствует, будет на входящие
                                );
                                currentChannel = channel;
                            }
                        });
                ChannelFuture future = bootstrap.bind(8189).sync();
                LOG.debug("server started on PORT = 8189!");
                future.channel().closeFuture().sync(); // block
            } catch (InterruptedException e) {
                LOG.error("e=", e);
            } finally {
                auth.shutdownGracefully();
                group.shutdownGracefully();
            }
        }).start();
    }
}
