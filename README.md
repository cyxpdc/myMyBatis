# 手写MyBatis



## 1.0版本





​										使用者

1.编写TestMapper接口和方法和TestMapperXml类，实现xml文件的功能；使用Test作为实体类

2.使用Bootstrap类运行测试





​										开发者

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





## 2.0版本：

> 1.0问题：

1.配置是写死的

2.jdbc映射也是写死的，代理类的invoke也写死了

3.Executor不应该执行sql，它的职责就是执行器而已，要把sql隔离出来

> 开始改善第一个问题：

MyConfiguration：配置文件不要写死在MapperXml里面，提取出来，单独成类
路径用scanPath属性保存，方法和sql的映射抽取出来，用MapperRegistory类保存

MapperRegistory：保存方法和MapperData的映射，组合进MyConfiguration

MapperData：保存sql和对应的实体类类型，组合进MapperRegistory，增加了扩展性，可以有不同的实体类，而不只是Test

> 开始改善第二、三个问题：

SimpleExecutor：作为一个执行者的身份来命名数据库

CachingExecutor：在SimpleExecutor的基础上加了层缓存，映射为sql：结果实体

StatementHandler：从数据库获取数据

ResultSetHandler：处理结果集,即用从数据库获取的数据来封装MapperData

MapperProxy：将配置和执行串联在一起运行的地方

> 解释：

> ​										使用者：
>
> 由BootStrap启动，创建MyConfiguration和MySqlSession，MySqlSession#getMapper得到mapper，调用相应方法即可
>
> ​										内部流程：
>
> 会调用MapperProxy#invoke，根据方法的全限定名获取(MapperRegistory)对应的MapperData(未封装，得到的是Test对象)，执行sqlSession#selectOne
>
> sqlSession#selectOne会将任务委派给executor，executor则将任务委派给StatementHandler；
>
> StatementHandler负责从数据库取数据，取到数据后，将封装MapperData的任务交给ResultSetHandler；
>
> ResultSetHandler**通过反射调用set方法给其字段设置值**，即将MapperRegistory中map的键(sql)查询出来的数据封装Test对象



## 3.0版本：用装饰器模式实现缓存

### 一级缓存

存在于sqlSession里面，一个sqlsession有两条相同的sql语句时，命中缓存；减少对数据库的压力

### 二级缓存

基于nameSpace，即多个SqlSession的同一个语句可以缓存在同一个地方，一个请求开启多个sqlsession，用相同的sql时，命中缓存

###相关类

ExecutorFactory：用来生成Executor，如SimpleExecutor或CachingExecutor，简单工厂

CachingExecutor：一级缓存，使用了装饰器模式

Executor和handler去除了没必要的Configuration，由sqlSession来管理即可，真正起到承上启下的作用

> 流程
>
> sqlSession先用CachingExecutor查询是否有相关的查询，如果没有，再去跑SimpleExecutor