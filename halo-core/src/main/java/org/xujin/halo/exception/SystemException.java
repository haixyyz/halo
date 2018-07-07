package org.xujin.halo.exception;

/**
 * 系统异常
 *
 * @author xujin
 */
public class SystemException extends HaloException {

    private static final long serialVersionUID = 4355163994767354840L;

    public SystemException(String errMessage) {
        super(errMessage);
    }

    public SystemException(ErrorCodeI errCode, String errMessage) {
        super(errMessage);
        this.setErrCode(errCode);
    }

    public SystemException(String errMessage, ErrorCodeI errorCode, Throwable e) {
        super(errMessage, e);
        this.setErrCode(errorCode);
    }
}
