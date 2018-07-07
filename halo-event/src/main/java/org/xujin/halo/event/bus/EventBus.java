package org.xujin.halo.event.bus;

import org.xujin.halo.event.extension.EventTypeResolver;
import org.xujin.halo.event.listener.ListenerExecutor;

import java.util.*;

/**
 * 事件总线
 */
public class EventBus {
    // 监听器执行器
    private List<ListenerExecutor> listenerExecutors = new ArrayList<>();
    // 监听器执行器缓存（key：事件类型）
    private Map<Object, List<ListenerExecutor>> listenerExecutorsCache = new HashMap<>();
    // 事件类型解决器
    private EventTypeResolver resolver;

    public EventBus(EventTypeResolver resolver) {
        this.resolver = resolver;
    }

    /**
     * 注册监听器
     *
     * @param listenerExecutor 监听器执行器
     */
    public void register(ListenerExecutor listenerExecutor) {
        listenerExecutors.add(listenerExecutor);
        Collections.sort(listenerExecutors);
        refreshListenerCache();
    }

    /**
     * 分派事件
     * （先执行优先级升序，再执行优先级降序）
     *
     * @param event 事件
     * @throws Throwable 执行过程中发生任何异常都会往外抛
     */
    public void dispatch(Object event) throws Throwable {
        // 获取该事件类型的监听器缓存
        List<ListenerExecutor> theListenerExecutors = listenerExecutorsCache.get(resolver.resolve(event));
        if (theListenerExecutors != null) {
            // 执行监听器
            for (ListenerExecutor listenerExecutor : theListenerExecutors) {
                listenerExecutor.execute(event);
            }
        }
    }

    // 刷新监听器缓存
    private void refreshListenerCache() {
        listenerExecutorsCache = new HashMap<>();
        // 获取本总线所有的事件类型
        Set<Object> eventTypes = new HashSet<>();
        for (ListenerExecutor listenerExecutor : listenerExecutors) {
            eventTypes.addAll(listenerExecutor.getEventTypes(true));
            eventTypes.addAll(listenerExecutor.getEventTypes(false));
        }
        // 根据事件类型设置缓存
        for (Object eventType : eventTypes) {
            // 特定事件类型的监听器缓存
            List<ListenerExecutor> theListenerExecutors = new ArrayList<>();
            // 获取指定事件类型的升序监听器
            for (ListenerExecutor listenerExecutor : listenerExecutors) {
                if (listenerExecutor.getEventTypes(true).contains(eventType)) {
                    theListenerExecutors.add(listenerExecutor);
                }
            }
            // 获取指定事件类型的降序监听器
            for (int i = listenerExecutors.size() - 1; i >= 0; i--) {
                ListenerExecutor listenerExecutor = listenerExecutors.get(i);
                if (listenerExecutor.getEventTypes(false).contains(eventType)) {
                    theListenerExecutors.add(listenerExecutor);
                }
            }
            // 设置缓存
            listenerExecutorsCache.put(eventType, theListenerExecutors);
        }
    }
}
