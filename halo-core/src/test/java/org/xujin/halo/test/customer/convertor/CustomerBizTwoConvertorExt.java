package org.xujin.halo.test.customer.convertor;

import org.xujin.halo.extension.Extension;
import org.xujin.halo.test.customer.Constants;
import org.xujin.halo.test.customer.CustomerCO;
import org.xujin.halo.test.customer.entity.CustomerEntity;
import org.xujin.halo.test.customer.entity.SourceType;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * CustomerBizTwoConvertorExt
 *
 * @author xujin
 * @date 2018-01-07 3:05 AM
 */
@Extension(bizCode = Constants.BIZ_2)
public class CustomerBizTwoConvertorExt implements CustomerConvertorExtPt{

    @Autowired
    private CustomerConvertor customerConvertor;//Composite basic convertor to do basic conversion

    @Override
    public CustomerEntity clientToEntity(CustomerCO customerCO){
        CustomerEntity customerEntity = customerConvertor.clientToEntity(customerCO);
        //In this business, if customers from RFQ and Advertisement are both regarded as Advertisement
        if(Constants.SOURCE_AD.equals(customerCO.getSource()) || Constants.SOURCE_RFQ.equals(customerCO.getSource()))
        {
            customerEntity.setSourceType(SourceType.AD);
        }
        return customerEntity;
    }

}
