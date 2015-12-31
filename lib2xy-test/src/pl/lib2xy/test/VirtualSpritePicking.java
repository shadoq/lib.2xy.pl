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
package pl.lib2xy.test;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import pl.lib2xy.XY;
import pl.lib2xy.app.Log;
import pl.lib2xy.app.ResourceManager;
import pl.lib2xy.app.Scene;


/**
 * @author Jarek
 */
public class VirtualSpritePicking extends Scene{

	private int numSprite = 200;
	private Sprite[] sprites = new Sprite[numSprite];
	private Texture tex;
	Vector3 tempVector = new Vector3();
	Vector3 intersection = new Vector3();
	Vector3 unprojectedVertex = new Vector3();
	Rectangle boundingRectangle;

	@Override
	public void initialize(){
		tex = ResourceManager.getTexture("sprite/bobs1.png");
		for (int i = 0; i < numSprite; i++){
			sprites[i] = new Sprite(tex);
			sprites[i].setColor(Color.WHITE);
			sprites[i].setPosition((int) (Math.random() * XY.width), (int) (Math.random() * XY.height));
			sprites[i].setOrigin(16, 16);
		}
	}

	@Override
	public void update(float delta){

	}

	@Override
	public void render(float delta){

		XY.spriteBatch.setProjectionMatrix(XY.fixedCamera.combined);
		XY.spriteBatch.begin();
		for (int i = 0; i < numSprite; i++){
			sprites[i].setColor(Color.WHITE);
			boundingRectangle = sprites[i].getBoundingRectangle();
			if (boundingRectangle.contains(unprojectedVertex.x, unprojectedVertex.y)){
				sprites[i].setColor(Color.YELLOW);
			}
			sprites[i].draw(XY.spriteBatch);
		}
		XY.spriteBatch.end();
	}

	@Override
	public void onTouchDown(int x, int y, int button){
		unprojectedVertex.set(Gdx.input.getX(), Gdx.input.getY(), 0);
		XY.fixedCamera.unproject(unprojectedVertex);
		Log.log("SpritePicking", x + " " + y + " " + button + " " + unprojectedVertex.toString());
	}
}
