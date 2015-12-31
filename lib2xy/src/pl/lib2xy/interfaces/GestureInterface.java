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

package pl.lib2xy.interfaces;

import com.badlogic.gdx.math.Vector2;

public interface GestureInterface{

	public void onZoom(float initialDistance, float distance);

	public void onTap(float x, float y, int count, int button);

	public void onPinch(Vector2 initialPointer1, Vector2 initialPointer2, Vector2 pointer1, Vector2 pointer2);

	public void onPan(float x, float y, float deltaX, float deltaY);

	public void onPanStop(float x, float y, int pointer, int button);

	public void onLongPress(float x, float y);

	public void onFling(float velocityX, float velocityY, int button);
}
