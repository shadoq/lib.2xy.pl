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
import pl.lib2xy.debug.Debug;
import pl.lib2xy.debug.gui.BaseBox;
import pl.lib2xy.debug.gui.BaseExpandBox;

public class TestPanel extends BaseExpandBox{

	public TestPanel(Debug debug, Table parentTable, Skin skin, float width, float height){
		super("Test Panel", debug, parentTable, skin, width, height);
		setupGui();
	}

	private void setupGui(){
		Table test = new Table(skin);
		test.setWidth(width);
		test.setHeight(height);
		test.add("col 1");
		test.add("col 2");
		test.row();
		test.add("col 1");
		test.add("col 2");
		test.row();
		contentGroup.addActor(test);

	}

	@Override
	protected void showContent(){
		super.showContent();
		setupGui();
	}

	@Override
	protected void hideContent(){
		super.hideContent();
	}
}
