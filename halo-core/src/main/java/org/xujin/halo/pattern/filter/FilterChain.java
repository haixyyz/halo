package org.xujin.halo.pattern.filter;

/**
 * 拦截器链
 * @author xujin
 * @date 2018/04/17
 */
public class FilterChain{

    private FilterInvoker header;

    public void doFilter(Object context){
        header.invoke(context);
    }

    public void setHeader(FilterInvoker header) {
        this.header = header;
    }
}
