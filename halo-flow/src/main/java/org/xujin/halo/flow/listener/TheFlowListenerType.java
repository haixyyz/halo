package org.xujin.halo.flow.listener;

import org.xujin.halo.event.extension.EventTypeResolver;
import org.xujin.halo.event.extension.ListenerType;
import org.xujin.halo.flow.event.FlowExceptionEvent;
import org.xujin.halo.flow.event.NodeDecidedEvent;

/**
 * 特定流程监听器类型
 */
public class TheFlowListenerType implements ListenerType {

    @Override
    public EventTypeResolver getResolver() {
        return TheFlowEventTypeResolver.INSTANCE;
    }

    // 特定流程事件类型解决器
    private static class TheFlowEventTypeResolver implements EventTypeResolver {
        // 实例
        private static final TheFlowEventTypeResolver INSTANCE = new TheFlowEventTypeResolver();

        @Override
        public Object resolve(Object event) {
            if (event instanceof NodeDecidedEvent) {
                return new TheFlowEventType(((NodeDecidedEvent) event).getFlow(), NodeDecidedEvent.class);
            }
            if (event instanceof FlowExceptionEvent) {
                return new TheFlowEventType(((FlowExceptionEvent) event).getFlow(), FlowExceptionEvent.class);
            }
            throw new IllegalArgumentException("无法识别的流程事件：" + event);
        }
    }
}
