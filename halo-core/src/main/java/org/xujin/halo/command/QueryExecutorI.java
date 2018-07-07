package org.xujin.halo.command;

import org.xujin.halo.dto.Command;
import org.xujin.halo.dto.Response;

public interface QueryExecutorI<R extends Response, C extends Command> extends CommandExecutorI<R,C>{

}
