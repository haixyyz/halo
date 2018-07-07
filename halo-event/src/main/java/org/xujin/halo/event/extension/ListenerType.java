package org.xujin.halo.event.extension;

/**
 * 监听器类型
 */
public interface ListenerType {

    /**
     * 获取事件类型解决器
     */
    EventTypeResolver getResolver();
}
