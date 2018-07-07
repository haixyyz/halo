package org.xujin.halo.event.boot;

import org.xujin.halo.event.EventPublisher;
import org.xujin.halo.event.bus.EventBusesHolder;
import org.xujin.halo.event.extension.support.BizListenerType;
import org.xujin.halo.event.listener.ListenersHolder;
import org.xujin.halo.event.publisher.DefaultEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Import;

/**
 * 事件总线配置类
 * （非spring-boot项目需手动引入本配置类完成事件总线配置）
 */
@Configuration
@Import({EventBusesHolder.class, ListenersHolder.class})
public class EventBusConfiguration {

    // 业务事件发布器
    @Bean
    @DependsOn("EventBusesHolder")      // 保证出现循环引用时不会出错
    public EventPublisher eventPublisher(EventBusesHolder eventBusesHolder) {
        return new DefaultEventPublisher(eventBusesHolder.getEventBus(BizListenerType.class));
    }
}
