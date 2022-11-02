package com.arjstack.tech.utils;

import java.io.PrintWriter;
import java.io.Serializable;
import java.io.StringWriter;

import org.slf4j.Logger;
import org.slf4j.event.Level;

public class LogWrapper implements Serializable {

	private static LogWrapper logWrapper = new LogWrapper();

	public static LogWrapper getInstance() {
		return logWrapper;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 * @param message
	 *            the String message to LOG.
	 */
	public void log(Logger logger, Level level, String message) {
		if (Level.DEBUG.equals(level)) {
			logger.debug(message);
		} else if (Level.ERROR.equals(level)) {
			logger.error(message);
		} else if (Level.INFO.equals(level)) {
			logger.info(message);
		} else if (Level.TRACE.equals(level)) {
			logger.trace(message);
		} else if (Level.WARN.equals(level)) {
			logger.warn(message);
		}
	}

	public void log(Logger logger, Level level, Throwable e) {
		StringWriter stackTrace = new StringWriter();
		PrintWriter writer = new PrintWriter(stackTrace);
		e.printStackTrace(writer);

		if (Level.DEBUG.equals(level)) {
			logger.debug(writer.toString());
		} else if (Level.ERROR.equals(level)) {
			logger.error(writer.toString());
		} else if (Level.INFO.equals(level)) {
			logger.info(writer.toString());
		} else if (Level.TRACE.equals(level)) {
			logger.trace(writer.toString());
		} else if (Level.WARN.equals(level)) {
			logger.warn(writer.toString());
		}
	}

	public void log(Logger logger, Level level, Throwable e, String message) {
		StringWriter stackTrace = new StringWriter();
		PrintWriter writer = new PrintWriter(stackTrace);
		e.printStackTrace(writer);

		if (Level.DEBUG.equals(level)) {
			logger.debug(message + ":" + writer.toString());
		} else if (Level.ERROR.equals(level)) {
			logger.error(message + ":" + writer.toString());
		} else if (Level.INFO.equals(level)) {
			logger.info(message + ":" + writer.toString());
		} else if (Level.TRACE.equals(level)) {
			logger.trace(message + ":" + writer.toString());
		} else if (Level.WARN.equals(level)) {
			logger.warn(message + ":" + writer.toString());
		}
	}

}
