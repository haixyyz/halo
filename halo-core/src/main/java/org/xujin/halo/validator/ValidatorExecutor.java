
package org.xujin.halo.validator;

import org.xujin.halo.extension.ExtensionExecutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * ValidatorExecutor
 * 
 * @author xujin 2017-11-04
 */
@Component
public class ValidatorExecutor extends ExtensionExecutor {

    @Autowired
    private PlainValidatorRepository plainValidatorRepository;

    public void validate(Class<? extends ValidatorI> targetClz, Object candidate) {
        ValidatorI validator = locateComponent(targetClz);
        validator.validate(candidate);
    }

    @Override
    protected <C> C locateComponent(Class<C> targetClz) {
        C validator = (C) plainValidatorRepository.getPlainValidators().get(targetClz);
        return null != validator ? validator : super.locateComponent(targetClz);
    }

}