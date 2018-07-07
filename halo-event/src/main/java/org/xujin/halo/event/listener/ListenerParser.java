package org.xujin.halo.event.listener;

import org.xujin.halo.event.annotation.listener.Listen;
import org.xujin.halo.event.annotation.listener.Listener;
import org.xujin.halo.event.extension.EventTypeResolver;
import org.xujin.halo.event.extension.ListenResolver;
import org.xujin.halo.event.extension.ListenerType;
import org.xujin.halo.event.listener.ListenerExecutor.ListenExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.support.AopUtils;
import org.springframework.cglib.core.ReflectUtils;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.util.ClassUtils;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/**
 * 监听器解析器
 */
public class ListenerParser {
    // 日志记录器
    private static final Logger logger = LoggerFactory.getLogger(ListenerParser.class);

    /**
     * 解析监听器
     *
     * @param listener 监听器
     * @return 监听器执行器
     */
    public static ListenerExecutor parseListener(Object listener) {
        // 获取目标class（应对AOP代理情况）
        Class<?> listenerClass = AopUtils.getTargetClass(listener);
        logger.debug("解析监听器：{}", ClassUtils.getQualifiedName(listenerClass));
        // 此处得到的@Listener是已经经过@AliasFor属性别名进行属性同步后的结果
        Listener listenerAnnotation = AnnotatedElementUtils.findMergedAnnotation(listenerClass, Listener.class);
        // 创建监听器执行器
        ListenerExecutor listenerExecutor = new ListenerExecutor(listenerAnnotation.type(), listenerAnnotation.priority(), listener, parseEventTypeResolver(listenerAnnotation.type()));
        for (Method method : listenerClass.getDeclaredMethods()) {
            Listen listenAnnotation = AnnotatedElementUtils.findMergedAnnotation(method, Listen.class);
            if (listenAnnotation != null) {
                ListenExecutor listenExecutor = parseListen(listenAnnotation, method);
                listenerExecutor.addListenExecutor(listenExecutor);
            }
        }
        listenerExecutor.validate();

        return listenerExecutor;
    }

    /**
     * 通过监听器类型解析得到事件类型解决器
     *
     * @param clazz 监听器类型
     */
    public static EventTypeResolver parseEventTypeResolver(Class<? extends ListenerType> clazz) {
        ListenerType listenerType = (ListenerType) ReflectUtils.newInstance(clazz);
        return listenerType.getResolver();
    }

    // 解析监听方法
    private static ListenExecutor parseListen(Listen listenAnnotation, Method method) {
        logger.debug("解析监听方法：{}", method);
        // 校验方法类型
        if (!Modifier.isPublic(method.getModifiers())) {
            throw new IllegalArgumentException("监听方法" + ClassUtils.getQualifiedMethodName(method) + "必须是public类型");
        }
        // 校验返回类型
        if (method.getReturnType() != void.class) {
            throw new IllegalArgumentException("监听方法" + ClassUtils.getQualifiedMethodName(method) + "的返回必须是void");
        }
        // 创建监听解决器
        ListenResolver resolver = (ListenResolver) ReflectUtils.newInstance(listenAnnotation.resolver());
        resolver.init(method);

        return new ListenExecutor(resolver, listenAnnotation.priorityAsc(), method);
    }
}
