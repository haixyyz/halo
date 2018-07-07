package org.xujin.halo.test.customer.validator.extension;

import org.xujin.halo.exception.ParamException;
import org.xujin.halo.extension.Extension;
import org.xujin.halo.test.customer.AddCustomerCmd;
import org.xujin.halo.test.customer.Constants;
import org.xujin.halo.test.customer.validator.extensionpoint.AddCustomerValidatorExtPt;

/**
 * AddCustomerBizTwoValidator
 *
 * @author xujin
 * @date 2018-01-07 1:31 AM
 */
@Extension(bizCode = Constants.BIZ_2)
public class AddCustomerBizTwoValidator implements AddCustomerValidatorExtPt {

    @Override
    public void validate(Object candidate) {
        AddCustomerCmd addCustomerCmd = (AddCustomerCmd) candidate;
        //For BIZ TWO CustomerTYpe could not be null
        if (addCustomerCmd.getCustomerCO().getCustomerType() == null)
            throw new ParamException("CustomerType could not be null");
    }
}
