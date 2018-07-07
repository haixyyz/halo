package org.xujin.halo.test.customer.convertor;

import org.xujin.halo.common.ApplicationContextHelper;
import org.xujin.halo.convertor.ConvertorI;
import org.xujin.halo.test.customer.CustomerCO;
import org.xujin.halo.test.customer.entity.CustomerEntity;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * CustomerConvertor
 *
 * @author xujin
 * @date 2018-01-07 3:08 AM
 */
@Component
public class CustomerConvertor implements ConvertorI {

    public CustomerEntity clientToEntity(Object clientObject){
        CustomerCO customerCO = (CustomerCO)clientObject;
        CustomerEntity customerEntity = (CustomerEntity)ApplicationContextHelper.getBean(CustomerEntity.class);
        customerEntity.setCompanyName(customerCO.getCompanyName());
        customerEntity.setCustomerType(customerCO.getCustomerType());
        return customerEntity;
    }
}
