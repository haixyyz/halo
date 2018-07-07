package org.xujin.halo.flow.annotation.flow;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

/**
 * 结束节点
 * （每个流程必须至少有一个结束节点，它是流程结束的标志。当流程跳转到结束节点时，流程会自动结束；
 * 对应的节点决策器不能有入参，且返回类型必须是void，决策器的方法体不会被执行）
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Node(autoExecute = false, newTx = true)
public @interface EndNode {

    /**
     * 节点名称（默认使用被注解的函数名，在一个流程内节点名称需唯一）
     */
    @AliasFor(annotation = Node.class, attribute = "name")
    String name() default "";

}
