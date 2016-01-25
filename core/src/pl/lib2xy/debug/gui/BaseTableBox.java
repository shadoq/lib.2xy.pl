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

import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import pl.lib2xy.debug.Debug;

public class BaseTableBox extends BaseExpandBox{

	protected Table main;
	protected ScrollPane scrollPane;

	public BaseTableBox(Debug debug, Table parentTable, Skin skin, float width, float height){
		super(debug, parentTable, skin, width, height);
		main = new Table(skin);

		final ScrollPane scrollPane = new ScrollPane(main, skin.get("default-no-background", ScrollPane.ScrollPaneStyle.class));
		scrollPane.setScrollbarsOnTop(true);
		scrollPane.setScrollingDisabled(true, false);

		scrollPane.setWidth(width);
		scrollPane.setHeight(height);

		contentGroup.addActor(scrollPane);

		main.add("AAAAAA");
		main.add("AAAAAA");
		main.add("AAAAAA");
		main.row();
		main.add("AAAAAA");
		main.add("AAAAAA");
		main.add("AAAAAA");
		main.row();
		main.add("AAAAAA");
		main.add("AAAAAA");
		main.add("AAAAAA");
		main.row();
		main.add("AAAAAA");
		main.add("AAAAAA");
		main.add("AAAAAA");
		main.row();
		main.add("AAAAAA");
		main.add("AAAAAA");
		main.add("AAAAAA");
		main.row();

		//		main.pack();
		//		contentGroup.addActor(main);

	}
}
