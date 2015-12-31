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

package pl.lib2xy.items;

import com.badlogic.gdx.graphics.Color;
import pl.lib2xy.constans.InterpolationType;
import pl.lib2xy.constans.MusicPlayType;
import pl.lib2xy.constans.ScreenEffectType;

public class SceneVO{

	public String name = "";
	public Color backgroundColor = new Color(Color.BLACK);
	public String backgroundFileName = "None";

	public ScreenEffectType transition = ScreenEffectType.None;
	public InterpolationType transitionInterpolation = InterpolationType.Linear;
	public float transitionDuration = 2f;

	public String music = "None";
	public MusicPlayType musicPlayType = MusicPlayType.None;

	public void reset(){
		name = "";
		backgroundColor = new Color(Color.BLACK);
		backgroundFileName = "None";

		transition = ScreenEffectType.None;
		transitionInterpolation = InterpolationType.Linear;
		transitionDuration = 2f;

		music = "None";
		musicPlayType = MusicPlayType.None;
	}

	public SceneVO(){
	}

	public SceneVO(SceneVO copy){
		this.name = copy.name;
		this.backgroundColor = copy.backgroundColor;
		this.backgroundFileName = copy.backgroundFileName;
		this.transition = copy.transition;
		this.transitionInterpolation = copy.transitionInterpolation;
		this.transitionDuration = copy.transitionDuration;
		this.music = copy.music;
		this.musicPlayType = copy.musicPlayType;
	}


}
