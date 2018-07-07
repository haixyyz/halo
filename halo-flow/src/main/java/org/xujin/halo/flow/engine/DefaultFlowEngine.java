package org.xujin.halo.flow.engine;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.xujin.halo.flow.FlowEngine;
import org.xujin.halo.flow.flow.FlowExecutor;
import org.xujin.halo.flow.flow.FlowsHolder;
import org.xujin.halo.flow.transaction.FlowTxExecutor;
import org.xujin.halo.flow.transaction.FlowTxsHolder;
import org.springframework.util.ClassUtils;

import java.util.Map;

/**
 * 流程引擎默认实现类
 */
public class DefaultFlowEngine implements FlowEngine {
    // 流程持有器
    private FlowsHolder flowsHolder;
    // 流程事务持有器
    private FlowTxsHolder flowTxsHolder;

    public DefaultFlowEngine(FlowsHolder flowsHolder, FlowTxsHolder flowTxsHolder) {
        this.flowsHolder = flowsHolder;
        this.flowTxsHolder = flowTxsHolder;
    }

    @Override
    public <T> T start(String flow, T target) {
        return start(flow, target, null);
    }

    @Override
    public <T> T start(String flow, T target, Map<Object, Object> attachment) {
        // 校验目标对象类型
        checkClassOfTarget(target, flow);
        // 构造目标上下文
        TargetContext<T> targetContext = new TargetContext(target, attachment);
        // 执行流程
        executeFlow(flow, targetContext);

        return targetContext.getTarget();
    }

    @Override
    public <T> T insertTarget(String flow, T target, Map<Object, Object> attachment) {
        // 校验目标对象类型
        checkClassOfTarget(target, flow);
        // 构造目标上下文
        TargetContext<T> targetContext = new TargetContext(target, attachment);
        // 执行插入目标对象
        executeInsertTarget(flow, targetContext);

        return targetContext.getTarget();
    }

    @Override
    public <T> T insertTargetAndStart(String flow, T target, Map<Object, Object> attachment) {
        // 校验目标对象类型
        checkClassOfTarget(target, flow);
        // 构造目标上下文
        TargetContext<T> targetContext = new TargetContext(target, attachment);
        // 执行插入目标对象
        executeInsertTarget(flow, targetContext);
        // 执行流程
        executeFlow(flow, targetContext);

        return targetContext.getTarget();
    }

    // 校验目标对象类型
    private void checkClassOfTarget(Object target, String flow) {
        FlowExecutor flowExecutor = flowsHolder.getRequiredFlowExecutor(flow);
        if (!flowExecutor.getClassOfTarget().isAssignableFrom(target.getClass())) {
            throw new IllegalArgumentException(String.format("传入的目标对象的类型[%s]和流程%s期望的类型[%s]不匹配", ClassUtils.getShortName(target.getClass()), flowExecutor.getFlowName(), ClassUtils.getShortName(flowExecutor.getClassOfTarget())));
        }
    }

    // 执行插入目标对象
    private void executeInsertTarget(String flow, TargetContext targetContext) {
        try {
            // 获取流程事务执行器
            FlowTxExecutor flowTxExecutor = flowTxsHolder.getRequiredFlowTxExecutor(flow);
            // 插入目标对象
            flowTxExecutor.insertTarget(targetContext);
        } catch (Throwable e) {
            ExceptionUtils.rethrow(e);
        }
    }

    // 执行流程
    private void executeFlow(String flow, TargetContext targetContext) {
        try {
            // 获取流程执行器
            FlowExecutor flowExecutor = flowsHolder.getRequiredFlowExecutor(flow);
            // 执行流程
            flowExecutor.execute(targetContext);
        } catch (Throwable e) {
            ExceptionUtils.rethrow(e);
        }
    }
}
