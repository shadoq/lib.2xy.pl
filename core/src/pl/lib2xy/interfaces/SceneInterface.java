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

public interface SceneInterface{

	public abstract void initialize();

	public abstract void update(float delta);

	public abstract void preRender(float delta);

	public abstract void render(float delta);

	public abstract void postRender(float delta);

	public abstract void pause();

	public abstract void resume();

	public abstract void dispose();

	public abstract void resize();


}
