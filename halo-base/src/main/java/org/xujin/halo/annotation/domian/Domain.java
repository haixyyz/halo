package org.xujin.halo.annotation.domian;

import java.lang.annotation.*;

/**
 * 域描述注解
 * @author xujin
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Inherited
public @interface Domain {

    /**
     * 当前域的唯一code
     * @return
     */
    String code();

    /**
     * 父域的唯一code
     * @return
     */
    String parentCode() default "";

    /**
     * 当前域的名称
     * @return
     */
    String name();

    /**
     * 当前域的描述
     * @return
     */
    String desc() default "";

}
