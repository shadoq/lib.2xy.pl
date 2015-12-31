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

package pl.lib2xy.client;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.gwt.GwtApplication;
import com.badlogic.gdx.backends.gwt.GwtApplicationConfiguration;
import pl.lib2xy.app.Run;
import pl.lib2xy.app.Scene;
import pl.lib2xy.test.*;

public class GwtLauncher extends GwtApplication{
	@Override
	public GwtApplicationConfiguration getConfig(){
		GwtApplicationConfiguration cfg = new GwtApplicationConfiguration(1280, 700);
		cfg.useDebugGL = true;
		cfg.preferFlash = false;
		return cfg;
	}

	@Override
	public ApplicationListener getApplicationListener(){
		Scene[] scenes = new Scene[]{
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
		new SaveDataTest(),		};
		Run run = new Run(scenes);
		return run;
	}
}