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
import pl.lib2xy.gui.GuiUtil;
import pl.lib2xy.interfaces.DataVO;
import pl.lib2xy.interfaces.GuiInterface;
import pl.lib2xy.items.ActorVO;
import pl.lib2xy.items.ProgressBarVO;

public class ProgressBar extends com.badlogic.gdx.scenes.scene2d.ui.ProgressBar implements GuiInterface, DataVO{

	public ProgressBarVO data = new ProgressBarVO();
	protected Skin skin;

	public ProgressBar(float min, float max, float stepSize, boolean vertical, Skin skin){
		super(min, max, stepSize, vertical, skin);
		this.skin = skin;
		data.width = getWidth();
		data.height = getHeight();
		data.originX = getOriginX();
		data.originY = getOriginY();
		data.min = min;
		data.max = max;
		data.step = stepSize;
		data.vertical = vertical;
	}

	public ProgressBar(ProgressBarVO progressBarVO, Skin skin){
		super(progressBarVO.min, progressBarVO.max, progressBarVO.step, progressBarVO.vertical, skin);
		this.skin = skin;
		data = progressBarVO;
		setVO(progressBarVO);
	}


	public void setVO(ProgressBarVO progressBarVO){
		data = progressBarVO;
		GuiUtil.setActorVO(data, this);
		setValue(data.value);

		//
		// TODO: set, min, max, step
		//
	}

	public void updateVO(){
		GuiUtil.updateActorVO(data, this);
		data.value = getValue();
		data.min = getMinValue();
		data.max = getMaxValue();
		data.step = getStepSize();
	}

	@Override
	public ActorVO getVO(){
		return data;
	}

	@Override
	public Array<String[]> getGuiDefinition(){
		Array<String[]> guiDef = new Array<>();
		guiDef.add(new String[]{this.getClass().getSimpleName(), "", "LABEL", ""});
		guiDef.add(new String[]{"min", "min", "SPINNER_INT", "0"});
		guiDef.add(new String[]{"max", "max", "SPINNER_INT", "100"});
		guiDef.add(new String[]{"stepSize", "stepSize", "SPINNER_INT", "1"});
		guiDef.add(new String[]{"name", "name", "TEXT", ""});
		Label.getGuiDefinition(guiDef);
		return guiDef;
	}

	@Override
	public ArrayMap<String, String> getValues(){

		updateVO();
		ArrayMap<String, String> values = new ArrayMap<>();

		values.put("min", getMinValue() + "");
		values.put("max", getMaxValue() + "");
		values.put("stepSize", getStepSize() + "");

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

		//
		// TODO: setup values min, max, stepSize
		//

		if (Integer.valueOf(values.get("zindex")) < 0){
			values.put("zindex", "0");
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
