package org.xujin.halo.flow.transaction;

import org.xujin.halo.flow.annotation.transaction.FlowTx;
import org.xujin.halo.flow.engine.TargetContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.support.AopUtils;
import org.springframework.core.ResolvableType;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.util.ClassUtils;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/**
 * 流程事务解析器
 */
public class FlowTxParser {
    // 日志记录器
    private static final Logger logger = LoggerFactory.getLogger(FlowTxParser.class);

    /**
     * 解析流程事务
     *
     * @param flowTx             流程事务
     * @param transactionManager 事务管理器
     * @return 流程事务执行器
     */
    public static FlowTxExecutor parseFlowTx(Object flowTx, PlatformTransactionManager transactionManager) {
        // 获取目标class（应对AOP代理情况）
        Class<?> flowTxClass = AopUtils.getTargetClass(flowTx);
        logger.debug("解析流程事务：{}", ClassUtils.getQualifiedName(flowTxClass));
        FlowTx flowTxAnnotation = flowTxClass.getAnnotation(FlowTx.class);
        // 创建流程事务执行器
        FlowTxExecutor flowTxExecutor = new FlowTxExecutor(flowTxAnnotation.flow(), flowTx, transactionManager);
        for (Method method : flowTxClass.getDeclaredMethods()) {
            for (Class clazz : FlowTxExecutor.FLOW_TX_OPERATE_ANNOTATIONS) {
                if (method.isAnnotationPresent(clazz)) {
                    // 设置流程事务操作执行器
                    flowTxExecutor.setOperateExecutor(clazz, parseFlowTxOperate(method));
                    break;
                }
            }
        }
        flowTxExecutor.validate();

        return flowTxExecutor;
    }

    // 解析流程事务操作
    private static FlowTxExecutor.FlowTxOperateExecutor parseFlowTxOperate(Method method) {
        logger.debug("解析流程事务方法：{}", method);
        // 校验方法类型
        if (!Modifier.isPublic(method.getModifiers())) {
            throw new IllegalArgumentException("流程事务方法" + ClassUtils.getQualifiedMethodName(method) + "必须是public类型");
        }
        // 校验入参
        Class[] parameterTypes = method.getParameterTypes();
        if (parameterTypes.length != 1) {
            throw new IllegalArgumentException("流程事务方法" + ClassUtils.getQualifiedMethodName(method) + "的入参必须是（TargetContext）");
        }
        if (parameterTypes[0] != TargetContext.class) {
            throw new IllegalArgumentException("流程事务方法" + ClassUtils.getQualifiedMethodName(method) + "的入参必须是（TargetContext）");
        }
        // 获取目标对象类型
        ResolvableType resolvableType = ResolvableType.forMethodParameter(method, 0);
        Class classOfTarget = resolvableType.getGeneric(0).resolve(Object.class);
        // 校验返回参数
        if (method.getReturnType() != classOfTarget) {
            throw new IllegalArgumentException("流程事务方法" + ClassUtils.getQualifiedMethodName(method) + "的返回类型必须是目标对象类型");
        }

        return new FlowTxExecutor.FlowTxOperateExecutor(method, classOfTarget);
    }
}
