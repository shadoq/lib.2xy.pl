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

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import pl.lib2xy.XY;
import pl.lib2xy.debug.commands.*;
import pl.lib2xy.debug.gui.BaseBox;

public class ToolBoxPanel extends BaseBox{

	String[] textureRegionList;
	Table tableList;

	public ToolBoxPanel(Table parentTable, Skin skin, float width, float height){
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

		TextButton saveScene = new TextButton("Save Scene", skin);
		TextButton reloadScene = new TextButton("Reload Scene", skin);

		TextButton packAssets = new TextButton("Pack Assets", skin);
		TextButton createDirectory = new TextButton("Create Directory", skin);
		TextButton reloadAssets = new TextButton("Reload Assets", skin);

		saveScene.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y){
				if (XY.debug != null){
					XY.debug.command(new SaveScene());
				}
			}
		});

		reloadScene.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y){
				if (XY.debug != null){
					XY.debug.command(new ReloadScene());
				}
			}
		});

		packAssets.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y){
				if (XY.debug != null){
					XY.debug.command(new PackAssert());
				}
			}
		});

		reloadAssets.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y){
				if (XY.debug != null){
					XY.debug.command(new UpdateAssert());
				}
			}
		});

		createDirectory.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y){
				if (XY.debug != null){
					XY.debug.command(new CreateDirectory());
				}
			}
		});

		tableList.add(saveScene).expandX().fillX();
		tableList.add(reloadScene).expandX().fillX();
		tableList.row();
		tableList.add(packAssets).expandX().fillX();
		tableList.add(reloadAssets).expandX().fillX();
		tableList.row();
		tableList.add(createDirectory).expandX().fillX();
	}
}
