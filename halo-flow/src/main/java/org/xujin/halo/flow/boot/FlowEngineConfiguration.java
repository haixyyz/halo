package org.xujin.halo.flow.boot;

import org.xujin.halo.event.boot.EventBusConfiguration;
import org.xujin.halo.flow.FlowEngine;
import org.xujin.halo.flow.engine.DefaultFlowEngine;
import org.xujin.halo.flow.flow.FlowsHolder;
import org.xujin.halo.flow.listener.DefaultFlowListener;
import org.xujin.halo.flow.processor.ProcessorsHolder;
import org.xujin.halo.flow.transaction.FlowTxsHolder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Import;

/**
 * 流程引擎配置类
 * （非spring-boot项目需手动引入本配置类完成流程引擎配置）
 */
@Configuration
@Import({EventBusConfiguration.class,
        FlowsHolder.class,
        ProcessorsHolder.class,
        FlowTxsHolder.class,
        DefaultFlowListener.class})
public class FlowEngineConfiguration {

    // 流程引擎
    @Bean
    @DependsOn({"FlowsHolder", "FlowTxsHolder"})     // 保证出现循环引用时不会出错
    public FlowEngine flowEngine(FlowsHolder flowsHolder, FlowTxsHolder flowTxsHolder) {
        return new DefaultFlowEngine(flowsHolder, flowTxsHolder);
    }
}
