/*
 * Copyright 2013-2015 See AUTHORS file.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package pl.lib2xy.app;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.google.gwt.core.client.GWT;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Class to loging string to memory and Gdx.debug, Loge levels
 * DEBUG - All log information
 * LOG - Standard application message
 * ERROR - Error level message
 */
public class Log{

	public static final int OFF = 0;
	public static final int DEBUG = 3;
	public static final int LOG = 2;
	public static final int ERROR = 1;

	private final static String ANSI_WHITE = "\u001b[37m";
	private final static String ANSI_RED = "\u001b[31m";
	private final static String ANSI_BLACK = "\u001b[30m";
	private final static String ANSI_BLUE = "\u001b[34m";
	private final static String ANSI_RESET = "\u001b[0m";
	private final static String ANSI_GREEN = "\u001b[32m";
	private final static String ANSI_YELLOW = "\u001b[33m";
	private final static String ANSI_CYAN = "\u001b[36m";
	private final static String ANSI_PURPLE = "\u001b[35m";

	private final static StringBuilder log = new StringBuilder("");
	private static int logLevel = DEBUG;

	/**
	 * @return
	 */
	public static String getLog(){
		return log.toString();
	}

	/**
	 * @return
	 */
	public static int getLogLevel(){
		return logLevel;
	}

	/**
	 * @param logLevel
	 */
	public static void setLogLevel(int logLevel){
		Log.logLevel = logLevel;
	}


	/**
	 * @param tag
	 * @param message
	 * @param color
	 */
	public static void debug(String tag, String message, int color){
		if (logLevel >= Log.DEBUG){
			writeASCI("[DEBUG]" + tag, message, color);
		}
	}

	public static void debug(String tag, String method, String message, int color){
		if (logLevel >= Log.DEBUG){
			writeASCI("DEBUG][" + tag + "][" + method + "", message, color);
		}
	}

	/**
	 * @param tag
	 * @param message
	 */
	public static void debug(String tag, String message){
		if (logLevel >= Log.DEBUG){
			write("[DEBUG]" + tag, message);
		}
	}

	/**
	 * @param message
	 */
	public static void debug(String message){
		if (logLevel >= Log.DEBUG){
			write("[DEBUG]", message);
		}
	}

	/**
	 * @param tag
	 * @param message
	 * @param color
	 */
	public static void log(String tag, String message, int color){
		if (logLevel >= Log.LOG){
			writeASCI("[LOG]" + tag, message, color);
		}
	}

	/**
	 * @param tag
	 * @param message
	 */
	public static void log(String tag, String message){
		if (logLevel >= Log.LOG){
			write("[LOG]" + tag, message);
		}
	}

	/**
	 * @param message
	 */
	public static void log(String message){
		if (logLevel >= Log.LOG){
			write("[LOG]", message);
		}
	}

	/**
	 * @param thrown
	 */
	public static void error(Throwable thrown){
		error("[ Exception ] ", thrown.getLocalizedMessage());
		error("[ Exception ] ", getStackTrace(thrown));
	}

	/**
	 * @param tag
	 * @param message
	 * @param thrown
	 */
	public static void error(String tag, String message, Throwable thrown){
		error("[ Exception in: " + tag + "] ", message + " - " + thrown.getLocalizedMessage());
		error("[ Exception in: " + tag + "] ", getStackTrace(thrown));
	}

	/**
	 * @param tag
	 * @param message
	 * @param color
	 */
	public static void error(String tag, String message, int color){
		if (logLevel >= Log.ERROR){
			writeASCI("[ERROR]" + tag, message, color);
		}
	}

	/**
	 * @param tag
	 * @param message
	 */
	public static void error(String tag, String message){
		if (logLevel >= Log.ERROR){
			write("[ERROR]" + tag, message);
		}
	}

	/**
	 * @param message
	 */
	public static void error(String message){
		if (logLevel >= Log.ERROR){
			write("[ERROR]", message);
		}
	}

	/**
	 *
	 */
	public static void clear(){
		log.setLength(0);
	}

	/**
	 * @param tag
	 * @param message
	 */
	public static void logLines(String tag, String message){

		if (logLevel >= Log.LOG){

			try{
				if (log.length() > 512){
					log.setLength(0);
				}
			} catch (NullPointerException ex){
				log.setLength(0);
			}
			StringBuilder tmpLog = new StringBuilder();
			tmpLog.append("[").append(tag).append("] ").append(" ------------------------------------ \n");
			String[] lines = message.split("\n");
			for (int i = 0; i < lines.length; i++){
				String line = lines[i];
				tmpLog.append("[").append(tag).append("][").append(i + 1).append("] ").append(line).append("\n");
			}
			tmpLog.append("[").append(tag).append("] ").append(" ------------------------------------ \n");
			log.append(tmpLog);
			Gdx.app.log(tag, tmpLog.toString());
		}
	}

	/**
	 * @param tag
	 * @param message
	 */
	private static void writeASCI(String tag, String message, int color){

		if (Gdx.app.getType() == Application.ApplicationType.Android ||
		Gdx.app.getType() == Application.ApplicationType.WebGL
		){
			write(tag, message);
		} else {
			switch (color){
				case 1:
					write(tag, ANSI_RED + message + ANSI_RESET);
					break;
				case 2:
					write(tag, ANSI_GREEN + message + ANSI_RESET);
					break;
				case 3:
					write(tag, ANSI_BLUE + message + ANSI_RESET);
					break;
				case 4:
					write(tag, ANSI_YELLOW + message + ANSI_RESET);
					break;
				case 5:
					write(tag, ANSI_CYAN + message + ANSI_RESET);
					break;
				case 6:
					write(tag, ANSI_PURPLE + message + ANSI_RESET);
					break;
				case 7:
					write(tag, ANSI_WHITE + message + ANSI_RESET);
					break;
				case 87:
					write(tag, ANSI_BLACK + message + ANSI_RESET);
					break;
				default:
					write(tag, ANSI_RESET + message);
					break;
			}
		}
	}

	/**
	 * @param tag
	 * @param message
	 */
	private static void write(String tag, String message){

		try{
			if (log.length() > 512){
				log.setLength(0);
			}
		} catch (NullPointerException ex){
			log.setLength(0);
		}

		if (tag != null){
			log.append("[").append(tag).append("] ").append(message).append("\n");
			if (Gdx.app != null){
				Gdx.app.log(tag, message);
			} else {
				System.out.println("[" + tag + "] " + message);
			}
		} else {
			log.append(message).append("\n");
			if (Gdx.app != null){
				Gdx.app.log("", message);
			} else {
				System.out.println(message);
			}
		}
	}

	/**
	 * @param thrown
	 * @return
	 */
	private static synchronized String getStackTrace(Throwable thrown){
		StringBuilder out = new StringBuilder();
		out.append(thrown.toString());
		StackTraceElement[] trace = thrown.getStackTrace();
		for (int i = 0; i < trace.length; i++){
			out.append("\n at ").append(trace[i]);
		}
		return out.toString();
	}
}
