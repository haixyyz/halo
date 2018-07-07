package org.xujin.halo.boot;

import org.xujin.halo.common.CoreConstant;
import org.xujin.halo.dto.event.Event;
import org.xujin.halo.event.EventHandlerI;
import org.xujin.halo.event.EventHub;
import org.xujin.halo.exception.InfraException;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * EventRegister
 *
 * @author xujin
 * @date 2018/3/20
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
@Component
public class EventRegister extends AbstractRegister implements ApplicationContextAware {

    @Autowired
    private EventHub eventHub;

    @Override
    public void doRegistration(Class<?> targetClz) {
        Class<? extends Event> eventClz = getEventFromExecutor(targetClz);
        EventHandlerI executor = getBean(targetClz);
        eventHub.register(eventClz, executor);
    }

    private Class<? extends Event> getEventFromExecutor(Class<?> eventExecutorClz) {
        Method[] methods = eventExecutorClz.getDeclaredMethods();
        for (Method method : methods) {
            Class<?>[] exeParams = method.getParameterTypes();
            /**
             * This is for return right response type on exception scenarios
             */
            if (CoreConstant.EXE_METHOD.equals(method.getName()) && exeParams.length == 1
                && Event.class.isAssignableFrom(exeParams[0]) && !method.isBridge()) {
                eventHub.getResponseRepository().put(eventExecutorClz, method.getReturnType());
                return (Class<? extends Event>) exeParams[0];
            }
        }
        throw new InfraException("Event param in " + eventExecutorClz + " " + CoreConstant.EXE_METHOD
                                 + "() is not detected");
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
