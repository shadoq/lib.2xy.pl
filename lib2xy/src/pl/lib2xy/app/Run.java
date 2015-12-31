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

import com.badlogic.gdx.*;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.JsonWriter;
import com.badlogic.gdx.utils.SnapshotArray;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import pl.lib2xy.XY;
import pl.lib2xy.audio.MusicPlayer;
import pl.lib2xy.constans.MusicPlayType;
import pl.lib2xy.debug.Debug;
import pl.lib2xy.gfx.g2d.EffectCreator;
import pl.lib2xy.interfaces.DataVO;
import pl.lib2xy.items.ActorVO;
import pl.lib2xy.items.SceneVO;

public class Run extends ApplicationAdapter{

	private static final String TAG = "Run";
	private static final boolean LOG = true;
	private static final String lineSeparator = "\n";
	private static long backupFileInterval = 0;

	private StringBuilder appStringTitle = new StringBuilder();
	private StringBuilder appStringDebug = new StringBuilder();
	private float debugTime = 100;
	private int debugActorCount = 0;
	private String debugString = "";
	private boolean appPause = false;

	private static Run instance = null;

	/**
	 *
	 */
	public Run(){
	}

	/**
	 * @param scene
	 */
	public Run(Scene scene){
		XY.scene = scene;
	}

	/**
	 * @param appName
	 */
	public Run(String appName){
		XY.appName = appName;
	}

	/**
	 * @param appName
	 * @param scene
	 */
	public Run(String appName, Scene scene){
		XY.appName = appName;
		XY.scene = scene;
	}

	/**
	 * @param sceneList
	 */
	public Run(Scene[] sceneList){
		this(sceneList, false);
	}

	public Run(Scene[] sceneList, boolean debugMode){
		if (sceneList == null){
			return;
		}
		if (sceneList.length == 0){
			return;
		}
		for (Scene scene : sceneList){
			if (scene != null){
				Log.debug(TAG, "Add scene: " + scene.getClass().getSimpleName());
				XY.sceneMaps.put("" + scene.getClass().getSimpleName(), scene);
			}
		}
		XY.scene = XY.sceneMaps.firstValue();
		XY.currentSceneName = XY.sceneMaps.firstKey();
	}

	/**
	 * @return
	 */
	public static Run getInstance(){
		return Run.instance;
	}

	/**
	 *
	 */
	@Override
	public void create(){

		if (XY.env == null){
			XY.env = new SystemEmulator();
		}
		Gdx.app.setLogLevel(Application.LOG_DEBUG);
		Run.instance = this;

		if (LOG){
			Log.debug(TAG, "Create app start");
		}

		initializeResource();
		initializeGfx();
		initializeApp();

		XY.cfg.grid = true;
		XY.cfg.debug = true;

		if (LOG){
			Log.debug(TAG, "Create app end");
		}

	}

	/**
	 * @param width
	 * @param height
	 */
	@Override
	public void resize(int width, int height){

		if (LOG){
			Log.debug(TAG, "Resize app - width: " + width + " height: " + height);
		}

		Screen.create();
		if (Debug.debugViewPort != null){
			Debug.debugViewPort.update(width, height, true);
			Debug.debugCamera.position.set(Debug.debugCamera.viewportWidth / 2, Debug.debugCamera.viewportHeight / 2, 0);
			Debug.debugCamera.update();
		}
		if (XY.viewPort != null){
			XY.viewPort.update(width, height, true);
		}
		resetCamera();
	}

	/**
	 *
	 */
	@Override
	public void render(){

		if (appPause){
			return;
		}

		if (XY.scene != null && XY.scene.data.backgroundColor != null){
			Gfx.clear(XY.scene.data.backgroundColor);
		} else {
			Gfx.clear();
		}

		//		XY.stage.getViewport().apply();

		XY.deltaTime = Gdx.graphics.getDeltaTime();
		if (!XY.cfg.pause){
			XY.osTime = XY.osTime + XY.deltaTime;
			XY.appTime = XY.appTime + XY.deltaTime;
		}

		if (XY.debug != null){
			XY.debug.update(XY.deltaTime);
		}
		if (XY.scene != null){
			if (!XY.cfg.pause){
				try{
					XY.scene.update(XY.deltaTime);
				} catch (Exception ex){
					Log.error(TAG, "Error in update scene", ex);
				}
			}
		}

		if (XY.cfg.grid){
			Gfx.setColor(Color.DARK_GRAY);
			Gfx.drawGrid(0, 0, XY.width, XY.height, 12, 12);
		}
		if (XY.cfg.editor){
			if (XY.debug != null){
				XY.debug.preRender(XY.deltaTime);
			}
		} else {
			if (XY.scene != null){
				XY.scene.preRender(XY.deltaTime);
			}
		}

		XY.preRenderStage.act(XY.deltaTime);
		XY.preRenderStage.draw();

		if (XY.scene != null){
			try{
				XY.scene.render(XY.deltaTime);
			} catch (Exception ex){
				Log.error(TAG, "Error in render scene", ex);
			}
		}

		if (XY.stage != null && XY.renderStage == true){
			//
			// Resetowanie GUI dla texture0
			//
			XY.gl.glActiveTexture(GL20.GL_TEXTURE0);
			if (!XY.cfg.pause){
				XY.stage.act(XY.deltaTime);
			}
			XY.stage.draw();
		}
		XY.postRenderStage.act(XY.deltaTime);
		XY.postRenderStage.draw();

		if (XY.debug != null){
			XY.debug.postRender(XY.deltaTime);
		}
		if (XY.scene != null){
			XY.scene.postRender(XY.deltaTime);
		}
		if (XY.cfg.grid){
			Gfx.setColor(Color.LIGHT_GRAY);
			Gfx.drawTick(0, 0, XY.width, XY.height, 24, 24);
		}

		if (XY.cfg.grid){
			Gfx.drawCircle(XY.mouse.x, XY.mouse.y, 5f, Color.RED);
			if (XY.mouseButton0.x > 0 && XY.mouseButton0.y > 0){
				Gfx.drawCircle(XY.mouseButton0.x, XY.mouseButton0.y, 5f, Color.YELLOW);
			}
			if (XY.mouseButton1.x > 0 && XY.mouseButton1.y > 0){
				Gfx.drawCircle(XY.mouseButton1.x, XY.mouseButton1.y, 5f, Color.CYAN);
			}
		}

		//
		// Debug
		//
		if (Debug.debugStage != null){
			Debug.debugStage.act(XY.deltaTime);
			Debug.debugStage.draw();
		}

		XY.env.frameRateLimit();
	}

	/**
	 *
	 */
	@Override
	public void pause(){
		appPause = true;
		if (LOG){
			Log.debug(TAG, "Pause app");
		}
		if (XY.scene != null){
			XY.scene.pause();
		}
		Lang.pause();
		ResourceManager.pause();
		Serializer.pause();
	}

	/**
	 *
	 */
	@Override
	public void resume(){
		appPause = false;
		if (LOG){
			Log.debug(TAG, "Resume app");
		}
		if (XY.scene != null){
			XY.scene.resume();
		}
		Lang.resume();
		Serializer.resume();
		ResourceManager.resume();
	}

	/**
	 *
	 */
	@Override
	public void dispose(){
		if (LOG){
			Log.debug(TAG, "Dispose app");
		}
		if (XY.scene != null){
			XY.scene.dispose();
		}
		Lang.dispose();
		ResourceManager.dispose();
		Serializer.dispose();
	}


	/**
	 * @param newScene
	 */
	public static void setScene(Scene newScene){

		Log.debug(TAG, "Set scene: " + newScene);

		if (newScene == null){
			return;
		}

		if (XY.stage != null){
			XY.stage.getRoot().clearActions();
			XY.stage.getRoot().clearChildren();
			XY.stage.getRoot().removeActor(XY.scene);
		}

		if (XY.preRenderStage != null && XY.scene != null){
			XY.scene.removeBackground();
			XY.preRenderStage.getRoot().clearActions();
			XY.preRenderStage.getRoot().clearChildren();
		}
		if (XY.postRenderStage != null && XY.scene != null){
			XY.postRenderStage.getRoot().clearActions();
			XY.postRenderStage.getRoot().clearChildren();
		}

		if (XY.scene != null){
			XY.scene.clear();
			XY.scene.pause();
			XY.scene.dispose();
			XY.scene = null;
		}

		ResourceManager.debugReset();

		XY.scene = newScene;
		XY.sceneIndex = XY.sceneMaps.indexOfValue(newScene, true);
		XY.currentSceneName = XY.sceneMaps.getKey(newScene, true);
		XY.appTime = 0;

		Log.debug(TAG, "Scene: " + XY.scene, 1);
		Log.debug(TAG, "Scene index: " + XY.sceneIndex, 1);
		Log.debug(TAG, "Scene name: " + XY.currentSceneName, 1);


		//
		// Load scene GUI
		//
		String str = XY.sceneGuiMaps.get(XY.currentSceneName);

		Log.debug(TAG, "Scene GUI: " + str, 1);

		if (str != null && !str.isEmpty()){
			String[] lines = str.split(lineSeparator);
			for (String line : lines){
				if (line == null){
					continue;
				}
				if (line.trim().isEmpty()){
					continue;
				}

				Log.debug(TAG, "Scene GUI load: " + line, 1);

				try{
					XY.json.fromJson(null, line);
				} catch (Exception ex){
					Log.error(TAG, "Error load actor: \n" + line, ex);
				}
			}
		}

		//
		// Load scene Data
		//
		String strData = XY.sceneDataMaps.get(XY.currentSceneName);

		Log.debug(TAG, "Scene Data: " + strData, 1);
		if (strData != null && !strData.isEmpty()){
			Log.debug(TAG, "Scene Data load: " + strData, 1);
			SceneVO sceneData = XY.json.fromJson(SceneVO.class, strData);
			XY.scene.data = sceneData;
		}

		if (XY.scene.data.name == null || XY.scene.data.name.isEmpty()){
			XY.scene.data.name = XY.currentSceneName;
		}
		if (XY.scene.getName() == null){
			XY.scene.setName(XY.currentSceneName);
		}

		//
		// Add scene to stage
		//
		if (XY.stage != null && XY.scene != null){

			XY.stage.addActor(XY.scene);
			if (!XY.scene.data.transition.equals("None")){
				EffectCreator.createEffect(
				XY.scene, XY.scene.data.transition, 0f,
				XY.scene.data.transitionDuration,
				XY.scene.data.transitionInterpolation
				);
			}
			if (!XY.scene.data.backgroundFileName.equals("None")){
				XY.scene.setBackground(XY.scene.data.backgroundFileName);
			}
		}

		if (!XY.scene.data.music.equals("None") && XY.scene.data.musicPlayType != MusicPlayType.None && !XY.disableEffect){
			MusicPlayer.getInstance().play(XY.scene.data.music, XY.scene.data.musicPlayType);
		} else if (XY.scene.data.musicPlayType == MusicPlayType.None){
			MusicPlayer.getInstance().stop();
		}

		if (XY.scene != null){
			try{
				XY.scene.initialize();
			} catch (Exception ex){
				Log.error(TAG, "Error in initalize scene", ex);
			}
			XY.scene.resize();
		}

		if (XY.debug != null){
			XY.debug.updateGUI();
		}
	}

	/**
	 * @param scene
	 * @param delay
	 */
	public static void setScene(final Scene scene, float delay){
		if (scene == null){
			return;
		}
		Log.debug("setSceneWithDelay", "name: " + scene + " delay: " + delay);
		Timer.schedule(new Timer.Task(){
			@Override
			public void run(){
				Log.debug("setSceneWithDelay", "Set scene: " + scene);
				setScene(scene);
			}
		}, delay);
	}

	/**
	 * @param sceneName
	 */
	public static void setScene(String sceneName){

		if (sceneName == null){
			Log.debug(TAG, "Scene name are NULL");
			return;
		}
		setScene(XY.sceneMaps.get(sceneName));
	}

	/**
	 * @param sceneName
	 * @param delay
	 */
	public static void setScene(final String sceneName, float delay){

		Timer.schedule(new Timer.Task(){
			@Override
			public void run(){
				Log.debug("setSceneWithDelay", "Set scene: " + sceneName);
				setScene(sceneName);
			}
		}, delay);
	}

	/**
	 * @return
	 */
	public static Scene getScene(){
		return XY.scene;
	}


	/**
	 *
	 */
	public static void saveScene(){

		if (XY.saveEnable == false){
			Log.debug(TAG, "SaveScene save is disable ");
			return;
		}
		if (XY.cfg.editor == false){
			Log.debug(TAG, "SaveScene editor is disable ");
			return;
		}
		if (XY.currentSceneName == null || XY.currentSceneName.isEmpty()){
			Log.debug(TAG, "SaveScene currentSceneName is empty ");
			return;
		}

		if (XY.scene == null){
			Log.debug(TAG, "Current scene is null ");
			return;
		}

		Log.debug(TAG, "SaveScene sceneName: " + XY.scene.getName());

		XY.saveEnable = false;
		XY.json.setOutputType(JsonWriter.OutputType.minimal);
		StringBuilder sb = new StringBuilder();

		SceneVO data = new SceneVO(XY.scene.data);
		final SnapshotArray<Actor> children = XY.scene.getChildren();

		//
		// Save GUI data
		//
		for (Actor actor : children){
			if (actor != null){
				if (actor instanceof DataVO){
					final ActorVO vo = ((DataVO) actor).getVO();
					if (!vo.fromEditor){
						continue;
					}
				}

				switch (actor.getClass().getName()){
					case "pl.lib2xy.gui.ui.Button":
					case "pl.lib2xy.gui.ui.CheckBox":
					case "pl.lib2xy.gui.ui.Dialog":
					case "pl.lib2xy.gui.ui.FxEffect":
					case "pl.lib2xy.gui.ui.GameArea":
					case "pl.lib2xy.gui.ui.Image":
					case "pl.lib2xy.gui.ui.ImageButton":
					case "pl.lib2xy.gui.ui.ImageTextButton":
					case "pl.lib2xy.gui.ui.Label":
					case "pl.lib2xy.gui.ui.Particle":
					case "pl.lib2xy.gui.ui.ProgressBar":
					case "pl.lib2xy.gui.ui.SelectBox":
					case "pl.lib2xy.gui.ui.Shader":
					case "pl.lib2xy.gui.ui.Slider":
					case "pl.lib2xy.gui.ui.Sprite":
					case "pl.lib2xy.gui.ui.Table":
					case "pl.lib2xy.gui.ui.TextArea":
					case "pl.lib2xy.gui.ui.TextButton":
					case "pl.lib2xy.gui.ui.TextField":
					case "pl.lib2xy.gui.ui.Touchpad":
						Log.debug(TAG, "Save actor: " + actor.getName() + " class: " + actor.getClass().getName());
						sb.append(XY.json.toJson(actor));
						sb.append(lineSeparator);
						break;
					default:
						Log.debug(TAG, "Don't save actor: " + actor.getName() + " class: " + actor.getClass().getName(), 2);
						break;
				}

			}
		}

		String stringGui = sb.toString();
		if (stringGui != null && !stringGui.isEmpty()){
			XY.sceneGuiMaps.put(XY.currentSceneName, stringGui);
			Log.debug(TAG, "GUI: " + XY.sceneGuiMaps.toString());

			//
			// Write GUI file
			//
			String fileGUI = XY.appDir + ResourceManager.CONFIG_FOLDER + XY.currentSceneName.toLowerCase() + "_gui.dat";
			XY.env.writeFile(fileGUI, stringGui);
		}
		//
		// Save scene DATA
		//
		String dataString = XY.json.toJson(data);
		if (dataString != null && !dataString.isEmpty()){
			Log.debug(TAG, "Save scene DATA: " + XY.currentSceneName);
			XY.sceneDataMaps.put(XY.currentSceneName, dataString);
			Log.debug(TAG, "DATA: " + XY.sceneDataMaps.toString());

			//
			// Write DATA file
			//
			String fileDATA = XY.appDir + ResourceManager.CONFIG_FOLDER + XY.currentSceneName.toLowerCase() + "_cfg.dat";
			XY.env.writeFile(fileDATA, dataString);
		}

		XY.saveEnable = true;
	}

	/**
	 *
	 */
	protected void initializeResource(){

		XY.graphics = Gdx.app.getGraphics();
		XY.input = Gdx.app.getInput();
		XY.audio = Gdx.app.getAudio();
		XY.platform = Gdx.app.getType();
		XY.gl = XY.graphics.getGL20();

		//
		// Konfiguracja katalogu aplikacji
		//
		if (Gdx.app.getType() == Application.ApplicationType.Desktop){
			XY.env.testAppDir();
		} else if (Gdx.app.getType() == Application.ApplicationType.Android){
			if (XY.appDir == null){
				XY.appDir = "";
			}
			if (XY.externalDir == null){
				XY.externalDir = "";
			}
		} else {
			if (XY.appDir == null){
				XY.appDir = "";
			}
			if (XY.externalDir == null){
				XY.externalDir = "";
			}
		}

		if (LOG){
			Log.debug(TAG, "App type: " + Gdx.app.getType());
			Log.debug(TAG, "Current dir: " + XY.appDir);
			Log.debug(TAG, "External dir: " + XY.externalDir);
		}


		final FileHandle fileHandle = ResourceManager.getFileHandle(ResourceManager.CONFIG_FOLDER + XY.appName.toLowerCase() + ".cfg");
		if (fileHandle != null && fileHandle.exists()){
			final String readString = fileHandle.readString();
			ResourceManager.readConfig(readString, XY.appName);
		} else {
			XY.cfg.reset();
			XY.cfg.title = XY.appName;
			XY.cfg.projectFile = XY.appName;
		}

		//
		// Preload Scene Data
		//
		Log.debug(TAG, "Preload Scene Data ...");

		for (String sceneName : XY.sceneMaps.keys()){

			if (sceneName != null){
				FileHandle guiFileHandle = ResourceManager.getFileHandle(ResourceManager.CONFIG_FOLDER + sceneName.toLowerCase() + "_gui.dat");
				if (guiFileHandle != null && guiFileHandle.exists()){
					Log.debug(TAG, "Load GUI: " + sceneName);
					String guiString = guiFileHandle.readString();
					XY.sceneGuiMaps.put(sceneName, guiString);
				}

				FileHandle cfgFileHandle = ResourceManager.getFileHandle(ResourceManager.CONFIG_FOLDER + sceneName.toLowerCase() + "_cfg.dat");
				if (cfgFileHandle != null && cfgFileHandle.exists()){
					Log.debug(TAG, "Load CFG: " + sceneName);
					String cfgString = cfgFileHandle.readString();
					XY.sceneDataMaps.put(sceneName, cfgString);
				}
			}
		}

		Serializer.create();
		ResourceManager.create();
		MathUtil.create();
		Lang.create();
	}

	/**
	 *
	 */
	protected void initializeGfx(){
		Screen.create();
		Cfg.setupGrid();
		XY.skin = ResourceManager.getSkin(ResourceManager.ASSET_DATA_FOLDER + ResourceManager.UI_FOLDER + "ui.json");
		XY.stage = new Stage(XY.viewPort);
		XY.postRenderStage = new Stage(XY.viewPort);
		XY.preRenderStage = new Stage(XY.viewPort);
		XY.shapeRenderer = new ShapeRenderer();
		XY.spriteBatch = new SpriteBatch();
		Gfx.initalize(XY.graphics, XY.gl, XY.stage, XY.skin, XY.fixedCamera, XY.shapeRenderer, XY.spriteBatch);
	}

	/**
	 *
	 */
	protected void initializeApp(){

		final GestureDetector gestureDetector = setupGestureDetector();
		final InputProcessor inputProcessor = setupInputProcessor();
		final ClickListener clickListener = setupClickListener();
		if (XY.scene != null){
			setScene(XY.scene);
		}

		//
		// Zapisywanie pamięci po inicjacji aplikacji
		//

		Log.debug(TAG, "- App: -----------------------------");
		Log.debug(TAG, " Java Heap: " + (Gdx.app.getJavaHeap() / 1024 / 1024) + " MB");
		Log.debug(TAG, " Native Heap: " + (Gdx.app.getNativeHeap() / 1024 / 1024) + " MB");
		Log.debug(TAG, " Config: " + XY.cfg.toString());
		Log.debug(TAG, "- App: -----------------------------");

		//
		// Debug utils
		//
		Log.debug(TAG, "Initalize DEBUG data ...");
		Debug.debugSkin = ResourceManager.getSkin(null);
		Debug.debugCamera = new OrthographicCamera(XY.graphics.getWidth(), XY.graphics.getHeight());
		Debug.debugCamera.position.set(XY.graphics.getWidth() / 2, XY.graphics.getHeight() / 2, Debug.debugCamera.position.z);
		Debug.debugCamera.update();
		Debug.debugViewPort = new ScreenViewport(Debug.debugCamera);
		Debug.debugStage = new Stage(Debug.debugViewPort);

		if (XY.cfg.debugGui){
			XY.debug = new Debug();
			XY.debug.setupGUI();
		} else {
			XY.debug = null;
		}

		//
		// Multiplekser setup
		//
		Log.debug(TAG, "Initalize Multiplekser ...");

		XY.stage.addListener(clickListener);
		XY.inputMultiplexer = new InputMultiplexer();
		XY.inputMultiplexer.addProcessor(inputProcessor);
		XY.inputMultiplexer.addProcessor(gestureDetector);
		if (XY.cfg.debugGui){
			XY.inputMultiplexer.addProcessor(Debug.debugStage);
		}
		XY.inputMultiplexer.addProcessor(XY.stage);
		XY.input.setInputProcessor(XY.inputMultiplexer);
	}

	/**
	 *
	 */
	public static void resetCamera(){
		XY.fixedCamera.position.set(XY.centerX, XY.centerY, 0);
		XY.fixedCamera.update();
	}

	/**
	 * Create and setup ClickListener
	 *
	 * @return
	 */
	private ClickListener setupClickListener(){
		return new ClickListener(){

			@Override
			public void clicked(InputEvent event, float x, float y){
				if (XY.cfg.editor){
					if (XY.debug != null){
						final Actor hit = XY.scene.hit((int) x, (int) y);
						if (hit != null){
							XY.debug.onClick(event, hit, (int) x, (int) y);
						}
						XY.scene.onClick(event, (int) x, (int) y);
					}
				} else {

					if (XY.scene != null){
						try{
							final Actor hit = XY.scene.hit((int) x, (int) y);
							if (hit != null){
								XY.scene.onClick(event, hit, (int) x, (int) y);
							}
							XY.scene.onClick(event, (int) x, (int) y);
						} catch (Exception ex){
							Log.error(TAG, "Exception onClick ...", ex);
						}
					}
				}
			}
		};
	}

	/**
	 * Create and setup InputProcessor
	 *
	 * @return
	 */
	private InputProcessor setupInputProcessor(){
		if (LOG){
			Log.debug(TAG, "Init InputProcessor listener  ...");
		}

		return new InputProcessor(){

			@Override
			public boolean touchDragged(int screenX, int screenY, int pointer){

				XY.mouseScreen.x = screenX;
				XY.mouseScreen.y = screenY;

				XY.mouseUnproject.x = screenX;
				XY.mouseUnproject.y = screenY;
				XY.mouseUnproject.z = 0;

				XY.fixedCamera.unproject(XY.mouseUnproject);
				XY.mouse.set(XY.mouseUnproject.x, XY.mouseUnproject.y);

				if (XY.cfg.editor){
					if (XY.debug != null){
						XY.debug.onDrag((int) XY.mouseUnproject.x, (int) XY.mouseUnproject.y);
					}
				} else {

					if (XY.scene != null){
						try{
							XY.scene.onDrag((int) XY.mouseUnproject.x, (int) XY.mouseUnproject.y);
						} catch (Exception ex){
							Log.error(TAG, "Exception onDrag ...", ex);
						}
					}
				}
				return false;
			}

			@Override
			public boolean touchDown(int screenX, int screenY, int pointer, int button){

				XY.mouseScreen.x = screenX;
				XY.mouseScreen.y = screenY;

				XY.mouseUnproject.x = screenX;
				XY.mouseUnproject.y = screenY;
				XY.mouseUnproject.z = 0;

				XY.fixedCamera.unproject(XY.mouseUnproject);
				XY.mouse.set(XY.mouseUnproject.x, XY.mouseUnproject.y);


				if (button == 0){
					XY.mouseButton0.set(XY.mouseUnproject.x, XY.mouseUnproject.y);
				}
				if (button == 1){
					XY.mouseButton1.set(XY.mouseUnproject.x, XY.mouseUnproject.y);
				}
				if (XY.cfg.editor){
					if (XY.debug != null){
						XY.debug.onTouchDown((int) XY.mouseUnproject.x, (int) XY.mouseUnproject.y, button);
					}
				} else {
					if (XY.scene != null){
						try{
							XY.scene.onTouchDown((int) XY.mouseUnproject.x, (int) XY.mouseUnproject.y, button);
						} catch (Exception ex){
							Log.error(TAG, "Exception onTouchDown ...", ex);
						}
					}
				}
				return false;
			}

			@Override
			public boolean touchUp(int screenX, int screenY, int pointer, int button){

				XY.mouseScreen.x = screenX;
				XY.mouseScreen.y = screenY;

				XY.mouseUnproject.x = screenX;
				XY.mouseUnproject.y = screenY;
				XY.mouseUnproject.z = 0;

				XY.fixedCamera.unproject(XY.mouseUnproject);
				XY.mouse.set(XY.mouseUnproject.x, XY.mouseUnproject.y);


				if (XY.cfg.editor){
					if (XY.debug != null){
						XY.debug.onTouchUp((int) XY.mouseUnproject.x, (int) XY.mouseUnproject.y, button);
					}
				} else {
					if (XY.scene != null){
						try{
							XY.scene.onTouchUp((int) XY.mouseUnproject.x, (int) XY.mouseUnproject.y, button);
						} catch (Exception ex){
							Log.error(TAG, "Exception onTouchUp ...", ex);
						}
					}
				}
				return false;
			}

			@Override
			public boolean scrolled(int amount){
				return false;
			}

			@Override
			public boolean mouseMoved(int screenX, int screenY){

				XY.mouseScreen.x = screenX;
				XY.mouseScreen.y = screenY;

				XY.mouseUnproject.x = screenX;
				XY.mouseUnproject.y = screenY;
				XY.mouseUnproject.z = 0;

				XY.fixedCamera.unproject(XY.mouseUnproject);
				XY.mouse.set(XY.mouseUnproject.x, XY.mouseUnproject.y);

				if (XY.cfg.editor){
					if (XY.debug != null){
						XY.debug.onMouseMoved((int) XY.mouseUnproject.x, (int) XY.mouseUnproject.y);
					}
				} else {
					if (XY.scene != null){
						try{
							XY.scene.onMouseMoved((int) XY.mouseUnproject.x, (int) XY.mouseUnproject.y);
						} catch (Exception ex){
							Log.error(TAG, "Exception onMouseMoved ...", ex);
						}
					}
				}
				return false;
			}

			@Override
			public boolean keyUp(int keycode){

				if (XY.cfg.editor){
					if (XY.debug != null){
						XY.debug.onKeyUp(keycode);
					}
				} else {
					if (XY.scene != null){
						try{
							XY.scene.onKeyUp(keycode);
						} catch (Exception ex){
							Log.error(TAG, "Exception onKeyUp ...", ex);
						}
					}
				}
				return false;
			}

			@Override
			public boolean keyTyped(char character){
				return false;
			}

			@Override
			public boolean keyDown(int keycode){
				if (XY.cfg.editor){
					if (XY.debug != null){
						XY.debug.onKeyDown(keycode);
					}
				} else {
					if (XY.scene != null){
						try{
							XY.scene.onKeyDown(keycode);
						} catch (Exception ex){
							Log.error(TAG, "Exception onKeyDown ...", ex);
						}
					}
				}
				return false;
			}
		};
	}

	/**
	 * Create and setup GestureDetector
	 *
	 * @return
	 */
	private GestureDetector setupGestureDetector(){
		if (LOG){
			Log.debug(TAG, "Init GestureDetector listener  ...");
		}

		return new GestureDetector(new GestureDetector.GestureListener(){

			@Override
			public boolean touchDown(float x, float y, int pointer, int button){
				return false;
			}

			@Override
			public boolean zoom(float initialDistance, float distance){
				if (XY.cfg.editor){
					if (XY.debug != null){
						XY.debug.onZoom(initialDistance, distance);
					}
				} else {
					if (XY.scene != null){
						try{
							XY.scene.onZoom(initialDistance, distance);
						} catch (Exception ex){
							Log.error(TAG, "Exception onZoom ...", ex);
						}
					}
				}
				return false;
			}

			@Override
			public boolean tap(float x, float y, int count, int button){
				if (XY.cfg.editor){
					if (XY.debug != null){
						XY.debug.onTap(x, y, count, button);
					}
				} else {
					y = XY.height - y;
					if (XY.scene != null){
						try{
							XY.scene.onTap(x, y, count, button);
						} catch (Exception ex){
							Log.error(TAG, "Exception onTap ...", ex);
						}
					}
				}
				return false;
			}

			@Override
			public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2, Vector2 pointer1, Vector2 pointer2){
				if (XY.cfg.editor){
					if (XY.debug != null){
						XY.debug.onPinch(initialPointer1, initialPointer2, pointer1, pointer2);
					}
				} else {
					if (XY.scene != null){
						try{
							XY.scene.onPinch(initialPointer1, initialPointer2, pointer1, pointer2);
						} catch (Exception ex){
							Log.error(TAG, "Exception onPinch ...", ex);
						}
					}
				}
				return false;
			}

			@Override
			public boolean pan(float x, float y, float deltaX, float deltaY){
				y = XY.height - y;
				if (XY.cfg.editor){
					if (XY.debug != null){
						XY.debug.onPan(x, y, deltaX, deltaY);
					}
				} else {
					if (XY.scene != null){
						try{
							XY.scene.onPan(x, y, deltaX, deltaY);
						} catch (Exception ex){
							Log.error(TAG, "Exception onPan ...", ex);
						}
					}
				}
				return false;
			}

			@Override
			public boolean panStop(float x, float y, int pointer, int button){
				y = XY.height - y;
				if (XY.cfg.editor){
					if (XY.debug != null){
						XY.debug.onPanStop(x, y, pointer, button);
					}
				} else {
					if (XY.scene != null){
						try{
							XY.scene.onPanStop(x, y, pointer, button);
						} catch (Exception ex){
							Log.error(TAG, "Exception onPanStop ...", ex);
						}
					}
				}
				return false;
			}

			@Override
			public boolean longPress(float x, float y){
				y = XY.height - y;
				if (XY.cfg.editor){
					if (XY.debug != null){
						XY.debug.onLongPress(x, y);
					}
				} else {
					if (XY.scene != null){
						try{
							XY.scene.onLongPress(x, y);
						} catch (Exception ex){
							Log.error(TAG, "Exception onLongPress ...", ex);
						}
					}
				}
				return false;
			}

			@Override
			public boolean fling(float velocityX, float velocityY, int button){
				if (XY.cfg.editor){
					if (XY.debug != null){
						XY.debug.onFling(velocityX, velocityY, button);
					}
				} else {
					if (XY.scene != null){
						try{
							XY.scene.onFling(velocityX, velocityY, button);
						} catch (Exception ex){
							Log.error(TAG, "Exception onFling ...", ex);
						}
					}
				}
				return false;
			}
		});
	}
}
