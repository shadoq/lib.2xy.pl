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

package pl.lib2xy.debug.panels;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ArrayMap;
import pl.lib2xy.XY;
import pl.lib2xy.app.Run;
import pl.lib2xy.audio.MusicPlayer;
import pl.lib2xy.constans.InterpolationType;
import pl.lib2xy.constans.MusicPlayType;
import pl.lib2xy.constans.ScreenEffectType;
import pl.lib2xy.debug.gui.BorderFrom;
import pl.lib2xy.gfx.g2d.EffectCreator;
import pl.lib2xy.interfaces.GuiInterface;

import java.util.Arrays;

public class ScenePropertiesPanel extends BorderFrom implements GuiInterface{

	Skin skin;

	public ScenePropertiesPanel(Skin skin, float width, float height){
		super(skin, null, null, false, width, height);
		this.skin = skin;
		this.actor = this;
		this.width = width;
		this.height = height;

		setupIconToolBar();
		setup();
	}

	private void setupIconToolBar(){
	}

	@Override
	public Array<String[]> getGuiDefinition(){
		Array<String[]> guiDef = new Array<>();
		guiDef.add(new String[]{"transition", "transition", "LIST", "None", Arrays.toString(ScreenEffectType.values())
																				  .replace("[", "")
																				  .replace("]","")
																				  .replace(", ", ",")});
		guiDef.add(new String[]{"interpolation", "interpolation", "LIST", "Linear", Arrays.toString(InterpolationType.values())
																						  .replace("[", "")
																						  .replace("]", "")
																						  .replace(", ", ",")});
		guiDef.add(new String[]{"Duration", "duration", "SPINNER", "1.0"});
		guiDef.add(new String[]{"background", "fileName", "TEXTURE_LIST", ""});
		guiDef.add(new String[]{"music", "music", "MUSIC_LIST", "", ""});
		guiDef.add(new String[]{"musicType", "musicType", "LIST", "None", Arrays.toString(MusicPlayType.values())
																				.replace("[", "")
																				.replace("]", "")
																				.replace(", ", ",")});
		guiDef.add(new String[]{"bgColor", "bgColor", "COLOR", ""});
		return guiDef;
	}

	@Override
	public ArrayMap<String, String> getValues(){
		ArrayMap<String, String> values = new ArrayMap<>();

		if (XY.scene != null){
			values.put("bgColor", XY.scene.data.backgroundColor.toString());
			values.put("transition", XY.scene.data.transition.toString());
			values.put("interpolation", XY.scene.data.transitionInterpolation.toString());
			values.put("duration", XY.scene.data.transitionDuration + "");
			values.put("fileName", XY.scene.data.backgroundFileName);
			values.put("music", XY.scene.data.music);
			values.put("musicType", XY.scene.data.musicPlayType.toString());
		}
		return values;
	}

	@Override
	public void setValues(String changeKey, ArrayMap<String, String> values){

		if (XY.scene == null){
			return;
		}

		boolean switchEffect = false;

		switch (changeKey){
			case "bgColor":
				if (values.get("bgColor").length() == 8 && values.get("bgColor").matches("[0-9A-Fa-f]+")){
					XY.scene.data.backgroundColor = Color.valueOf(values.get("bgColor"));
				}
				break;
			case "transition":
				XY.scene.data.transition = ScreenEffectType.valueOf(values.get("transition").trim());
				switchEffect = true;
				break;
			case "duration":
				XY.scene.data.transitionDuration = Float.valueOf(values.get("duration").trim());
				switchEffect = true;
				break;
			case "interpolation":
				XY.scene.data.transitionInterpolation = InterpolationType.valueOf(values.get("interpolation").trim());
				switchEffect = true;
				break;
			case "fileName":
				XY.scene.data.backgroundFileName = values.get("fileName");
				if (!XY.scene.data.backgroundFileName.equals("None")){
					XY.scene.setBackground(XY.scene.data.backgroundFileName);
				} else {
					XY.scene.removeBackground();
				}
				break;
			case "music":
				XY.scene.data.music = values.get("music");
				break;
			case "musicType":
				XY.scene.data.musicPlayType = MusicPlayType.valueOf(values.get("musicType"));
				break;
		}
		XY.saveEnable = true;
		if (!XY.scene.data.transition.equals("None") && switchEffect == true){
			EffectCreator.createEffect(XY.scene, XY.scene.data.transition, 1f,
									   XY.scene.data.transitionDuration, XY.scene.data.transitionInterpolation);
		}
		if (!XY.scene.data.music.equals("None") && XY.scene.data.musicPlayType != MusicPlayType.None && XY.disableEffect == false){
			MusicPlayer.getInstance().play(XY.scene.data.music, XY.scene.data.musicPlayType);
		} else if (XY.scene.data.musicPlayType == MusicPlayType.None){
			MusicPlayer.getInstance().stop();
		} else if (XY.scene.data.music.equals("None")){
			MusicPlayer.getInstance().stop();
		}
	}
}
