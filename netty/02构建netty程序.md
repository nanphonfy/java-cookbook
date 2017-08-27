>netty支持的客户端连接数，理论上仅受限于系统的可用资源（及JDK版本可能会施加的限制）

## 1 服务端
>**编写netty服务器：**
①至少一个ChannelHandler，该组件实现服务器对从客户端接收数据的处理，即业务逻辑；
②引导，配置服务器启动的代码，将服务器绑定到它要监听连接请求的端口上。

- [x] EchoServer
```
public class EchoServer {
    private final int port;

    public EchoServer(int port) {
        this.port = port;
    }

    public static void main(String[] args) throws Exception {
        int port = Integer.parseInt("8888");
        new EchoServer(port).start();//调用服务器的 start()方法
    }

    public void start() throws Exception {
        final EchoServerHandler serverHandler = new EchoServerHandler();//创建ChannelHandler
        EventLoopGroup group = new NioEventLoopGroup();//使用NIO传输，接受和处理新的连接
        try {
            ServerBootstrap b = new ServerBootstrap();//创建了一个ServerBootstrap实例
            b.group(group)
                .channel(NioServerSocketChannel.class)//指定channel的类型
                .localAddress(new InetSocketAddress(port))//为本地地址选定端口
                .childHandler(new ChannelInitializer<SocketChannel>() {//每次创建连接，一个新的子Channel将会被创建
                    @Override
                    public void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline().addLast(serverHandler);//把创建ChannelHandler放入该新的Channel的ChannelPipeline
                    }
                });

            ChannelFuture f = b.bind().sync();// sync()将导致当前Thread阻塞，直到绑定操作完成
            f.channel().closeFuture().sync();//阻塞等待直到服务器的 Channel关闭
        } finally {
            group.shutdownGracefully().sync();//关闭EventLoopGroup，并释放所有的资源，包括所有被创建的线程
        }
    }
}
```

- [x] EchoServerHandler
```
// ChannelHandler是一个接口族的父接口，实现负责接收并响应事件通知。所有的数据处理逻辑都包含在这些核心抽象的实现中
// ChannelInboundHandlerAdapter extends ChannelHandlerAdapter① implements ChannelInboundHandler②
// ①ChannelHandlerAdapter implements ChannelHandler
// ②ChannelInboundHandler extends ChannelHandler

@Sharable//标示一个ChannelHandler 可以被多个 Channel 安全地共享
public class EchoServerHandler extends ChannelInboundHandlerAdapter {// ChannelInboundHandlerAdapter用来响应传入的消息，定义响应入站事件的方法
    @Override
    //每个传入的消息都要调用
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        ByteBuf in = (ByteBuf) msg;
        System.out.println("Server received: " + in.toString(CharsetUtil.UTF_8));
        //将接收到的消息写给发送者，而【不冲刷】出站消息
        ctx.write(in);
    }

    @Override
    //通知ChannelInboundHandler最后一次对channelRead()的调用是当前批量读取中的最后一条消息
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.writeAndFlush(Unpooled.EMPTY_BUFFER).//将未决消息冲刷到远程节点
                addListener(ChannelFutureListener.CLOSE);//关闭该Channel
    }

    @Override
    //读取操作期间，有异常抛出时会调用
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();//打印异常栈跟踪
        ctx.close();//关闭该Channel
    }
}
```
>- **如果不捕获异常，会发生什么呢?**  
每个Channel都拥有一个与之关联的ChannelPipeline，其持有一个ChannelHandler的实例链。默认，ChannelHandler会把对它方法的调用转发给链中的下一个 ChannelHandler。 **若exceptionCaught()没被该链中的某处实现，那么所接收的异常将会被传递到 ChannelPipeline的尾端并被记录。** 所以，应至少有一个实现exceptionCaught()方法的ChannelHandler。
## 2 客户端
>**编写netty客户端：** ①连接到服务器；
②发送一个或多个消息；③每个消息，等待并接收服务器响应；④关闭连接。  
编写客户端主要代码部分也是业务逻辑和引导。
- [x] EchoClient
```
public class EchoClient {
    private final String host;
    private final int port;

    public EchoClient(String host, int port) {
        this.host = host;
        this.port = port;
    }
    
    public static void main(String[] args) throws Exception {
        final String host = "localhost";
        final int port = 8888;
        new EchoClient(host, port).start();
    }

    public void start() throws Exception {
        EventLoopGroup group = new NioEventLoopGroup();//使用NIO传输
        try {
            Bootstrap b = new Bootstrap();
            b.group(group)//指定EventLoopGroup，处理客户端事件
                .channel(NioSocketChannel.class)//指定channel的类型
                .remoteAddress(new InetSocketAddress(host, port))//设置远程服务器的ip和端口号
                //在创建Channel时，向 ChannelPipeline中添加一个 EchoClientHandler实例(ChannelHandler)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    public void initChannel(SocketChannel ch)
                        throws Exception {
                        ch.pipeline().addLast(new EchoClientHandler());
                    }
                });
            //连接到远程节点，阻塞等待直到连接完成
            ChannelFuture f = b.connect().sync();
            //阻塞，直到Channel 关闭
            f.channel().closeFuture().sync();
        } finally {
            //关闭线程池并释放所有的资源
            group.shutdownGracefully().sync();
        }
    }
}
```
- [x] EchoClientHandler
```
// SimpleChannelInboundHandler<I> extends ChannelInboundHandlerAdapter
// ChannelInboundHandlerAdapter extends ChannelHandlerAdapter① implements ChannelInboundHandler②
// ①ChannelHandlerAdapter implements ChannelHandler
// ②ChannelInboundHandler extends ChannelHandler

@Sharable//标示一个ChannelHandler 可以被多个 Channel 安全地共享
public class EchoClientHandler extends SimpleChannelInboundHandler<ByteBuf> {
    @Override
    //服务器的连接建立之后将被调用
    public void channelActive(ChannelHandlerContext ctx) {
        //当被通知Channel是活跃的时候，发送一条消息
        ctx.writeAndFlush(Unpooled.copiedBuffer("我构建了Netty客户端，发送消息!",CharsetUtil.UTF_8));
    }

    @Override
    //当从服务器接收到一条消息时被调用
    public void channelRead0(ChannelHandlerContext ctx, ByteBuf in) {
        System.out.println("Client received: " + in.toString(CharsetUtil.UTF_8));
    }

    @Override
    //在处理过程中引发异常时被调用
    public void exceptionCaught(ChannelHandlerContext ctx,Throwable cause) {
        cause.printStackTrace();//打印异常栈跟踪
        ctx.close();//关闭该Channel
    }
}
```
>- **SimpleChannelInboundHandler 与ChannelInboundHandler**   
以上代码，客户端和服务端分别用到两个不同的ChannelHandler，这**和两个因素的相互作用有关：业务逻辑如何处理消息以及 Netty 如何管理资源。**  
>>当客户端的channelRead0()完成时，已有消息传入并处理，当该方法返回时，SimpleChannelInboundHandler 释放指向保存该消息的ByteBuf的内存引用。在EchoServerHandler中，仍需将传入消息回送给发送者，而 *write()操作是异步的*，直到channelRead()返回后可能仍没完成。  
为此，EchoServerHandler扩展了 ChannelInboundHandlerAdapter，其在这个时间点上不会释放消息。消息在EchoServerHandler 的 channelReadComplete()方法中，当 writeAndFlush()方
法被调用时被释放。
05、06将对消息的资源管理进行详细的介绍。

>以上示例使用了NIO(可扩展性+彻底异步性)，是目前使用最广泛的传输。也可使用不同的传输实现，如OIO 传输，需指定OioServerSocketChannel和OioEventLoopGroup。 


