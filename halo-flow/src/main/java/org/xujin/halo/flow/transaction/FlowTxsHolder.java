package org.xujin.halo.flow.transaction;

import org.xujin.halo.flow.annotation.transaction.FlowTx;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 流程事务持有器
 */
@Component
public class FlowTxsHolder {
    @Autowired
    private ApplicationContext applicationContext;
    @Autowired(required = false)
    private PlatformTransactionManager transactionManager;
    // 流程事务执行器Map（key：流程事务对应的流程名称）
    private Map<String, FlowTxExecutor> flowTxExecutorMap = new HashMap<>();

    // 初始化（查询spring容器中所有的@FlowTx流程事务并解析，spring自动执行）
    @PostConstruct
    public void init() {
        String[] beanNames = applicationContext.getBeanNamesForAnnotation(FlowTx.class);
        if (beanNames.length > 0 && transactionManager == null) {
            throw new RuntimeException("存在流程事务但是不存在事务管理器（PlatformTransactionManager），请检查是否有配置spring事务管理器");
        }
        for (String beanName : beanNames) {
            // 解析流程事务
            FlowTxExecutor flowTxExecutor = FlowTxParser.parseFlowTx(applicationContext.getBean(beanName), transactionManager);
            if (flowTxExecutorMap.containsKey(flowTxExecutor.getFlow())) {
                throw new RuntimeException("流程" + flowTxExecutor.getFlow() + "存在多个流程事务");
            }
            // 将执行器放入持有器中
            flowTxExecutorMap.put(flowTxExecutor.getFlow(), flowTxExecutor);
        }
    }

    /**
     * 获取所有流程事务对应的流程名称
     */
    public Set<String> getFlowNames() {
        return flowTxExecutorMap.keySet();
    }

    /**
     * 获取流程事务执行器
     *
     * @param flow 流程名称
     * @throws IllegalArgumentException 如果不存在该流程事务处理器
     */
    public FlowTxExecutor getRequiredFlowTxExecutor(String flow) {
        if (!flowTxExecutorMap.containsKey(flow)) {
            throw new IllegalArgumentException("不存在流程" + flow + "的流程事务");
        }
        return flowTxExecutorMap.get(flow);
    }
}
