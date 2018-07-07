package org.xujin.halo.flow;

import java.util.Map;

/**
 * 流程引擎
 */
public interface FlowEngine {

    /**
     * 执行流程
     *
     * @param flow   流程名称
     * @param target 目标对象
     * @return 流程执行结束后的目标对象（可能和传入的目标对象不是同一个对象）
     */
    <T> T start(String flow, T target);

    /**
     * 执行流程
     *
     * @param flow       流程名称
     * @param target     目标对象
     * @param attachment 附件（为null的话则会自动生成一个空Map作为附件）
     * @return 流程执行结束后的目标对象（可能和传入的目标对象不是同一个对象）
     */
    <T> T start(String flow, T target, Map<Object, Object> attachment);

    /**
     * 以新事务插入目标对象到数据库并提交事务
     *
     * @param flow       流程名称
     * @param target     目标对象
     * @param attachment 附件（为null的话则会自动生成一个空Map作为附件）
     * @return 插入到数据库后的目标对象（可能和传入的目标对象不是同一个对象）
     */
    <T> T insertTarget(String flow, T target, Map<Object, Object> attachment);

    /**
     * 以新事务插入目标对象到数据库并提交事务，然后执行流程
     *
     * @param flow       流程名称
     * @param target     目标对象
     * @param attachment 附件（为null的话则会自动生成一个空Map作为附件）
     * @return 流程执行结束后的目标对象（可能和传入的目标对象不是同一个对象）
     */
    <T> T insertTargetAndStart(String flow, T target, Map<Object, Object> attachment);

}
