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

package pl.lib2xy.test;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import pl.lib2xy.XY;
import pl.lib2xy.app.Cfg;
import pl.lib2xy.app.Scene;
import pl.lib2xy.gui.ui.FxEffect;
import pl.lib2xy.gui.ui.TextButton;


/**
 *
 */
public class DemoFxEffect extends Scene{

	@Override
	public void initialize(){

		FxEffect fxEffect1 = new FxEffect("BobsMulti");
		fxEffect1.setWidth(Cfg.gridX12);
		fxEffect1.setHeight(Cfg.gridY12);
		fxEffect1.setX(0);
		fxEffect1.setY(0);
		addActor(fxEffect1);

		FxEffect fxEffect2 = new FxEffect("Plasma");
		fxEffect2.setWidth(Cfg.gridX12);
		fxEffect2.setHeight(Cfg.gridY12);
		fxEffect2.setX(Cfg.gridX12);
		fxEffect2.setY(Cfg.gridY12);
		addActor(fxEffect2);

		FxEffect fxEffect3 = new FxEffect("StarField");
		fxEffect3.setWidth(Cfg.gridX12);
		fxEffect3.setHeight(Cfg.gridY12);
		fxEffect3.setX(Cfg.gridX12);
		fxEffect3.setY(0);
		addActor(fxEffect3);

		FxEffect fxEffect4 = new FxEffect("Grid");
		fxEffect4.setWidth(Cfg.gridX12);
		fxEffect4.setHeight(Cfg.gridY12);
		fxEffect4.setX(0);
		fxEffect4.setY(Cfg.gridY12);
		addActor(fxEffect4);

		TextButton backButton = new TextButton("Back", XY.skin);
		backButton.setX(30);
		backButton.setY(27);
		backButton.setWidth(136);
		backButton.setHeight(39);
		backButton.getLabel().setAlignment(Align.center, Align.center);
		addActor(backButton);

		if (backButton != null){
			backButton.addListener(new ChangeListener(){
				@Override
				public void changed(ChangeEvent event, Actor actor){
					setScene("Main");
				}
			});
		}
	}

	@Override
	public void update(float delta){

	}

	@Override
	public void render(float delta){

	}

	@Override
	public void resize(){

	}

	@Override
	public void pause(){

	}

	@Override
	public void dispose(){

	}

	@Override
	public void resume(){

	}

	@Override
	public void onClick(InputEvent event, int x, int y){

	}

	@Override
	public void onClick(InputEvent event, Actor actor, int x, int y){

		if (actor != null){
			log("Click on actor: " + actor + " name: " + actor.getName());
			if (actor.getName() != null){
				if (actor.getName().contains("dialogButton")){
					showDialog("Test Dialog ...");
				} else if (actor.getName().contains("confirmButton")){
					showConfirmDialog("Test Confirm Dialog", "Are You sure ?");
				} else if (actor.getName().contains("toastButton")){
					showToast("Test toast ....", 4);
				} else if (actor.getName().contains("messageButton")){
					showMessageDialog("Message Dialog", "Message ...");
				}

			}
		}
	}

	@Override
	public void onDrag(int x, int y){

	}

	@Override
	public void onTouchDown(int x, int y, int button){

	}

	@Override
	public void onTouchUp(int x, int y, int button){

	}

	@Override
	public void onKeyDown(int keycode){

	}

	@Override
	public void onKeyUp(int keycode){

	}

	@Override
	public void onMouseMoved(int screenX, int screenY){

	}

	@Override
	public void onZoom(float initialDistance, float distance){

	}

	@Override
	public void onTap(float x, float y, int count, int button){

	}

	@Override
	public void onPinch(Vector2 initialPointer1, Vector2 initialPointer2, Vector2 pointer1, Vector2 pointer2){

	}

	@Override
	public void onPan(float x, float y, float deltaX, float deltaY){

	}

	@Override
	public void onPanStop(float x, float y, int pointer, int button){

	}

	@Override
	public void onLongPress(float x, float y){

	}

	@Override
	public void onFling(float velocityX, float velocityY, int button){

	}
}
