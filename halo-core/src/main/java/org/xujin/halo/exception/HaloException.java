package org.xujin.halo.exception;

/**
 * 
 * Halo框架抽象异常
 * 
 * @author
 */
public abstract class HaloException extends RuntimeException{

    private static final long serialVersionUID = 1L;
    
    private ErrorCodeI errCode;
    
    public HaloException(String errMessage){
        super(errMessage);
    }
    
    public HaloException(String errMessage, Throwable e) {
        super(errMessage, e);
    }
    
    public ErrorCodeI getErrCode() {
        return errCode;
    }
    
    public void setErrCode(ErrorCodeI errCode) {
        this.errCode = errCode;
    }
    
}
