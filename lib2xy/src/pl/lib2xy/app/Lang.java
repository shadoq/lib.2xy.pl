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

import java.util.HashMap;
import java.util.Map;

/**
 * @author Jarek
 */
public class Lang{


	private final static String TAG = "Lang";

	private static Map enText = new HashMap<String, String>();
	private static Map plText = new HashMap<String, String>();
	//
	// 0 - En
	// 1 - Pl
	//
	private static int currentLang = 0;

	private Lang(){

	}

	/**
	 * @param text
	 * @return
	 */
	public static String get(String text){

		String out = null;
		switch (currentLang){
			default:
				out = (String) enText.get(text);
				break;
			case 1:
				out = (String) plText.get(text);
				break;
		}

		if (out == null){
			out = text;
			Log.debug(TAG, "Don't find text '" + text + "' for lang: " + currentLang, 3);
		}
		return out;
	}

	/**
	 * @return
	 */
	public static int getLang(){
		return currentLang;
	}

	/**
	 * @param lang
	 */
	public static void setLang(int lang){
		Log.log(TAG, "Set lang:" + lang);
		currentLang = lang;
	}

	public static void create(){
		//		S3Setting enLangIni = new S3Setting("lang/en");
		//		enText = enLangIni.getHashMap();
		//		S3Setting plLangIni = new S3Setting("lang/pl");
		//		plText = plLangIni.getHashMap();
		//		S3Log.debug("S3Lang::init", "EN size: " + enText.size() + " PL size: " + plText.size());
	}

	public static void pause(){

	}

	public static void resume(){

	}

	public static void dispose(){

	}

}
