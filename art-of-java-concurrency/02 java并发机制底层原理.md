>java编译后成为字节码，被类加载器加载到JVM，最终需转化为汇编指令在CPU执行，其并发机制依赖于JVM和CPU指令。

#### 1 volatile应用
>volatile是轻量级synchronized（多处理器保证共享变量的可见性），使用恰到时，比synchronized使用和执行成本更低（不会引起上下文切换和调度）。

##### 1.1 volatile定义与实现原理
>volatile定义：

- 实现原理相关的CPU术语和说明
