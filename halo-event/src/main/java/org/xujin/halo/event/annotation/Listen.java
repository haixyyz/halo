package org.xujin.halo.event.annotation;

import org.xujin.halo.event.extension.support.ClassListenResolver;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

/**
 * 监听
 */
@Documented
@Target({ElementType.METHOD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@org.xujin.halo.event.annotation.listener.Listen(resolver = ClassListenResolver.class)
public @interface Listen {

    /**
     * 是否按照优先级升序
     * <p>
     * true：表示升序，即监听器中优先级值越小优先级越高；false：表示降序，即监听器中优先级值越大优先级越高。默认为升序。
     * 当一个事件发布时，总是先执行完优先级为升序的监听方法，再执行优先级为降序的监听方法
     */
    @AliasFor(annotation = org.xujin.halo.event.annotation.listener.Listen.class, attribute = "priorityAsc")
    boolean priorityAsc() default true;
}
