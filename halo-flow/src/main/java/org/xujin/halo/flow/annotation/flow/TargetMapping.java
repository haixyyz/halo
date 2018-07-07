package org.xujin.halo.flow.annotation.flow;

import java.lang.annotation.*;

/**
 * 目标对象映射
 * <p>
 * 流程开启执行前会自动调用此注解对应的方法，将目标对象映射到开始执行的流程节点。
 * 每次开启新事务时都会调用它将目标对象映射到流程节点
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface TargetMapping {
}
