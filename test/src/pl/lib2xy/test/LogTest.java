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

package pl.lib2xy.test;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import pl.lib2xy.app.Log;
import pl.lib2xy.app.Scene;

public class LogTest extends Scene{

	public void initialize(){

		System.out.println("Level Log");
		Log.setLogLevel(Log.LOG);
		Log.debug("Debug message ...");
		Log.log("Log message");
		Log.error("Error message");
		Log.logLines("Lines", "Lines");

		System.out.println("Level Debug");
		Log.setLogLevel(Log.DEBUG);
		Log.debug("Debug message ...");
		Log.log("Log message");
		Log.error("Error message");
		Log.logLines("Lines", "Lines");

		System.out.println("Level Error");
		Log.setLogLevel(Log.ERROR);
		Log.debug("Debug message ...");
		Log.log("Log message");
		Log.error("Error message");
		Log.logLines("Lines", "Lines");
	}
}
