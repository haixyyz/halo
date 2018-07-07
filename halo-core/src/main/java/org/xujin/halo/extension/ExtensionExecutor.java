package org.xujin.halo.extension;

import org.xujin.halo.boot.ComponentExecutor;
import org.xujin.halo.common.CoreConstant;
import org.xujin.halo.common.DefaultBizCode;
import org.xujin.halo.context.TenantContext;
import org.xujin.halo.exception.InfraException;
import org.xujin.halo.logger.Logger;
import org.xujin.halo.logger.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 扩展点执行器-ExtensionExecutor
 * @author xujin
 */
@Component
public class ExtensionExecutor extends ComponentExecutor{

    private Logger logger = LoggerFactory.getLogger(ExtensionExecutor.class);

    @Autowired
    private ExtensionRepository extensionRepository;

    @Override
    protected <C> C locateComponent(Class<C> targetClz) {
        C extension = locateExtension(targetClz);
        logger.debug("[Located Extension]: "+extension.getClass().getSimpleName());
        return locateExtension(targetClz);
    }

    /**
     * @param targetClz
     */
    @SuppressWarnings("unchecked")
    protected <Ext> Ext locateExtension(Class<Ext> targetClz) {
        String bizCode = TenantContext.getBizCode();
        String tenantId = TenantContext.getTenantId();
        ExtensionCoordinate extensionCoordinate = new ExtensionCoordinate(targetClz.getSimpleName(), bizCode, tenantId);
        /**
         * 1.First search key is: extensionPoint + bizCode + tenantId
         */
        Ext extension = (Ext)extensionRepository.getExtensionRepo().get(extensionCoordinate);
        if (extension != null) {
            return extension;
        }
        /**
         * 2.Second search key is: extensionPoint + bizCode
         */        
        extensionCoordinate.setTenantId(CoreConstant.DEFAULT_TENANT_ID);
        extension = (Ext)extensionRepository.getExtensionRepo().get(extensionCoordinate);
        if (extension != null) {
            return extension;
        }  
        /**
         * 3.Third search key is: extensionPoint
         */
        extensionCoordinate.setBizCode(DefaultBizCode.DEFAULT_BIZ_CODE);
        extension = (Ext)extensionRepository.getExtensionRepo().get(extensionCoordinate);
        if (extension != null) {
            return extension;
        }          
        throw new InfraException("Can not find extension for ExtensionPoint: "+targetClz);
    }
}
