package org.xujin.halo.flow.annotation.listener;

import org.xujin.halo.event.annotation.listener.Listener;
import org.xujin.halo.flow.listener.FlowListenerType;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

/**
 * 流程监听器
 * （监听的是所有流程发生的事件）
 */
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Listener(type = FlowListenerType.class)
public @interface FlowListener {

    /**
     * 优先级
     */
    @AliasFor(annotation = Listener.class, attribute = "priority")
    int priority() default Integer.MAX_VALUE;
}
