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

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ArrayMap;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import pl.lib2xy.XY;
import pl.lib2xy.app.Cfg;
import pl.lib2xy.app.Gfx;
import pl.lib2xy.app.ResourceManager;

/**
 * @author Jarek
 */
public class SimpleShader extends AbstractEffect{

	private Texture texture = new Texture(1, 1, Pixmap.Format.RGBA8888);
	private Color color;
	private Pixmap pixmap;
	private pl.lib2xy.gfx.g3d.shaders.SimpleShader shader;

	/**
	 *
	 */
	public SimpleShader(){
		init();
	}

	/**
	 * @param random
	 */
	public SimpleShader(boolean random){
		this();
	}

	/**
	 *
	 */
	@Override
	public final void init(){
		if (pixmap == null){
			pixmap = new Pixmap(Cfg.proceduralTextureSize, 1, Pixmap.Format.RGBA8888);
			pixmap.setColor(1, 1, 1, 1);
			pixmap.fill();
			texture = new Texture(pixmap);
		}
		if (shader == null){
			shader = new pl.lib2xy.gfx.g3d.shaders.SimpleShader();
		}
	}

	/**
	 *
	 */
	@Override
	public void start(){
	}

	/**
	 *
	 */
	@Override
	public void stop(){
	}

	/**
	 *
	 */
	@Override
	public void update(float effectTime, float sceneTime, float endTime, float procent, boolean isPause){

		if (isPause){
			return;
		}

		duration += XY.deltaTime;
	}

	/**
	 *
	 */
	@Override
	public void preRender(){
	}

	/**
	 * @param batch
	 * @param parentAlpha
	 */
	@Override
	public void render(Batch batch, float parentAlpha){
		final float startX = parentActor.getX();
		final float startY = parentActor.getY();
		final float width = parentActor.getWidth();
		final float height = parentActor.getHeight();
		final float endX = startX + width;
		final float endY = startY + height;
		final float widthDiv2 = parentActor.getWidth() / 2;
		final float heightDiv2 = parentActor.getHeight() / 2;
		final float centerX = startX + widthDiv2;
		final float centerY = startY + heightDiv2;

		shader.setResolutionShader(width, height);
		shader.setTimeShader(duration);
		shader.setMouse(XY.mouseUnproject.x / width, XY.mouseUnproject.y / height);
		Gfx.drawBackground(batch, shader, startX, startY, endX, endY);
	}

	/**
	 *
	 */
	@Override
	public void postRender(){
	}

	/**
	 * @param guiDef
	 */
	@Override
	public void getGuiDefinition(Array<String[]> guiDef){
		guiDef.add(new String[]{"shader", "shader", "SHADER_LIST", ""});
	}

	/**
	 * @param values
	 */
	@Override
	public void getValues(ArrayMap<String, String> values){
		values.put("shader", shader.getName() + ".json");
	}

	/**
	 * @param changeKey
	 * @param values
	 */
	@Override
	public void setValues(String changeKey, ArrayMap<String, String> values){
		if (changeKey.equals("shader")){
			if (values.get("shader") == null || values.get("shader").equals("")){
				shader = ResourceManager.getShaderData("default.json");
			} else {
				shader = ResourceManager.getShaderData(values.get("shader"));
			}
		}
	}

	/**
	 * @param json
	 * @param jsonData
	 */
	@Override
	public void read(Json json, JsonValue jsonData){
		shader = ResourceManager.getShaderData(jsonData.getString("shader"));
	}

	/**
	 * @param json
	 * @param objectWrite
	 */
	@Override
	public void write(Json json, Object objectWrite){

		json.writeValue("shader", shader.getName());

	}
}
