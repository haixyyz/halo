package org.xujin.halo.flow.annotation.processor;

import java.lang.annotation.*;

/**
 * 业务处理（必需）
 * <p>
 * 此注解对应的方法返回值会作为处理器的返回值返回给流程节点。
 * 入参必须是TargetContext类型；对于返回参数类型的话只要是能被赋值给节点决策器的入参就行
 * （比如：String execute(TargetContext targetContext)）
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ProcessorExecute {
}
