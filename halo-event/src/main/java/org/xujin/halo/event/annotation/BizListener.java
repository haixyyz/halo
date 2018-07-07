package org.xujin.halo.event.annotation;

import org.xujin.halo.event.annotation.listener.Listener;
import org.xujin.halo.event.extension.support.BizListenerType;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

/**
 * 业务监听器
 */
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Listener(type = BizListenerType.class)
public @interface BizListener {

    /**
     * 优先级
     * （具体执行顺序需要结合@Listen注解的priorityAsc属性共同决定）
     */
    @AliasFor(annotation = Listener.class, attribute = "priority")
    int priority() default Integer.MAX_VALUE;
}
