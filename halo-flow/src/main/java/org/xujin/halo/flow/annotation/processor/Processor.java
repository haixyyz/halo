package org.xujin.halo.flow.annotation.processor;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * 节点处理器
 * <p>
 * 执行步骤：
 * 1、@ProcessorBefore（可选）
 * 2、@ProcessorExecute（必需）
 * 3、@ProcessorAfter（可选）
 * 4、@ProcessorError（可选，如果@ProcessorBefore、@ProcessorExecute、@ProcessorAfter任何一个发生异常则执行@ProcessorError）
 * 5、@ProcessorEnd（可选，无论是否发生异常都会执行）
 */
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Component
public @interface Processor {

    /**
     * 处理器名字（默认使用被注解类的名字，首字母小写）
     */
    String name() default "";
}
