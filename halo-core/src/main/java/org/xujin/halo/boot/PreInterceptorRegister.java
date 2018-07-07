
package org.xujin.halo.boot;

import org.xujin.halo.command.CommandHub;
import org.xujin.halo.command.CommandInterceptorI;
import org.xujin.halo.command.PreInterceptor;
import org.xujin.halo.dto.Command;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * PreInterceptorRegister 
 * @author xujin
 */
@Component
public class PreInterceptorRegister extends AbstractRegister implements ApplicationContextAware{

    @Autowired
    private CommandHub commandHub;
    
    @Override
    public void doRegistration(Class<?> targetClz) {
        CommandInterceptorI commandInterceptor = getBean(targetClz);
        PreInterceptor preInterceptorAnn = targetClz.getDeclaredAnnotation(PreInterceptor.class);
        Class<? extends Command>[] supportClasses = preInterceptorAnn.commands();
        registerInterceptor(supportClasses, commandInterceptor);        
    }

    private void registerInterceptor(Class<? extends Command>[] supportClasses, CommandInterceptorI commandInterceptor) {
        if (null == supportClasses || supportClasses.length == 0) {
            commandHub.getGlobalPreInterceptors().add(commandInterceptor);
            order(commandHub.getGlobalPreInterceptors());
            return;
        } 
        for (Class<? extends Command> supportClass : supportClasses) {
            commandHub.getPreInterceptors().put(supportClass, commandInterceptor);
            order(commandHub.getPreInterceptors().get(supportClass));
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
