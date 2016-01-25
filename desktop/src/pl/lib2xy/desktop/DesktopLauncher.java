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

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import pl.lib2xy.XY;
import pl.lib2xy.app.Run;
import pl.lib2xy.app.Scene;
import pl.lib2xy.test.*;

public class DesktopLauncher{
	public static void main(String[] args){
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();

		cfg.title = "lib2xy";
		cfg.width = 1280;
		cfg.height = 720;

		Scene[] scenes =
		{
		new pl.lib2xy.test.Main(),
		new GameOXO(),
		new GameAsteroids(),
		new DemoGui(),
		new DemoGuiGen(),
		new DemoMessage(),
		new DemoFxEffect(),
		new DemoProceduralPixmapTest(),
		new G2DSimpleShapes(),
		new G2DSpriteTest(),
		new G3DFogTest(),
		new G3DModelTest(),
		new G3DMaterialTest(),
		new GfxGradientTest(),
		new GuiUiTest(),
		new GuiUiTest2(),
		new GuiAllTest(),
		new GuiDialogTest(),
		new GuiEditor2dTest(),
		new GuiFormTest(),
		new GuiResourceTest(),
		new GuiTabHostTest(),
		new LogTest(),
		new ScrollPaneTest(),
		new SimpleGrid(),
		new SimpleTest(),
		new VirtualScreenTest(),
		new VirtualSpritePicking(),
		new SaveDataTest(),
		};

		XY.env = new SystemEmulator();
		Run run = new Run(scenes);
		new LwjglApplication(run, cfg);
	}
}
