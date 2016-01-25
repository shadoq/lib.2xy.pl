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

public class LabelVO extends ActorVO{

	public String text = "Label";
	public String style = "default";
	public String font = "";
	public String align = "left";
	public boolean wrap = false;

	public LabelVO(){
	}

	public LabelVO(LabelVO labelVO){
		super(labelVO);
		text = new String(labelVO.text);
		style = new String(labelVO.style);
		font = new String(labelVO.font);
		align = new String(labelVO.align);
		wrap = labelVO.wrap;
	}

	@Override
	public String toString(){
		return super.toString() + "\n" + "LabelVO{" +
		"text='" + text + '\'' +
		", style='" + style + '\'' +
		", font='" + font + '\'' +
		", align='" + align + '\'' +
		", wrap=" + wrap +
		'}';
	}
}
