package org.xujin.halo.logger;

public class LoggerFactory {

    private static boolean useSysLogger = true;

    public static Logger getLogger(Class<?> clazz) {
        if(useSysLogger) {
            return SysLogger.singleton;
        }
        org.slf4j.Logger slfjLogger = org.slf4j.LoggerFactory.getLogger(clazz);
        return new SLFJLogger(slfjLogger);
    }

    public static Logger getLogger(String loggerName) {
        if(useSysLogger) {
            return SysLogger.singleton;
        }
        org.slf4j.Logger slfjLogger = org.slf4j.LoggerFactory.getLogger(loggerName);
        return new SLFJLogger(slfjLogger);
    }

    /**
     * This is just for test purpose, don't use it on product!
     */
    public static void activateSysLogger() {
        useSysLogger = true;
    }

    public static void deactivateSysLogger() {
        useSysLogger = false;
    }
}
