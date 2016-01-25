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

package pl.lib2xy.debug.gui;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ArrayMap;
import com.badlogic.gdx.utils.ObjectMap;
import pl.lib2xy.app.ResourceManager;

public class BaseTabBox extends BaseBox{

	ArrayMap<String, Actor> maps = new ArrayMap<>();

	int activeTab = 0;
	int tabWidth = 0;

	public BaseTabBox(Table parentTable, Skin skin, float width, float height){
		super(parentTable, skin, width, height);
	}

	public void addTab(String name, Actor box){
		maps.put(name, box);
	}

	public void addTab(String name, String content){
		Label label = new Label(content, skin);
		maps.put(name, label);
	}

	public void setup(){
		setupTabs();
	}

	protected void setupTabs(){

		contentGroup.clear();

		if (maps.size > 0){
			tabWidth = (int) ((width) / maps.size);
		} else {
			tabWidth = (int) width;
		}

		int posX = 1;
		int i = 0;
		for (ObjectMap.Entry<String, Actor> map : maps){

			Label tabLabel = new Label(map.key, skin);

			float labWidth = tabWidth / 2f - tabLabel.getWidth() / 2f;

			tabLabel.setX(posX + labWidth);
			tabLabel.setY(height - tabLabel.getHeight() + 2);
			tabLabel.setTouchable(Touchable.disabled);

			final Label.LabelStyle style = new Label.LabelStyle(tabLabel.getStyle());
			style.font = ResourceManager.getBitmapFontFromClass("regular-10.fnt");
			tabLabel.setStyle(style);

			String tabBgName = "sm_tab";
			if (i == activeTab){
				tabBgName = "sm_tab2";
			}
			Image tabBG = ResourceManager.getIconFromClass(tabBgName);
			Image tabSep = ResourceManager.getIconFromClass("sm_tab3");

			tabBG.setX(posX);
			tabBG.setY(height - tabLabel.getHeight() + 4);

			tabBG.setWidth(tabWidth);
			tabBG.setHeight(tabLabel.getHeight() - 4);


			tabSep.setX(posX);
			tabSep.setY(height - tabLabel.getHeight() + 2);

			tabSep.setWidth(tabWidth);
			tabSep.setHeight(1);
			tabSep.setTouchable(Touchable.disabled);

			if (i == activeTab){
				tabSep.setColor(1.0F, 1.0F, 1.0F, 1.0F);
				tabBG.setColor(1.0F, 1.0F, 1.0F, 1.0F);
			} else {
				tabSep.setColor(1.0F, 1.0F, 1.0F, 0.65F);
				tabBG.setColor(1.0F, 1.0F, 1.0F, 0.65F);
			}

			contentGroup.addActor(tabBG);
			contentGroup.addActor(tabSep);
			contentGroup.addActor(tabLabel);

			if (map.value != null){

				Actor mainActor = map.value;

				mainActor.setX(0);
				mainActor.setY(height - mainActor.getHeight() - tabBG.getHeight());

				if (i == activeTab){
					mainActor.setVisible(true);
				} else {
					mainActor.setVisible(false);
				}

				contentGroup.addActor(mainActor);
			}

			final int finalI = i;
			tabBG.addListener(new ClickListener(){
				@Override
				public void clicked(InputEvent event, float x, float y){

					activeTab = finalI;
					setupTabs();
				}
			});

			posX = (int) (posX + tabWidth);
			i++;
		}
	}

	protected void showContent(){

	}

	protected void hideContent(){

	}
}
