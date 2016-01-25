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

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lib2xy.gui.dialog;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import pl.lib2xy.XY;
import pl.lib2xy.app.Lang;
import pl.lib2xy.app.Log;
import pl.lib2xy.gui.GuiResource;
import pl.lib2xy.gui.GuiUtil;
import pl.lib2xy.gui.widget.WidgetBoolListener;
import pl.lib2xy.gui.widget.WidgetInterface;

/**
 *
 * @author Jarek
 */
public class InputDialog extends DialogBase implements WidgetInterface, InputProcessor
{

	private boolean getOk=false;
	private WidgetBoolListener listener=null;
	private String inputText="";

	public void show(String alertString, String fieldmessage){
		super.create();

		getOk=false;

		final Label alertLabel= GuiResource.label(alertString, "labelFile");

		alertLabel.setWrap(true);
		alertLabel.setAlignment(Align.center | Align.center);

		final TextField textField=GuiResource.textField("", fieldmessage, "alertTextField");

		final Button buttonOk=GuiResource.textButton("Ok", "confirmButtonOk");
		final Button buttonCancel=GuiResource.textButton("Cancel", "confirmButtonCancel");

		//
		// Utworzenie okna
		//
		mainWindow.getTitleLabel().setText(Lang.get("Input"));
		mainWindow.row();
		mainWindow.add(alertLabel).colspan(2).minWidth(250).fill();
		mainWindow.row();
		mainWindow.add(textField).colspan(2).minWidth(250).fill();
		mainWindow.row();
		mainWindow.add(buttonOk).fillX();
		mainWindow.add(buttonCancel).fillX();
		mainWindow.pack();
		mainWindow.setModal(true);

		GuiUtil.windowPosition(mainWindow, 12, 12);
		XY.stage.addActor(mainWindow);

		//
		// Akcje GUI
		//
		buttonOk.addListener(new ClickListener()
		{
			@Override
			public void clicked(InputEvent event, float x, float y){
				getOk=true;
				inputText=textField.getText();
				if (listener != null){
					listener.Action(getOk);
				}
				hide();
			}
		});

		buttonCancel.addListener(new ClickListener()
		{
			@Override
			public void clicked(InputEvent event, float x, float y){
				getOk=false;
				inputText="";
				if (listener != null){
					listener.Action(getOk);
				}
				hide();
			}
		});
		super.show();
	}

	/**
	 *
	 * @param listener
	 */
	public void setListener(WidgetBoolListener listener){
		this.listener=listener;
	}

	/**
	 *
	 * @return
	 */
	public boolean get(){
		return getOk;
	}

	/**
	 * 
	 * @return 
	 */
	public String getInputText(){
		return inputText;
	}
	
	@Override
	public void hide(){
		super.hide();
	}

	@Override
	public boolean keyDown(int keycode){
		Log.log("keyDown", "code=" + keycode);
		return true;
	}

	@Override
	public boolean keyUp(int keycode){
		Log.log("keyUp", "code=" + keycode);
		if (keycode == Keys.ESCAPE){
			getOk=false;
			if (listener != null){
				listener.Action(getOk);
			}
			hide();
		} else if (keycode == Keys.ENTER){
			getOk=true;
			if (listener != null){
				listener.Action(getOk);
			}
			hide();
		}
		return true;
	}

	@Override
	public boolean keyTyped(char character){
		Log.log("keyTyped", "code=" + character);
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
