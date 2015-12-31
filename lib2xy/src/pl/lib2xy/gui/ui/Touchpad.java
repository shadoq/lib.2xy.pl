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
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ArrayMap;
import pl.lib2xy.gui.GuiUtil;
import pl.lib2xy.interfaces.GuiInterface;
import pl.lib2xy.items.TouchpadVO;

/**
 * Created by Jarek on 2014-06-13.
 */
public class Touchpad extends com.badlogic.gdx.scenes.scene2d.ui.Touchpad implements GuiInterface{

	public TouchpadVO data = new TouchpadVO();

	public Touchpad(float deadzoneRadius, Skin skin){
		super(deadzoneRadius, skin);
		data.deadzoneRadius = deadzoneRadius;
		data.width = 128;
		data.height = 128;
		data.originX = 64;
		data.originY = 64;
		setVO(data);
	}

	public Touchpad(TouchpadVO touchpadVO, Skin skin){
		super(touchpadVO.deadzoneRadius, skin);
		data = touchpadVO;
		setVO(data);
	}

	public float getDeadzoneRadius(){
		return data.deadzoneRadius;
	}

	public void setVO(TouchpadVO touchpadVO){
		data = touchpadVO;
		GuiUtil.setActorVO(data, this);
	}

	public void updateVO(){
		GuiUtil.updateActorVO(data, this);
		data.deadzoneRadius = getDeadzoneRadius();
	}

	@Override
	public Array<String[]> getGuiDefinition(){
		Array<String[]> guiDef = new Array<>();
		guiDef.add(new String[]{this.getClass().getSimpleName(), "", "LABEL", ""});
		guiDef.add(new String[]{"name", "name", "TEXT", ""});
		guiDef.add(new String[]{"radius", "radius", "SPINNER", "0.1"});
		Label.getGuiDefinition(guiDef);
		return guiDef;
	}

	@Override
	public ArrayMap<String, String> getValues(){
		ArrayMap<String, String> values = new ArrayMap<>();

		updateVO();

		values.put("radius", data.deadzoneRadius + "");
		values.put("name", data.name);
		values.put("x", data.x + "");
		values.put("y", data.y + "");
		values.put("width", data.width + "");
		values.put("height", data.height + "");
		values.put("originX", data.originX + "");
		values.put("originY", data.originY + "");
		values.put("rotation", data.rotation + "");
		values.put("color", data.color.toString());
		values.put("touchable", data.touchable + "");
		values.put("visible", data.visible + "");
		values.put("zindex", data.zIndex + "");

		return values;
	}

	@Override
	public void setValues(String changeKey, ArrayMap<String, String> values){

		if (Integer.valueOf(values.get("zindex")) < 0){
			values.put("zindex", "0");
		}

		if (Integer.valueOf(values.get("zindex")) < 0){
			values.put("radius", "0");
		}

		if (changeKey.equals("radius")){
			setDeadzone(Float.valueOf(values.get("radius")));
		}

		if (changeKey.equals("name")){
			setName(values.get("name"));
		}
		if (changeKey.equals("x")){
			setX(Float.valueOf(values.get("x")));
		}
		if (changeKey.equals("y")){
			setY(Float.valueOf(values.get("y")));
		}
		if (changeKey.equals("width")){
			setWidth(Float.valueOf(values.get("width")));
		}
		if (changeKey.equals("height")){
			setHeight(Float.valueOf(values.get("height")));
		}
		if (changeKey.equals("originX")){
			setOriginX(Float.valueOf(values.get("originX")));
		}
		if (changeKey.equals("originY")){
			setOriginY(Float.valueOf(values.get("originY")));
		}
		if (changeKey.equals("rotation")){
			setRotation(Float.valueOf(values.get("rotation")));
		}
		if (changeKey.equals("color")){
			setColor(Color.valueOf(values.get("color")));
		}
		if (changeKey.equals("touchable")){
			setTouchable(Touchable.valueOf(values.get("touchable")));
		}
		if (changeKey.equals("visible")){
			setVisible(Boolean.valueOf(values.get("visible")));
		}
		if (changeKey.equals("zindex")){
			setZIndex((int) (float) (Integer.valueOf(values.get("zindex"))));
		}
		updateVO();
	}
}
