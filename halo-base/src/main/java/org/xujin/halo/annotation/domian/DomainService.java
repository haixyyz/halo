package org.xujin.halo.annotation.domian;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface DomainService {

    /**
     * 当前域的唯一编码
     * @return
     */
    String code();

    /**
     * 域服务的名称用于显示
     * @return
     */
    String name();

    /**
     * 当前域的描述
     * @return
     */
    String desc() default "";

    /**
     * 当前域服务的帮助url
     * @return
     */
    String helpUrl() default "";

    /**
     *  the domain ability codes
     * @return
     */
    String[] abilityCodes() default "";


}
