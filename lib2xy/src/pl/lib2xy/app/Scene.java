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
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.Timer;
import pl.lib2xy.XY;
import pl.lib2xy.constans.InterpolationType;
import pl.lib2xy.constans.ScreenEffectType;
import pl.lib2xy.gfx.g2d.EffectCreator;
import pl.lib2xy.gui.GuiUtil;
import pl.lib2xy.gui.ui.Dialog;
import pl.lib2xy.interfaces.GestureInterface;
import pl.lib2xy.interfaces.KeyboardInterface;
import pl.lib2xy.interfaces.SceneInterface;
import pl.lib2xy.interfaces.TouchInterface;
import pl.lib2xy.items.SceneVO;

/**
 * Main "magical" scene class. Implement method for all components
 */
public abstract class Scene extends Group implements SceneInterface, KeyboardInterface, TouchInterface, GestureInterface{

	final protected static String TAG = "Scene";
	final protected static boolean enableDebug = true;
	protected Image backgroundImage = null;

	public SceneVO data;

	public Scene(){
		data = new SceneVO();
		data.reset();
	}

	public Scene(SceneVO data){
		this.data = data;
	}

	@Override
	public void initialize(){

	}

	@Override
	public void update(float delta){

	}

	@Override
	public void preRender(float delta){

	}

	@Override
	public void render(float delta){

	}

	@Override
	public void postRender(float delta){

	}

	@Override
	public void resize(){
		if (enableDebug){
			Log.debug(TAG, "Scene resize ...");
		}
	}

	@Override
	public void pause(){
		if (enableDebug){
			Log.debug(TAG, "Scene pause ...");
		}
	}

	@Override
	public void dispose(){
		if (enableDebug){
			Log.debug(TAG, "Scene dispose ...");
		}
	}

	@Override
	public void resume(){
		if (enableDebug){
			Log.debug(TAG, "Scene resume ...");
		}
	}

	//-------------------------------------------------------------------
	//-------------------------------------------------------------------
	// Input method
	//-------------------------------------------------------------------
	//-------------------------------------------------------------------
	@Override
	public void onClick(InputEvent event, int x, int y){
		if (enableDebug){
			Log.debug(TAG, "onClick event: " + event + " x: " + x + " y:" + y);
		}
	}

	@Override
	public void onClick(InputEvent event, Actor actor, int x, int y){
		if (enableDebug){
			Log.debug(TAG, "onClick on Actor event: " + event + " actor: " + actor + " x: " + x + " y:" + y);
		}
	}

	@Override
	public void onDrag(int x, int y){
		if (enableDebug){
			Log.debug(TAG, "onDrag x: " + x + " y:" + y);
		}
	}

	@Override
	public void onTouchDown(int x, int y, int button){
		if (enableDebug){
			Log.debug(TAG, "onTouchDown x: " + x + " y:" + y + " button: " + button);
		}
	}

	@Override
	public void onTouchUp(int x, int y, int button){
		if (enableDebug){
			Log.debug(TAG, "onTouchUp x: " + x + " y:" + y + " button: " + button);
		}
	}

	@Override
	public void onKeyDown(int keycode){
		if (enableDebug){
			Log.debug(TAG, "onKeyDown keycode: " + keycode);
		}
		if (keycode == Input.Keys.MENU){
			Gdx.input.vibrate(300);
		}
	}

	@Override
	public void onKeyUp(int keycode){
		if (enableDebug){
			Log.debug(TAG, "onKeyUp keycode: " + keycode);
		}
	}

	public void onMouseMoved(int screenX, int screenY){
	}

	//-------------------------------------------------------------------
	//-------------------------------------------------------------------
	// Gesture detection method
	//-------------------------------------------------------------------
	//-------------------------------------------------------------------
	@Override
	public void onZoom(float initialDistance, float distance){
		if (enableDebug){
			Log.debug(TAG, "onZoom initialDistance: " + initialDistance + " distance: " + distance);
		}
	}

	@Override
	public void onTap(float x, float y, int count, int button){
		if (enableDebug){
			Log.debug(TAG, "onTap x: " + x + " y: " + y + " count: " + count + " button: " + button);
		}
	}

	@Override
	public void onPinch(Vector2 initialPointer1, Vector2 initialPointer2, Vector2 pointer1, Vector2 pointer2){
		if (enableDebug){
			Log.debug(TAG, "onPinch initialPointer1: " + initialPointer1 + " initialPointer2: " + initialPointer2
			+ " pointer1: " + pointer1 + " pointer2: " + pointer2);
		}
	}

	@Override
	public void onPan(float x, float y, float deltaX, float deltaY){
		if (enableDebug){
			Log.debug(TAG, "onPan x: " + x + " y: " + y + " deltaX: " + deltaX + " deltaY: " + deltaY);
		}
	}

	@Override
	public void onPanStop(float x, float y, int pointer, int button){
		if (enableDebug){
			Log.debug(TAG, "onPanStop x: " + x + " y: " + y + " pointer: " + pointer + " button: " + button);
		}
	}

	@Override
	public void onLongPress(float x, float y){
		if (enableDebug){
			Log.debug(TAG, "onLongPress x: " + x + " y: " + y);
		}
	}

	@Override
	public void onFling(float velocityX, float velocityY, int button){
		if (enableDebug){
			Log.debug(TAG, "onFling velocityX: " + velocityX + " velocityY: " + velocityY + " button: " + button);
		}
	}


	//----------------------------------------------------------
	//----------------------------------------------------------
	// Scene GFX method
	//----------------------------------------------------------
	//----------------------------------------------------------

	/**
	 * Settings the application image background
	 *
	 * @param backgroundFileName
	 */
	public void setBackground(String backgroundFileName){
		if (backgroundImage != null){
			removeBackground();
		}
		if (ResourceManager.getTextureRegion(backgroundFileName) != null){
			backgroundImage = new Image(new TextureRegionDrawable(ResourceManager.getTextureRegion(backgroundFileName)), Scaling.stretch);
			backgroundImage.setX(0);
			backgroundImage.setY(0);
			backgroundImage.setWidth(XY.width);
			backgroundImage.setHeight(XY.height);
			XY.preRenderStage.addActor(backgroundImage);
			backgroundImage.toBack();
		}
	}

	/**
	 * Remove the background image
	 */
	public void removeBackground(){
		XY.preRenderStage.getRoot().removeActor(backgroundImage);
		backgroundImage = null;
	}

	//----------------------------------------------------------
	//----------------------------------------------------------
	// Scene setup method
	//----------------------------------------------------------
	//----------------------------------------------------------

	/**
	 * Returns the current scene class
	 */
	public static Scene getScene(){
		return Run.getScene();
	}

	/**
	 * Load the scene class and data for name
	 *
	 * @param sceneName - Application Name
	 */
	public static void setScene(String sceneName){
		Run.setScene(sceneName);
	}

	/**
	 * Load the scene class and data for name with delay
	 *
	 * @param sceneName - Application Name
	 * @param delay     - Dalay in second
	 */
	public static void setScene(String sceneName, float delay){
		Run.setScene(sceneName, delay);
	}

	/**
	 * Load the scene class and data for name with delay
	 *
	 * @param sceneName - Application Name
	 * @param delay     - Dalay in second
	 */
	public static void setSceneWithDelay(String sceneName, float delay){
		Run.setScene(sceneName, delay);
	}

	/**
	 * Load the next scene class and data
	 */
	public static void nextScene(){
		if (XY.sceneIndex <= XY.sceneMaps.size){
			XY.sceneIndex++;
		}
		Run.setScene(XY.sceneMaps.getKeyAt(XY.sceneIndex));
	}

	/**
	 * Load the previous scene class and data
	 */
	public static void prevScene(){
		if (XY.sceneIndex >= 0){
			XY.sceneIndex--;
		}
		Run.setScene(XY.sceneMaps.getKeyAt(XY.sceneIndex));
	}

	/**
	 * Load the next scene class and data with delay
	 *
	 * @param delay
	 */
	public static void nexSceneWithDelay(float delay){
		if (XY.sceneIndex <= XY.sceneMaps.size){
			XY.sceneIndex++;
		}
		Run.setScene(XY.sceneMaps.getKeyAt(XY.sceneIndex), delay);
	}

	/**
	 * Load the previous scene class and data with delay
	 *
	 * @param delay
	 */
	public static void prevSceneWithDelay(float delay){
		if (XY.sceneIndex >= 0){
			XY.sceneIndex--;
		}
		Run.setScene(XY.sceneMaps.getKeyAt(XY.sceneIndex), delay);
	}

	//-------------------------------------------------------------------
	//-------------------------------------------------------------------
	// Scene actor method
	//-------------------------------------------------------------------
	//-------------------------------------------------------------------

	/**
	 * Add actor to the application
	 *
	 * @param actor
	 * @param x
	 * @param y
	 */
	public void addActor(Actor actor, float x, float y){
		if (actor != null){
			actor.setPosition(x, y);
			addActor(actor);
		}
	}

	/**
	 * Add actor to the application with delay
	 *
	 * @param actor
	 * @param delay
	 */
	public void addActorWithDelay(final Actor actor, float delay){
		Timer.schedule(new Timer.Task(){
			@Override
			public void run(){
				addActor(actor);
			}
		}, delay);
	}

	/**
	 * Remove actor form application
	 *
	 * @param actorName
	 * @return
	 */
	public boolean removeActor(String actorName){
		return removeActor(findActor(actorName));
	}

	/**
	 * Remove actor form application with delay
	 *
	 * @param actor
	 * @param delay
	 */
	public void removeActorWithDelay(Actor actor, float delay){
		addAction(Actions.sequence(Actions.delay(delay), Actions.removeActor(actor)));
	}

	/**
	 * Finds the actor in App on the X/Y position
	 *
	 * @param x
	 * @param y
	 * @return
	 */
	public Actor hit(float x, float y){
		Actor actor = hit(x, y, true);
		if (actor != null){
			while (actor.getParent() != this){
				actor = actor.getParent();
			}
		}
		return actor;
	}


	/**
	 * Copy a basic parameters from source actor to the destination actor
	 *
	 * @param source
	 * @param destination
	 * @return
	 */
	public Actor copyActor(Actor source, Actor destination){
		GuiUtil.copyActor(source, destination);
		return destination;
	}

	/**
	 * Copy a basic parameters from source actor to the destination actor
	 * and replace in App
	 *
	 * @param source
	 * @param destination
	 * @return
	 */
	public Actor replaceActor(Actor source, Actor destination){
		GuiUtil.copyActor(source, destination);
		removeActor(source);
		addActor(destination);
		return destination;
	}

	/**
	 * @param nameActor
	 * @param sceneName
	 * @return
	 */
	public Actor actorSceneOnClick(final String nameActor, final String sceneName){

		final Actor actor = findActor(nameActor);
		if (actor == null){
			log("[actorSceneOnClick] Can't find actor: " + nameActor);
			return null;
		}

		actor.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y){
				if (enableDebug){
					log("Click on Actor: " + nameActor + " set scene: " + sceneName);
				}
				setScene(sceneName);
			}
		});

		return actor;
	}

	/**
	 * @param nameActor
	 * @param sceneName
	 * @param delay
	 * @return
	 */
	public Actor actorDelaySceneOnClick(final String nameActor, final String sceneName, final float delay){
		final Actor actor = findActor("nameActor");
		if (actor == null){
			return null;
		}

		actor.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y){
				if (enableDebug){
					log("Click on Actor: " + nameActor + " set scene: " + sceneName);
				}
				setSceneWithDelay(sceneName, delay);
			}
		});

		return actor;
	}

	/**
	 * @param nameActor
	 * @param effectType
	 * @return
	 */
	public Actor actorEffect(final String nameActor, ScreenEffectType effectType){
		return actorEffect(nameActor, effectType, 1f, InterpolationType.Linear);
	}

	/**
	 * @param nameActor
	 * @param effectType
	 * @param duration
	 * @param interpolation
	 * @return
	 */
	public Actor actorEffect(final String nameActor, ScreenEffectType effectType, float duration, InterpolationType interpolation){

		final Actor actor = findActor(nameActor);
		if (actor == null){
			log("[actorSceneOnClick] Can't find actor: " + nameActor);
			return null;
		}

		EffectCreator.createEffect(actor, effectType, 0f, duration, interpolation);
		return actor;
	}


	//----------------------------------------------------------
	//----------------------------------------------------------
	// Other scene method
	//----------------------------------------------------------
	//----------------------------------------------------------

	/**
	 * Save text to debug
	 *
	 * @param text
	 */
	public void log(String text){
		Log.debug(getName(), text);
	}

	/**
	 * Return random float value on 0 to range
	 *
	 * @param range
	 * @return
	 */
	public float random(float range){
		return MathUtils.random(range);
	}

	/**
	 * Show Toast GUI element
	 *
	 * @param message
	 * @param duration
	 */
	public void showToast(String message, float duration){
		Table table = new Table(XY.skin);
		table.add(message).center();
		table.setBackground(XY.skin.getDrawable("dialogDim"));
		table.pack();
		table.setPosition(XY.width / 2 - table.getWidth() / 2, XY.height / 2 - table.getHeight() / 2);
		addActor(table);
		table.addAction(Actions.sequence(Actions.alpha(0), Actions.fadeIn(0.4f), Actions.delay(duration), Actions.fadeOut(0.4f), Actions.removeActor(table)));
	}

	/**
	 * Show simple Dialog with OK button
	 *
	 * @param message
	 */
	public Dialog showDialog(String message){
		Dialog dialog = new Dialog("Dialog", XY.skin);
		dialog.getContentTable().add(message);
		dialog.button("OK", "OK");
		dialog.pack();
		dialog.show(getStage());
		dialog.setPosition(XY.width / 2 - dialog.getWidth() / 2, XY.height / 2 - dialog.getHeight() / 2);
		return dialog;
	}

	/**
	 * Show simple Dialog with OK button and custom title
	 *
	 * @param title
	 * @param message
	 */
	public Dialog showMessageDialog(String title, String message){
		Dialog dialog = new Dialog(title, XY.skin);
		dialog.getContentTable().add(message);
		dialog.button("OK", "OK");
		dialog.pack();
		dialog.show(getStage());
		dialog.setPosition(XY.width / 2 - dialog.getWidth() / 2, XY.height / 2 - dialog.getHeight() / 2);
		return dialog;
	}

	/**
	 * Show confirm Dialog with YES.NO button and custom title
	 *
	 * @param title
	 * @param message
	 * @return
	 */
	public Dialog showConfirmDialog(String title, String message){
		Dialog dialog = new Dialog(title, XY.skin);
		dialog.getContentTable().add(message);
		dialog.button("Yes", "Yes");
		dialog.button("No", "No");
		dialog.pack();
		dialog.show(getStage());
		dialog.setPosition(XY.width / 2 - dialog.getWidth() / 2, XY.height / 2 - dialog.getHeight() / 2);
		return dialog;
	}


	public void exitApp(){

		if (!XY.cfg.editor){

			Gdx.app.exit();

			//			Dialog dialog = new Dialog("Exit ?", S3.skin){
			//				@Override
			//				protected void result(Object object){
			//					if (object == null){
			//						return;
			//					}
			//					if (((String) object).equals("Yes")){
			//						Gdx.app.exit();
			//					}
			//				}
			//			};
			//			dialog.setBackground(S3.skin.getDrawable("white_pixel"));
			//			dialog.getContentTable().add("Are you sure to exit application ?");
			//			dialog.button("Yes", "Yes");
			//			dialog.button("No", "No");
			//			dialog.pack();
			//			dialog.setPosition(S3.width / 2 - dialog.getWidth() / 2, S3.height / 2 - dialog.getHeight() / 2);
			//			dialog.show(S3.debugStage);
		}
	}
}
