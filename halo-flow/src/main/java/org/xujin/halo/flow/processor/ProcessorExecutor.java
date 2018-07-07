
package org.xujin.halo.flow.processor;

import org.springframework.util.ClassUtils;
import org.xujin.halo.flow.annotation.processor.*;
import org.xujin.halo.flow.engine.TargetContext;
import org.xujin.halo.method.MethodExecutor;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * 处理器执行器
 */
public class ProcessorExecutor {
    /**
     * 处理器方法注解
     */
    public static final Class[] PROCESSOR_METHOD_ANNOTATIONS = {ProcessorBefore.class, ProcessorExecute.class, ProcessorAfter.class, ProcessorEnd.class, ProcessorError.class};

    // 处理器名称
    private String processorName;
    // 处理器
    private Object processor;
    // 处理器方法执行器Map（key：处理器方法注解的Class）
    private Map<Class, ProcessorMethodExecutor> methodExecutorMap = new HashMap<>();

    public ProcessorExecutor(String processorName, Object processor) {
        this.processorName = processorName;
        this.processor = processor;
    }

    /**
     * 执行处理器
     * （顺序：@ProcessorBefore、@ProcessorExecute、@ProcessorAfter、@ProcessorEnd；如果执行@ProcessorBefore、@ProcessorExecute、@ProcessorAfter发生异常，则会在执行@ProcessorEnd之前执行@ProcessorError）
     *
     * @param targetContext 目标上下文
     * @return Execute类型方法返回的结果
     * @throws Throwable 执行过程中发生任何异常都后会往外抛
     */
    public Object execute(TargetContext targetContext) throws Throwable {
        try {
            executeMethod(ProcessorBefore.class, targetContext);
            Object result = executeMethod(ProcessorExecute.class, targetContext);
            executeMethod(ProcessorAfter.class, targetContext);
            return result;
        } catch (Throwable e) {
            executeMethod(ProcessorError.class, targetContext);
            throw e;
        } finally {
            executeMethod(ProcessorEnd.class, targetContext);
        }
    }

    // 执行处理器方法（对于不存在对应的方法，则忽略并返回null）
    private Object executeMethod(Class clazz, TargetContext targetContext) throws Throwable {
        ProcessorMethodExecutor methodExecutor = methodExecutorMap.get(clazz);
        if (methodExecutor == null) {
            return null;
        }
        return methodExecutor.execute(processor, targetContext);
    }

    /**
     * 设置处理器方法执行器
     *
     * @param clazz          处理器方法注解
     * @param methodExecutor 方法执行器
     * @throws IllegalArgumentException 如果annotationClass不是处理器方法注解
     * @throws IllegalStateException    如果已存在该类型的处理器方法执行器
     */
    public void setMethodExecutor(Class clazz, ProcessorMethodExecutor methodExecutor) {
        if (!Arrays.asList(PROCESSOR_METHOD_ANNOTATIONS).contains(clazz)) {
            throw new IllegalArgumentException(ClassUtils.getShortName(clazz) + "不是处理器方法注解");
        }
        if (methodExecutorMap.containsKey(clazz)) {
            throw new IllegalStateException("处理器" + processorName + "存在多个@" + ClassUtils.getShortName(clazz) + "类型的方法");
        }
        methodExecutorMap.put(clazz, methodExecutor);
    }

    /**
     * 获取返回类型
     */
    public Class getReturnType() {
        return methodExecutorMap.get(ProcessorExecute.class).getReturnType();
    }

    /**
     * 获取目标对象类型
     */
    public Class getClassOfTarget() {
        return methodExecutorMap.get(ProcessorExecute.class).getClassOfTarget();
    }

    /**
     * 获取处理器名称
     */
    public String getProcessorName() {
        return processorName;
    }

    /**
     * 获取处理器
     */
    public Object getProcessor() {
        return processor;
    }

    /**
     * 校验处理器执行器是否有效
     *
     * @throws IllegalStateException 校验不通过
     */
    public void validate() {
        if (processorName == null || processor == null) {
            throw new IllegalStateException("处理器" + processorName + "内部要素不全");
        }
        if (!methodExecutorMap.containsKey(ProcessorExecute.class)) {
            throw new IllegalStateException("处理器" + processorName + "不存在@ProcessorExecute类型的处理器方法");
        }
        // 校验处理器内部目标对象类型是否统一
        for (ProcessorMethodExecutor methodExecutor : methodExecutorMap.values()) {
            if (methodExecutor.getClassOfTarget() != getClassOfTarget()) {
                throw new IllegalStateException("处理器" + processorName + "内目标对象类型不统一");
            }
        }
    }

    /**
     * 处理器方法执行器
     */
    public static class ProcessorMethodExecutor extends MethodExecutor {
        // 目标对象类型
        private Class classOfTarget;

        public ProcessorMethodExecutor(Method targetMethod, Class classOfTarget) {
            super(targetMethod);
            this.classOfTarget = classOfTarget;
        }

        /**
         * 执行处理器方法
         *
         * @param processor     处理器
         * @param targetContext 目标上下文
         * @throws Throwable 执行过程中发生任何异常都会往外抛
         */
        public Object execute(Object processor, TargetContext targetContext) throws Throwable {
            return execute(processor, new Object[]{targetContext});
        }

        /**
         * 获取目标对象类型
         */
        public Class getClassOfTarget() {
            return classOfTarget;
        }
    }
}
