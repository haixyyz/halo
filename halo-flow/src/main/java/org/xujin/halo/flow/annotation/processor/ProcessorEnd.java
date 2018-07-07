package org.xujin.halo.flow.annotation.processor;

import java.lang.annotation.*;

/**
 * 结束处理（可选）
 * <p>
 * 无论是否发生异常都会执行.
 * 入参必须是TargetContext类型，返回值必须是void（比如：void end(TargetContext targetContext)）
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ProcessorEnd {
}
