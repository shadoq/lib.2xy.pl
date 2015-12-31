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
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import pl.lib2xy.XY;
import pl.lib2xy.app.Cfg;
import pl.lib2xy.app.Log;
import pl.lib2xy.app.Scene;
import pl.lib2xy.gui.GuiForm;
import pl.lib2xy.gui.GuiResource;


/**
 * @author Jarek
 */
public class GuiFormTest extends Scene{

	//
	// Test value
	//
	public Float testFloat = 5f;
	public Integer testInteger = 40;
	public Boolean testBoolean = false;
	public String testListString = "";
	public Color testColor = Color.BLACK;
	public String testDir = "";
	protected float start;
	protected float duration = 10;
	protected float localTime = 0;
	protected float sceneTime = 0;
	protected float endTime = 0;
	protected float procent = 0;
	protected GuiForm guiForm;
	protected GuiForm guiForm2;

	@Override
	public void initialize(){

		String[] listItem = {"Pierwsza opcja", "Druga opcja", "Trzecia opcja", "Czwarta opcja"};

		guiForm = new GuiForm(this.getClass().getSimpleName());
		guiForm2 = new GuiForm(this.getClass().getSimpleName());

		ChangeListener valLitsener = new ChangeListener(){
			@Override
			public void changed(ChangeEvent event, Actor actor){
				Log.log("valueChangeListener", "TestValue1: " + guiForm.getFloat("testValue1"));
				Log.log("valueChangeListener", "TestValue2: " + guiForm.getFloat("testValue2"));
				Log.log("valueChangeListener", "TestValue3: " + guiForm.getFloat("testValue3"));
				Log.log("valueChangeListener", "TestValue4: " + guiForm.getFloat("testValue4"));
			}
		};

		guiForm.addFileBrowser("testListString", "diffuseTextureName", testListString, "texture", null);

		guiForm.add("testValue1", "Test Value 1", 0, 0, 1, 0.1f, valLitsener);
		guiForm.add("testValue2", "Test Value 2", 0, 0, 10, 1f, valLitsener);
		guiForm.add("testValue3", "Test Value 3", 0, -10, 10, 1f, valLitsener);
		guiForm.add("testValue4", "Test Value 4", 0, -100, 111, 1f, valLitsener);
		guiForm.addEditorBox("testValue4", "", 200, 200, 300, 100, valLitsener);

		guiForm.addSelectIndex("selectIndex", "Test select index", 0, listItem, valLitsener);
		guiForm.addColorList("colorIndex", "Color index text", valLitsener);

		guiForm2.add("testValue1", "Test Value 1", 0, 0, 1, 0.1f, valLitsener);
		guiForm2.addCheckBox("testBool 1", "Test boolean 1", false, valLitsener);
		guiForm2.addCheckBox("testBool 2", "Test boolean 2", true, valLitsener);
		guiForm2.addCheckBox("testBool 3", "Test boolean 3", false, valLitsener);

		guiForm2.addImageList("imageList", "Image List", new String[]{"sprite/bobs0.png", "sprite/bobs1.png", "sprite/bobs2.png"}, valLitsener);

		Window window = GuiResource.window("Test Effect", "window");
		window.defaults().align(Align.top | Align.left);
		window.row();
		window.add(guiForm.getScrollPane()).height(Cfg.gridY24).width(Cfg.gridY12);
		window.add(guiForm2.getScrollPane()).height(Cfg.gridY24).width(Cfg.gridY12);
		window.pack();

		addActor(window);
	}

}
