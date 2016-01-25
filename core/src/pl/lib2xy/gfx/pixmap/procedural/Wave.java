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

/**
 * @author Jarek
 */
public class Wave implements ProceduralInterface{

	private static int wave_counter = 0;

	/**
	 * @param pixmap
	 * @param wave_step_width
	 * @param wave_wave_lenght
	 * @param wave_dir
	 * @param wave_change_r
	 * @param wave_change_g
	 * @param wave_change_b
	 * @param wave_value_r
	 * @param wave_value_g
	 * @param wave_value_b
	 */
	public static void generate(final Pixmap pixmap, int wave_step_width, int wave_wave_lenght, int wave_dir,
								boolean wave_change_r, boolean wave_change_g, boolean wave_change_b,
								int wave_value_r, int wave_value_g, int wave_value_b){

		int width = pixmap.getWidth();
		int height = pixmap.getHeight();

		int ce = 0;
		wave_counter += wave_step_width;

		for (int x = 0; x < width; x++){
			for (int y = 0; y < height; y++){

				switch (wave_dir){
					default:
						ce = x + y + wave_counter;
						break;
					case 1:
						ce = x - y - wave_counter;
						break;
					case 2:
						ce = x - y + wave_counter;
						break;
					case 3:
						ce = x + y - wave_counter;
						break;


					case 4:
						ce = y + wave_counter;
						break;
					case 5:
						ce = y - wave_counter;
						break;
					case 6:
						ce = x - wave_counter;
						break;
					case 7:
						ce = x + wave_counter;
						break;
				}

				int r = 0;
				int g = 0;
				int b = 0;
				if (wave_change_r){
					r = (int) Math.ceil(255.0D - Math.abs(Math.sin(wave_wave_lenght / 200.0D * ce / 31.0D * 3.1415D)) * 255.0D);
				} else {
					r = wave_value_r;
				}
				if (wave_change_g){
					g = (int) Math.ceil(255.0D - Math.abs(Math.sin(wave_wave_lenght / 200.0D * ce / 31.0D * 3.1415D)) * 255.0D);
				} else {
					g = wave_value_g;
				}
				if (wave_change_b){
					b = (int) Math.ceil(255.0D - Math.abs(Math.sin(wave_wave_lenght / 200.0D * ce / 31.0D * 3.1415D)) * 255.0D);
				} else {
					b = wave_value_b;
				}

				//
				// Draw pixel
				//
				pixmap.drawPixel(x, y, (r << 24) | (g << 16) | (b << 8) | 255);
			}
		}
	}

	/**
	 * @param pixmap
	 */
	@Override
	public void generate(final Pixmap pixmap){
		generate(pixmap, 20, 20, 0, true, true, true, 255, 255, 255);
	}

	@Override
	public void random(Pixmap pixmap){
		generate(pixmap, 20, 20, 0, true, true, true, 255, 255, 255);
	}
}
