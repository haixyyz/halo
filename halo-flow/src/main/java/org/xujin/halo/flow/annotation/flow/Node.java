package org.xujin.halo.flow.annotation.flow;

import java.lang.annotation.*;

/**
 * 节点
 * （此为节点父注解，StartNode、ProcessNode、StateNode、WaitNode、EndNode都是根据此注解延伸的）
 */
@Documented
@Target(ElementType.ANNOTATION_TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Node {

    /**
     * 节点名称（默认使用被注解的函数名，在一个流程内节点名称需唯一）
     */
    String name() default "";

    /**
     * 节点处理器（默认不执行处理器）
     */
    String processor() default "";

    /**
     * 是否自动执行本节点（默认自动执行）
     */
    boolean autoExecute() default true;

    /**
     * 本节点执行前是否创建新事务（默认不新建）
     */
    boolean newTx() default false;

}
