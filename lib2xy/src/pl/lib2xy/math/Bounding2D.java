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

package pl.lib2xy.math;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import pl.lib2xy.debug.editors.SceneEditor;

public class Bounding2D{

	public Vector2 start = new Vector2();
	public Vector2 end = new Vector2();
	public Vector2 size = new Vector2();
	public Vector2 deltaPosition = new Vector2();

	public Vector2 percentStart = new Vector2();
	public Vector2 percentEnd = new Vector2();
	public Vector2 percentSize = new Vector2();

	public Actor actor;

	public void setPosition(float x, float y){
		if (this.start.x != x || this.start.y != y){
			this.start.x = x;
			this.start.y = y;
			this.end.x = x + size.x;
			this.end.y = y + size.y;
			positionChanged();
		}
	}


	public void setBounds(float x, float y, float width, float height){
		if (this.start.x != x || this.start.y != y){
			this.start.x = x;
			this.start.y = y;
			this.end.x = x + size.x;
			this.end.y = y + size.y;
			positionChanged();
		}
		if (this.size.x != width || this.size.y != height){
			this.size.x = width;
			this.size.y = height;
			this.end.x = this.start.x + this.size.x;
			this.end.y = this.start.y + this.size.y;
			sizeChanged();
		}
	}

	public void set(Bounding2D set){
		this.start.x = set.start.x;
		this.start.y = set.start.y;
		this.size.x = set.size.x;
		this.size.y = set.size.y;
		this.end.x = set.end.x;
		this.end.y = set.end.y;
		positionChanged();
		sizeChanged();
	}

	public Bounding2D copy(){
		Bounding2D copy = new Bounding2D();
		copy.start.x = start.x;
		copy.start.y = start.y;
		copy.size.x = size.x;
		copy.size.y = size.y;
		copy.end.x = end.x;
		copy.end.y = end.y;
		return copy;
	}

	public void update(){
		this.setBounds(actor.getX(),actor.getY(),actor.getWidth(),actor.getHeight());
	}

	protected void positionChanged(){

	}

	protected void sizeChanged(){

	}

	@Override
	public String toString(){
		return "Bounding2D{" +
		"start=" + start +
		", end=" + end +
		", size=" + size +
		", deltaPosition=" + deltaPosition +
		", percentStart=" + percentStart +
		", percentEnd=" + percentEnd +
		", percentSize=" + percentSize +
		", actor=" + actor +
		'}';
	}
}
