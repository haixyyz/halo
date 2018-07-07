package org.xujin.halo.exception;

/**
 * Preconditions 先决条件
 * @author xujin
 */
public class Preconditions {

    public static void checkArgument(boolean expression) {
        if (!expression) {
            throw new ParamException("");
        }
    }

    public static void checkArgument(boolean expression, Object errorMessage) {
        if (!expression) {
            throw new ParamException(String.valueOf(errorMessage));
        }
    }

    public static void checkState(boolean expression) {
        if (!expression) {
            throw new BizException("");
        }
    }

    public static void checkState(boolean expression, Object errorMessage) {
        if (!expression) {
            throw new BizException(String.valueOf(errorMessage));
        }
    }
}
