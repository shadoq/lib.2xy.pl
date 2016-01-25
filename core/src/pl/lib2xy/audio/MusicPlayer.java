
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

package pl.lib2xy.audio;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.files.FileHandle;
import pl.lib2xy.app.Log;
import pl.lib2xy.app.ResourceManager;
import pl.lib2xy.constans.MusicPlayType;

/**
 * MusicPlayer - class to management audio file and playing
 */
public class MusicPlayer{

	private static final String TAG = "MusicPlayer";

	protected static Music music;

	private static MusicPlayer instance;
	protected String musicFileName = null;
	protected MusicPlayType playType;
	protected boolean musicIsInit = false;

	/**
	 * Get instance class
	 *
	 * @return
	 */
	public static MusicPlayer getInstance(){
		if (instance == null){
			instance = new MusicPlayer();
		}
		return instance;
	}

	public void play(){
		play(musicFileName, playType);
	}

	/**
	 * Play audio file
	 *
	 * @param musicFileName
	 * @param playType
	 */
	public void play(String musicFileName, MusicPlayType playType){

		Log.debug(TAG, "Play music: " + musicFileName + " type: " + playType);

		if (!musicFileName.contains("music/")){
			musicFileName = "music/" + musicFileName;
		}

		this.musicFileName = musicFileName;
		this.playType = playType;

		if (music != null){
			try {
				if (music.isPlaying()){
					music.stop();
				}
				music = null;
				musicIsInit = false;
			} catch (Exception e){
				Log.error(TAG, "Error stop play...", e);
			}
		}

		FileHandle fileHandle = ResourceManager.getFileHandle(this.musicFileName);
		if (fileHandle.isDirectory()){
			Log.debug(TAG, "File music: " + this.musicFileName + "  is directory !");
			return;
		}
		if (!fileHandle.exists()){
			Log.debug(TAG, "File music: " + this.musicFileName + " not exists !");
			return;
		}
		try {
			Log.debug(TAG, "Load music: " + this.musicFileName);
			music = Gdx.audio.newMusic(ResourceManager.getFileHandle(this.musicFileName));
			if (music == null){
				return;
			}
			music.play();
			musicIsInit = true;
			if (playType == MusicPlayType.Once){
				music.setLooping(false);
			} else {
				music.setLooping(true);
			}
		} catch (Exception ex){
			Log.error(TAG, "Load music exception: " + this.musicFileName, ex);
		}
	}

	/**
	 * Stop playing audio file
	 */
	public void stop(){
		if (music != null){
			try {
				Log.debug(TAG, "Stop play: " + musicFileName);
				if (music.isPlaying()){
					music.stop();
				}
				music = null;
				musicIsInit = false;
			} catch (Exception e){
				Log.error(TAG, "Error stop play...", e);
			}
		}
	}
}
