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
import pl.lib2xy.XY;

import java.util.Random;

/**
 * @author Jarek
 */
public class Falling implements ProceduralInterface{

	static Random rnd = new Random();
	static float randomCounter = 0;
	static boolean[] isActive = new boolean[256];
	static int[] position = new int[256];

	/**
	 * @param pixmap
	 * @param length
	 * @param number
	 * @param direction
	 * @param randomColor
	 * @param randomIntervall
	 * @param redValue
	 * @param greenValue
	 * @param blueValue
	 */
	public static void generate(final Pixmap pixmap, int length, float number, int direction, int randomColor, float randomIntervall, int redValue,
								int greenValue, int blueValue){

		int width = pixmap.getWidth();
		int height = pixmap.getHeight();

		int rgb = 0;
		int r = 255;
		int g = 255;
		int b = 255;
		int a = 255;

		if (randomColor > 0){
			if (randomCounter > randomIntervall){
				redValue = (int) (rnd.nextDouble() * 255.0D);
				greenValue = (int) (rnd.nextDouble() * 255.0D);
				blueValue = (int) (rnd.nextDouble() * 255.0D);
				randomCounter = 0;
			} else {
				randomCounter += XY.deltaTime;
			}
		}

		if (width > height){
			if (width > isActive.length){
				isActive = new boolean[width];
				position = new int[width];
			}
		} else {
			if (height > isActive.length){
				isActive = new boolean[height];
				position = new int[height];
			}
		}
		switch (direction){
			//
			// Botton
			//
			default:
				for (int x = 0; x < width; x++){
					for (int y = 1; y < height; y++){
						rgb = pixmap.getPixel(x, height - y - 1);
						pixmap.drawPixel(x, height - y, rgb);
					}
				}

				for (int x = 0; x < width; x++){
					int ra = (int) (rnd.nextDouble() * 100.0D);

					if (ra < number){
						isActive[x] = true;
						position[x] = 0;
					}

					if (isActive[x]){
						r = (int) (redValue - position[x] * Math.floor(redValue / length));
						g = (int) (greenValue - position[x] * Math.floor(greenValue / length));
						b = (int) (blueValue - position[x] * Math.floor(blueValue / length));

						//
						// Clamp
						//
						r = (r < 255) ? r : 255;
						r = (r > 0) ? r : 0;
						g = (g < 255) ? g : 255;
						g = (g > 0) ? g : 0;
						b = (b < 255) ? b : 255;
						b = (b > 0) ? b : 0;

						pixmap.drawPixel(x, 0, (r << 24) | (g << 16) | (b << 8) | a);
						position[x] += 1;

						if (position[x] <= length){
							continue;
						}
						isActive[x] = false;
						position[x] = 0;
					} else {
						pixmap.drawPixel(x, 0, 0);
					}
				}
				break;
			//
			// Top
			//
			case 1:
				for (int x = 0; x < width; x++){
					for (int y = 0; y < height - 1; y++){
						rgb = pixmap.getPixel(x, y + 1);
						pixmap.drawPixel(x, y, rgb);
					}
				}

				for (int x = 0; x < width; x++){
					int ra = (int) (rnd.nextDouble() * 100.0D);

					if (ra < number){
						isActive[x] = true;
						position[x] = 0;
					}

					if (isActive[x]){
						r = (int) (redValue - position[x] * Math.floor(redValue / length));
						g = (int) (greenValue - position[x] * Math.floor(greenValue / length));
						b = (int) (blueValue - position[x] * Math.floor(blueValue / length));

						//
						// Clamp
						//
						r = (r < 255) ? r : 255;
						r = (r > 0) ? r : 0;
						g = (g < 255) ? g : 255;
						g = (g > 0) ? g : 0;
						b = (b < 255) ? b : 255;
						b = (b > 0) ? b : 0;

						pixmap.drawPixel(x, height - 1, (r << 24) | (g << 16) | (b << 8) | a);
						position[x] += 1;

						if (position[x] <= length){
							continue;
						}
						isActive[x] = false;
						position[x] = 0;
					} else {
						pixmap.drawPixel(x, height - 1, 0);
					}
				}
				break;
			//
			// Right
			//
			case 2:
				for (int x = 1; x < width; x++){
					for (int y = 0; y < height; y++){
						rgb = pixmap.getPixel(width - x - 1, y);
						pixmap.drawPixel(width - x, y, rgb);
					}
				}

				for (int y = 0; y < height; y++){
					int ra = (int) (rnd.nextDouble() * 100.0D);

					if (ra < number){
						isActive[y] = true;
						position[y] = 0;
					}

					if (isActive[y]){
						r = (int) (redValue - position[y] * Math.floor(redValue / length));
						g = (int) (greenValue - position[y] * Math.floor(greenValue / length));
						b = (int) (blueValue - position[y] * Math.floor(blueValue / length));

						//
						// Clamp
						//
						r = (r < 255) ? r : 255;
						r = (r > 0) ? r : 0;
						g = (g < 255) ? g : 255;
						g = (g > 0) ? g : 0;
						b = (b < 255) ? b : 255;
						b = (b > 0) ? b : 0;

						pixmap.drawPixel(0, y, (r << 24) | (g << 16) | (b << 8) | a);
						position[y] += 1;

						if (position[y] <= length){
							continue;
						}
						isActive[y] = false;
						position[y] = 0;
					} else {
						pixmap.drawPixel(0, y, 0);
					}
				}
				break;
			//
			// Left
			//
			case 3:
				for (int x = 0; x < width - 1; x++){
					for (int y = 0; y < height; y++){
						rgb = pixmap.getPixel(x + 1, y);
						pixmap.drawPixel(x, y, rgb);
					}
				}

				for (int y = 0; y < height; y++){
					int ra = (int) (rnd.nextDouble() * 100.0D);

					if (ra < number){
						isActive[y] = true;
						position[y] = 0;
					}

					if (isActive[y]){
						r = (int) (redValue - position[y] * Math.floor(redValue / length));
						g = (int) (greenValue - position[y] * Math.floor(greenValue / length));
						b = (int) (blueValue - position[y] * Math.floor(blueValue / length));

						//
						// Clamp
						//
						r = (r < 255) ? r : 255;
						r = (r > 0) ? r : 0;
						g = (g < 255) ? g : 255;
						g = (g > 0) ? g : 0;
						b = (b < 255) ? b : 255;
						b = (b > 0) ? b : 0;

						pixmap.drawPixel(width - 1, y, (r << 24) | (g << 16) | (b << 8) | a);
						position[y] += 1;

						if (position[y] <= length){
							continue;
						}
						isActive[y] = false;
						position[y] = 0;
					} else {
						pixmap.drawPixel(width - 1, y, 0);
					}
				}
				break;

		}
	}

	/**
	 * @param pixmap
	 */
	@Override
	public void generate(final Pixmap pixmap){
		generate(pixmap, 20, 30, 0, 0, 20, 255, 255, 0);
	}

	@Override
	public void random(Pixmap pixmap){
		generate(pixmap, (int) (1 + Math.random() * 20), (int) (10 + Math.random() * 30), (int) (Math.random() * 4), (int) (Math.random() * 2), 20, 255, 255,
				 255);
	}
}