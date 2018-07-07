package org.xujin.halo.exception;

/**
 * Extends your error codes in your App by implements this Interface.
 *
 * Created by xujin on 2018/4/18.
 */
public interface ErrorCodeI {

    public String getErrCode();

    public String getErrDesc();

    public boolean isRetriable();
}
