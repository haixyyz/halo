package org.xujin.halo.event;

/**
 * 事件发布器
 */
public interface EventPublisher {

    /**
     * 发布事件
     *
     * @param event 事件
     */
    void publish(Object event);

}
