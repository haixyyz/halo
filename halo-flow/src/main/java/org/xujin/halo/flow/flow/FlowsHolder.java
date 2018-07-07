package org.xujin.halo.flow.flow;

import org.xujin.halo.event.bus.EventBusesHolder;
import org.xujin.halo.flow.annotation.flow.Flow;
import org.xujin.halo.flow.processor.ProcessorsHolder;
import org.xujin.halo.flow.transaction.FlowTxsHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 流程持有器
 */
@Component
public class FlowsHolder {
    @Autowired
    private ApplicationContext applicationContext;
    @Autowired
    private ProcessorsHolder processorsHolder;
    @Autowired
    private FlowTxsHolder flowTxsHolder;
    @Autowired
    private EventBusesHolder eventBusesHolder;
    // 流程执行器Map（key：流程名称）
    private Map<String, FlowExecutor> flowExecutorMap = new HashMap<>();

    // 初始化（查询spring容器中所有的@Flow流程并解析，spring自动执行）
    @PostConstruct
    public void init() {
        String[] beanNames = applicationContext.getBeanNamesForAnnotation(Flow.class);
        for (String beanName : beanNames) {
            // 解析流程
            FlowExecutor flowExecutor = FlowParser.parseFlow(applicationContext.getBean(beanName), processorsHolder, flowTxsHolder, eventBusesHolder);
            if (flowExecutorMap.containsKey(flowExecutor.getFlowName())) {
                throw new RuntimeException("存在重名的流程" + flowExecutor.getFlowName());
            }
            // 将执行器放入持有器中
            flowExecutorMap.put(flowExecutor.getFlowName(), flowExecutor);
        }
    }

    /**
     * 获取所有流程名称
     */
    public Set<String> getFlowNames() {
        return flowExecutorMap.keySet();
    }

    /**
     * 获取流程执行器
     *
     * @param flow 流程名称
     * @throws IllegalArgumentException 如果不存在该流程执行器
     */
    public FlowExecutor getRequiredFlowExecutor(String flow) {
        if (!flowExecutorMap.containsKey(flow)) {
            throw new IllegalArgumentException("不存在流程" + flow);
        }
        return flowExecutorMap.get(flow);
    }
}
