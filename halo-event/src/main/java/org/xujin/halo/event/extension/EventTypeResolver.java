package org.xujin.halo.event.extension;

/**
 * 事件类型解决器
 */
public interface EventTypeResolver {

    /**
     * 根据事件得到对应的事件类型
     *
     * @param event 事件
     * @return 事件类型
     */
    Object resolve(Object event);
}
