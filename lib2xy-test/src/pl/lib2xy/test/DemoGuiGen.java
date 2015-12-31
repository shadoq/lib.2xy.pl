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
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import pl.lib2xy.XY;
import pl.lib2xy.app.Cfg;
import pl.lib2xy.app.Scene;
import pl.lib2xy.gui.Gui;
import pl.lib2xy.gui.GuiResource;

/**
 * @author Jarek
 */
public class DemoGuiGen extends Scene{

	//
	// Test value
	//
	public Float testFloat = 5f;
	public Integer testInteger = 40;
	public Boolean testBoolean = false;
	public String testListString = "";
	public Color testColor = Color.BLACK;
	public String testDir = "";
	public String[] imageList;
	protected float start;
	protected float duration = 10;
	protected float localTime = 0;
	protected float sceneTime = 0;
	protected float endTime = 0;
	protected float procent = 0;
	protected Gui gui = null;
	protected Gui gui2 = null;
	protected Gui gui3 = null;

	@Override
	public void initialize(){

		gui = new Gui();
		gui2 = new Gui();
		gui3 = new Gui();

		Gui.disableListener = true;

		gui.addLabel("Test Button", true, 3);
		gui.addButton("btnFloat1", this, "testFloat", 10f, true, "gr2");
		gui.addButton("btnFloat2", this, "testFloat", 20f, false, "gr2");
		gui.addButton("btnFloat3", this, "testFloat", 30f, false, "gr2");

		gui.addButton("btnBoolean1", this, "testBoolean", true, true, "gr3");
		gui.addButton("btnBoolean2", this, "testBoolean", false, false, "gr3");

		gui.addLabel("Test Button Toggle", true, 3);
		gui.addButtonToggle("btnInteger1", this, "testInteger", 10, true, "gr0");
		gui.addButtonToggle("btnInteger2", this, "testInteger", 20, false, "gr0");
		gui.addButtonToggle("btnInteger3", this, "testInteger", 30, false, "gr0");

		gui.addButtonToggle("btnInteger1G", this, "testInteger", 30, true, "gr1");
		gui.addButtonToggle("btnInteger2G", this, "testInteger", 40, false, "gr1");
		gui.addButtonToggle("btnInteger3G", this, "testInteger", 50, false, "gr1");

		gui.addLabel("Test Slider All colspan-1", true, 3);
		gui.addSlider("Slider Float 1", this, "testFloat", testFloat, 0f, 10f, 2f, 1);
		gui.addSlider("Slider Float 2", this, "testFloat", testFloat, 0f, 10f, 1f, 2);
		gui.addSlider("Slider Float 3", this, "testFloat", testFloat, 0f, 10f, 1f, 2);

		gui.addLabel("Test CheckBox", true, 3);
		gui.addCheckBox("CheckBox", this, "testBoolean", true, false, true, 1, null, null);
		gui.addCheckBox("CheckBox", this, "testBoolean", true, false, true, 1, null, null);
		gui.addCheckBox("CheckBox", this, "testBoolean", true, false, true, 1, null, null);

		gui2.addLabel("Test List", true, 3);
		String[] listItem = {"Pierwsza opcja", "Druga opcja", "Trzecia opcja", "Czwarta opcja"};
		gui2.addList("Test list", this, "testListString", listItem, "", true, 2, null);
		gui2.addSelectBox("Test select box", this, "testListString", listItem, "", true, 2, null);
		gui2.addColorBrowser("Test Color", this, "testColor", Color.RED, true);

		Window window = GuiResource.window("Test Effect", "window");
		window.defaults();

		window.row();
		window.add(gui.getScrollPane()).height(Cfg.gridY22).width(Cfg.gridX12);
		window.add(gui2.getScrollPane()).height(Cfg.gridY22).width(Cfg.gridX8);
		window.pack();
		window.setMovable(false);

		addActor(window);

		Gui.disableListener = false;
	}
}
