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

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

public class BaseBox extends Group{

	protected Skin skin;

	protected Table parentTable;

	protected Group backGroup;
	protected Group contentGroup;

	protected Texture lightPixel;
	protected Texture darkPixel;
	protected Texture bgPixel;

	protected float width;
	protected float height;

	public BaseBox(Table parentTable, Skin skin, float width, float height){

		backGroup = new Group();
		contentGroup = new Group();

		this.parentTable = parentTable;
		this.skin = skin;
		this.width = width;
		this.height = height;

		if (width>0){
			setWidth(width);
		}
		if (height>0){
			setHeight(height);
		}

		addActor(backGroup);
		addActor(contentGroup);

		setupBox();
	}

	protected void setupBox(){
		Image imageBG = new Image(skin, "bg_opaque");
		imageBG.setX(0);
		imageBG.setY(0);
		imageBG.setHeight(getHeight());
		imageBG.setWidth(getWidth());
		backGroup.addActor(imageBG);
	}

}
