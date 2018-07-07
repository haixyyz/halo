package org.xujin.halo.flow.processor;

import org.apache.commons.lang3.StringUtils;
import org.xujin.halo.flow.annotation.processor.Processor;
import org.xujin.halo.flow.annotation.processor.ProcessorExecute;
import org.xujin.halo.flow.engine.TargetContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.support.AopUtils;
import org.springframework.core.ResolvableType;
import org.springframework.util.ClassUtils;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/**
 * 处理器解析器
 */
public class ProcessorParser {
    // 日志记录器
    private static final Logger logger = LoggerFactory.getLogger(ProcessorParser.class);

    /**
     * 解析处理器
     *
     * @param processor 处理器
     * @return 处理器执行器
     */
    public static ProcessorExecutor parseProcessor(Object processor) {
        // 获取目标class（应对AOP代理情况）
        Class<?> processorClass = AopUtils.getTargetClass(processor);
        logger.debug("解析处理器：{}", ClassUtils.getQualifiedName(processorClass));
        // 获取处理器名称
        String processorName = processorClass.getAnnotation(Processor.class).name();
        if (StringUtils.isEmpty(processorName)) {
            processorName = ClassUtils.getShortNameAsProperty(processorClass);
        }
        // 创建处理器执行器
        ProcessorExecutor processorExecutor = new ProcessorExecutor(processorName, processor);
        for (Method method : processorClass.getDeclaredMethods()) {
            for (Class clazz : ProcessorExecutor.PROCESSOR_METHOD_ANNOTATIONS) {
                if (method.isAnnotationPresent(clazz)) {
                    // 设置处理器方法执行器
                    processorExecutor.setMethodExecutor(clazz, parseProcessorMethod(clazz, method));
                    break;
                }
            }
        }
        processorExecutor.validate();

        return processorExecutor;
    }

    /**
     * 解析处理器方法
     */
    private static ProcessorExecutor.ProcessorMethodExecutor parseProcessorMethod(Class clazz, Method method) {
        logger.debug("解析处理器方法：{}", method);
        // 校验方法类型
        if (!Modifier.isPublic(method.getModifiers())) {
            throw new IllegalArgumentException("处理器方法" + ClassUtils.getQualifiedMethodName(method) + "必须是public类型");
        }
        // 校验入参
        Class[] parameterTypes = method.getParameterTypes();
        if (parameterTypes.length != 1) {
            throw new IllegalArgumentException("处理器方法" + ClassUtils.getQualifiedMethodName(method) + "入参必须是（TargetContext）");
        }
        if (parameterTypes[0] != TargetContext.class) {
            throw new IllegalArgumentException("处理器方法" + ClassUtils.getQualifiedMethodName(method) + "入参必须是（TargetContext）");
        }
        // 校验返回类型
        if (clazz != ProcessorExecute.class && method.getReturnType() != void.class) {
            throw new IllegalArgumentException("非@ProcessorExecute类型的处理器方法" + ClassUtils.getQualifiedMethodName(method) + "的返回类型必须是void");
        }
        // 获取目标对象类型
        ResolvableType resolvableType = ResolvableType.forMethodParameter(method, 0);
        Class classOfTarget = resolvableType.getGeneric(0).resolve(Object.class);

        return new ProcessorExecutor.ProcessorMethodExecutor(method, classOfTarget);
    }
}
