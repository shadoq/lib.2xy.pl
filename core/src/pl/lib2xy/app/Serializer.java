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

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import pl.lib2xy.XY;
import pl.lib2xy.gui.ui.Sprite;
import pl.lib2xy.interfaces.DataVO;
import pl.lib2xy.items.SpriteVO;
import pl.lib2xy.constans.InterpolationType;
import pl.lib2xy.constans.MusicPlayType;
import pl.lib2xy.constans.ScreenEffectType;
import pl.lib2xy.gfx.g3d.shaders.SimpleShader;
import pl.lib2xy.gui.ui.*;
import pl.lib2xy.items.*;

/**
 * Class to read/write game object from/to json text file
 */
public class Serializer{

	private static final String TAG = "Serializer";


	/**
	 * Read/Write SceneVO setting
	 */
	private static class SceneVOSerializer implements Json.Serializer<SceneVO>{

		@Override
		public SceneVO read(Json json, JsonValue jsonData, Class type){
			Log.debug(TAG, "json: " + json);
			SceneVO sceneVO = new SceneVO();
			if (jsonData.has("name")){
				sceneVO.name = jsonData.getString("name", "Unknown");
			}
			if (jsonData.has("backgroundColor")){
				sceneVO.backgroundColor = Color.valueOf(jsonData.getString("backgroundColor", "00000000"));
			}
			if (jsonData.has("background")){
				sceneVO.backgroundFileName = jsonData.getString("background", "None");
			}
			if (jsonData.has("transition")){
				sceneVO.transition = ScreenEffectType.valueOf(jsonData.getString("transition", "None"));
			}
			if (jsonData.has("transitionDuration")){
				sceneVO.transitionDuration = Float.valueOf(jsonData.getString("transitionDuration", "1.0"));
			}
			if (jsonData.has("transitionInterpolation")){
				sceneVO.transitionInterpolation = InterpolationType.valueOf(jsonData.getString("transitionInterpolation", "Linear"));
			}
			if (jsonData.has("music")){
				sceneVO.music = jsonData.getString("music", "None");
			}
			if (jsonData.has("musicType")){
				sceneVO.musicPlayType = MusicPlayType.valueOf(jsonData.getString("musicType", "None"));
			}
			return sceneVO;
		}

		@Override
		public void write(Json json, SceneVO sceneVO, Class knownType){

			Log.debug(TAG, "json: " + json);

			json.writeObjectStart();
			json.writeValue("class", sceneVO.getClass().getName());
			json.writeValue("name", sceneVO.name);
			json.writeValue("backgroundColor", sceneVO.backgroundColor.toString());

			json.writeValue("transition", sceneVO.transition.toString());
			json.writeValue("transitionDuration", sceneVO.transitionDuration);
			json.writeValue("transitionInterpolation", sceneVO.transitionInterpolation.toString());

			json.writeValue("background", sceneVO.backgroundFileName);

			json.writeValue("music", sceneVO.music);
			json.writeValue("musicType", sceneVO.musicPlayType.toString());

			json.writeObjectEnd();
		}
	}


	/**
	 * Read/Write scene setting
	 */
	private static class AppSerializer implements Json.Serializer<SceneImpl>{

		@Override
		public SceneImpl read(Json json, JsonValue jsonData, Class type){
			Log.debug(TAG, "json: " + json);
			SceneImpl app = new SceneImpl();
			SceneVO sceneVO = new SceneVO();

			if (jsonData.has("name")){
				sceneVO.name = jsonData.getString("name", "Unknown");
			}
			if (jsonData.has("backgroundColor")){
				sceneVO.backgroundColor = Color.valueOf(jsonData.getString("backgroundColor", "00000000"));
			}
			if (jsonData.has("background")){
				sceneVO.backgroundFileName = jsonData.getString("background", "None");
			}
			if (jsonData.has("transition")){
				sceneVO.transition = ScreenEffectType.valueOf(jsonData.getString("transition", "None"));
			}
			if (jsonData.has("transitionDuration")){
				sceneVO.transitionDuration = Float.valueOf(jsonData.getString("transitionDuration", "1.0"));
			}
			if (jsonData.has("transitionInterpolation")){
				sceneVO.transitionInterpolation = InterpolationType.valueOf(jsonData.getString("transitionInterpolation", "Linear"));
			}
			if (jsonData.has("music")){
				sceneVO.music = jsonData.getString("music", "None");
			}
			if (jsonData.has("musicType")){
				sceneVO.musicPlayType = MusicPlayType.valueOf(jsonData.getString("musicType", "None"));
			}
			if (XY.scene != null){
				XY.scene.data = sceneVO;
			}
			return app;
		}

		@Override
		public void write(Json json, SceneImpl app, Class knownType){
			Log.debug(TAG, "json: " + json);

			json.writeObjectStart();
			json.writeValue("class", app.getClass().getName());
			json.writeValue("name", app.data.name);
			json.writeValue("backgroundColor", app.data.backgroundColor.toString());

			json.writeValue("transition", app.data.transition.toString());
			json.writeValue("transitionDuration", app.data.transitionDuration);
			json.writeValue("transitionInterpolation", app.data.transitionInterpolation.toString());

			json.writeValue("background", app.data.backgroundFileName);

			json.writeValue("music", app.data.music);
			json.writeValue("musicType", app.data.musicPlayType.toString());

			json.writeObjectEnd();
		}
	}


	/**
	 * Read/Write libGdx 2d Actor data
	 */
	private static class ActorSerializer implements Json.Serializer<Actor>{

		public static void readActor(JsonValue jsonData, Actor actor){

			if (jsonData.has("name")){
				actor.setName(jsonData.getString("name"));
			}
			if (jsonData.has("x")){
				actor.setX(jsonData.getFloat("x"));
			}
			if (jsonData.has("y")){
				actor.setY(jsonData.getFloat("y"));
			}
			if (jsonData.has("width")){
				actor.setWidth(jsonData.getFloat("width"));
			}
			if (jsonData.has("height")){
				actor.setHeight(jsonData.getFloat("height"));
			}
			if (jsonData.has("ox")){
				actor.setOriginX(jsonData.getFloat("ox"));
			}
			if (jsonData.has("oy")){
				actor.setOriginY(jsonData.getFloat("oy"));
			}
			if (jsonData.has("rotation")){
				actor.setRotation(jsonData.getFloat("rotation"));
			}
			if (jsonData.getString("color") != null && (jsonData.getString("color").length() == 6 || jsonData.getString("color").length() == 8)){
				actor.setColor(Color.valueOf(jsonData.getString("color")));
			}
			if (jsonData.has("touchable")){
				actor.setTouchable(Touchable.valueOf(jsonData.getString("touchable")));
			}
			if (jsonData.has("visible")){
				actor.setVisible(jsonData.getBoolean("visible"));
			}
			if (jsonData.has("zindex")){
				actor.setZIndex(jsonData.getInt("zindex"));
			}
			if (actor instanceof DataVO){
				((DataVO) actor).getVO().fromEditor = true;
			}
			if (XY.scene != null){
				String newName = actor.getName();
				if (newName == null){
					newName = actor.getClass().getSimpleName();
				}
				int counter = 1;
				while (Run.getScene().findActor(newName) != null){
					newName = actor.getName() + "_" + counter;
					counter++;
				}
				actor.setName(newName);
				XY.scene.addActor(actor);
			}
		}

		public static void writeActor(Json json, Actor actor){
			json.writeValue("class", actor.getClass().getName());
			json.writeValue("name", actor.getName());
			json.writeValue("x", actor.getX());
			json.writeValue("y", actor.getY());
			json.writeValue("width", actor.getWidth());
			json.writeValue("height", actor.getHeight());
			json.writeValue("ox", actor.getOriginX());
			json.writeValue("oy", actor.getOriginY());
			json.writeValue("rotation", actor.getRotation());
			json.writeValue("zindex", actor.getZIndex());
			json.writeValue("color", actor.getColor().toString());
			json.writeValue("touchable", actor.getTouchable().toString());
			json.writeValue("visible", actor.isVisible());
		}


		public static void readActorVO(JsonValue jsonData, ActorVO actorVO){

			if (jsonData.has("name")){
				actorVO.name = jsonData.getString("name");
			}
			if (actorVO.name == null || actorVO.name.isEmpty()){
				actorVO.name = actorVO.getClass().getSimpleName();
			}
			if (jsonData.has("x")){
				actorVO.x = jsonData.getFloat("x");
			}
			if (jsonData.has("y")){
				actorVO.y = jsonData.getFloat("y");
			}
			if (jsonData.has("width")){
				actorVO.width = jsonData.getFloat("width");
			}
			if (jsonData.has("height")){
				actorVO.height = jsonData.getFloat("height");
			}
			if (jsonData.has("ox")){
				actorVO.originX = jsonData.getFloat("ox");
			}
			if (jsonData.has("oy")){
				actorVO.originY = jsonData.getFloat("oy");
			}
			if (jsonData.has("rotation")){
				actorVO.rotation = jsonData.getFloat("rotation");
			}
			if (jsonData.getString("color") != null && (jsonData.getString("color").length() == 6 || jsonData.getString("color").length() == 8)){
				actorVO.color = Color.valueOf(jsonData.getString("color"));
			}
			if (jsonData.has("touchable")){
				actorVO.touchable = jsonData.getString("touchable");
			}
			if (jsonData.has("visible")){
				actorVO.visible = jsonData.getBoolean("visible");
			}
			if (jsonData.has("zindex")){
				actorVO.zIndex = jsonData.getInt("zindex");
			}

			if (jsonData.has("log")){
				actorVO.debug = jsonData.getBoolean("log");
			}
			if (jsonData.has("scaleX")){
				actorVO.scaleX = jsonData.getFloat("scaleX");
			}
			if (jsonData.has("scaleY")){
				actorVO.scaleY = jsonData.getFloat("scaleY");
			}
			if (jsonData.has("id")){
				actorVO.id = jsonData.getString("id");
			}
			if (jsonData.has("layer")){
				actorVO.layer = jsonData.getString("layer");
			}
			if (jsonData.has("tag")){
				actorVO.tag = jsonData.getString("tag");
			}
			if (jsonData.has("customVars")){
				actorVO.customVars = jsonData.getString("customVars");
			}
			actorVO.fromEditor = true;
		}

		public static void writeActorVO(Json json, Actor actor, ActorVO actorVO){

			json.writeValue("class", actor.getClass().getName());

			json.writeValue("name", actorVO.name);
			json.writeValue("x", actorVO.x);
			json.writeValue("y", actorVO.y);
			json.writeValue("width", actorVO.width);
			json.writeValue("height", actorVO.height);
			json.writeValue("ox", actorVO.originX);
			json.writeValue("oy", actorVO.originY);
			json.writeValue("rotation", actorVO.rotation);
			json.writeValue("zIndex", actorVO.zIndex);
			json.writeValue("color", actorVO.color.toString());
			json.writeValue("touchable", actorVO.touchable);
			json.writeValue("visible", actorVO.visible);

			json.writeValue("log", actorVO.debug);
			json.writeValue("scaleX", actorVO.scaleX);
			json.writeValue("scaleY", actorVO.scaleY);

			json.writeValue("id", actorVO.id);
			json.writeValue("layer", actorVO.layer);
			json.writeValue("tag", actorVO.tag);
			json.writeValue("customVars", actorVO.customVars);


			json.writeValue("norX", (float) (Math.round((actorVO.x / (float) XY.width) * 100.0) / 100.0));
			json.writeValue("norY", (float) (Math.round((actorVO.y / (float) XY.height) * 100.0) / 100.0));
			json.writeValue("norWidth", (float) (Math.round((actorVO.width / (float) XY.width) * 100.0) / 100.0));
			json.writeValue("norHeight", (float) (Math.round((actorVO.height / (float) XY.height) * 100.0) / 100.0));
			json.writeValue("norOx", (float) (Math.round((actorVO.originX / (float) XY.width) * 100.0) / 100.0));
			json.writeValue("norOy", (float) (Math.round((actorVO.originY / (float) XY.height) * 100.0) / 100.0));
		}


		public static void addActor(Actor actor){

			if (Run.getScene() != null && actor != null){
				String newName = actor.getName();
				if (newName == null){
					newName = actor.getClass().getSimpleName();
				}
				int counter = 1;
				while (Run.getScene().findActor(newName) != null){
					newName = actor.getName() + "_" + counter;
					counter++;
				}
				actor.setName(newName);
				Run.getScene().addActor(actor);
			}
		}

		@Override
		public Actor read(Json json, JsonValue jsonData, Class arg2){
			Actor actor = new Actor();
			readActor(jsonData, actor);
			return actor;
		}

		@Override
		public void write(Json json, Actor actor, Class arg2){
			json.writeObjectStart();
			writeActor(json, actor);
			json.writeObjectEnd();
		}

	}

	/**
	 * Read/Write label data
	 */
	private static class LabelSerializer implements Json.Serializer<Label>{

		@Override
		public void write(Json json, Label label, Class knownType){

			Log.debug("LabelSerializer", "Write: " + label.getName());
			json.writeObjectStart();
			Serializer.ActorSerializer.writeActorVO(json, label, label.data);

			json.writeValue("text", label.data.text);
			json.writeValue("styleName", label.data.style);
			json.writeValue("align", label.data.align);
			json.writeValue("wrap", label.data.wrap);
			json.writeValue("font", label.data.font);

			json.writeObjectEnd();
		}

		@Override
		public Label read(Json json, JsonValue jsonData, Class type){

			Log.debug("LabelSerializer", "Read: " + jsonData.getString("name"));

			LabelVO labelVO = new LabelVO();
			Serializer.ActorSerializer.readActorVO(jsonData, labelVO);

			if (jsonData.has("text")){
				labelVO.text = jsonData.getString("text");
			}
			if (jsonData.has("styleName")){
				labelVO.style = jsonData.getString("styleName");
			}
			if (jsonData.has("align")){
				labelVO.align = jsonData.getString("align");
			}
			if (jsonData.has("wrap")){
				labelVO.wrap = jsonData.getBoolean("wrap");
			}
			if (jsonData.has("font")){
				labelVO.font = jsonData.getString("font");
			}

			Label label = new Label(labelVO, XY.skin);
			Serializer.ActorSerializer.addActor(label);
			return label;
		}

	}

	/**
	 * Read/Write Button data
	 */
	private static class ButtonSerializer implements Json.Serializer<Button>{


		@Override
		public void write(Json json, Button button, Class knownType){

			Log.debug("TextButtonSerializer", "Write: " + button.getName());
			json.writeObjectStart();
			Serializer.ActorSerializer.writeActorVO(json, button, button.data);
			json.writeValue("style", button.data.style);
			json.writeObjectEnd();
		}

		@Override
		public Button read(Json json, JsonValue jsonData, Class type){
			Log.debug("TextButtonSerializer", "Read: " + jsonData.getString("name"));

			ButtonVO buttonVO = new ButtonVO();
			Serializer.ActorSerializer.readActorVO(jsonData, buttonVO);

			if (jsonData.has("style")){
				buttonVO.style = jsonData.getString("style");
			}

			Button button = new Button(buttonVO, XY.skin);
			Serializer.ActorSerializer.addActor(button);
			return button;
		}
	}

	/**
	 *
	 */
	private static class ImageSerializer implements Json.Serializer<Image>{

		@Override
		public void write(Json json, Image image, Class knownType){
			Log.debug("ImageSerializer", "Write: " + image.getName());
			json.writeObjectStart();
			Serializer.ActorSerializer.writeActorVO(json, image, image.data);
			json.writeValue("texture", image.data.image);
			json.writeObjectEnd();
		}

		@Override
		public Image read(Json json, JsonValue jsonData, Class type){
			Log.debug("ImageSerializer", "Read: " + jsonData.getString("name"));

			ImageVO imageVO = new ImageVO();

			if (jsonData.has("texture")){
				imageVO.image = jsonData.getString("texture");
			}
			if (imageVO.image.isEmpty()){
				imageVO.image = "def256";
			}
			Serializer.ActorSerializer.readActorVO(jsonData, imageVO);
			Image image = new Image(imageVO);
			Serializer.ActorSerializer.addActor(image);
			return image;
		}
	}

	/**
	 *
	 */
	private static class ImageButtonSerializer implements Json.Serializer<ImageButton>{

		@Override
		public void write(Json json, ImageButton image, Class knownType){
			Log.debug("ImageButtonSerializer", "Write: " + image.getName());

			json.writeObjectStart();
			Serializer.ActorSerializer.writeActorVO(json, image, image.data);
			json.writeValue("texture", image.data.image);
			json.writeObjectEnd();
		}

		@Override
		public ImageButton read(Json json, JsonValue jsonData, Class type){
			Log.debug("ImageButtonSerializer", "Read: " + jsonData.getString("name"));

			ImageButtonVO imageButtonVO = new ImageButtonVO();

			if (jsonData.has("texture")){
				imageButtonVO.image = jsonData.getString("texture");
			}
			if (imageButtonVO.image.isEmpty()){
				imageButtonVO.image = "def256";
			}
			Serializer.ActorSerializer.readActorVO(jsonData, imageButtonVO);
			ImageButton imageButton = new ImageButton(imageButtonVO, XY.skin);
			Serializer.ActorSerializer.addActor(imageButton);
			return imageButton;
		}
	}

	/**
	 *
	 */
	private static class TextButtonSerializer implements Json.Serializer<TextButton>{

		@Override
		public void write(Json json, TextButton button, Class knownType){

			Log.debug("TextButtonSerializer", "Write: " + button.getName());
			json.writeObjectStart();
			Serializer.ActorSerializer.writeActorVO(json, button, button.data);

			json.writeValue("text", button.data.text);
			json.writeValue("style", button.data.style);
			json.writeValue("align", button.data.align);
			json.writeValue("wrap", button.data.wrap);
			json.writeValue("font", button.data.font);

			json.writeObjectEnd();
		}

		@Override
		public TextButton read(Json json, JsonValue jsonData, Class type){
			Log.debug("TextButtonSerializer", "Read: " + jsonData.getString("name"));

			TextButtonVO textButtonVO = new TextButtonVO();
			Serializer.ActorSerializer.readActorVO(jsonData, textButtonVO);

			if (jsonData.has("text")){
				textButtonVO.text = jsonData.getString("text");
			}
			if (jsonData.has("style")){
				textButtonVO.style = jsonData.getString("style");
			}
			if (jsonData.has("align")){
				textButtonVO.align = jsonData.getString("align");
			}
			if (jsonData.has("wrap")){
				textButtonVO.wrap = jsonData.getBoolean("wrap");
			}
			if (jsonData.has("font")){
				textButtonVO.font = jsonData.getString("font");
			}

			TextButton textButton = new TextButton(textButtonVO, XY.skin);
			Serializer.ActorSerializer.addActor(textButton);
			return textButton;
		}
	}

	private static class SliderSerializer implements Json.Serializer<Slider>{

		@Override
		public void write(Json json, Slider slider, Class knownType){

			Log.debug("SliderSerializer", "Write: " + slider.getName());

			json.writeObjectStart();
			Serializer.ActorSerializer.writeActorVO(json, slider, slider.data);

			json.writeValue("value", slider.data.value);
			json.writeValue("min", slider.data.min);
			json.writeValue("max", slider.data.max);
			json.writeValue("stepSize", slider.data.step);
			json.writeValue("vertical", slider.data.vertical);
			json.writeObjectEnd();
		}

		@Override
		public Slider read(Json json, JsonValue jsonData, Class type){
			Log.debug("SliderSerializer", "Read: " + jsonData.getString("name"));

			SliderVO sliderVO = new SliderVO();
			Serializer.ActorSerializer.readActorVO(jsonData, sliderVO);

			if (jsonData.has("value")){
				sliderVO.value = jsonData.getFloat("value");
			}
			if (jsonData.has("min")){
				sliderVO.min = jsonData.getFloat("min");
			}
			if (jsonData.has("max")){
				sliderVO.max = jsonData.getFloat("max");
			}
			if (jsonData.has("stepSize")){
				sliderVO.step = jsonData.getFloat("stepSize");
			}
			if (jsonData.has("vertical")){
				sliderVO.vertical = jsonData.getBoolean("vertical");
			}

			Slider slider = new Slider(sliderVO, XY.skin);
			Serializer.ActorSerializer.addActor(slider);
			return slider;
		}
	}

	private static class TextFieldSerializer implements Json.Serializer<TextField>{

		@Override
		public void write(Json json, TextField textField, Class knownType){

			Log.debug("TextFieldSerializer", "Write: " + textField.getName());

			json.writeObjectStart();
			Serializer.ActorSerializer.writeActorVO(json, textField, textField.data);
			json.writeValue("text", textField.data.text);
			json.writeValue("messageText", textField.data.messageText);
			json.writeValue("style", textField.data.style);
			json.writeValue("font", textField.data.font);
			json.writeObjectEnd();
		}

		@Override
		public TextField read(Json json, JsonValue jsonData, Class type){
			Log.debug("TextFieldSerializer", "Read: " + jsonData.getString("name"));

			TextFieldVO textFieldVO = new TextFieldVO();
			Serializer.ActorSerializer.readActorVO(jsonData, textFieldVO);

			if (jsonData.has("text")){
				textFieldVO.text = jsonData.getString("text");
			}
			if (jsonData.has("messageText")){
				textFieldVO.messageText = jsonData.getString("messageText");
			}
			if (jsonData.has("style")){
				textFieldVO.style = jsonData.getString("style");
			}
			if (jsonData.has("font")){
				textFieldVO.font = jsonData.getString("font");
			}

			TextField textField = new TextField(textFieldVO, XY.skin);
			Serializer.ActorSerializer.addActor(textField);
			return textField;
		}

	}

	/**
	 *
	 */
	private static class CheckBoxSerializer implements Json.Serializer<CheckBox>{

		@Override
		public void write(Json json, CheckBox checkBox, Class knownType){
			Log.debug("CheckBoxSerializer", "Write: " + checkBox.getName());

			json.writeObjectStart();
			Serializer.ActorSerializer.writeActorVO(json, checkBox, checkBox.data);

			json.writeValue("text", checkBox.data.text);
			json.writeValue("style", checkBox.data.style);
			json.writeValue("state", checkBox.data.state);

			json.writeValue("styleName", checkBox.data.style);
			json.writeValue("font", checkBox.data.font);

			json.writeObjectEnd();
		}

		@Override
		public CheckBox read(Json json, JsonValue jsonData, Class type){
			Log.debug("CheckBoxSerializer", "Read: " + jsonData.getString("name"));

			CheckBoxVO checkBoxVO = new CheckBoxVO();
			Serializer.ActorSerializer.readActorVO(jsonData, checkBoxVO);

			if (jsonData.has("text")){
				checkBoxVO.text = jsonData.getString("text");
			}
			if (jsonData.has("state")){
				checkBoxVO.state = Boolean.valueOf(jsonData.getString("state"));
			}
			if (jsonData.has("styleName")){
				checkBoxVO.style = jsonData.getString("styleName", "");
			}
			if (jsonData.has("font")){
				checkBoxVO.font = jsonData.getString("font", "");
			}

			CheckBox checkBox = new CheckBox(checkBoxVO, XY.skin);
			Serializer.ActorSerializer.addActor(checkBox);
			return checkBox;
		}
	}

	/**
	 *
	 */
	private static class ImageTextButtonSerializer implements Json.Serializer<ImageTextButton>{

		@Override
		public void write(Json json, ImageTextButton image, Class knownType){
			Log.debug("ImageTextButtonSerializer", "Write: " + image.getName());

			json.writeObjectStart();
			Serializer.ActorSerializer.writeActorVO(json, image, image.data);
			json.writeValue("texture", image.data.image);
			json.writeValue("text", image.data.text);
			json.writeValue("style", image.data.style);
			json.writeValue("font", image.data.font);
			json.writeObjectEnd();

		}

		@Override
		public ImageTextButton read(Json json, JsonValue jsonData, Class type){
			Log.debug("ImageTextButtonSerializer", "Read: " + jsonData.getString("name"));

			ImageTextButtonVO imageTextButtonVO = new ImageTextButtonVO();

			if (jsonData.has("texture")){
				imageTextButtonVO.image = jsonData.getString("texture");
			}
			if (imageTextButtonVO.image.isEmpty()){
				imageTextButtonVO.image = "def256";
			}
			if (jsonData.has("text")){
				imageTextButtonVO.text = jsonData.getString("text");
			}
			if (jsonData.has("style")){
				imageTextButtonVO.style = jsonData.getString("style");
			}
			if (jsonData.has("font")){
				imageTextButtonVO.font = jsonData.getString("font");
			}

			Serializer.ActorSerializer.readActorVO(jsonData, imageTextButtonVO);
			ImageTextButton imageTextButton = new ImageTextButton(imageTextButtonVO, XY.skin);
			Serializer.ActorSerializer.addActor(imageTextButton);
			return imageTextButton;
		}
	}

	private static class ProgressBarSerializer implements Json.Serializer<ProgressBar>{

		@Override
		public void write(Json json, ProgressBar progressBar, Class knownType){

			Log.debug("SliderSerializer", "Write: " + progressBar.getName());

			json.writeObjectStart();
			Serializer.ActorSerializer.writeActorVO(json, progressBar, progressBar.data);

			json.writeValue("value", progressBar.data.value);
			json.writeValue("min", progressBar.data.min);
			json.writeValue("max", progressBar.data.max);
			json.writeValue("stepSize", progressBar.data.step);
			json.writeValue("vertical", progressBar.data.vertical);
			json.writeObjectEnd();
		}

		@Override
		public ProgressBar read(Json json, JsonValue jsonData, Class type){
			Log.debug("SliderSerializer", "Read: " + jsonData.getString("name"));

			ProgressBarVO progressBarVO = new ProgressBarVO();
			Serializer.ActorSerializer.readActorVO(jsonData, progressBarVO);

			if (jsonData.has("value")){
				progressBarVO.value = jsonData.getFloat("value");
			}
			if (jsonData.has("min")){
				progressBarVO.min = jsonData.getFloat("min");
			}
			if (jsonData.has("max")){
				progressBarVO.max = jsonData.getFloat("max");
			}
			if (jsonData.has("stepSize")){
				progressBarVO.step = jsonData.getFloat("stepSize");
			}
			if (jsonData.has("vertical")){
				progressBarVO.vertical = jsonData.getBoolean("vertical");
			}

			ProgressBar progressBar = new ProgressBar(progressBarVO, XY.skin);
			Serializer.ActorSerializer.addActor(progressBar);
			return progressBar;
		}

	}

	/**
	 *
	 */
	private static class FxEffectSerializer implements Json.Serializer<FxEffect>{

		@Override
		public void write(Json json, FxEffect fxEffect, Class knownType){

			Log.debug("FxEffectSerializer", "Write: " + fxEffect.getName());

			json.writeObjectStart();
			Serializer.ActorSerializer.writeActorVO(json, fxEffect, fxEffect.data);

			if (fxEffect.getEffect() != null){
				fxEffect.data.effect = fxEffect.getEffect().getClass().getName();
				json.writeValue("effect", fxEffect.data.effect);
				fxEffect.getEffect().write(json, fxEffect);
			}
			json.writeObjectEnd();
		}

		@Override
		public FxEffect read(Json json, JsonValue jsonData, Class type){
			Log.debug("LabelSerializer", "Read: " + jsonData.getString("name"));

			FxEffectVO fxEffectVO = new FxEffectVO();
			Serializer.ActorSerializer.readActorVO(jsonData, fxEffectVO);

			if (jsonData.has("effect")){
				fxEffectVO.effect = jsonData.getString("effect");
			}

			FxEffect fxEffect = new FxEffect(fxEffectVO);
			try{
				fxEffect.getEffect().read(json, jsonData);
			} catch (Exception ex){
			}

			Serializer.ActorSerializer.addActor(fxEffect);
			return fxEffect;
		}
	}

	private static class SpriteSerializer implements Json.Serializer<Sprite>{

		@Override
		public Sprite read(Json json, JsonValue jsonData, Class knownType){

			SpriteVO spriteVO = new SpriteVO();

			float tmpDur = 0;

			if (jsonData.has("duration")){
				tmpDur = jsonData.getFloat("duration");
			}
			if (tmpDur < 0){
				tmpDur = 0.0f;
			}
			spriteVO.duration = tmpDur;

			if (jsonData.has("textures")){
				spriteVO.textures = jsonData.getString("textures");
			}
			if (jsonData.has("frameCount")){
				spriteVO.frames = jsonData.getInt("frameCount");
			}
			if (jsonData.has("active")){
				spriteVO.animationActive = jsonData.getBoolean("active");
			}
			if (jsonData.has("looping")){
				spriteVO.animationLooping = jsonData.getBoolean("looping");
			}
			if (jsonData.has("single")){
				spriteVO.singleImage = jsonData.getBoolean("single");
			}
			ActorSerializer.readActorVO(jsonData, spriteVO);

			Sprite sprite = new Sprite(spriteVO);
			ActorSerializer.addActor(sprite);
			return sprite;
		}


		@Override
		public void write(Json json, Sprite sprite, Class type){
			json.writeObjectStart();
			Serializer.ActorSerializer.writeActorVO(json, sprite, sprite.data);
			json.writeValue("textures", sprite.data.textures);
			json.writeValue("duration", sprite.data.duration);
			json.writeValue("active", sprite.data.animationActive);
			json.writeValue("looping", sprite.data.animationLooping);
			json.writeValue("single", sprite.data.singleImage);
			json.writeValue("frameCount", sprite.data.frames);
			json.writeObjectEnd();
		}

	}

	/**
	 *
	 */
	private static class GameAreaSerializer implements Json.Serializer<GameArea>{

		@Override
		public void write(Json json, GameArea gameArea, Class knownType){

			Log.debug("GameAreaSerializer", "Write: " + gameArea.getName());
			json.writeObjectStart();
			Serializer.ActorSerializer.writeActorVO(json, gameArea, gameArea.data);
			json.writeObjectEnd();
		}

		@Override
		public GameArea read(Json json, JsonValue jsonData, Class type){
			Log.debug("GameAreaSerializer", "Read: " + jsonData.getString("name"));

			GameAreaVO gameAreaVO = new GameAreaVO();
			ActorSerializer.readActorVO(jsonData, gameAreaVO);

			GameArea gameArea = new GameArea(gameAreaVO);
			ActorSerializer.addActor(gameArea);
			return gameArea;
		}
	}


	private static class TableSerializer implements Json.Serializer<Table>{

		@Override
		public void write(Json json, Table table, Class knownType){

			Log.debug("TableSerializer", "Write: " + table.getName());
			json.writeObjectStart();

			Serializer.ActorSerializer.writeActorVO(json, table, table.data);
			json.writeValue("columns", table.data.columns);
			json.writeValue("rows", table.data.rows);
			json.writeValue("definition", table.data.definition);
			json.writeValue("log", table.data.debug);

			json.writeObjectEnd();
		}

		@Override
		public Table read(Json json, JsonValue jsonData, Class type){
			Log.debug("TableSerializer", "Read: " + jsonData.getString("name"));

			TableVO tableVO = new TableVO();
			ActorSerializer.readActorVO(jsonData, tableVO);

			if (jsonData.has("columns")){
				tableVO.columns = jsonData.getInt("columns", 0);
			}
			if (jsonData.has("rows")){
				tableVO.rows = jsonData.getInt("rows", 0);
			}
			if (jsonData.has("definition")){
				tableVO.definition = jsonData.getString("definition", "");
			}
			if (jsonData.has("log")){
				tableVO.debug = jsonData.getBoolean("log", false);
			}

			Table table = new Table(tableVO, XY.skin);
			ActorSerializer.addActor(table);
			return table;
		}
	}


	private static class SelectBoxSerializer implements Json.Serializer<SelectBox>{

		@Override
		public void write(Json json, SelectBox selectBox, Class knownType){

			Log.debug("SelectBoxSerializer", "Write: " + selectBox.getName());

			json.writeObjectStart();
			Serializer.ActorSerializer.writeActorVO(json, selectBox, selectBox.data);
			json.writeValue("items", selectBox.data.items);
			json.writeValue("style", selectBox.data.style);
			json.writeValue("font", selectBox.data.font);
			json.writeObjectEnd();
		}

		@Override
		public SelectBox read(Json json, JsonValue jsonData, Class type){
			Log.debug("SelectBoxSerializer", "Read: " + jsonData.getString("name"));

			SelectBoxVO selectBoxVO = new SelectBoxVO();
			Serializer.ActorSerializer.readActorVO(jsonData, selectBoxVO);

			if (jsonData.has("items")){
				selectBoxVO.items = jsonData.getString("items");
			}
			if (jsonData.has("style")){
				selectBoxVO.style = jsonData.getString("style");
			}
			if (jsonData.has("font")){
				selectBoxVO.font = jsonData.getString("font");
			}

			SelectBox selectBox = new SelectBox(selectBoxVO, XY.skin);
			Serializer.ActorSerializer.addActor(selectBox);
			return selectBox;
		}
	}


	private static class TextAreaSerializer implements Json.Serializer<TextArea>{

		@Override
		public void write(Json json, TextArea textArea, Class knownType){

			Log.debug("TextAreaSerializer", "Write: " + textArea.getName());

			json.writeObjectStart();
			Serializer.ActorSerializer.writeActorVO(json, textArea, textArea.data);
			json.writeValue("text", textArea.data.text);
			json.writeValue("messageText", textArea.data.messageText);
			json.writeValue("style", textArea.data.style);
			json.writeValue("font", textArea.data.font);
			json.writeObjectEnd();
		}

		@Override
		public TextArea read(Json json, JsonValue jsonData, Class type){
			Log.debug("TextAreaSerializer", "Read: " + jsonData.getString("name"));

			TextAreaVO textAreaVO = new TextAreaVO();
			Serializer.ActorSerializer.readActorVO(jsonData, textAreaVO);

			if (jsonData.has("text")){
				textAreaVO.text = jsonData.getString("text");
			}
			if (jsonData.has("messageText")){
				textAreaVO.messageText = jsonData.getString("messageText");
			}
			if (jsonData.has("style")){
				textAreaVO.style = jsonData.getString("style");
			}
			if (jsonData.has("font")){
				textAreaVO.font = jsonData.getString("font");
			}

			TextArea textArea = new TextArea(textAreaVO, XY.skin);
			Serializer.ActorSerializer.addActor(textArea);
			return textArea;
		}
	}


	private static class TouchpadSerializer implements Json.Serializer<Touchpad>{

		@Override
		public void write(Json json, Touchpad touchpad, Class knownType){

			Log.debug("Touchpad", "Write: " + touchpad.getName());
			json.writeObjectStart();
			Serializer.ActorSerializer.writeActorVO(json, touchpad, touchpad.data);

			Serializer.ActorSerializer.writeActor(json, touchpad);
			json.writeValue("radius", touchpad.data.deadzoneRadius);
			json.writeValue("style", touchpad.data.style);
			json.writeObjectEnd();
		}

		@Override
		public Touchpad read(Json json, JsonValue jsonData, Class type){
			Log.debug("Touchpad", "Read: " + jsonData.getString("name"));

			TouchpadVO touchpadVO = new TouchpadVO();
			Serializer.ActorSerializer.readActorVO(jsonData, touchpadVO);

			if (jsonData.has("radius")){
				touchpadVO.deadzoneRadius = jsonData.getFloat("radius");
			}
			if (jsonData.has("style")){
				touchpadVO.style = jsonData.getString("style");
			}
			Touchpad touchpad = new Touchpad(touchpadVO, XY.skin);
			Serializer.ActorSerializer.addActor(touchpad);
			return touchpad;
		}
	}


	private static class ShaderSerializer implements Json.Serializer<SimpleShader.ShaderData>{

		@Override
		public void write(Json json, SimpleShader.ShaderData shader, Class knownType){

			Log.debug("ShaderSerializer", "Write: " + shader.name);
			json.writeObjectStart();

			json.writeValue("class", shader.getClass().getName());
			json.writeValue("name", shader.name);
			json.writeValue("type", shader.type);
			json.writeValue("id", shader.id);
			json.writeValue("date", shader.date);
			json.writeValue("author", shader.author);
			json.writeValue("description", shader.description);
			json.writeValue("tags", shader.tags);

			json.writeValue("color", shader.color);
			json.writeValue("normal", shader.normal);
			json.writeValue("textures", shader.textures);

			json.writeValue("vertex", shader.vertex);
			json.writeValue("fragment", shader.fragment);

			json.writeObjectEnd();
		}

		@Override
		public SimpleShader.ShaderData read(Json json, JsonValue jsonData, Class type){
			Log.debug("ShaderSerializer", "Read: " + jsonData.getString("name"));
			SimpleShader.ShaderData shader = new SimpleShader.ShaderData();

			if (jsonData.has("name")){
				shader.name = jsonData.getString("name");
			}

			if (jsonData.has("type")){
				shader.type = jsonData.getString("type");
			}
			if (jsonData.has("id")){
				shader.id = jsonData.getString("id");
			}
			if (jsonData.has("date")){
				shader.date = jsonData.getString("date");
			}
			if (jsonData.has("author")){
				shader.author = jsonData.getString("author");
			}
			if (jsonData.has("description")){
				shader.description = jsonData.getString("description");
			}
			if (jsonData.has("tags")){
				shader.tags = jsonData.getString("tags");
			}

			if (jsonData.has("color")){
				shader.color = Boolean.valueOf(jsonData.getString("color"));
			}
			if (jsonData.has("normal")){
				shader.normal = Boolean.valueOf(jsonData.getString("normal"));
			}
			if (jsonData.has("textures")){
				shader.textures = Integer.parseInt(jsonData.getString("textures"));
			}

			if (jsonData.has("vertex")){
				shader.vertex = jsonData.getString("vertex");
			}
			if (jsonData.has("fragment")){
				shader.fragment = jsonData.getString("fragment");
			}

			return shader;
		}
	}

	private Serializer(){
	}

	public static void create(){
		registerSerializer(SceneVO.class, new SceneVOSerializer());
		registerSerializer(Scene.class, new AppSerializer());
		registerSerializer(SceneImpl.class, new AppSerializer());
		registerSerializer(Actor.class, new ActorSerializer());
		registerSerializer(Label.class, new LabelSerializer());
		registerSerializer(Button.class, new ButtonSerializer());
		registerSerializer(Image.class, new ImageSerializer());
		registerSerializer(ImageButton.class, new ImageButtonSerializer());
		registerSerializer(TextButton.class, new TextButtonSerializer());
		registerSerializer(Slider.class, new SliderSerializer());
		registerSerializer(TextField.class, new TextFieldSerializer());
		registerSerializer(CheckBox.class, new CheckBoxSerializer());
		registerSerializer(ImageTextButton.class, new ImageTextButtonSerializer());
		registerSerializer(ProgressBar.class, new ProgressBarSerializer());
		registerSerializer(FxEffect.class, new FxEffectSerializer());
		registerSerializer(Sprite.class, new SpriteSerializer());
		registerSerializer(GameArea.class, new GameAreaSerializer());
		registerSerializer(Table.class, new TableSerializer());
		registerSerializer(SelectBox.class, new SelectBoxSerializer());
		registerSerializer(TextArea.class, new TextAreaSerializer());
		registerSerializer(Touchpad.class, new TouchpadSerializer());
		registerSerializer(SimpleShader.ShaderData.class, new ShaderSerializer());
	}

	private static void registerSerializer(Class<?> clazz, Json.Serializer serializer){
		Log.debug(TAG, "Register serializer: Class: " + clazz + " serializer: " + serializer);
		XY.json.setSerializer(clazz, serializer);
	}

	public static void pause(){

	}

	public static void resume(){

	}

	public static void dispose(){

	}
}
