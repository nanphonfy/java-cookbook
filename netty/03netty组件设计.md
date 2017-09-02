Netty解决了两个关注领域：技术的(基于Java NIO的异步和事件驱动，保证性能、可伸缩性)和体系结构的（包含一组设计模式，解耦、可重用和模块化）　　

##  1. Netty网络抽象代表
>Channel:Socket ；EventLoop:控制流、多线程处理、并发；
ChannelFuture:异步通知。
- Channel
>基于Socket类，Channel接口所提供的API，大大降低直接使用Socket类的复杂性。
- EventLoop
>`创建Channel->将Channel注册到EventLoop( EventLoopGroup提供)->整个生命周期都用EventLoop处理IO`  
>>一个EventLoopGroup包含1+个EventLoop(生命周期内只和一个Thread(所有IO事件都由Thread处理)绑定)；
一个Channel在生命周期内只注册于一个EventLoop(可能被分配给1+个Channel)；
- ChannelFuture  
Netty所有I/O操作都是异步的。操作可能不立即返回，ChannelFuture用于在之后的某个时间点确定其结果。
>>ChannelFuture接口，其 addListener()方法注册了一个 ChannelFutureListener。
## 2. 管理数据流+处理逻辑的组件
- ChannelHandler接口  
>Netty主要组件是ChannelHandler，处理入站+出站数据的逻辑容器。
如ChannelInboundHandler，为实现上者的子接口，接收入站事件和数据，再经由我们的业务逻辑处理。业务逻辑通常驻留在1+个ChannelInboundHandler。 
- ChannelPipeline 接口
>事件运动方向从客户端到服务器端，称为事件的出站，反之入站。ChannelPipeline提供了ChannelHandler 链的容器，创建Channel时，自动分配其专属的ChannelPipeline。
>>**ChannelInitializer安装ChannelHandler到Pipelien流程：** ①ChannelInitializer的实现被注册到ServerBootstrap或Bootstrap->②该类的initChannel()被调用时，该类将在ChannelPipeline安装一组自定义ChannelHandler->③该类从ChannelPipeline移除。  

>当ChannelHandler被添加到ChannelPipeline时，会被分配一个ChannelHandlerContext，其代表之间的绑定。

>**为什么需要适配器类?**  
>>有一些适配器类(eg.ChannelHandlerAdapter、ChannelInboundHandlerAdapter、ChannelOutboundHandlerAdapter等)可更简单的编写自定义ChannelHandler，因其提供所有方法的默认实现。  

>**出站和入站**
>>入站消息被解码，从字节转换为Java对象；出站消息被编码，从当前格式被编码为字节。Netty 提供的编、解码器*适配器类*都实现ChannelOutboundHandler 或ChannelInboundHandler接口。
入站数据->channelRead方法/事件已被重写，入站该方法会被调用。 随后，将调用由预置解码器的decode()，将已解码字节转发给ChannelPipeline中的下一个ChannelInboundHandler。出站消息相反方向：编码器将消息转换为字节，并将它们转发给下一个ChannelOutboundHandler。

>**抽象类 SimpleChannelInboundHandler**
>>最常见的操作有：接收解码消息并对该数据进行业务逻辑处理，只需扩展基类SimpleChannelInboundHandler<T>，T为处理消息的 Java 类型。该类型的channelRead0最重要。  
## 3. 引导
>两种引导：客户端（ Bootstrap）+服务器（ServerBootstrap）。引导一个客户端只需一个EventLoopGroup，服务端则需两个（可以同一实例）
>>其一只包含一个 ServerChannel，代表已绑定到某个本地端口(正在监听的套接字) ;其二包含所有已创建的用来处理传
入客户端连接（对于每个服务器已经接受的连接都有一个）的Channel。
