package org.xujin.halo.exception;

/**
 * 
 * Infrastructure Exception
 * 基础设施层异常
 * @author xujin
 */
public class InfraException extends HaloException {
    
    private static final long serialVersionUID = 1L;
    
    public InfraException(String errMessage){
        super(errMessage);
        this.setErrCode(BasicErrorCode.INFRA_ERROR);
    }
    
    public InfraException(String errMessage, Throwable e) {
        super(errMessage, e);
        this.setErrCode(BasicErrorCode.INFRA_ERROR);
    }
}