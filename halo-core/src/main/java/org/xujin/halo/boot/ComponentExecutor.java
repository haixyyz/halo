package org.xujin.halo.boot;

import java.util.function.Function;

/**
 * @author xujin
 * @date 2018/4/21
 */
public abstract class ComponentExecutor {

    public <R, C> R execute(Class<C> targetClz, Function<C, R> exeFunction) {
        C component = locateComponent(targetClz);
        return exeFunction.apply(component);
    }

    protected abstract <C> C locateComponent(Class<C> targetClz);
}
