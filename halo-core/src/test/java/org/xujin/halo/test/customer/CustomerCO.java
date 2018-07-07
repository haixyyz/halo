package org.xujin.halo.test.customer;

import org.xujin.halo.dto.ClientObject;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * CustomerCO
 *
 * @author xujin 2018-01-06 7:30 PM
 */
@Data
public class CustomerCO extends ClientObject{

    @NotEmpty
    private String companyName;
    @NotEmpty
    private String source;  //advertisement, p4p, RFQ, ATM
    private CustomerType customerType; //potential, intentional, important, vip
}
