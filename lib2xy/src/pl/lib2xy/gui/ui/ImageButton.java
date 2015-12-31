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
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ArrayMap;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.SnapshotArray;
import pl.lib2xy.app.ResourceManager;
import pl.lib2xy.gui.GuiUtil;
import pl.lib2xy.interfaces.DataVO;
import pl.lib2xy.interfaces.GuiInterface;
import pl.lib2xy.items.ActorVO;
import pl.lib2xy.items.ImageButtonVO;

/**
 * Created by Jarek on 2014-06-06.
 */
public class ImageButton extends com.badlogic.gdx.scenes.scene2d.ui.ImageButton implements GuiInterface, DataVO{

	public ImageButtonVO data = new ImageButtonVO();

	public ImageButton(Skin skin){
		super(skin);
		data.width = getWidth();
		data.height = getHeight();
		data.originX = getOriginX();
		data.originY = getOriginY();
		setVO(data);
	}

	public ImageButton(ImageButtonVO imageButtonVO, Skin skin){
		super(skin);
		data = imageButtonVO;
		setVO(data);
	}

	public String getTextureName(){
		return data.image;
	}

	public void setTextureName(String textureName){
		data.image = textureName;
	}

	@Override
	public void setName(String name){
		super.setName(name);
		final SnapshotArray<Actor> children = getChildren();
		for (Actor child : children){
			child.setName(name);
		}

	}


	public void setImage(String imageName){
		data.image = imageName;
		final ImageButtonStyle style = new ImageButtonStyle(getStyle());
		getImage().setDrawable(new TextureRegionDrawable(new TextureRegion(ResourceManager.getTextureRegion(imageName))));
		getImage().setScaling(Scaling.stretch);
		getImage().setAlign(Align.center);
		style.imageUp = getImage().getDrawable();
		setStyle(style);
	}


	public void setDrawable(Drawable drawable){
		if (drawable == null){
			return;
		}
		final ImageButtonStyle style = new ImageButtonStyle(getStyle());
		getImage().setDrawable(drawable);
		getImage().setScaling(Scaling.stretch);
		getImage().setAlign(Align.center);
		style.imageUp = getImage().getDrawable();
		setStyle(style);
	}


	public void setVO(ImageButtonVO imageButtonVO){
		data = imageButtonVO;
		GuiUtil.setActorVO(data, this);
		setImage(data.image);
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
			setImage(data.image);
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
