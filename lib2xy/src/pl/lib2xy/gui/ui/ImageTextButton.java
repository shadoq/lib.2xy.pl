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
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ArrayMap;
import pl.lib2xy.XY;
import pl.lib2xy.app.ResourceManager;
import pl.lib2xy.gui.GuiUtil;
import pl.lib2xy.interfaces.DataVO;
import pl.lib2xy.interfaces.GuiInterface;
import pl.lib2xy.items.ActorVO;
import pl.lib2xy.items.ImageTextButtonVO;

/**
 * Created by Jarek on 2014-06-13.
 */
public class ImageTextButton extends com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton implements GuiInterface, DataVO{

	protected Skin skin;
	public ImageTextButtonVO data = new ImageTextButtonVO();

	public ImageTextButton(String text, Skin skin){
		super(text, skin);
		this.skin = skin;
		data.text = text;
	}

	public ImageTextButton(ImageTextButtonVO imageTextButtonVO, Skin skin){
		super(imageTextButtonVO.text, skin);
		this.skin = skin;
		data = imageTextButtonVO;
		setVO(data);
	}

	public String getTextureName(){
		return data.image;
	}

	public void setTextureName(String textureName){
		data.image = textureName;
		final ImageTextButtonStyle style = new ImageTextButtonStyle(getStyle());
		style.imageUp = new TextureRegionDrawable(ResourceManager.getTextureRegion(data.image));
		setStyle(style);
	}


	public void setFontName(String fontName){
		data.font = fontName;
		if (fontName != null && !fontName.equals("None") && !fontName.equals("")){
			final ImageTextButtonStyle style = new ImageTextButtonStyle(getStyle());
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
		setStyle(skin.get(styleName, ImageTextButtonStyle.class));
	}

	public String getStyleName(){
		return data.style;
	}

	public void setVO(ImageTextButtonVO imageTextButtonVO){
		data = imageTextButtonVO;
		GuiUtil.setActorVO(data, this);
		setText(data.text);
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
		guiDef.add(new String[]{"texture", "texture", "TEXTURE_LIST", ""});
		guiDef.add(new String[]{"name", "name", "TEXT", ""});

		guiDef.add(new String[]{"styleName", "styleName", "LIST", "default", "default,toggle"});
		guiDef.add(new String[]{"font", "font", "FONT_LIST", ""});

		Label.getGuiDefinition(guiDef);
		return guiDef;
	}

	@Override
	public ArrayMap<String, String> getValues(){

		updateVO();
		ArrayMap<String, String> values = new ArrayMap<>();
		values.put("text", data.text);
		values.put("texture", data.image);
		values.put("name", data.name);

		values.put("styleName", data.style);
		values.put("font", data.font);

		values.put("x", data.x + "");
		values.put("y", data.y + "");
		values.put("width", data.width + "");
		values.put("height", data.height + "");
		values.put("originX", data.originY + "");
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

		if (changeKey.equals("texture")){
			data.image = values.get("texture");
			final ImageTextButtonStyle style = new ImageTextButtonStyle(getStyle());
			style.imageUp = new TextureRegionDrawable(ResourceManager.getTextureRegion(data.image));
			setStyle(style);
		}

		if (changeKey.equals("text")){
			setText(values.get("text"));
			data.text = values.get("text");
		}

		if (changeKey.equals("styleName")){
			setStyleName(values.get("styleName"));
		}
		if (changeKey.equals("font")){
			setFontName(values.get("font"));
		}

		if (changeKey.equals("name")){
			setName(values.get("name"));
			data.name = values.get("name");
		}
		if (changeKey.equals("x")){
			setX(Float.valueOf(values.get("x")));
			data.x = getX();
		}
		if (changeKey.equals("y")){
			setY(Float.valueOf(values.get("y")));
			data.y = getY();
		}
		if (changeKey.equals("width")){
			setWidth(Float.valueOf(values.get("width")));
			data.width = getWidth();
		}
		if (changeKey.equals("height")){
			setHeight(Float.valueOf(values.get("height")));
			data.height = getHeight();
		}
		if (changeKey.equals("originX")){
			setOriginX(Float.valueOf(values.get("originX")));
			data.originX = getOriginX();
		}
		if (changeKey.equals("originY")){
			setOriginY(Float.valueOf(values.get("originY")));
			data.originY = getOriginY();
		}
		if (changeKey.equals("rotation")){
			setRotation(Float.valueOf(values.get("rotation")));
			data.rotation = getRotation();
		}
		if (changeKey.equals("color")){
			setColor(Color.valueOf(values.get("color")));
			data.color = getColor();
		}
		if (changeKey.equals("touchable")){
			setTouchable(Touchable.valueOf(values.get("touchable")));
			data.touchable = values.get("touchable");
		}
		if (changeKey.equals("visible")){
			setVisible(Boolean.valueOf(values.get("visible")));
			data.visible = isVisible();
		}
		if (changeKey.equals("zindex")){
			setZIndex((int) (float) (Integer.valueOf(values.get("zindex"))));
			data.zIndex = Integer.valueOf(values.get("zindex"));
		}
		updateVO();
	}

}
