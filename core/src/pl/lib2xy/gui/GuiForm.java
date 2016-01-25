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

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import pl.lib2xy.app.Log;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Set;

/**
 * Class to create dynamic form. Access to variables is implemented with Java reflection API
 *
 * @author Jarek
 */
public class GuiForm{

	private static final boolean DEBUG = false;
	private static final String TAG = GuiForm.class.getSimpleName();

	private static LinkedHashMap<String, Element> globalElemets = new LinkedHashMap<String, Element>(50);
	private float fadeDuration = 0.3f;
	private Table guiTable;
	private LinkedHashMap<String, Element> elemets = new LinkedHashMap<String, Element>(10);
	private LinkedHashMap<String, Actor> guiEelemets = new LinkedHashMap<String, Actor>(10);
	private String presentDir = "present/";
	private Object className = "";
	private boolean hasTwoSpan = false;

	public enum ElementType{

		FLOAT_SLIDER, BUTTON_RANDOM, BUTTON_RESET, BUTTON_GROUP, CHECKBOX, FILE_BROWSER, LIST_INDEX, SELECT_INDEX, SELECT_COLOR_INDEX, SELECT_STRING,
		COLOR_SELECT, TEXT_FIELD, SIZE2D_EDITOR, IMAGE_LIST, LABEL
	}

	private class Element{

		public String name = "";
		public String label = "";
		public float valueFloat = 0;
		public float valueFloatMin = 0f;
		public float valueFloatMax = 1f;
		public float valueFloatStep = 0.1f;
		public float valueFloatStart = 0;
		public String valueString = "";
		public String valueStringStart = "";
		public boolean valueBoolean = false;
		public String[] valueArrayString;
		public Color valueColor = Color.WHITE;
		public ElementType type = ElementType.FLOAT_SLIDER;
		public ChangeListener changeListener = null;
		public float valueX = 0;
		public float valueY = 0;
		public float valueWidth = 0;
		public float valueHeight = 0;
		public Actor actor = null;
		public int colSpan = 1;
		public boolean newRow = true;
	}

	public GuiForm(String className){
		this.className = className.toLowerCase().trim();
		elemets = new LinkedHashMap<String, Element>(10);
		guiEelemets = new LinkedHashMap<String, Actor>(10);
		hasTwoSpan = false;
	}

	//--------------------------------------------------------
	// Simple element
	//--------------------------------------------------------

	/**
	 * @param name
	 * @param label
	 * @param color
	 */
	public void addLabel(String name, String label, Color color, int colSpan, boolean newRow){

		if (DEBUG){
			Log.debug(TAG, "Add Label ... name: " + name);
		}

		Element el = new Element();
		el.name = name;
		el.label = label;
		el.valueColor = color;
		el.type = ElementType.LABEL;
		el.colSpan = colSpan;
		el.newRow = newRow;

		elemets.put(name, el);
		globalElemets.put(name, el);
	}

	/**
	 * @param name
	 * @param label
	 * @param start
	 * @param min
	 * @param max
	 * @param step
	 * @param changeListener
	 */
	public void addSlider(String name, String label, float start, float min, float max, float step, ChangeListener changeListener, int colSpan, boolean newRow){

		if (DEBUG){
			Log.debug(TAG, "Add slider ... name: " + name + " start: " + start + " min: " + min + " max: " + max
			+ " step: " + step);
		}

		Element el = new Element();
		el.name = name;
		el.label = label;
		el.valueFloat = start;
		el.valueFloatStart = start;
		el.valueFloatMin = min;
		el.valueFloatMax = max;
		el.valueFloatStep = step;
		el.changeListener = changeListener;
		el.type = ElementType.FLOAT_SLIDER;
		el.colSpan = colSpan;
		el.newRow = newRow;

		hasTwoSpan = true;

		elemets.put(name, el);
		globalElemets.put(name, el);
	}


	/**
	 * @param name
	 * @param label
	 * @param value
	 * @param changeListener
	 */
	public void addCheckBox(String name, String label, boolean value, final ChangeListener changeListener, int colSpan, boolean newRow){

		if (DEBUG){
			Log.debug(TAG, "Add CheckBox ... name: " + name + " value: " + value);
		}

		Element el = new Element();
		el.name = name;
		el.label = label;
		el.valueFloat = 0;
		el.valueBoolean = value;
		el.changeListener = changeListener;
		el.type = ElementType.CHECKBOX;
		el.colSpan = colSpan;
		el.newRow = newRow;

		elemets.put(name, el);
		globalElemets.put(name, el);
	}


	/**
	 * @param name
	 * @param label
	 * @param value
	 * @param messageText
	 * @param changeListener
	 */
	public void addTextField(String name, String label, String value, String messageText, final ChangeListener changeListener, int colSpan, boolean newRow){

		if (DEBUG){
			Log.debug(TAG, "Add TextField ... name: " + name + " value: " + value);
		}

		Element el = new Element();
		el.name = name;
		el.label = label;
		el.valueString = value;
		el.valueStringStart = messageText;
		el.changeListener = changeListener;
		el.type = ElementType.TEXT_FIELD;
		el.colSpan = colSpan;
		el.newRow = newRow;

		elemets.put(name, el);
		globalElemets.put(name, el);
	}


	/**
	 * @param name
	 * @param label
	 * @param value
	 * @param items
	 * @param changeListener
	 */
	public void addSelectIndex(String name, String label, int value, String[] items, final ChangeListener changeListener, int colSpan, boolean newRow){

		if (DEBUG){
			Log.debug(TAG, "Add SelectIndex ... name: " + name + " value: " + value);
		}

		Element el = new Element();
		el.name = name;
		el.label = label;
		el.valueFloat = value;
		el.valueArrayString = items;
		el.changeListener = changeListener;
		el.type = ElementType.SELECT_INDEX;
		el.colSpan = colSpan;
		el.newRow = newRow;

		elemets.put(name, el);
		globalElemets.put(name, el);
	}

	/**
	 * @param name
	 * @param label
	 * @param value
	 * @param items
	 * @param changeListener
	 * @param colSpan
	 * @param newRow
	 */
	public void addListIndex(String name, String label, int value, String[] items, final ChangeListener changeListener, int colSpan, boolean newRow){

		if (DEBUG){
			Log.debug(TAG, "Add ListIndex ... name: " + name + " value: " + value);
		}

		Element el = new Element();
		el.name = name;
		el.label = label;
		el.valueFloat = value;
		el.valueArrayString = items;
		el.changeListener = changeListener;
		el.type = ElementType.LIST_INDEX;
		el.colSpan = colSpan;
		el.newRow = newRow;

		elemets.put(name, el);
		globalElemets.put(name, el);
	}

	//--------------------------------------------------------
	// Complex element
	//--------------------------------------------------------

	/**
	 * @param name
	 * @param label
	 * @param values
	 * @param changeListener
	 * @param colSpan
	 * @param newRow
	 */
	public void addButtonGroup(String name, String label, final String[] values, final ChangeListener changeListener, int colSpan, boolean newRow){

		if (DEBUG){
			Log.debug(TAG, "Add ButtonGroup ... name: " + name);
		}

		Element el = new Element();
		el.name = name;
		el.label = label;
		el.valueArrayString = values;
		el.changeListener = changeListener;
		el.type = ElementType.BUTTON_GROUP;
		el.colSpan = colSpan;
		el.newRow = newRow;

		elemets.put(name, el);
		globalElemets.put(name, el);
	}


	/**
	 * @param name
	 * @param label
	 * @param changeListener
	 * @param colSpan
	 * @param newRow
	 */
	public void addColorList(String name, String label, final ChangeListener changeListener, int colSpan, boolean newRow){

		if (DEBUG){
			Log.debug(TAG, "Add ColorList ... name: " + name);
		}

		Element el = new Element();
		el.name = name;
		el.label = label;
		el.valueFloat = 0;
		el.changeListener = changeListener;
		el.type = ElementType.SELECT_COLOR_INDEX;
		el.colSpan = colSpan;
		el.newRow = newRow;

		elemets.put(name, el);
		globalElemets.put(name, el);
	}


	/**
	 * @param name
	 * @param label
	 * @param value
	 * @param changeListener
	 * @param colSpan
	 * @param newRow
	 */
	public void addColorSelect(String name, String label, Color value, final ChangeListener changeListener, int colSpan, boolean newRow){

		if (DEBUG){
			Log.debug(TAG, "Add ColorSelect ... name: " + name + " value: " + value);
		}

		Element el = new Element();
		el.name = name;
		el.label = label;
		el.valueColor = value;
		el.changeListener = changeListener;
		el.type = ElementType.COLOR_SELECT;
		el.colSpan = colSpan;
		el.newRow = newRow;

		elemets.put(name, el);
		globalElemets.put(name, el);
	}

	/**
	 * @param name
	 * @param label
	 * @param file
	 * @param directory
	 * @param changeListener
	 * @param colSpan
	 * @param newRow
	 */
	public void addFileBrowser(String name, String label, String file, String directory, final ChangeListener changeListener, int colSpan, boolean newRow){

		if (DEBUG){
			Log.debug(TAG, "Add FileBrowser ... name: " + name + " file: " + file + " directory: " + directory);
		}

		Element el = new Element();
		el.name = name;
		el.label = label;
		el.valueString = file;
		el.valueStringStart = directory;
		el.changeListener = changeListener;
		el.type = ElementType.FILE_BROWSER;
		el.colSpan = colSpan;
		el.newRow = newRow;

		elemets.put(name, el);
		globalElemets.put(name, el);
	}


	/**
	 * @param name
	 * @param label
	 * @param values
	 * @param changeListener
	 */
	public void addRandomButton(String name, String label, final String[] values, final ChangeListener changeListener, int colSpan, boolean newRow){

		if (DEBUG){
			Log.debug(TAG, "Add RandomButton ... name: " + name);
		}

		ChangeListener localChangeListener = new ChangeListener(){
			@Override
			public void changed(ChangeEvent event, Actor actor){

				Log.debug(TAG, "RandomButton change", 2);

				Gui.disableListener = true;
				for (int i = 0; i < values.length; i++){
					String key = values[i];
					Element get = elemets.get(key);

					switch (get.type){
						default:
							break;
						case FLOAT_SLIDER:
							get.valueFloat =
							(float) (get.valueFloatMin + (get.valueFloatMax / 2f) + (Math.random() * ((get.valueFloatMax - get.valueFloatMin) / 2f)));
							if (guiEelemets.containsKey(key)){
								((Slider) guiEelemets.get(key)).setValue(get.valueFloat);
							}
							break;
					}

				}
				Gui.disableListener = false;
				if (changeListener != null){
					changeListener.changed(event, actor);
				}
			}
		};

		Element el = new Element();
		el.name = name;
		el.label = label;
		el.valueArrayString = values;
		el.changeListener = localChangeListener;
		el.type = ElementType.BUTTON_RANDOM;
		el.colSpan = colSpan;
		el.newRow = newRow;

		elemets.put(name, el);
		globalElemets.put(name, el);
	}

	/**
	 * @param name
	 * @param label
	 * @param values
	 * @param changeListener
	 */
	public void addResetButton(String name, String label, final String[] values, final ChangeListener changeListener, int colSpan, boolean newRow){

		if (DEBUG){
			Log.debug(TAG, "Add ResetButton ... name: " + name);
		}

		ChangeListener localChangeListener = new ChangeListener(){
			@Override
			public void changed(ChangeEvent event, Actor actor){
				Log.debug(TAG, "RandomButtonChange", 2);

				Gui.disableListener = true;
				for (int i = 0; i < values.length; i++){
					String key = values[i];
					Element get = elemets.get(key);

					switch (get.type){
						default:
							break;
						case FLOAT_SLIDER:
							get.valueFloat = get.valueFloatStart;
							if (guiEelemets.containsKey(key)){
								((Slider) guiEelemets.get(key)).setValue(get.valueFloat);
							}
							break;
					}

				}
				Gui.disableListener = false;
				if (changeListener != null){
					changeListener.changed(event, actor);
				}
			}
		};

		Element el = new Element();
		el.name = name;
		el.label = label;
		el.valueArrayString = values;
		el.changeListener = localChangeListener;
		el.type = ElementType.BUTTON_RESET;
		el.colSpan = colSpan;
		el.newRow = newRow;

		elemets.put(name, el);
		globalElemets.put(name, el);
	}


	/**
	 * @param name
	 * @param label
	 * @param values
	 * @param changeListener
	 */
	public void addImageList(String name, String label, final String[] values, String directory, final ChangeListener changeListener, int colSpan,
							 boolean newRow){

		if (DEBUG){
			Log.debug(TAG, "Add ImageList ... name: " + name + " label: " + label);
		}

		Element el = new Element();
		el.name = name;
		el.label = label;
		el.valueArrayString = values;
		el.valueStringStart = directory;
		el.changeListener = changeListener;
		el.type = ElementType.IMAGE_LIST;
		el.colSpan = colSpan;
		el.newRow = newRow;

		elemets.put(name, el);
		globalElemets.put(name, el);
	}

	/**
	 * @param name
	 * @param label
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @param changeListener
	 */
	public void addEditorBox(String name, String label, float x, float y, float width, float height, final ChangeListener changeListener, int colSpan,
							 boolean newRow){

		if (DEBUG){
			Log.debug(TAG, "Add EditorBox ... name: " + name + " value: " + x + ":" + y + " " + width + ":" + height);
		}

		Element el = new Element();
		el.name = name;
		el.label = label;
		el.valueX = x;
		el.valueY = y;
		el.valueWidth = width;
		el.valueHeight = height;
		el.changeListener = changeListener;
		el.type = ElementType.SIZE2D_EDITOR;
		elemets.put(name, el);
		globalElemets.put(name, el);
	}


	/**
	 * @param name
	 * @param value
	 */
	public void set(String name, float value){
		elemets.get(name).valueFloat = value;
	}

	/**
	 * @param name
	 * @param value
	 */
	public void set(String name, boolean value){
		elemets.get(name).valueBoolean = value;
	}

	/**
	 * @param name
	 * @param value
	 */
	public void set(String name, int value){
		elemets.get(name).valueFloat = value;
	}

	/**
	 * @param name
	 * @param value
	 */
	public void set(String name, Color value){
		elemets.get(name).valueColor = value;
	}

	/**
	 * @param name
	 * @param value
	 */
	public void set(String name, String value){
		elemets.get(name).valueString = value;
	}

	/**
	 * @param name
	 * @return
	 */
	public float getFloat(String name){
		if (elemets.containsKey(name)){
			return elemets.get(name).valueFloat;
		}
		return 0;
	}

	/**
	 * @param name
	 * @return
	 */
	public int getInt(String name){
		if (elemets.containsKey(name)){
			return (int) elemets.get(name).valueFloat;
		}
		return 0;
	}

	public String getString(String name){
		if (elemets.containsKey(name)){
			return elemets.get(name).valueString;
		}
		return "";
	}

	public Color getColor(String name){
		if (elemets.containsKey(name)){
			return elemets.get(name).valueColor;
		}
		return Color.WHITE;
	}

	public boolean getBoolean(String name){
		if (elemets.containsKey(name)){
			return elemets.get(name).valueBoolean;
		}
		return false;
	}

	public float getX(String name){
		if (elemets.containsKey(name)){
			return elemets.get(name).valueX;
		}
		return 0;
	}

	public float getY(String name){
		if (elemets.containsKey(name)){
			return elemets.get(name).valueY;
		}
		return 0;
	}

	public float getWidth(String name){
		if (elemets.containsKey(name)){
			return elemets.get(name).valueWidth;
		}
		return 0;
	}

	public float getHeight(String name){
		if (elemets.containsKey(name)){
			return elemets.get(name).valueHeight;
		}
		return 0;
	}

	public void setX(String name, float value){
		elemets.get(name).valueX = value;
	}

	public void setY(String name, float value){
		elemets.get(name).valueY = value;
	}

	public void setWidth(String name, float value){
		elemets.get(name).valueWidth = value;
	}

	public void setHeight(String name, float value){
		elemets.get(name).valueHeight = value;
	}

	public String[] getArrayString(String name){
		return elemets.get(name).valueArrayString;
	}

	/**
	 * @return
	 */
	public Table getTable(){

		if (DEBUG){
			Log.debug(TAG, "Get Table GUI ...");
		}

		if (guiTable == null){
			Gui gui = new Gui();
			Gui.disableListener = true;
			Set<String> keySet = elemets.keySet();
			Iterator<String> iterator = keySet.iterator();

			while (iterator.hasNext()){
				String key = iterator.next();
				Element el = elemets.get(key);

				if (el.type != ElementType.FLOAT_SLIDER && hasTwoSpan){
					el.colSpan++;
				}

				switch (el.type){
					default:
						if (DEBUG){
							Log.debug(TAG, "Not support GUI from " + el.type.name(), 1);
						}
						break;

					case LABEL:
						if (DEBUG){
							Log.debug(TAG, "Create LABEL: " + key, 1);
						}
						gui.addLabel(el.label, true, 0, el.colSpan, el.valueColor);
						break;
					case FLOAT_SLIDER:
						if (DEBUG){
							Log.debug(TAG, "Create SLIDER: " + key, 1);
						}
						Slider addSlider =
						gui.addSlider(el.label, el, "valueFloat", el.valueFloat, el.valueFloatMin, el.valueFloatMax, el.valueFloatStep, el.newRow, true,
									  el.colSpan,
									  el.changeListener);
						guiEelemets.put(key, addSlider);
						el.actor = addSlider;
						break;
					case CHECKBOX:
						if (DEBUG){
							Log.debug(TAG, "Get checkbox: " + key, 1);
						}
						CheckBox addCheckBox = gui
						.addCheckBox(el.label, el, "valueBoolean", true, false, el.newRow, el.colSpan, null, el.changeListener);
						guiEelemets.put(key, addCheckBox);
						el.actor = addCheckBox;
						break;


					case BUTTON_GROUP:
						for (int i = 0; i < el.valueArrayString.length; i++){
							Button addButtonToggle = gui
							.addButtonToggle(el.valueArrayString[i], el, "valueFloat", (float) i, el.newRow, el.colSpan, el.name
							+ "_group", el.changeListener);
							guiEelemets.put(el.valueArrayString[i], addButtonToggle);
						}
						break;
					case TEXT_FIELD:
						if (DEBUG){
							Log.debug(TAG, "Get text field: " + key, 1);
						}
						TextField addTextField = gui
						.addTextField(el.label, el, "valueString", el.valueString, el.valueStringStart, el.newRow, el.colSpan, el.changeListener);
						guiEelemets.put(key, addTextField);
						el.actor = addTextField;
						break;
					case SELECT_INDEX:
						if (DEBUG){
							Log.debug(TAG, "Get select_index: " + key, 1);
						}
						SelectBox addSelectIndexBox = gui
						.addSelectIndexBox(el.label, el, "valueFloat", el.valueArrayString, (int) el.valueFloat, el.newRow, el.colSpan, el.changeListener);
						guiEelemets.put(key, addSelectIndexBox);
						el.actor = addSelectIndexBox;
						break;
					case LIST_INDEX:
						if (DEBUG){
							Log.debug(TAG, "Get list_index: " + key, 1);
						}
						List addListIndex = gui
						.addListIndex(el.label, el, "valueFloat", el.valueArrayString, (int) el.valueFloat, el.newRow, el.colSpan, el.changeListener);
						guiEelemets.put(key, addListIndex);
						el.actor = addListIndex;
						break;
					case SELECT_COLOR_INDEX:
						if (DEBUG){
							Log.debug(TAG, "Get select color index browser: " + key, 1);
						}
						List addColorList = gui
						.addColorList(el.label, el, "valueFloat", (int) el.valueFloat, el.newRow, el.colSpan, el.changeListener);
						guiEelemets.put(key, addColorList);
						el.actor = addColorList;
						break;
					case COLOR_SELECT:
						if (DEBUG){
							Log.debug(TAG, "Get select color: " + key, 1);
						}
						gui.addColorBrowserChange(el.label, el, "valueColor", el.valueColor, el.newRow, el.colSpan, el.changeListener);
						break;
					case FILE_BROWSER:
						gui.addFileBrowserChange(el.label, el, "valueString", el.valueString, el.valueStringStart, true, 1, el.changeListener);
						break;
					case BUTTON_RANDOM:
						if (DEBUG){
							Log.debug(TAG, "Get BUTTON RANDOM: " + key, 1);
						}
						Button addButton = gui.addButton(el.label, el, null, null, el.newRow, el.colSpan, false, null, el.changeListener);
						guiEelemets.put(key, addButton);
						el.actor = addButton;
						break;
					case BUTTON_RESET:
						if (DEBUG){
							Log.debug(TAG, "Get BUTTON RESET: " + key, 1);
						}
						Button addButton1 = gui.addButton(el.label, el, null, null, el.newRow, el.colSpan, false, null, el.changeListener);
						guiEelemets.put(key, addButton1);
						el.actor = addButton1;
						break;
					case IMAGE_LIST:
						if (DEBUG){
							Log.debug(TAG, "Get image list: " + key, 1);
						}
						gui.addImageList(el.label, el, "valueArrayString", el.valueArrayString, el.valueStringStart, el.newRow, el.colSpan, el.changeListener);
						break;
					case SIZE2D_EDITOR:
						if (DEBUG){
							Log.debug(TAG, "Get size2d editor: " + key, 1);
						}
						Table addEditor2d = gui
						.addEditor2d(el.label, el, "valueX", "valueY", "valueWidth", "valueHeight", el.valueX, el.valueY, el.valueWidth, el.valueHeight, true,
									 1, el.changeListener);
						guiEelemets.put(key, addEditor2d);
						el.actor = addEditor2d;
						break;

				}
			}
			guiTable = gui.getTable();
			Gui.disableListener = false;
		}

		return guiTable;
	}

	/**
	 *
	 */
	public void clearGuiTable(){
		guiTable = null;
	}

	/**
	 * @return
	 */
	public ScrollPane getScrollPane(){

		Table table = getTable();
		ScrollPane guiScrollCell = GuiResource.scrollPaneTransparent(table, "guiScrollCell");
		guiScrollCell.setScrollingDisabled(true, false);
		guiScrollCell.setFlickScroll(true);
		guiScrollCell.setFadeScrollBars(false);

		return guiScrollCell;
	}

	/**
	 *
	 */
	public void clean(){
		elemets = new LinkedHashMap<String, Element>(10);
		guiEelemets = new LinkedHashMap<String, Actor>(10);
		globalElemets = new LinkedHashMap<String, Element>(10);
	}
}
