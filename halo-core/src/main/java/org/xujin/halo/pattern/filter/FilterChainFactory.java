package org.xujin.halo.pattern.filter;

import org.xujin.halo.common.ApplicationContextHelper;

/**
 * 责任链模式工厂
 * @author xujin
 * @date 2018/04/17
 */
public class FilterChainFactory {

    public static FilterChain buildFilterChain(Class... filterClsList) {
        FilterInvoker last = new FilterInvoker(){};
        FilterChain filterChain = new FilterChain();
        for(int i = filterClsList.length - 1; i >= 0; i--){
            FilterInvoker next = last;
            Filter filter = (Filter)ApplicationContextHelper.getBean(filterClsList[i]);
            last = new FilterInvoker(){
                @Override
                public void invoke(Object context) {
                    filter.doFilter(context, next);
                }
            };
        }
        filterChain.setHeader(last);
        return filterChain;
    }


}
