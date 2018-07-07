package org.xujin.halo.flow.annotation.processor;

import java.lang.annotation.*;

/**
 * 后置处理（可选）
 * <p>
 * 一般是业务处理后的收尾工作。
 * 入参必须是TargetContext类型，返回值必须是void（比如：void after(TargetContext targetContext)）
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ProcessorAfter {
}
