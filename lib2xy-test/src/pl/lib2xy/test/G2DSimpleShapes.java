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
import pl.lib2xy.XY;
import pl.lib2xy.app.Gfx;
import pl.lib2xy.app.Scene;

/**
 * @author Jarek
 */
public class G2DSimpleShapes extends Scene{
	@Override
	public void render(float delta){

		Gfx.setColor(Color.YELLOW);
		Gfx.drawCircle(XY.centerX, XY.centerY, 100);
		Gfx.setColor(Color.GRAY);
		Gfx.drawCircle(XY.centerX, XY.centerY, 50);

		Gfx.setColor(Color.GREEN);
		Gfx.drawFilledRectangle(XY.centerX, XY.centerY + 30, 10, 10, 0, Color.RED);
		Gfx.drawFilledRectangle(XY.centerX, XY.centerY + 50, 10, 10, 0, new Color(0.5f, 0.5f, 0.5f, 0.5f));
		Gfx.drawFilledRectangle(XY.centerX, XY.centerY + 50, 10, 10, 30, Color.CYAN);

		Gfx.setColor(Color.BLUE);
		Gfx.drawLine(XY.centerX - 200, XY.centerY, XY.centerX + 200, XY.centerY);
		Gfx.setColor(Color.RED);
		Gfx.drawLine(XY.centerX, XY.centerY - 200, XY.centerX, XY.centerY + 200);
	}
}
