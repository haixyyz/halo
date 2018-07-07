package org.xujin.halo.test.customer.repository;

import org.xujin.halo.repository.RepositoryI;
import org.xujin.halo.test.customer.entity.CustomerEntity;
import org.springframework.stereotype.Repository;

/**
 * CustomerRepository
 *
 * @author xujin
 * @date 2018-01-07 11:59 AM
 */
@Repository
public class CustomerRepository implements RepositoryI {

    public void persist(CustomerEntity customerEntity){
        System.out.println("Persist customer to DB : "+ customerEntity);
    }
}
