package org.xujin.halo.command;

import org.xujin.halo.exception.InfraException;
import com.google.common.collect.LinkedListMultimap;
import com.google.common.collect.ListMultimap;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Command Hub holds all the important information about Command
 * 
 * @author xujin
 */
@SuppressWarnings("rawtypes")
@Component
public class CommandHub{

    @Getter
    @Setter
    private ListMultimap<Class/*CommandClz*/, CommandInterceptorI> preInterceptors = LinkedListMultimap.create();
    @Getter
    @Setter
    private ListMultimap<Class/*CommandClz*/, CommandInterceptorI> postInterceptors = LinkedListMultimap.create();

    @Getter
    @Setter
    //全局通用的PreInterceptors
    private List<CommandInterceptorI> globalPreInterceptors = new ArrayList<>();
    @Getter
    @Setter
    //全局通用的PostInterceptors
    private List<CommandInterceptorI> globalPostInterceptors = new ArrayList<>();
    @Getter
    @Setter
    private Map<Class/*CommandClz*/, CommandInvocation> commandRepository = new HashMap<>();
    
    @Getter
    private Map<Class/*CommandClz*/, Class/*ResponseClz*/> responseRepository = new HashMap<>();
    
    public CommandInvocation getCommandInvocation(Class cmdClass) {
        CommandInvocation commandInvocation = commandRepository.get(cmdClass);
        if (commandRepository.get(cmdClass) == null){
            throw new InfraException(cmdClass + " is not registered in CommandHub, please register first");
        }
        return commandInvocation;
    }
}
