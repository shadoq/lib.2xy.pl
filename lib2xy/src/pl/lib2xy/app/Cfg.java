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
import pl.lib2xy.XY;

public class Cfg{

	public String title = "";
	public String projectFile = "";
	public String version = "";
	public String target = "";

	//
	// Application config
	//
	public boolean pause = false;
	public boolean debug = true;
	public boolean grid = true;
	public boolean logging = false;
	public boolean editor = false;
	public boolean debugGui = true;

	public boolean resize = false;
	public boolean forceExit = true;
	public boolean fullScreen = false;
	public boolean vSync = false;
	public int frameRateLimit = 0;

	//
	// Audio config
	//
	public int audioBufferCount = 10;
	public boolean disableAudio = false;

	//
	// Screen config
	//
	public String targetSize = "800x480";
	public String editSize = "800x480";
	public Screen.ResolutionType resolutionType = Screen.ResolutionType.MDPI;
	public boolean virtualScreen = true;
	public Screen.ViewPortType viewPortType = Screen.ViewPortType.SCREEN;

	public void reset(){
		this.title = XY.appName;
		this.projectFile = XY.appName.toLowerCase().replace(' ', '_').replace(':', '_').replace('\\', '_').replace('/', '_');
		this.targetSize = "800x480";
		this.editSize = "800x480";
		this.audioBufferCount = 10;
		this.resize = false;
		this.forceExit = true;
		this.fullScreen = false;
		this.vSync = true;
		this.frameRateLimit = 0;
		this.disableAudio = false;
		this.logging = true;
		this.version = "";
		this.target = "";
		resolutionType = Screen.ResolutionType.MDPI;
		this.viewPortType = Screen.ViewPortType.SCREEN;
		this.virtualScreen = true;

		if (Gdx.app.getType() == Application.ApplicationType.Android){
			debugGui = false;
		}
	}

	public void setConfig(Cfg config){
		this.title = config.title;
		this.projectFile = config.projectFile;
		this.version = config.version;
		this.target = config.target;

		this.pause = config.pause;
		this.debug = config.debug;
		this.grid = config.grid;
		this.logging = config.logging;
		this.resize = config.resize;
		this.forceExit = config.forceExit;
		this.fullScreen = config.fullScreen;
		this.vSync = config.vSync;
		this.frameRateLimit = config.frameRateLimit;

		this.audioBufferCount = config.audioBufferCount;
		this.disableAudio = config.disableAudio;

		this.targetSize = config.targetSize;
		this.editSize = config.editSize;
		this.resolutionType = config.resolutionType;
		this.virtualScreen = config.virtualScreen;
		this.viewPortType = config.viewPortType;

		this.debugGui = config.debugGui;
		this.debug = config.debugGui;

	}

	@Override
	public String toString(){
		return "Cfg{" +
		"title: '" + title + '\'' +
		", projectFile: '" + projectFile + '\'' +
		", version: '" + version + '\'' +
		", target: '" + target + '\'' +
		", pause: " + pause +
		", log: " + debug +
		", grid: " + grid +
		", logging: " + logging +
		", resize: " + resize +
		", forceExit: " + forceExit +
		", fullScreen: " + fullScreen +
		", vSync: " + vSync +
		", frameRateLimit: " + frameRateLimit +
		", audioBufferCount: " + audioBufferCount +
		", disableAudio: " + disableAudio +
		", targetSize: '" + targetSize + '\'' +
		", editSize: '" + editSize + '\'' +
		", resolutionType: " + resolutionType +
		", virtualScreen: " + virtualScreen +
		", viewPortType: " + viewPortType +
		'}';
	}

	//
	// Resource config
	//
	final public static int proceduralTextureSize = 64;

	//
	// Grid config
	//


	public static int gridX0 = 0;
	public static int gridX1 = (int) Math.round(XY.width / 24.0 * 1);
	public static int gridX2 = (int) Math.round(XY.width / 24.0 * 2);
	public static int gridX3 = (int) Math.round(XY.width / 24.0 * 3);
	public static int gridX4 = (int) Math.round(XY.width / 24.0 * 4);
	public static int gridX5 = (int) Math.round(XY.width / 24.0 * 5);
	public static int gridX6 = (int) Math.round(XY.width / 24.0 * 6);
	public static int gridX7 = (int) Math.round(XY.width / 24.0 * 7);
	public static int gridX8 = (int) Math.round(XY.width / 24.0 * 8);
	public static int gridX9 = (int) Math.round(XY.width / 24.0 * 9);
	public static int gridX10 = (int) Math.round(XY.width / 24.0 * 10);
	public static int gridX11 = (int) Math.round(XY.width / 24.0 * 11);
	public static int gridX12 = (int) Math.round(XY.width / 24.0 * 12);
	public static int gridX13 = (int) Math.round(XY.width / 24.0 * 13);
	public static int gridX14 = (int) Math.round(XY.width / 24.0 * 14);
	public static int gridX15 = (int) Math.round(XY.width / 24.0 * 15);
	public static int gridX16 = (int) Math.round(XY.width / 24.0 * 16);
	public static int gridX17 = (int) Math.round(XY.width / 24.0 * 17);
	public static int gridX18 = (int) Math.round(XY.width / 24.0 * 18);
	public static int gridX19 = (int) Math.round(XY.width / 24.0 * 19);
	public static int gridX20 = (int) Math.round(XY.width / 24.0 * 20);
	public static int gridX21 = (int) Math.round(XY.width / 24.0 * 21);
	public static int gridX22 = (int) Math.round(XY.width / 24.0 * 22);
	public static int gridX23 = (int) Math.round(XY.width / 24.0 * 23);
	public static int gridX24 = XY.width;

	public static int gridY0 = 0;
	public static int gridY1 = (int) Math.round(XY.height / 24.0 * 1);
	public static int gridY2 = (int) Math.round(XY.height / 24.0 * 2);
	public static int gridY3 = (int) Math.round(XY.height / 24.0 * 3);
	public static int gridY4 = (int) Math.round(XY.height / 24.0 * 4);
	public static int gridY5 = (int) Math.round(XY.height / 24.0 * 5);
	public static int gridY6 = (int) Math.round(XY.height / 24.0 * 6);
	public static int gridY7 = (int) Math.round(XY.height / 24.0 * 7);
	public static int gridY8 = (int) Math.round(XY.height / 24.0 * 8);
	public static int gridY9 = (int) Math.round(XY.height / 24.0 * 9);
	public static int gridY10 = (int) Math.round(XY.height / 24.0 * 10);
	public static int gridY11 = (int) Math.round(XY.height / 24.0 * 11);
	public static int gridY12 = (int) Math.round(XY.height / 24.0 * 12);
	public static int gridY13 = (int) Math.round(XY.height / 24.0 * 13);
	public static int gridY14 = (int) Math.round(XY.height / 24.0 * 14);
	public static int gridY15 = (int) Math.round(XY.height / 24.0 * 15);
	public static int gridY16 = (int) Math.round(XY.height / 24.0 * 16);
	public static int gridY17 = (int) Math.round(XY.height / 24.0 * 17);
	public static int gridY18 = (int) Math.round(XY.height / 24.0 * 18);
	public static int gridY19 = (int) Math.round(XY.height / 24.0 * 19);
	public static int gridY20 = (int) Math.round(XY.height / 24.0 * 20);
	public static int gridY21 = (int) Math.round(XY.height / 24.0 * 21);
	public static int gridY22 = (int) Math.round(XY.height / 24.0 * 22);
	public static int gridY23 = (int) Math.round(XY.height / 24.0 * 23);
	public static int gridY24 = XY.height;

	public static void setupGrid(){
		gridX0 = 0;
		gridX1 = (int) Math.round(XY.width / 24.0 * 1);
		gridX2 = (int) Math.round(XY.width / 24.0 * 2);
		gridX3 = (int) Math.round(XY.width / 24.0 * 3);
		gridX4 = (int) Math.round(XY.width / 24.0 * 4);
		gridX5 = (int) Math.round(XY.width / 24.0 * 5);
		gridX6 = (int) Math.round(XY.width / 24.0 * 6);
		gridX7 = (int) Math.round(XY.width / 24.0 * 7);
		gridX8 = (int) Math.round(XY.width / 24.0 * 8);
		gridX9 = (int) Math.round(XY.width / 24.0 * 9);
		gridX10 = (int) Math.round(XY.width / 24.0 * 10);
		gridX11 = (int) Math.round(XY.width / 24.0 * 11);
		gridX12 = (int) Math.round(XY.width / 24.0 * 12);
		gridX13 = (int) Math.round(XY.width / 24.0 * 13);
		gridX14 = (int) Math.round(XY.width / 24.0 * 14);
		gridX15 = (int) Math.round(XY.width / 24.0 * 15);
		gridX16 = (int) Math.round(XY.width / 24.0 * 16);
		gridX17 = (int) Math.round(XY.width / 24.0 * 17);
		gridX18 = (int) Math.round(XY.width / 24.0 * 18);
		gridX19 = (int) Math.round(XY.width / 24.0 * 19);
		gridX20 = (int) Math.round(XY.width / 24.0 * 20);
		gridX21 = (int) Math.round(XY.width / 24.0 * 21);
		gridX22 = (int) Math.round(XY.width / 24.0 * 22);
		gridX23 = (int) Math.round(XY.width / 24.0 * 23);
		gridX24 = XY.width;

		gridY0 = 0;
		gridY1 = (int) Math.round(XY.height / 24.0 * 1);
		gridY2 = (int) Math.round(XY.height / 24.0 * 2);
		gridY3 = (int) Math.round(XY.height / 24.0 * 3);
		gridY4 = (int) Math.round(XY.height / 24.0 * 4);
		gridY5 = (int) Math.round(XY.height / 24.0 * 5);
		gridY6 = (int) Math.round(XY.height / 24.0 * 6);
		gridY7 = (int) Math.round(XY.height / 24.0 * 7);
		gridY8 = (int) Math.round(XY.height / 24.0 * 8);
		gridY9 = (int) Math.round(XY.height / 24.0 * 9);
		gridY10 = (int) Math.round(XY.height / 24.0 * 10);
		gridY11 = (int) Math.round(XY.height / 24.0 * 11);
		gridY12 = (int) Math.round(XY.height / 24.0 * 12);
		gridY13 = (int) Math.round(XY.height / 24.0 * 13);
		gridY14 = (int) Math.round(XY.height / 24.0 * 14);
		gridY15 = (int) Math.round(XY.height / 24.0 * 15);
		gridY16 = (int) Math.round(XY.height / 24.0 * 16);
		gridY17 = (int) Math.round(XY.height / 24.0 * 17);
		gridY18 = (int) Math.round(XY.height / 24.0 * 18);
		gridY19 = (int) Math.round(XY.height / 24.0 * 19);
		gridY20 = (int) Math.round(XY.height / 24.0 * 20);
		gridY21 = (int) Math.round(XY.height / 24.0 * 21);
		gridY22 = (int) Math.round(XY.height / 24.0 * 22);
		gridY23 = (int) Math.round(XY.height / 24.0 * 23);
		gridY24 = XY.height;
	}
}
