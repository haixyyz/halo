package org.xujin.halo.test.customer.entity;

import org.xujin.halo.domain.Entity;
import org.xujin.halo.extension.ExtensionExecutor;
import org.xujin.halo.test.customer.CustomerType;
import org.xujin.halo.test.customer.entity.rule.CustomerRuleExtPt;
import org.xujin.halo.test.customer.repository.CustomerRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Customer Entity
 *
 * @author xujin
 * @date 2018-01-07 2:38 AM
 */
@Data
@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class CustomerEntity extends Entity {

    private String companyName;
    private SourceType sourceType;
    private CustomerType customerType;

    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private ExtensionExecutor extensionExecutor;

    public CustomerEntity() {

    }

    public void addNewCustomer() {
        //Add customer policy
        extensionExecutor.execute(CustomerRuleExtPt.class, extension -> extension.addCustomerCheck(this));

        //Persist customer
        customerRepository.persist(this);
    }
}
