package org.xujin.halo.method;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 方法执行器
 */
public abstract class MethodExecutor {
    // 目标方法
    private Method targetMethod;

    public MethodExecutor(Method targetMethod) {
        this.targetMethod = targetMethod;
    }

    /**
     * 执行方法
     *
     * @param obj  被执行的对象
     * @param args 需传入目标方法的参数
     * @return 目标方法返回的结果
     * @throws Throwable 执行过程中发生任何异常都会往外抛
     */
    protected Object execute(Object obj, Object[] args) throws Throwable {
        try {
            return targetMethod.invoke(obj, args);
        } catch (InvocationTargetException e) {
            // 抛出原始异常
            throw e.getTargetException();
        }
    }

    /**
     * 获取目标方法入参类型
     */
    public Class[] getParameterTypes() {
        return targetMethod.getParameterTypes();
    }

    /**
     * 获取目标方法返回类型
     */
    public Class getReturnType() {
        return targetMethod.getReturnType();
    }
}
