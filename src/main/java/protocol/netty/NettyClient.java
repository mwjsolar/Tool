package protocol.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;


/**
 * Created by hzmawenjun on 2016/2/29.
 */
public class NettyClient {
    EventLoopGroup group = new NioEventLoopGroup();


    public void connect(String host,int port) {
        Bootstrap b = new Bootstrap();
        b.group(group)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.TCP_NODELAY,true)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        System.out.println("client initChannel..");
                        socketChannel.pipeline().addLast(new TimeClientHandler());
                    }
                });

        try {
            // 发起异步连接操作
            ChannelFuture f = b.connect(host, port).sync();
            // 等待客户端链路关闭
            f.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    class TimeClientHandler  extends ChannelInboundHandlerAdapter {

        private final ByteBuf firstMessage;

        public TimeClientHandler() {
            byte[] req = "QUERY TIME ORDER".getBytes();
            firstMessage = Unpooled.buffer(req.length);
            firstMessage.writeBytes(req);
        }

        @Override
        public void channelActive(ChannelHandlerContext ctx) throws Exception {
            //与服务端建立连接后
            System.out.println("client channelActive..");
            ctx.writeAndFlush(firstMessage);
        }

        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg)
                throws Exception {
            System.out.println("client channelRead..");
            //服务端返回消息后
            ByteBuf buf = (ByteBuf) msg;
            byte[] req = new byte[buf.readableBytes()];
            buf.readBytes(req);
            String body = new String(req, "UTF-8");
            System.out.println("Now is :" + body);
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
                throws Exception {
            System.out.println("client exceptionCaught..");
            // 释放资源
            System.out.println("Unexpected exception from downstream:"
            );
            ctx.close();
        }

    }

    public static void main(String[] args) {
        int port = 9000;
        if (args != null && args.length > 0) {
            try {
                port = Integer.parseInt(args[0]);
            } catch (Exception e) {
            }
        }
        new NettyClient().connect("127.0.0.1",port);
    }
}
