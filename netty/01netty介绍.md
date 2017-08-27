## 1 前言
高性能网络编程不仅要求超一流的编程技巧， 还需要网络编程、多线程处理和并发等专业知识。
- Java网络编程
>最早期的API[java.net（1995—2002）]只支持由本地系统套接字库提供的阻塞函数，只能同时处理一个连接，要管理多个并发客户端，需要为每个新的客户端Socket创建一个新的Thread，该并发方案可以支撑中小数量(小于10万)的客户端。  
①任何时候都可能有大量的线程处于休眠状态，只等待输入或者输出数据就绪，资源浪费；  
②需为每个线程的调用栈都分配内存；  
③线程的上下文切换所带来开销。
- Java NIO
> 本地套接字库很早就提供了非阻塞[JDK 1.4 非阻塞I/O java.nio（2002）]调用。   
①使用 setsockopt()，读/写调用在没有数据的时候立即返回；
②使用操作系统的事件通知,即Selector选择器，单一线程便可处理多个并发连接。  
**注：** NIO代表非阻塞I/O（Non-blocking I/O），是新的输入/输出（New Input/Output），阻塞I/O （blocking
I/O）是旧的输入/输出（old input/output，OIO）。  
①使用较少的线程处理多连接，减少内存管理和上下文切换所带来开销；  
②没有 I/O 操作需处理时，线程也可被用于其他任务。
>>尽管已经有许多直接使用 Java NIO API的应用程序被构建了， 但是要做到如此正确和安全并不容易。 特别是， 在高负载下可靠和高效地处理和调度 I/O 操作是一项繁琐而且容易出错的任务，
**最好留给高性能的网络编程专家——Netty**

## 2 netty简介
>熟悉底层API的人员少，所以用较简单的抽象隐藏底层实现的复杂性的原则催生出了框架，为常见编程任务封装了解决方案。在网络编程领域，Netty是Java的卓越框架。 

>**特性：**
>- 设计（API统一、线程模型简单强大、支持无连接数据报套接字、支持复用，链接逻辑组件）
>- 易使用（文档详细、示例多）
>- 性能（比 Java API的吞吐量更高，延迟更低、共享池和复用资源消耗更低、减少内存拷贝）
>- 健壮性（不会 OutOfMemoryError、消除高速网络NIO 不公平读/写比率）
>- 安全性（SSL/TLS、StartTLS）
>- 社区驱动（发布快速频繁）

使用了netty的开源产品，有hadoop、dubbo、Apache Cassandra、Apache Thrift、Elasticsearch等 

**异步和事件驱动:**

>**异步（非同步）事件:** 本质上，一个既是异步的又是事件
驱动的系统可以以任意的顺序响应任意时间点的事件。  
**异步和可伸缩性的联系：**
>- 非阻塞网络调用不必等待一个操作完成。完全异步的 I/O 正是基于这个特性构建的。异步方法会立即返回，在完成时，会直接或者在稍后某个时间点通知用户。
>- 选择器，能通过较少的线程监视许多连接上的事件。

## 3 核心组件
>Netty主要构件块：
>1. Channel ；
>1. 回调；
>1. Future ；
>1. 事件和 ChannelHandler。  
这些构建块代表了不同类型的构造：资源、逻辑以及通知。应用将使用它们访问网络以及流经网络的数据。

- Channel  
>是 Java NIO 的一个基本构造(java.nio.channels),代表一个到实体的开放连接（如硬件设备、文件、网络套接字或者大于等于一个I/O操作的程序组件），如读操作和写操作。  
Channel可看做传入（入站）或传出（出站）的数据载体。可被打开或被关闭，连接或断开。
- 回调
>一个回调其实就是一个方法，一个指向已被提供给另外一个方法的方法引用。Netty 在内部使用了回调来处理事件：
当一个新的连接已被建立，
ChannelHandler 的channelActive(ChannelHandler
Context)回调方法将会被调用，并将打印信息。
```
//被回调触发的 ChannelHandler
public class ConnectHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelActive(ChannelHandlerContext ctx)throws Exception {
        System.out.println("Client " + ctx.channel().remoteAddress() + "connected");
    }
}
```
- Future
>Future可看作异步操作结果的占位符，在未来某时刻完成，并提供对其结果的访问。JDK实现了java.util.concurrent.Future，但只允许手动检查是否完成，或一直阻塞直到完成， Netty自己实现了ChannelFuture。  
ChannelFuture能够注册一个或多个
ChannelFutureListener实例。监听器的回调方法operationComplete()，将会在对应的操作完成时被调用，然后监听器可判断该操作是成功还是出错。**由ChannelFutureListener提供的通知机制消除了手动检查是否完成的必要。**
Netty 完全是异步和事件驱动的，每个 Netty的出站I/O操作都将返回一个ChannelFuture，它们都不会阻塞。

```
//回调实例
Channel channel = ...;
ChannelFuture future = channel.connect(new InetSocketAddress("192.168.0.1", 25));
//ChannelFutureListener是回调的一个更加精细版本
future.addListener(new ChannelFutureListener() {
    @Override
    public void operationComplete(ChannelFuture future) {
        if (future.isSuccess()){
            ByteBuf buffer = Unpooled.copiedBuffer("Hello",Charset.defaultCharset());
            ChannelFuture wf = future.channel().writeAndFlush(buffer);
            ....
        } else {
            Throwable cause = future.cause();
            cause.printStackTrace();
        }
    }
});
```
- 事件和 ChannelHandler
>每个事件都可以被分发给ChannelHandler类中的某个用户实现的方法。  
以下为将事件驱动范式直接转换为应用程序构件块的例子，即流经 ChannelHandler 链的入站事件和出站事件：  
- [x] 出站事件->出站处理器->出站事件->出站处理器->出站事件->  
- [x] 入站事件->入站处理器->入站事件->入站处理器->入站事件->
- 梳理
1. Future、回调和 ChannelHandler  
>Netty异步编程模型建立在Future和回调，将事件派发到ChannelHandler的方法则发生在更深的层次上。结合在一起后就可提供一个处理环境，使应用程序逻辑可独立演变。
2. 选择器、事件和 EventLoop
>Netty 通过触发事件将 Selector从应用程序中抽象出来，消除了所有本来将需要手动编写的派发代码。在内部，将会为每个 Channel 分配一个 EventLoop(只由一个线程驱动，处理了一个 Channel的所有IO事件,可消除同步顾虑)，用以处理所有事件。