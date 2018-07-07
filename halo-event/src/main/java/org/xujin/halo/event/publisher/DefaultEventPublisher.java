package org.xujin.halo.event.publisher;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.xujin.halo.event.EventPublisher;
import org.xujin.halo.event.bus.EventBus;

/**
 * 事件发布器默认实现类
 */
public class DefaultEventPublisher implements EventPublisher {
    // 事件总线
    private EventBus eventBus;

    public DefaultEventPublisher(EventBus eventBus) {
        this.eventBus = eventBus;
    }

    @Override
    public void publish(Object event) {
        try {
            eventBus.dispatch(event);
        } catch (Throwable e) {
            ExceptionUtils.rethrow(e);
        }
    }
}
