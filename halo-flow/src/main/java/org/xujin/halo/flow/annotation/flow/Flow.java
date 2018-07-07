package org.xujin.halo.flow.annotation.flow;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * 流程
 * <p>
 * 流程包含的节点类型：开始节点（@StartNode）、状态节点（@StateNode）、处理节点（@ProcessNode）、等待节点（@WaitNode）、结束节点（@EndNode）
 * 一：开启了流程事务情况
 * 流程在刚开始执行时会自动的开启一个新事务并调用流程事务锁住目标对象；
 * 当流程被正常中断或正常执行结束（无异常抛出），则会提交事务；否则如果有任何异常抛出，则会回滚事务（当然已经提交的那些事务是不会回滚的）。
 * <p>
 * 二：未开启流程事务情况
 * 整个执行过程中流程引擎不会对事务做任何操作（既不会主动开启事务，也不会主动提交事务），也不会调用流程事务锁住目标对象；@StateNode节点效果也会跟@ProcessNode节点效果一样
 */
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Component
public @interface Flow {

    /**
     * 流程名称（默认使用被注解的类名，首字母小写）
     */
    String name() default "";

    /**
     * 是否开启流程事务（默认开启）
     */
    boolean enableFlowTx() default true;

}
