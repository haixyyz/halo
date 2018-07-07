package org.xujin.halo.command;

import org.xujin.halo.dto.Command;
import org.xujin.halo.dto.Response;

/**
 * Interceptor will do AOP processing before or after Command Execution
 * 
 * @author xujin
 */
public interface CommandInterceptorI {
   
   /**
    * Pre-processing before command execution
    * @param command
    */
   default public void preIntercept(Command command){};
   
   /**
    * Post-processing after command execution
    * @param command
    * @param response, Note that response could be null, check it before use
    */
   default public void postIntercept(Command command, Response response){};

}
