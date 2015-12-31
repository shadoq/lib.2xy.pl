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

package pl.lib2xy.gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox.CheckBoxStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.List.ListStyle;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane.ScrollPaneStyle;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox.SelectBoxStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldStyle;
import com.badlogic.gdx.utils.Align;
import pl.lib2xy.XY;
import pl.lib2xy.app.Log;
import pl.lib2xy.app.ResourceManager;
import pl.lib2xy.gui.dialog.Alert;
import pl.lib2xy.gui.ui.Window;

/**
 * Factory class to create GUI elements with default parameters
 *
 * @author Jarek
 */
public class GuiResource{

	private static final String TAG = GuiResource.class.getSimpleName();
	private static final boolean LOG = true;

	private GuiResource(){

	}

	/**
	 * Zwraca image button
	 *
	 * @param fileName
	 * @return
	 */
	public static Button button(String fileName){
		if (LOG){
			Log.debug(TAG, "Create button: " + fileName);
		}
		Texture texture = ResourceManager.getTexture(fileName);
		Button button = new Button(new Image(texture), XY.skin);
		return button;
	}

	/**
	 * Zwraca image button
	 *
	 * @param region
	 * @return
	 */
	public static Button button(Texture region){
		if (LOG){
			Log.debug(TAG, "Create button texture: " + region);
		}
		Button button = new Button(new Image(region), XY.skin);
		return button;
	}

	/**
	 * Zwraca elementu GUI - Image Button
	 *
	 * @param region
	 * @param name
	 * @return
	 */
	public static Button button(TextureRegion region, String name){
		if (LOG){
			Log.debug(TAG, "Create button texture: " + name);
		}
		Button button = new Button(new Image(region), XY.skin);
		button.setName(name);
		return button;
	}

	/**
	 * @param fileName
	 * @return
	 */
	public static Button buttonToogle(String fileName){
		if (LOG){
			Log.debug(TAG, "Create button toogle FileName: " + fileName);
		}
		Texture texture = ResourceManager.getTexture(fileName);
		Button button = new Button(new Image(texture), XY.skin, "toggle");
		return button;
	}

	/**
	 * Zwraca elementu GUI - Image Button Toggle
	 *
	 * @param region
	 * @param name
	 * @return
	 */
	public static Button buttonToggle(TextureRegion region, String name){
		if (LOG){
			Log.debug(TAG, "Create button toggle: " + name);
		}
		Button button = new Button(new Image(region), XY.skin, "toggle");
		button.setName(name);
		return button;
	}

	/**
	 * @param text
	 * @param name
	 * @return
	 */
	public static TextField textField(String text, String messageText, String name){
		if (LOG){
			Log.debug(TAG, "Create TextField: " + name);
		}
		TextField textfield = new TextField(text, XY.skin.get(TextFieldStyle.class));
		textfield.setName(name);
		textfield.setMessageText(messageText);
		textfield.setTextFieldListener(new TextField.TextFieldListener(){
			@Override
			public void keyTyped(TextField textField, char key){
				if (key == '\n'){
					textField.getOnscreenKeyboard().show(false);
				}
			}
		});
		return textfield;
	}

	/**
	 * @param widget
	 * @param name
	 * @return
	 */
	public static ScrollPane scrollPaneTransparent(Actor widget, String name){
		if (LOG){
			Log.debug(TAG, "Create guiScrollPaneTransparent: " + name);
		}
		ScrollPane scrollPane = new ScrollPane(widget, XY.skin, "default-no-background");
		scrollPane.setName(name);
		return scrollPane;
	}

	/**
	 * @param widget
	 * @param widget2
	 * @param name
	 * @return
	 */
	public static SplitPane splitPaneVertical(Actor widget, Actor widget2, String name){
		if (LOG){
			Log.debug(TAG, "Create guiSplitPaneHorizontal: " + name);
		}
		final SplitPane splitPane = new SplitPane(widget, widget2, true, XY.skin, "default-vertical");
		splitPane.setName(name);
		return splitPane;
	}

	/**
	 * @param widget
	 * @param name
	 * @return
	 */
	public static ScrollPane scrollPane(Actor widget, String name){
		if (LOG){
			Log.debug(TAG, "Create guiScrollPane: " + name);
		}
		ScrollPane scrollPane = new ScrollPane(widget, XY.skin.get(ScrollPaneStyle.class));
		scrollPane.setName(name);
		return scrollPane;
	}

	/**
	 * @param name
	 * @return
	 */
	public static Table table(String name){
		if (LOG){
			Log.debug(TAG, "Create Window: " + name);
		}
		final Table table = new Table(XY.skin);
		table.setName(name);
		table.center();
		return table;
	}

	/**
	 * Zwraca element GUI - Window
	 *
	 * @param text
	 * @param name
	 * @return
	 */
	public static Window window(String text, String name){
		if (LOG){
			Log.debug(TAG, "Create Window: " + name);
		}
		Window window = new Window(text, XY.skin);
		window.setName(name);
		window.setX(0);
		window.setY(0);
		window.center();
		return window;
	}

	public static Window windowDialog(String text, String name){
		if (LOG){
			Log.debug(TAG, "Create Window: " + name);
		}
		Window window = new Window(text, XY.skin, "dialog");
		window.setName(name);
		window.setX(0);
		window.setY(0);
		window.center();
		return window;
	}

	/**
	 * @return
	 */
	public static Window windowNoBackground(){
		Window window = new Window("", XY.skin, "default-transparent");
		window.setMovable(false);
		window.setX(0);
		window.setY(0);
		window.center();
		return window;
	}

	/**
	 * Zwraca element GUI - Window
	 *
	 * @return
	 */
	public static Window windowBackend(){
		if (LOG){
			Log.debug(TAG, "Create guiWindowBackend ");
		}
		Window windowBackend = new Window("", XY.skin, "default-backend");
		windowBackend.setX(0);
		windowBackend.setY(0);
		windowBackend.setWidth(XY.width);
		windowBackend.setHeight(XY.height);
		windowBackend.setMovable(false);
		windowBackend.setModal(false);
		return windowBackend;
	}

	/**
	 * @param text
	 * @param name
	 * @return
	 */
	public static CheckBox checkBox(String text, String name){
		if (LOG){
			Log.debug(TAG, "Create CheckBox: " + name);
		}
		CheckBox checkBox = new CheckBox(text, XY.skin.get(CheckBoxStyle.class));
		checkBox.align(Align.left);
		checkBox.setName(name);
		return checkBox;
	}

	/**
	 * @param listItem
	 * @param name
	 * @return
	 */
	public static List<String> list(String[] listItem, String name){
		if (LOG){
			Log.debug(TAG, "Create List: " + name);
		}
		if (listItem == null){
			listItem = new String[]{"-= Select =-"};
		}
		List<String> list = new List<String>(XY.skin.get(ListStyle.class));
		list.setItems(listItem);
		list.setName(name);
		return list;
	}

	/**
	 * Zwraca element GUI - slider
	 *
	 * @param min
	 * @param max
	 * @param step
	 * @param name
	 * @return
	 */
	public static Slider slider(float min, float max, float step, String name){
		if (LOG){
			Log.debug(TAG, "Create Slider: " + name);
		}

		Slider slider = new Slider(min, max, step, false, XY.skin);
		slider.setName(name);
		return slider;
	}

	public static Slider slider(float min, float max, float step, String name, Skin skin){
		if (LOG){
			Log.debug(TAG, "Create Slider: " + name);
		}

		Slider slider = new Slider(min, max, step, false, skin);
		slider.setName(name);
		return slider;
	}

	/**
	 * Zwraca element GUI - label
	 *
	 * @param text
	 * @param name
	 * @return
	 */
	public static Label label(String text, String name){
		if (LOG){
			Log.debug(TAG, "Create Label: " + name);
		}
		Label label = new Label(text, XY.skin.get(LabelStyle.class));
		label.setName(name);
		return label;
	}

	public static Label label(String text, String name, Skin skin){
		if (LOG){
			Log.debug(TAG, "Create Label: " + name);
		}
		Label label = new Label(text, skin.get(LabelStyle.class));
		label.setName(name);
		return label;
	}

	/**
	 * @param widget
	 * @param name
	 * @return
	 */
	public static ScrollPane flickScrollPane(Actor widget, String name){
		if (LOG){
			Log.debug(TAG, "Create FlickScrollPane: " + name);
		}
		ScrollPane scrollPane = new ScrollPane(widget);
		scrollPane.setFlickScroll(true);
		scrollPane.setName(name);
		return scrollPane;
	}

	/**
	 * @param region
	 * @param name
	 * @return
	 */
	public static ScrollPane flickScrollPane(TextureRegion region, String name){
		if (LOG){
			Log.debug(TAG, "Create FlickScrollPane: " + name);
		}
		ScrollPane scrollPane = new ScrollPane(new Image(region));
		scrollPane.setFlickScroll(true);
		scrollPane.setName(name);
		return scrollPane;
	}

	/**
	 * @param widget
	 * @param widget2
	 * @param name
	 * @return
	 */
	public static SplitPane splitPaneHorizontal(Actor widget, Actor widget2, String name){
		if (LOG){
			Log.debug(TAG, "Create guiSplitPaneHorizontal: " + name);
		}
		final SplitPane splitPane = new SplitPane(widget, widget2, false, XY.skin, "default-horizontal");
		splitPane.setName(name);
		return splitPane;
	}

	/**
	 * @param item
	 * @param name
	 * @return
	 */
	public static SelectBox selectBox(String[] item, String name){
		if (LOG){
			Log.debug(TAG, "Create SelectBox: " + name);
		}
		SelectBox selectbox = new SelectBox(XY.skin.get(SelectBoxStyle.class));
		selectbox.setItems(item);
		selectbox.setName(name);
		return selectbox;
	}

	/**
	 * Zwraca elementu GUI - TextButton
	 *
	 * @param text
	 * @param name
	 * @return
	 */
	public static TextButton textButton(String text, String name){
		if (LOG){
			Log.debug(TAG, "Create TextButton: " + name);
		}
		TextButton button = new TextButton(text, XY.skin);
		button.setName(name);
		return button;
	}

	/**
	 * @param text
	 * @param name
	 * @return
	 */
	public static TextButton textButtonToggle(String text, String name){
		if (LOG){
			Log.debug(TAG, "Create TextButton: " + name);
		}
		TextButton button = new TextButton(text, XY.skin, "toggle");
		button.setName(name);
		return button;
	}

	/**
	 * @param fileName
	 * @return
	 */
	public static Image image(String fileName){
		if (LOG){
			Log.debug(TAG, "Create Button Toggle: " + fileName);
		}
		Image image = new Image(ResourceManager.getTexture(fileName));
		return image;
	}

	/**
	 * @param message
	 */
	public static void alertMemory(String message){

		long javaHeap = (Gdx.app.getJavaHeap() / 1024 / 1024);
		long nativeHeap = (Gdx.app.getNativeHeap() / 1024 / 1024);

		Log.log(TAG + "::alertMemory", "Memory: -----------------------------");
		Log.log(TAG + "::alertMemory", "Java Heap: " + (Gdx.app.getJavaHeap() / 1024 / 1024) + " MB");
		Log.log(TAG + "::alertMemory", "Native Heap: " + (Gdx.app.getNativeHeap() / 1024 / 1024) + " MB");
		Log.log(TAG + "::alertMemory", "Memory: -----------------------------");

		Alert alert = new Alert();
		alert.show(
		message + "\n\nJava Heap: " + javaHeap + " MB\n Native Heap: " + nativeHeap + " MB");

		System.gc();
	}
}
