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

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.utils.XmlReader;
import com.badlogic.gdx.utils.XmlWriter;
import pl.lib2xy.app.*;
import pl.lib2xy.gui.GuiForm;

import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

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
		// Test Read lang
		//
		Setting setting2 = new Setting("lang/en");
		HashMap<String, String> hashMap = setting2.getHashMap();
		Set<String> keySet = hashMap.keySet();
		Iterator<String> iterator = keySet.iterator();
		while (iterator.hasNext()){
			String string = iterator.next();
			Log.log(">>>>>>>", string + "=" + hashMap.get(string));
		}

		Log.log(">>>>>>>", Lang.get("lang1"));

		//
		// Test read/write GuiForm
		//
		GuiForm value = new GuiForm(this.getClass().getSimpleName());

		float test1 = 0.5f;
		float test2 = 1.0f;
		float test3 = 2.25f;
		float test4 = -4.23f;
		value.add("test1", "test1", test1, -10, 10, 0.1f, null);
		value.add("test2", "test2", test2, -10, 10, 0.1f, null);
		value.add("test3", "test3", test3, -10, 10, 0.1f, null);
		value.add("test4", "test4", test4, -10, 10, 0.1f, null);

		value.addButtonGroup("btn1", "btn1", new String[]{"b1", "b2", "b3", "b4"}, null);
		value.addButtonGroup("btn2", "btn2", new String[]{"b1", "b2", "b3", "b4"}, null);

		value.addColorList("cs1", "cs1", null);
		value.addColorList("cs2", "cs2", null);

		value.addColorSelect("color1", "color1", Color.WHITE, null);
		value.addColorSelect("color2", "color2", Color.RED, null);
		value.addColorSelect("color3", "color3", Color.BLACK, null);
		value.addColorSelect("color4", "color4", Color.YELLOW, null);
		value.addColorSelect("color5", "color5", Color.CYAN, null);
		value.addColorSelect("color6", "color6", Color.DARK_GRAY, null);
		value.addColorSelect("color7", "color7", Color.LIGHT_GRAY, null);
		value.addColorSelect("color8", "color8", Color.PINK, null);
		value.addColorSelect("color9", "color9", new Color(0.5f, 0.4f, 0.3f, 0.1f), null);

		value.addFileBrowser("file1", "file1", "texture", "texture", null);

		value.addListIndex("list1", "list1", 0, new String[]{"l1", "l2", "l3", "l4"}, null);
		value.addListIndex("list2", "list2", 1, new String[]{"l1", "l2", "l3", "l4"}, null);
		value.addListIndex("list3", "list3", 2, new String[]{"l1", "l2", "l3", "l4"}, null);

		value.addSelectIndex("select1", "select1", 0, new String[]{"s1", "s2", "s3", "s4"}, null);
		value.addSelectIndex("select2", "select2", 2, new String[]{"s1", "s2", "s3", "s4"}, null);
		value.addSelectIndex("select3", "select3", 3, new String[]{"s1", "s2", "s3", "s4"}, null);

		value.addTextField("text3", "text3", "123qaz", "message1", null);

		value.savePresent();

		String fileName = "test/filetest.xml";

		try{
			Log.log("saveXML", "Save XML file: " + fileName);
			FileHandle fsH = ResourceManager.getFileHandle(fileName);

			StringWriter writer = new StringWriter();
			XmlWriter xml = new XmlWriter(writer);
			XmlWriter el = xml.element("sceneManager");

			value.save(el);

			el.pop();

			fsH.writeString(writer.toString(), false);
		} catch (IOException ex){
			Log.error("saveXML", " Exception in Scenemanager: ", ex);
		}


		try{
			XmlReader reader = new XmlReader();
			XmlReader.Element parse = reader.parse(ResourceManager.getFileHandle(fileName));

			value.read(parse);

		} catch (IOException ex){
			Log.error("readXML", " Exception in Scenemanager: ", ex);
		}

		if (value.getFloat("test1") != test1){
			Log.log("testXML", "Error test1", 1);
		}
		if (value.getFloat("test2") != test2){
			Log.log("testXML", "Error test2", 1);
		}
		if (value.getFloat("test3") != test3){
			Log.log("testXML", "Error test3", 1);
		}
		if (value.getFloat("test4") != test4){
			Log.log("testXML", "Error test4", 1);
		}

		if (value.getInt("btn1") != 0){
			Log.log("testXML", "Error btn1", 1);
		}
		if (value.getInt("btn2") != 0){
			Log.log("testXML", "Error btn2", 1);
		}

		if (value.getInt("cs1") != 0){
			Log.log("testXML", "Error cs1", 1);
		}
		if (value.getInt("cs2") != 0){
			Log.log("testXML", "Error cs2", 1);
		}

		if (!value.getColor("color1").equals(Color.WHITE)){
			Log.log("testXML", "Error color1", 1);
		}
		if (!value.getColor("color2").equals(Color.RED)){
			Log.log("testXML", "Error color2", 1);
		}
		if (!value.getColor("color3").equals(Color.BLACK)){
			Log.log("testXML", "Error color3", 1);
		}

		if (!value.getColor("color4").equals(Color.YELLOW)){
			Log.log("testXML", "Error color4", 1);
		}
		if (!value.getColor("color5").equals(Color.CYAN)){
			Log.log("testXML", "Error color5", 1);
		}
		if (!value.getColor("color6").equals(Color.DARK_GRAY)){
			Log.log("testXML", "Error color6", 1);
		}
		if (!value.getColor("color7").equals(Color.LIGHT_GRAY)){
			Log.log("testXML", "Error color7", 1);
		}
		if (!value.getColor("color8").equals(Color.PINK)){
			Log.log("testXML", "Error color8 " + Color.PINK.toString() + " " + value.getColor("color8").toString(), 1);
		}
		if (!value.getColor("color9").equals(new Color(0.5f, 0.4f, 0.3f, 0.1f))){
			Log.log("testXML", "Error color9 " + new Color(0.5f, 0.4f, 0.3f, 0.1f).toString() + " " + value.getColor("color9").toString(), 1);
		}

		if (!value.getString("file1").equals("texture")){
			Log.log("testXML", "Error file1", 1);
		}

		if (value.getInt("list1") != 0){
			Log.log("testXML", "Error list1", 1);
		}
		if (value.getInt("list2") != 1){
			Log.log("testXML", "Error list2", 1);
		}
		if (value.getInt("list3") != 2){
			Log.log("testXML", "Error list3", 1);
		}

		if (value.getInt("select1") != 0){
			Log.log("testXML", "Error select1", 1);
		}
		if (value.getInt("select2") != 2){
			Log.log("testXML", "Error select2", 1);
		}
		if (value.getInt("select3") != 3){
			Log.log("testXML", "Error select3", 1);
		}
		if (!value.getString("text3").equals("123qaz")){
			Log.log("testXML", "Error text3", 1);
		}


		Setting s3 = new Setting("highscore");
		s3.setTopScore();
		s3.setEncodeString("place1", "1000");
		s3.setEncodeString("place2", "800");
		s3.setEncodeString("place3", "700");
		s3.setEncodeString("place4", "600");
		s3.setEncodeString("place5", "500");
		s3.setEncodeString("place6", "400");
		s3.setEncodeString("place7", "300");
		s3.setEncodeString("place8", "200");
		s3.setEncodeString("place9", "100");
		s3.setEncodeString("place10", "0");
		s3.save();

		Setting s32 = new Setting("highscore");
		Log.log("testEncode", s32.getEncodeString("place1"));
		Log.log("testEncode", s32.getEncodeString("place2"));
		Log.log("testEncode", s32.getEncodeString("place3"));
		Log.log("testEncode", s32.getEncodeString("place4"));
		Log.log("testEncode", s32.getEncodeString("place5"));
		Log.log("testEncode", s32.getEncodeString("place6"));
		Log.log("testEncode", s32.getEncodeString("place7"));
		Log.log("testEncode", s32.getEncodeString("place8"));
		Log.log("testEncode", s32.getEncodeString("place9"));
		Log.log("testEncode", s32.getEncodeString("place10"));

		s32.setTopScore();
		HashMap<String, String> topScore = s32.getTopScore();
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

		Setting s64 = new Setting("highscore");
		s64.addTopScore(100);
		s64.addTopScore(130);
		s64.addTopScore(1071);
		s64.addTopScore(150);
		s64.addTopScore(271);
		s64.addTopScore(2455);
		s64.addTopScore(0);
		s64.addTopScore(451);
		s64.addTopScore(123);
		s64.addTopScore(102);
		s64.addTopScore(50);
		s64.addTopScore(2321);
		s64.save();

		topScore = s64.getTopScore();
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
