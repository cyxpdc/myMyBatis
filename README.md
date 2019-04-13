# 手写MyBatis



##1.0版本



###使用者

1.编写TestMapper接口和方法和TestMapperXml类，实现xml文件的功能；使用Test作为实体类

2.使用Bootstrap类运行测试



###开发者

MySqlSession：组合进了配置类和操作sql类，起一个承上启下的作用。selectOne方法将JDBC操作委托给Executor，getMapper生成TestMapper的代理MapperProxy

MapperProxy：代理类。TestMapper调用方法时，会调用其invoke方法，通过组合的sqlSession调用selectOne来操作数据库

MyExecutor、SimpleExecutor：SimpleExecutor实现了MyExecutor接口，查询数据库的方法为query，此处使用了JDBC，真正地去操作数据库

> 解释 ：
>
> ​	                                                     使用者：
>
> 用户先编写TestMapper接口和方法和对应的TestMapperXml类，在TestMapperXml中使用HashMap，**将TestMapper接口的方法作为键，具体的sql语句作为值，TestMapperXml的nameSpace为TestMapper的全限定名**，这样就将mapper和xml关联起来了；
>
> 然后创建一个MySqlSession对象，使用MySqlSession#getMapper得到TestMapper，运行其方法即可；
>
> ​	                                                     内部流程：
>
> MySqlSession#getMapper得到TestMapper的**代理类MapperProxy**，运行方法时，会调用MapperProxy#invoke(Object proxy, Method method, Object[] args)；
>
> 其invoke根据反射的方法：method.getDeclaringClass()得到方法对应的接口，getName()得到此接口的全限定名，即nameSpace，来找到对应的xml；根据xml的method名用HashMap得到对应的sql语句；
>
> 然后传入调用MySqlSession#selectOne(sql)，selectOne(sql)会调用SimpleExecutor#query(sql)，即调用JDBC去查询数据库。