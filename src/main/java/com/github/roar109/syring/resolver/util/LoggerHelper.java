package com.github.roar109.syring.resolver.util;

/**
 * @author hector.mendoza
 * */
public class LoggerHelper {

	private static final Boolean debugEnabled = System.getProperty("syring.debug.log.enabled") != null;

	public static void log(final String message) {
		if (debugEnabled) {
			System.out.println("DEBUG: " + message);
		}
	}
	
	public static void logError(final String message){
		System.err.println(message);
	}
	
	public static void logError(final Exception exception){
		System.err.println(exception.getMessage());
		exception.printStackTrace();
	}
}
