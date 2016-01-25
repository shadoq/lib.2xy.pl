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

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import pl.lib2xy.XY;
import pl.lib2xy.app.Cfg;
import pl.lib2xy.app.Gfx;
import pl.lib2xy.app.Scene;

public class SimpleGrid extends Scene{


	@Override
	public void initialize(){
	}

	@Override
	public void dispose(){
	}

	@Override
	public void update(float delta){

	}

	@Override
	public void render(float delta){
		Gfx.drawLine(Cfg.gridX0, Cfg.gridY0, Cfg.gridX0, Cfg.gridY24, Color.GREEN);
		Gfx.drawLine(Cfg.gridX1, Cfg.gridY0, Cfg.gridX1, Cfg.gridY24, Color.RED);
		Gfx.drawLine(Cfg.gridX2, Cfg.gridY0, Cfg.gridX2, Cfg.gridY24, Color.RED);
		Gfx.drawLine(Cfg.gridX3, Cfg.gridY0, Cfg.gridX3, Cfg.gridY24, Color.RED);
		Gfx.drawLine(Cfg.gridX4, Cfg.gridY0, Cfg.gridX4, Cfg.gridY24, Color.RED);
		Gfx.drawLine(Cfg.gridX5, Cfg.gridY0, Cfg.gridX5, Cfg.gridY24, Color.RED);
		Gfx.drawLine(Cfg.gridX6, Cfg.gridY0, Cfg.gridX6, Cfg.gridY24, Color.RED);
		Gfx.drawLine(Cfg.gridX7, Cfg.gridY0, Cfg.gridX7, Cfg.gridY24, Color.RED);
		Gfx.drawLine(Cfg.gridX8, Cfg.gridY0, Cfg.gridX8, Cfg.gridY24, Color.RED);
		Gfx.drawLine(Cfg.gridX9, Cfg.gridY0, Cfg.gridX9, Cfg.gridY24, Color.RED);
		Gfx.drawLine(Cfg.gridX10, Cfg.gridY0, Cfg.gridX10, Cfg.gridY24, Color.RED);
		Gfx.drawLine(Cfg.gridX11, Cfg.gridY0, Cfg.gridX11, Cfg.gridY24, Color.RED);
		Gfx.drawLine(Cfg.gridX12, Cfg.gridY0, Cfg.gridX12, Cfg.gridY24, Color.RED);
		Gfx.drawLine(Cfg.gridX13, Cfg.gridY0, Cfg.gridX13, Cfg.gridY24, Color.RED);
		Gfx.drawLine(Cfg.gridX14, Cfg.gridY0, Cfg.gridX14, Cfg.gridY24, Color.RED);
		Gfx.drawLine(Cfg.gridX15, Cfg.gridY0, Cfg.gridX15, Cfg.gridY24, Color.RED);
		Gfx.drawLine(Cfg.gridX16, Cfg.gridY0, Cfg.gridX16, Cfg.gridY24, Color.RED);
		Gfx.drawLine(Cfg.gridX17, Cfg.gridY0, Cfg.gridX17, Cfg.gridY24, Color.RED);
		Gfx.drawLine(Cfg.gridX18, Cfg.gridY0, Cfg.gridX18, Cfg.gridY24, Color.RED);
		Gfx.drawLine(Cfg.gridX19, Cfg.gridY0, Cfg.gridX19, Cfg.gridY24, Color.RED);
		Gfx.drawLine(Cfg.gridX20, Cfg.gridY0, Cfg.gridX20, Cfg.gridY24, Color.RED);
		Gfx.drawLine(Cfg.gridX21, Cfg.gridY0, Cfg.gridX21, Cfg.gridY24, Color.RED);
		Gfx.drawLine(Cfg.gridX22, Cfg.gridY0, Cfg.gridX22, Cfg.gridY24, Color.RED);
		Gfx.drawLine(Cfg.gridX23, Cfg.gridY0, Cfg.gridX23, Cfg.gridY24, Color.RED);
		Gfx.drawLine(Cfg.gridX24, Cfg.gridY0, Cfg.gridX24, Cfg.gridY24, Color.GREEN);
	}
}
