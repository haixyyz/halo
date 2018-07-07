package org.xujin.halo.event;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * 事件处理器
 * @author xujin
 * @date 2018/5/20
 */
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Component
public @interface EventHandler {
}
