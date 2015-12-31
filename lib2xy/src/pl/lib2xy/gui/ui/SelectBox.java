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
import pl.lib2xy.items.SelectBoxVO;

public class SelectBox extends com.badlogic.gdx.scenes.scene2d.ui.SelectBox<String> implements GuiInterface, DataVO{

	protected Skin skin;
	public SelectBoxVO data = new SelectBoxVO();

	public SelectBox(Skin skin){
		super(skin);
		this.skin = skin;
	}

	public SelectBox(SelectBoxVO selectBoxVO, Skin skin){
		super(skin);
		this.skin = skin;
		setVO(selectBoxVO);
	}

	public void setItems(String items){
		if (items == null){
			return;
		}
		data.items = items;
		final String[] strings = items.split("\n");
		setItems(strings);
	}

	public String getItemsDef(){
		return data.items;
	}

	public String getFontName(){
		return data.font;
	}

	public void setFontName(String fontName){
		data.font = fontName;
		if (fontName != null && !fontName.equals("None") && !fontName.equals("")){
			final SelectBoxStyle style = getStyle();
			style.font = ResourceManager.getBitmapFont(fontName);
			style.listStyle.font = ResourceManager.getBitmapFont(fontName);
			if (style.font != null){
				setStyle(style);
			}
		} else {
			setStyleName(data.style);
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
		setStyle(skin.get(styleName, SelectBoxStyle.class));
	}


	public void setVO(SelectBoxVO selectBoxVO){
		data = selectBoxVO;
		GuiUtil.setActorVO(data, this);
		setFontName(data.font);
		setItems(data.items);
	}

	public void updateVO(){
		GuiUtil.updateActorVO(data, this);
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
		guiDef.add(new String[]{"items", "items", "TEXT_AREA", ""});

		guiDef.add(new String[]{"styleName", "styleName", "LIST", "default", "default,default-light"});
		guiDef.add(new String[]{"font", "font", "FONT_LIST", ""});

		Label.getGuiDefinition(guiDef);
		return guiDef;
	}

	@Override
	public ArrayMap<String, String> getValues(){

		updateVO();

		ArrayMap<String, String> values = new ArrayMap<>();
		values.put("name", data.name);

		values.put("styleName", data.style);
		values.put("font", data.font);

		values.put("items", data.items);
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

		//
		// Check
		//
		if (Integer.valueOf(values.get("zindex")) < 0){
			values.put("zindex", "0");
		}

		//
		// Set
		//
		if (changeKey.equals("name")){
			setName(values.get("name"));
		}

		if (changeKey.equals("styleName")){
			setStyleName(values.get("styleName"));
		}
		if (changeKey.equals("font")){
			setFontName(values.get("font"));
		}

		if (changeKey.equals("items")){
			setItems(values.get("items"));
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
