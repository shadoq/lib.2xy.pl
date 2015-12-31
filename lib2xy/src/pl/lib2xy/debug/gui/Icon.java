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

package pl.lib2xy.debug.gui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;
import pl.lib2xy.XY;
import pl.lib2xy.app.ResourceManager;
import pl.lib2xy.debug.Debug;
import pl.lib2xy.gui.ui.*;
import pl.lib2xy.items.*;

public class Icon extends Group{

	protected float iconWidth = 44;
	protected float iconHeight = 44;

	public Icon(){
	}

	protected void setupIcon(){
		setWidth(iconWidth);
		setHeight(iconHeight);

		final Image pixel1 = ResourceManager.getIconFromClass("white_pixel");
		final Image pixel2 = ResourceManager.getIconFromClass("white_pixel");
		final Image pixel3 = ResourceManager.getIconFromClass("white_pixel");
		final Image pixel4 = ResourceManager.getIconFromClass("white_pixel");

		pixel1.setX(0);
		pixel1.setY(0);
		pixel1.setWidth(iconWidth);
		pixel1.setHeight(1);

		pixel2.setX(0);
		pixel2.setY(0);
		pixel2.setWidth(1);
		pixel2.setHeight(iconHeight);

		pixel3.setX(iconWidth);
		pixel3.setY(0);
		pixel3.setWidth(1);
		pixel3.setHeight(iconHeight);

		pixel4.setX(0);
		pixel4.setY(iconHeight);
		pixel4.setWidth(iconWidth);
		pixel4.setHeight(1);

		addActor(pixel1);
		addActor(pixel2);
		addActor(pixel3);
		addActor(pixel4);
	}

	protected void setupDragAndDrop(final Actor actor){

		DragAndDrop dragAndDrop = new DragAndDrop();
		dragAndDrop.addSource(new DragAndDrop.Source(actor){
			@Override
			public DragAndDrop.Payload dragStart(InputEvent event, float x, float y, int pointer){

				if (!XY.cfg.editor){
					return null;
				}
				if (XY.scene == null){
					return null;
				}

				if (actor == null){
					return null;
				}

				XY.mouseBusy = true;

				DragAndDrop.Payload payload = new DragAndDrop.Payload();

				if (actor instanceof IconTextureRegion){
					Image image = new Image(ResourceManager.getTextureRegion(((IconTextureRegion) actor).getRegionName()));
					payload.setDragActor(image);
					payload.setValidDragActor(image);
					payload.setObject(actor);

				} else if (actor instanceof IconActor){

					final String textLabel = ((IconActor) actor).getTextLabel();
					Actor actorToInsert = ((IconActor) actor).getActorToInsert();

					//
					// Copy insert actor dataVO
					//
					if (actorToInsert instanceof pl.lib2xy.gui.ui.Image){
						ImageVO imageVO = new ImageVO(((pl.lib2xy.gui.ui.Image) actorToInsert).data);
						actorToInsert = new pl.lib2xy.gui.ui.Image(imageVO);
					} else if (actorToInsert instanceof pl.lib2xy.gui.ui.Sprite){
						SpriteVO spriteVO = new SpriteVO(((Sprite) actorToInsert).data);
						actorToInsert = new pl.lib2xy.gui.ui.Sprite(spriteVO);
					} else if (actorToInsert instanceof pl.lib2xy.gui.ui.Label){
						LabelVO spriteVO = new LabelVO(((pl.lib2xy.gui.ui.Label) actorToInsert).data);
						actorToInsert = new pl.lib2xy.gui.ui.Label(spriteVO, XY.skin);
					} else if (actorToInsert instanceof pl.lib2xy.gui.ui.TextButton){
						TextButtonVO spriteVO = new TextButtonVO(((TextButton) actorToInsert).data);
						actorToInsert = new pl.lib2xy.gui.ui.TextButton(spriteVO, XY.skin);
					} else if (actorToInsert instanceof pl.lib2xy.gui.ui.FxEffect){
						FxEffectVO spriteVO = new FxEffectVO(((FxEffect) actorToInsert).data);
						actorToInsert = new pl.lib2xy.gui.ui.FxEffect(spriteVO);
					} else if (actorToInsert instanceof pl.lib2xy.gui.ui.CheckBox){
						CheckBoxVO spriteVO = new CheckBoxVO(((CheckBox) actorToInsert).data);
						actorToInsert = new pl.lib2xy.gui.ui.CheckBox(spriteVO, XY.skin);
					} else if (actorToInsert instanceof pl.lib2xy.gui.ui.Slider){
						SliderVO spriteVO = new SliderVO(((Slider) actorToInsert).data);
						actorToInsert = new pl.lib2xy.gui.ui.Slider(spriteVO, XY.skin);
					} else if (actorToInsert instanceof pl.lib2xy.gui.ui.TextField){
						TextFieldVO spriteVO = new TextFieldVO(((TextField) actorToInsert).data);
						actorToInsert = new pl.lib2xy.gui.ui.TextField(spriteVO, XY.skin);
					} else if (actorToInsert instanceof pl.lib2xy.gui.ui.TextArea){
						TextAreaVO spriteVO = new TextAreaVO(((TextArea) actorToInsert).data);
						actorToInsert = new pl.lib2xy.gui.ui.TextArea(spriteVO, XY.skin);
					} else if (actorToInsert instanceof pl.lib2xy.gui.ui.SelectBox){
						SelectBoxVO spriteVO = new SelectBoxVO(((SelectBox) actorToInsert).data);
						actorToInsert = new pl.lib2xy.gui.ui.SelectBox(spriteVO, XY.skin);
					} else if (actorToInsert instanceof pl.lib2xy.gui.ui.Table){
						TableVO spriteVO = new TableVO(((Table) actorToInsert).data);
						actorToInsert = new pl.lib2xy.gui.ui.Table(spriteVO, XY.skin);
					} else if (actorToInsert instanceof pl.lib2xy.gui.ui.GameArea){
						GameAreaVO spriteVO = new GameAreaVO(((GameArea) actorToInsert).data);
						actorToInsert = new pl.lib2xy.gui.ui.GameArea(spriteVO);
					} else if (actorToInsert instanceof pl.lib2xy.gui.ui.Button){
						ButtonVO spriteVO = new ButtonVO(((Button) actorToInsert).data);
						actorToInsert = new pl.lib2xy.gui.ui.Button(spriteVO, XY.skin);
					} else if (actorToInsert instanceof pl.lib2xy.gui.ui.ImageButton){
						ImageButtonVO spriteVO = new ImageButtonVO(((ImageButton) actorToInsert).data);
						actorToInsert = new pl.lib2xy.gui.ui.ImageButton(spriteVO, XY.skin);
					} else if (actorToInsert instanceof pl.lib2xy.gui.ui.ImageTextButton){
						ImageTextButtonVO spriteVO = new ImageTextButtonVO(((ImageTextButton) actorToInsert).data);
						actorToInsert = new pl.lib2xy.gui.ui.ImageTextButton(spriteVO, XY.skin);
					} else if (actorToInsert instanceof pl.lib2xy.gui.ui.Touchpad){
						TouchpadVO spriteVO = new TouchpadVO(((Touchpad) actorToInsert).data);
						actorToInsert = new pl.lib2xy.gui.ui.Touchpad(spriteVO, XY.skin);
					}

					final Label label = new Label(textLabel, Debug.debugSkin);
					payload.setDragActor(actorToInsert);
					payload.setValidDragActor(label);
					payload.setObject(actorToInsert);

				} else if (actor != null){
					final Label label = new Label(actor.getClass().getSimpleName(), Debug.debugSkin);
					payload.setDragActor(label);
					payload.setValidDragActor(label);
					payload.setObject(actor);
				}
				return payload;
			}
		});

		dragAndDrop.addTarget(new DragAndDrop.Target(XY.debug.getGui()){

			@Override
			public boolean drag(DragAndDrop.Source source, DragAndDrop.Payload payload, float x, float y, int pointer){

				if (!XY.cfg.editor){
					payload.getDragActor().getColor().a = 0.4f;
					return false;
				}

				payload.getDragActor().setColor(Color.WHITE);
				return true;
			}

			@Override
			public void drop(DragAndDrop.Source source, DragAndDrop.Payload payload, float x, float y, int pointer){
				XY.mouseBusy = false;
				final Actor dragActor = (Actor) payload.getObject();
				Debug.sceneEditor.addActorToScene(dragActor, XY.mouseUnproject.x, XY.mouseUnproject.y - dragActor.getHeight());
			}
		});

	}
}
