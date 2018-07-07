package org.xujin.halo.assembler;


/**
 * 适用于消息，查询，RPC等接口的参数装配
 * Assembler Interface
 *
 * @author xujin
 */
public interface AssemblerI<F, T> extends AntiCorruptionI{

    default T assembleParam(F from) {
        return null;
    }

    default void assembleParam(F from, T to) {
    }

    default T assembleResult(F from) {
        return null;
    }

    default void assembleResult(F from, T to) {
    }
}
