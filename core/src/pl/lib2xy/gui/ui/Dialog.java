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

package pl.lib2xy.gui.ui;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import pl.lib2xy.app.ResourceManager;
import pl.lib2xy.gui.GuiResource;

/**
 * Created by Jarek on 2014-06-13.
 */
public class Dialog extends com.badlogic.gdx.scenes.scene2d.ui.Dialog{

	protected float fadeDuration = 0.2f;
	protected com.badlogic.gdx.scenes.scene2d.ui.Window mainWindow;

	public Dialog(String title, Skin skin){
		super(title, skin, "dialog");
		initialize();
	}

	public Dialog(String title, Skin skin, String windowStyleName){
		super(title, skin, windowStyleName);
		initialize();
	}

	public Dialog(String title, WindowStyle windowStyle){
		super(title, windowStyle);
		initialize();
	}

	private void initialize(){
		Actor actor = getChildren().get(1);
		if (actor instanceof Table){
			((Table) actor).defaults().minWidth(250);
		}
		Actor actor2 = getChildren().get(2);
		if (actor2 instanceof Table){
			((Table) actor2).defaults().pad(5);
		}
		getTitleTable().padLeft(10);
	}

	@Override
	public com.badlogic.gdx.scenes.scene2d.ui.Dialog show(Stage stage){
		return super.show(stage);
	}

	@Override
	public com.badlogic.gdx.scenes.scene2d.ui.Dialog show(Stage stage, Action action){
		return super.show(stage, action);
	}

	@Override
	public void hide(){
		super.hide();
	}

	@Override
	public void hide(Action action){
		super.hide(action);
	}
}
