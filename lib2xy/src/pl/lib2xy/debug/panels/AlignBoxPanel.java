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
import pl.lib2xy.debug.Debug;
import pl.lib2xy.debug.gui.BaseBox;

public class AlignBoxPanel extends BaseBox{

	Table tableList;

	public AlignBoxPanel(Table parentTable, Skin skin, float width, float height){
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

		TextButton toFront = new TextButton("To Front", skin);
		TextButton toBack = new TextButton("To Back", skin);

		TextButton middleSelection = new TextButton("Middle", skin);
		TextButton topSelection = new TextButton("Top", skin);
		TextButton leftSelection = new TextButton("Left", skin);
		TextButton centerSelection = new TextButton("Center", skin);
		TextButton rightSelection = new TextButton("Right", skin);
		TextButton bottomSelection = new TextButton("Bottom", skin);

		TextButton middlePage = new TextButton("Middle", skin);
		TextButton topPage = new TextButton("Top", skin);
		TextButton leftPage = new TextButton("Left", skin);
		TextButton centerPage = new TextButton("Center", skin);
		TextButton rightPage = new TextButton("Right", skin);
		TextButton bottomPage = new TextButton("Bottom", skin);
		TextButton halfTopPage = new TextButton("Half Top", skin);
		TextButton halfBottomPage = new TextButton("Half Bott.", skin);

		toFront.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y){
				if (!XY.cfg.editor){
					return;
				}
				if (Debug.sceneEditor != null){
					Debug.sceneEditor.toFront();
				}
			}
		});

		toBack.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y){
				if (!XY.cfg.editor){
					return;
				}
				if (Debug.sceneEditor != null){
					Debug.sceneEditor.toBack();
				}
			}
		});

		//
		// Selection
		//
		topSelection.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y){
				if (!XY.cfg.editor){
					return;
				}
				if (Debug.sceneEditor != null){
					Debug.sceneEditor.actorAlignTop();
				}
			}
		});

		leftSelection.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y){
				if (!XY.cfg.editor){
					return;
				}
				if (Debug.sceneEditor != null){
					Debug.sceneEditor.actorAlignLeft();
				}
			}
		});

		centerSelection.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y){
				if (!XY.cfg.editor){
					return;
				}
				if (Debug.sceneEditor != null){
					Debug.sceneEditor.actorAlignCenter();
				}
			}
		});


		rightSelection.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y){
				if (!XY.cfg.editor){
					return;
				}
				if (Debug.sceneEditor != null){
					Debug.sceneEditor.actorAlignRight();
				}
			}
		});


		bottomSelection.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y){
				if (!XY.cfg.editor){
					return;
				}
				if (Debug.sceneEditor != null){
					Debug.sceneEditor.actorAlignBottom();
				}
			}
		});
		middleSelection.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y){
				if (!XY.cfg.editor){
					return;
				}
				if (Debug.sceneEditor != null){
					Debug.sceneEditor.actorAlignMiddle();
				}
			}
		});


		//
		// Page
		//
		topPage.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y){
				if (!XY.cfg.editor){
					return;
				}
				if (Debug.sceneEditor != null){
					Debug.sceneEditor.actorAlignTopPage();
				}
			}
		});

		leftPage.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y){
				if (!XY.cfg.editor){
					return;
				}
				if (Debug.sceneEditor != null){
					Debug.sceneEditor.actorAlignLeftPage();
				}
			}
		});

		centerPage.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y){
				if (!XY.cfg.editor){
					return;
				}
				if (Debug.sceneEditor != null){
					Debug.sceneEditor.actorAlignCenterPage();
				}
			}
		});


		rightPage.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y){
				if (!XY.cfg.editor){
					return;
				}
				if (Debug.sceneEditor != null){
					Debug.sceneEditor.actorAlignRightPage();
				}
			}
		});


		bottomPage.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y){
				if (!XY.cfg.editor){
					return;
				}
				if (Debug.sceneEditor != null){
					Debug.sceneEditor.actorAlignBottomPage();
				}
			}
		});
		middlePage.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y){
				if (!XY.cfg.editor){
					return;
				}
				if (Debug.sceneEditor != null){
					Debug.sceneEditor.actorAlignMiddlePage();
				}
			}
		});

		halfTopPage.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y){
				if (!XY.cfg.editor){
					return;
				}
				if (Debug.sceneEditor != null){
					Debug.sceneEditor.actorAlignHalfTopPage();
				}
			}
		});

		halfBottomPage.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y){
				if (!XY.cfg.editor){
					return;
				}
				if (Debug.sceneEditor != null){
					Debug.sceneEditor.actorAlignHalfBottomPage();
				}
			}
		});

		tableList.add(toFront).expandX().fillX();
		tableList.add(toBack).expandX().fillX();
		tableList.add("").expandX().fillX();
		tableList.row();
		tableList.add("Selection").colspan(3).expandX().fillX();
		tableList.row();
		tableList.add(middleSelection).expandX().fillX();
		tableList.add(topSelection).expandX().fillX();
		tableList.add("").expandX().fillX();
		tableList.row();
		tableList.add(leftSelection).expandX().fillX();
		tableList.add(centerSelection).expandX().fillX();
		tableList.add(rightSelection).expandX().fillX();
		tableList.row();
		tableList.add("").expandX().fillX();
		tableList.add(bottomSelection).expandX().fillX();
		tableList.add("").expandX().fillX();
		tableList.row();
		tableList.add("Page").colspan(3).expandX().fillX();
		tableList.row();
		tableList.add(middlePage).expandX().fillX();
		tableList.add(topPage).expandX().fillX();
		tableList.add(halfTopPage).expandX().fillX();
		tableList.row();
		tableList.add(leftPage).expandX().fillX();
		tableList.add(centerPage).expandX().fillX();
		tableList.add(rightPage).expandX().fillX();
		tableList.row();
		tableList.add("").expandX().fillX();
		tableList.add(bottomPage).expandX().fillX();
		tableList.add(halfBottomPage).expandX().fillX();
		tableList.row();
	}
}
