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

package pl.lib2xy.debug.gui;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class IconTextureRegion extends Icon{

	TextureRegion region;
	String regionName;

	Image image;
	float width;
	float height;

	public IconTextureRegion(TextureRegion region, String regionName){

		setupIcon();

		this.region = region;
		this.regionName = regionName;

		image = new Image(region);

		width = image.getWidth();
		height = image.getHeight();

		if ((image.getWidth() > iconWidth) || (image.getHeight() > iconHeight)){
			float scale = 1.0f;
			if (image.getWidth() > image.getHeight()){
				scale = 1.0F / (image.getWidth() / iconWidth);
			} else {
				scale = 1.0F / (image.getHeight() / iconHeight);
			}

			image.setScale(scale);
			image.setX((getWidth() - image.getWidth() * image.getScaleX()) / 2.0F);
			image.setY((getHeight() - image.getHeight() * image.getScaleY()) / 2.0F);
		} else {
			image.setX((getWidth() - image.getWidth()) / 2.0F);
			image.setY((getHeight() - image.getHeight()) / 2.0F);
		}

		setupDragAndDrop(this);
		addActor(image);
	}

	public String getRegionName(){
		return regionName;
	}

	public float getRegionWidth(){
		return width;
	}

	public float getRegionHeight(){
		return height;
	}
}
