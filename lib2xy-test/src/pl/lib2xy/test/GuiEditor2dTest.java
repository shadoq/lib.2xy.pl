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

import pl.lib2xy.XY;
import pl.lib2xy.app.Scene;
import pl.lib2xy.gui.dialog.Editor2d;

/**
 * @author Jarek
 */
public class GuiEditor2dTest extends Scene{

	private Editor2d editor2d;

	@Override
	public void initialize(){
		editor2d = new Editor2d();
		editor2d.create(XY.centerX - 220, XY.centerY - 120, 440, 240);
		editor2d.show();
	}
}
