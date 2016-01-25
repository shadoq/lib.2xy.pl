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

package pl.lib2xy.gui.widget;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;

/**
 * @author Jarek
 */
public class TabContainer extends Table{

	private Window.WindowStyle style;
	private Actor actor;

	public TabContainer(){
		super();
	}

	public TabContainer(Skin skin){
		this(skin, "default");
	}

	public TabContainer(Skin skin, String styleName){
		super(skin);
		setStyle(skin.get(styleName, Window.WindowStyle.class));
	}

	public TabContainer(Skin skin, boolean tranparent){
		super(skin);
		if (tranparent){
			setStyle(skin.get("default-backend", Window.WindowStyle.class));
		} else {
			setStyle(skin.get("default", Window.WindowStyle.class));
		}
	}

	public void setStyle(Window.WindowStyle style){
		this.style = style;
		setBackground(style.background);
	}

}