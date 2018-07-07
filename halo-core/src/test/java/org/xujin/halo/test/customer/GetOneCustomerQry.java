package org.xujin.halo.test.customer;

import org.xujin.halo.dto.Query;
import lombok.Data;

/**
 * GetOneCustomerQry
 *
 * @author xujin 2018-01-06 7:38 PM
 */
@Data
public class GetOneCustomerQry extends Query{
    private long customerId;
    private String companyName;
}
