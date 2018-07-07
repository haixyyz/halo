package org.xujin.halo.test.customer;


import org.xujin.halo.dto.Command;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * AddCustomerCmd
 *
 * @author xujin 2018-01-06 7:28 PM
 */
@Data
public class AddCustomerCmd extends Command {

    @NotNull
    @Valid
    private CustomerCO customerCO;
}
