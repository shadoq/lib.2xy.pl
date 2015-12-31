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
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ArrayMap;
import pl.lib2xy.XY;
import pl.lib2xy.app.ResourceManager;
import pl.lib2xy.gui.GuiUtil;
import pl.lib2xy.interfaces.DataVO;
import pl.lib2xy.interfaces.GuiInterface;
import pl.lib2xy.items.ActorVO;
import pl.lib2xy.items.TextAreaVO;

/**
 * Created by Jarek on 2014-06-13.
 */
public class TextArea extends com.badlogic.gdx.scenes.scene2d.ui.TextArea implements GuiInterface, DataVO{

	protected Skin skin;
	public TextAreaVO data = new TextAreaVO();

	public TextArea(String text, Skin skin){
		super(text, skin);
		this.skin = skin;
		data.text = String.valueOf(text);
		data.width = getWidth();
		data.height = getHeight();
		data.originX = getOriginX();
		data.originY = getOriginY();
		setVO(data);
	}

	public TextArea(TextAreaVO textAreaVO, Skin skin){
		super(textAreaVO.text, skin);
		this.skin = skin;
		setVO(textAreaVO);
	}

	public void setFontName(String fontName){
		data.font = fontName;
		if (fontName != null && !fontName.equals("None") && !fontName.equals("")){
			final TextFieldStyle style = new TextFieldStyle(getStyle());
			style.font = ResourceManager.getBitmapFont(fontName);
			if (style.font != null){
				setStyle(style);
			}
		} else {
			setStyleName(data.style);
		}
	}

	public String getFontName(){
		return data.font;
	}

	public void setStyleName(String styleName){
		data.style = styleName;
		if (skin == null){
			skin = XY.skin;
		}
		setStyle(skin.get(styleName, TextFieldStyle.class));
	}

	public String getStyleName(){
		return data.style;
	}

	public void setTextDef(String text){
		data.text = text;
		super.setText(data.text);
	}

	@Override
	public String getText(){
		data.text = super.getText();
		return data.text;
	}

	public void setVO(TextAreaVO textAreaVO){
		data = textAreaVO;
		GuiUtil.setActorVO(data, this);
		setTextDef(data.text);
		setMessageText(data.messageText);
		setStyleName(data.style);
		setFontName(data.font);
	}

	public void updateVO(){
		data.text = getText();
		GuiUtil.updateActorVO(data, this);
		data.messageText = getMessageText();
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
		guiDef.add(new String[]{"name", "name", "TEXT", ""});

		guiDef.add(new String[]{"text", "text", "TEXT", ""});
		guiDef.add(new String[]{"messageText", "text", "TEXT", ""});

		guiDef.add(new String[]{"styleName", "styleName", "LIST", "default", "default,default-light"});
		guiDef.add(new String[]{"font", "font", "FONT_LIST", ""});

		Label.getGuiDefinition(guiDef);
		return guiDef;
	}

	@Override
	public ArrayMap<String, String> getValues(){

		updateVO();

		ArrayMap<String, String> values = new ArrayMap<>();
		values.put("text", data.text);
		values.put("messageText", data.messageText);

		values.put("styleName", data.style);
		values.put("font", data.font);

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

		if (changeKey.equals("text")){
			data.text = values.get("text");
		}
		if (changeKey.equals("messageText")){
			data.messageText = values.get("messageText");
		}
		if (changeKey.equals("styleName")){
			data.style = values.get("styleName");
		}
		if (changeKey.equals("font")){
			data.font = values.get("font");
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
