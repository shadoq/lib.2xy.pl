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

import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ArrayMap;
import pl.lib2xy.XY;
import pl.lib2xy.debug.Debug;
import pl.lib2xy.debug.commands.ReloadScene;
import pl.lib2xy.debug.commands.SaveScene;
import pl.lib2xy.debug.editors.SceneEditor;
import pl.lib2xy.debug.gui.BorderExpandFrom;
import pl.lib2xy.interfaces.GuiInterface;

public class AppIconPanel extends BorderExpandFrom implements GuiInterface{

	public AppIconPanel(Skin skin){
		super(skin, null, null, false);
		this.skin = skin;
		this.actor = this;
		setup();
	}


	@Override
	public Array<String[]> getGuiDefinition(){
		Array<String[]> guiDef = new Array<>();

		guiDef.add(new String[]{"play", "play", "TOGGLE", "icon_pause_button"});
		guiDef.add(new String[]{"editor", "editor", "TOGGLE", "icon_maintenance"});
		guiDef.add(new String[]{"grid", "grid", "BUTTON", "icon_dice"});
		guiDef.add(new String[]{"snpToGrid", "snpToGrid", "TOGGLE", "icon_close"});

		guiDef.add(new String[]{"", "", "BUTTON_SPACE", ""});
		guiDef.add(new String[]{"reloadScene", "reloadScene", "BUTTON", "icon_refresh"});
		guiDef.add(new String[]{"saveScene", "saveScene", "BUTTON", "icon_save"});

		guiDef.add(new String[]{"", "", "BUTTON_SPACE", ""});
		guiDef.add(new String[]{"cutActor", "cutActor", "BUTTON", "icon_cut"});
		guiDef.add(new String[]{"copyActor", "copyActor", "BUTTON", "icon_clipboard"});
		guiDef.add(new String[]{"pasteActor", "pasteActor", "BUTTON", "icon_down"});
		guiDef.add(new String[]{"", "", "BUTTON_SPACE", ""});
		guiDef.add(new String[]{"duplicateActor", "duplicateActor", "BUTTON", "icon_cloud_add"});
		guiDef.add(new String[]{"", "", "BUTTON_SPACE", ""});
		guiDef.add(new String[]{"deleteActor", "deleteActor", "BUTTON", "icon_delete"});

		return guiDef;
	}

	@Override
	public ArrayMap<String, String> getValues(){
		ArrayMap<String, String> values = new ArrayMap<>();
		return values;
	}

	@Override
	public void setValues(String changeKey, ArrayMap<String, String> values){

		switch (changeKey){
			case "play":
				XY.cfg.pause = !XY.cfg.pause;
				break;
			case "editor":
				XY.cfg.editor = !XY.cfg.editor;
				if (XY.cfg.editor){
					XY.inputMultiplexer.removeProcessor(XY.stage);
				} else {
					XY.inputMultiplexer.removeProcessor(XY.stage);
					XY.inputMultiplexer.addProcessor(XY.stage);
				}
				break;
			case "grid":
				XY.cfg.grid = !XY.cfg.grid;
				break;
			case "snpToGrid":
				SceneEditor.snapToGrid = !SceneEditor.snapToGrid;
				break;
			case "reloadScene":
				XY.debug.command(new ReloadScene());
				break;
			case "saveScene":
				XY.debug.command(new SaveScene());
				break;
			case "cutActor":
				pl.lib2xy.debug.Debug.sceneEditor.cutSelectActor();
				break;
			case "copyActor":
				pl.lib2xy.debug.Debug.sceneEditor.copySelectActor();
				break;
			case "duplicateActor":
				pl.lib2xy.debug.Debug.sceneEditor.duplicateSelectActor();
				break;
			case "pasteActor":
				pl.lib2xy.debug.Debug.sceneEditor.pasteSelectActor();
				break;
			case "deleteActor":
				pl.lib2xy.debug.Debug.sceneEditor.deleteSelectedActors();
				break;

		}
	}
}
