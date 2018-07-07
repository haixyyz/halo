package org.xujin.halo.extension;

import org.springframework.core.annotation.AliasFor;
import org.xujin.halo.common.CoreConstant;
import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * Extension 
 * @author xujin
 */
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Component
public @interface Extension {

    /**
     * 一级业务code
     * @return
     */
    String bizCode()  default CoreConstant.DEFAULT_BIZ_CODE;

    /**
     * 二级业务code
     * @return
     */
    String tenantId() default CoreConstant.DEFAULT_TENANT_ID;
}
