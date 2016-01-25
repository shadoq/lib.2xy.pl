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

package pl.lib2xy.gfx.g3d.shaders;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;
import pl.lib2xy.app.Log;
import pl.lib2xy.app.ResourceManager;

public class ShaderLoader{

	private static Array<String> include = new Array<>();
	private static ObjectMap<String, String> sniptet = new ObjectMap<>();

	public String parse(String code){
		StringBuilder out = new StringBuilder();
		return parse(out, code);
	}

	public String parse(StringBuilder out, String code){

		if (code == null){
			return null;
		}

		String[] lines = code.split("\n");
		int idx = 0;
		int jdx = 0;
		for (String line : lines){

			if (line.trim().startsWith("#include")){
				idx = line.indexOf("\"");
				jdx = line.indexOf("\"", idx + 1);
				if (idx > 0 && jdx > 0 && jdx > idx){
					String name = line.substring(idx + 1, jdx);
					if (name != null && name.length() > 0){

						final FileHandle fileHandle = ResourceManager.getFileHandle("shader/include/" + name);
						if (fileHandle != null && fileHandle.exists()){

//							if (!sniptet.containsKey(name)){
								Log.debug("Include shader from file: " + fileHandle.toString());
								final String string = fileHandle.readString();
								String parse = parse(new StringBuilder(), string);
								sniptet.put(name, parse);
								out.append(parse).append("\r\n");

//							} else {
//								Log.debug("Include shader from cache: " + fileHandle.toString());
//								final String cache = sniptet.get(name);
//								out.append(cache).append("\r\n");
//							}
						}
					}
				}
			} else {
				out.append(line.trim()).append("\r\n");
			}
		}

		return out.toString();
	}
}
