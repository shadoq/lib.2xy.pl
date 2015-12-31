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
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ArrayMap;
import com.badlogic.gdx.utils.SnapshotArray;
import pl.lib2xy.XY;
import pl.lib2xy.app.ResourceManager;
import pl.lib2xy.gui.GuiUtil;
import pl.lib2xy.interfaces.DataVO;
import pl.lib2xy.interfaces.GuiInterface;
import pl.lib2xy.items.ActorVO;
import pl.lib2xy.items.TextButtonVO;

/**
 * Created by Jarek on 2014-06-06.
 */
public class TextButton extends com.badlogic.gdx.scenes.scene2d.ui.TextButton implements GuiInterface, DataVO{

	protected Skin skin;
	public TextButtonVO data = new TextButtonVO();

	public TextButton(String text, Skin skin){
		super(text, skin);
		this.skin = skin;
		data.text = String.valueOf(text);
		data.width = getWidth();
		data.height = getHeight();
		data.originX = getOriginX();
		data.originY = getOriginY();
		setLabelWrap(data.wrap);
		setLabelAlign(data.align);
	}

	public TextButton(TextButtonVO textButtonVO, Skin skin){
		super(textButtonVO.text, skin);
		this.skin = skin;
		data = textButtonVO;
		setVO(data);
	}

	public String getStyleName(){
		return data.style;
	}

	public void setStyleName(String styleName){
		data.style = styleName;
		if (skin == null){
			skin = XY.skin;
		}
		setStyle(skin.get(styleName, TextButtonStyle.class));
	}

	@Override
	public void setName(String name){
		super.setName(name);
		data.name = name;
		final SnapshotArray<Actor> children = getChildren();
		for (Actor child : children){
			child.setName(name);
		}
	}

	public boolean isLabelWrap(){
		return data.wrap;
	}

	public void setLabelWrap(boolean wrap){
		data.wrap = wrap;
		getLabel().setWrap(wrap);
	}

	public String getLabelAlign(){
		return data.align;
	}

	public void setLabelAlign(String align){
		if (align == null){
			align = "left";
		}
		data.align = align;
		if (align.equalsIgnoreCase("left")){
			getLabel().setAlignment(Align.left);
		} else if (align.equalsIgnoreCase("center")){
			getLabel().setAlignment(Align.center);
		} else if (align.equalsIgnoreCase("right")){
			getLabel().setAlignment(Align.right);
		}
	}

	public String getFontName(){
		return data.font;
	}

	public void setFontName(String fontName){
		data.font = fontName;
		if (fontName != null && !fontName.equals("font") && !fontName.equals("None") && !fontName.equals("")){
			final TextButtonStyle textButtonStyle = new TextButtonStyle(getStyle());
			textButtonStyle.font = ResourceManager.getBitmapFont(fontName);
			setStyle(textButtonStyle);
		}
	}

	public float getFontScaleX(){
		return data.scaleX;
	}

	public void setFontScaleX(float fontScaleX){
		data.scaleX = fontScaleX;
		getLabel().setFontScaleX(fontScaleX);
	}

	public float getFontScaleY(){
		return data.scaleY;
	}

	public void setFontScaleY(float fontScaleY){
		data.scaleY = fontScaleY;
		getLabel().setFontScaleY(fontScaleY);
	}

	public void setVO(TextButtonVO textButtonVO){
		data = textButtonVO;
		GuiUtil.setActorVO(data, this);
		setText(data.text);
		setLabelWrap(data.wrap);
		setLabelAlign(data.align);
		setStyleName(data.style);
		setFontName(data.font);
	}

	public void updateVO(){
		GuiUtil.updateActorVO(data, this);
		data.text = getText().toString();
		data.scaleX = getLabel().getScaleX();
		data.scaleY = getLabel().getScaleY();
		data.style = getStyleName();
		data.font = getFontName();
		data.align = getLabelAlign();
		data.wrap = isLabelWrap();
	}

	@Override
	public ActorVO getVO(){
		return data;
	}

	@Override
	public Array<String[]> getGuiDefinition(){
		Array<String[]> guiDef = new Array<>();
		guiDef.add(new String[]{this.getClass().getSimpleName(), "", "LABEL", ""});
		guiDef.add(new String[]{"text", "text", "TEXT", ""});
		guiDef.add(new String[]{"name", "name", "TEXT", ""});

		guiDef.add(
		new String[]{"styleName", "styleName", "LIST", "default", "default,toggle,default-light,toggle-light,default-title,toggle-title,noborder,tab"});
		guiDef.add(new String[]{"wrap", "wrap", "CHECKBOX", "false"});
		guiDef.add(new String[]{"align", "align", "LIST", "left", "left,center,right"});

		guiDef.add(new String[]{"font", "font", "FONT_LIST", ""});
		guiDef.add(new String[]{"fontScaleX", "fontScaleX", "SPINNER", "1.0"});
		guiDef.add(new String[]{"fontScaleY", "fontScaleY", "SPINNER", "1.0"});

		Label.getGuiDefinition(guiDef);
		return guiDef;
	}

	@Override
	public ArrayMap<String, String> getValues(){

		updateVO();
		ArrayMap<String, String> values = new ArrayMap<>();
		values.put("text", data.text);
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
		values.put("wrap", data.wrap + "");
		values.put("align", data.align);

		values.put("font", data.font);
		values.put("fontScaleX", data.scaleX + "");
		values.put("fontScaleY", data.scaleY + "");

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
		if (changeKey.equals("font")){
			data.font = values.get("font");
		}
		if (changeKey.equals("fontScaleX")){
			data.scaleX = Float.valueOf(values.get("fontScaleX"));
		}
		if (changeKey.equals("fontScaleY")){
			data.scaleY = Float.valueOf(values.get("fontScaleY"));
		}

		if (changeKey.equals("align")){
			data.align = values.get("align");
		}
		if (changeKey.equals("wrap")){
			data.wrap = Boolean.valueOf(values.get("wrap"));
		}

		if (changeKey.equals("text")){
			data.text = values.get("text");
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
