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

package pl.lib2xy.gui.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.ui.Widget;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ArrayMap;
import com.badlogic.gdx.utils.reflect.ClassReflection;
import com.badlogic.gdx.utils.reflect.ReflectionException;
import com.sun.deploy.util.StringUtils;
import pl.lib2xy.app.Log;
import pl.lib2xy.gfx.effect.*;
import pl.lib2xy.gui.GuiUtil;
import pl.lib2xy.interfaces.DataVO;
import pl.lib2xy.interfaces.GuiInterface;
import pl.lib2xy.items.ActorVO;
import pl.lib2xy.items.FxEffectVO;
import pl.lib2xy.utils.TextUtil;

import java.util.Arrays;

public class FxEffect extends Widget implements GuiInterface, DataVO{

	private static final String[] effectList = {
	Wait.class.getSimpleName(),
	BobsMulti.class.getSimpleName(),
	Point.class.getSimpleName(),
	Copper.class.getSimpleName(),
	PointMulti.class.getSimpleName(),
	StarField.class.getSimpleName(),
	Plasma.class.getSimpleName(),
	Grid.class.getSimpleName(),
	SimpleShader.class.getSimpleName(),
	};
	private static final String[] effectClassList = {
	Wait.class.getName(),
	BobsMulti.class.getName(),
	Point.class.getName(),
	Copper.class.getName(),
	PointMulti.class.getName(),
	StarField.class.getName(),
	Plasma.class.getName(),
	Grid.class.getName(),
	SimpleShader.class.getName(),
	};
	private static final String TAG = "FxEffect";

	protected float effectTime = 0;
	protected float effectPercent;
	protected AbstractEffect abstractEffect;

	public FxEffectVO data = new FxEffectVO();

	public FxEffect(){
		data.effect = "";
		data.width = 64;
		data.height = 64;
		data.originX = 32;
		data.originY = 32;
		setVO(data);
		createEffectForName(data.effect);
	}

	public FxEffect(String effectName){
		Log.debug(TAG, "Create effect: " + effectName);
		data.effect = effectName;
		data.width = 64;
		data.height = 64;
		data.originX = 32;
		data.originY = 32;
		setVO(data);
		createEffectForName(data.effect);
	}

	public FxEffect(FxEffectVO fxEffectVO){
		Log.debug(TAG, "Create effect: " + fxEffectVO.effect);
		data = fxEffectVO;
		setVO(data);
		createEffectForName(data.effect);
	}

	private void createEffect(String effectName){

		if (effectName == null || effectName.isEmpty()){
			abstractEffect = new Wait();
			abstractEffect.setParentActor(this);
			abstractEffect.init();
			return;
		}
		abstractEffect = null;

		try{
			abstractEffect = (AbstractEffect) ClassReflection.newInstance(ClassReflection.forName(effectName));
			abstractEffect.setParentActor(this);
			abstractEffect.init();
			abstractEffect.update(0, 0, 1, 0, false);
		} catch (ReflectionException ex){
			Log.error(TAG, "Error create effect", ex);
		}
	}

	private void createEffectForName(String effectName){
		abstractEffect = null;
		try{
			int index = getEffectIndexList(effectName);
			abstractEffect = (AbstractEffect) ClassReflection.newInstance(ClassReflection.forName(effectClassList[index]));
			abstractEffect.setParentActor(this);
			abstractEffect.init();
			abstractEffect.update(0, 0, 1, 0, false);
		} catch (ReflectionException ex){
			Log.error(TAG, "Error create effect", ex);
		}
	}

	public int getEffectIndexList(String className){

		for (int i = 0; i < effectList.length; i++){
			if (effectList[i].equalsIgnoreCase(className)){
				return i;
			}
		}
		return 0;
	}

	public AbstractEffect getEffect(){
		return abstractEffect;
	}

	@Override
	public void draw(Batch batch, float parentAlpha){

		Color color = getColor();
		batch.setColor(color.r, color.g, color.b, color.a * parentAlpha);

		if (abstractEffect != null){
			abstractEffect.render(batch, parentAlpha);
		}
	}

	@Override
	public void act(float delta){
		super.act(delta);

		effectTime += delta;
		if (abstractEffect != null){
			abstractEffect.update(effectTime, effectTime, 10, 1, false);
		}
	}


	public void setVO(FxEffectVO fxEffectVO){
		data = fxEffectVO;
		GuiUtil.setActorVO(data, this);
	}

	public void updateVO(){
		GuiUtil.updateActorVO(data, this);
	}

	@Override
	public ActorVO getVO(){
		return data;
	}


	@Override
	public Array<String[]> getGuiDefinition(){

		Array<String[]> guiDef = new Array<>();
		guiDef.add(new String[]{"Fx Effect", "", "LABEL", ""});
		guiDef.add(
		new String[]{"effect", "effect", "LIST", "Wait", TextUtil.join(Arrays.asList(effectList), ",")});
		guiDef.add(new String[]{"name", "name", "TEXT", ""});

		if (abstractEffect != null){
			guiDef.add(new String[]{abstractEffect.getClass().getSimpleName(), "", "LABEL", ""});
			try{
				abstractEffect.getGuiDefinition(guiDef);
			} catch (Exception ex){
				Log.error(this.getClass().getName(), "Exception in getGuiDefinition", ex);
			}
		}
		Label.getGuiDefinition(guiDef);
		return guiDef;
	}

	@Override
	public ArrayMap<String, String> getValues(){
		ArrayMap<String, String> values = new ArrayMap<>();
		if (abstractEffect != null){
			values.put("effect", abstractEffect.getClass().getSimpleName());
		} else {
			values.put("effect", "");
		}

		if (abstractEffect != null){
			abstractEffect.getValues(values);
		}

		values.put("name", getName());
		values.put("x", getX() + "");
		values.put("y", getY() + "");
		values.put("width", getWidth() + "");
		values.put("height", getHeight() + "");
		values.put("originX", getOriginX() + "");
		values.put("originY", getOriginY() + "");
		values.put("rotation", getRotation() + "");
		values.put("color", getColor().toString());
		values.put("touchable", getTouchable() + "");
		values.put("visible", isVisible() + "");
		values.put("zindex", getZIndex() + "");
		return values;
	}

	@Override
	public void setValues(String changeKey, ArrayMap<String, String> values){

		if (Integer.valueOf(values.get("zindex")) < 0){
			values.put("zindex", "0");
		}

		if (abstractEffect != null){
			try{
				abstractEffect.setValues(changeKey, values);
			} catch (Exception ex){
				ex.printStackTrace();
			}
		}

		if (changeKey.equals("effect")){
			data.effect = "pl.lib2xy.gfx.effect." + values.get("effect");
			createEffect(data.effect);
		}

		if (changeKey.equals("name")){
			data.name = values.get("name");
		}
		if (changeKey.equals("x")){
			data.x = Float.valueOf(values.get("x"));
		}
		if (changeKey.equals("y")){
			data.y = Float.valueOf(values.get("y"));
		}
		if (changeKey.equals("width")){
			data.width = Float.valueOf(values.get("width"));
		}
		if (changeKey.equals("height")){
			data.height = Float.valueOf(values.get("height"));
		}
		if (changeKey.equals("originX")){
			data.originX = Float.valueOf(values.get("originX"));
		}
		if (changeKey.equals("originY")){
			data.originY = Float.valueOf(values.get("originY"));
		}
		if (changeKey.equals("rotation")){
			data.rotation = Float.valueOf(values.get("rotation"));
		}
		if (changeKey.equals("color")){
			data.color = Color.valueOf(values.get("color"));
		}
		if (changeKey.equals("touchable")){
			data.touchable = values.get("touchable");
		}
		if (changeKey.equals("visible")){
			data.visible = Boolean.valueOf(values.get("visible"));
		}
		if (changeKey.equals("zindex")){
			data.zIndex = Integer.valueOf(values.get("zindex"));
		}

		setVO(data);
	}

}
