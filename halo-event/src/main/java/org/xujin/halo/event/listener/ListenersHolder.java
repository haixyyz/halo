package org.xujin.halo.event.listener;

import org.xujin.halo.event.annotation.listener.Listener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.util.ClassUtils;

import javax.annotation.PostConstruct;
import java.util.*;

/**
 * 监听器持有器
 */
@Component
public class ListenersHolder {
    @Autowired
    private ApplicationContext applicationContext;
    // 监听器执行器Map（key：监听器的类型）
    private Map<Class, List<ListenerExecutor>> listenerExecutorsMap = new HashMap<>();

    // 初始化（查询spring容器中所有的@Listener监听器并解析，spring自动执行）
    @PostConstruct
    public void init() {
        String[] beanNames = applicationContext.getBeanNamesForAnnotation(Listener.class);
        for (String beanName : beanNames) {
            // 解析监听器
            ListenerExecutor listenerExecutor = ListenerParser.parseListener(applicationContext.getBean(beanName));
            // 将执行器放入持有器中
            List<ListenerExecutor> listenerExecutors = listenerExecutorsMap.get(listenerExecutor.getType());
            if (listenerExecutors == null) {
                listenerExecutors = new ArrayList<>();
                listenerExecutorsMap.put(listenerExecutor.getType(), listenerExecutors);
            }
            listenerExecutors.add(listenerExecutor);
        }
    }

    /**
     * 获取所有的监听器类型
     */
    public Set<Class> getTypes() {
        return listenerExecutorsMap.keySet();
    }

    /**
     * 获取指定类型的监听器执行器
     *
     * @param type 监听器类型
     * @throws IllegalArgumentException 如果不存在该类型的监听器执行器
     */
    public List<ListenerExecutor> getRequiredListenerExecutors(Class type) {
        if (!listenerExecutorsMap.containsKey(type)) {
            throw new IllegalArgumentException("不存在" + ClassUtils.getShortName(type) + "类型的监听器");
        }
        return listenerExecutorsMap.get(type);
    }
}
