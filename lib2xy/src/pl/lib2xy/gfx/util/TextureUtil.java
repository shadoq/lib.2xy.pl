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

package pl.lib2xy.gfx.util;

import pl.lib2xy.app.MathUtil;

import java.nio.FloatBuffer;

/**
 * @author Jarek
 */
public class TextureUtil{

	/**
	 * @param centerX
	 * @param centerY
	 * @param width
	 * @param height
	 * @param vertexCoords
	 * @param offset
	 */
	public static void calculateVertex2DCenterCoords(float centerX, float centerY, float width, float height, FloatBuffer vertexCoords, int offset){
		width = width / 2;
		height = height / 2;
		float cxmw = (centerX - width);
		float cxpw = (centerX + width);
		float cymh = (centerY - height);
		float cyph = (centerY + height);
		vertexCoords.position(offset);
		vertexCoords.put(cxmw);
		vertexCoords.put(cymh);
		vertexCoords.put(cxmw);
		vertexCoords.put(cyph);
		vertexCoords.put(cxpw);
		vertexCoords.put(cymh);
		vertexCoords.put(cxpw);
		vertexCoords.put(cyph);
		vertexCoords.position(0);
	}

	/**
	 * @param topX
	 * @param topY
	 * @param width
	 * @param height
	 * @param vertexCoords
	 * @param offset
	 */
	public static void calculateVertex2DTopCoords(float topX, float topY, float width, float height, FloatBuffer vertexCoords, int offset){
		float cxmw = (topX);
		float cxpw = (topX + width);
		float cymh = (topY);
		float cyph = (topY + height);
		vertexCoords.position(offset);
		vertexCoords.put(cxmw);
		vertexCoords.put(cymh);
		vertexCoords.put(cxmw);
		vertexCoords.put(cyph);
		vertexCoords.put(cxpw);
		vertexCoords.put(cymh);
		vertexCoords.put(cxpw);
		vertexCoords.put(cyph);
		vertexCoords.position(0);
	}

	/**
	 * @param centerX
	 * @param centerY
	 * @param width
	 * @param height
	 * @param vertexCoords
	 * @param offset
	 */
	public static void calculateVertex2DEfectCoords(float centerX, float centerY, float width, float height, FloatBuffer vertexCoords, int offset, float angleX,
													float angleY, float scaleX, float scaleY){
		width = width / 2;
		height = height / 2;
		float vertexMinX = (centerX - width - MathUtil.fastCos(angleX) * scaleX);
		float vertexMaxX = (centerX + width + MathUtil.fastCos(angleX) * scaleX);
		float veretxMinY = (centerY - height - MathUtil.fastSin(angleY) * scaleY);
		float vertexMaxY = (centerY + height + MathUtil.fastSin(angleY) * scaleY);
		vertexCoords.position(offset);
		vertexCoords.put(vertexMinX);
		vertexCoords.put(veretxMinY);
		vertexCoords.put(vertexMinX);
		vertexCoords.put(vertexMaxY);
		vertexCoords.put(vertexMaxX);
		vertexCoords.put(veretxMinY);
		vertexCoords.put(vertexMaxX);
		vertexCoords.put(vertexMaxY);
		vertexCoords.position(0);
	}

	/**
	 * @param texturePreRow
	 * @param texturePerColumn
	 * @param index
	 * @param textureCoords
	 * @param offset
	 */
	public static void calculateTexture2DCoords(int texturePreRow, int texturePerColumn, int index, FloatBuffer textureCoords, int offset){
		float xStep = 1.0F / texturePreRow;
		float yStep = 1.0F / texturePerColumn;
		float x = index % texturePreRow;
		float y = index / texturePreRow;
		float xMin = x * xStep;
		float xMax = xMin + (xStep);
		float yMin = y * yStep;
		float yMax = yMin + (yStep);
		textureCoords.position(offset);
		textureCoords.put(xMin);
		textureCoords.put(yMin);
		textureCoords.put(xMin);
		textureCoords.put(yMax);
		textureCoords.put(xMax);
		textureCoords.put(yMin);
		textureCoords.put(xMax);
		textureCoords.put(yMax);
		textureCoords.position(0);
	}

}
