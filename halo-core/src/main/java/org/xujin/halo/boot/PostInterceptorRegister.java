
package org.xujin.halo.boot;

import org.xujin.halo.command.CommandHub;
import org.xujin.halo.command.CommandInterceptorI;
import org.xujin.halo.command.PostInterceptor;
import org.xujin.halo.dto.Command;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * PostInterceptorRegister 
 * @author xujin
 */
@Component
public class PostInterceptorRegister extends AbstractRegister implements ApplicationContextAware{

    @Autowired
    private CommandHub commandHub;
    
    @Override
    public void doRegistration(Class<?> targetClz) {
        CommandInterceptorI commandInterceptor = getBean(targetClz);
        PostInterceptor postInterceptorAnn = targetClz.getDeclaredAnnotation(PostInterceptor.class);
        Class<? extends Command>[] supportClasses = postInterceptorAnn.commands();
        registerInterceptor(supportClasses, commandInterceptor);        
    }

    private void registerInterceptor(Class<? extends Command>[] supportClasses, CommandInterceptorI commandInterceptor) {
        if (null == supportClasses || supportClasses.length == 0) {
            commandHub.getGlobalPostInterceptors().add(commandInterceptor);
            order(commandHub.getGlobalPostInterceptors());
            return;
        } 
        for (Class<? extends Command> supportClass : supportClasses) {
            commandHub.getPostInterceptors().put(supportClass, commandInterceptor);
            order(commandHub.getPostInterceptors().get(supportClass));
        }
    }    

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
