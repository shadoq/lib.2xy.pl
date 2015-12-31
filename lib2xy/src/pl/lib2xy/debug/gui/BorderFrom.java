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
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class BorderFrom extends BaseForm{

	public BorderFrom(Skin skin){
		super(skin, null, null, false, 0, 0);
		setupDraw();
		pad(3);
	}

	public BorderFrom(Skin skin, Actor actor){
		super(skin, actor, null, false, 0, 0);
		setupDraw();
		pad(3);
		setupDraw();
	}

	public BorderFrom(Skin skin, Actor actor, String header, boolean scroll, float width, float height){
		super(skin, actor, header, scroll, width, height);
		setupDraw();
		pad(3);
		setupDraw();
	}

	private void setupDraw(){
		setBackground(skin.getDrawable("bg_opaque"));
	}
}
