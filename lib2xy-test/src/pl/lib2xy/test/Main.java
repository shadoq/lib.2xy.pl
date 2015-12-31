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

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import pl.lib2xy.app.Scene;

public class Main extends Scene{

	@Override
	public void initialize(){

	}

	@Override
	public void update(float delta){

	}

	@Override
	public void render(float delta){

	}

	@Override
	public void onClick(InputEvent event, Actor actor, int x, int y){
		if (actor != null){
			log("Click on actor: " + actor + " name: " + actor.getName());
			if (actor.getName() != null){
				if (actor.getName().contains("messageButton")){
					log("Click on Message ...");
					setScene("DemoMessage");
				} else if (actor.getName().contains("asteroidsButton")){
					log("Click on Asteroids ...");
					setScene("GameAsteroids");
				} else if (actor.getName().contains("oxoButton")){
					log("Click on OXO ...");
					setScene("GameOXO");
				} else if (actor.getName().contains("fxButton")){
					log("Click on FX ...");
					setScene("DemoFxEffect");
				} else if (actor.getName().contains("pixmapButton")){
					log("Click on Pixmap ...");
					setScene("DemoProceduralPixmapTest");
				} else if (actor.getName().contains("guiButton")){
					log("Click on Gui ...");
					setScene("DemoGui");
				} else if (actor.getName().contains("guiFormButton")){
					log("Click on Gui Form ...");
					setScene("DemoGuiGen");
				}
			}
		}
	}
}
