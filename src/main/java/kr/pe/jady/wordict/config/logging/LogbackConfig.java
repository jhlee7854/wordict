package kr.pe.jady.wordict.config.logging;

import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.encoder.PatternLayoutEncoder;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.Appender;
import ch.qos.logback.core.ConsoleAppender;
import ch.qos.logback.ext.spring.ApplicationContextHolder;

@Configuration
public class LogbackConfig {
	
	private static String DEFAULT_LOGGING_PATTERN = "%d{HH:mm:ss.SSS} [%thread] %-5level %50.-50logger{36} - %msg%n";
	
	@Bean
	public static ApplicationContextHolder applicationContextHolder() {
		return new ApplicationContextHolder();
	}
	
	@Bean
	public static LoggerContext loggerContext() {
		LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();
		return loggerContext;
	}
	
	@Bean (initMethod = "start", destroyMethod = "stop")
	public static PatternLayoutEncoder encoder (LoggerContext ctx) {
		PatternLayoutEncoder encoder = new PatternLayoutEncoder();
		encoder.setContext(ctx);
		encoder.setPattern(DEFAULT_LOGGING_PATTERN);
		return encoder;
	}
	
	@Bean (initMethod = "start", destroyMethod = "stop")
	public static ConsoleAppender<ILoggingEvent> consoleAppender (LoggerContext ctx, PatternLayoutEncoder encoder) {
		ConsoleAppender<ILoggingEvent> appender = new ConsoleAppender<>();
		appender.setContext(ctx);
		appender.setEncoder(encoder);
		return appender;
	}
	
	@Bean
	public static Logger springframeworkLogger(LoggerContext loggerContext, ConsoleAppender<ILoggingEvent> consoleAppender) {
		return createLogger(loggerContext, "org.springframework", Level.INFO, consoleAppender);
	}
	
	@Bean
	public static Logger applicationLogger(LoggerContext loggerContext, ConsoleAppender<ILoggingEvent> consoleAppender) {
		return createLogger(loggerContext, "kr.pe.jady.wordict", Level.DEBUG, consoleAppender);
	}
	
	@Bean
	public static Logger rootLogger(LoggerContext loggerContext, ConsoleAppender<ILoggingEvent> consoleAppender) {
		return createLogger(loggerContext, Logger.ROOT_LOGGER_NAME, Level.INFO, consoleAppender);
	}
	
	@SafeVarargs
	private static Logger createLogger(LoggerContext loggerContext, String loggerName, Level logLevel, Appender<ILoggingEvent> ... appenders) {
		Logger logger = loggerContext.getLogger(loggerName);
		logger.detachAndStopAllAppenders();
		for (Appender<ILoggingEvent> appender : appenders) {
			logger.addAppender(appender);
		}
		logger.setLevel(logLevel);
		logger.setAdditive(false);
		return logger;
	}

}
