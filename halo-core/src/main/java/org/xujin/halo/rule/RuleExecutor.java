package org.xujin.halo.rule;

import org.xujin.halo.extension.ExtensionExecutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * RuleExecutor
 *
 * Note that Rule is extensible as long as @Extension is added
 *
 * @author xujin 2017-11-04
 */
@Component
public class RuleExecutor extends ExtensionExecutor {

    @Autowired
    private PlainRuleRepository plainRuleRepository;

    @Override
    protected <C> C locateComponent(Class<C> targetClz) {
        C rule = (C) plainRuleRepository.getPlainRules().get(targetClz);
        return null != rule ? rule : super.locateComponent(targetClz);
    }

    public void validate(Class<? extends RuleI> targetClz, Object... candidate) {
        RuleI rule = this.locateComponent(targetClz);
        rule.validate(candidate);
    }
}
