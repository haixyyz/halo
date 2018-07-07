package org.xujin.halo.flow.annotation.transaction;

import java.lang.annotation.*;

/**
 * 插入目标对象到数据库
 * <p>
 * 本注解的作用：创建一个新事务用来插入目标对象到数据库并提交，前提是调用流程引擎的insertTargetAndStart方法。
 * 本注解存在的原因：在开启流程事务情况下，流程引擎是创建新事务进行执行，如果插入目标对象到数据库的事务未提交就执行流程引擎，则流程引擎在锁目标对象时就会出现死锁。
 * 所以流程引擎留一个口子新开启一个事务来插入目标对象到数据库。
 * 注意：如果插入目标对象时发现目标对象在数据库中已存在，根据幂等性原则，应该使用数据库中的已存在的目标对象（流程引擎是通过锁目标对象来实现））
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface InsertTarget {
}
