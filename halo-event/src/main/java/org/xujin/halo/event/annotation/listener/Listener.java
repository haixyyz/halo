package org.xujin.halo.event.annotation.listener;

import org.xujin.halo.event.extension.ListenerType;
import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * 监听器
 */
@Documented
@Target(ElementType.ANNOTATION_TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Component
public @interface Listener {

    /**
     * 类型
     */
    Class<? extends ListenerType> type();

    /**
     * 优先级
     * （具体执行顺序需要结合@Listen注解的priorityAsc属性共同决定）
     */
    int priority() default Integer.MAX_VALUE;
}
