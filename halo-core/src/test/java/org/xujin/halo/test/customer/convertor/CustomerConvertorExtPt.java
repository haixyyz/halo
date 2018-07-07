package org.xujin.halo.test.customer.convertor;

import org.xujin.halo.convertor.ConvertorI;
import org.xujin.halo.extension.ExtensionPointI;
import org.xujin.halo.test.customer.CustomerCO;
import org.xujin.halo.test.customer.entity.CustomerEntity;

/**
 * CustomerConvertorExtPt
 *
 * @author xujin
 * @date 2018-01-07 2:37 AM
 */
public interface CustomerConvertorExtPt extends ConvertorI, ExtensionPointI {

    public CustomerEntity clientToEntity(CustomerCO customerCO);
}
