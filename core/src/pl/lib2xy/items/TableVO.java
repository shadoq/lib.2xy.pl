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

package pl.lib2xy.items;

public class TableVO extends ActorVO{

	public String definition = "";
	public String style = "default";
	public int columns = 0;
	public int rows = 0;
	public boolean debug = false;

	public TableVO(){
	}

	public TableVO(TableVO tableVO){
		super(tableVO);
		definition = new String(tableVO.definition);
		style = new String(tableVO.style);
		columns = tableVO.columns;
		rows = tableVO.rows;
		debug = tableVO.debug;
	}
}
