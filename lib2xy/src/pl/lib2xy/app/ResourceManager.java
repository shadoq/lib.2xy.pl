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
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.*;
import pl.lib2xy.XY;
import pl.lib2xy.gfx.g3d.shaders.SimpleShader;

import java.io.*;
import java.lang.StringBuilder;
import java.util.Arrays;

/**
 * Main class to read/write and cache application data
 */
public class ResourceManager{

	private static final String TAG = "ResourceManager";
	public static boolean LOG = true;
	public static boolean USE_CACHE_RESOURCE = true;

	public static final String ASSET_FOLDER = "assets/";
	public static final String ASSET_DATA_FOLDER = "assets/";
	public static final String CONFIG_FOLDER = "assets/config/";

	public static final String FONT_FOLDER = "ui/";
	public static final String SHADER_FOLDER = "shader/";
	public static final String SOUND_FOLDER = "sound/";
	public static final String MUSIC_FOLDER = "music/";
	public static final String MAPS_FOLDER = "maps/";
	public static final String UI_FOLDER = "ui/";
	public static final String ATLAS_FOLDER = "atlas/";
	public static final String SPRITE_FOLDER = "sprite/";
	public static final String TEXTURE_FOLDER = "texture/";
	public static final String MODEL_FOLDER = "model/";
	public static final String PARTICLE_FOLDER = "particle/";
	public static final String BIN_FOLDER = "bin/";
	public static final String SOURCE_FOLDER = "src/";
	public static final String SOURCE_SCENE_FOLDER = "scene/";
	public static final String SOURCE_RUN_FOLDER = "run/";
	public static final String LIBRARY_FOLDER = "library/";
	public static final String RESOURCE_FOLDER = "resource/";
	public static final String RESOURCE_ATLAS_FOLDER = "atlas/";
	public static final String RESOURCE_FONT_FOLDER = "font/";
	public static final String RESOURCE_FONT_TTF_FOLDER = "font_ttf/";
	public static final String RESOURCE_MAPS_FOLDER = "maps/";
	public static final String RESOURCE_SKIN_FOLDER = "skin/";
	private static boolean reload = false;
	private static StringBuilder debugString = new StringBuilder();

	private static Skin debugSkin = null;

	private static final String classPatchResource = XY.libDataClassPath;
	private static final String webPatchResource = XY.libDataWebGLPath;

	private static class TextureHandle{
		String fileName;
		Texture texture;
		int width;
		int height;
		int defResolution;
	}

	private static Json json = new Json();
	private static JsonReader jsonReader = new JsonReader();
	private static JsonValue jsonValue;

	public final static ArrayMap<String, String> asset = new ArrayMap<String, String>();

	public final static ArrayMap<String, BitmapFont> bitmapFontResuorce = new ArrayMap<String, BitmapFont>();
	public final static ArrayMap<String, Pixmap> pixmapResuorce = new ArrayMap<String, Pixmap>();
	public final static ArrayMap<String, TextureHandle> textureResuorce = new ArrayMap<String, TextureHandle>();
	public final static ArrayMap<String, TextureRegion> regionResource = new ArrayMap<String, TextureRegion>();
	public final static ArrayMap<String, TextureRegion> regionAnimResource = new ArrayMap<String, TextureRegion>();
	public final static ArrayMap<String, Skin> skinResuorce = new ArrayMap<String, Skin>();
	public final static ArrayMap<String, XmlReader.Element> xmlResuorce = new ArrayMap<String, XmlReader.Element>();
	public final static ArrayMap<String, ShaderProgram> shaderProgramResuorce = new ArrayMap<String, ShaderProgram>();
	public final static ArrayMap<String, String> soundResuorce = new ArrayMap<String, String>();
	public final static ArrayMap<String, SimpleShader> simpleShaderProgramResuorce = new ArrayMap<String, SimpleShader>();

	private static int usedBitmapFont = 0;
	private static int usedPixmap = 0;
	private static int usedTexture = 0;
	private static int usedTextureRegion = 0;

	/**
	 * @param jsonString
	 * @param projectFileName
	 */
	public static void readConfig(String jsonString, String projectFileName){

		XY.cfg.reset();
		jsonValue = jsonReader.parse(jsonString);
		XY.appName = projectFileName;

		if (jsonValue == null){
			XY.cfg.title = projectFileName;
			XY.cfg.projectFile = projectFileName;
			return;
		}

		//
		// App Name and version
		//
		if (jsonValue.has("title")){
			XY.cfg.title = jsonValue.getString("title", projectFileName);
		} else {
			XY.cfg.title = projectFileName;
		}

		if (jsonValue.has("projectFile")){
			XY.cfg.projectFile = jsonValue.getString("projectFile", projectFileName);
		} else {
			XY.cfg.projectFile = projectFileName;
		}

		if (jsonValue.has("version")){
			XY.cfg.version = jsonValue.getString("version");
		} else {
			XY.cfg.version = "";
		}
		if (jsonValue.has("target")){
			XY.cfg.target = jsonValue.getString("target");
		} else {
			XY.cfg.target = "";
		}
		if (jsonValue.has("forceExit")){
			XY.cfg.forceExit = jsonValue.getBoolean("forceExit");
		}


		//
		// Screen config
		//
		if (jsonValue.has("editSize")){
			XY.cfg.editSize = jsonValue.getString("editSize", "800x480");
		}
		if (jsonValue.has("targetSize")){
			XY.cfg.targetSize = jsonValue.getString("targetSize", "800x480");
		}

		if (jsonValue.has("resize")){
			XY.cfg.resize = jsonValue.getBoolean("resize");
		} else {
			XY.cfg.resize = false;
		}

		if (jsonValue.has("fullScreen")){
			XY.cfg.fullScreen = jsonValue.getBoolean("fullScreen");
		} else {
			XY.cfg.fullScreen = false;
		}

		if (jsonValue.has("vSync")){
			XY.cfg.vSync = jsonValue.getBoolean("vSync");
		} else {
			XY.cfg.vSync = true;
		}

		if (jsonValue.has("viewPortType")){
			XY.cfg.viewPortType = Screen.ViewPortType.valueOf(jsonValue.getString("viewPortType", "" + Screen.ViewPortType.STRETCH).toUpperCase());
		} else {
			XY.cfg.viewPortType = Screen.ViewPortType.STRETCH;
		}
		if (jsonValue.has("resolutionType")){
			XY.cfg.resolutionType = Screen.ResolutionType.valueOf(jsonValue.getString("resolutionType", "" + Screen.ResolutionType.MDPI).toUpperCase());
		} else {
			XY.cfg.resolutionType = Screen.ResolutionType.MDPI;
		}

		if (jsonValue.has("virtualScreen")){
			XY.cfg.virtualScreen = jsonValue.getBoolean("virtualScreen");
		} else {
			XY.cfg.virtualScreen = false;
		}

		//
		// Audio Config
		//
		if (jsonValue.has("audioBufferCount")){
			XY.cfg.audioBufferCount = jsonValue.getInt("audioBufferCount");
		} else {
			XY.cfg.audioBufferCount = 10;
		}
		if (jsonValue.has("disableAudio")){
			XY.cfg.disableAudio = jsonValue.getBoolean("disableAudio");
		} else {
			XY.cfg.disableAudio = false;
		}

		//
		// Debug Config
		//
		if (jsonValue.has("log")){
			XY.cfg.debug = jsonValue.getBoolean("log");
		} else {
			XY.cfg.debug = false;
		}
		if (jsonValue.has("logging")){
			XY.cfg.logging = jsonValue.getBoolean("logging");
		} else {
			XY.cfg.logging = false;
		}
		if (jsonValue.has("grid")){
			XY.cfg.grid = jsonValue.getBoolean("grid");
		} else {
			XY.cfg.grid = false;
		}
	}

	/**
	 *
	 */
	public static void writeConfig(){

		Log.debug(TAG, "Write Config: " + XY.cfg.projectFile + ".cfg");

		//
		// Write config file
		//
		String cfgJson = "{\n" +
		"\ttitle: \"" + XY.cfg.title + "\",\n" +
		"\tprojectFile: \"" + XY.cfg.projectFile + "\",\n" +
		"\ttargetSize: \"" + XY.cfg.targetSize + "\",\n" +
		"\teditSize: \"" + XY.cfg.editSize + "\",\n" +
		"\taudioBufferCount: \"" + XY.cfg.audioBufferCount + "\",\n" +
		"\tresize: \"" + XY.cfg.resize + "\",\n" +
		"\tforceExit: \"" + XY.cfg.forceExit + "\",\n" +
		"\tfullScreen: \"" + XY.cfg.fullScreen + "\",\n" +
		"\tvSync: \"" + XY.cfg.vSync + "\",\n" +
		"\tdisableAudio: \"" + XY.cfg.disableAudio + "\",\n" +
		"\tversion: \"" + XY.cfg.version + "\",\n" +
		"\ttarget: \"" + XY.cfg.target + "\",\n" +
		"\tresolutionType: \"" + XY.cfg.resolutionType + "\",\n" +
		"\tvirtualScreen: \"" + XY.cfg.virtualScreen + "\",\n" +
		"\tviewPortType: \"" + XY.cfg.viewPortType + "\",\n" +

		"\tlog: \"" + XY.cfg.debug + "\",\n" +
		"\tlogging: \"" + XY.cfg.logging + "\",\n" +
		"\tgrid: \"" + XY.cfg.grid + "\",\n" +
		"\tdebugGui: \"" + XY.cfg.debugGui + "\",\n" +
		"}";

		String file = XY.appDir + CONFIG_FOLDER + XY.cfg.projectFile.toLowerCase() + ".cfg";
		XY.env.writeFile(file, cfgJson);
		file = XY.appDir + XY.cfg.projectFile.toLowerCase() + ".cfg";
		XY.env.writeFile(file, cfgJson);
	}

	/**
	 *
	 */
	public static void readAssets(){

		asset.clear();
		String file = XY.appDir + CONFIG_FOLDER + XY.cfg.projectFile.toLowerCase() + "_asset.dat";
		Log.debug(TAG, "Read assert def: " + file);
		final FileHandle fileHandle = getFileHandle(file);
		if (fileHandle == null){
			return;
		}
		if (!fileHandle.exists()){
			return;
		}
		final String asstesJson = fileHandle.readString();
		if (asstesJson == null){
			return;
		}
		JsonValue assetValue = jsonReader.parse(asstesJson);
		if (assetValue == null){
			return;
		}
		try{
			for (JsonValue value : assetValue){
				asset.put(value.name, value.asString());
			}
		} catch (SerializationException ex){
			Log.error(TAG, "Error parse data ...", ex);
		}
		Log.debug(TAG, "--------------------------------------");
		Log.debug(TAG, " Asset data: " + asset);
		Log.debug(TAG, "--------------------------------------");
	}

	public static void writeAssets(){
		String file = XY.appDir + CONFIG_FOLDER + XY.cfg.projectFile.toLowerCase() + "_asset.dat";
		Log.debug(TAG, "Write assert def: " + file);
		XY.env.writeFile(file, json.toJson(asset, ArrayMap.class, String.class));
	}


	/**
	 * Load asserts data from Json file, and preloaded gfx and other data
	 */
	public static void preloadAssert(){

		//
		// Load Atlas texture
		//
		if (asset.get("atlas") != null){
			final String[] uis = asset.get("atlas").split("#");

			Log.debug(TAG, "Load Atlas assert: " + Arrays.toString(uis));

			for (String atlas : uis){
				if (!atlas.isEmpty()){

					final FileHandle fileHandle = getFileHandle(ATLAS_FOLDER + atlas);
					Log.debug(TAG, "Load: " + fileHandle.path());
					if (fileHandle.exists()){
						TextureAtlas textureAtlas = new TextureAtlas(fileHandle);
						loadAtlasRegion(textureAtlas);
					} else {
						Log.debug(TAG, "Error load: " + fileHandle.path() + " file not extis ...");
					}
				}
			}
		}

		//
		// Load Bitmap Font
		//
		if (asset.get("fonts") != null){
			final String[] uis = asset.get("fonts").split("#");

			Log.debug(TAG, "Load Bitmap Font assert: " + Arrays.toString(uis));

			for (String font : uis){
				if (!font.isEmpty()){
					getBitmapFont(font);
				}
			}
		}

	}

	/**
	 * @param name
	 * @return
	 */
	public static FileHandle getFileHandle(String name){

		if (name.equals("")){
			return null;
		}

		FileHandle fileHeader;

		//
		// Read for WebGL
		//
		if (Gdx.app.getType() == Application.ApplicationType.WebGL){

			if (name.startsWith("assets/")){
				name = name.replace("assets/", "");
			}
			fileHeader = Gdx.files.internal(name);
			if (fileHeader != null){
				Log.debug(TAG, "WebGL: File extis in internal path: " + fileHeader.path());
				Log.debug(TAG, "WebGL: File internal size: " + fileHeader.length());
				if (fileHeader.length() == 0){
					return null;
				}
				return fileHeader;
			}
			return null;
		} else {
			fileHeader = Gdx.files.absolute(name);
		}
		if (fileHeader != null && fileHeader.exists()){
			Log.debug(TAG, "File extis in absolute path: " + fileHeader.path(), 1);
			return fileHeader;
		}

		//
		// Data -> for Android
		//
		if (Gdx.app.getType() == Application.ApplicationType.Android){

			if (name.startsWith("assets/")){
				name = name.replace("assets/", "");
			}
			if (name.startsWith("data/")){
				Log.debug("OS::getFileHandle", "Set to data path: " + name);
				fileHeader = Gdx.files.internal(name);
			} else {
				Log.debug("OS::getFileHandle", "Set to data path: " + "data/" + name);
				fileHeader = Gdx.files.internal("data/" + name);
			}

			if (fileHeader.exists()){
				Log.debug("OS::getFileHandle", "File extis in " + fileHeader.path());
				return fileHeader;
			}
		}

		if (Gdx.app.getType() == Application.ApplicationType.Desktop){
			if (!name.startsWith("assets/")){
				Log.debug("OS::getFileHandle", "Set PC data path: " + "assets/" + name);
				fileHeader = Gdx.files.internal("assets/" + name);
			}
			if (fileHeader != null && fileHeader.exists()){
				Log.debug(TAG, "File extis in PC data folder: " + fileHeader.path(), 1);
				return fileHeader;
			}
		}

		//
		// Data -> for Android
		//
		if (name.startsWith("assets/")){
			fileHeader = Gdx.files.absolute(XY.appDir + name);
		} else {
			fileHeader = Gdx.files.absolute(XY.appDir + ASSET_DATA_FOLDER + name);
		}
		if (fileHeader != null && fileHeader.exists()){
			Log.debug(TAG, "File extis in assets data folder: " + fileHeader.path(), 1);
			return fileHeader;
		}

		fileHeader = null;
		return fileHeader;
	}

	/**
	 * @param name
	 * @param write
	 * @return
	 */
	public static FileHandle getFileHandle(String name, boolean write){

		if (name.equals("")){
			return null;
		}

		FileHandle fileHeader = Gdx.files.absolute(name);
		if (fileHeader != null && fileHeader.exists()){
			Log.debug(TAG, "File extis in absolute path: " + fileHeader.path(), 1);
			return fileHeader;
		}

		fileHeader = Gdx.files.absolute(XY.appDir + ASSET_DATA_FOLDER + name);
		if (fileHeader != null && fileHeader.exists()){
			Log.debug(TAG, "File extis in assets data folder: " + fileHeader.path(), 1);
			return fileHeader;
		}


		fileHeader = Gdx.files.absolute(XY.externalDir + name);
		if (fileHeader != null && fileHeader.exists()){
			Log.debug(TAG, "File extis in assets data folder: " + fileHeader.path(), 1);
			return fileHeader;
		}
		return fileHeader;
	}


	/**
	 * @param fileName
	 */
	public static void loadAtlas(String fileName){
		final FileHandle fileHandle = getFileHandle(fileName);
		if (fileHandle != null){
			Log.debug(TAG, "Load: " + fileHandle.path());
			if (fileHandle.exists()){
				TextureAtlas textureAtlas = new TextureAtlas(fileHandle);
				loadAtlasRegion(textureAtlas);
			} else {
				Log.debug(TAG, "Error load: " + fileHandle.path() + " file not extis ...");
			}
		}
	}

	/**
	 * @param textureAtlas
	 */
	private static void loadAtlasRegion(TextureAtlas textureAtlas){
		final Array<TextureAtlas.AtlasRegion> regions = textureAtlas.getRegions();
		for (TextureAtlas.AtlasRegion region : regions){

			if (region == null){
				continue;
			}

			Log.debug(TAG, "Load region: " + region.name + " index: " + region.index);

			if (USE_CACHE_RESOURCE){
				if (region.index == -1){
					if (regionResource.containsKey(region.name)){
						regionResource.removeKey(region.name);
					}
					regionResource.put(region.name, region);
				} else {
					if (regionAnimResource.containsKey(region.name + region.index)){
						regionAnimResource.removeKey(region.name + region.index);
					}
					regionAnimResource.put(region.name + region.index, region);
					if (regionResource.containsKey(region.name)){
						regionResource.removeKey(region.name);
					}
					regionResource.put(region.name, region);
				}
			}
		}
	}

	/**
	 * @param fileName
	 * @return
	 */
	public static Texture getTexture(String fileName){

		Texture texture;
		if (!textureResuorce.containsKey(fileName)){

			if (LOG){
				Log.debug(TAG, "Load texture file: " + fileName, 3);
			}

			try{
				FileHandle fileHandle = getFileHandle(fileName);
				texture = new Texture(fileHandle);
				texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

				if (USE_CACHE_RESOURCE){
					if (textureResuorce.containsKey(fileName)){
						textureResuorce.removeKey(fileName);
					}
					TextureHandle textureHeandle = new TextureHandle();
					textureHeandle.fileName = fileName;
					textureHeandle.texture = texture;
					textureHeandle.width = texture.getWidth();
					textureHeandle.height = texture.getHeight();
					textureHeandle.defResolution = 0;
					textureResuorce.put(fileName, textureHeandle);
				}

			} catch (Exception e){
				Log.error(TAG, "Error create texture data ....", e);

				Pixmap pixmap = new Pixmap(Cfg.proceduralTextureSize, Cfg.proceduralTextureSize, Pixmap.Format.RGBA4444);
				pixmap.setColor(Color.RED);
				pixmap.fill();
				texture = new Texture(pixmap);
				texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
				texture.draw(pixmap, 0, 0);
			}
		} else {

			if (LOG){
				Log.debug(TAG, "Load texture from cache: " + fileName, 2);
			}
			TextureHandle textureHandle = textureResuorce.get(fileName);
			texture = textureHandle.texture;
		}
		if (LOG){
			Log.debug(TAG, "Texture size width: " + texture.getWidth() + "px height: " + texture.getHeight());
		}
		usedTexture++;

		return texture;
	}


	/**
	 * Load texture from bitmap file
	 *
	 * @param fileName
	 * @return
	 */
	public static TextureRegion getTextureRegion(String fileName){

		TextureRegion textureRegion;

		if (!regionResource.containsKey(fileName) || reload){

			if (LOG){
				Log.debug(TAG, "Load textureRegion region: " + fileName, 3);
			}

			try{
				FileHandle fileHandle = getFileHandle(fileName);

				if (fileHandle != null){
					textureRegion = new TextureRegion(new Texture(fileHandle));
					textureRegion.getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
					if (USE_CACHE_RESOURCE){
						if (regionResource.containsKey(fileName)){
							regionResource.removeKey(fileName);
						}
						regionResource.put(fileName, textureRegion);
					}
				} else {
					Log.debug(TAG, "TextureRegion not found: " + fileName, 4);
					textureRegion = new TextureRegion(createEmptyTexture());
				}

			} catch (Exception e){
				Log.error("S3ResourceManager::getTexture", "Error create textureRegion data ....", e);

				try{
					textureRegion = new TextureRegion(createEmptyTexture());
				} catch (Exception ex){
					textureRegion = regionResource.firstValue();
				}
			}
		} else {

			if (LOG){
				Log.debug(TAG, "Load textureRegion region from cache: " + fileName, 2);
			}
			textureRegion = regionResource.get(fileName);
		}

		usedTextureRegion++;

		return textureRegion;
	}

	private static Texture createEmptyTexture(){
		Pixmap pixmap = new Pixmap(Cfg.proceduralTextureSize, Cfg.proceduralTextureSize, Pixmap.Format.RGBA4444);
		pixmap.setColor(Color.RED);
		pixmap.fill();
		Texture text2 = new Texture(pixmap);
		text2.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
		text2.draw(pixmap, 0, 0);
		return text2;
	}

	/**
	 * Load texture reagion at index from atlas file
	 *
	 * @param fileName
	 * @param index
	 * @return
	 */
	public static TextureRegion getTextureRegion(String fileName, int index){

		TextureRegion texture = null;
		if (regionAnimResource.containsKey(fileName + index)){
			if (LOG){
				Log.debug(TAG, "Load texture region: " + fileName + " index: " + index, 2);
			}
			texture = regionAnimResource.get(fileName + index);
			if (LOG){
				Log.debug(TAG, "Texture size width: " + texture.getTexture().getWidth() + "px height: " + texture.getTexture().getHeight());
			}
		}
		usedTextureRegion++;
		return texture;
	}

	/**
	 * Load pixmap from bitmap file
	 *
	 * @param fileName
	 * @return
	 */
	public static Pixmap getPixmap(String fileName){

		Pixmap pixmap;

		if (!pixmapResuorce.containsKey(fileName) || reload){

			if (LOG){
				Log.debug(TAG, "Load pixmap file: " + fileName, 3);
			}
			try{
				FileHandle fileHandle = getFileHandle(fileName);
				pixmap = new Pixmap(fileHandle);
				if (USE_CACHE_RESOURCE){
					pixmapResuorce.put(fileName, pixmap);
				}
			} catch (Exception e){
				Log.error("S3ResourceManager::getPixmap", "Error create pixmap data ....", e);
				pixmap = new Pixmap(Cfg.proceduralTextureSize, Cfg.proceduralTextureSize, Pixmap.Format.RGBA4444);
				pixmap.setColor(Color.RED);
				pixmap.fill();
			}
		} else {

			if (LOG){
				Log.debug(TAG, "Load pixmap from cache: " + fileName, 1);
			}
			pixmap = pixmapResuorce.get(fileName);
		}

		if (LOG){
			Log.debug(TAG, "Pixmap size width: " + pixmap.getWidth() + "px height: " + pixmap.getHeight() + "px ", 0);
		}
		usedPixmap++;
		return pixmap;
	}

	/**
	 * Load bitmap font
	 *
	 * @param fileName
	 * @return
	 */
	public static BitmapFont getBitmapFont(String fileName){

		BitmapFont bitmapFont;

		if (!bitmapFontResuorce.containsKey(fileName) || reload){
			if (LOG){
				Log.debug(TAG, "Load Bitmap Font from file: " + fileName, 1);
			}
			FileHandle fileHandle = getFileHandle(FONT_FOLDER + fileName);
			if (fileHandle.exists()){
				bitmapFont = new BitmapFont(fileHandle);
				bitmapFontResuorce.put(fileName, bitmapFont);
			} else {
				bitmapFont = null;
			}
		} else {
			if (LOG){
				Log.debug(TAG, "Load Bitmap Font from cache: " + fileName, 1);
			}
			bitmapFont = bitmapFontResuorce.get(fileName);
		}

		usedBitmapFont++;
		return bitmapFont;
	}

	/**
	 * Load bitmap font
	 *
	 * @param fileName
	 * @return
	 */
	public static BitmapFont getBitmapFontFromClass(String fileName){

		BitmapFont bitmapFont;

		if (!bitmapFontResuorce.containsKey(fileName) || reload){
			if (LOG){
				Log.debug(TAG, "Load Bitmap Font from file: " + fileName, 1);
			}
			FileHandle fileHandle;
			if (Gdx.app.getType() == Application.ApplicationType.WebGL){
				fileHandle = getFileHandle(webPatchResource + "ui/" + fileName);
			} else {
				fileHandle = Gdx.files.classpath(classPatchResource + "ui/" + fileName);
			}
			if (fileHandle != null && fileHandle.exists()){
				bitmapFont = new BitmapFont(fileHandle);
				bitmapFontResuorce.put(fileName, bitmapFont);
			} else {
				bitmapFont = null;
			}
		} else {
			if (LOG){
				Log.debug(TAG, "Load Bitmap Font from cache: " + fileName, 1);
			}
			bitmapFont = bitmapFontResuorce.get(fileName);
		}

		usedBitmapFont++;
		return bitmapFont;
	}

	/**
	 * @param fileName
	 * @return
	 */
	public static Skin getSkin(String fileName){

		if (LOG){
			Log.debug(TAG, "Load Skin: " + fileName, 1);
		}

		Skin skin;
		if (fileName == null){
			Log.debug(TAG, "Load debug Skin ...", 1);
			FileHandle debugSkinFH;
			if (Gdx.app.getType() == Application.ApplicationType.WebGL){
				debugSkinFH = getFileHandle(webPatchResource + "ui/ui.json");
			} else {
				debugSkinFH = Gdx.files.classpath(classPatchResource + "ui/ui.json");
			}
			skin = new Skin(debugSkinFH);

		} else {

			if (!skinResuorce.containsKey(fileName) || reload == true){
				FileHandle fileHandle = getFileHandle(fileName);
				if (LOG){
					Log.debug(TAG, "getSkin", "Load skin from file: " + fileHandle, 3);
				}
				if (fileHandle != null){
					skin = new Skin(fileHandle);
				} else {
					Log.debug(TAG, "Load debug Skin ...", 1);
					FileHandle debugSkinFH;
					if (Gdx.app.getType() == Application.ApplicationType.WebGL){
						debugSkinFH = getFileHandle(webPatchResource + "ui/ui.json");
					} else {
						debugSkinFH = Gdx.files.classpath(classPatchResource + "ui/ui.json");
					}
					skin = new Skin(debugSkinFH);
				}
				if (USE_CACHE_RESOURCE){
					skinResuorce.put(fileName, skin);
				}
			} else {
				if (LOG){
					Log.debug(TAG, "Load skin from cache: " + fileName, 1);
				}
				skin = skinResuorce.get(fileName);
			}
		}
		return skin;
	}

	/**
	 * @param fileName
	 * @return
	 */
	public static XmlReader.Element getXml(String fileName){

		XmlReader.Element xml = new XmlReader.Element(null, null);
		if (!xmlResuorce.containsKey(fileName) || reload){
			if (LOG){
				Log.debug(TAG, "Load XML from file: " + fileName, 1);
			}
			FileHandle fileHandle = getFileHandle(fileName);
			XmlReader reader = new XmlReader();
			try{
				xml = reader.parse(fileHandle);
				if (USE_CACHE_RESOURCE){
					xmlResuorce.put(fileName, xml);
				}
			} catch (IOException ex){
				Log.error(TAG, "Exception in read XML " + fileName, ex);
			}
		} else {
			if (LOG){
				Log.debug(TAG, "Load XML from cache: " + fileName, 1);
			}
			xml = xmlResuorce.get(fileName);
		}
		return xml;
	}

	/**
	 * @param fileName
	 * @return
	 */
	public static ShaderProgram getShaderProgram(String fileName){
		ShaderProgram shaderProgram;
		if (!shaderProgramResuorce.containsKey(fileName) || reload){
			if (LOG){
				Log.debug(TAG, "Load ShaderProgram from file: " + fileName, 3);
			}
			FileHandle vertexHandle = getFileHandle(fileName + ".vertex");
			FileHandle fragmentHandle = getFileHandle(fileName + ".fragment");
			shaderProgram = getShaderProgramFromString(vertexHandle.readString(), fragmentHandle.readString(), fileName);
			if (USE_CACHE_RESOURCE){
				shaderProgramResuorce.put(fileName, shaderProgram);
			}
		} else {
			if (LOG){
				Log.debug(TAG, "Load ShaderProgram from cache: " + fileName, 1);
			}
			shaderProgram = shaderProgramResuorce.get(fileName);
		}
		return shaderProgram;
	}

	/**
	 * @param vertex
	 * @param fragment
	 * @param name
	 * @return
	 */
	public static ShaderProgram getShaderProgramFromString(String vertex, String fragment, String name){

		if (LOG){
			Log.debug(TAG, "Load ShaderProgram from string: " + name, 2);
		}

		ShaderProgram.pedantic = true;
		ShaderProgram shaderProgram = new ShaderProgram(vertex, fragment);

		if (!shaderProgram.isCompiled()){
			Log.error(TAG, "-----------------------------------------");
			Log.error(TAG, " Error compile " + shaderProgram.getLog());
			Log.error(TAG, " fragment:  \n" + vertex);
			Log.error(TAG, " vertex:  \n" + fragment);
			Log.error(TAG, "-----------------------------------------");
		} else {
			if (LOG){
				Log.debug(TAG, "ShaderProgram " + name + " compiled !", 0);
			}
		}
		return shaderProgram;
	}


	public static Animation getAnimation(String textureName, float duration, Array<? extends TextureRegion> keyFrames){
		Animation animation = new Animation(duration, keyFrames);
		return animation;
	}

	public static Animation getAnimation(String textureName, float duration, TextureRegion... keyFrames){
		Animation animation = new Animation(duration, keyFrames);
		return animation;
	}

	private static Animation getAnimation(String textureName, int numberOfFrames, int hOffset){
		// Key frames list
		TextureRegion[] keyFrames = new TextureRegion[numberOfFrames];
		TextureRegion texture = getTextureRegion(textureName);
		int width = texture.getRegionWidth() / numberOfFrames;
		int height = texture.getRegionHeight();
		// Set key frames (each comes from the single texture)
		for (int i = 0; i < numberOfFrames; i++){
			keyFrames[i] = new TextureRegion(texture, width * i, hOffset, width, height);
		}
		Animation animation = new Animation(1f / numberOfFrames, keyFrames);
		return animation;
	}

	private static Animation getAnimation(String textureName, int numberOfFrames, float duration, int hOffset){
		// Key frames list
		TextureRegion[] keyFrames = new TextureRegion[numberOfFrames];
		TextureRegion texture = getTextureRegion(textureName);
		int width = texture.getRegionWidth() / numberOfFrames;
		int height = texture.getRegionHeight();
		// Set key frames (each comes from the single texture)
		for (int i = 0; i < numberOfFrames; i++){
			keyFrames[i] = new TextureRegion(texture, width * i, hOffset, width, height);
		}
		Animation animation = new Animation(duration, keyFrames);
		return animation;
	}

	public static Animation getAnimation(String textureName, int numberOfFrames){
		return getAnimation(textureName, numberOfFrames, 0);
	}

	public static Animation getAnimation(String textureName, int numberOfFrames, float duration){
		return getAnimation(textureName, numberOfFrames, duration, 0);
	}

	public static Animation[] getAnimation(String textureName, int rows, int cols, float duration){
		TextureRegion texture = getTextureRegion(textureName);
		int height = texture.getRegionHeight() / rows;
		Animation[] animations = new Animation[rows];
		for (int i = 0; i < rows; i++){
			animations[i] = getAnimation(textureName, cols, i * height);
		}
		return animations;
	}

	/**
	 * @return
	 */
	public static ArrayMap<String, String> getSceneMap(){

		ArrayMap<String, String> sceneMaps = new ArrayMap<>();

		String mapsFile = CONFIG_FOLDER + XY.cfg.projectFile.toLowerCase() + "_scene.dat";
		if (LOG){
			Log.debug(TAG, "Load maps data: " + mapsFile);
		}
		final FileHandle mapsFileHandle = getFileHandle(mapsFile);

		if (mapsFileHandle != null && mapsFileHandle.exists()){
			try{
				JsonValue sv = jsonReader.parse(mapsFileHandle);
				for (JsonValue jv : sv.iterator()){
					sceneMaps.put(jv.name, jv.asString());
				}
			} catch (SerializationException ex){
				Log.error(TAG, "Error parse data ...", ex);
			}

			Log.debug(TAG, "--------------------------------------");
			Log.debug(TAG, " Scene data: " + sceneMaps);
			Log.debug(TAG, "--------------------------------------");
		} else {
			Log.debug(TAG, "File not extis ..." + mapsFile);
		}

		return sceneMaps;
	}

	/**
	 * @param name
	 * @return
	 */
	public static Image getImageFromClass(String name){

		if (debugSkin == null){
			debugSkin = getSkin(null);
		}
		final TextureRegion textureRegion = debugSkin.getRegion(name);
		final Image image = new Image(textureRegion);

		return image;
	}

	/**
	 * @param name
	 * @return
	 */
	public static Image getIconFromClass(String name){

		if (debugSkin == null){
			debugSkin = getSkin(null);
		}
		final TextureRegion textureRegion = debugSkin.getRegion(name);
		final Image image = new Image(textureRegion);

		image.addListener(new ClickListener(){
			@Override
			public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor){
				super.enter(event, x, y, pointer, fromActor);
				image.setColor(Color.RED);
			}

			@Override
			public void exit(InputEvent event, float x, float y, int pointer, Actor toActor){
				super.exit(event, x, y, pointer, toActor);
				image.setColor(Color.WHITE);
			}
		});

		return image;
	}

	/**
	 * @return
	 */
	public static String[] getBitmapFontList(){
		if (bitmapFontResuorce != null){

			final int length = bitmapFontResuorce.size + 1;
			String[] keys = new String[length];
			ArrayMap.Keys<String> bitmapFont = bitmapFontResuorce.keys();
			keys[0] = "None";
			int i = 1;
			for (String font : bitmapFont){
				keys[i] = font;
				i++;
			}
			return keys;
		}
		return new String[0];
	}

	/**
	 * @return
	 */
	public static String[] getSceneMapList(){
		if (bitmapFontResuorce != null){

			final int length = XY.sceneMaps.size;
			String[] keys = new String[length];
			ArrayMap.Keys<String> scenes = XY.sceneMaps.keys();
			int i = 0;
			for (String scene : scenes){
				keys[i] = scene;
				i++;
			}
			return keys;
		}
		return new String[0];
	}

	/**
	 * @return
	 */
	public static String[] getTextureRegionList(){
		if (regionResource != null){
			final int length = regionResource.size + 1;
			String[] keys = new String[length];
			ArrayMap.Keys<String> textures = regionResource.keys();
			int i = 1;
			keys[0] = "None";
			for (String texture : textures){
				keys[i] = texture.trim();
				i++;
			}
			return keys;
		}
		return new String[0];
	}

	/**
	 * @return
	 */
	public static String[] getShaderList(){

		final String shaders = asset.get("shaders");
		if (shaders != null){
			final String[] shadersName = shaders.split("#");
			final Array<String> shaderKeys = new Array<>(shadersName);
			shaderKeys.insert(0, "None");
			return shaderKeys.toArray(String.class);
		}
		return null;
	}

	public static String[] getMusicList(){

		final String shaders = asset.get("mp3");
		if (shaders != null){
			final String[] shadersName = shaders.split("#");
			final Array<String> shaderKeys = new Array<>(shadersName);
			shaderKeys.insert(0, "None");
			return shaderKeys.toArray(String.class);
		}
		return null;
	}

	public static SimpleShader getShaderData(String shaderName){
		if (shaderName.length() > 0){
			shaderName = shaderName.replace(".json", "");
			final FileHandle fileHandle = getFileHandle("shader/" + shaderName + ".json");
			if (fileHandle.exists()){
				Log.debug("ShaderPanel", "Load shader: " + fileHandle.path());

				final String s = fileHandle.readString();
				final SimpleShader.ShaderData shaderData = XY.json.fromJson(SimpleShader.ShaderData.class, s);

				SimpleShader shader = new SimpleShader(shaderData);
				simpleShaderProgramResuorce.put(shaderName, shader);

				return shader;

			} else {
				return new SimpleShader();
			}

		} else {
			return new SimpleShader();
		}
	}


	/**
	 *
	 */
	public static void clear(){
		if (pixmapResuorce != null){
			pixmapResuorce.clear();
		}
		if (regionResource != null){
			regionResource.clear();
		}
		if (regionAnimResource != null){
			regionAnimResource.clear();
		}
		if (skinResuorce != null){
			skinResuorce.clear();
		}
		if (xmlResuorce != null){
			xmlResuorce.clear();
		}
		if (shaderProgramResuorce != null){
			shaderProgramResuorce.clear();
		}
		if (bitmapFontResuorce != null){
			bitmapFontResuorce.clear();
		}
	}

	/**
	 *
	 */
	public static void create(){

		debugSkin = null;
		readAssets();
		reload = true;
		preloadAssert();
		reload = false;
	}

	/**
	 *
	 */
	public static void pause(){

	}

	/**
	 *
	 */
	public static void resume(){
	}

	/**
	 *
	 */
	public static void dispose(){
		//
		// Dispose ShaderProgram
		//
		if (shaderProgramResuorce != null){
			if (LOG){
				Log.debug(TAG, "Dispose ShaderProgram", 1);
			}
			ArrayMap.Keys<String> keys = shaderProgramResuorce.keys();
			for (String key : keys){
				shaderProgramResuorce.get(key).dispose();
				shaderProgramResuorce.removeKey(key);
			}
			shaderProgramResuorce.clear();
		}
		//
		// Dispose Skin
		//
		if (skinResuorce != null){

			if (LOG){
				Log.debug(TAG, "Dispose skin", 1);
			}
			ArrayMap.Keys<String> keys = skinResuorce.keys();
			for (String key : keys){
				skinResuorce.get(key).dispose();
				skinResuorce.removeKey(key);
			}
			skinResuorce.clear();
		}
		//
		// Dispose SML
		//
		if (xmlResuorce != null){
			if (LOG){
				Log.debug(TAG, "Dispose XML", 1);
			}
			ArrayMap.Keys<String> keys = xmlResuorce.keys();
			for (String key : keys){
				xmlResuorce.removeKey(key);
			}
			xmlResuorce.clear();
		}

		//
		// Dispose BitMap Fonts
		//
		if (bitmapFontResuorce != null){
			if (LOG){
				Log.debug(TAG, "Dispose Bitmap Font", 1);
			}
			ArrayMap.Keys<String> keys = bitmapFontResuorce.keys();
			for (String key : keys){
				bitmapFontResuorce.get(key).dispose();
				bitmapFontResuorce.removeKey(key);
			}
			bitmapFontResuorce.clear();
		}
	}

	public static ArrayMap<String, String> getAsset(){
		return asset;
	}

	public static ArrayMap<String, BitmapFont> getBitmapFontResuorce(){
		return bitmapFontResuorce;
	}

	public static ArrayMap<String, TextureRegion> getRegionResource(){
		return regionResource;
	}

	public static void debugReset(){
		usedBitmapFont = 0;
		usedPixmap = 0;
		usedTexture = 0;
		usedTextureRegion = 0;
	}

	public static String debug(){
		debugString.setLength(0);
		debugString.append(" BFont: ").append(bitmapFontResuorce.size).append("/").append(usedBitmapFont);
		debugString.append(" Pixmap: ").append(pixmapResuorce.size).append("/").append(usedPixmap);
		debugString.append(" Texture: ").append(textureResuorce.size).append("/").append(usedTexture);
		debugString.append(" Region: ").append(regionResource.size).append("/").append(usedTextureRegion);
		debugString.append(" AnimRegion: ").append(regionAnimResource.size);

		return debugString.toString();
	}

	public static void createDirectoryStructure(){

		if (XY.appDir == null || XY.appDir == ""){
			return;
		}

		Log.debug(TAG, "Create project structure: " + XY.appDir);

		XY.env.createAssetsDirectoryStructure();
		XY.env.updateAssets();
		preloadAssert();
	}


	/**
	 * @param directory
	 * @param defaultDir
	 * @return
	 */
	public static String[] getDirectoryList(String directory, String defaultDir){
		return XY.env.getDirectoryList(directory, defaultDir, false, null);
	}

	/**
	 * @param directory
	 * @param defaultDir
	 * @param externalMode
	 * @return
	 */
	public static String[] getDirectoryList(String directory, String defaultDir, boolean externalMode){
		return XY.env.getDirectoryList(directory, defaultDir, false, null);
	}


	/**
	 * @param fileHandle
	 * @return
	 */
	public static String getFileSaveName(FileHandle fileHandle){
		return getFileSaveName(fileHandle.toString());
	}

	/**
	 * @param filePath
	 * @return
	 */
	public static String getFileSaveName(String filePath){

		if (filePath == null){
			return null;
		}

		filePath = filePath.replaceAll("\\\\", "/");

		String[] arr = filePath.split("/");
		String out = filePath;
		if (arr.length > 1){
			out = arr[arr.length - 2] + "/" + arr[arr.length - 1];
		}
		return out;
	}

}
