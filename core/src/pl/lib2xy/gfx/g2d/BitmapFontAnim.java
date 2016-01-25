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

package pl.lib2xy.gfx.g2d;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.BitmapFontCache;
import com.badlogic.gdx.math.Interpolation;
import pl.lib2xy.app.MathUtil;


/**
 * @author Jarek
 */
public class BitmapFontAnim{

	private final BitmapFont font;
	private final BitmapFontCache cache;
	private float x;
	private float y;
	private int textLengh;
	private float[] vertices;
	private float[] copyVertices;
	private float tempX;
	private float tempY;
	private float tempX2;
	private float tempY2;
	private float tempWidth;
	private float tempHeight;
	private float tempCenterX;
	private float tempCenterY;

	/**
	 * @param cache
	 */
	public BitmapFontAnim(BitmapFontCache cache){
		this.cache = cache;
		font = cache.getFont();
		x = cache.getX();
		y = cache.getY();
		vertices = cache.getVertices();
		copyVertices = new float[cache.getVertices().length];
		System.arraycopy(cache.getVertices(), 0, copyVertices, 0, cache.getVertices().length);
		textLengh = copyVertices.length / 20;
	}

	/**
	 * @param time
	 * @param start
	 * @param end
	 * @param interpolation
	 */
	public void size(float time, float start, float end, Interpolation interpolation){
		time = MathUtil.fastCos(time);
		float range = interpolation.apply(start, end, time) / 2;

		int idx = 0;
		for (int i = 0; i < textLengh; ++i){

			//
			// Getting position value
			//
			tempX = copyVertices[idx];
			tempY = copyVertices[idx + 1];

			tempX2 = copyVertices[idx + 10];
			tempY2 = copyVertices[idx + 6];

			tempWidth = tempX2 - tempX;
			tempHeight = tempY2 - tempY;

			tempCenterX = (tempX2 + tempX) / 2;
			tempCenterY = (tempY2 + tempY) / 2;

			//
			// Calculate new value
			//
			tempX = tempCenterX - range;
			tempX2 = tempCenterX + range;

			tempY = tempCenterY - range;
			tempY2 = tempCenterY + range;

			//
			// Setting new value on oryginal vertext array
			//
			vertices[idx] = tempX;
			vertices[idx + 1] = tempY;

			vertices[idx + 5] = tempX;
			vertices[idx + 6] = tempY2;

			vertices[idx + 10] = tempX2;
			vertices[idx + 11] = tempY2;

			vertices[idx + 15] = tempX2;
			vertices[idx + 16] = tempY;

			idx = idx + 20;
		}
	}

	/**
	 * @param time
	 * @param step
	 * @param start
	 * @param end
	 * @param interpolation
	 */
	public void wobblingSize(float time, float step, float start, float end, Interpolation interpolation){

		float range = 0;
		float fxTime = time;
		int idx = 0;
		for (int i = 0; i < textLengh; ++i){

			fxTime = fxTime + step;
			time = MathUtil.fastCos(fxTime);
			range = interpolation.apply(start, end, time) / 2;

			//
			// Getting position value
			//
			tempX = copyVertices[idx];
			tempY = copyVertices[idx + 1];

			tempX2 = copyVertices[idx + 10];
			tempY2 = copyVertices[idx + 6];

			tempWidth = tempX2 - tempX;
			tempHeight = tempY2 - tempY;

			tempCenterX = (tempX2 + tempX) / 2;
			tempCenterY = (tempY2 + tempY) / 2;

			//
			// Calculate new value
			//
			tempX = tempCenterX - range;
			tempX2 = tempCenterX + range;

			tempY = tempCenterY - range;
			tempY2 = tempCenterY + range;

			//
			// Setting new value on oryginal vertext array
			//
			vertices[idx] = tempX;
			vertices[idx + 1] = tempY;

			vertices[idx + 5] = tempX;
			vertices[idx + 6] = tempY2;

			vertices[idx + 10] = tempX2;
			vertices[idx + 11] = tempY2;

			vertices[idx + 15] = tempX2;
			vertices[idx + 16] = tempY;

			idx = idx + 20;
		}
	}

	/**
	 * @param time
	 * @param startX
	 * @param startY
	 * @param endX
	 * @param endY
	 * @param interpolation
	 */
	public void position(float time, float startX, float startY, float endX, float endY, Interpolation interpolation){

		float timeX = MathUtil.fastCos(time);
		float timeY = MathUtil.fastSin(time);
		float rangeX = interpolation.apply(startX, endX, timeX);
		float rangeY = interpolation.apply(startY, endY, timeY);

		int idx = 0;
		for (int i = 0; i < textLengh; ++i){

			//
			// Getting position value
			//
			tempX = copyVertices[idx];
			tempY = copyVertices[idx + 1];

			tempX2 = copyVertices[idx + 10];
			tempY2 = copyVertices[idx + 6];

			tempWidth = tempX2 - tempX;
			tempHeight = tempY2 - tempY;

			tempCenterX = (tempX2 + tempX) / 2;
			tempCenterY = (tempY2 + tempY) / 2;

			//
			// Calculate new value
			//
			tempX = tempX + rangeX;
			tempX2 = tempX2 + rangeX;

			tempY = tempY + rangeY;
			tempY2 = tempY2 + rangeY;

			//
			// Setting new value on oryginal vertext array
			//
			vertices[idx] = tempX;
			vertices[idx + 1] = tempY;

			vertices[idx + 5] = tempX;
			vertices[idx + 6] = tempY2;

			vertices[idx + 10] = tempX2;
			vertices[idx + 11] = tempY2;

			vertices[idx + 15] = tempX2;
			vertices[idx + 16] = tempY;

			idx = idx + 20;
		}
	}

	/**
	 * @param time
	 * @param stepX
	 * @param stepY
	 * @param startX
	 * @param startY
	 * @param endX
	 * @param endY
	 * @param interpolation
	 */
	public void wobblingPosition(float time, float stepX, float stepY, float startX, float startY, float endX, float endY, Interpolation interpolation){

		float timeX = time;
		float timeY = time;
		float rangeX = 0;
		float rangeY = 0;
		float impX = 0;
		float impY = 0;

		int idx = 0;
		for (int i = 0; i < textLengh; ++i){

			timeX = timeX + stepX;
			timeY = timeY + stepY;
			impX = MathUtil.fastCos(timeX);
			impY = MathUtil.fastSin(timeY);
			rangeX = interpolation.apply(startX, endX, impX);
			rangeY = interpolation.apply(startY, endY, impY);

			//
			// Getting position value
			//
			tempX = copyVertices[idx];
			tempY = copyVertices[idx + 1];

			tempX2 = copyVertices[idx + 10];
			tempY2 = copyVertices[idx + 6];

			tempWidth = tempX2 - tempX;
			tempHeight = tempY2 - tempY;

			tempCenterX = (tempX2 + tempX) / 2;
			tempCenterY = (tempY2 + tempY) / 2;

			//
			// Calculate new value
			//
			tempX = tempX + rangeX;
			tempX2 = tempX2 + rangeX;

			tempY = tempY + rangeY;
			tempY2 = tempY2 + rangeY;

			//
			// Setting new value on oryginal vertext array
			//
			vertices[idx] = tempX;
			vertices[idx + 1] = tempY;

			vertices[idx + 5] = tempX;
			vertices[idx + 6] = tempY2;

			vertices[idx + 10] = tempX2;
			vertices[idx + 11] = tempY2;

			vertices[idx + 15] = tempX2;
			vertices[idx + 16] = tempY;

			idx = idx + 20;
		}
	}

	/**
	 * @param time
	 * @param stepSizeX
	 * @param stepSizeY
	 * @param stepPosX
	 * @param stepPosY
	 * @param startSizeX
	 * @param startSizeY
	 * @param endSizeX
	 * @param endSizeY
	 * @param startPosX
	 * @param startPosY
	 * @param endPosX
	 * @param endPosY
	 * @param interpolationSize
	 * @param interpolationPosistion
	 */
	public void wobblingSizeAndPosition(float time, float stepSizeX, float stepSizeY, float stepPosX, float stepPosY, float startSizeX, float startSizeY,
										float endSizeX, float endSizeY, float startPosX, float startPosY, float endPosX, float endPosY,
										Interpolation interpolationSize, Interpolation interpolationPosistion){

		float deltaTimeSizeX = time;
		float deltaTimeSizeY = time;
		float deltaTimePosX = time;
		float deltaTimePosY = time;
		float impSizeX = 0;
		float impSizeY = 0;
		float impPosX = 0;
		float impPosY = 0;

		int idx = 0;
		for (int i = 0; i < textLengh; ++i){
			deltaTimeSizeX = deltaTimeSizeX + stepSizeX;
			deltaTimeSizeY = deltaTimeSizeY + stepSizeY;
			deltaTimePosX = deltaTimePosX + stepPosX;
			deltaTimePosY = deltaTimePosY + stepPosY;
			impSizeX = interpolationSize.apply(startSizeX, endSizeX, MathUtil.fastCos(deltaTimeSizeX)) / 2;
			impSizeY = interpolationSize.apply(startSizeY, endSizeY, MathUtil.fastSin(deltaTimeSizeY)) / 2;
			impPosX = interpolationPosistion.apply(startPosX, endPosX, MathUtil.fastCos(deltaTimePosX));
			impPosY = interpolationPosistion.apply(startPosY, endPosY, MathUtil.fastSin(deltaTimePosY));

			//
			// Getting position value
			//
			tempX = copyVertices[idx];
			tempY = copyVertices[idx + 1];

			tempX2 = copyVertices[idx + 10];
			tempY2 = copyVertices[idx + 6];

			tempWidth = tempX2 - tempX;
			tempHeight = tempY2 - tempY;

			tempCenterX = (tempX2 + tempX) / 2;
			tempCenterY = (tempY2 + tempY) / 2;

			//
			// Calculate new value
			//
			tempX = tempX + impPosX - impSizeX;
			tempX2 = tempX2 + impPosX + impSizeX;

			tempY = tempY + impPosY - impSizeY;
			tempY2 = tempY2 + impPosY + impSizeY;

			//
			// Setting new value on oryginal vertext array
			//
			vertices[idx] = tempX;
			vertices[idx + 1] = tempY;

			vertices[idx + 5] = tempX;
			vertices[idx + 6] = tempY2;

			vertices[idx + 10] = tempX2;
			vertices[idx + 11] = tempY2;

			vertices[idx + 15] = tempX2;
			vertices[idx + 16] = tempY;

			idx = idx + 20;
		}
	}

	/**
	 * @param time
	 * @param speedX
	 * @param speedY
	 */
	public void scroll(float time, float speedX, float speedY){

		speedX = speedX * time;
		speedY = speedY * time;

		int idx = 0;
		for (int i = 0; i < textLengh; ++i){
			//
			// Getting position value
			//
			tempX = copyVertices[idx];
			tempY = copyVertices[idx + 1];

			tempX2 = copyVertices[idx + 10];
			tempY2 = copyVertices[idx + 6];

			tempWidth = tempX2 - tempX;
			tempHeight = tempY2 - tempY;

			tempCenterX = (tempX2 + tempX) / 2;
			tempCenterY = (tempY2 + tempY) / 2;

			//
			// Calculate new value
			//
			tempX = tempX + speedX;
			tempX2 = tempX2 + speedX;

			tempY = tempY + speedY;
			tempY2 = tempY2 + speedY;

			//
			// Setting new value on oryginal vertext array
			//
			vertices[idx] = tempX;
			vertices[idx + 1] = tempY;

			vertices[idx + 5] = tempX;
			vertices[idx + 6] = tempY2;

			vertices[idx + 10] = tempX2;
			vertices[idx + 11] = tempY2;

			vertices[idx + 15] = tempX2;
			vertices[idx + 16] = tempY;

			idx = idx + 20;
		}
	}

	/**
	 * @param time
	 * @param speedX
	 * @param wobbingSpeed
	 * @param amplitudeY
	 * @param multiplerX
	 */
	public void sinusScroll(float time, float speedX, float wobbingSpeed, float amplitudeY, float multiplerX){

		speedX = speedX * time * 10;
		multiplerX = multiplerX * 10;
		time = time * wobbingSpeed * 10;

		float angleY = 0;
		float moveY = 0;
		int idx = 0;
		int index = 0;
		for (int i = 0; i < textLengh; ++i){

			angleY = (time + i * multiplerX) * MathUtil.DIV_PI2_360;

			//
			// Fast version: moveY=MathUtil.fastSin(angleY) * amplitudeX;
			//
			index = (int) (angleY * MathUtil.factor) % MathUtil.cacheSize;
			moveY = ((index < 0) ? MathUtil.sinCache[index + MathUtil.cacheSize] : MathUtil.sinCache[index]) * amplitudeY;

			//
			// Getting position value
			//
			tempX = copyVertices[idx];
			tempY = copyVertices[idx + 1];

			tempX2 = copyVertices[idx + 10];
			tempY2 = copyVertices[idx + 6];

			tempWidth = tempX2 - tempX;
			tempHeight = tempY2 - tempY;

			tempCenterX = (tempX2 + tempX) / 2;
			tempCenterY = (tempY2 + tempY) / 2;

			//
			// Calculate new value
			//
			tempX = tempX + speedX;
			tempX2 = tempX2 + speedX;

			tempY = tempY + moveY;
			tempY2 = tempY2 + moveY;

			//
			// Setting new value on oryginal vertext array
			//
			vertices[idx] = tempX;
			vertices[idx + 1] = tempY;

			vertices[idx + 5] = tempX;
			vertices[idx + 6] = tempY2;

			vertices[idx + 10] = tempX2;
			vertices[idx + 11] = tempY2;

			vertices[idx + 15] = tempX2;
			vertices[idx + 16] = tempY;

			idx = idx + 20;
		}
	}
}
