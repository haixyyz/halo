package org.xujin.halo.test.customer.entity.rule;

import org.xujin.halo.exception.BizException;
import org.xujin.halo.extension.Extension;
import org.xujin.halo.test.customer.Constants;
import org.xujin.halo.test.customer.entity.CustomerEntity;
import org.xujin.halo.test.customer.entity.SourceType;

/**
 * CustomerBizOneRuleExt
 *
 * @author xujin
 * @date 2018-01-07 12:10 PM
 */
@Extension(bizCode = Constants.BIZ_1)
public class CustomerBizOneRuleExt implements CustomerRuleExtPt{

    @Override
    public boolean addCustomerCheck(CustomerEntity customerEntity) {
        if(SourceType.AD == customerEntity.getSourceType()){
            throw new BizException("Sorry, Customer from advertisement can not be added in this period");
        }
        return true;
    }
}
