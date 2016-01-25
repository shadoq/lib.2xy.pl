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
import com.badlogic.gdx.graphics.GL20;
import pl.lib2xy.XY;
import pl.lib2xy.app.Scene;
import pl.lib2xy.gui.widget.TabHost;

/**
 * @author Jarek
 */
public class GuiTabHostTest extends Scene{

	@Override
	public void initialize(){
		TabHost tabHost = new TabHost(XY.skin);
		TabHost tabHost2 = new TabHost(XY.skin);
		TabHost tabHost3 = new TabHost(XY.skin, true);

		tabHost.addTab("Test", "Test Main");
		tabHost.addTab("Main Screen", tabHost2);
		tabHost.addTab("Game", tabHost3);
		tabHost.addTab("Config", "Test Main 4");
		tabHost.addTab("Render", "Test Main 5");

		tabHost2.addTab("Test Tab 2a", "Tab 2 a ........");
		tabHost2.addTab("Test Tab 2b", "Tab 2 b ........");
		tabHost2.addTab("Test Tab 2c", "Tab 2 c ........");
		tabHost2.addTab("Test Tab 2d", "Tab 2 d ........");

		tabHost3.addTab("Test Tab 3a", "Tab 3 a ........");
		tabHost3.addTab("Test Tab 3b", "Tab 3 b ........");
		tabHost3.addTab("Test Tab 3c", "Tab 3 c ........");
		tabHost3.addTab("Test Tab 3d", "Tab 3 d ........");

		tabHost.setX(0);
		tabHost.setY(0);
		tabHost.setWidth(XY.width);
		tabHost.setHeight(XY.height);

		XY.stage.addActor(tabHost);
	}
}
