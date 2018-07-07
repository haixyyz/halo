package org.xujin.halo.boot;

import org.xujin.halo.common.CoreConstant;
import org.xujin.halo.exception.InfraException;
import org.xujin.halo.extension.Extension;
import org.xujin.halo.extension.ExtensionCoordinate;
import org.xujin.halo.extension.ExtensionPointI;
import org.xujin.halo.extension.ExtensionRepository;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * ExtensionRegister 
 * @author xujin
 */
@Component
public class ExtensionRegister implements RegisterI, ApplicationContextAware{

    @Autowired
    private ExtensionRepository extensionRepository;
    
    private ApplicationContext applicationContext;
    
    @Override
    public void doRegistration(Class<?> targetClz) {
        ExtensionPointI extension = (ExtensionPointI) applicationContext.getBean(targetClz);
        Extension extensionAnn = targetClz.getDeclaredAnnotation(Extension.class);
        String extensionPoint = calculateExtensionPoint(targetClz);
        ExtensionCoordinate extensionCoordinate = new ExtensionCoordinate(extensionPoint, extensionAnn.bizCode(), extensionAnn.tenantId());
        ExtensionPointI preVal = extensionRepository.getExtensionRepo().put(extensionCoordinate, extension);
        if (preVal != null) {
            throw new InfraException("Duplicate registration is not allowed for :"+extensionCoordinate);
        }
    }

    /**
     * @param targetClz
     * @return
     */
    private String calculateExtensionPoint(Class<?> targetClz) {
        Class[] interfaces = targetClz.getInterfaces();
        if (ArrayUtils.isEmpty(interfaces))
            throw new InfraException("Please assign a extension point interface for "+targetClz);
        for (Class intf : interfaces) {
            String extensionPoint = intf.getSimpleName();
            if (StringUtils.contains(extensionPoint, CoreConstant.EXTENSION_EXTPT_NAMING))
                return extensionPoint;
        }
        throw new InfraException("Your name of ExtensionPoint for "+targetClz+" is not valid, must be end of "+CoreConstant.EXTENSION_EXTPT_NAMING);
    }



    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}