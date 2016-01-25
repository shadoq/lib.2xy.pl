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

package pl.lib2xy.gfx.pixmap.procedural;

import com.badlogic.gdx.graphics.Pixmap;
import pl.lib2xy.gfx.math.PerlinNoise;

/**
 * @author Jarek
 */
public class PerlinNoise2Dv2 implements ProceduralInterface{

	/**
	 * @param pixmap
	 * @param amplitude
	 * @param frequency
	 * @param octaves
	 */
	public static void generate(final Pixmap pixmap, float amplitude, float frequency, int octaves){

		int width = pixmap.getWidth();
		int height = pixmap.getHeight();

		int grey = 0;
		for (int y = 0; y < height; y++){
			for (int x = 0; x < width; x++){
				grey = PerlinNoise.fastNoise(x, y, amplitude, frequency, octaves);
				pixmap.drawPixel(x, y, (grey << 24) | (grey << 16) | (grey << 8) | 255);
			}
		}
	}

	/**
	 * @param pixmap
	 */
	@Override
	public void generate(final Pixmap pixmap){
		generate(pixmap, 4.0f, 5.0f, 1);
	}

	@Override
	public void random(Pixmap pixmap){
		generate(pixmap, (float) Math.random(), (float) Math.random(), 8);
	}
}
