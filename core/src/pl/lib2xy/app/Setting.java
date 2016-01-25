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

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.utils.GdxRuntimeException;
import pl.lib2xy.utils.XorEncrypter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * Class to Read/Write settings ini file,
 * can encode key and data value using XOR encryption
 */
public class Setting{

	private String propFile;
	private Preferences preferences;

	/**
	 * Create setting instance with properties file name,
	 * storage in data scene directory
	 *
	 * @param fileName
	 */
	public Setting(final String fileName){

		if (fileName == null || fileName.equals("")){
			throw new GdxRuntimeException("Wrong INI file Name ...");
		}
		propFile = fileName + ".ini";

		Log.debug("Setting", "Load file: " + propFile);

		preferences = Gdx.app.getPreferences(propFile);
	}

	/**
	 * Save setting data to file
	 */
	public void save(){
		preferences.flush();
	}

	/**
	 * @param key
	 * @param value
	 */
	public void setString(String key, String value){
		preferences.putString(key, value);
	}

	/**
	 * @param key
	 * @param value
	 */
	public void setInt(String key, int value){
		preferences.putInteger(key, value);
	}

	/**
	 * @param key
	 * @param value
	 */
	public void setFloat(String key, float value){
		preferences.putFloat(key, value);
	}

	/**
	 * @param key
	 * @param value
	 */
	public void setEncodeString(String key, String value){
		preferences.putString(XorEncrypter.encode(key), XorEncrypter.encode(value));
	}

	/**
	 * @param key
	 * @return
	 */
	public String getEncodeString(String key){
		return XorEncrypter.encode(preferences.getString(XorEncrypter.encode(key)));
	}

	/**
	 * @param key
	 * @return
	 */
	public String getString(String key){
		return preferences.getString(key);
	}

	/**
	 * @param key
	 * @return
	 */
	public int getInt(String key){
		return preferences.getInteger(key);
	}

	/**
	 * @param key
	 * @return
	 */
	public float getFloat(String key){
		return preferences.getFloat(key);
	}

	/**
	 *
	 */
	public void setTopScore(){
		setTopScore(null);
	}

	/**
	 * @return
	 */
	public HashMap<String, String> getTopScore(){
		HashMap<String, String> score = new HashMap<>(10);
		for (int i = 1; i < 11; i++){
			String keyName = "place" + i;
			String encodeString = getEncodeString(keyName);
			if (encodeString == null){
				encodeString = "0";
				setEncodeString(keyName, "0");
			}
			score.put(keyName, encodeString);
		}
		return score;
	}

	public HashMap<String, String> getTopScoreData(){
		HashMap<String, String> score = new HashMap<>(10);
		for (int i = 1; i < 11; i++){
			String keyName = "data" + i;
			String encodeString = getEncodeString(keyName);
			if (encodeString == null){
				encodeString = "";
				setEncodeString(keyName, "");
			}
			score.put(keyName, encodeString);
		}
		return score;
	}

	/**
	 * @param score
	 */
	public void setTopScore(HashMap<String, String> score){
		if (score == null){
			score = new HashMap<>(10);
			score.put("place1", "0");
			score.put("place2", "0");
			score.put("place3", "0");
			score.put("place4", "0");
			score.put("place5", "0");
			score.put("place6", "0");
			score.put("place7", "0");
			score.put("place8", "0");
			score.put("place9", "0");
			score.put("place10", "0");
		}

		for (int i = 1; i < 11; i++){
			String keyName = "place" + i;
			if (score.containsKey(keyName)){
				score.put(keyName, score.get(keyName));
			} else {
				score.put(keyName, "0");
				setEncodeString(keyName, "0");
			}
		}
		save();
	}

	public void addTopScore(int topScoreValue){
		addTopScore(topScoreValue, null);
	}

	/**
	 * @param topScoreValue
	 */
	public void addTopScore(int topScoreValue, String extendData){
		for (int i = 1; i < 11; i++){
			String keyName = "place" + i;
			String extKeyName = "data" + i;
			String encodeString = getEncodeString(keyName);
			if (encodeString == null){
				encodeString = "0";
			}
			int value = 0;
			try{
				value = Integer.parseInt(encodeString);
			} catch (NumberFormatException ex){
				Log.error("Setting", "Error parse number: " + encodeString, ex);
				return;
			}
			if (topScoreValue > value){
				for (int j = 9; j >= i; j--){
					if (getEncodeString("place" + j) != null){
						setEncodeString("place" + (j + 1), getEncodeString("place" + j));
						if (getEncodeString("data" + j) != null){
							setEncodeString("data" + (j + 1), getEncodeString("data" + j));
						}
					} else {
						setEncodeString("place" + (j + 1), "0");
						setEncodeString("data" + (j + 1), "");
					}
				}
				setEncodeString(keyName, "" + topScoreValue);
				if (extendData != null){
					setEncodeString(extKeyName, extendData);
				}
				break;
			}
		}
	}
}
