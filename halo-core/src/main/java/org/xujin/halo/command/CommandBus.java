package org.xujin.halo.command;

import org.xujin.halo.exception.BasicErrorCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import org.xujin.halo.context.TenantContext;
import org.xujin.halo.dto.Command;
import org.xujin.halo.dto.Response;
import org.xujin.halo.exception.HaloException;
import org.xujin.halo.exception.ErrorCodeI;
import org.xujin.halo.exception.InfraException;
import org.xujin.halo.logger.Logger;
import org.xujin.halo.logger.LoggerFactory;

/**
 * Just send Command to CommandBus, 
 * 
 * @author xujin 2017年10月24日 上午12:47:18
 */
@Component
public class CommandBus implements CommandBusI{
    
    Logger logger = LoggerFactory.getLogger(CommandBus.class);
    
    @Autowired
    private CommandHub commandHub;

    @SuppressWarnings("unchecked")
    @Override
    public Response send(Command cmd) {
        Response response = null;
        try {
            //从commandHub中获取到对应的命令去调用命令的execute方法
            response = commandHub.getCommandInvocation(cmd.getClass()).invoke(cmd);
        }
        catch (Exception exception) {
            //统一的Command异常处理器
            response = handleException(cmd, response, exception);
        }
        finally {
            //Clean up context
            TenantContext.remove();
        }
        return response;
    }

    private Response handleException(Command cmd, Response response, Exception exception) {
        logger.error(exception.getMessage(), exception);
        Class responseClz = commandHub.getResponseRepository().get(cmd.getClass());
        try {
            response = (Response) responseClz.newInstance();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new InfraException(e.getMessage());
        }
        if (exception instanceof HaloException) {
            ErrorCodeI errCode = ((HaloException) exception).getErrCode();
            response.setErrCode(errCode.getErrCode());
        }
        else {
            response.setErrCode(BasicErrorCode.SYS_ERROR.getErrCode());
        }
        response.setErrMessage(exception.getMessage());
        return response;
    }
}
