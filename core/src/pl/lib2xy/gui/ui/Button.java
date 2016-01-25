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
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ArrayMap;
import com.badlogic.gdx.utils.SnapshotArray;
import pl.lib2xy.XY;
import pl.lib2xy.gui.GuiUtil;
import pl.lib2xy.interfaces.DataVO;
import pl.lib2xy.interfaces.GuiInterface;
import pl.lib2xy.items.ActorVO;
import pl.lib2xy.items.ButtonVO;

/**
 * Created by Jarek on 2014-05-18.
 */
public class Button extends com.badlogic.gdx.scenes.scene2d.ui.Button implements GuiInterface, DataVO{

	protected Skin skin;
	public ButtonVO data = new ButtonVO();

	public Button(Skin skin){
		super(skin);
		data.width = getWidth();
		data.height = getHeight();
		data.originX = getOriginX();
		data.originY = getOriginY();
		setVO(data);
	}

	public Button(ButtonVO buttonVO, Skin skin){
		super(skin);
		this.skin = skin;
		data = buttonVO;
		setVO(data);
	}

	public Button(Actor child, Skin skin){
		super(child, skin);
		data.width = getWidth();
		data.height = getHeight();
		data.originX = getOriginX();
		data.originY = getOriginY();
		setVO(data);
	}

	public Button(Actor child, Skin skin, String styleName){
		super(child, skin, styleName);
		data.width = getWidth();
		data.height = getHeight();
		data.originX = getOriginX();
		data.originY = getOriginY();
		setVO(data);
	}

	@Override
	public void setName(String name){
		super.setName(name);
		final SnapshotArray<Actor> children = getChildren();
		for (Actor child : children){
			child.setName(name);
		}

	}


	public String getStyleName(){
		return data.style;
	}

	public void setStyleName(String styleName){
		data.style = styleName;
		if (skin == null){
			skin = XY.skin;
		}
		setStyle(skin.get(styleName, ButtonStyle.class));
	}

	public void setVO(ButtonVO buttonVO){
		data = buttonVO;
		GuiUtil.setActorVO(buttonVO, this);
		setStyleName(data.style);
	}

	public void updateVO(){
		GuiUtil.updateActorVO(data, this);
		data.style = getStyleName();
	}

	@Override
	public ActorVO getVO(){
		return data;
	}


	@Override
	public Array<String[]> getGuiDefinition(){

		Array<String[]> guiDef = new Array<>();
		guiDef.add(new String[]{this.getClass().getSimpleName(), "", "LABEL", ""});
		guiDef.add(new String[]{"name", "name", "TEXT", ""});
		Label.getGuiDefinition(guiDef);
		return guiDef;
	}

	@Override
	public ArrayMap<String, String> getValues(){

		updateVO();
		ArrayMap<String, String> values = new ArrayMap<>();
		values.put("name", data.name);
		values.put("x", data.x + "");
		values.put("y", data.y + "");
		values.put("width", data.width + "");
		values.put("height", data.height + "");
		values.put("originX", data.originX + "");
		values.put("originY", data.originY + "");
		values.put("rotation", data.rotation + "");
		values.put("color", data.color.toString());
		values.put("touchable", data.touchable);
		values.put("visible", data.visible + "");
		values.put("zindex", data.zIndex + "");

		values.put("styleName", data.style);

		return values;
	}

	@Override
	public void setValues(String changeKey, ArrayMap<String, String> values){

		if (Integer.valueOf(values.get("zindex")) < 0){
			values.put("zindex", "0");
		}
		if (changeKey.equals("styleName")){
			data.style = values.get("styleName");
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
