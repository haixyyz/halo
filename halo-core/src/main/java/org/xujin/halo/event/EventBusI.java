package org.xujin.halo.event;

import org.xujin.halo.dto.event.Event;
import org.xujin.halo.dto.Response;


/**
 * EventBus interface
 * @author xujin
 * @date 2018/3/20
 */
public interface EventBusI {

    /**
     * Send event to EventBus
     * 发送事件到事件Bus
     * @param event
     * @return Response
     */
    public Response fire(Event event);

    /**
     * fire all handlers which registed the event
     *  触发已经注册的所有注册事件对应的处理器
     * @param event
     * @return Response
     */
    public void fireAll(Event event);

    /**
     *  异步触发对应注册事件的对应的处理器
     * Async fire all handlers
     * @param event
     */
    public void asyncFire(Event event);
}
