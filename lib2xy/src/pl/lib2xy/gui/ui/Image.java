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
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ArrayMap;
import com.badlogic.gdx.utils.Scaling;
import pl.lib2xy.app.ResourceManager;
import pl.lib2xy.gui.GuiUtil;
import pl.lib2xy.interfaces.DataVO;
import pl.lib2xy.interfaces.GuiInterface;
import pl.lib2xy.items.ActorVO;
import pl.lib2xy.items.ImageVO;

/**
 * Created by Jarek on 2014-05-18.
 */
public class Image extends com.badlogic.gdx.scenes.scene2d.ui.Image implements GuiInterface, DataVO{

	public ImageVO data = new ImageVO();

	public Image(){
		super();
		data.width = getWidth();
		data.height = getHeight();
		data.originX = getOriginX();
		data.originY = getOriginY();
	}

	public Image(String textureName){
		data.image = textureName;
		data.width = getWidth();
		data.height = getHeight();
		data.originX = getOriginX();
		data.originY = getOriginY();
		setVO(data);
	}

	public Image(TextureRegion region, String textureName){
		super(new TextureRegionDrawable(region));
		data.image = textureName;
		data.width = getWidth();
		data.height = getHeight();
		data.originX = getOriginX();
		data.originY = getOriginY();
		setVO(data);
	}

	public Image(ImageVO imageVO){
		data = imageVO;
		setVO(imageVO);
	}

	public String getTextureName(){
		return data.image;
	}

	public void setTextureName(String textureName){
		data.image = textureName;
		setDrawable(new TextureRegionDrawable(new TextureRegion(ResourceManager.getTextureRegion(data.image))));
	}

	public void setVO(ImageVO imageVO){
		data = imageVO;
		GuiUtil.setActorVO(data, this);
		setDrawable(new TextureRegionDrawable(new TextureRegion(ResourceManager.getTextureRegion(imageVO.image))));
		setScaling(Scaling.stretch);
		setAlign(Align.center);
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
		guiDef.add(new String[]{this.getClass().getSimpleName(), "", "LABEL", ""});
		guiDef.add(new String[]{"texture", "texture", "TEXTURE_LIST", ""});
		guiDef.add(new String[]{"name", "name", "TEXT", ""});
		Label.getGuiDefinition(guiDef);
		return guiDef;
	}

	@Override
	public ArrayMap<String, String> getValues(){

		updateVO();
		ArrayMap<String, String> values = new ArrayMap<>();
		values.put("texture", getTextureName());
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

		if (changeKey.equals("texture")){
			data.image = values.get("texture");
			setDrawable(new TextureRegionDrawable(new TextureRegion(ResourceManager.getTextureRegion(data.image))));
		}

		if (Integer.valueOf(values.get("zindex")) < 0){
			values.put("zindex", "0");
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
