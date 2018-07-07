## 1.Halo Framework

   Halo, 发音美 [ˈheɪloʊ]，和hello发音基本一致，中文名光环。Halo是Hallo的简写，是德语你好的意思。
Halo框架名寓意是赋能于业务应用开发，业务方使用者自带光环，脚踏七彩祥云，为业务开发造福，为业务架构治理和防腐提供统一的方法论。
Halo框架是基于CQRS+扩展点+流程编排的应用框架，致力于采用领域驱动的设计思想，规范控制程序员的随心所欲，从而解决软件的复杂性问题。
架构原则很简单，即在高内聚，低耦合，可扩展，易理解大的指导思想下，尽可能的贯彻OO的设计思想和原则。

如果你觉得Halo不错，让你很爽，烦请拨冗**“Star”**。


> Halo Framework,光环框架是基于DDD+CQRS+扩展点+业务中间件，业务系统使用之自带光环!

> Halo,中文名光环。美 [ˈheɪloʊ]，Halo是Hallo的简写，是德语你好的意思。

## 2.Halo 模块

| 模块名 | 描述 | 备注 |
| --- | --- | --- |
| halo-base | base层注解 |  |
| halo-collection | 采集能力图 |  |
| halo-common | 框架公共部分 |  |
| halo-core | 框架核心 |  |
| halo-event | 框架Event事件 |  |
| halo-flow | 流程编排 | todo 待重构 |
| halo-test | 框架测试模块 | todo 待重构 |
| halo-utils | 框架工具类用于各种DTO对象之间快速复制 |  |
| halo-springcloud | 分布式Command |第二种结合Spring Cloud  |

## 3.使用

### 3.1 maven 依赖

```pom
<halo.framework.version>1.0.4</halo.framework.version>

<!-- Halo框架核心 -->
<dependency>
    <groupId>org.xujin.halo</groupId>
    <artifactId>halo-core</artifactId>
    <version>${halo.framework.version}</version>
</dependency>

<!-- Halo测试组件可选-->
<dependency>
    <groupId>org.xujin.halo</groupId>
    <artifactId>halo-test</artifactId>
    <version>${halo.framework.version}</version>
    <scope>test</scope>
</dependency>


<!-- Halo能力采集图-->
<dependency>
    <groupId>org.xujin.halo</groupId>
    <artifactId>halo-collection</artifactId>
    <version>${halo.framework.version}</version>
</dependency>
```

案例:[crm-sales](https://github.com/SoftwareKing/crm-sales)


