package org.xujin.halo.validator;

import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * Plain Validator is Validator Component without ExtensionPoint
 * @author xujin
 * @date 2018/6/21
 */
@Component
public class PlainValidatorRepository {
    @Getter
    private Map<Class<? extends ValidatorI>, ValidatorI> plainValidators = new HashMap<>();
}
