package org.xujin.halo.dto.event;

/**
 *
 * @author xujin
 * @date 2018/05/17
 */
public abstract class MqEvent extends Event {

    public abstract String getTopic();

}
