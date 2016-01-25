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

package pl.lib2xy.gui.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ArrayMap;
import pl.lib2xy.XY;
import pl.lib2xy.gui.GuiUtil;
import pl.lib2xy.interfaces.DataVO;
import pl.lib2xy.interfaces.GuiInterface;
import pl.lib2xy.items.ActorVO;
import pl.lib2xy.items.TableVO;

public class Table extends com.badlogic.gdx.scenes.scene2d.ui.Table implements GuiInterface, DataVO{

	protected Skin skin;
	public TableVO data = new TableVO();

	public Table(Skin skin){
		super(skin);
		this.skin = skin;
		data.width = 128;
		data.height = 128;
		data.originX = 64;
		data.originY = 64;
		setVO(data);
	}

	public Table(TableVO tableVO, Skin skin){
		super(skin);
		data = tableVO;
		setVO(data);
	}

	public Table(){
		super(XY.skin);
	}

	public int getColumnsDef(){
		return data.columns;
	}

	public int getRowsDef(){
		return data.rows;
	}

	public String getDefinition(){
		return data.definition;
	}

	public void setColumnsDef(int columns){
		data.columns = columns;
	}

	public void setRowsDef(int rows){
		data.rows = rows;
	}

	public void setDefinition(String definition){
		data.definition = definition;
	}

	public void setupDefinition(){
		setDefinition(data.definition, data.columns, data.rows);
	}

	@Override
	public void setDebug(boolean debug){
		data.debug = debug;
		super.setDebug(debug);
	}

	/**
	 * Setup table layout from string, special chars:
	 * [Text] - cell with text
	 * [label:] -
	 * [image:] -
	 * [center:]
	 * [left:]
	 * [right:]
	 * [fill:]
	 * [expand:]
	 * [] - empty cell
	 * --- - new row
	 *
	 * @param definition
	 */
	private void setDefinition(String definition, int columns, int rows){

		clear();

		if (columns > 0 && rows > 0){

			for (int y = 0; y < rows; y++){
				for (int x = 0; x < columns; x++){
					if (data.debug){
						add("rows: " + y + " cols: " + x).expand().fill();
					} else {
						add().expand().fill();
					}
				}
				if (rows > 1){
					row();
				}
			}
		} else {

			String[] rowDef, cellDef;
			if (definition.contains("---")){
				rowDef = definition.split("---");
			} else {
				rowDef = new String[]{definition};
			}

			for (String row : rowDef){

				if (row == null){
					continue;
				}

				if (row.contains("[")){
					cellDef = row.split("\\[");
				} else {
					cellDef = new String[]{row};

				}

				for (String cell : cellDef){

					if (cell == null){
						add("");
						continue;
					}
					cell = cell.trim();
					if (cell.endsWith("]")){
						cell = cell.substring(0, cell.length() - 1);
					}

					Cell currentCell = null;
					if (!cell.contains("|")){
						currentCell = add(cell);
					} else {

						final String[] cellStrings = cell.split("\\|");
						if (cellStrings != null){
							currentCell = add(cellStrings[0]);
							if (currentCell != null){
								for (int i = 1; i < cellStrings.length; i++){
									if (cellStrings[i].contains("center")){
										currentCell.center();
									} else if (cellStrings[i].contains("left")){
										currentCell.left();
									} else if (cellStrings[i].contains("right")){
										currentCell.right();
									} else if (cellStrings[i].contains("fillX")){
										currentCell.fillX();
									} else if (cellStrings[i].contains("fillY")){
										currentCell.fillY();
									} else if (cellStrings[i].contains("expandX")){
										currentCell.expandX();
									} else if (cellStrings[i].contains("expandY")){
										currentCell.expandY();
									} else if (cellStrings[i].contains("fill")){
										currentCell.fill();
									} else if (cellStrings[i].contains("expand")){
										currentCell.expand();
									}
								}
							}
						}
					}
				}
				if (rowDef.length > 1){
					row();
				}
			}
		}
	}


	public void setVO(TableVO tableVO){
		data = tableVO;
		GuiUtil.setActorVO(data, this);
		setDefinition(data.definition, data.columns, data.rows);
	}

	public void updateVO(){
		GuiUtil.updateActorVO(data, this);
	}

	@Override
	public ActorVO getVO(){
		return data;
	}

	@Override
	public Array<String[]> getGuiDefinition(){
		Array<String[]> guiDef = new Array<>();

		guiDef.add(new String[]{this.getClass().getSimpleName(), "", "LABEL", ""});
		guiDef.add(new String[]{"name", "name", "TEXT", ""});

		guiDef.add(new String[]{"columns", "columns", "SPINNER_INT", "0"});
		guiDef.add(new String[]{"rows", "rows", "SPINNER_INT", "0"});
		guiDef.add(new String[]{"definition", "definition", "TEXT_AREA", ""});

		guiDef.add(new String[]{"log", "log", "CHECKBOX", "false"});

		Label.getGuiDefinition(guiDef);
		return guiDef;
	}

	@Override
	public ArrayMap<String, String> getValues(){

		updateVO();
		ArrayMap<String, String> values = new ArrayMap<>();

		values.put("name", data.name);
		values.put("columns", data.columns + "");
		values.put("rows", data.rows + "");
		values.put("definition", data.definition);
		values.put("log", data.debug + "");

		values.put("x", data.x + "");
		values.put("y", data.y + "");
		values.put("width", data.width + "");
		values.put("height", data.height + "");
		values.put("originX", data.originX + "");
		values.put("originY", data.originY + "");
		values.put("rotation", data.rotation + "");
		values.put("color", data.color.toString());
		values.put("touchable", data.touchable + "");
		values.put("visible", data.visible + "");
		values.put("zindex", data.zIndex + "");

		return values;
	}

	@Override
	public void setValues(String changeKey, ArrayMap<String, String> values){

		//
		// Check
		//
		if (Integer.valueOf(values.get("zindex")) < 0){
			values.put("zindex", "0");
		}

		//
		// Set
		//
		if (changeKey.equals("name")){
			setName(values.get("name"));
		}
		if (changeKey.equals("columns")){
			data.columns = Integer.valueOf(values.get("columns"));
			setDefinition(data.definition, data.columns, data.rows);
		}
		if (changeKey.equals("rows")){
			data.rows = Integer.valueOf(values.get("rows"));
			setDefinition(data.definition, data.columns, data.rows);
		}
		if (changeKey.equals("definition")){
			data.definition = values.get("definition");
			setDefinition(data.definition, data.columns, data.rows);
		}
		if (changeKey.equals("log")){
			setDebug(Boolean.valueOf(values.get("log")));
			setDefinition(data.definition, data.columns, data.rows);
		}
		if (changeKey.equals("x")){
			setX(Float.valueOf(values.get("x")));
		}
		if (changeKey.equals("y")){
			setY(Float.valueOf(values.get("y")));
		}
		if (changeKey.equals("width")){
			setWidth(Float.valueOf(values.get("width")));
		}
		if (changeKey.equals("height")){
			setHeight(Float.valueOf(values.get("height")));
		}
		if (changeKey.equals("originX")){
			setOriginX(Float.valueOf(values.get("originX")));
		}
		if (changeKey.equals("originY")){
			setOriginY(Float.valueOf(values.get("originY")));
		}
		if (changeKey.equals("rotation")){
			setRotation(Float.valueOf(values.get("rotation")));
		}
		if (changeKey.equals("color")){
			setColor(Color.valueOf(values.get("color")));
		}
		if (changeKey.equals("touchable")){
			setTouchable(Touchable.valueOf(values.get("touchable")));
		}
		if (changeKey.equals("visible")){
			setVisible(Boolean.valueOf(values.get("visible")));
		}
		if (changeKey.equals("zindex")){
			setZIndex((int) (float) (Integer.valueOf(values.get("zindex"))));
		}
		updateVO();
	}
}
