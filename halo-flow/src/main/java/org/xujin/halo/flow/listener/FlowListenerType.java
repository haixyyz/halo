package org.xujin.halo.flow.listener;

import org.xujin.halo.event.extension.EventTypeResolver;
import org.xujin.halo.event.extension.ListenerType;
import org.xujin.halo.event.extension.support.ClassEventTypeResolver;

/**
 * 流程监听器类型
 */
public class FlowListenerType implements ListenerType {

    @Override
    public EventTypeResolver getResolver() {
        return ClassEventTypeResolver.INSTANCE;
    }
}
