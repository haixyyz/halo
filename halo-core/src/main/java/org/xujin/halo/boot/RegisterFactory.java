
package org.xujin.halo.boot;

import org.xujin.halo.command.Command;
import org.xujin.halo.command.PostInterceptor;
import org.xujin.halo.command.PreInterceptor;
import org.xujin.halo.common.CoreConstant;
import org.xujin.halo.event.EventHandler;
import org.xujin.halo.exception.InfraException;
import org.xujin.halo.extension.Extension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * RegisterFactory
 *
 * @author xujin
 */
@Component
public class RegisterFactory{

    @Autowired
    private PreInterceptorRegister preInterceptorRegister;
    @Autowired
    private PostInterceptorRegister postInterceptorRegister;
    @Autowired
    private CommandRegister commandRegister;
    @Autowired
    private ExtensionRegister extensionRegister;
    @Autowired
    private EventRegister eventRegister;
    @Autowired
    private PlainValidatorRegister plainValidatorRegister;
    @Autowired
    private PlainRuleRegister plainRuleRegister;

    public RegisterI getRegister(Class<?> targetClz) {
        PreInterceptor preInterceptorAnn = targetClz.getDeclaredAnnotation(PreInterceptor.class);
        if (preInterceptorAnn != null) {
            return preInterceptorRegister;
        }
        PostInterceptor postInterceptorAnn = targetClz.getDeclaredAnnotation(PostInterceptor.class);
        if (postInterceptorAnn != null) {
            return postInterceptorRegister;
        }
        Command commandAnn = targetClz.getDeclaredAnnotation(Command.class);
        if (commandAnn != null) {
            return commandRegister;
        }
        Extension extensionAnn = targetClz.getDeclaredAnnotation(Extension.class);
        if (extensionAnn != null) {
            return extensionRegister;
        }
        if (isPlainValidator(targetClz)) {
            return plainValidatorRegister;
        }
        if (isPlainRule(targetClz)) {
            return plainRuleRegister;
        }
        EventHandler eventHandlerAnn = targetClz.getDeclaredAnnotation(EventHandler.class);
        if (eventHandlerAnn != null) {
            return eventRegister;
        }
        return null;
    }

    private boolean isPlainRule(Class<?> targetClz) {
        if (ClassInterfaceChecker.check(targetClz, CoreConstant.RULEI_CLASS) && makeSureItsNotExtensionPoint(targetClz)) {
            return true;
        }
        return false;
    }

    private boolean isPlainValidator(Class<?> targetClz) {
        if (ClassInterfaceChecker.check(targetClz, CoreConstant.VALIDATORI_CLASS) && makeSureItsNotExtensionPoint(targetClz)) {
            return true;
        }
        return false;
    }

    private boolean makeSureItsNotExtensionPoint(Class<?> targetClz) {
        if (ClassInterfaceChecker.check(targetClz, CoreConstant.EXTENSIONPOINT_CLASS)) {
            throw new InfraException(
                "Please add @Extension for " + targetClz.getSimpleName() + " since it's a ExtensionPoint");
        }
        return true;
    }
}
