package org.xujin.halo.flow.listener;

import org.xujin.halo.event.extension.ListenResolver;
import org.xujin.halo.flow.annotation.listener.TheFlowListener;
import org.xujin.halo.flow.engine.TargetContext;
import org.xujin.halo.flow.event.NodeDecidedEvent;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.util.ClassUtils;

import java.lang.reflect.Method;

/**
 * 监听注解@ListenNodeDecided的解决器
 */
public class ListenNodeDecidedResolver implements ListenResolver {
    // 监听的事件类型
    private TheFlowEventType eventType;

    @Override
    public void init(Method listenMethod) {
        TheFlowListener theFlowListenerAnnotation = AnnotatedElementUtils.findMergedAnnotation(listenMethod.getDeclaringClass(), TheFlowListener.class);
        if (theFlowListenerAnnotation == null) {
            throw new IllegalArgumentException("@ListenNodeDecided只能标注在特定流程监听器（@TheFlowListener）的方法上");
        }
        // 校验入参
        Class[] parameterTypes = listenMethod.getParameterTypes();
        if (parameterTypes.length != 2) {
            throw new RuntimeException("监听节点选择方法" + ClassUtils.getQualifiedMethodName(listenMethod) + "的入参必须是（String, TargetContext）");
        }
        if (parameterTypes[0] != String.class || parameterTypes[1] != TargetContext.class) {
            throw new RuntimeException("监听节点选择方法" + ClassUtils.getQualifiedMethodName(listenMethod) + "的入参必须是（String, TargetContext）");
        }
        eventType = new TheFlowEventType(theFlowListenerAnnotation.flow(), NodeDecidedEvent.class);
    }

    @Override
    public Object getEventType() {
        return eventType;
    }

    @Override
    public Object[] resolveArgs(Object event) {
        NodeDecidedEvent nodeDecidedEvent = (NodeDecidedEvent) event;
        return new Object[]{nodeDecidedEvent.getNode(), nodeDecidedEvent.getTargetContext()};
    }
}
