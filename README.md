# netty_dome
Netty 是一个 NIO client-server(客户端服务器)框架，使用 Netty 可以快速开发网络应用，例如服务器和客户
端协议。 Netty 提供了一种新的方式来使开发网络应用程序，这种新的方式使得它很容易使用和有很强的扩展性。
Netty 的内部实现时很复杂的，但是 Netty ᨀ供了简单易用的 api 从网络处理代码中解耦业务逻辑。 Netty 是完全基
于 NIO 实现的，所以整个 Netty 都是异步的。


#### NIO和IO总结
- [Java NIO介绍（一）————Buffer和Channels简单介绍](http://blog.csdn.net/qq_25673113/article/details/53203692)
- [Java NIO介绍（二）————无堵塞io和Selector简单介绍](http://blog.csdn.net/qq_25673113/article/details/53284732)


#### Netty_dome[项目中的例子]
- 多人聊天
- 传输自定义类(注意需要序列化传输的类)