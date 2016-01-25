/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lib2xy.gui.widget;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import pl.lib2xy.XY;
import pl.lib2xy.app.Cfg;
import pl.lib2xy.app.Log;
import pl.lib2xy.app.ResourceManager;
import pl.lib2xy.gui.GuiResource;

/**
 * @author Jarek
 */
public class FileBrowser extends Table{

	private static final String TAG = FileBrowser.class.getSimpleName();
	protected String currentDir = "";
	protected String currentFile = "";
	protected String currentPath = "";
	protected boolean getOk = false;
	private Label fileLabel = null;
	private Label dirLabel = null;
	private List list = null;
	private ScrollPane fileList = null;
	private static String lastDefaultDir = "";
	private static String lastInternalDir = "texture";
	private static String lastInternalFile = "";
	private static String lastExternalDir = Gdx.files.getExternalStoragePath();
	private static String lastExternalFile = "";
	private boolean extensionMask = false;
	private String[] fileMaskExt;
	private ChangeListener changeListener = null;

	private FileBrowser instance;

	public FileBrowser(){
		super(XY.skin);
	}

	public FileBrowser(Skin skin){
		super(skin);
	}

	public Table getTable(){
		return getTable("", "");
	}

	public Table getTable(final String fileToPatch){
		return getTable(fileToPatch, "");
	}

	public Table getTable(final String filePatch, final String defaultDir){
		return getTable(filePatch, defaultDir, null);
	}

	public Table getTable(String filePatch, final String defaultDir, final String fileMask){

		extensionMask = false;
		if (fileMask != null){
			extensionMask = true;
			if (fileMask.contains("|")){
				fileMaskExt = fileMask.split("|");
			} else {
				fileMaskExt = new String[]{fileMask};
			}
		}

		//
		// Elementy GUI
		//
		list = GuiResource.list(new String[]{}, "listFile");
		fileList = GuiResource.scrollPane(list, "fileListScroll");
		fileList.setScrollingDisabled(true, false);
		fileList.setFadeScrollBars(false);
		fileList.setFillParent(true);

		dirLabel = GuiResource.label("Dir: ", "dirFile");
		fileLabel = GuiResource.label("File: ", "labelFile");

		dirLabel.setAlignment(Align.left, Align.center);
		dirLabel.setWrap(false);

		fileLabel.setAlignment(Align.left, Align.center);
		fileLabel.setWrap(false);

		//
		// Read Dir
		//
		getOk = false;
		filePatch = filePatch.trim();
		final FileHandle fileHandle = ResourceManager.getFileHandle(filePatch);

		if (fileHandle == null){
			currentFile = "";
			currentDir = defaultDir;
			currentPath = defaultDir + "/";
		} else {

			if (fileHandle.parent().path().equalsIgnoreCase("/")){
				currentFile = "";
				currentDir = fileHandle.path();
				currentPath = fileHandle.path();
			} else {
				currentFile = fileHandle.name();
				currentDir = fileHandle.parent().path();
				currentPath = fileHandle.path();
			}
		}

		lastInternalDir = currentDir;
		lastInternalFile = currentFile;
		if (!defaultDir.equalsIgnoreCase(lastDefaultDir)){
			lastExternalDir = defaultDir;
			lastExternalFile = "";
		}

		lastDefaultDir = defaultDir;

		Log.debug(TAG, "-- Read Directory -----------------", 3);
		Log.debug(TAG, "filePatch: " + filePatch, 3);
		Log.debug(TAG, "defaultDir: " + defaultDir, 3);
		Log.debug(TAG, "currentFile: " + currentFile, 3);
		Log.debug(TAG, "currentDir: " + currentDir, 3);
		Log.debug(TAG, "currentPath: " + currentPath, 3);
		Log.debug(TAG, "------------------------------------", 3);

		actionDirectory(currentFile, currentDir);

		//
		// Utworzenie okna
		//

		row();
		add(fileList).expandX().height(Cfg.gridY6).fillX().minHeight(150).maxHeight(250).left();
		row();
		add(fileLabel).fillX().expandX().left();
		pack();

		//
		// List dir
		//
		instance = this;
		list.addListener(new

						 ChangeListener(){
							 @Override
							 public void changed(ChangeEvent event, Actor actor){
								 updateDirectory();
								 if (changeListener != null){
									 changeListener.changed(new ChangeEvent(), instance);
								 }
							 }
						 }

		);

		return this;
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

	public void setListener(ChangeListener listener){
		this.changeListener = listener;
	}

	/**
	 * @param fileName
	 * @param directory
	 */
	private void actionDirectory(String fileName, String directory){

		Log.debug(TAG, "-- Action Directory -----------------", 1);
		Log.debug(TAG, "fileName: " + fileName, 1);
		Log.debug(TAG, "directory: " + directory, 1);
		Log.debug(TAG, "-------------------------------------", 1);

		String[] files;
		if (extensionMask){
			files = XY.env.getDirectoryList(directory, directory, false, fileMaskExt);
		} else {
			files = XY.env.getDirectoryList(directory, directory, false, null);
		}

		java.util.Arrays.sort(files);

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
		value = value.substring(1, value.length() - 1);
		if (value.equalsIgnoreCase("")){
			return;
		}

		if (currentDir.equalsIgnoreCase("/")){
			return;
		}

		currentFile = value;
		currentPath = currentDir + "/" + value;

		Log.debug("WidgetFileBrowser::selected",
				  "currentFile=" + currentFile + " currentPath=" + currentPath + " test=" + ResourceManager.getFileSaveName(currentPath));

		fileLabel.setText("File: " + currentFile);
		dirLabel.setText("Dir: " + currentDir);

		lastInternalDir = currentDir;
		lastInternalFile = value;

	}
}
