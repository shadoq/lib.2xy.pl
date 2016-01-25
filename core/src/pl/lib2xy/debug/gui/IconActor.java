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
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class IconActor extends Icon{

	Actor actor;
	String text;
	Skin skin;

	public IconActor(String text, Actor actor, Skin skin){

		if (actor == null){
			return;
		}

		iconWidth = 94;
		iconHeight = 32;

		setupIcon();

		this.actor = actor;
		this.text = text;
		this.skin = skin;

		Label label = new Label(text, skin);
		label.setY(getHeight() / 2 - label.getHeight() / 2);

		setupDragAndDrop(this);
		addActor(label);
	}

	public Actor getActorToInsert(){
		return actor;
	}

	public String getTextLabel(){
		return text;
	}

}
