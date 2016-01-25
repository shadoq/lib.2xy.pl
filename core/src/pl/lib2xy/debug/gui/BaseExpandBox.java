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

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import pl.lib2xy.app.Log;
import pl.lib2xy.app.ResourceManager;
import pl.lib2xy.debug.Debug;

public class BaseExpandBox extends BaseBox{

	protected boolean isExpand = true;
	protected Image expandButton;
	protected Label label;
	protected Debug debug;
	protected String textLabel;

	public BaseExpandBox(Debug debug, Table parentTable, Skin skin, float width, float height){
		super(parentTable, skin, width, height);
		this.skin = skin;
		this.debug = debug;
	}

	public BaseExpandBox(String textLabel, Debug debug, Table parentTable, Skin skin, float width, float height){
		super(parentTable, skin, width, height);
		this.skin = skin;
		this.debug = debug;
		this.textLabel = textLabel;

		setupLabel();
	}

	protected void setupBox(){

		super.setupBox();

		expandButton = ResourceManager.getIconFromClass("sm_expand");
		expandButton.setVisible(true);
		expandButton.setOrigin(expandButton.getWidth() / 2, expandButton.getHeight() / 2);
		expandButton.setRotation(isExpand ? 0 : 180);
		expandButton.setX(getWidth() - expandButton.getWidth() - 4);
		expandButton.setY(getHeight() - expandButton.getHeight() - 4);

		backGroup.addActor(expandButton);

		expandButton.addListener(new ClickListener(){

			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
				super.touchDown(event, x, y, pointer, button);
				return true;
			}

			public void touchUp(InputEvent event, float x, float y, int pointer, int button){
				super.touchUp(event, x, y, pointer, button);
				backGroup.clear();
				contentGroup.clear();
				if (isExpand){
					hideContent();
				} else {
					showContent();
				}
				setupBox();
				setupLabel();

				isExpand = !isExpand;
				parentTable.invalidate();
				parentTable.layout();
				debug.getGui().invalidate();
				debug.getGui().layout();
			}
		});

	}

	protected void setupLabel(){
		if (textLabel != null){
			label = new Label(textLabel, skin);
			label.setX(2);
			label.setY(getHeight() - label.getHeight() + 5);
			backGroup.addActor(label);
		}

	}

	protected void showContent(){
		setHeight(height);
		if (contentGroup != null){
			contentGroup.setVisible(true);
		}
	}

	protected void hideContent(){
		setHeight(expandButton.getHeight() * 4);
		if (contentGroup != null){
			contentGroup.setVisible(false);
		}
	}
}
