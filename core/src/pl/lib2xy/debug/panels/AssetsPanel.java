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

import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import pl.lib2xy.app.ResourceManager;
import pl.lib2xy.debug.gui.BaseBox;
import pl.lib2xy.debug.gui.IconTextureRegion;

public class AssetsPanel extends BaseBox{

	String[] textureRegionList;
	Table tableList;

	/**
	 * @param parentTable
	 * @param skin
	 * @param width
	 * @param height
	 */
	public AssetsPanel(Table parentTable, Skin skin, float width, float height){
		super(parentTable, skin, width, height);
		tableList = new Table(skin);
		tableList.setWidth(width);
		tableList.setHeight(height);
		setupList();

		final ScrollPane scrollPane = new ScrollPane(tableList, skin.get("default-no-background", ScrollPane.ScrollPaneStyle.class));
		scrollPane.setScrollbarsOnTop(true);
		scrollPane.setFadeScrollBars(false);
		scrollPane.setScrollingDisabled(true, false);

		scrollPane.setWidth(width);
		scrollPane.setHeight(height);

		contentGroup.addActor(scrollPane);
	}

	/**
	 *
	 */
	public void setupList(){
		textureRegionList = ResourceManager.getTextureRegionList();
		int i = 0;

		for (String textureName : textureRegionList){
			if (textureName.equals("None")){
				continue;
			}
			IconTextureRegion iconTextureAtlas = new IconTextureRegion(ResourceManager.getTextureRegion(textureName), textureName);
			tableList.add(iconTextureAtlas).pad(3);
			if ((i % 4) == 3){
				tableList.row();
			}
			i++;
		}
	}

	/**
	 *
	 */
	public void update(){
		tableList.clear();
		setupList();
		tableList.invalidate();
	}
}
