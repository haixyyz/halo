package org.xujin.halo.event;

import org.xujin.halo.dto.event.Event;
import org.xujin.halo.dto.Response;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;

/**
 *  event handler
 *  事件处理器的抽象接口
 * @author xujin
 * @date 2018/6/20
 */
public interface EventHandlerI<R extends Response, E extends Event> {

    default public ExecutorService getExecutor(){
        return null;
    }

    public R execute(E e);
}
