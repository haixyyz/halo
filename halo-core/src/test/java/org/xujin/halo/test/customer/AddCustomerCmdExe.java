package org.xujin.halo.test.customer;

import org.xujin.halo.command.Command;
import org.xujin.halo.command.CommandExecutorI;
import org.xujin.halo.dto.Response;
import org.xujin.halo.extension.ExtensionExecutor;
import org.xujin.halo.logger.Logger;
import org.xujin.halo.logger.LoggerFactory;
import org.xujin.halo.test.customer.convertor.CustomerConvertorExtPt;
import org.xujin.halo.test.customer.entity.CustomerEntity;
import org.xujin.halo.test.customer.validator.extensionpoint.AddCustomerValidatorExtPt;
import org.xujin.halo.validator.ValidatorExecutor;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * AddCustomerCmdExe
 *
 * @author xujin 2018-01-06 7:48 PM
 */
@Command
public class AddCustomerCmdExe implements CommandExecutorI<Response, AddCustomerCmd> {

    private Logger logger = LoggerFactory.getLogger(AddCustomerCmd.class);

    @Autowired
    private ValidatorExecutor validatorExecutor;

    @Autowired
    private ExtensionExecutor extensionExecutor;


    @Override
    public Response execute(AddCustomerCmd cmd) {
        logger.info("Start processing command:" + cmd);
        validatorExecutor.validate(AddCustomerValidatorExtPt.class, cmd);

        //Convert CO to Entity
        CustomerEntity customerEntity = extensionExecutor.execute(CustomerConvertorExtPt.class, extension -> extension.clientToEntity(cmd.getCustomerCO()));

        //Call Domain Entity for business logic processing
        logger.info("Call Domain Entity for business logic processing..."+customerEntity);
        customerEntity.addNewCustomer();

        logger.info("End processing command:" + cmd);
        return Response.buildSuccess();
    }
}
