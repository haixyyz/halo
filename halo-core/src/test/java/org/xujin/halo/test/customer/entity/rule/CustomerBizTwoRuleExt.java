package org.xujin.halo.test.customer.entity.rule;

import org.xujin.halo.extension.Extension;
import org.xujin.halo.test.customer.Constants;
import org.xujin.halo.test.customer.entity.CustomerEntity;

/**
 * CustomerBizTwoRuleExt
 *
 * @author xujin
 * @date 2018-01-07 12:10 PM
 */
@Extension(bizCode = Constants.BIZ_2)
public class CustomerBizTwoRuleExt implements CustomerRuleExtPt{

    @Override
    public boolean addCustomerCheck(CustomerEntity customerEntity) {
        //Any Customer can be added
        return true;
    }
}
