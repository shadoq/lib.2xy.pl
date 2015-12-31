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

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lib2xy.gui.dialog;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import pl.lib2xy.XY;
import pl.lib2xy.app.*;
import pl.lib2xy.gui.GuiResource;
import pl.lib2xy.gui.GuiUtil;
import pl.lib2xy.gui.widget.WidgetBoolListener;
import pl.lib2xy.gui.widget.WidgetInterface;
import pl.lib2xy.gui.widget.WidgetStringListener;

import java.io.File;

/**
 * @author Jarek
 */
public class FileBrowser extends DialogBase implements WidgetInterface{

	private static final String TAG = FileBrowser.class.getSimpleName();
	protected String currentDir = "";
	protected String currentFile = "";
	protected String currentPath = "";
	protected boolean getOk = false;
	private WidgetStringListener listenerOK = null;
	private WidgetBoolListener listenerCancel = null;
	private Label fileLabel = null;
	private Label dirLabel = null;
	private List list = null;
	private ScrollPane fileList = null;
	private static String lastDefaultDir = "";
	private static String lastInternalDir = "texture";
	private static String lastInternalFile = "";
	private static String lastExternalDir = Gdx.files.getExternalStoragePath();
	private static String lastExternalFile = "";
	private static boolean externalMode = false;
	private boolean writeMode = false;
	private boolean extensionMask = false;
	private String[] fileMaskExt;

	@Override
	public void show(){
		show("", "/");
	}

	public void show(final String fileToPatch){
		show(fileToPatch, "");
	}

	public void show(final String filePatch, final String defaultDir){
		show(filePatch, defaultDir, null, null);
	}

	public void show(final String filePatch, final String defaultDir, final String fileMask){
		show(filePatch, defaultDir, fileMask, null);
	}

	public void show(String filePatch, final String defaultDir, final String fileMask, final String writeExtension){

		writeMode = false;
		extensionMask = false;
		if (writeExtension != null){
			writeMode = true;
		}
		if (fileMask != null){
			extensionMask = true;
			if (fileMask.contains("|")){
				fileMaskExt = fileMask.split("|");
			} else {
				fileMaskExt = new String[]{fileMask};
			}
		}

		mainWindow = GuiResource.windowDialog("WidgetBase", "WidgetBase");
		mainWindow.setColor(1f, 1f, 1f, 0f);
		mainWindow.setVisible(false);
		mainWindow.setModal(true);
		mainWindow.setMovable(false);

		//
		// Elementy GUI
		//
		list = GuiResource.list(new String[]{}, "listFile");
		fileList = GuiResource.scrollPane(list, "fileListScroll");
		fileList.setScrollingDisabled(true, false);
		fileList.setFadeScrollBars(false);

		dirLabel = GuiResource.label("Dir: ", "dirFile");
		fileLabel = GuiResource.label("File: ", "labelFile");

		dirLabel.setAlignment(Align.left, Align.center);
		dirLabel.setWrap(false);

		fileLabel.setAlignment(Align.left, Align.center);
		fileLabel.setWrap(false);

		final Button buttonInternalDir = GuiResource.textButton(Lang.get("internalDir"), "internalDir");
		final Button buttonExternalDir = GuiResource.textButton(Lang.get("externalDir"), "externalDir");

		final Button buttonHomeDir = GuiResource.textButton(Lang.get("homeDir"), "homeDir");

		final Button buttonOk = GuiResource.textButton(Lang.get("Ok"), "colorDialogButtonOk");
		final Button buttonCancel = GuiResource.textButton("Cancel", "colorDialogButtonCancel");

		final Button buttonNewFile = GuiResource.textButton("NewFile", "newFileBtn");

		//
		// Driver letter
		//
		final Button[] driverButtons = new Button[4];
		File[] roots = File.listRoots();

		for (int i = 0; i < roots.length; i++){
			if (i < driverButtons.length){
				Button driverButton = GuiResource.textButton("" + roots[i], "colorDialogButtonOk");

				driverButton.setName("" + roots[i]);
				//
				// External Mode
				//
				driverButton.addListener(new ClickListener(){
					@Override
					public void clicked(InputEvent event, float x, float y){
						externalMode = true;
						String name = ((Button) event.getListenerActor()).getName();
						currentDir = name;
						currentFile = "";
						currentPath = currentDir + "/" + currentFile;
						actionDirectory(currentFile, currentDir, true);
					}
				});

				driverButtons[i] = driverButton;
			}

		}

		//
		// Read Dir
		//
		getOk = false;
		filePatch = filePatch.trim();
		if (filePatch.equalsIgnoreCase("")){
			filePatch = defaultDir + "/";
			currentFile = "";
			currentDir = defaultDir;
			currentPath = defaultDir + "/";
			lastInternalDir = defaultDir;
		} else {
			FileHandle fh;
			if (externalMode){
				fh = ResourceManager.getFileHandle(filePatch);
			} else {
				fh = Gdx.files.absolute(filePatch);
			}
			if (fh.parent().path().equalsIgnoreCase("/")){
				currentFile = "";
				currentDir = fh.path();
				currentPath = fh.path();
			} else {
				currentFile = fh.name();
				currentDir = fh.parent().path();
				currentPath = fh.path();
			}
			if (externalMode){
				lastExternalDir = currentDir;
				lastExternalFile = currentFile;
				if (!defaultDir.equalsIgnoreCase(lastDefaultDir)){
					lastInternalDir = defaultDir;
					lastInternalFile = "";
				}

			} else {
				lastInternalDir = currentDir;
				lastInternalFile = currentFile;
				if (!defaultDir.equalsIgnoreCase(lastDefaultDir)){
					lastExternalDir = defaultDir;
					lastExternalFile = "";
				}
			}
		}
		lastDefaultDir = defaultDir;

		Log.debug(TAG, "-- Read Directory -----------------", 3);
		Log.debug(TAG, "filePatch: " + filePatch, 3);
		Log.debug(TAG, "defaultDir: " + defaultDir, 3);
		Log.debug(TAG, "currentFile: " + currentFile, 3);
		Log.debug(TAG, "currentDir: " + currentDir, 3);
		Log.debug(TAG, "currentPath: " + currentPath, 3);
		Log.debug(TAG, "------------------------------------", 3);

		actionDirectory(currentFile, currentDir, externalMode);
		//
		// Utworzenie okna
		//
		Table buttonTable = GuiResource.table("fileButton");

		buttonTable.row();
		buttonTable.add(buttonInternalDir).fill().expand();
		buttonTable.add(buttonExternalDir).fill().expand();

		if (Gdx.app.getType() == Application.ApplicationType.Desktop){
			buttonTable.add(buttonHomeDir).fill().expand();
		}

		for (int i = 0; i < driverButtons.length; i++){
			buttonTable.add(driverButtons[i]).fill().expand();
		}

		mainWindow.getTitleLabel().setText(Lang.get("fileBrowser"));

		int colSpan = 2;
		if (writeMode){
			colSpan = 3;
		}
		mainWindow.row();
		mainWindow.add(buttonTable).fillX().colspan(colSpan).left();
		mainWindow.row();
		mainWindow.add(dirLabel).fillX().colspan(colSpan).left();
		mainWindow.row().fill().expandX();
		mainWindow.add(fileList).colspan(colSpan).height(Cfg.gridY12).width(Cfg.gridX12);
		mainWindow.row();
		mainWindow.add(fileLabel).fillX().colspan(colSpan).left();
		mainWindow.row();
		if (writeMode){
			mainWindow.add(buttonNewFile).fill().expand();
		}
		mainWindow.add(buttonOk).fill().expand();
		mainWindow.add(buttonCancel).fill().expand();
		mainWindow.pack();
		mainWindow.setModal(true);
		mainWindow.setMovable(false);

		GuiUtil.windowPosition(mainWindow, 12, 12);
		XY.stage.addActor(mainWindow);

		//
		// Akcje GUI
		//

		//
		// List dir
		//
		list.addListener(new ChangeListener(){
			@Override
			public void changed(ChangeEvent event, Actor actor){
				updateDirectory();
			}
		});

		//
		// Button Cancel
		//
		buttonCancel.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y){
				getOk = false;
				hide();
				if (listenerCancel != null){
					listenerCancel.Action(getOk);
				}
			}
		});

		//
		// Button Ok
		//
		buttonOk.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y){

				Log.log("WidgetFileBrowser::buttonOk::click", "currentFile=" + currentPath + " event=" + event.toString());

				if (currentFile.equalsIgnoreCase("") || currentFile == null){
					return;
				}
				getOk = true;
				hide();
				if (listenerOK != null){
					listenerOK.Action(currentPath);
				}
			}
		});

		//
		// Button New File
		//
		buttonNewFile.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y){

				if (!externalMode){
					Alert alert = new Alert();
					alert.show("newFileOlnyExternal");
					return;
				}

				final InputDialog inputDialog = new InputDialog();

				WidgetBoolListener setText = new WidgetBoolListener(){
					@Override
					public void Action(boolean status){
						if (status){
							if (inputDialog.getInputText().length() < 1){
								return;
							}
							currentFile = inputDialog.getInputText() + '.' + writeExtension;
							currentPath = currentDir + "/" + currentFile;
							Log.debug(TAG, "Set new file: " + currentPath);
							FileHandle file = ResourceManager.getFileHandle(currentPath);
							file.writeString("", false);
							actionDirectory(currentFile, currentDir, externalMode);

						}
					}
				};

				inputDialog.setListener(setText);
				inputDialog.show("newFileName", "enterFileName");
			}
		});

		//
		// Internal Mode
		//
		buttonInternalDir.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y){
				externalMode = false;
				currentDir = lastInternalDir;
				currentFile = lastInternalFile;
				currentPath = currentDir + "/" + currentFile;
				actionDirectory(currentFile, currentDir, false);
			}
		});


		//
		// External Mode
		//
		buttonExternalDir.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y){
				externalMode = true;
				currentDir = lastExternalDir;
				currentFile = lastExternalFile;
				currentPath = currentDir + "/" + currentFile;
				actionDirectory(currentFile, currentDir, true);
			}
		});

		//
		// Home Mode
		//
		buttonHomeDir.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y){
				externalMode = true;
				currentDir = Gdx.files.getExternalStoragePath();
				currentFile = "";
				currentPath = currentDir + "/" + currentFile;
				actionDirectory(currentFile, currentDir, true);
			}
		});

		super.show();
	}

	@Override
	public void hide(){
		super.hide();
	}

	public void setOkListener(WidgetStringListener listener){
		listenerOK = listener;
	}

	public void setCancelListener(WidgetBoolListener listener){
		listenerCancel = listener;
	}

	public String getPatch(){
		return currentPath;
	}

	public String getDirectory(){
		return currentDir;
	}

	public String getFile(){
		return currentFile;
	}

	public boolean isExternalMode(){
		return externalMode;
	}

	/**
	 * @param fileName
	 * @param directory
	 * @param exMode
	 */
	private void actionDirectory(String fileName, String directory, boolean exMode){

		Log.debug(TAG, "-- Action Directory -----------------", 1);
		Log.debug(TAG, "exMode: " + exMode, 1);
		Log.debug(TAG, "fileName: " + fileName, 1);
		Log.debug(TAG, "directory: " + directory, 1);
		Log.debug(TAG, "-------------------------------------", 1);

		String[] files;
		if (extensionMask){
			files = XY.env.getDirectoryList(directory, directory, exMode, fileMaskExt);
		} else {
			files = XY.env.getDirectoryList(directory, directory, exMode, null);
		}

		if (exMode){

			FileHandle fh;
			for (int i = 0; i < files.length; i++){
				String string = files[i];
				fh = Gdx.files.absolute(directory + "/" + string);
				if (fh.isDirectory()){
					files[i] = "<Dir> " + string;
				}
			}

			java.util.Arrays.sort(files);
			if (directory.length() > 3){
				String[] parent = new String[]{"<Parent Dir> .."};
				String[] merageString = new String[parent.length + files.length];
				System.arraycopy(parent, 0, merageString, 0, parent.length);
				System.arraycopy(files, 0, merageString, parent.length, files.length);
				files = merageString;
			}
		} else {
			java.util.Arrays.sort(files);
		}

		list.setItems(files);
		list.invalidate();
		currentFile = fileName;
		fileLabel.setText("File: " + currentFile);
		dirLabel.setText("Dir: " + currentDir);
		list.setSelected(currentFile);
	}

	/**
	 *
	 */
	private void updateDirectory(){
		String value = list.getSelection().toString();
		if (value == null){
			return;
		}
		value = value.trim();
		if (value.equalsIgnoreCase("")){
			return;
		}

		if (currentDir.equalsIgnoreCase("/")){
			return;
		}

		if (value.equalsIgnoreCase("<Parent Dir> ..") && externalMode == true){

			FileHandle fh = Gdx.files.absolute(currentDir);
			String path = fh.parent().path();

			if (path.length() > 2){

				currentDir = fh.parent().path();
				lastExternalDir = currentDir;
				currentPath = currentDir;
				currentFile = "";

				Log.debug("WidgetDialogFileBrowser", "currentDir: " + currentDir, 1);
				Log.debug("WidgetDialogFileBrowser", "currentPath: " + currentPath, 1);
				Log.debug("WidgetDialogFileBrowser", "currentFile: " + currentFile, 1);

				actionDirectory(currentFile, currentDir, true);
			}
			return;
		}

		if ((value.startsWith("<Dir>") || value.startsWith("(Dir)") || value.startsWith("/<Dir>") || value.startsWith("//<Dir>")
		|| value.startsWith("/(Dir)") || value.startsWith("//(Dir)")) && externalMode == true){
			value = value.replace("//(Dir)", "");
			value = value.replace("/(Dir)", "");
			value = value.replace("//<Dir>", "");
			value = value.replace("/<Dir>", "");
			value = value.replace("<Dir>", "");
			value = value.replace("(Dir)", "");
			value = value.trim();

			Log.debug("WidgetDialogFileBrowser", "currentDir: " + value, 1);
			Log.debug("WidgetDialogFileBrowser", "currentDir: " + currentDir, 1);

			FileHandle fh = Gdx.files.absolute(currentDir + "/" + value + "/");
			currentDir = fh.path();
			lastExternalDir = currentDir;
			currentPath = currentDir;
			currentFile = "";

			Log.debug("WidgetDialogFileBrowser", "currentDir: " + currentDir, 1);
			Log.debug("WidgetDialogFileBrowser", "currentPath: " + currentPath, 1);
			Log.debug("WidgetDialogFileBrowser", "currentFile: " + currentFile, 1);

			actionDirectory(currentFile, currentDir, true);
			return;
		}

		currentFile = value;
		currentPath = currentDir + "/" + value;

		Log.debug("WidgetFileBrowser::selected",
				  "currentFile=" + currentFile + " currentPath=" + currentPath + " test=" + ResourceManager.getFileSaveName(currentPath));

		fileLabel.setText("File: " + currentFile);
		dirLabel.setText("Dir: " + currentDir);

		if (externalMode){
			lastExternalDir = currentDir;
			lastExternalFile = value;
		} else {
			lastInternalDir = currentDir;
			lastInternalFile = value;
		}

	}
}
