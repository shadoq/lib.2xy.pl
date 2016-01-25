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
import com.badlogic.gdx.utils.SnapshotArray;
import pl.lib2xy.XY;
import pl.lib2xy.app.Log;
import pl.lib2xy.debug.editors.SceneEditor;
import pl.lib2xy.debug.gui.BorderFrom;
import pl.lib2xy.interfaces.GuiInterface;

public class ItemListPanel extends BorderFrom implements GuiInterface{

	Skin skin;
	List<String> list;

	public ItemListPanel(Skin skin, float width, float height){
		super(skin, null, null, false, width, height);
		this.skin = skin;
		this.actor = this;
		setup();
	}


	@Override
	public void setup(){
		lock();
		createList();
		unlock();
	}


	@Override
	public void update(){
		if (lock){
			return;
		}
		lock();
		final String[] list1 = getList();
		list.setItems(list1);
		if (pl.lib2xy.debug.Debug.sceneEditor != null && pl.lib2xy.debug.Debug.sceneEditor.getSelectedCount() == 0){
			list.getSelection().clear();
		} else if (pl.lib2xy.debug.Debug.sceneEditor != null && pl.lib2xy.debug.Debug.sceneEditor.getSelectedCount() == 1){
			if (SceneEditor.currentActor != null){
				list.setSelected(SceneEditor.currentActor.getName());
			} else {
				list.getSelection().clear();
			}
		}
		unlock();
	}

	public void createList(){

		final String[] list1 = getList();
		list = (List<String>) addFullList("sceneItems", "sceneItems", "", list1);
		if (pl.lib2xy.debug.Debug.sceneEditor != null && pl.lib2xy.debug.Debug.sceneEditor.getSelectedCount() == 0){
			list.getSelection().clear();
		}
	}

	protected String[] getList(){
		final SnapshotArray<Actor> actors;
		if (XY.scene != null){
			actors = XY.scene.getChildren();
		} else {
			actors = new SnapshotArray<>();
		}
		final Array<String> ar = new Array<>();

		int i = 0;
		for (Actor act : actors){
			if (act != null){
				if (act.getName() == null){
					act.setName("unknown_" + i);
					i++;
				}
				ar.add(act.getName());
			}
		}

		String[] actorList = new String[ar.size];
		i = 0;
		for (String s : ar){
			if (s != null){
				actorList[i] = s;
			} else {
				actorList[i] = "unknown_" + i;
			}
			i++;
		}

		return actorList;
	}

	@Override
	public Array<String[]> getGuiDefinition(){
		Array<String[]> guiDef = new Array<>();
		//		guiDef.addSlider(new String[]{"sceneItems", "sceneItems", "SCENE_ITEMS", ""});
		return guiDef;
	}

	@Override
	public ArrayMap<String, String> getValues(){
		ArrayMap<String, String> values = new ArrayMap<>();
		return values;
	}

	@Override
	public void setValues(String changeKey, ArrayMap<String, String> values){
		if (changeKey.equals("sceneItems")){
			if (XY.scene != null){
				final Actor item = XY.scene.findActor(values.get("sceneItems"));
				if (item != null){
					Log.debug(TAG, "Set actor: " + item.getName());
					pl.lib2xy.debug.Debug.sceneEditor.setSelectActor(item.getName());
				}
			}
		}
	}

	public void setSelect(String name){
		if (name != null){
			list.setSelected(name);
		} else {
			list.getSelection().clear();
		}
	}
}
