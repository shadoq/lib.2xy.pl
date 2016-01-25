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

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ArrayMap;
import com.badlogic.gdx.utils.ObjectMap;
import pl.lib2xy.XY;
import pl.lib2xy.app.Log;
import pl.lib2xy.app.ResourceManager;
import pl.lib2xy.app.Run;
import pl.lib2xy.debug.Debug;
import pl.lib2xy.debug.gui.BorderFrom;
import pl.lib2xy.interfaces.GuiInterface;

public class SceneListPanel extends BorderFrom implements GuiInterface{

	Skin skin;

	public SceneListPanel(Skin skin, float width, float height){
		super(skin, null, "Scene List", false, width, height);
		this.skin = skin;
		this.actor = this;
		setupIconToolBar();
		setup();
	}

	private void setupIconToolBar(){

	}

	@Override
	public void update(){

		lock();
		if (actor instanceof GuiInterface){
			values = ((GuiInterface) actor).getValues();
			if (values == null){
				values = new ArrayMap<>();
			}

			for (ObjectMap.Entry<String, Actor> entry : actorsMap){
				final String key = entry.key;
				Actor updateActor = entry.value;
				if (key != null && entry.value != null){
					if (values.containsKey(key)){
						final String value = values.get(key);

						if (updateActor != null){
							final String simpleName = updateActor.getClass().getSimpleName();

							switch (simpleName){
								case "List":

									final String[] sceneMapList = ResourceManager.getSceneMapList();
									((List) updateActor).setItems(sceneMapList);
									((List) updateActor).setSelected(value);
									break;
							}
						}
					}
				}
			}

		}
		mainTable.pack();
		mainTable.invalidate();

		pack();
		invalidate();
		unlock();
	}


	@Override
	public Array<String[]> getGuiDefinition(){
		Array<String[]> guiDef = new Array<>();
		guiDef.add(new String[]{"scene", "scene", "SCENE_LIST", ""});
		return guiDef;
	}

	@Override
	public ArrayMap<String, String> getValues(){
		ArrayMap<String, String> values = new ArrayMap<>();
		values.put("scene", XY.currentSceneName);
		return values;
	}

	@Override
	public void setValues(String changeKey, ArrayMap<String, String> values){
		if (changeKey.equals("scene")){
			Log.debug("SceneListPanel", "Set scene: " + values.get("scene"));
			Run.setScene(values.get("scene"));
		}
	}
}
