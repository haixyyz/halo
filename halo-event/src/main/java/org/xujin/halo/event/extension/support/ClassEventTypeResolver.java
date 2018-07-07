package org.xujin.halo.event.extension.support;

import org.xujin.halo.event.extension.EventTypeResolver;

/**
 * Class事件类型解决器（事件类型就是事件对应的Class类）
 */
public class ClassEventTypeResolver implements EventTypeResolver {
    /**
     * 实例
     */
    public static final ClassEventTypeResolver INSTANCE = new ClassEventTypeResolver();

    @Override
    public Object resolve(Object event) {
        return event.getClass();
    }
}
