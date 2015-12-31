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

package pl.lib2xy.utils;

import java.util.Collection;
import java.util.Iterator;
import java.util.StringTokenizer;

/**
 * Small class to text operations
 */
public class TextUtil{

	private TextUtil(){
	}

	/*
	 * Capitalizes the First Letter of a String
	 */
	public static String capitalize(String text){
		if (text != null && text != ""){
			return (text.substring(0, 1)).toUpperCase() + text.substring(1);
		} else {
			return "";
		}
	}

	/*
	 * UnCapitalizes the First Letter of a String
	 */
	public static String uncapitalize(String text){
		return text.substring(0, 1).toLowerCase() + text.substring(1);
	}


	/**
	 * Clear string for file name
	 *
	 * @param fileName
	 * @return
	 */
	public static String clearFileName(String fileName){
		return fileName.trim()
					   .replace(".json", "")
					   .replace(".java", "")
					   .replace(" ", "_")
					   .replace("/", "_")
					   .replace("\\", "_")
					   .replace("@", "_")
					   .replace("%", "_")
					   .replace(":", "_");
	}

	public static String trimWhitespace(String var0){
		if (var0 == null){
			return var0;
		} else {
			StringBuffer var1 = new StringBuffer();

			for (int var2 = 0; var2 < var0.length(); ++var2){
				char var3 = var0.charAt(var2);
				if (var3 != 10 && var3 != 12 && var3 != 13 && var3 != 9){
					var1.append(var3);
				}
			}

			return var1.toString().trim();
		}
	}

	public static String join(Collection var0, String var1){
		StringBuffer var2 = new StringBuffer();

		for (Iterator var3 = var0.iterator(); var3.hasNext(); var2.append((String) var3.next())){
			if (var2.length() != 0){
				var2.append(var1);
			}
		}

		return var2.toString();
	}
}
