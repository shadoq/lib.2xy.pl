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
package pl.lib2xy.desktop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Array;
import pl.lib2xy.XY;
import pl.lib2xy.app.Log;
import pl.lib2xy.app.ResourceManager;

import java.io.*;
import java.util.LinkedList;

public class SystemEmulator extends pl.lib2xy.app.SystemEmulator{

	private static final String TAG = "DesktopSystemEmulator";

	/**
	 * @param filename
	 * @param data
	 */
	public void writeFile(String filename, String data){

		Log.debug(TAG, "Write file: " + filename);
		File file = new File(filename);
		try{
			file.createNewFile();
			FileOutputStream fos = new FileOutputStream(file);
			fos.write(data.getBytes());
			fos.close();
		} catch (IOException ex){
			Log.error(TAG, "Error write file: " + filename, ex);
		}
	}

	/**
	 *
	 */
	public void updateAssets(){

		Log.debug(TAG, "Update Asset ...");
		ResourceManager.asset.clear();

		//
		// Assert UI
		//
		StringBuilder sb;
		sb = new StringBuilder();
		for (String filename : new File(XY.appDir + ResourceManager.ASSET_DATA_FOLDER + ResourceManager.UI_FOLDER).list()){
			if (filename.endsWith(".atlas")){
				Log.debug(TAG, "Update asset UI: " + filename);
				sb.append(filename);
				sb.append("#");
			}
		}
		ResourceManager.asset.put("ui", sb.toString());

		//
		// Assert Atlas
		//
		sb = new StringBuilder();
		for (String filename : new File(XY.appDir + ResourceManager.ASSET_DATA_FOLDER + ResourceManager.ATLAS_FOLDER).list()){
			if (filename.endsWith(".atlas")){
				Log.debug(TAG, "Update asset ATLAS: " + filename);
				sb.append(filename);
				sb.append("#");
			}
		}
		ResourceManager.asset.put("atlas", sb.toString());

		//
		// Mp3 Files
		//
		sb = new StringBuilder();
		for (String filename : new File(XY.appDir + ResourceManager.ASSET_DATA_FOLDER + ResourceManager.MUSIC_FOLDER).list()){
			if (filename.endsWith(".mp3")){
				Log.debug(TAG, "Update asset MP3: " + filename);
				sb.append(filename);
				sb.append("#");
			}
		}
		ResourceManager.asset.put("mp3", sb.toString());

		//
		// Fonts Files
		//
		sb = new StringBuilder();
		for (String filename : new File(XY.appDir + ResourceManager.ASSET_DATA_FOLDER + ResourceManager.UI_FOLDER).list()){
			if (filename.endsWith(".fnt")){
				Log.debug(TAG, "Update Bitmap Font: " + filename);
				sb.append(filename);
				sb.append("#");
			}
		}
		ResourceManager.asset.put("fonts", sb.toString());

		//
		// Shader Files
		//
		sb = new StringBuilder();
		for (String filename : new File(XY.appDir + ResourceManager.ASSET_DATA_FOLDER + ResourceManager.SHADER_FOLDER).list()){
			if (filename.endsWith(".json")){
				Log.debug(TAG, "Update Shader JSON: " + filename);
				sb.append(filename);
				sb.append("#");
			}
		}
		ResourceManager.asset.put("shaders", sb.toString());
	}

	/**
	 * Pobiera katalog dysku zwraca w postaci tablicy stringów
	 *
	 * @param directory - katalog do odczytania
	 * @return - tablica plikow danego katalogu
	 */
	public String[] getDirectoryList(String directory, String defaultDir, boolean externalMode, final String[] extensionMask){

		FilenameFilter filter = new FilenameFilter(){
			@Override
			public boolean accept(File dir, String name){
				if (name.startsWith(".")){
					return false;
				}
				if (name.equalsIgnoreCase("res.dat")){
					return false;
				}
				if (name.equalsIgnoreCase("_meta")){
					return false;
				}
				if (dir.isFile()){
					return false;
				}

				if (extensionMask != null){
					boolean flag = false;
					for (String ext : extensionMask){
						if (name.contains(ext)){
							flag = true;
						}
					}
					return flag;
				}
				return true;
			}
		};

		FilenameFilter filterDir = new FilenameFilter(){
			@Override
			public boolean accept(File dir, String name){
				if (name.equalsIgnoreCase("..")){
					return true;
				}
				if (name.startsWith(".")){
					return false;
				}
				if (name.equalsIgnoreCase("res.dat")){
					return false;
				}
				if (name.equalsIgnoreCase("_meta")){
					return false;
				}
				if (dir.isFile()){
					return false;
				}
				if (extensionMask != null){
					if (!dir.isFile()){
						return true;
					}
					boolean flag = false;
					for (String ext : extensionMask){
						if (name.contains(ext)){
							flag = true;
						}
					}
					return flag;
				}
				return true;
			}
		};

		Log.log("getDirectoryList::getDir", "currentDir:" + directory + " defaultDir:" + defaultDir + " externalMode: " + externalMode, 1);

		String[] metaDirInfo = null;
		String[] out = null;
		String[] children = null;
		String[] childrenInternal = null;

		if (externalMode){

			FileHandle directoryHandle = Gdx.files.absolute(directory);
			Log.log("getDirectoryList::getDir", "Read external DIR part 1: " + directoryHandle.toString(), 3);

			String extension = directoryHandle.extension();
			if ( //
				//
				//
			extension.equalsIgnoreCase("png")
			|| extension.equalsIgnoreCase("jpg")
			|| extension.equalsIgnoreCase("gif")
			|| extension.equalsIgnoreCase("tif")
			|| extension.equalsIgnoreCase("bmp")
			|| extension.equalsIgnoreCase("jpeg")
			|| extension.equalsIgnoreCase("tga")
			|| //
			//
			//
			extension.equalsIgnoreCase("txt")
			|| extension.equalsIgnoreCase("xml")
			|| extension.equalsIgnoreCase("html")
			|| extension.equalsIgnoreCase("htm")
			|| extension.equalsIgnoreCase("rtf")
			|| extension.equalsIgnoreCase("pdf")
			|| extension.equalsIgnoreCase("epub")
			|| //
			//
			//
			extension.equalsIgnoreCase("avi")
			|| extension.equalsIgnoreCase("mpg")
			|| extension.equalsIgnoreCase("wmf")
			|| //
			//
			//
			extension.equalsIgnoreCase("mp3")
			|| extension.equalsIgnoreCase("ogg")
			|| //
			//
			//
			extension.equalsIgnoreCase("exe")
			|| extension.equalsIgnoreCase("com")
			|| extension.equalsIgnoreCase("zip")
			|| extension.equalsIgnoreCase("rar")
			|| extension.equalsIgnoreCase("bz2")){
				directory = directoryHandle.parent().path();
				directoryHandle = ResourceManager.getFileHandle(directory);
			}

			Log.log("getDirectoryList::getDir", "Read external DIR part 2: " + directoryHandle.toString(), 3);

			File dir = directoryHandle.file();
			children = dir.list(filterDir);

			FileHandle fh;
			if (children != null){
				for (int i = 0; i < children.length; i++){
					String string = children[i];
					fh = Gdx.files.absolute(string);
					if (fh.isDirectory()){
						children[i] = "<Dir> " + string;
					}
				}
			}

		} else {
			//
			// Odczyt plików internal
			//
			if (!defaultDir.equalsIgnoreCase("")){
				String metaDir = defaultDir;
				FileHandle metaFileHandle;
				metaFileHandle = ResourceManager.getFileHandle(metaDir);

				if (!metaFileHandle.extension().equalsIgnoreCase("")){
					metaDir = metaFileHandle.parent().path() + "/_meta";
				} else if (metaFileHandle.isDirectory()){
					metaDir = metaDir + "/_meta";
				} else {
					if (metaDir.startsWith("data/")){
						metaDir = metaDir + "/_meta";
					} else {
						metaDir = "data/" + metaDir + "/_meta";
					}
				}

				Log.log("getDirectoryList::getDir", "Prepare to read _meta dir info >>>>>>>>>>>>>> " + metaDir.toString(), 3);

				//
				// Odczyt pliku _meta dla wewnętrznego katalogu
				//
				metaFileHandle = ResourceManager.getFileHandle(metaDir);
				if (metaFileHandle != null && metaFileHandle.exists()){

					try{
						String readString = metaFileHandle.readString();
						if (readString != null){
							if (readString.length() > 10){

								String[] lines = readString.split("\n");
								LinkedList<String> list = new LinkedList<String>();
								for (int i = 0; i < lines.length; i++){
									String line = lines[i].trim();
									if (!line.equalsIgnoreCase("_meta") && !line.equalsIgnoreCase(".") && !line.equalsIgnoreCase("..") && line.length() > 1){
										list.add(line);
									}
								}
								metaDirInfo = list.toArray(new String[list.size()]);
							}
						}

					} catch (Exception ex){
						Log.error(System.class.getName(), "Error Read _meta data ...", ex);
					}
				}
			}

			//
			// Odczyt fizycznego katalogu (zewnętrznego)
			//
			FileHandle directoryHandle = ResourceManager.getFileHandle(directory);
			if (directoryHandle != null){

				Log.log("getDirectoryList::getDir", "Prepare to read external dir info >>>>>>>>>>>>>> " + directoryHandle.toString(), 3);

				if (!directoryHandle.extension().equalsIgnoreCase("")){
					directory = directoryHandle.parent().path();
					directoryHandle = ResourceManager.getFileHandle(directory);
				}
				File dir = directoryHandle.file();
				children = dir.list(filter);
			}

			//
			// Odczyt fizycznego katalogu (wewnętrznego)
			//
			directoryHandle = ResourceManager.getFileHandle(directory);
			if (directoryHandle != null){
				Log.log("getDirectoryList::getDir", "Prepare to read internal dir info >>>>>>>>>>>>>> " + directoryHandle.toString(), 3);
				if (!directoryHandle.extension().equalsIgnoreCase("")){
					directory = directoryHandle.parent().path();
					directoryHandle = ResourceManager.getFileHandle(directory);
				}
				File dir = directoryHandle.file();
				childrenInternal = dir.list(filter);
			}
		}

		//
		// Łączenie wyników
		//
		Array<String> merageArray = new Array();
		if (metaDirInfo != null){
			merageArray.addAll(metaDirInfo);
		}
		if (children != null){
			for (int i = 0; i < children.length; i++){
				if (!merageArray.contains(children[i], false)){
					merageArray.add(children[i]);
				}
			}
		}
		if (childrenInternal != null){
			for (int i = 0; i < childrenInternal.length; i++){
				if (!merageArray.contains(childrenInternal[i], false)){
					merageArray.add(childrenInternal[i]);
				}
			}
		}

		merageArray.sort();
		out = new String[merageArray.size];
		for (int i = 0; i < merageArray.size; i++){
			if (!merageArray.get(i).equalsIgnoreCase("_meta")){
				out[i] = merageArray.get(i);
			}
		}

		if (out == null){
			return new String[]{};
		}
		return out;
	}

	public void testAppDir(){

		if (XY.appDir == null){
			try{
				File dir1 = new File(".");
				XY.appDir = dir1.getCanonicalPath() + "\\";
			} catch (Exception e){
				if (this.getClass().getClassLoader().getResource("") != null){
					XY.appDir = this.getClass().getClassLoader().getResource("").getPath();
				}
			}
		}

		if (XY.externalDir == null){
			XY.externalDir = Gdx.files.getExternalStoragePath() + XY.appName + "\\";

			final FileHandle extDir = Gdx.files.absolute(XY.externalDir);
			if (extDir != null && !extDir.exists()){
				extDir.mkdirs();
			}
		}
	}

	/**
	 *
	 */
	public void createAssetsDirectoryStructure(){

		new File(XY.appDir + ResourceManager.ASSET_FOLDER).mkdir();
		new File(XY.appDir + ResourceManager.ASSET_DATA_FOLDER).mkdir();
		new File(XY.appDir + ResourceManager.CONFIG_FOLDER).mkdir();

		new File(XY.appDir + ResourceManager.ASSET_DATA_FOLDER + ResourceManager.FONT_FOLDER).mkdir();
		new File(XY.appDir + ResourceManager.ASSET_DATA_FOLDER + ResourceManager.SHADER_FOLDER).mkdir();
		new File(XY.appDir + ResourceManager.ASSET_DATA_FOLDER + ResourceManager.SOUND_FOLDER).mkdir();
		new File(XY.appDir + ResourceManager.ASSET_DATA_FOLDER + ResourceManager.MUSIC_FOLDER).mkdir();
		new File(XY.appDir + ResourceManager.ASSET_DATA_FOLDER + ResourceManager.MAPS_FOLDER).mkdir();
		new File(XY.appDir + ResourceManager.ASSET_DATA_FOLDER + ResourceManager.UI_FOLDER).mkdir();

		new File(XY.appDir + ResourceManager.ASSET_DATA_FOLDER + ResourceManager.ATLAS_FOLDER).mkdir();
		new File(XY.appDir + ResourceManager.ASSET_DATA_FOLDER + ResourceManager.SPRITE_FOLDER).mkdir();
		new File(XY.appDir + ResourceManager.ASSET_DATA_FOLDER + ResourceManager.TEXTURE_FOLDER).mkdir();
		new File(XY.appDir + ResourceManager.ASSET_DATA_FOLDER + ResourceManager.MODEL_FOLDER).mkdir();
		new File(XY.appDir + ResourceManager.ASSET_DATA_FOLDER + ResourceManager.PARTICLE_FOLDER).mkdir();

		new File(XY.appDir + ResourceManager.RESOURCE_FOLDER).mkdir();
		new File(XY.appDir + ResourceManager.RESOURCE_FOLDER + ResourceManager.RESOURCE_ATLAS_FOLDER).mkdir();
		new File(XY.appDir + ResourceManager.RESOURCE_FOLDER + ResourceManager.RESOURCE_FONT_FOLDER).mkdir();
		new File(XY.appDir + ResourceManager.RESOURCE_FOLDER + ResourceManager.RESOURCE_FONT_TTF_FOLDER).mkdir();
		new File(XY.appDir + ResourceManager.RESOURCE_FOLDER + ResourceManager.RESOURCE_MAPS_FOLDER).mkdir();
		new File(XY.appDir + ResourceManager.RESOURCE_FOLDER + ResourceManager.RESOURCE_SKIN_FOLDER).mkdir();

		new File(XY.appDir + ResourceManager.SOURCE_FOLDER).mkdir();
		new File(XY.appDir + ResourceManager.SOURCE_FOLDER + ResourceManager.SOURCE_RUN_FOLDER).mkdir();
		new File(XY.appDir + ResourceManager.SOURCE_FOLDER + ResourceManager.SOURCE_SCENE_FOLDER).mkdir();

		//
		// Create resource file
		//
		String[] uiFiles = new String[]{
		"fonts.png",
		"ui.png",
		"ui.json",
		"ui.atlas",
		"thin-32.fnt",
		"thin-28.fnt",
		"thin-22.fnt",
		"thin-18.fnt",
		"thin-16.fnt",
		"thin-14.fnt",
		"thin-12.fnt",
		"thin-12.fnt",
		"thin-12.fnt",
		"thin-10.fnt",
		"thin-8.fnt",
		"regular-32.fnt",
		"regular-28.fnt",
		"regular-22.fnt",
		"regular-18.fnt",
		"regular-16.fnt",
		"regular-14.fnt",
		"regular-12.fnt",
		"regular-12.fnt",
		"regular-12.fnt",
		"regular-10.fnt",
		"regular-8.fnt"
		};

		for (String file : uiFiles){

			String destFile = XY.appDir + ResourceManager.ASSET_DATA_FOLDER + ResourceManager.UI_FOLDER + file;

			if (!fileExtis(destFile)){
				final byte[] bytes = Gdx.files.classpath(XY.libDataClassPath + "/ui/" + file).readBytes();
				if (bytes != null){
					FileHandle fileHandle = Gdx.files.absolute(destFile);
					if (fileHandle != null){
						fileHandle.writeBytes(bytes, false);
					}
				}
			}
		}

	}

	/**
	 * @param filename
	 * @return
	 */
	public static String readFile(String filename){

		Log.debug(TAG, "Read file: " + filename);
		StringBuffer sb = new StringBuffer();
		File file = new File(filename);
		if (!file.exists()){
			return "";
		}
		BufferedReader br = null;
		try{
			FileReader fr = new FileReader(file);
			br = new BufferedReader(fr);
			String line;
			while ((line = br.readLine()) != null){
				sb.append(line);
				sb.append(System.lineSeparator());
			}
			br.close();
			fr.close();
		} catch (IOException ex){
			Log.error(TAG, "Error read file: " + filename, ex);
		}
		return sb.toString();
	}

	/**
	 * @param filename
	 * @return
	 */
	public static String readFileFromClassPath(String filename){
		Log.debug(TAG, "Read file form class: " + filename);
		StringBuffer sb = new StringBuffer();
		try{
			BufferedReader br = new BufferedReader(new InputStreamReader(
			ResourceManager.class.getClassLoader().getResourceAsStream(XY.libDataClassPath + filename)));
			for (int c = br.read(); c != -1; c = br.read()){
				sb.append((char) c);
			}
		} catch (IOException e){
			e.printStackTrace();
		}
		return sb.toString();
	}

	/**
	 * @param filename
	 * @return
	 */
	public static boolean fileExtis(String filename){
		Log.debug(TAG, "Write file: " + filename);
		File file = new File(filename);
		return file.exists();
	}

	/**
	 *
	 */
	public void frameRateLimit(){

		if (XY.cfg.frameRateLimit > 0){
			try{
				if (1000 / XY.cfg.frameRateLimit - XY.deltaTime > 0){
					Thread.sleep((long) (1000 / XY.cfg.frameRateLimit - XY.deltaTime));
				}
			} catch (InterruptedException e){
				e.printStackTrace();
			}
		}

	}

}
