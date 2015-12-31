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
import pl.lib2xy.XY;
import pl.lib2xy.app.ResourceManager;
import pl.lib2xy.debug.gui.BaseBox;
import pl.lib2xy.debug.gui.IconActor;
import pl.lib2xy.gui.ui.*;

public class AddUiPanel extends BaseBox{

	Table tableList;

	public AddUiPanel(Table parentTable, Skin skin, float width, float height){
		super(parentTable, skin, width, height);
		tableList = new Table(skin);
		tableList.setWidth(width);
		tableList.setHeight(height);
		setupList();

		final ScrollPane scrollPane = new ScrollPane(tableList, skin.get("default-no-background", ScrollPane.ScrollPaneStyle.class));
		scrollPane.setScrollbarsOnTop(true);
		scrollPane.setFadeScrollBars(false);
		scrollPane.setScrollingDisabled(true, false);

		scrollPane.setWidth(width);
		scrollPane.setHeight(height);

		contentGroup.addActor(scrollPane);
	}

	public void setupList(){
		tableList.add(new IconActor("Image", new Image(ResourceManager.getTextureRegion("def256"), "def256"), skin)).pad(3);
		tableList.add(new IconActor("Sprite", new Sprite(0, true, "def256"), skin)).pad(3);
		tableList.row();
		tableList.add(new IconActor("Label", new Label("Label", XY.skin), skin)).pad(3);
		tableList.add(new IconActor("Text Button", new TextButton("TextButton", XY.skin), skin)).pad(3);
		tableList.row();
		tableList.add(new IconActor("Fx Effect", new FxEffect(), skin)).pad(3);
		tableList.add(new IconActor("CheckBox", new CheckBox("CheckBox", XY.skin), skin)).pad(3);
		tableList.row();
		tableList.add(new IconActor("Slider", new Slider(0, 100, 1, false, XY.skin), skin)).pad(3);
		tableList.add(new IconActor("Text Field", new TextField("Text Field", XY.skin), skin)).pad(3);
		tableList.row();
		tableList.add(new IconActor("TextArea", new TextArea("TextArea", XY.skin), skin)).pad(3);
		tableList.add(new IconActor("Select Box", new SelectBox(XY.skin), skin)).pad(3);
		tableList.row();
		tableList.add(new IconActor("Table", new pl.lib2xy.gui.ui.Table(XY.skin), skin)).pad(3);
		tableList.add(new IconActor("Game Area", new GameArea(), skin)).pad(3);
		tableList.row();
		tableList.add(new IconActor("Button", new Button(XY.skin), skin)).pad(3);
		tableList.add(new IconActor("Progress Bar", new ProgressBar(0, 100, 1, false, XY.skin), skin)).pad(3);
		tableList.row();
		tableList.add(new IconActor("Image Button", new ImageButton(XY.skin), skin)).pad(3);
		tableList.add(new IconActor("Image Text Button", new ImageTextButton("Image Text Button", XY.skin), skin)).pad(3);
		tableList.row();
		tableList.add(new IconActor("Touchpad", new Touchpad(0.5f, XY.skin), skin)).pad(3);
		tableList.row();
	}
}
