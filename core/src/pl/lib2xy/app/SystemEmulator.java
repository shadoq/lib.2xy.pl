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

package pl.lib2xy.app;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import pl.lib2xy.XY;

public class SystemEmulator{

	public void writeFile(String filename, String data){
	}

	/**
	 *
	 */
	public void updateAssets(){

	}

	/**
	 * @param directory
	 * @param defaultDir
	 * @param externalMode
	 * @param extensionMask
	 * @return
	 */
	public String[] getDirectoryList(String directory, String defaultDir, boolean externalMode, final String[] extensionMask){
		return new String[]{};
	}

	public void testAppDir(){

		if (XY.appDir == null){
			XY.appDir = "";
		}
		if (XY.externalDir == null){
			XY.externalDir = "";
		}
	}

	/**
	 *
	 */
	public void createAssetsDirectoryStructure(){

	}

	/**
	 *
	 */
	public void frameRateLimit(){
	}
}
