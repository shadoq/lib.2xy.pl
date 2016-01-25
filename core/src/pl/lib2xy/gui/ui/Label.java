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
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ArrayMap;
import pl.lib2xy.XY;
import pl.lib2xy.app.ResourceManager;
import pl.lib2xy.gui.GuiUtil;
import pl.lib2xy.interfaces.DataVO;
import pl.lib2xy.interfaces.GuiInterface;
import pl.lib2xy.items.ActorVO;
import pl.lib2xy.items.LabelVO;

/**
 * Created by Jarek on 2014-05-18.
 */
public class Label extends com.badlogic.gdx.scenes.scene2d.ui.Label implements GuiInterface, DataVO{

	protected Skin skin;
	public LabelVO data = new LabelVO();

	public Label(CharSequence text, Skin skin){
		super(text, skin);
		data.text = String.valueOf(text);
		data.width = getWidth();
		data.height = getHeight();
		data.originX = getOriginX();
		data.originY = getOriginY();
		setVO(data);
	}

	public Label(LabelVO labelVO, Skin skin){
		super(labelVO.text, skin, labelVO.style);
		setVO(labelVO);
	}

	public Label(CharSequence text, Skin skin, Color color){
		this(text, skin);
		setColor(color);
	}

	public Label(CharSequence text, Skin skin, String styleName){
		super(text, skin, styleName);
		data.text = String.valueOf(text);
		data.width = getWidth();
		data.height = getHeight();
		data.originX = getOriginX();
		data.originY = getOriginY();
		data.style = styleName;
		setVO(data);
	}

	public Label(CharSequence text, Skin skin, String styleName, Color color){
		super(text, skin, styleName);
		data.text = String.valueOf(text);
		data.x = getX();
		data.y = getX();
		data.width = getWidth();
		data.height = getHeight();
		data.originX = getOriginX();
		data.originY = getOriginY();
		data.style = styleName;
		data.color = color;
		setVO(data);

	}

	public static void getGuiDefinition(Array<String[]> guiDef){
		guiDef.add(new String[]{"Actor", "", "LABEL", ""});
		guiDef.add(new String[]{"x", "x", "SPINNER_INT", "0"});
		guiDef.add(new String[]{"y", "y", "SPINNER_INT", "0"});
		guiDef.add(new String[]{"width", "width", "SPINNER_INT", "10"});
		guiDef.add(new String[]{"height", "height", "SPINNER_INT", "10"});
		guiDef.add(new String[]{"originX", "originX", "SPINNER_INT", "0"});
		guiDef.add(new String[]{"originY", "originY", "SPINNER_INT", "0"});
		guiDef.add(new String[]{"rotation", "rotation", "SPINNER_INT", "0"});
		guiDef.add(new String[]{"color", "color", "COLOR", "000000ff"});
		guiDef.add(new String[]{"touchable", "touchable", "LIST", "true", "enabled,disabled,childrenOnly"});
		guiDef.add(new String[]{"visible", "visible", "CHECKBOX", "true"});
		guiDef.add(new String[]{"zindex", "zindex", "SPINNER_INT", "0"});
	}

	public String getStyleName(){
		return data.style;
	}

	public void setStyleName(String styleName){
		data.style = styleName;
		if (skin == null){
			skin = XY.skin;
		}
		setStyle(skin.get(styleName, LabelStyle.class));
	}

	public boolean isWrap(){
		return data.wrap;
	}

	@Override
	public void setWrap(boolean wrap){
		data.wrap = wrap;
		super.setWrap(wrap);
	}

	public String getAlign(){
		return data.align;
	}

	public void setAlign(String align){
		if (align == null){
			align = "left";
		}
		data.align = align;
		if (align.equalsIgnoreCase("left")){
			setAlignment(Align.left);
		} else if (align.equalsIgnoreCase("center")){
			setAlignment(Align.center);
		} else if (align.equalsIgnoreCase("right")){
			setAlignment(Align.right);
		}
	}

	public String getFontName(){
		return data.font;
	}

	public void setFontName(String fontName){
		data.font = fontName;
		if (fontName != null && !fontName.equals("None") && !fontName.equals("")){
			final LabelStyle style = new LabelStyle(getStyle());
			style.font = ResourceManager.getBitmapFont(fontName);
			if (style.font != null){
				setStyle(style);
			}
		} else {
			setStyleName(data.style);
		}
	}

	public float getScaleX(){
		data.scaleX = super.getFontScaleX();
		return data.scaleX;
	}

	public void setScaleX(float scaleX){
		data.scaleX = scaleX;
		setFontScaleX(data.scaleX);
	}

	public float getScaleY(){
		data.scaleY = super.getFontScaleY();
		return data.scaleY;
	}

	public void setScaleY(float scaleY){
		data.scaleY = scaleY;
		setFontScaleY(data.scaleY);
	}

	public void setVO(LabelVO labelVO){
		data = labelVO;
		GuiUtil.setActorVO(data, this);
		setText(data.text);
		setWrap(data.wrap);
		setAlign(data.align);
		setFontName(data.font);
	}

	public void updateVO(){
		GuiUtil.updateActorVO(data, this);
		data.text = getText().toString();
		data.style = getStyleName();
		data.font = getFontName();
		data.align = getAlign();
		data.wrap = isWrap();
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
		new String[]{"styleName", "styleName", "LIST", "default", "default,default-light,default-opaque,default-light-opaque,default-title,default-title-opaque"});
		guiDef.add(new String[]{"wrap", "wrap", "CHECKBOX", "false"});
		guiDef.add(new String[]{"align", "align", "LIST", "left", "left,center,right"});

		guiDef.add(new String[]{"font", "font", "FONT_LIST", ""});
		guiDef.add(new String[]{"fontScaleX", "fontScaleX", "SPINNER", "1.0"});
		guiDef.add(new String[]{"fontScaleY", "fontScaleY", "SPINNER", "1.0"});

		getGuiDefinition(guiDef);
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
			setStyleName(values.get("styleName"));
		}
		if (changeKey.equals("font")){
			setFontName(values.get("font"));
		}
		if (changeKey.equals("fontScaleX")){
			data.scaleX = Float.valueOf(values.get("fontScaleX"));
			setFontScaleX(data.scaleX);
		}
		if (changeKey.equals("fontScaleY")){
			data.scaleY = Float.valueOf(values.get("fontScaleY"));
			setFontScaleY(data.scaleY);
		}

		if (changeKey.equals("align")){
			setAlign(values.get("align"));
		}
		if (changeKey.equals("wrap")){
			setWrap(Boolean.valueOf(values.get("wrap")));
		}
		if (changeKey.equals("text")){
			setText(values.get("text"));
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
