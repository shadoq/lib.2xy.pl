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

package pl.lib2xy;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.ArrayMap;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.viewport.Viewport;
import pl.lib2xy.app.Cfg;
import pl.lib2xy.app.Scene;
import pl.lib2xy.app.SystemEmulator;
import pl.lib2xy.debug.Debug;

final public class XY{

	public static Graphics graphics = null;
	public static GL20 gl = null;
	public static Input input = null;
	public static Audio audio = null;
	public static Application.ApplicationType platform = null;
	public static SystemEmulator env = null;

	//-----------------------------------------
	// App data
	//-----------------------------------------
	public static String appName = "default";
	public static String appDir = null;
	public static String externalDir = null;
	public static Cfg cfg = new Cfg();

	//-----------------------------------------
	// Time Data
	//-----------------------------------------
	public static float osTime = 0;
	public static float appTime = 0;
	public static float deltaTime = 0;

	//-----------------------------------------
	// Screen data
	//-----------------------------------------
	public static int centerX = 0;
	public static int centerY = 0;
	public static int width = 0;
	public static int height = 0;
	public static int screenWidth = 0;
	public static int screenHeight = 0;
	public static Skin skin;
	public static Viewport viewPort;
	public static Stage preRenderStage;
	public static Stage stage;
	public static Stage postRenderStage;
	public static boolean renderStage = true;

	//-----------------------------------------
	// Graphics
	//-----------------------------------------
	public static OrthographicCamera fixedCamera;
	public static ShapeRenderer shapeRenderer;
	public static SpriteBatch spriteBatch;

	//-----------------------------------------
	// Input
	//-----------------------------------------
	public static InputMultiplexer inputMultiplexer;


	public static Vector2 mouse = new Vector2();
	public static Vector2 mouseScreen = new Vector2();
	public static Vector2 mouseButton0 = new Vector2();
	public static Vector2 mouseButton1 = new Vector2();

	public static boolean mouseBusy = false;
	public static Vector3 mouseUnproject = new Vector3();

	//-----------------------------------------
	// Scene data
	//-----------------------------------------
	public static final String libDataClassPath = "pl/lib2xy/resource/";
	public static final String libDataWebGLPath = "resource/";

	public static final Json json = new Json();
	public static JsonReader jsonReader = new JsonReader();
	public static JsonValue cfgJson = null;

	public static ArrayMap<String, Scene> sceneMaps = new ArrayMap<>(10);
	public static ArrayMap<String, String> sceneGuiMaps = new ArrayMap<>(10);
	public static ArrayMap<String, String> sceneDataMaps = new ArrayMap<>(10);

	public static int sceneIndex = 0;
	public static Scene scene;
	public static String currentSceneName = "";
	public static boolean saveEnable = true;
	public static boolean disableEffect = false;

	public static Debug debug;
}