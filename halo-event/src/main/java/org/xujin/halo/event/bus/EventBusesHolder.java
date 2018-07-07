package org.xujin.halo.event.bus;

import org.xujin.halo.event.extension.ListenerType;
import org.xujin.halo.event.listener.ListenerExecutor;
import org.xujin.halo.event.listener.ListenerParser;
import org.xujin.halo.event.listener.ListenersHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

/**
 * 事件总线持有器
 */
@Component
public class EventBusesHolder {
    @Autowired
    private ListenersHolder listenersHolder;
    // 事件总线Map（key：总线类型）
    private Map<Class, EventBus> eventBusMap = new HashMap<>();

    // 初始化（根据监听器类型创建相应类型的事件总线，spring自动执行）
    @PostConstruct
    public void init() {
        for (Class type : listenersHolder.getTypes()) {
            // 初始化事件总线
            EventBus eventBus = getEventBus(type);
            for (ListenerExecutor listenerExecutor : listenersHolder.getRequiredListenerExecutors(type)) {
                eventBus.register(listenerExecutor);
            }
        }
    }

    /**
     * 获取事件总线
     * （如果不存在该类型的事件总线，则新创建一个）
     *
     * @param type 总线类型
     */
    public synchronized EventBus getEventBus(Class<? extends ListenerType> type) {
        if (!eventBusMap.containsKey(type)) {
            eventBusMap.put(type, new EventBus(ListenerParser.parseEventTypeResolver(type)));
        }
        return eventBusMap.get(type);
    }
}
