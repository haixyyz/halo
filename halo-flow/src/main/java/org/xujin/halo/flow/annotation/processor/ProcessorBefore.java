package org.xujin.halo.flow.annotation.processor;

import java.lang.annotation.*;

/**
 * 前置处理（可选）
 * <p>
 * 一般进行预处理，比如一些预校验。
 * 入参必须是TargetContext类型，返回值必须是void（比如：void before(TargetContext targetContext)）
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ProcessorBefore {
}
