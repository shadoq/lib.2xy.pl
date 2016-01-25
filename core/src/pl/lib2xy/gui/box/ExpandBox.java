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

package pl.lib2xy.gui.box;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import pl.lib2xy.XY;
import pl.lib2xy.app.Log;
import pl.lib2xy.gui.ui.Image;
import pl.lib2xy.gui.ui.Table;
import pl.lib2xy.gui.ui.Window;

public class ExpandBox extends Window{

	protected Image button;
	protected ExpandTable mainTable;
	private float topBarSize = 30;

	public ExpandBox(String title, Skin skin){
		this(title, null, skin);
	}

	public ExpandBox(String title, Table table, Skin skin){
		super(title, skin);
		super.setMovable(false);
		mainTable = new ExpandTable(table, skin);
		mainTable.top();
		add(mainTable).padBottom(4);

		getTitleLabel().setAlignment(Align.left);

		button = new Image("icon_go");
		button.setOrigin(11, 11);
		button.setSize(32, 32);
		button.setRotation(270);

		final ClickListener listener = new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y){
				Log.log("Click on colpase ...");

				mainTable.setClose(!mainTable.isClose());
				if (mainTable.isClose()){
					button.setRotation(90);
				} else {
					button.setRotation(270);
				}
			}
		};
		getTitleTable().addListener(listener);
		getTitleLabel().addListener(listener);
		button.addListener(listener);
		getTitleTable().add(button).right();
	}

	public ExpandTable getMainTable(){
		return mainTable;
	}

	@Override
	public float getPrefHeight(){
		return mainTable.getPrefHeight() + topBarSize;
	}

	/**
	 *
	 */
	public static class ExpandTable extends Table{

		protected CollapseAction collapseAction = new CollapseAction();

		protected float animSpeed = 1500;
		protected boolean actionRunning = false;
		protected float currentHeight = 0;
		protected boolean isClose = false;
		protected Table table;

		public ExpandTable(Table table){
			this(table, XY.skin, false);
		}

		public ExpandTable(Skin skin){
			this(new Table(skin), skin, false);
		}

		public ExpandTable(Table table, Skin skin){
			this(table, skin, false);
		}

		public ExpandTable(Table table, Skin skin, boolean close){
			super(skin);
			this.isClose = close;
			this.table = table;
			updateTouchable();

			if (table != null){
				addActor(table);
			}
		}

		protected void updateTouchable(){
			if (table == null){
				return;
			}
			if (isClose){
				setTouchable(Touchable.disabled);
			} else {
				setTouchable(Touchable.enabled);
			}
		}


		public void setClose(boolean close, boolean withAnimation){

			isClose = close;
			updateTouchable();

			actionRunning = true;

			if (withAnimation){
				addAction(collapseAction);
			} else {
				if (close){
					currentHeight = 0;
					isClose = true;
				} else {
					if (table != null){
						currentHeight = table.getPrefHeight();
					} else {
						currentHeight = 0;
					}
					isClose = false;
				}

				actionRunning = false;
				invalidateHierarchy();
			}
		}

		public void setClose(boolean close){
			setClose(close, true);
		}

		public boolean isClose(){
			return isClose;
		}


		@Override
		public void draw(Batch batch, float parentAlpha){
			if (currentHeight > 1){
				batch.flush();
				boolean clipEnabled = clipBegin(getX(), getY(), getWidth(), currentHeight);

				super.draw(batch, parentAlpha);

				batch.flush();
				if (clipEnabled){
					clipEnd();
				}
			}
		}

		@Override
		public void layout(){

			if (table == null){
				return;
			}

			table.setBounds(0, 0, table.getPrefWidth(), table.getPrefHeight());

			if (actionRunning == false){
				if (isClose){
					currentHeight = 0;
				} else {
					currentHeight = table.getPrefHeight();
				}
			}
		}

		@Override
		public float getPrefWidth(){
			return table == null ? 0 : table.getPrefWidth();
		}

		@Override
		public float getPrefHeight(){
			if (table == null){
				return 0;
			}

			if (actionRunning == false){
				if (isClose){
					return 0;
				} else {
					return table.getPrefHeight();
				}
			}
			return currentHeight;
		}


		public void setTable(Table table){
			this.table = table;
			clearChildren();
			addActor(table);
		}

		public Table getTable(){
			return table;
		}

		private class CollapseAction extends Action{
			@Override
			public boolean act(float delta){
				if (isClose){
					currentHeight -= delta * animSpeed;
					if (currentHeight <= 1){
						currentHeight = 1;
						isClose = true;
						actionRunning = false;
						updateTouchable();
					}
				} else {
					currentHeight += delta * animSpeed;
					if (currentHeight > table.getPrefHeight()){
						currentHeight = table.getPrefHeight();
						isClose = false;
						actionRunning = false;
						updateTouchable();
					}
				}
				invalidateHierarchy();
				return !actionRunning;
			}
		}

	}
}
