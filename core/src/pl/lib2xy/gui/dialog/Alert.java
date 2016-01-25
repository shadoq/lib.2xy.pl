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

package pl.lib2xy.gui.dialog;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import pl.lib2xy.XY;
import pl.lib2xy.app.Lang;
import pl.lib2xy.app.Log;
import pl.lib2xy.gui.GuiResource;
import pl.lib2xy.gui.GuiUtil;
import pl.lib2xy.gui.widget.WidgetInterface;

/**
 * @author Jarek
 */
public class Alert extends DialogBase implements WidgetInterface, InputProcessor{

	private boolean getOk = false;

	/**
	 * @param alertString
	 */
	public void show(String alertString){

		super.create();

		final Label alertLabel = GuiResource.label(alertString, "labelFile");

		alertLabel.setWrap(true);
		alertLabel.setAlignment(Align.center | Align.center);

		final Button buttonOk = GuiResource.textButton("Ok", "colorDialogButtonOk");

		mainWindow.getTitleLabel().setText(Lang.get("Alert"));

		mainWindow.row();
		mainWindow.add(alertLabel).minWidth(250).minHeight(110).fill();
		mainWindow.row();
		mainWindow.add(buttonOk).fillX();
		mainWindow.pack();
		mainWindow.setModal(true);

		GuiUtil.windowPosition(mainWindow, 12, 12);

		XY.stage.addActor(mainWindow);

		//
		// Akcje GUI
		//
		getOk = false;
		buttonOk.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y){
				getOk = true;
				hide();
			}
		});
		super.show();
	}

	@Override
	public void hide(){
		super.hide();
	}

	@Override
	public boolean keyDown(int keycode){
		Log.debug("keyDown", "code=" + keycode);
		return true;
	}

	@Override
	public boolean keyUp(int keycode){
		if (keycode == Input.Keys.ESCAPE){
			getOk = true;
			hide();
		}
		return true;
	}

	@Override
	public boolean keyTyped(char character){
		Log.debug("keyTyped", "code=" + character);
		return true;
	}

	@Override
	public boolean touchDown(int x, int y, int pointer, int button){
		return true;
	}

	@Override
	public boolean touchUp(int x, int y, int pointer, int button){
		return true;
	}

	@Override
	public boolean touchDragged(int x, int y, int pointer){
		return true;
	}

	@Override
	public boolean scrolled(int amount){
		return true;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY){
		return true;
	}
}
