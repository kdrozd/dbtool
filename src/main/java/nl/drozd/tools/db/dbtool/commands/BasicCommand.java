package nl.drozd.tools.db.dbtool.commands;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.LoggerContext;
import picocli.CommandLine.Option;

/*
 * Most basic options common to all commands
 */
public abstract class BasicCommand {
	protected final static Logger logger = LoggerFactory.getLogger("CMD");
	@Option(
			names = { "--loglevel", "-ll" },
			description = "Logging level, default value: ${DEFAULT-VALUE}, available levels: ${COMPLETION-CANDIDATES}",
			hidden = false,
			defaultValue = "WARN",
			fallbackValue = "WARN"
			
			
			)
	protected LogLevelEnum logLevel;

	protected void changeLogLevel(String logLevel) {

		Level level = Level.toLevel(logLevel.toUpperCase()); // default to INFO

		LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();
		List<ch.qos.logback.classic.Logger> loggerList = loggerContext.getLoggerList();
		loggerList.stream()
				.filter(lo -> lo.getName().equals("ROOT"))
				.forEach(tmpLogger -> tmpLogger.setLevel(level));
	}
}
