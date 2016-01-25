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

package pl.lib2xy.gui.widget;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ObjectMap;
import pl.lib2xy.gui.GuiResource;

/**
 * @author Jarek
 */
public class TabHost extends Table{

	private VerticalGroup tabsVertical;
	private HorizontalGroup tabsHorizontal;
	private Stack body;

	private ObjectMap<Button, Actor> nodes = new ObjectMap<Button, Actor>(10);
	private ButtonGroup buttonGroup = new ButtonGroup();
	private Actor curentActor = null;
	private boolean verticalTabs = false;
	private boolean opositeTabs = false;
	private Skin skin;
	private float defaultWidth = 0;
	private float defaultHeight = 0;
	private Cell mainCell;
	private Cell tabCell;

	public static class TabHostStyle{
		public TabHostStyle(){

		}
	}

	public TabHost(){
		super();
		initalizeTabHost();
	}

	public TabHost(Skin skin){
		this(skin, false, false);
	}

	public TabHost(Skin skin, boolean verticalTabs){
		this(skin, verticalTabs, false);
	}

	public TabHost(Skin skin, boolean verticalTabs, boolean opositeTabs){
		super(skin);
		this.skin = skin;
		this.verticalTabs = verticalTabs;
		this.opositeTabs = opositeTabs;
		initalizeTabHost();
	}

	public float getDefaultWidth(){
		return defaultWidth;
	}

	public void setDefaultWidth(float defaultWidth){
		this.defaultWidth = defaultWidth;
	}

	public float getDefaultHeight(){
		return defaultHeight;
	}

	public void setDefaultHeight(float defaultHeight){
		this.defaultHeight = defaultHeight;
	}

	public TabContainer addTab(String button, String actor){
		TabLabel tabLabel = new TabLabel(button, skin);
		Label label = new Label(actor, skin);
		return addTab(tabLabel, label);
	}

	public TabContainer addTab(String button, String actor, boolean transparent){
		TabLabel tabLabel = new TabLabel(button, skin);
		Label label = new Label(actor, skin);
		return addTab(tabLabel, label, transparent);
	}

	public TabContainer addTab(String button, final Actor actor){
		TabLabel tabLabel = new TabLabel(button, skin);
		return addTab(tabLabel, actor, false);
	}

	public TabContainer addTab(String button, final Actor actor, boolean transparent){
		TabLabel tabLabel = new TabLabel(button, skin);
		return addTab(tabLabel, actor, transparent);
	}

	public TabContainer addTab(String button, String actor, Skin skin){
		TabLabel tabLabel = new TabLabel(button, skin);
		Label label = new Label(actor, skin);
		return addTab(tabLabel, label);
	}

	public TabContainer addTab(String button, final Actor actor, Skin skin){
		TabLabel tabLabel = new TabLabel(button, skin);
		return addTab(tabLabel, actor);
	}

	public TabContainer addTabScrollPane(final Button button, final Actor actor){
		final ScrollPane scrollPane = GuiResource.scrollPaneTransparent(actor, "");
		scrollPane.setFadeScrollBars(true);
		scrollPane.setFlickScroll(true);
		scrollPane.setScrollingDisabled(true, false);
		return addTab(button, scrollPane);
	}

	private TabContainer addTab(final Button button, final Actor actor){
		return addTab(button, actor, false);
	}

	private TabContainer addTab(final Button button, final Actor actor, final boolean tranparent){

		final TabContainer tab = new TabContainer(skin, tranparent);

		if (actor instanceof TabHost){
			tab.add(actor).expand().fill();
		} else {
			tab.add(actor);
		}

		if (verticalTabs){
			tabsVertical.addActor(button);
		} else {
			tabsHorizontal.addActor(button);
		}

		buttonGroup.add(button);
		buttonGroup.uncheckAll();
		button.setChecked(true);
		nodes.put(button, tab);

		curentActor = tab;
		body.clear();
		body.addActor(tab);
		body.invalidate();

		button.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y){
				super.clicked(event, x, y);
				curentActor = tab;
				body.clear();
				body.addActor(tab);
				body.invalidate();
			}
		});

		return tab;
	}

	private void initalizeTabHost(){

		if (verticalTabs){
			tabsVertical = new VerticalGroup();
			tabsVertical.fill();
		} else {
			tabsHorizontal = new HorizontalGroup();
			tabsHorizontal.fill();
		}
		body = new Stack();
		clear();

		if (opositeTabs){
			if (verticalTabs){
				mainCell = super.add(body).expand().fill().pad(0);
				tabCell = super.add(tabsVertical).left().expandY().fillY().pad(0);
			} else {
				mainCell = super.add(body).expand().fill().pad(0);
				super.row();
				tabCell = super.add(tabsHorizontal).left().expandX().fillX().pad(0);
			}
		} else {
			if (verticalTabs){
				tabCell = super.add(tabsVertical).left().expandY().fillY().pad(0);
			} else {
				tabCell = super.add(tabsHorizontal).left().expandX().fillX().pad(0);
				super.row();
			}
			mainCell = super.add(body).expand().fill().pad(0);
		}
	}

}
