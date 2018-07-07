package org.xujin.halo.command;

import org.xujin.halo.dto.Command;
import org.xujin.halo.dto.Response;

/**
 * 
 * CommandExecutorI
 * 
 * @author xujin
 */
public interface CommandExecutorI<R extends Response, C extends Command> {

    public R execute(C cmd);
}
