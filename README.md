## Halo Framework

Halo Framework,光环框架是基于DDD+CQRS+扩展点+业务中间件，业务系统使用之自带光环!

> Halo,中文名光环。美 [ˈheɪloʊ]，Halo是Hallo的简写，是德语你好的意思。

## Halo 模块

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

## 使用

### maven 依赖

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

案例:https://github.com/SoftwareKing/crm-sales


