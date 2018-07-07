package org.xujin.halo.event;

import org.xujin.halo.dto.event.Event;
import org.xujin.halo.exception.InfraException;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 事件控制中枢
 * @author xujin
 * @date 2018/5/20
 */
@SuppressWarnings("rawtypes")
@Component
public class EventHub {
    @Getter
    @Setter
    private ListMultimap<Class, EventHandlerI> eventRepository = ArrayListMultimap.create();
    
    @Getter
    private Map<Class, Class> responseRepository = new HashMap<>();
    
    public List<EventHandlerI> getEventHandler(Class eventClass) {
        List<EventHandlerI> eventHandlerIList = findHandler(eventClass);
        if (eventHandlerIList == null || eventHandlerIList.size() == 0) {
            throw new InfraException(eventClass + "is not registered in eventHub, please register first");
        }
        return eventHandlerIList;
    }

    /**
     * 注册事件
     * @param eventClz
     * @param executor
     */
    public void register(Class<? extends Event> eventClz, EventHandlerI executor){
        eventRepository.put(eventClz, executor);
    }

    private List<EventHandlerI> findHandler(Class<? extends Event> eventClass){
        List<EventHandlerI> eventHandlerIList = null;
        Class cls = eventClass;
        eventHandlerIList = eventRepository.get(cls);
        return eventHandlerIList;
    }

}
