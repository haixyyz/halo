package org.xujin.halo.test.customer.entity.rule;

import org.xujin.halo.extension.ExtensionPointI;
import org.xujin.halo.rule.RuleI;
import org.xujin.halo.test.customer.entity.CustomerEntity;

/**
 * CustomerRuleExtPt
 *
 * @author xujin
 * @date 2018-01-07 12:03 PM
 */
public interface CustomerRuleExtPt extends RuleI, ExtensionPointI {

    //Different business check for different biz
    public boolean addCustomerCheck(CustomerEntity customerEntity);

    //Different upgrade policy for different biz
    default public void customerUpgradePolicy(CustomerEntity customerEntity){
        //Nothing special
    }
}
