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

import com.badlogic.gdx.utils.reflect.ClassReflection;
import com.badlogic.gdx.utils.reflect.Field;
import pl.lib2xy.app.Log;

/**
 * @author Jarek
 */
public class Element{

	private final static String TAG = "S3Element";
	private Object obj = null;
	private Field fld = null;
	private String fieldName = "";

	/**
	 * @param obj       - A reference object to override
	 * @param fieldName - field name to modify the variable
	 */
	public Element(Object obj, String fieldName){
		this.obj = obj;
		this.fieldName = fieldName;
		this.fld = findField(obj.getClass(), fieldName);
	}

	private static Field findField(Class clazz, String filedName){

		Log.log(TAG, "Find field: " + clazz.getSimpleName() + " field: " + filedName);

		if (clazz != null && !clazz.getSimpleName().equalsIgnoreCase("class")){
			Field[] declaredFields = ClassReflection.getFields(clazz);
			for (int i = 0; i < declaredFields.length; i++){
				Field field = declaredFields[i];
				if (field.getName().equals(filedName)){
					field.setAccessible(true);
					return field;
				}
			}

			Class superClass = clazz.getSuperclass();
			if (superClass != null){
				return findField(superClass, filedName);
			}
		}
		throw new RuntimeException("No find field: " + filedName);
	}

	/**
	 * @return
	 */
	public Object getValue(){
		try{
			return fld.get(obj);
		} catch (Exception e){
			throw new RuntimeException(
			"Error getValue for field: " + fieldName + " in object" + obj.getClass().getName(), e);
		}
	}

	/**
	 * Setting a new field value
	 *
	 * @param value
	 */
	public void setValue(Object value){
		try{
			Log.debug(TAG, "setValue for field: " + fieldName + " in object  " + obj.getClass());
			fld.set(obj, value);
		} catch (Exception e){
			throw new RuntimeException(
			"Error setValue for field: " + fieldName + " in object" + obj.getClass().getName(), e);
		}
	}
}
