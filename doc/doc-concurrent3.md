fork/join
============================


java.util.concurrent.ForkJoinPool


说是一个经典的并发模式，能利用多CPU核心，轻松过渡到多核时代
就是一个任务分到不同的CPU执行，然后合并结果


在java外围，Apache的Hadoop map/reduce框架也是为并行计算而生，可以用到大型集群上


还有Scala，Closure，Erlang等天生支持并行的语言