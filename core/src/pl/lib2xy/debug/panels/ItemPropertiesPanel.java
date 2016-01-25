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
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ArrayMap;
import com.badlogic.gdx.utils.ObjectMap;
import pl.lib2xy.XY;
import pl.lib2xy.app.Log;
import pl.lib2xy.debug.gui.BorderFrom;
import pl.lib2xy.interfaces.GuiInterface;

public class ItemPropertiesPanel extends BorderFrom implements GuiInterface{

	private static final String TAG = "Properties";
	private Actor processActor;

	public ItemPropertiesPanel(Skin skin, float width, float height){
		super(skin, null, null, true, width, height);
		this.skin = skin;
		this.actor = this;
		setupIconToolBar();
		setup();
	}

	private void setupIconToolBar(){
	}

	public void setActor(Actor processActor){

		if (lock){
			return;
		}
		lock();
		this.processActor = processActor;

		if (processActor instanceof GuiInterface){
			try {

				headerTable.clear();
				headerTable.add(processActor.getClass().getSimpleName() + " : " + processActor.getName()).maxWidth(200);
				createGUI();
			} catch (Exception ex){
				Log.error(TAG, "Error setUp actor ...", ex);
			}
		} else {
			try {
				clear();
			} catch (Exception ex){
				Log.error(TAG, "Error setUp actor ...", ex);
			}
		}
		unlock();
	}

	private void createGUI(){
		clear();
		setDefinition(((GuiInterface) processActor).getGuiDefinition(), ((GuiInterface) processActor).getValues());
		update();
	}


	public void update(){
		lock();
		if (processActor instanceof GuiInterface){
			values = ((GuiInterface) processActor).getValues();
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
								case "TextField":
									if (updateActor.getName().contains("_textint")){
										float fl = Float.valueOf(value);
										((TextField) updateActor).setText("" + (int) fl);
									} else {
										((TextField) updateActor).setText(value);
									}
									break;
								case "CheckBox":
									((CheckBox) updateActor).setChecked(Boolean.valueOf(value));
									break;
								case "SelectBox":
									((SelectBox<String>) updateActor).setSelected(value);
									break;
								case "List":
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
		return guiDef;
	}

	@Override
	public ArrayMap<String, String> getValues(){
		ArrayMap<String, String> values = new ArrayMap<>();
		return values;
	}

	@Override
	public void setValues(String changeKey, ArrayMap<String, String> values){
		if (!XY.cfg.editor){
			return;
		}
		if (processActor instanceof GuiInterface){
			((GuiInterface) processActor).setValues(changeKey, values);
		}

		if (changeKey.equals("name")){
			//TODO: Update list name
		}
	}
}


