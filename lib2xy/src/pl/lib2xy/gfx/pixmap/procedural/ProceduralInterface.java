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
 * A interface for procedural texture filter and pixamp generator
 *
 * @author Jaroslaw Czub (http://shad.net.pl)
 */
public interface ProceduralInterface{
	public static final boolean DEBUG = false;

	public void generate(final Pixmap pixmap);

	public void random(final Pixmap pixmap);
}
