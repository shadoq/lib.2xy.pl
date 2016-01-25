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

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.SplitPane;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;

import pl.lib2xy.XY;
import pl.lib2xy.app.Cfg;
import pl.lib2xy.app.Log;
import pl.lib2xy.app.Scene;
import pl.lib2xy.gui.GuiForm;
import pl.lib2xy.gui.GuiResource;
import pl.lib2xy.gui.GuiUtil;
import pl.lib2xy.gui.dialog.*;
import pl.lib2xy.gui.dialog.FileBrowser;
import pl.lib2xy.gui.ui.Image;
import pl.lib2xy.gui.ui.TextButton;
import pl.lib2xy.gui.widget.*;

/**
 * @author Jarek
 */
public class GuiUiTest2 extends Scene{

	@Override
	public void initialize(){


		//
		// Color Dialog
		//
		final TextButton colorButton = GuiResource.textButton("ColorDialog", "buttonColorDialog");
		colorButton.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y){
				Log.log("buttonColorDialog:click", "click x:" + x + " y:" + y + " event: " + event.toString());
				ColorBrowser color = new ColorBrowser();
				color.show();
			}
		});

		//
		// Color Dialog
		//
		final TextButton colorButton2 = GuiResource.textButton("ColorDialog Set", "buttonColorDialogSet");
		colorButton2.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y){
				Log.log("buttonColorDialogSet:click", "click x:" + x + " y:" + y + " event: " + event.toString());
				ColorBrowser color = new ColorBrowser();
				color.set((float) Math.random(), (float) Math.random(), (float) Math.random());
				color.show();
			}
		});

		//
		// File Dialog
		//
		final TextButton fileButton = GuiResource.textButton("File Dialog", "buttonFileDialog");
		fileButton.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y){
				Log.log("buttonFileDialog:click", "click x:" + x + " y:" + y + " event: " + event.toString());
				FileBrowser fileDialog = new FileBrowser();
				fileDialog.show("", "sprite", "png");
			}
		});

		//
		// File Dialog (Write Mode)
		//
		final TextButton fileWriteButton = GuiResource.textButton("File Dialog Write", "buttonFileDialog");
		fileWriteButton.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y){
				Log.log("buttonFileDialog:click", "click x:" + x + " y:" + y + " event: " + event.toString());
				FileBrowser fileDialog = new FileBrowser();
				fileDialog.show("sprite", "sprite", ".xml", "xml");
			}
		});

		//
		// Alert dialog
		//
		final TextButton alertButton = GuiResource.textButton("Alert Dialog", "alertFileDialog");
		alertButton.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y){
				Log.log("alertFileDialog:click", "click x:" + x + " y:" + y + " event: " + event.toString());
				Alert alertDialog = new Alert();
				alertDialog.show("Taki sobie testowy alert");
			}
		});

		//
		// Confirm dialog
		//
		final TextButton confirmButton = GuiResource.textButton("Confirm Dialog", "confirmButtonDialog");
		confirmButton.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y){
				Log.log("confirmButtonDialog:click", "click x:" + x + " y:" + y + " event: " + event.toString());
				Confirm confirmDialog = new Confirm();
				confirmDialog.show("Taki sobie testowy confirm dialog");
			}
		});

		//
		// Input dialog
		//
		final TextButton inputButton = GuiResource.textButton("Input Dialog", "InputDialog");
		inputButton.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y){
				Log.log("inputButton:click", "click x:" + x + " y:" + y + " event: " + event.toString());
				InputDialog inputDialog = new InputDialog();
				inputDialog.show("Taki sobie testowy confirm input", "Label");
			}
		});


		//
		// Editor2d dialog
		//
		final TextButton editorButton = GuiResource.textButton("Editor 2d", "confirmButtonDialog");
		editorButton.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y){
				Log.log("confirmButtonDialog:click", "click x:" + x + " y:" + y + " event: " + event.toString());
				Editor2d editorDialog = new Editor2d();
				editorDialog.create(XY.centerX - 220, XY.centerY - 120, 440, 240);
				editorDialog.show();
			}
		});


		Window window = GuiResource.window("Dialog", "basicUI");
		window.defaults().align(Align.top | Align.center).padLeft(5);
		window.setMovable(false);

		window.row();
		window.add(colorButton).fillX();
		window.row();
		window.add(colorButton2).fillX();
		window.row();
		window.add(fileButton).fillX();
		window.row();
		window.add(fileWriteButton).fillX();
		window.row();
		window.add(alertButton).fillX();
		window.row();
		window.add(confirmButton).fillX();
		window.row();
		window.add(inputButton).fillX();
		window.row();
		window.add(editorButton).fillX();
		window.row();

		GuiUtil.windowPosition(window, 0, 0);
		addActor(window);

		Image image = new Image("texture/def256.png");

		Image image2 = new Image("texture/def256.png");
		ScrollPane scrollPane = GuiResource.scrollPane(image2, "scroll");

		Image image3 = new Image("texture/def256.png");
		Image image4 = new Image("texture/def256.png");
		SplitPane splitPaneHorizontal = GuiResource.splitPaneHorizontal(image3, image4, "scroll");

		Image image5 = new Image("texture/def256.png");
		Image image6 = new Image("texture/def256.png");
		SplitPane splitPaneVertical = GuiResource.splitPaneVertical(image5, image6, "scroll");

		final String[] listItems = {"This is a list entry", "And another one 2", "The meaning of life 3", "Is hard to come by 4",
									"And another one 5", "The meaning of life 6", "Is hard to come by 7", "This is a list entry 8", "And another one 9"};

		//
		// List Test
		//
		final List listScroll1 = GuiResource.list(listItems, "list");
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

		//
		// List 2 Test
		//
		final List list2 = GuiResource.list(listItems, "list");
		final ScrollPane listFlickScrollPane = GuiResource.flickScrollPane(list2, "flickscroll");
		list2.addListener(new ChangeListener(){
			@Override
			public void changed(ChangeEvent event, Actor actor){
				Log.log("list2.addListener::ChangeListener",
						"List2 Value: " + list2.getSelection() + " Event: " + event.toString() + " actor: " + actor.toString());
			}
		});


		Window window2 = GuiResource.window("Basic UI 2", "basicUI2");
		window2.defaults().align(Align.top | Align.left).padLeft(5);
		window2.setMovable(false);

		window2.add("Image");
		window2.add("ScrollPane");
		window2.row();
		window2.add(image).width(128).height(128);
		window2.add(scrollPane).width(128).height(128);
		window2.row();

		window2.add("List");
		window2.add("List FlickScroll");
		window2.row();
		window2.row();
		window2.add(listScroll).height(100);
		window2.add(listFlickScrollPane).height(100);
		window2.row();

		window2.add("SplitPane Horizontal");
		window2.add("SplitPane Vertical");
		window2.row();
		window2.row();
		window2.add(splitPaneHorizontal).width(128).height(128);
		window2.add(splitPaneVertical).width(128).height(128);
		window2.row();

		GuiUtil.windowPosition(window2, 4, 0);
		addActor(window2);

		GuiForm guiForm = new GuiForm(this.getClass().getSimpleName());

		ChangeListener guiFormListener = new ChangeListener(){
			@Override
			public void changed(ChangeEvent event, Actor actor){
			}
		};

		guiForm.addLabel("label1", "Slider", Color.WHITE, 1, true);
		guiForm.addSlider("testValue1", "Slider 1", 0, 0, 1, 0.1f, null, 1, true);
		guiForm.addSlider("testValue2", "Slider 2", 0, 0, 10, 1f, null, 1, true);
		guiForm.addSlider("testValue3", "Slider 3", 0, -10, 10, 1f, null, 1, true);
		guiForm.addSlider("testValue4", "Slider 4", 0, -100, 111, 1f, null, 1, true);

		guiForm.addRandomButton("randomSlider", "Random Slider Value", new String[]{"testValue1", "testValue2", "testValue3", "testValue4"}, null, 1, true);
		guiForm.addResetButton("resetSlider", "Default Slider Value", new String[]{"testValue1", "testValue2", "testValue3", "testValue4"}, null, 1, true);

		guiForm.addLabel("label2", "CheckBox", Color.WHITE, 1, true);

		guiForm.addCheckBox("testBool 1", "CheckBox 1", false, null, 1, true);
		guiForm.addCheckBox("testBool 2", "CheckBox 2", true, null, 1, true);
		guiForm.addCheckBox("testBool 3", "CheckBox 3", false, null, 1, true);

		guiForm.addLabel("label3", "Button Group", Color.WHITE, 1, true);
		guiForm.addButtonGroup("btn1", "btn1", new String[]{"Button 1", "Button 2", "Button 3", "Button 4"}, null, 1, true);

		guiForm.addLabel("label2", "TextField", Color.WHITE, 1, true);
		guiForm.addTextField("textField1", "TextField Label", "Value", "MessageText", null, 1, true);
		guiForm.addTextField("textField2", "TextField Label 2", "Value 2", "MessageText 2", null, 1, true);


		guiForm.addLabel("label5", "Select List", Color.WHITE, 1, true);
		guiForm.addSelectIndex("select1", "select1", 0, new String[]{"Select 1", "Select 2", "Select 3", "Select 4"}, null, 1, true);

		guiForm.addLabel("label6", "Select List 2", Color.WHITE, 1, true);
		guiForm.addSelectIndex("select2", "select2", 2, new String[]{"Android", "Windows", "Atari", "Amiga"}, null, 1, true);

		guiForm.addLabel("label7", "List Box", Color.WHITE, 1, true);
		guiForm.addListIndex("list1", "list1", 0, new String[]{"List 1", "List 2", "List 3", "List 4"}, null, 1, true);

		guiForm.addLabel("label9", "Color List", Color.WHITE, 1, true);
		guiForm.addColorList("cs1", "ColorList", null, 1, true);

		guiForm.addLabel("label10", "Color Selector", Color.WHITE, 1, true);
		guiForm.addColorSelect("color1", "color1", Color.WHITE, null, 1, true);
		guiForm.addColorSelect("color2", "color2", Color.RED, null, 1, true);

		guiForm.addEditorBox("editor2d", "Editor2d", 10, 10, 20, 20, null, 1, true);

		guiForm.addLabel("label11", "File Browser", Color.WHITE, 1, true);
		guiForm.addFileBrowser("file", "File", "color.png", "texture", null, 1, true);

		guiForm.addImageList("imageList", "Image List", new String[]{"def256.jpg", "gradient1.png", "logo0.png"}, "texture", null, 1, true);
		Window window3 = GuiResource.window("FormBuilder UI", "formBuilderUI");
		window3.defaults().align(Align.top | Align.left).padLeft(5);
		window3.setMovable(false);
		window3.add(guiForm.getScrollPane()).width(Cfg.gridX8).height(Cfg.gridY23);

		GuiUtil.windowPosition(window3, 13, 0);
		addActor(window3);
	}
}
