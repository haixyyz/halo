package org.xujin.halo.annotation.domian;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * 领域能力，用来描述领域实体的行为
 * @author xujin
 *
 */
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@Component
public @interface DomainAbility {

}
