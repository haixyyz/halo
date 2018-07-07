package org.xujin.halo.event.extension.support;

import org.xujin.halo.event.extension.ListenResolver;
import org.springframework.util.ClassUtils;

import java.lang.reflect.Method;

/**
 * Class监听解决器（监听方法只能有一个入参，事件类型就是入参的Class类）
 */
public class ClassListenResolver implements ListenResolver {
    // 监听的事件类型
    private Class eventType;

    @Override
    public void init(Method listenMethod) {
        // 校验入参
        Class[] parameterTypes = listenMethod.getParameterTypes();
        if (parameterTypes.length != 1) {
            throw new IllegalArgumentException(String.format("监听方法%s必须只有一个入参", ClassUtils.getQualifiedMethodName(listenMethod)));
        }
        // 设置事件类型
        eventType = parameterTypes[0];
    }

    @Override
    public Object getEventType() {
        return eventType;
    }

    @Override
    public Object[] resolveArgs(Object event) {
        return new Object[]{event};
    }
}
