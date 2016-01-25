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

import pl.lib2xy.XY;
import pl.lib2xy.app.ResourceManager;
import pl.lib2xy.app.Scene;
import pl.lib2xy.gui.ui.Sprite;

/**
 * @author Jarek
 */
public class G2DSpriteTest extends Scene{

	Sprite sprite;
	Sprite sprite2;
	Sprite sprite3;

	@Override
	public void initialize(){

		ResourceManager.loadAtlas("atlas/anim2.atlas");

		sprite = new Sprite();
		sprite.setX(XY.centerX - 64);
		sprite.setY(XY.centerY - 128);
		sprite.setWidth(128);
		sprite.setHeight(128);

		sprite2 = new Sprite();
		sprite2.setX(XY.centerX - 64);
		sprite2.setY(XY.centerY);
		sprite2.setWidth(128);
		sprite2.setHeight(128);

		sprite3 = new Sprite();
		sprite3.setX(XY.centerX - 128);
		sprite3.setY(XY.centerY);
		sprite3.setWidth(128);
		sprite3.setHeight(128);
		sprite3.setRotation(45);

		sprite.setTextures(0.2f, 5, true, true, "implode", "explosion");
		sprite.setAnimationName("implode");

		sprite2.setTextures(0.2f, 5, true, true, "implode", "explosion");
		sprite2.setAnimationName("explosion");

		sprite3.setTextures(0.25f, 16, false, true, "anim/betty.png");

		addActor(sprite);
		addActor(sprite2);
		addActor(sprite3);
	}
}
