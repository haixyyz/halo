package org.xujin.halo.test;

import org.xujin.halo.TestConfig;
import org.xujin.halo.common.ApplicationContextHelper;
import org.xujin.halo.test.customer.entity.CustomerEntity;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * EntityPrototypeTest
 *
 * @author xujin
 * @date 2018-01-07 12:34 PM
 */

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {TestConfig.class})
public class EntityPrototypeTest {

    @Test
    public void testPrototype(){
        CustomerEntity customerEntity1 = (CustomerEntity)ApplicationContextHelper.getBean(CustomerEntity.class);
        System.out.println(customerEntity1);
        CustomerEntity customerEntity2 = (CustomerEntity)ApplicationContextHelper.getBean(CustomerEntity.class);
        System.out.println(customerEntity2);

        Assert.assertEquals(customerEntity1, customerEntity2);
        Assert.assertFalse(customerEntity1 == customerEntity2); //It should be different objects
    }
}
