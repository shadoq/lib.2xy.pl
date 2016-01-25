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

public class ImageTextButtonVO extends ActorVO{

	public String text = "Label";
	public String style = "default";
	public String font = "";
	public String align = "left";
	public String image = "";
	public boolean wrap = false;

	public ImageTextButtonVO(){
	}

	public ImageTextButtonVO(ImageTextButtonVO textButtonVO){
		super(textButtonVO);
		text = new String(textButtonVO.text);
		style = new String(textButtonVO.style);
		font = new String(textButtonVO.font);
		align = new String(textButtonVO.align);
		image = new String(textButtonVO.image);
		wrap = textButtonVO.wrap;
	}
}
