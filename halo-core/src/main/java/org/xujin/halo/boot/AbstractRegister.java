package org.xujin.halo.boot;

import org.xujin.halo.exception.InfraException;
import org.springframework.context.ApplicationContext;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

import java.util.List;

/**
 * @author xujin
 * @date 2018/02/13
 */
public abstract class AbstractRegister implements RegisterI{

    protected ApplicationContext applicationContext;

    protected<T> T getBean(Class targetClz){
        T executorI = null;
        //优先按type查
        try {
            executorI = (T) applicationContext.getBean(targetClz);
        }catch (Exception e){
        }
        //按name查
        if(executorI == null){
            String simpleName = targetClz.getSimpleName();
            //首字母小写
            simpleName = Character.toLowerCase(simpleName.charAt(0)) + simpleName.substring(1);
            executorI = (T) applicationContext.getBean(simpleName);
        }
        if(executorI == null){
            new InfraException("Command " + targetClz + " init error!");
        }
        return executorI;
    }

    /**
     * 根据Order注解排序
     * @param interceptorIList
     */
    protected <T> void order(List<T> interceptorIList){
        if(interceptorIList == null || interceptorIList.size() <= 1){
            return;
        }
        T newInterceptor = interceptorIList.get(interceptorIList.size() - 1);
        Order order = newInterceptor.getClass().getDeclaredAnnotation(Order.class);
        if(order == null){
            return;
        }
        int index = interceptorIList.size() - 1;
        for(int i = interceptorIList.size() - 2; i >= 0; i--){
            int itemOrderInt = Ordered.LOWEST_PRECEDENCE;
            Order itemOrder = interceptorIList.get(i).getClass().getDeclaredAnnotation(Order.class);
            if(itemOrder != null){
                itemOrderInt = itemOrder.value();
            }
            if(itemOrderInt > order.value()){
                interceptorIList.set(index, interceptorIList.get(i));
                index = i;
            }else {
                break;
            }
        }
        if(index < interceptorIList.size() - 1){
            interceptorIList.set(index, newInterceptor);
        }
    }
}
