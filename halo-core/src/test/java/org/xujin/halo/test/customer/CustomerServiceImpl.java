package org.xujin.halo.test.customer;

import org.xujin.halo.command.CommandBusI;
import org.xujin.halo.dto.Response;
import org.xujin.halo.dto.SingleResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * CustomerServiceImpl
 *
 * @author xujin 2018-01-06 7:40 PM
 */
@Service
public class CustomerServiceImpl implements CustomerServiceI{

    @Autowired
    private CommandBusI commandBus;

    @Override
    public Response addCustomer(AddCustomerCmd addCustomerCmd) {
        return (Response)commandBus.send(addCustomerCmd);
    }

    @Override
    public SingleResponse<CustomerCO> getCustomer(GetOneCustomerQry getOneCustomerQry) {
        return null;
    }
}
