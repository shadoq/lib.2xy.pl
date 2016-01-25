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

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import pl.lib2xy.XY;
import pl.lib2xy.app.Log;
import pl.lib2xy.app.Scene;
import pl.lib2xy.gui.GuiResource;
import pl.lib2xy.gui.dialog.*;

/**
 * @author Jarek
 */
public class GuiDialogTest extends Scene{

	private List list = null;

	@Override
	public void initialize(){

		//
		// Color Dialog
		//
		final Button colorButton = GuiResource.textButton("ColorDialog", "buttonColorDialog");
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
		final Button colorButton2 = GuiResource.textButton("ColorDialog Set", "buttonColorDialogSet");
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
		final Button fileButton = GuiResource.textButton("File Dialog", "buttonFileDialog");
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
		final Button fileWriteButton = GuiResource.textButton("File Dialog Write", "buttonFileDialog");
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
		final Button alertButton = GuiResource.textButton("Alert Dialog", "alertFileDialog");
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
		final Button confirmButton = GuiResource.textButton("Confirm Dialog", "confirmButtonDialog");
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
		final Button inputButton = GuiResource.textButton("Input Dialog", "InputDialog");
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
		final Button editorButton = GuiResource.textButton("Editor 2d", "confirmButtonDialog");
		editorButton.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y){
				Log.log("confirmButtonDialog:click", "click x:" + x + " y:" + y + " event: " + event.toString());
				Editor2d editorDialog = new Editor2d();
				editorDialog.create(XY.centerX - 220, XY.centerY - 120, 440, 240);
				editorDialog.show();
			}
		});

		Window window = GuiResource.window("Dialog", "window");
		window.setHeight(XY.height);
		window.setWidth(XY.centerX);
		window.defaults().align(Align.top);
		window.row();
		window.add(colorButton);
		window.add(colorButton2);
		window.add(fileButton);
		window.add(fileWriteButton);
		window.add(alertButton);
		window.add(confirmButton);
		window.add(inputButton);
		window.add(editorButton);
		window.setMovable(false);
		window.pack();
		addActor(window);
	}
}
