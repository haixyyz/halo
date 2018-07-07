package org.xujin.halo.pattern.filter;

/**
 *
 */
public interface Filter {

	void doFilter(Object context, FilterInvoker nextFilter);

}