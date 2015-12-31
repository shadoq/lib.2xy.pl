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
import pl.lib2xy.XY;
import pl.lib2xy.app.ResourceManager;
import pl.lib2xy.gui.GuiUtil;
import pl.lib2xy.interfaces.DataVO;
import pl.lib2xy.interfaces.GuiInterface;
import pl.lib2xy.items.ActorVO;
import pl.lib2xy.items.CheckBoxVO;

/**
 * Created by Jarek on 2014-06-13.
 */
public class CheckBox extends com.badlogic.gdx.scenes.scene2d.ui.CheckBox implements GuiInterface, DataVO{

	protected Skin skin;

	public CheckBoxVO data = new CheckBoxVO();

	public CheckBox(String text, Skin skin){
		super(text, skin);
		this.skin = skin;
		data.text = String.valueOf(text);
		data.width = getWidth();
		data.height = getHeight();
		data.originX = getOriginX();
		data.originY = getOriginY();
		setVO(data);
	}

	public CheckBox(String text, Skin skin, String style){
		super(text, skin, style);
		this.skin = skin;
		data.style = style;
		data.text = String.valueOf(text);
		data.width = getWidth();
		data.height = getHeight();
		data.originX = getOriginX();
		data.originY = getOriginY();
		setVO(data);
	}

	public CheckBox(CheckBoxVO checkBoxVO, Skin skin){
		super(checkBoxVO.text, skin);
		setVO(data);
	}


	public String getFontName(){
		return data.font;
	}

	public void setFontName(String fontName){
		data.font = fontName;
		if (fontName != null && !fontName.equals("None") && !fontName.equals("")){
			final CheckBoxStyle style = new CheckBoxStyle(getStyle());
			style.font = ResourceManager.getBitmapFont(fontName);
			if (style.font != null){
				setStyle(style);
			}
		} else {
			setStyleName(data.style);
		}
	}

	public void setStyleName(String styleName){
		data.style = styleName;
		if (skin == null){
			skin = XY.skin;
		}
		setStyle(skin.get(styleName, CheckBoxStyle.class));
	}

	public String getStyleName(){
		return data.style;
	}


	public void setVO(CheckBoxVO checkBoxVO){
		data = checkBoxVO;
		GuiUtil.setActorVO(data, this);
		setChecked(data.state);
		setText(data.text);
		setFontName(data.font);
	}

	public void updateVO(){
		data.state = isChecked();
		GuiUtil.updateActorVO(data, this);
		data.text = getText().toString();
		data.visible = isVisible();
		data.style = getStyleName();
		data.font = getFontName();
	}

	@Override
	public ActorVO getVO(){
		return data;
	}

	@Override
	public Array<String[]> getGuiDefinition(){

		Array<String[]> guiDef = new Array<>();
		guiDef.add(new String[]{this.getClass().getSimpleName(), "", "LABEL", ""});
		guiDef.add(new String[]{"state", "state", "CHECKBOX", "false"});
		guiDef.add(new String[]{"text", "text", "TEXT", ""});
		guiDef.add(new String[]{"name", "name", "TEXT", ""});

		guiDef.add(new String[]{"styleName", "styleName", "LIST", "default", "default,default-light"});
		guiDef.add(new String[]{"font", "font", "FONT_LIST", ""});

		Label.getGuiDefinition(guiDef);
		return guiDef;
	}

	@Override
	public ArrayMap<String, String> getValues(){

		updateVO();
		ArrayMap<String, String> values = new ArrayMap<>();
		values.put("state", data.state + "");
		values.put("text", data.text);
		values.put("name", data.name);

		values.put("styleName", data.style);
		values.put("font", data.font);

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
		return values;
	}

	@Override
	public void setValues(String changeKey, ArrayMap<String, String> values){

		if (Integer.valueOf(values.get("zindex")) < 0){
			values.put("zindex", "0");
		}

		if (changeKey.equals("state")){
			setChecked(Boolean.valueOf(values.get("state")));
		}
		if (changeKey.equals("text")){
			setText(values.get("text"));
		}

		if (changeKey.equals("styleName")){
			setStyleName(values.get("styleName"));
		}
		if (changeKey.equals("font")){
			setFontName(values.get("font"));
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

		setVO(data);
	}
}
