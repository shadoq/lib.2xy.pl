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

public class ActorVO{
	public String name = "";
	public String touchable = "enabled";
	public boolean visible = true;
	public boolean debug = false;
	public float x = 0;
	public float y = 0;
	public float width = 64;
	public float height = 64;
	public float originX = 32;
	public float originY = 32;
	public float scaleX = 1;
	public float scaleY = 1;
	public float rotation = 0;
	public int zIndex = 0;
	public Color color = new Color(Color.WHITE);

	public String id = "";
	public String layer = "";
	public String tag = "";
	public String customVars = "";

	public boolean fromEditor = false;

	public ActorVO(){
	}

	public ActorVO(ActorVO actorVO){

		name = new String(actorVO.name);
		touchable = new String(actorVO.touchable);
		visible = actorVO.visible;
		debug = actorVO.debug;
		x = actorVO.x;
		y = actorVO.y;
		width = actorVO.width;
		height = actorVO.height;
		originX = actorVO.originX;
		originY = actorVO.originY;
		scaleX = actorVO.scaleX;
		scaleY = actorVO.scaleY;
		rotation = actorVO.rotation;
		zIndex = actorVO.zIndex;
		color = new Color(actorVO.color);

		id = new String(actorVO.id);
		layer = new String(actorVO.layer);
		tag = new String(actorVO.tag);
		customVars = new String(actorVO.customVars);

		fromEditor = actorVO.fromEditor;
	}

	@Override
	public String toString(){
		return "ActorVO{" +
		"name='" + name + '\'' +
		", touchable='" + touchable + '\'' +
		", visible=" + visible +
		", log=" + debug +
		", x=" + x +
		", y=" + y +
		", width=" + width +
		", height=" + height +
		", originX=" + originX +
		", originY=" + originY +
		", scaleX=" + scaleX +
		", scaleY=" + scaleY +
		", rotation=" + rotation +
		", zIndex=" + zIndex +
		", color=" + color +
		", id='" + id + '\'' +
		", layer='" + layer + '\'' +
		", tag='" + tag + '\'' +
		", customVars='" + customVars + '\'' +
		'}';
	}
}
