package org.xujin.halo.extensionpoint;

import org.xujin.halo.dto.Response;

/**
 * 消息消费扩展点
 * @author xujin
 *
 */
public interface MqEventConsumerExtPt {

	Response receive(String msg);
}
