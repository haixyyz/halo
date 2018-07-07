package org.xujin.halo.flow.annotation.flow;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

/**
 * 等待节点
 * （只有当等待节点是第一个被执行的节点，等待节点才会被执行；否则流程执行到等待节点时会正常中断。（这符合等待异步通知这类场景）。
 * 对应的节点决策器返回值类型必须为String，入参类型可为：()、(TargetContext)、(T)、(T, TargetContext)————T表示能被对应的处理器返回结果赋值的类型）
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Node(autoExecute = false, newTx = true)
public @interface WaitNode {

    /**
     * 节点名称（默认使用被注解的函数名，在一个流程内节点名称需唯一）
     */
    @AliasFor(annotation = Node.class, attribute = "name")
    String name() default "";

    /**
     * 节点处理器（默认不执行处理器）
     */
    @AliasFor(annotation = Node.class, attribute = "processor")
    String processor() default "";

}
