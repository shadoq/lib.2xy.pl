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

package pl.lib2xy.test;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import pl.lib2xy.XY;
import pl.lib2xy.app.Cfg;
import pl.lib2xy.app.Log;
import pl.lib2xy.app.ResourceManager;
import pl.lib2xy.app.Scene;
import pl.lib2xy.gui.GuiResource;
import pl.lib2xy.gui.GuiUtil;
import pl.lib2xy.gui.box.ExpandBox;
import pl.lib2xy.gui.ui.FxEffect;
import pl.lib2xy.gui.ui.Table;

public class DemoGui extends Scene{

	@Override
	public void initialize(){

		final TextButton textButton = GuiResource.textButton("Text Button", "Text Button");

		Table expandContent2 = new Table();
		expandContent2.add(textButton).width(200).height(50);
		expandContent2.row();
		expandContent2.add("Test line");
		expandContent2.row();
		expandContent2.add("Test line");
		expandContent2.row();
		ExpandBox expandBox2 = new ExpandBox("Test", expandContent2, XY.skin);

		Table expandContent3 = new Table();
		expandContent3.add("Test line");
		expandContent3.row();
		expandContent3.add(textButton).fill().expand().width(200).height(50);
		expandContent3.row();
		expandContent3.add("Test line");
		expandContent3.row();
		ExpandBox expandBox3 = new ExpandBox("Test", expandContent3, XY.skin);

		Table expandContent4 = new Table();
		expandContent4.add("Test line").width(200);
		expandContent4.row();
		expandContent4.add("Test line");
		expandContent4.row();
		expandContent4.add("Test line");
		expandContent4.row();
		ExpandBox expandBox4 = new ExpandBox("Test", expandContent4, XY.skin);

		Table table = new Table();
		table.defaults().pad(0);
		table.add(expandBox2).top().fill();
		table.row();
		table.add(expandBox3).top().fill();
		table.row();
		table.add(expandBox4).top().fill();
		table.row();
		GuiUtil.windowPosition(table, 1, 3);
		addActor(table);

		//
		// FxEffect
		//
		FxEffect fxEffect2 = new FxEffect("Plasma");
		fxEffect2.setWidth(Cfg.gridX3);
		fxEffect2.setHeight(Cfg.gridY3);
		fxEffect2.setX(Cfg.gridX0);
		fxEffect2.setY(Cfg.gridY21);
		addActor(fxEffect2);

		FxEffect fxEffect3 = new FxEffect("StarField");
		fxEffect3.setWidth(Cfg.gridX3);
		fxEffect3.setHeight(Cfg.gridY3);
		fxEffect3.setX(Cfg.gridX3);
		fxEffect3.setY(Cfg.gridY21);
		addActor(fxEffect3);

		TextureRegion image2 = new TextureRegion(ResourceManager.getTexture("texture/def256.png"));
		TextureRegion image3 = new TextureRegion(ResourceManager.getTexture("texture/def256.png"));

		final SplitPane splitPane = GuiResource.splitPaneHorizontal(new Image(image3), new Image(image3), "scroll");
		final SplitPane splitPane2 = GuiResource.splitPaneVertical(new Image(image3), new Image(image3), "scroll");

		final ScrollPane scrollPane = GuiResource.flickScrollPane(image2, "flickscroll");
		final ScrollPane scrollPane2 = GuiResource.scrollPane(new Image(image3), "scroll");

		final String[] listEntries = {"This is a list entry", "And another one", "The meaning of life", "Is hard to come by",
									  "This is a list entry", "And another one", "The meaning of life", "Is hard to come by", "This is a list entry",
									  "And another one", "The meaning of life", "Is hard to come by", "This is a list entry", "And another one",
									  "The meaning of life", "Is hard to come by", "This is a list entry", "And another one", "The meaning of life",
									  "Is hard to come by"};

		final List listScroll1 = GuiResource.list(listEntries, "list");
		final ScrollPane listScroll = GuiResource.scrollPane(listScroll1, "list");
		listScroll.setFlickScroll(true);
		listScroll.setFadeScrollBars(false);
		listScroll1.addListener(new ChangeListener(){
			@Override
			public void changed(ChangeEvent event, Actor actor){
				Log.log("list.addListener::ChangeListener",
						"List Value: " + listScroll1.getSelection() + " Event: " + event.toString() + " actor: " + actor.toString());
			}
		});

		final List list2 = GuiResource.list(listEntries, "list");
		final ScrollPane scrollPane3 = GuiResource.flickScrollPane(list2, "flickscroll");
		list2.addListener(new ChangeListener(){
			@Override
			public void changed(ChangeEvent event, Actor actor){
				Log.log("list2.addListener::ChangeListener",
						"List2 Value: " + list2.getSelection() + " Event: " + event.toString() + " actor: " + actor.toString());
			}
		});

		Window window2 = GuiResource.window("Dialog2", "window2");
		window2.setHeight(XY.height);
		window2.setWidth(XY.centerX);
		window2.setX(XY.centerX);
		window2.defaults().align(Align.top);
		window2.row().fill().expandX();

		window2.row();
		window2.add(splitPane).colspan(2);

		window2.row();
		window2.add(scrollPane).colspan(2);

		window2.row();
		window2.add(scrollPane2).width(192).height(128);
		window2.add(splitPane2).width(192).height(128);


		window2.row();
		window2.add(listScroll).width(192);
		window2.add(scrollPane3).width(192);

		window2.setMovable(false);

		addActor(window2);
	}
}
