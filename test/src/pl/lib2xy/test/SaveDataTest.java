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

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.XmlReader;
import pl.lib2xy.app.Log;
import pl.lib2xy.app.ResourceManager;
import pl.lib2xy.app.Scene;
import pl.lib2xy.app.Setting;
import pl.lib2xy.gui.GuiForm;

import java.io.IOException;
import java.util.HashMap;

/**
 * @author Jarek
 */
public class SaveDataTest extends Scene{

	@Override
	public void initialize(){
		//
		// File Internal
		//
		Log.log("SaveDataTest", "File path: internal");
		FileHandle fileHandle = ResourceManager.getFileHandle("font/0.png");
		if (fileHandle != null){
			Log.log("SaveDataTest", "fileHandle.toString: " + fileHandle.toString());
			Log.log("SaveDataTest", "fileHandle.path: " + fileHandle.path());
			Log.log("SaveDataTest", "fileHandle.name: " + fileHandle.name());
		} else {
			Log.log("SaveDataTest", "getFileHandle(\"font/0.png\") is null");
		}

		//
		// Read Internal
		//
		Log.log("SaveDataTest", "Read internal .........");
		fileHandle = ResourceManager.getFileHandle("test/test.txt");
		if (fileHandle != null){
			Log.log("SaveDataTest", "fileHandle.toString: " + fileHandle.toString());
			Log.log("SaveDataTest", "fileHandle.path: " + fileHandle.path());
			Log.log("SaveDataTest", "fileHandle.name: " + fileHandle.name());
			Log.log("SaveDataTest", "fileHandle.exits: " + fileHandle.exists());
			Log.log("SaveDataTest", "fileHandle.read: " + fileHandle.readString());
		} else {
			Log.log("SaveDataTest", "getFileHandle(\"test/test.txt\") is null");
		}

		//
		// Write internal
		//
		Log.log("SaveDataTest", "Internal file is not write .........");
		fileHandle = ResourceManager.getFileHandle("test/test.txt", true);
		if (fileHandle != null){
			Log.log("SaveDataTest", "fileHandle.toString: " + fileHandle.toString());
			Log.log("SaveDataTest", "fileHandle.path: " + fileHandle.path());
			Log.log("SaveDataTest", "fileHandle.name: " + fileHandle.name());
			Log.log("SaveDataTest", "fileHandle.exits: " + fileHandle.exists());
			Log.log("SaveDataTest", "fileHandle.read: " + fileHandle.readString());
		} else {
			Log.log("SaveDataTest", "getFileHandle(\"test/test.txt\") is null");
		}

		//
		// Write external
		//
		Log.log("SaveDataTest", "Write external .........");
		fileHandle = ResourceManager.getFileHandle("test/write.txt", true);
		if (fileHandle != null){
			Log.log("SaveDataTest", "fileHandle.toString: " + fileHandle.toString());
			Log.log("SaveDataTest", "fileHandle.path: " + fileHandle.path());
			Log.log("SaveDataTest", "fileHandle.name: " + fileHandle.name());
			fileHandle.writeString("Testowy string", false);
			fileHandle.writeString("\n random: " + Math.random(), true);
			fileHandle.writeString("\n random: " + Math.random(), true);
			fileHandle.writeString("\n random: " + Math.random(), true);
			fileHandle.writeString("\n random: " + Math.random(), true);
			Log.log("SaveDataTest", "fileHandle.read: " + fileHandle.readString());
		} else {
			Log.log("SaveDataTest", "getFileHandle(\"test/test.txt\") is null");
		}

		Log.log("SaveDataTest", "SaveDataTest end .........");


		//
		// Test Ini
		//
		Log.log("SaveDataTest", "Write ini .........");
		Setting setting = new Setting("test/testini");
		setting.setString("Test", "Taki sobie testowy String :P");
		setting.setString("Test2", "Taki sobie testowy String :P\nWiloczłonowy z polskimi krzaczkami ąśęłśćĄŚĘŁŚĆ");
		setting.setInt("int1", 100);
		setting.setInt("int2", 200);
		setting.setInt("int3", 300);
		setting.setInt("int4", 400);

		setting.setFloat("float1", 1.23f);
		setting.setFloat("float2", 2.23f);
		setting.setFloat("float3", 3.23f);
		setting.setFloat("float4", 4.23f);

		setting.setString("Test", "A teraz jest inny");

		setting.save();

		Log.log("SaveDataTest", "Read ini .........");
		Setting settingTest = new Setting("test/testini");
		if (settingTest.getString("Test").equals("A teraz jest inny")){
			Log.log("Test", "String OK");
		}
		if (settingTest.getInt("int1") == 100){
			Log.log("Test", "int OK");
		}
		if (settingTest.getFloat("float1") == 1.23f){
			Log.log("Test", "float OK");
		}

		//
		// Test read XML
		//
		Log.log("readXML", "Read XML Shader test ......");
		fileHandle = ResourceManager.getFileHandle("shader/empty.xml");
		Log.log(">>>>>>", fileHandle.toString());

		try{
			XmlReader reader = new XmlReader();
			XmlReader.Element parse = reader.parse(fileHandle);

			Log.log("readXML", ">>>>>>> " + parse.getChildCount());
			String shaderName = parse.getChildByName("name").getText();
			String vertexInit = parse.getChildByName("vertex_init").getText();
			String vertexMain = parse.getChildByName("vertex_main").getText();
			String fragmentInit = parse.getChildByName("fragment_init").getText();
			String fragmentMain = parse.getChildByName("fragment_main").getText();
			String fragmentFrag = parse.getChildByName("fragment_frag").getText();

			Log.log("readXML", ">>>>>>> shaderName: " + shaderName);

			Log.log("readXML", ">>>>>>> vertexInit: " + vertexInit);
			Log.log("readXML", ">>>>>>> vertexMain: " + vertexMain);
			Log.log("readXML", ">>>>>>> fragmentInit: " + fragmentInit);
			Log.log("readXML", ">>>>>>> fragmentMain: " + fragmentMain);
			Log.log("readXML", ">>>>>>> fragmentFrag: " + fragmentFrag);

		} catch (IOException e){
			Log.error(this.getClass().getCanonicalName(), "Write internal exception", e);
		}

		//
		// Test read/write GuiForm
		//
		GuiForm value = new GuiForm(this.getClass().getSimpleName());

		float test1 = 0.5f;
		float test2 = 1.0f;
		float test3 = 2.25f;
		float test4 = -4.23f;
		value.addSlider("test1", "test1", test1, -10, 10, 0.1f, null, 1, true);
		value.addSlider("test2", "test2", test2, -10, 10, 0.1f, null, 1, true);
		value.addSlider("test3", "test3", test3, -10, 10, 0.1f, null, 1, true);
		value.addSlider("test4", "test4", test4, -10, 10, 0.1f, null, 1, true);

		value.addButtonGroup("btn1", "btn1", new String[]{"b1", "b2", "b3", "b4"}, null, 1, true);
		value.addButtonGroup("btn2", "btn2", new String[]{"b1", "b2", "b3", "b4"}, null, 1, true);

		value.addColorList("cs1", "cs1", null, 1, true);
		value.addColorList("cs2", "cs2", null, 1, true);

		value.addColorSelect("color1", "color1", Color.WHITE, null, 1, true);
		value.addColorSelect("color2", "color2", Color.RED, null, 1, true);
		value.addColorSelect("color3", "color3", Color.BLACK, null, 1, true);
		value.addColorSelect("color4", "color4", Color.YELLOW, null, 1, true);
		value.addColorSelect("color5", "color5", Color.CYAN, null, 1, true);
		value.addColorSelect("color6", "color6", Color.DARK_GRAY, null, 1, true);
		value.addColorSelect("color7", "color7", Color.LIGHT_GRAY, null, 1, true);
		value.addColorSelect("color8", "color8", Color.PINK, null, 1, true);;
		value.addColorSelect("color9", "color9", new Color(0.5f, 0.4f, 0.3f, 0.1f), null, 1, true);;

		value.addFileBrowser("file1", "file1", "texture", "texture", null, 1, true);

		value.addListIndex("list1", "list1", 0, new String[]{"l1", "l2", "l3", "l4"}, null, 1, true);
		value.addListIndex("list2", "list2", 1, new String[]{"l1", "l2", "l3", "l4"}, null, 1, true);
		value.addListIndex("list3", "list3", 2, new String[]{"l1", "l2", "l3", "l4"}, null, 1, true);

		value.addSelectIndex("select1", "select1", 0, new String[]{"s1", "s2", "highscore", "s4"}, null, 1, true);
		value.addSelectIndex("select2", "select2", 2, new String[]{"s1", "s2", "highscore", "s4"}, null, 1, true);
		value.addSelectIndex("select3", "select3", 3, new String[]{"s1", "s2", "highscore", "s4"}, null, 1, true);

		value.addTextField("text3", "text3", "123qaz", "message1", null, 1, true);

		value.addTextField("text3", "text3", "123qaz", "message1", null, 1, true);
		value.addTextField("text3", "text3", "123qaz", "message1", null, 1, true);

		Setting highscore = new Setting("highscore");
		highscore.setTopScore();
		highscore.setEncodeString("place1", "1000");
		highscore.setEncodeString("place2", "800");
		highscore.setEncodeString("place3", "700");
		highscore.setEncodeString("place4", "600");
		highscore.setEncodeString("place5", "500");
		highscore.setEncodeString("place6", "400");
		highscore.setEncodeString("place7", "300");
		highscore.setEncodeString("place8", "200");
		highscore.setEncodeString("place9", "100");
		highscore.setEncodeString("place10", "0");
		highscore.save();

		Setting highscore1 = new Setting("highscore");
		Log.log("testEncode", highscore1.getEncodeString("place1"));
		Log.log("testEncode", highscore1.getEncodeString("place2"));
		Log.log("testEncode", highscore1.getEncodeString("place3"));
		Log.log("testEncode", highscore1.getEncodeString("place4"));
		Log.log("testEncode", highscore1.getEncodeString("place5"));
		Log.log("testEncode", highscore1.getEncodeString("place6"));
		Log.log("testEncode", highscore1.getEncodeString("place7"));
		Log.log("testEncode", highscore1.getEncodeString("place8"));
		Log.log("testEncode", highscore1.getEncodeString("place9"));
		Log.log("testEncode", highscore1.getEncodeString("place10"));

		highscore1.setTopScore();
		HashMap<String, String> topScore = highscore1.getTopScore();
		Log.log("topScore", topScore.get("place1"));
		Log.log("topScore", topScore.get("place2"));
		Log.log("topScore", topScore.get("place3"));
		Log.log("topScore", topScore.get("place4"));
		Log.log("topScore", topScore.get("place5"));
		Log.log("topScore", topScore.get("place6"));
		Log.log("topScore", topScore.get("place7"));
		Log.log("topScore", topScore.get("place8"));
		Log.log("topScore", topScore.get("place9"));
		Log.log("topScore", topScore.get("place10"));

		Setting highscore2 = new Setting("highscore");
		highscore2.addTopScore(100);
		highscore2.addTopScore(130);
		highscore2.addTopScore(1071);
		highscore2.addTopScore(150);
		highscore2.addTopScore(271);
		highscore2.addTopScore(2455);
		highscore2.addTopScore(0);
		highscore2.addTopScore(451);
		highscore2.addTopScore(123);
		highscore2.addTopScore(102);
		highscore2.addTopScore(50);
		highscore2.addTopScore(2321);
		highscore2.save();

		topScore = highscore2.getTopScore();
		Log.log("topScore - place1", topScore.get("place1"));
		Log.log("topScore - place2", topScore.get("place2"));
		Log.log("topScore - place3", topScore.get("place3"));
		Log.log("topScore - place4", topScore.get("place4"));
		Log.log("topScore - place5", topScore.get("place5"));
		Log.log("topScore - place6", topScore.get("place6"));
		Log.log("topScore - place7", topScore.get("place7"));
		Log.log("topScore - place8", topScore.get("place8"));
		Log.log("topScore - place9", topScore.get("place9"));
		Log.log("topScore - place10", topScore.get("place10"));
	}

}
