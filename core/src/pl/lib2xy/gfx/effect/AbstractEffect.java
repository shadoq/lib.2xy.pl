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

package pl.lib2xy.gfx.effect;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ArrayMap;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;

/**
 * Abstract effect class
 */
public abstract class AbstractEffect{

	protected float duration = 0;
	protected Actor parentActor;

	public Actor getParentActor(){
		return parentActor;
	}

	public void setParentActor(Actor parentActor){
		this.parentActor = parentActor;
	}

	abstract public void init();

	abstract public void start();

	abstract public void stop();

	abstract public void update(float effectTime, float sceneTime, float endTime, float procent, boolean isPause);

	abstract public void preRender();

	abstract public void render(Batch batch, float parentAlpha);

	abstract public void postRender();

	abstract public void getGuiDefinition(final Array<String[]> guiDef);

	abstract public void getValues(final ArrayMap<String, String> values);

	abstract public void setValues(final String changeKey, final ArrayMap<String, String> values);

	abstract public void read(final Json json, final JsonValue jsonData);

	abstract public void write(final Json json, final Object objectWrite);
}
