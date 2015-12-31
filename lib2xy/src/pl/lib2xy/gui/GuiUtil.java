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

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import pl.lib2xy.XY;
import pl.lib2xy.app.Log;
import pl.lib2xy.items.ActorVO;

/**
 * Short method tool to operation of Gui Object
 */
public class GuiUtil{

	private static final String TAG = GuiUtil.class.getSimpleName();

	public enum Position{
		CENTER,
		LEFT, TOP, RIGHT, BOTTOM,
		TOP_LEFT, TOP_RIGHT,
		BOTTOM_LEFT, BOTTOM_RIGHT
	}

	private GuiUtil(){

	}

	/**
	 * @param actor
	 * @param sizeX
	 * @param sizeY
	 * @return
	 */
	public static Actor normalizeSize(Actor actor, float sizeX, float sizeY){
		if (sizeX < 0.0){
			sizeX = 0;
		} else if (sizeX > 1){
			sizeX = 1;
		}
		if (sizeY < 0.0){
			sizeY = 0;
		} else if (sizeY > 1){
			sizeY = 1;
		}
		actor.setWidth(sizeX * XY.width);
		actor.setHeight(sizeY * XY.height);
		return actor;
	}

	/**
	 * @param sizeY
	 * @return
	 */
	public static int normalizeY(float sizeY){
		if (sizeY < 0.0){
			sizeY = 0;
		} else if (sizeY > 1){
			sizeY = 1;
		}
		return (int) (sizeY * XY.height);
	}

	/**
	 * @param sizeX
	 * @return
	 */
	public static int normalizeX(float sizeX){
		if (sizeX < 0.0){
			sizeX = 0;
		} else if (sizeX > 1){
			sizeX = 1;
		}
		return (int) (sizeX * XY.width);
	}

	/**
	 * Wyrównuje pozycję okna do grida 0 - wyrówanie do lewej 5 - centrowanie 10
	 * - wyrownanie do prawej
	 *
	 * @param window
	 * @param gridX
	 * @param gridY
	 * @return
	 */
	public static Table windowPosition(Table window, int gridX, int gridY){
		if (gridX > 24){
			gridX = 24;
		} else if (gridX < 0){
			gridX = 0;
		}
		if (gridY > 24){
			gridY = 24;
		} else if (gridX < 0){
			gridY = 0;
		}
		int posX = (XY.width / 24) * gridX;
		int posY = (XY.height / 24) * gridY;

		window.pack();

		int posXCalc = posX;
		int posYCalc = posY;
		if (gridX == 12){
			posXCalc = (int) (XY.centerX - (window.getWidth() / 2));
		} else if (gridX == 12){
			posXCalc = (int) (XY.width - window.getWidth());
		}
		if (gridY == 12){
			posYCalc = (int) (XY.centerY - (window.getHeight() / 2));
		} else if (gridY == 12){
			posYCalc = (int) (XY.height - window.getHeight());
		}
		Log.debug(TAG, "Set grid: " + gridX + ":" + gridY + " - " + posX + ":" + posY + " - " + posXCalc + ":" + posYCalc);
		window.setX(posXCalc);
		window.setY(posYCalc);
		return window;
	}

	/**
	 * @param window
	 * @param position
	 * @return
	 */
	public static Table windowPosition(Table window, Position position){
		switch (position){
			case CENTER:
				return windowPosition(window, 5, 5);
			case LEFT:
				return windowPosition(window, 0, 5);
			case TOP:
				return windowPosition(window, 5, 10);
			case RIGHT:
				return windowPosition(window, 10, 5);
			case BOTTOM:
				return windowPosition(window, 5, 0);
			case TOP_LEFT:
				return windowPosition(window, 0, 10);
			case TOP_RIGHT:
				return windowPosition(window, 10, 10);
			case BOTTOM_LEFT:
				return windowPosition(window, 0, 0);
			case BOTTOM_RIGHT:
				return windowPosition(window, 10, 0);
		}
		return null;
	}

	/**
	 * @param source
	 * @param destination
	 */
	public static void copyActor(Actor source, Actor destination){
		if (source == null || destination == null){
			return;
		}
		destination.setBounds(source.getX(), source.getY(), source.getWidth(), source.getHeight());
		destination.setColor(source.getColor());
		destination.setName(source.getName());
		destination.setOrigin(source.getOriginX(), source.getOriginY());
		destination.setRotation(source.getRotation());
		destination.setScale(source.getScaleX(), source.getScaleY());
		destination.setTouchable(source.getTouchable());
		destination.setUserObject(source.getUserObject());
		destination.setVisible(source.isVisible());
		if (source.getZIndex() > 0){
			destination.setZIndex(source.getZIndex());
		}
	}

	/**
	 * @param actor
	 * @return
	 */
	public static Rectangle rectangleFromActor(Actor actor){
		return new Rectangle(actor.getX(), actor.getY(), actor.getWidth(), actor.getHeight());
	}

	public static void setActorVO(ActorVO actorVO, Actor actor){
		actor.setName(actorVO.name);
		actor.setX(actorVO.x);
		actor.setY(actorVO.y);
		actor.setZIndex(actorVO.zIndex);
		actor.setWidth(actorVO.width);
		actor.setHeight(actorVO.height);
		actor.setOriginX(actorVO.originX);
		actor.setOriginY(actorVO.originY);
		actor.setScaleX(actorVO.scaleX);
		actor.setScaleY(actorVO.scaleY);
		actor.setRotation(actorVO.rotation);
		actor.setDebug(actorVO.debug);
		actor.setColor(actorVO.color);
		actor.setVisible(actorVO.visible);
		actor.setTouchable(Touchable.valueOf(actorVO.touchable));
	}

	public static void updateActorVO(ActorVO actorVO, Actor actor){
		actorVO.name = actor.getName();
		actorVO.x = actor.getX();
		actorVO.y = actor.getY();
		actorVO.zIndex = actor.getZIndex();
		actorVO.width = actor.getWidth();
		actorVO.height = actor.getHeight();
		actorVO.originX = actor.getOriginX();
		actorVO.originY = actor.getOriginY();
		actorVO.scaleX = actor.getScaleX();
		actorVO.scaleY = actor.getScaleY();
		actorVO.rotation = actor.getRotation();
		actorVO.debug = actor.getDebug();
		actorVO.color = actor.getColor();
		actorVO.visible = actor.isVisible();
		actorVO.touchable = actor.getTouchable().name();
	}

	public static Pixmap createPixmapFromString(String textData, int multipler){

		if (textData == null){
			return null;
		}
		final String[] split = textData.split("\n");

		final String[] sizes = split[0].split("x");
		if (sizes == null || sizes.length < 2 || sizes[0].equals("") || sizes[1].equals("")){
			return null;
		}
		int width = Integer.valueOf(sizes[0]);
		int height = Integer.valueOf(sizes[1]);

		Pixmap pixmap = new Pixmap(width, height, Pixmap.Format.RGBA8888);

		for (int y = 0; y < height; y++){
			String[] widthData = split[y + 1].split("\\|");
			if (widthData == null){
				return null;
			}
			for (int x = 0; x < width; x++){
				Integer pixValue = Integer.valueOf(widthData[x]) * multipler;
				pixmap.drawPixel(x, y, pixValue << 24 | pixValue << 16 | pixValue << 8 | 255);

			}
		}

		return pixmap;
	}
}
