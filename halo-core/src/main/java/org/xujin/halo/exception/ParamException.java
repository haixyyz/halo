package org.xujin.halo.exception;

/**
 * 参数异常
 */
public class ParamException extends HaloException {
    
    private static final long serialVersionUID = 1L;
    
    public ParamException(String errMessage){
        super(errMessage);
        this.setErrCode(BasicErrorCode.PARAM_ERROR);
    }

    public ParamException(ErrorCodeI errCode, String errMessage){
        super(errMessage);
        this.setErrCode(errCode);
    }

    public ParamException(String errMessage, Throwable e) {
        super(errMessage, e);
        this.setErrCode(BasicErrorCode.PARAM_ERROR);
    }
}
