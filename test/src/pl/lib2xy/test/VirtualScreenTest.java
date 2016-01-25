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

import com.badlogic.gdx.graphics.Color;

import pl.lib2xy.XY;
import pl.lib2xy.app.Gfx;
import pl.lib2xy.app.Scene;

/**
 * @author Jarek
 */
public class VirtualScreenTest extends Scene{

	@Override
	public void render(float delta){

		Gfx.setColor(Color.GRAY);
		Gfx.drawGrid(0, 0, XY.width, XY.height, 10, 10);
		Gfx.setColor(Color.RED);
		Gfx.drawCircle(XY.centerX, XY.centerY, 100);
		Gfx.setColor(Color.YELLOW);
		Gfx.drawCircle(XY.centerX, XY.centerY, 200);

	}
}
