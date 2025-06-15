package Utilities;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LogsUtils {

    public static final String LOGSPATH = "testOutputs/Logs/";

    private static final Logger logger = LogManager.getLogger(LogsUtils.class);

    public static void trace(String message) {
        String caller = Thread.currentThread().getStackTrace()[2].toString();
        logger.trace(message);
    }

    public static void debug(String message) {
        String caller = Thread.currentThread().getStackTrace()[2].toString();
        logger.debug(message);
    }

    public static void info(String message) {
        String caller = Thread.currentThread().getStackTrace()[2].toString();
        logger.info(message);
    }

    public static void warn(String message) {
        String caller = Thread.currentThread().getStackTrace()[2].toString();
        logger.warn(message);
    }
    public static void error(String message) {
        String caller = Thread.currentThread().getStackTrace()[2].toString();
        logger.error(message);
    }

    public static void fatal(String message) {
        String caller = Thread.currentThread().getStackTrace()[2].toString();
        logger.fatal(message);
    }
}
