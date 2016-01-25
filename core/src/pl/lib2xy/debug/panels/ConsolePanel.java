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

import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextArea;
import com.badlogic.gdx.utils.Timer;
import pl.lib2xy.app.Log;
import pl.lib2xy.debug.gui.BaseBox;

public class ConsolePanel extends BaseBox{

	TextArea textArea;

	public ConsolePanel(Table parentTable, Skin skin, float width, float height){
		super(parentTable, skin, width, height);

		textArea = new TextArea(Log.getLog(), skin);
		textArea.setWidth(width);
		textArea.setHeight(height);
		textArea.clearListeners();

		final ScrollPane scrollPane = new ScrollPane(textArea, skin.get("default-no-background", ScrollPane.ScrollPaneStyle.class));
		scrollPane.setScrollbarsOnTop(true);
		scrollPane.setFadeScrollBars(false);
		scrollPane.setScrollingDisabled(true, false);

		scrollPane.setWidth(width);
		scrollPane.setHeight(height);

		contentGroup.addActor(scrollPane);

		Timer timer = Timer.instance();
		timer.scheduleTask(new Timer.Task(){
			@Override
			public void run(){
				textArea.setText(Log.getLog());
			}
		}, 1, 1);
	}
}
