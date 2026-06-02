package Utilities;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LogsUtils {

    public static final String LOGSPATH = "testOutputs/Logs/";

    private static final Logger logger = LogManager.getLogger(LogsUtils.class);

    private LogsUtils() {
    }


    private static String caller() {
        for (StackTraceElement element : Thread.currentThread().getStackTrace()) {
            String cls = element.getClassName();
            if (!cls.equals(LogsUtils.class.getName()) && !cls.equals(Thread.class.getName())) {
                return element.getClassName() + "." + element.getMethodName()
                        + ":" + element.getLineNumber();
            }
        }
        return "unknown";
    }

    private static void log(Level level, String message, Throwable throwable) {
        if (!logger.isEnabled(level)) {
            return; // Skip the (relatively costly) stack walk when the level is off.
        }
        if (throwable == null) {
            logger.log(level, "[{}] {}", caller(), message);
        } else {
            logger.log(level, "[{}] {}", caller(), message, throwable);
        }
    }

    public static void trace(String message) {
        log(Level.TRACE, message, null);
    }

    public static void debug(String message) {
        log(Level.DEBUG, message, null);
    }

    public static void info(String message) {
        log(Level.INFO, message, null);
    }

    public static void warn(String message) {
        log(Level.WARN, message, null);
    }

    public static void warn(String message, Throwable throwable) {
        log(Level.WARN, message, throwable);
    }

    public static void error(String message) {
        log(Level.ERROR, message, null);
    }

    public static void error(String message, Throwable throwable) {
        log(Level.ERROR, message, throwable);
    }

    public static void fatal(String message) {
        log(Level.FATAL, message, null);
    }

    public static void fatal(String message, Throwable throwable) {
        log(Level.FATAL, message, throwable);
    }
}
