package org.xujin.halo.command;

import org.xujin.halo.dto.Command;
import org.xujin.halo.dto.Response;

/**
 * 
 * CommandBus
 * 
 * @author xujin
 */
public interface CommandBusI {

    /**
     * Send command to CommandBus, then the command will be executed by CommandExecutor
     * 
     * @param Command or Query
     * @return Response
     */
    public Response send(Command cmd);
}
