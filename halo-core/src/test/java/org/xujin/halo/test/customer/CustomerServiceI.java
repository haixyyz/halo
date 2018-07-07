package org.xujin.halo.test.customer;

import org.xujin.halo.dto.Response;
import org.xujin.halo.dto.SingleResponse;

/**
 * CustomerServiceI
 *
 * @author xujin 2018-01-06 7:24 PM
 */
public interface CustomerServiceI {
    public Response addCustomer(AddCustomerCmd addCustomerCmd);
    public SingleResponse<CustomerCO> getCustomer(GetOneCustomerQry getOneCustomerQry);
}
