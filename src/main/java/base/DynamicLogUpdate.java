package base;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import org.slf4j.LoggerFactory;

/**
 * Created by mwjsolar on 16/7/30.
 */

public class DynamicLogUpdate {
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(DynamicLogUpdate.class);
    public static void main(String[] args) {

        logger.debug("test debug before");

        String loggerName = Logger.ROOT_LOGGER_NAME;
        String level = "debug";
        LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();
        Logger logger = loggerContext.getLogger(loggerName);
        if (logger == null)
            return;

        logger.setLevel(matchLevel(level));


        //TEST LOG
        logger.info("test info");

        logger.debug("test debug after");
    }

    private static Level  matchLevel(String levelName) {
        Level level;
        switch (levelName) {
            case "debug":
                level = Level.DEBUG;
                break;
            case "info":
                level = Level.INFO;
                break;
            default:
                throw new IllegalArgumentException("");
        }
        return level;
    }


}
