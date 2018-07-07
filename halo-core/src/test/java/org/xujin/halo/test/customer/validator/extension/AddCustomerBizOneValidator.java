package org.xujin.halo.test.customer.validator.extension;

import org.xujin.halo.exception.BizException;
import org.xujin.halo.extension.Extension;
import org.xujin.halo.test.customer.AddCustomerCmd;
import org.xujin.halo.test.customer.Constants;
import org.xujin.halo.test.customer.CustomerType;
import org.xujin.halo.test.customer.validator.extensionpoint.AddCustomerValidatorExtPt;

/**
 * AddCustomerBizOneValidator
 *
 * @author xujin
 * @date 2018-01-07 1:31 AM
 */
@Extension(bizCode = Constants.BIZ_1)
public class AddCustomerBizOneValidator implements AddCustomerValidatorExtPt {

    @Override
    public void validate(Object candidate) {
        AddCustomerCmd addCustomerCmd = (AddCustomerCmd) candidate;
        //For BIZ TWO CustomerTYpe could not be VIP
        if(CustomerType.VIP == addCustomerCmd.getCustomerCO().getCustomerType())
            throw new BizException("Customer Type could not be VIP for Biz One");
    }
}
