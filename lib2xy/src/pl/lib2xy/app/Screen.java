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
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.viewport.*;
import pl.lib2xy.XY;

public class Screen{

	public static enum ResolutionType{
		LDPI, MDPI, HDPI, XHDPI
	}

	public static enum ViewPortType{
		SCREEN, STRETCH, EXTEND, SCREEN_1_5, SCREEN_1_25, SCREEN_0_75, SCREEN_0_5

	}

	private static final String TAG = "Screen";

	private static int VIRTUAL_WIDTH = 800;
	private static int VIRTUAL_HEIGHT = 480;
	private static boolean VIRTUAL_SCREEN = true;

	private static int screenWidth = 0;
	private static int screenHeight = 0;
	private static int screenCenterX = 0;
	private static int screenCenterY = 0;

	private static int viewportX = 0;
	private static int viewportY = 0;
	private static int viewportWidth = 0;
	private static int viewportHeight = 0;

	private static float scale = 1.0f;
	private static float aspectRatio = 1.0f;
	private static float realAspectRatio = 1.0f;
	private static float aspectRatioX = 1.0f;
	private static float aspectRatioY = 1.0f;

	private static ResolutionType resolution = ResolutionType.MDPI;
	private static Graphics.GraphicsType type;
	private static float density = 0;

	/**
	 *
	 */
	public static void create(){
		type = XY.graphics.getType();
		density = XY.graphics.getDensity();

		Log.debug(TAG, "GL_VENDOR=" + XY.gl.glGetString(GL30.GL_VENDOR));
		Log.debug(TAG, "GL_VENDOR=" + XY.gl.glGetString(GL30.GL_VENDOR));
		Log.debug(TAG, "GL_RENDERER=" + XY.gl.glGetString(GL30.GL_RENDERER));
		Log.debug(TAG, "GL_VERSION=" + XY.gl.glGetString(GL30.GL_VERSION));
		if (Gdx.app.getType() != Application.ApplicationType.WebGL){
			Log.debug(TAG, "GL_EXTENSIONS :\n" + XY.gl.glGetString(GL30.GL_EXTENSIONS).trim().replace(" ", " "));
		}

		if (XY.cfg.editSize != null){
			String[] virtualScreenSize = XY.cfg.editSize.split("x");
			if (Integer.parseInt(virtualScreenSize[0]) > 0 && Integer.parseInt(virtualScreenSize[1]) > 0){
				VIRTUAL_WIDTH = Integer.parseInt(virtualScreenSize[0]);
				VIRTUAL_HEIGHT = Integer.parseInt(virtualScreenSize[1]);
			}
		}

		if (VIRTUAL_SCREEN){
			XY.width = VIRTUAL_WIDTH;
			XY.height = VIRTUAL_HEIGHT;
			resolution = ResolutionType.MDPI;
		} else {
			XY.width = XY.graphics.getWidth();
			XY.height = XY.graphics.getHeight();

			//
			// Detect screen size
			//
			if ((XY.width > XY.height && XY.width <= 481) || (XY.height > XY.width && XY.height <= 481)){
				resolution = ResolutionType.LDPI;
			} else if ((XY.width > XY.height && XY.width < 721) || (XY.height > XY.width && XY.height < 721)){
				resolution = ResolutionType.MDPI;
			} else if (((XY.width > XY.height && XY.width < 1301) || (XY.height > XY.width && XY.height < 1301)) && density < 1.75f){
				resolution = ResolutionType.HDPI;
			} else {
				resolution = ResolutionType.XHDPI;
			}
		}

		resize(XY.graphics.getWidth(), XY.graphics.getHeight());
	}

	public static void resize(int screenWidth, int screenHeight){

		XY.screenWidth=screenWidth;
		XY.screenHeight=screenHeight;

		Screen.screenWidth = screenWidth;
		Screen.screenHeight = screenHeight;

		screenCenterX = screenWidth / 2;
		screenCenterY = screenHeight / 2;

		XY.centerX = XY.width / 2;
		XY.centerY = XY.height / 2;

		realAspectRatio = (float) screenWidth / (float) screenHeight;
		aspectRatio = (float) XY.width / (float) XY.height;
		aspectRatioX = (float) screenWidth / (float) VIRTUAL_WIDTH;
		aspectRatioY = (float) screenHeight / (float) VIRTUAL_HEIGHT;

		scale = 1.0f;
		viewportX = 0;
		viewportY = 0;

		if (realAspectRatio > aspectRatio){
			scale = (float) Screen.screenHeight / (float) XY.height;
			viewportX = (int) ((Screen.screenWidth - XY.width * scale) / 2f);
		} else if (realAspectRatio < aspectRatio){
			scale = (float) Screen.screenWidth / (float) XY.width;
			viewportY = (int) ((Screen.screenHeight - XY.height * scale) / 2f);
		} else {
			scale = (float) Screen.screenWidth / (float) XY.width;
		}

		viewportWidth = (int) ((float) XY.width * scale);
		viewportHeight = (int) ((float) XY.height * scale);

		//
		// Camera setup
		//
		if (XY.fixedCamera == null){
			XY.fixedCamera = new OrthographicCamera(XY.width, XY.height);
			XY.fixedCamera.position.set(XY.centerX, XY.centerY, XY.fixedCamera.position.z);
			XY.fixedCamera.update();
		}

		//
		// Viewport Setup
		//
		if (XY.viewPort == null){
			setViewport(XY.cfg.viewPortType);
		}

		//
		// Stage setup
		//
		if (XY.stage != null){
			XY.stage.getViewport().update(screenWidth, screenHeight, true);
		}
		if (XY.postRenderStage != null){
			XY.postRenderStage.getViewport().update(screenWidth, screenHeight, true);
		}
		if (XY.preRenderStage != null){
			XY.preRenderStage.getViewport().update(screenWidth, screenHeight, true);
		}

		Log.debug(TAG, "Screen density: " + density + " type:" + type, 2);
		Log.debug(TAG, "Screen size: " + Screen.screenWidth + ":" + Screen.screenHeight + " center: " + screenCenterX + ":" + screenCenterY, 2);
		Log.debug(TAG, "Virtual size: " + XY.width + ":" + XY.height + " center: " + XY.centerX + ":" + XY.centerY, 2);

		Log.debug(TAG, "aspectVirtual: " + aspectRatio + " aspectRatio: " + realAspectRatio + " scale: " + scale, 2);
		Log.debug(TAG, "aspectRatioX: " + aspectRatioX + " aspectRatioY: " + aspectRatioY, 2);
		Log.debug(TAG, "windowDensity: " + density + " windowType: " + type + "resolution: " + resolution, 2);
		Log.debug(TAG, "viewport: " + viewportX + ":" + viewportY + " viewport Size: " + viewportWidth + ":" + viewportHeight, 2);
		Log.debug(TAG, "viewport: " + XY.viewPort, 2);
	}

	/**
	 * @param viewPortType
	 * @return
	 */
	public static Viewport setViewport(ViewPortType viewPortType){

		switch (viewPortType){
			default:
			case SCREEN:
				XY.viewPort = new ScreenViewport(XY.fixedCamera);
				break;
			case STRETCH:
				XY.viewPort = new StretchViewport(XY.width, XY.height, XY.fixedCamera);
				break;
			case EXTEND:
				XY.viewPort = new ExtendViewport(XY.width, XY.height, 1024, 768, XY.fixedCamera);
				break;
			case SCREEN_1_5:
				ScreenViewport sVW = new ScreenViewport(XY.fixedCamera);
				sVW.setUnitsPerPixel(1.5f);
				XY.viewPort = sVW;
				break;
			case SCREEN_1_25:
				ScreenViewport sVW2 = new ScreenViewport(XY.fixedCamera);
				sVW2.setUnitsPerPixel(1.25f);
				XY.viewPort = sVW2;
				break;
			case SCREEN_0_75:
				ScreenViewport sVW3 = new ScreenViewport(XY.fixedCamera);
				sVW3.setUnitsPerPixel(0.75f);
				XY.viewPort = sVW3;
				break;
			case SCREEN_0_5:
				ScreenViewport sVW4 = new ScreenViewport(XY.fixedCamera);
				sVW4.setUnitsPerPixel(0.5f);
				XY.viewPort = sVW4;
				break;
		}

		return XY.viewPort;
	}

}
