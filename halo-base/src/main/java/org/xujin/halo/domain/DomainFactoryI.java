package org.xujin.halo.domain;

/**
 * 领域工厂
 * @author xujin
 *
 */
public interface DomainFactoryI<T extends Entity> {

	T create();

}
