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

package pl.lib2xy.debug.gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ArrayMap;
import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.SnapshotArray;
import pl.lib2xy.XY;
import pl.lib2xy.app.Log;
import pl.lib2xy.app.ResourceManager;
import pl.lib2xy.gui.widget.ColorSampler;
import pl.lib2xy.interfaces.GuiInterface;

import java.util.Arrays;

public class BaseForm extends Table{

	protected static final String TAG = "BaseTable";
	protected Array<String[]> definition = new Array<>();
	protected ArrayMap<String, String> values = new ArrayMap<>();
	protected ArrayMap<String, Actor> actorsMap = new ArrayMap<>();

	protected Actor actor;

	protected Skin skin;
	protected int defaultWidth = 140;
	protected int defaultLabelWidth = 70;
	protected int defaultHeight = 120;
	protected int defaultCellHeight = 300;

	protected Table headerTable;

	protected Table iconTable;
	protected Table iconTable2;
	protected Table iconTable3;
	protected Table mainTable;

	protected boolean lock = false;

	protected float width = 0;
	protected float height = 0;


	public BaseForm(Skin skin, Actor actor, String header, boolean scroll, float width, float height){

		super(skin);
		this.width = width;
		this.height = height;

		if (width > 0){
			setWidth(width);
		}
		if (height > 0){
			setHeight(height);
		}
		lock();

		headerTable = new Table(skin);
		iconTable = new Table(skin);
		iconTable2 = new Table(skin);
		iconTable3 = new Table(skin);
		mainTable = new Table(skin);

		this.skin = skin;
		if (actor != null){
			this.actor = actor;
		}

		if (header != null){
			headerTable.add(header).left();
		}

		add(headerTable).left();
		row();
		add(iconTable).left();
		row();
		add(iconTable2).left();
		row();
		add(iconTable3).left();
		row();
		if (scroll){
			final ScrollPane scrollPane = new ScrollPane(mainTable, skin.get("default-no-background", ScrollPane.ScrollPaneStyle.class));
			scrollPane.setScrollbarsOnTop(true);
			scrollPane.setScrollingDisabled(true, false);
			if (width > 0 && height > 0){
				add(scrollPane).top().left().width(width).height(height);
			} else if (width > 0){
				add(scrollPane).top().left().width(width).expandY().fillY();
			} else if (height > 0){
				add(scrollPane).top().left().height(height).expandX().fillX();
			} else {
				add(scrollPane).top().left().expandY().fillY().expandX().fillX();
			}
		} else {
			if (width > 0 && height > 0){
				add(mainTable).top().left().width(width).height(height);
			} else if (width > 0){
				add(mainTable).top().left().width(width).expandY().fillY();
			} else if (height > 0){
				add(mainTable).top().left().height(height).expandX().fillX();
			} else {
				add(mainTable).top().left().expandY().fillY().expandX().fillX();
			}
		}
		row();
		unlock();
	}

	public void setup(){
		lock();
		if (actor instanceof GuiInterface){
			setDefinition(((GuiInterface) actor).getGuiDefinition(), ((GuiInterface) actor).getValues());
			update();
		}
		unlock();
	}

	public void update(){
		lock();
		if (actor instanceof GuiInterface){
			values = ((GuiInterface) actor).getValues();
			if (values == null){
				values = new ArrayMap<>();
			}

			for (ObjectMap.Entry<String, Actor> entry : actorsMap){
				final String key = entry.key;
				Actor updateActor = entry.value;
				if (key != null && entry.value != null){
					if (values.containsKey(key)){
						final String value = values.get(key);

						if (updateActor != null){

							final String simpleName = updateActor.getClass().getSimpleName();

							switch (simpleName){
								case "TextField":
									if (updateActor.getName().contains("_textint")){
										float fl = Float.valueOf(value);
										((TextField) updateActor).setText("" + (int) fl);
									} else {
										((TextField) updateActor).setText(value);
									}
									break;
								case "CheckBox":
									((CheckBox) updateActor).setChecked(Boolean.valueOf(value));
									break;
								case "SelectBox":
									((SelectBox<String>) updateActor).setSelected(value);
									break;
								case "List":
									((List) updateActor).setSelected(value);
									break;
								case "ColorSampler":
									((ColorSampler) updateActor).setColor(Color.valueOf(value));
									break;
							}
						}
					}
				}
			}

		}
		mainTable.invalidate();
		invalidate();
		unlock();
	}

	protected void lock(){
		lock = true;
	}

	protected void unlock(){
		lock = false;
	}

	public void clear(){
		if (definition != null){
			definition.clear();
		}
		if (values != null){
			values.clear();
		}
		mainTable.clear();
		iconTable.clear();
		iconTable2.clear();
		iconTable3.clear();

	}

	public void setDefinition(Array<String[]> definition, ArrayMap<String, String> values){

		if (definition == null){
			return;
		}

		mainTable.defaults().left();

		this.definition = definition;
		this.values = values;

		for (String[] def : definition){

			if (def == null){
				continue;
			}
			if (def.length < 4){
				continue;
			}

			String label = def[0];
			String key = def[1];
			String type = def[2];

			String setValue = def[3];

			if (values != null && values.containsKey(key)){
				setValue = values.get(key);
			}

			switch (type){
				default:
					Log.debug(TAG, "Not support properties: " + Arrays.toString(def));
					break;
				case "LABEL":
					addLabel(label);
					break;
				case "SPINNER_INT":
					int min = -9999;
					int max = 9999;
					int step = 1;
					float value = Float.valueOf(setValue);
					addSpinnerInt(label, key, (int) value, min, max, step);
					break;
				case "SPINNER":
					float minF = -9999;
					float maxF = 9999;
					float stepF = 0.1f;
					float valueF = Float.valueOf(setValue);
					addSpinnerFloat(label, key, valueF, minF, maxF, stepF);
					break;
				case "TEXT":
					addTextField(label, key, setValue);
					break;
				case "TEXT_AREA":
					addTextAreaField(label, key, setValue);
					break;
				case "COLOR":
					addColor(label, key, setValue);
					break;
				case "CHECKBOX":
					boolean valueB = Boolean.valueOf(setValue);
					addCheckBox(label, key, valueB);
					break;
				case "LIST":
					String[] split = def[4].split(",");
					addSelectBox(label, key, setValue, split);
					break;
				case "FONT_LIST":
					addFontList(label, key, setValue);
					break;
				case "TEXTURE_LIST":
					addTextureList(label, key, setValue);
					break;
				case "SHADER_LIST":
					addShaderList(label, key, setValue);
					break;
				case "MUSIC_LIST":
					addMusicList(label, key, setValue);
					break;


				case "SCENE_LIST":
					addSceneList(label, key, setValue);
					break;
				case "SCENE_ITEMS":
					addSceneItemsList(label, key, setValue);
					break;
				case "ICON":
					addIcon(label, key, setValue, 1);
					break;
				case "ICON2":
					addIcon(label, key, setValue, 2);
					break;
				case "ICON3":
					addIcon(label, key, setValue, 3);
					break;

				case "BUTTON":
					addButton(label, key, setValue, "default", 1);
					break;
				case "TOGGLE":
					addButton(label, key, setValue, "toggle", 1);
					break;

				case "BUTTON_SPACE":
					addButtonSpace(label, key, setValue, "default", 1);
					break;

			}
		}

		mainTable.pack();
		mainTable.invalidate();

		pack();
		invalidate();
	}


	private void addTextField(final String text, final String key, final String value){
		final Label label = new Label(text, skin.get(Label.LabelStyle.class));
		label.setName(key + "_label");
		label.setColor(Color.LIGHT_GRAY);

		final TextField textfield = new TextField(text, skin.get(TextField.TextFieldStyle.class));
		textfield.setName(key + "_textfield");
		textfield.setText("" + value);
		textfield.setTextFieldListener(new TextField.TextFieldListener(){
			@Override
			public void keyTyped(TextField textField, char keyChar){
				if (keyChar == '\n' || keyChar == '\r'){
					textField.getOnscreenKeyboard().show(false);
					textfield.setText(textField.getText().trim());
					setProperty(key, textField.getText());
				}
			}
		});

		actorsMap.put(key, textfield);
		mainTable.add(label).left().width(defaultLabelWidth);
		mainTable.add(textfield).left().width(defaultWidth);
		mainTable.row();

	}

	private void addTextAreaField(final String text, final String key, final String value){

		final Label label = new Label(text, skin.get(Label.LabelStyle.class));
		label.setName(key + "_label");
		label.setColor(Color.LIGHT_GRAY);

		final ExtendsTextArea textArea = new ExtendsTextArea(text, skin.get(TextField.TextFieldStyle.class));
		textArea.setName(key + "_textarea");
		textArea.setText("" + value);

		ScrollPane pane = new ScrollPane(textArea, skin);
		pane.setForceScroll(false, true);
		pane.setFlickScroll(false);
		pane.setOverscroll(false, true);

		//
		// Fix slider FixScroll
		//
		pane.addListener(new InputListener(){
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
				event.stop();
				return false;
			}
		});

		final TextButton textButton = new TextButton("OK", skin);
		textButton.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y){
				textArea.getOnscreenKeyboard().show(false);
				textArea.setText(textArea.getText().trim());
				setProperty(key, textArea.getText());
			}
		});

		actorsMap.put(key, textArea);
		mainTable.add(label).left().width(defaultLabelWidth);
		mainTable.row();
		mainTable.add(pane).left().width(defaultWidth + defaultLabelWidth).colspan(2).height(defaultHeight);
		mainTable.row();
		mainTable.add(textButton).left().width(defaultWidth + defaultLabelWidth).colspan(2);
		mainTable.row();
	}


	private void addColor(final String text, final String key, final String value){

		final Label label = new Label(text, skin.get(Label.LabelStyle.class));
		label.setName(key + "_label");
		label.setColor(Color.LIGHT_GRAY);

		final ColorSampler colorSampler = new ColorSampler(skin);
		colorSampler.addListener(new ChangeListener(){
			@Override
			public void changed(ChangeEvent event, Actor actor){
				setProperty(key, colorSampler.getColor().toString());
			}
		});
		if (value != null && value.length() == 8){
			colorSampler.setColor(Color.valueOf(value));
		}

		actorsMap.put(key, colorSampler);
		mainTable.add(label).left().width(defaultLabelWidth);
		mainTable.row();
		mainTable.add(colorSampler).left().width(defaultWidth + defaultLabelWidth).colspan(2).height(defaultHeight);
		mainTable.row();
	}

	private void addSceneItemsList(final String label, final String key, final String setValue){

		if (XY.scene != null){
			final SnapshotArray<Actor> actors = XY.scene.getChildren();
			final Array<String> ar = new Array<>();

			for (Actor act : actors){
				if (act != null){
					ar.add(act.getName());
				}
			}

			String[] actorList = new String[ar.size];
			int i = 0;
			for (String s : ar){
				actorList[i] = s;
			}
			addFullList(label, key, setValue, actorList);
		}
	}


	private void addButtonSpace(final String label, final String key, final String setValue, final String styleName, int i){

		switch (i){
			default:
				iconTable.add("").left().width(20);
				break;
			case 2:
				iconTable2.add("").left().width(20);
				break;
			case 3:
				iconTable3.add("").left().width(20);
				break;
		}

	}

	private void addButton(final String label, final String key, final String setValue, final String styleName, int i){
		final ImageButton imageButton = new ImageButton(skin, styleName);
		final ImageButton.ImageButtonStyle style = new ImageButton.ImageButtonStyle(imageButton.getStyle());
		style.imageUp = ResourceManager.getIconFromClass(setValue).getDrawable();

		imageButton.setStyle(style);

		imageButton.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y){
				setProperty(key, "" + imageButton.isChecked());
			}
		});

		switch (i){
			default:
				iconTable.add(imageButton).left();
				break;
			case 2:
				iconTable2.add(imageButton).left();
				break;
			case 3:
				iconTable3.add(imageButton).left();
				break;
		}

	}

	private void addIcon(final String label, final String key, final String setValue, int i){
		final Image image = ResourceManager.getIconFromClass(setValue);

		actorsMap.put(key, image);

		image.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y){
				setProperty(key, setValue);
			}
		});

		switch (i){
			default:
				iconTable.add(image).left();
				break;
			case 2:
				iconTable2.add(image).left();
				break;
			case 3:
				iconTable3.add(image).left();
				break;
		}
	}

	/**
	 * @param text
	 */

	protected void addLabel(String text){
		final Label label = new Label(text, skin.get(Label.LabelStyle.class));
		label.setName(text);
		label.setColor(Color.YELLOW);
		actorsMap.put(text, label);
		mainTable.add(label).left().top().colspan(2);
		mainTable.row();
	}

	/**
	 * @param text
	 * @param key
	 * @param value
	 * @param min
	 * @param max
	 * @param step
	 */
	protected void addSpinnerInt(final String text, final String key, int value, int min, int max, int step){
		final Label label = new Label(text, skin.get(Label.LabelStyle.class));
		label.setName(key + "_label");
		label.setColor(Color.LIGHT_GRAY);

		final TextField textfield = new TextField("" + value, skin.get(TextField.TextFieldStyle.class));
		textfield.setName(key + "_textint");

		textfield.setTextFieldListener(new TextField.TextFieldListener(){
			@Override
			public void keyTyped(TextField textField, char keyChar){
				if (keyChar == '\n' || keyChar == '\r'){
					textField.getOnscreenKeyboard().show(false);
					textfield.setText(textField.getText().trim());
					setProperty(key, textField.getText());
				}
			}
		});

		textfield.setTextFieldFilter(new TextField.TextFieldFilter(){

			@Override
			public boolean acceptChar(TextField textField, char c){
				return Character.isDigit(c) || c == '-';
			}
		});

		textfield.addListener(new InputListener(){

			@Override
			public boolean keyUp(InputEvent event, int keycode){
				if (Input.Keys.UP == event.getKeyCode() && XY.cfg.editor){
					float textValue = 0;
					try{
						textValue = Float.valueOf(textfield.getText().trim());
						textValue += 1;
					} catch (Exception ec){
					}
					textfield.setText("" + ((int) textValue));
					textfield.setCursorPosition(textfield.getText().length());
					setProperty(key, textfield.getText());
				} else if (Input.Keys.DOWN == event.getKeyCode() && XY.cfg.editor){
					float textValue = 0;
					try{
						textValue = Float.valueOf(textfield.getText().trim());
						textValue -= 1;
					} catch (Exception ec){
					}
					textfield.setText("" + ((int) textValue));
					textfield.setCursorPosition(textfield.getText().length());
					setProperty(key, textfield.getText());
				}
				return true;
			}
		});

		actorsMap.put(key, textfield);

		mainTable.add(label).left().width(defaultLabelWidth);
		mainTable.add(textfield).left().width(defaultWidth);
		mainTable.row();
	}

	/**
	 * @param text
	 * @param key
	 * @param value
	 * @param min
	 * @param max
	 * @param step
	 */
	protected void addSpinnerFloat(final String text, final String key, float value, float min, float max, float step){
		final Label label = new Label(text, skin.get(Label.LabelStyle.class));
		label.setName(key + "_label");
		label.setColor(Color.LIGHT_GRAY);

		final TextField textfield = new TextField("" + value, skin.get(TextField.TextFieldStyle.class));
		textfield.setName(key + "_textfloat");
		textfield.setTextFieldListener(new TextField.TextFieldListener(){
			@Override
			public void keyTyped(TextField textField, char keyChar){

				if (keyChar == '\n' || keyChar == '\r'){
					textField.getOnscreenKeyboard().show(false);
					textfield.setText(textField.getText().trim());
					setProperty(key, textField.getText());
				}
			}
		});

		textfield.setTextFieldFilter(new TextField.TextFieldFilter(){

			@Override
			public boolean acceptChar(TextField textField, char c){
				return Character.isDigit(c) || c == '.' || c == '-';
			}
		});

		textfield.addListener(new InputListener(){

			@Override
			public boolean keyUp(InputEvent event, int keycode){
				if (Input.Keys.UP == event.getKeyCode() && XY.cfg.editor){
					float textValue = 0;
					try{
						textValue = Float.valueOf(textfield.getText().trim());
						textValue += 0.1;
					} catch (Exception ec){
					}
					float formatString = (float) (Math.round(textValue * 10.0) / 10.0);
					textfield.setText("" + formatString);
					textfield.setCursorPosition(textfield.getText().length());
					setProperty(key, textfield.getText());
				} else if (Input.Keys.DOWN == event.getKeyCode() && XY.cfg.editor){
					float textValue = 0;
					try{
						textValue = Float.valueOf(textfield.getText().trim());
						textValue -= 0.1;
					} catch (Exception ec){
					}
					float formatString = (float) (Math.round(textValue * 10.0) / 10.0);
					textfield.setText("" + formatString);
					textfield.setCursorPosition(textfield.getText().length());
					setProperty(key, textfield.getText());
				}
				return true;
			}
		});

		actorsMap.put(key, textfield);

		mainTable.add(label).left().width(defaultLabelWidth);
		mainTable.add(textfield).left().width(defaultWidth);
		mainTable.row();
	}

	/**
	 * @param text
	 * @param key
	 * @param value
	 */
	protected void addCheckBox(final String text, final String key, boolean value){
		final CheckBox checkBox = new CheckBox(text, skin.get(CheckBox.CheckBoxStyle.class));
		checkBox.setName(key);
		checkBox.setChecked(value);

		checkBox.addListener(new ChangeListener(){
			@Override
			public void changed(ChangeEvent event, Actor actor){
				setProperty(key, Boolean.toString(checkBox.isChecked()));
			}
		});

		actorsMap.put(key, checkBox);

		mainTable.add();
		mainTable.add(checkBox).left();
		mainTable.row();
	}

	/**
	 * @param text
	 * @param key
	 * @param value
	 * @param items
	 */
	protected void addSelectBox(final String text, final String key, final String value, final String[] items){
		final Label label = new Label(text, skin.get(Label.LabelStyle.class));
		label.setName(key + "_label");
		label.setColor(Color.LIGHT_GRAY);

		final SelectBox<String> selectbox = new SelectBox(skin.get(SelectBox.SelectBoxStyle.class));
		if (items != null){
			selectbox.setItems(items);
		}
		selectbox.setName(key + "_selectbox");
		selectbox.setSelected(value);

		selectbox.addListener(new ChangeListener(){
			@Override
			public void changed(ChangeEvent event, Actor actor){
				setProperty(key, selectbox.getSelected());
			}
		});

		actorsMap.put(key, selectbox);

		mainTable.add(label).left().width(defaultLabelWidth);
		mainTable.add(selectbox).left().width(defaultWidth);
		mainTable.row();
	}


	/**
	 * @param text
	 * @param key
	 * @param value
	 * @param items
	 */
	protected Actor addFullList(final String text, final String key, final String value, final String[] items){

		final List<String> list = new List(skin.get(List.ListStyle.class));
		Log.debug(Arrays.toString(items));
		list.setItems(items);
		list.setName(key + "_list");
		list.setSelected(value);

		list.addListener(new ChangeListener(){
			@Override
			public void changed(ChangeEvent event, Actor actor){
				setProperty(key, list.getSelected());
			}
		});

		actorsMap.put(key, list);

		final ScrollPane scrollPane = new ScrollPane(list, XY.skin.get(ScrollPane.ScrollPaneStyle.class));
		scrollPane.setFadeScrollBars(false);
		scrollPane.setScrollbarsOnTop(true);
		scrollPane.setScrollingDisabled(true, false);
		scrollPane.setHeight(defaultHeight);

		mainTable.add(scrollPane).left().width(defaultLabelWidth + defaultWidth).height(defaultHeight);
		mainTable.row();

		return list;
	}

	/**
	 * @param text
	 * @param key
	 * @param value
	 * @param items
	 */
	protected void addList(final String text, final String key, final String value, final String[] items){
		final Label label = new Label(text, skin.get(Label.LabelStyle.class));
		label.setName(key + "_label");
		label.setColor(Color.LIGHT_GRAY);

		final List<String> list = new List(skin.get(List.ListStyle.class));
		list.setItems(items);
		list.setName(key + "_list");
		list.setSelected(value);

		list.addListener(new ChangeListener(){
			@Override
			public void changed(ChangeEvent event, Actor actor){
				setProperty(key, list.getSelected());
			}
		});

		actorsMap.put(key, list);

		final ScrollPane scrollPane = new ScrollPane(list, XY.skin.get(ScrollPane.ScrollPaneStyle.class));
		scrollPane.setFadeScrollBars(false);
		scrollPane.setScrollbarsOnTop(true);
		scrollPane.setScrollingDisabled(true, false);
		mainTable.add(label).left().width(defaultLabelWidth);
		mainTable.add(scrollPane).left().width(defaultWidth).height(defaultHeight);
		mainTable.row();
	}

	/**
	 * @param label
	 * @param key
	 * @param value
	 */
	protected void addFontList(String label, String key, String value){
		final String[] bitmapFontList = ResourceManager.getBitmapFontList();
		addSelectBox(label, key, value, bitmapFontList);
	}

	/**
	 * @param label
	 * @param key
	 * @param value
	 */
	protected void addSceneList(String label, String key, String value){
		final String[] bitmapFontList = ResourceManager.getSceneMapList();
		addList(label, key, value, bitmapFontList);
	}


	/**
	 * @param label
	 * @param key
	 * @param value
	 */
	protected void addTextureList(String label, String key, String value){
		final String[] bitmapFontList = ResourceManager.getTextureRegionList();
		addSelectBox(label, key, value, bitmapFontList);
	}

	protected void addShaderList(String label, String key, String value){
		final String[] bitmapFontList = ResourceManager.getShaderList();
		addSelectBox(label, key, value, bitmapFontList);
	}

	private void addMusicList(String label, String key, String value){
		final String[] bitmapFontList = ResourceManager.getMusicList();
		addSelectBox(label, key, value, bitmapFontList);
	}

	/**
	 * @param key
	 * @param value
	 */
	protected void setProperty(final String key, final String value){

		if (lock){
			return;
		}
		lock();

		if (values != null){

			Gdx.app.postRunnable(new Runnable(){
				@Override
				public void run(){

					lock();
					Log.debug(TAG, "setProperty key:" + key + " value: " + value);

					if (key == null || value == null){
						return;
					}

					if (key.isEmpty() || value.isEmpty()){
						return;
					}

					values.put(key, value.trim());

					if (actor instanceof GuiInterface){
						try{
							((GuiInterface) actor).setValues(key, values);
						} catch (Exception ex){
							Log.error(TAG, "Error set value - key: " + key + " value: " + values, ex);
						}
					}
					unlock();
				}
			});
			unlock();
		}
	}
}

/**
 *
 */
class ExtendsTextArea extends TextArea{

	public ExtendsTextArea(String text, Skin skin){
		super(text, skin);
	}

	public ExtendsTextArea(String text, Skin skin, String styleName){
		super(text, skin, styleName);
	}

	public ExtendsTextArea(String text, TextFieldStyle style){
		super(text, style);
	}

	@Override
	protected void initialize(){
		super.initialize();
	}

	@Override
	protected InputListener createInputListener(){
		return new ExtendsTextAreaListener();
	}

	class ExtendsTextAreaListener extends TextAreaListener{

		@Override
		public boolean keyTyped(InputEvent event, char character){

			if (character == '\n'){
				character = '\r';
			}

			return super.keyTyped(event, character);
		}
	}
}