package org.xujin.halo.flow.annotation.processor;

import java.lang.annotation.*;

/**
 * 错误处理（可选）
 * <p>
 * 在执行@ProcessorBefore、@ProcessorExecute、@ProcessorAfter任何一个发生异常时会执行。
 * 入参必须是TargetContext类型，返回值必须是void（比如：void error(TargetContext targetContext)）
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ProcessorError {
}
