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

public class TextAreaVO extends ActorVO{

	public String text = "Label";
	public String messageText = "";
	public String style = "default";
	public String font = "";

	public TextAreaVO(){
	}

	public TextAreaVO(TextAreaVO textFieldVO){
		super(textFieldVO);
		text = new String(textFieldVO.text);
		messageText = new String(textFieldVO.messageText);
		style = new String(textFieldVO.style);
		font = new String(textFieldVO.font);
	}
}
