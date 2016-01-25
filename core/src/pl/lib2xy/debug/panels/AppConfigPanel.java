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

package pl.lib2xy.debug.panels;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ArrayMap;
import pl.lib2xy.XY;
import pl.lib2xy.app.ResourceManager;
import pl.lib2xy.app.Run;
import pl.lib2xy.app.Screen;
import pl.lib2xy.debug.gui.BorderExpandFrom;
import pl.lib2xy.interfaces.GuiInterface;

import java.util.Arrays;

public class AppConfigPanel extends BorderExpandFrom implements GuiInterface{

	public AppConfigPanel(Skin skin){
		super(skin, null, "App Config", false);
		this.skin = skin;
		this.actor = this;
		setup();
	}


	@Override
	public Array<String[]> getGuiDefinition(){
		Array<String[]> guiDef = new Array<>();
		guiDef.add(new String[]{"limitFPS", "limitFPS", "LIST", "0", "0,10,20,30,40,50,60"});
		guiDef.add(new String[]{"editSize", "editSize", "LIST", "800x480", "800x480,480x800"});
		guiDef.add(new String[]{"viewPort", "viewPort", "LIST", "800x480", Arrays.toString(Screen.ViewPortType.values())
																				 .replace("[", "")
																				 .replace("]", "")
		.replace(", ", ",")});

		return guiDef;
	}

	@Override
	public ArrayMap<String, String> getValues(){
		ArrayMap<String, String> values = new ArrayMap<>();
		values.put("limitFPS", "" + XY.cfg.frameRateLimit);
		values.put("editSize", "" + XY.cfg.editSize);
		values.put("viewPort", XY.cfg.viewPortType.name());
		return values;
	}

	@Override
	public void setValues(String changeKey, ArrayMap<String, String> values){

		switch (changeKey){
			case "limitFPS":
				XY.cfg.frameRateLimit = Integer.valueOf(values.get("limitFPS"));
				break;
			case "editSize":
				XY.cfg.editSize = values.get("editSize");
				XY.viewPort = null;
				Screen.create();
				Run.resetCamera();
				ResourceManager.writeConfig();
				XY.saveEnable = true;
				break;
			case "viewPort":
				XY.cfg.viewPortType = Screen.ViewPortType.valueOf(values.get("viewPort"));
				XY.viewPort = null;
				ResourceManager.writeConfig();
				Run.getInstance().resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
				break;
		}
	}
}
