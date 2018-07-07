package org.xujin.halo.flow.annotation.listener;

import org.xujin.halo.event.annotation.listener.Listener;
import org.xujin.halo.flow.listener.TheFlowListenerType;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

/**
 * 特定流程监听器
 * （监听的是某一个特定流程发生的事件，配合@ListenNodeDecide、@ListenFlowException一起使用）
 */
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Listener(type = TheFlowListenerType.class)
public @interface TheFlowListener {

    /**
     * 被监听的流程
     */
    String flow();

    /**
     * 优先级
     */
    @AliasFor(annotation = Listener.class, attribute = "priority")
    int priority() default Integer.MAX_VALUE;
}
