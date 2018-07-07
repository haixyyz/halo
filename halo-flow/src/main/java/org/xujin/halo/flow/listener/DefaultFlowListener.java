package org.xujin.halo.flow.listener;

import org.xujin.halo.event.EventPublisher;
import org.xujin.halo.event.annotation.Listen;
import org.xujin.halo.event.bus.EventBusesHolder;
import org.xujin.halo.event.publisher.DefaultEventPublisher;
import org.xujin.halo.flow.annotation.listener.FlowListener;
import org.xujin.halo.flow.event.FlowExceptionEvent;
import org.xujin.halo.flow.event.NodeDecidedEvent;
import org.springframework.context.annotation.DependsOn;

/**
 * 默认的流程监听器
 * （流程引擎初始化时会初始化本监听器，其作用是监听所有流程发生的事件，然后将事件转发给对应流程的特定流程监听器（@TheFlowListener））
 */
@FlowListener
@DependsOn("EventBusesHolder")      // 保证出现循环引用时不会出错
public class DefaultFlowListener {
    // 特定流程事件发布器
    private EventPublisher eventPublisher;

    public DefaultFlowListener(EventBusesHolder eventBusesHolder) {
        eventPublisher = new DefaultEventPublisher(eventBusesHolder.getEventBus(TheFlowListenerType.class));
    }

    // 监听节点选择事件
    @Listen
    public void listenNodeDecidedEvent(NodeDecidedEvent event) {
        eventPublisher.publish(event);
    }

    // 监听流程异常事件
    @Listen
    public void listenFlowExceptionEvent(FlowExceptionEvent event) {
        eventPublisher.publish(event);
    }
}
