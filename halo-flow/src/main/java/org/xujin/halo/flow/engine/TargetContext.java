package org.xujin.halo.flow.engine;

import org.springframework.util.Assert;

import java.util.HashMap;
import java.util.Map;

/**
 * 目标上下文
 */
public class TargetContext<T> {
    // 目标对象
    private T target;
    // 附件（一般存的是target不包含的信息，但在流程执行中又需要用到的信息）
    private Map<Object, Object> attachment;

    public TargetContext(T target, Map<Object, Object> attachment) {
        Assert.notNull(target, "目标对象不能为null");
        this.target = target;
        this.attachment = attachment;
        if (this.attachment == null) {
            this.attachment = new HashMap<>();
        }
    }

    /**
     * 获取目标对象
     */
    public T getTarget() {
        return target;
    }

    /**
     * 刷新目标对象
     *
     * @param target 目标对象（会替换掉目标上下文中原有的目标对象）
     */
    public void refreshTarget(T target) {
        Assert.notNull(target, "目标对象不能为null");
        this.target = target;
    }

    /**
     * 获取附件属性
     */
    public <V> V getAttachmentAttr(Object key) {
        return (V) attachment.get(key);
    }

    /**
     * 设置附件属性
     */
    public void setAttachmentAttr(Object key, Object value) {
        attachment.put(key, value);
    }
}
