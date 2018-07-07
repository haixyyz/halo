package org.xujin.halo.event.extension.support;

import org.xujin.halo.event.extension.EventTypeResolver;
import org.xujin.halo.event.extension.ListenerType;

/**
 * biz监听器类型
 */
public class BizListenerType implements ListenerType {

    @Override
    public EventTypeResolver getResolver() {
        return ClassEventTypeResolver.INSTANCE;
    }
}
