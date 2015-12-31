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

package pl.lib2xy.debug;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.SnapshotArray;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.google.gwt.i18n.client.NumberFormat;
import pl.lib2xy.XY;
import pl.lib2xy.app.ResourceManager;
import pl.lib2xy.debug.editors.SceneEditor;
import pl.lib2xy.debug.gui.*;
import pl.lib2xy.debug.interfaces.CommandInterface;
import pl.lib2xy.debug.interfaces.DebugEditorInterface;
import pl.lib2xy.debug.panels.*;
import pl.lib2xy.interfaces.GestureInterface;
import pl.lib2xy.interfaces.KeyboardInterface;
import pl.lib2xy.interfaces.TouchInterface;
import pl.lib2xy.math.Bounding2D;

/**
 *
 */
public class Debug implements KeyboardInterface, TouchInterface, GestureInterface{

	public final static int leftPanelSize = 220;
	public final static int rightPanelSize = 220;
	public final static int topPanelSize = 30;
	public final static int bottonPanelSize = 30;

	public static Skin debugSkin;
	public static Stage debugStage;
	public static Viewport debugViewPort;
	public static OrthographicCamera debugCamera;


	//
	// Editors
	//
	public static SceneEditor sceneEditor;
	protected DebugEditorInterface debugEditor;

	Table gui;
	Table top;
	Table left;
	Table right;
	Table bottom;

	//
	// Left
	//
	public AppConfigPanel appConfigPanel;
	public SceneListPanel sceneListPanel;
	public ScenePropertiesPanel sceneProportiesPanel;

	//
	// Right
	//
	public ItemListPanel itemListPanel;
	public ItemPropertiesPanel itemPropertiesPanel;
	public AssetsPanel assetsPanel;

	//
	// Bottom
	//
	protected Label mousePos;
	protected Label boundingBoxLabel;
	protected Label overLabel;
	protected Label clipBoardLabel;
	protected Label selectLabel;

	protected Label timeLabel;

	protected Label fpsLabel;
	protected Label memLabel;
	protected Label resourceLabel;

	protected StringBuilder stringBuider = new StringBuilder();
	protected float debugTime = 10;

	protected float formatOsTime;
	protected float formatAppTime;
	protected float formatDeltaTime;


	public Debug(){
		//		debugEditor = new DebugEditor();
		sceneEditor = new SceneEditor();
		debugEditor = sceneEditor;
	}

	public void setupGUI(){
		gui = new Table(debugSkin);

		setupTop();
		setupLeft();
		setupRight();
		setupBottom();

		gui.add(top).expandX().fillX().colspan(3);
		gui.row();
		gui.add(left).left().top().fillY().expandY();
		gui.add().expandY().fillY();
		gui.add(right).right().top().fillY().expandY();
		gui.row();
		gui.add(bottom).expandX().fillX().colspan(3);
		gui.setFillParent(true);
		debugStage.addActor(gui);
	}

	private void setupTop(){
		top = new Table(debugSkin);
		top.setBackground("bg_normal");
		top.defaults().pad(-4);

		AppIconPanel appIconPanel = new AppIconPanel(debugSkin);
		top.add(appIconPanel);
		top.add().expandX();

	}

	private void setupBottom(){

		bottom = new Table(debugSkin);
		bottom.setBackground("bg_normal");
		bottom.defaults().pad(-4);

		mousePos = new Label("0:0 | 0:0", debugSkin);

		boundingBoxLabel = new Label("x: 0 y: 0 w: 0 h: 0", debugSkin);
		overLabel = new Label("None", debugSkin);
		clipBoardLabel = new Label("Clip: None", debugSkin);

		selectLabel = new Label("Select: None", debugSkin);

		timeLabel = new Label("Time", debugSkin);
		fpsLabel = new Label("FPS", debugSkin);
		memLabel = new Label("Mem", debugSkin);

		resourceLabel = new Label("Resource", debugSkin);

		bottom.add(timeLabel).width(180);
		bottom.add(fpsLabel).width(120);

		bottom.add(boundingBoxLabel).width(220);
		bottom.add(selectLabel).width(150);
		bottom.add(overLabel).width(120);
		bottom.add("").expandX();

		bottom.add(resourceLabel);

		bottom.row();
		bottom.add(memLabel).width(180);
		bottom.add(mousePos).width(120);
		bottom.add().width(220);
		bottom.add(clipBoardLabel).width(150);
		bottom.add("");
	}

	/**
	 *
	 */
	private void setupRight(){
		right = new Table(debugSkin);
		right.setBackground("bg_normal");
		right.defaults().pad(-4);
		right.defaults().align(Align.topRight);

		BaseTabBox baseTabBox = new BaseTabBox(right, debugSkin, rightPanelSize, 180);
		assetsPanel = new AssetsPanel(right, debugSkin, rightPanelSize, 152);
		AddUiPanel addUiPanel = new AddUiPanel(right, debugSkin, rightPanelSize, 152);
		baseTabBox.addTab("Assets", assetsPanel);
		baseTabBox.addTab("UI", addUiPanel);
		baseTabBox.setup();

		itemListPanel = new ItemListPanel(debugSkin,rightPanelSize, 130);
		itemPropertiesPanel = new ItemPropertiesPanel(debugSkin, rightPanelSize, 300);

		right.add(baseTabBox).top().padBottom(5);
		right.row();
		right.add(itemListPanel).top().padBottom(5);
		right.row();
		right.add(itemPropertiesPanel).top().padBottom(5);
		right.row();
		right.add("").expandY().fillY();
		right.row();
	}

	/**
	 *
	 */
	private void setupLeft(){
		left = new Table(debugSkin);
		left.setBackground("bg_normal");
		left.defaults().pad(-4);
		left.defaults().align(Align.topLeft).expandX();

		appConfigPanel = new AppConfigPanel(debugSkin);
		sceneListPanel = new SceneListPanel(debugSkin, leftPanelSize, 130);

		sceneProportiesPanel = new ScenePropertiesPanel(debugSkin, leftPanelSize, 320);
		AlignBoxPanel alignBoxPanel = new AlignBoxPanel(left, debugSkin, leftPanelSize, 320);

		ConsolePanel consolePanel = new ConsolePanel(left, debugSkin, leftPanelSize, 320);
		ToolBoxPanel toolBoxPanel = new ToolBoxPanel(left, debugSkin, leftPanelSize, 320);

		BaseTabBox baseTabBox = new BaseTabBox(left, debugSkin, leftPanelSize, 500);
		baseTabBox.addTab("Scene", sceneProportiesPanel);
		baseTabBox.addTab("Tools", toolBoxPanel);
		baseTabBox.addTab("Align", alignBoxPanel);
		baseTabBox.addTab("Log", consolePanel);

		baseTabBox.setup();

		left.add(appConfigPanel).top().padBottom(5);
		left.row();
		left.add(sceneListPanel).top().padBottom(5);
		left.row();
		left.add(baseTabBox).top().padBottom(5);
		left.row();
		left.add("").expandY().fillY();
		left.row();
	}

	/**
	 * @param deltaTime
	 */
	public void update(float deltaTime){

		if (XY.cfg.editor){
			boundingBoxLabel.getColor().a = 1.0f;
			overLabel.getColor().a = 1.0f;
			clipBoardLabel.getColor().a = 1.0f;
			selectLabel.getColor().a = 1.0f;
		} else {
			boundingBoxLabel.getColor().a = 0.5f;
			overLabel.getColor().a = 0.5f;
			clipBoardLabel.getColor().a = 0.5f;
			selectLabel.getColor().a = 0.5f;
		}

		stringBuider.setLength(0);
		mousePos.setText(
		stringBuider.append((int) XY.mouseScreen.x).append(":").append((int) XY.mouseScreen.y).append(" | ")
					.append((int) XY.mouseUnproject.x).append(":").append((int) XY.mouseUnproject.y).toString()
		);

		debugTime += deltaTime;
		if (debugTime > 0.5f){
			stringBuider.setLength(0);
			debugTime = 0;
			int debugActorCount = 0;
			if (XY.scene != null){
				final SnapshotArray<Actor> children = XY.scene.getChildren();
				if (children != null){
					debugActorCount = children.size;
				}
			}
			String debugString = ResourceManager.debug();

			resourceLabel.setText(
			stringBuider.append("actor: ").append(debugActorCount).append(debugString).toString());
		}

		stringBuider.setLength(0);

		formatOsTime = (float) (Math.round(XY.osTime * 10.0) / 10.0);
		formatAppTime = (float) (Math.round(XY.appTime * 10.0) / 10.0);
		formatDeltaTime = (float) (Math.round(XY.deltaTime * 1000.0) / 1000.0);

		timeLabel.setText(
		stringBuider.append("oT: ").append(formatOsTime).
		append(" sT: ").append(formatAppTime).
					append(" dT: ").append(formatDeltaTime).
					toString()
		);

		stringBuider.setLength(0);
		fpsLabel.setText(
		stringBuider.append("fps: ").append(XY.graphics.getFramesPerSecond()).toString()
		);

		stringBuider.setLength(0);
		memLabel.setText(
		stringBuider.append("h: ").append((Gdx.app.getJavaHeap() / 1024)).append(" kb")
					.append(" m: ").append(Gdx.app.getNativeHeap() / 1024).append(" kb").toString()
		);
	}


	public void updateGUI(){
		itemListPanel.update();
		appConfigPanel.update();
		sceneListPanel.update();
		sceneProportiesPanel.update();
		debugEditor.updateGui();
	}

	public void updateResourceGUI(){
		assetsPanel.update();
	}

	public void preRender(float deltaTime){
		debugEditor.preRender(deltaTime);
	}

	public void postRender(float deltaTime){
		debugEditor.postRender(deltaTime);
	}

	public Table getGui(){
		return gui;
	}


	public ItemListPanel getItemListPanel(){
		return itemListPanel;
	}

	public ItemPropertiesPanel getItemPropertiesPanel(){
		return itemPropertiesPanel;
	}


	public void setOver(Actor hit){

		if (hit != null){
			overLabel.setText(hit.getName());
		} else {
			overLabel.setText("None");
		}
	}

	public void setBoundXY(Bounding2D selectBounding2D, Array<Bounding2D> selectActors, Actor currentActor, int countSelectActor){
		if (countSelectActor == 0){
			selectLabel.setText("Select: None");
		} else if (countSelectActor == 1){
			selectLabel.setText("Select: " + currentActor.getName());
		} else {
			if (selectActors != null){
				selectLabel.setText("Select: " + selectActors.size + " actors ");
			}
		}

		if (selectBounding2D != null){
			boundingBoxLabel.setText(
			"x: " + (int) selectBounding2D.start.x + " y: " + (int) selectBounding2D.start.x + " w: " + (int) selectBounding2D.size.x + " h: " + (int) selectBounding2D.size.y);
		} else {
			boundingBoxLabel.setText("x: 0 y: 0 w: 0 h: 0");
		}
	}

	public void setClipboard(Actor actor){
		if (actor != null){
			clipBoardLabel.setText("Clip: " + actor.getName());
		} else {
			clipBoardLabel.setText("Clip: None");
		}

	}

	public void setClipboard(int countSelectActor){
		if (countSelectActor > 0){
			clipBoardLabel.setText("Clip: " + countSelectActor + " actors");
		} else {
			clipBoardLabel.setText("Clip: None");
		}
	}

	public void command(CommandInterface command){
		if (command != null){
			command.execute();
		}
	}

	public void onMouseMoved(int screenX, int screenY){
		sceneEditor.onMouseMoved(screenX, screenY);
	}

	@Override
	public void onZoom(float initialDistance, float distance){
		sceneEditor.onZoom(initialDistance, distance);

	}

	@Override
	public void onTap(float x, float y, int count, int button){
		sceneEditor.onTap(x, y, count, button);
	}

	@Override
	public void onPinch(Vector2 initialPointer1, Vector2 initialPointer2, Vector2 pointer1, Vector2 pointer2){
		sceneEditor.onPinch(initialPointer1, initialPointer2, pointer1, pointer2);
	}

	@Override
	public void onPan(float x, float y, float deltaX, float deltaY){
		sceneEditor.onPan(x, y, deltaX, deltaY);
	}

	@Override
	public void onPanStop(float x, float y, int pointer, int button){
		sceneEditor.onPan(x, y, pointer, button);
	}

	@Override
	public void onLongPress(float x, float y){
		sceneEditor.onLongPress(x, y);
	}

	@Override
	public void onFling(float velocityX, float velocityY, int button){
		sceneEditor.onFling(velocityX, velocityY, button);
	}

	@Override
	public void onTouchDown(int x, int y, int button){
		sceneEditor.onTouchDown(x, y, button);
	}

	@Override
	public void onDrag(int x, int y){
		sceneEditor.onDrag(x, y);
	}

	@Override
	public void onTouchUp(int x, int y, int button){
		sceneEditor.onTouchUp(x, y, button);
	}

	@Override
	public void onClick(InputEvent event, int x, int y){
		sceneEditor.onClick(event, x, y);
	}

	@Override
	public void onClick(InputEvent event, Actor actor, int x, int y){
		sceneEditor.onClick(event, actor, x, y);
	}

	@Override
	public void onKeyDown(int keycode){
		sceneEditor.onKeyDown(keycode);
	}

	@Override
	public void onKeyUp(int keycode){
		sceneEditor.onKeyUp(keycode);
	}
}
