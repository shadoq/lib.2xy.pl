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

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import pl.lib2xy.app.Cfg;
import pl.lib2xy.app.Gfx;
import pl.lib2xy.app.Scene;
import pl.lib2xy.gfx.util.GradientUtil;

/**
 * @author Jarek
 */
public class GfxGradientTest extends Scene{

	private Pixmap pixmap;
	private Texture texture;
	private Color col;

	@Override
	public void initialize(){

		pixmap = new Pixmap(Cfg.proceduralTextureSize, Cfg.proceduralTextureSize, Pixmap.Format.RGBA8888);
		texture = new Texture(pixmap);
		texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

		for (int y = 0; y < Cfg.proceduralTextureSize; y++){
			for (int x = 0; x < (Cfg.proceduralTextureSize / 2); x++){

				col = GradientUtil.getColorPalleteUncache((float) x / Cfg.proceduralTextureSize * 2, y / 5);
				pixmap.drawPixel(x, y, Color.rgba8888(col.r, col.g, col.b, col.a));
				col = GradientUtil.getColorPallete((float) x / Cfg.proceduralTextureSize * 2, y / 5);
				pixmap.drawPixel(x + Cfg.proceduralTextureSize / 2, y, Color.rgba8888(col.r, col.g, col.b, col.a));
			}
		}

		texture.draw(pixmap, 0, 0);
	}

	@Override
	public void render(float delta){
		Gfx.drawBackground(texture);
	}
}
