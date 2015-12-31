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

package pl.lib2xy.debug.editors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.SnapshotArray;
import pl.lib2xy.XY;
import pl.lib2xy.app.Gfx;
import pl.lib2xy.app.Log;
import pl.lib2xy.app.Scene;
import pl.lib2xy.debug.Debug;
import pl.lib2xy.debug.gui.IconTextureRegion;
import pl.lib2xy.debug.interfaces.DebugEditorInterface;
import pl.lib2xy.gui.ui.Image;
import pl.lib2xy.gui.ui.Sprite;
import pl.lib2xy.interfaces.DataVO;
import pl.lib2xy.math.Bounding2D;

public class SceneEditor extends Scene implements DebugEditorInterface{

	private static final int POINT_SIZE = 10;
	private static final String TAG = "SceneEditor";
	private static String lineSeparator = "\n";

	public static boolean snapToGrid = false;

	protected boolean dragging;
	protected boolean resize;
	protected boolean cameraMove;

	protected int edge;
	protected float startX, startY, lastX, lastY, actorClickX, actorClickY, lastDeltaWidth, lastDeltaHeight;

	protected boolean isHand = false;
	protected boolean isRight = false;
	protected boolean isTop = false;
	protected boolean isLeft = false;
	protected boolean isBotton = false;
	protected boolean isOutSelectBox = false;

	protected boolean shiftKeyDown = false;
	protected boolean altKeyDown = false;
	protected boolean controlKeyDown = false;

	protected boolean isClickDown = false;
	protected static Array<Bounding2D> selectActors = new Array<>();
	protected static Array<String> selectNames = new Array<>();

	public static int countSelectActor = 0;
	public static Actor currentActor;

	protected Bounding2D bounding2D = new Bounding2D();
	protected Bounding2D selectBounding2D = new Bounding2D();
	protected Bounding2D copySelectBounding2D = new Bounding2D();
	protected Bounding2D lastSelectBounding2D = new Bounding2D();

	protected Bounding2D newBounding2D = new Bounding2D();

	protected String copyJson;
	protected int copyCount = 0;


	/**
	 *
	 */
	public void updateGui(){
		clearSelectActor();
	}

	/**
	 * @return
	 */
	public int getSelectedCount(){
		return countSelectActor;
	}

	/**
	 * @param delta
	 */
	@Override
	public void postRender(float delta){

		if (XY.cfg.editor){
			if (isClickDown){
				Gfx.setColor(Color.RED);
				Gfx.drawSquare(bounding2D.start.x, bounding2D.start.y, bounding2D.size.x, bounding2D.size.y);
			}
			if (countSelectActor > 0){
				for (Bounding2D selectActor : selectActors){
					Gfx.setColor(Color.CYAN);
					Gfx.drawSquare(selectActor.actor.getX(), selectActor.actor.getY(), selectActor.actor.getWidth(), selectActor.actor.getHeight());
				}
				if (selectBounding2D.size.x > 0 && selectBounding2D.size.y > 0){
					Gfx.setColor(Color.YELLOW);
					Gfx.drawSquare(selectBounding2D.start.x, selectBounding2D.start.y, selectBounding2D.size.x, selectBounding2D.size.y);
					Gfx.drawFilledCircle(selectBounding2D.start.x + 3, selectBounding2D.start.y + 3, 6, Color.YELLOW);
					Gfx.drawFilledCircle(selectBounding2D.end.x - 3, selectBounding2D.end.y - 3, 6, Color.YELLOW);

					Gfx.drawFilledCircle(selectBounding2D.start.x + selectBounding2D.size.x / 2, selectBounding2D.start.y + 3, 6, Color.RED);
					Gfx.drawFilledCircle(selectBounding2D.start.x + selectBounding2D.size.x / 2, selectBounding2D.end.y - 3, 6, Color.RED);

					Gfx.drawFilledCircle(selectBounding2D.start.x + 3, selectBounding2D.start.y + selectBounding2D.size.y / 2, 6, Color.RED);
					Gfx.drawFilledCircle(selectBounding2D.end.x - 3, selectBounding2D.start.y + selectBounding2D.size.y / 2, 6, Color.RED);
				}
			}
		} else {
			if (currentActor != null){
				Gfx.setColor(Color.GREEN);
				Gfx.drawSquare(currentActor.getX(), currentActor.getY(), currentActor.getWidth(),
							   currentActor.getHeight());
				XY.debug.getItemPropertiesPanel().update();
			}
		}

	}

	/**
	 * @param x
	 * @param y
	 * @param button
	 */
	@Override
	public void onTouchDown(int x, int y, int button){

		if (XY.scene == null){
			return;
		}

		if (
		(XY.mouseScreen.x < Debug.leftPanelSize) || (XY.mouseScreen.x > (XY.screenWidth - Debug.rightPanelSize)) ||
		(XY.mouseScreen.y < Debug.topPanelSize) || (XY.mouseScreen.y > (XY.screenHeight - Debug.bottonPanelSize))
		){
			return;
		}

		if (button == 0){

			if (countSelectActor == 0){
				bounding2D.start.x = x;
				bounding2D.start.y = y;
				bounding2D.size.x = 0;
				bounding2D.size.y = 0;
				bounding2D.end.x = x;
				bounding2D.end.y = y;

				isClickDown = true;
			} else if (countSelectActor > 0 && isOutSelectBox){

				bounding2D.start.x = x;
				bounding2D.start.y = y;
				bounding2D.size.x = 0;
				bounding2D.size.y = 0;
				bounding2D.end.x = x;
				bounding2D.end.y = y;

				selectBounding2D.start.x = x;
				selectBounding2D.start.y = y;
				selectBounding2D.size.x = 0;
				selectBounding2D.size.y = 0;
				selectBounding2D.end.x = x;
				selectBounding2D.end.y = y;

				clearSelectActor();

				isClickDown = true;

			} else if (countSelectActor > 0){
				resize = false;
				dragging = false;
				edge = 0;

				if (x > selectBounding2D.end.x - POINT_SIZE){
					edge |= Align.right;
					resize = true;
				}
				if (x < selectBounding2D.start.x + POINT_SIZE){
					edge |= Align.left;
					resize = true;
				}
				if (y > selectBounding2D.end.y - POINT_SIZE){
					edge |= Align.top;
					resize = true;
				}
				if (y < selectBounding2D.start.y + POINT_SIZE){
					edge |= Align.bottom;
					resize = true;
				}

				if (!resize){
					dragging = true;
				}

				actorClickX = x - selectBounding2D.start.x;
				actorClickY = y - selectBounding2D.start.y;
			}

			startX = x;
			startY = y;
			lastX = x;
			lastY = y;

		} else if (button == 1){

			bounding2D.start.x = x;
			bounding2D.start.y = y;
			bounding2D.size.x = 0;
			bounding2D.size.y = 0;
			bounding2D.end.x = x;
			bounding2D.end.y = y;

			selectBounding2D.start.x = x;
			selectBounding2D.start.y = y;
			selectBounding2D.size.x = 0;
			selectBounding2D.size.y = 0;
			selectBounding2D.end.x = x;
			selectBounding2D.end.y = y;

			clearSelectActor();
			isClickDown = false;
			isHand = false;

		} else if (button == 2){

			cameraMove = true;
			startX = XY.mouseScreen.x;
			startY = XY.mouseScreen.y;
			lastX = XY.mouseScreen.x;
			lastY = XY.mouseScreen.y;
		}

		if (countSelectActor == 1){
			if (currentActor != null){
				XY.debug.getItemPropertiesPanel().setActor(currentActor);
			} else {
				XY.debug.getItemPropertiesPanel().setActor(null);
			}
		} else if (countSelectActor > 1){
			XY.debug.getItemPropertiesPanel().setActor(null);
		} else {
			XY.debug.getItemPropertiesPanel().setActor(null);
		}

		if (XY.debug != null){
			XY.debug.setBoundXY(selectBounding2D, selectActors, currentActor, countSelectActor);
		}

	}

	/**
	 * @param x
	 * @param y
	 * @param button
	 */
	@Override
	public void onTouchUp(int x, int y, int button){

		if (XY.scene == null){
			return;
		}
		//
		// Select New Object
		//
		if (isClickDown && currentActor == null){
			isClickDown = false;
			isHand = false;

			selectActors.clear();
			selectNames.clear();
			Actor lastChild = null;

			float startX = selectBounding2D.start.x + selectBounding2D.size.x * 0.5f;
			float startY = selectBounding2D.start.y + selectBounding2D.size.y * 0.5f;
			float endX = selectBounding2D.start.x + selectBounding2D.size.x * 0.5f;
			float endY = selectBounding2D.start.y + selectBounding2D.size.y * 0.5f;

			//
			// Click on one actor
			//
			if (bounding2D.size.x > -POINT_SIZE && bounding2D.size.y > -POINT_SIZE && bounding2D.size.x < POINT_SIZE && bounding2D.size.y < POINT_SIZE){
				selectActors.clear();

				Actor actor = XY.scene.hit(XY.mouseUnproject.x, XY.mouseUnproject.y);

				if (actor != null){

					Bounding2D selectActor = new Bounding2D();
					selectActor.actor = actor;
					selectActor.start.x = actor.getX();
					selectActor.start.y = actor.getY();
					selectActor.size.x = actor.getWidth();
					selectActor.size.y = actor.getHeight();
					selectActor.end.x = selectActor.start.x + selectActor.size.x;
					selectActor.end.y = selectActor.start.y + selectActor.size.y;

					selectActors.add(selectActor);
					selectNames.add(selectActor.actor.getName());

					startX = actor.getX();
					startY = actor.getY();
					endX = actor.getX() + actor.getWidth();
					endY = actor.getY() + actor.getHeight();

					currentActor = actor;
				} else {
					currentActor = null;
				}
				lastChild = currentActor;

			} else {

				//
				// Create Select BoundingBox
				//
				if (bounding2D.size.x > 0 && bounding2D.size.y < 0){
					selectBounding2D.start.x = bounding2D.start.x;
					selectBounding2D.start.y = bounding2D.start.y + bounding2D.size.y;
					selectBounding2D.size.x = bounding2D.size.x;
					selectBounding2D.size.y = -bounding2D.size.y;
				} else if (bounding2D.size.x < 0 && bounding2D.size.y > 0){
					selectBounding2D.start.x = bounding2D.start.x + bounding2D.size.x;
					selectBounding2D.start.y = bounding2D.start.y;
					selectBounding2D.size.x = -bounding2D.size.x;
					selectBounding2D.size.y = bounding2D.size.y;
				} else if (bounding2D.size.x < 0 && bounding2D.size.y < 0){
					selectBounding2D.start.x = bounding2D.start.x + bounding2D.size.x;
					selectBounding2D.start.y = bounding2D.start.y + bounding2D.size.y;
					selectBounding2D.size.x = -bounding2D.size.x;
					selectBounding2D.size.y = -bounding2D.size.y;
				} else {
					selectBounding2D.start.x = bounding2D.start.x;
					selectBounding2D.start.y = bounding2D.start.y;
					selectBounding2D.size.x = bounding2D.size.x;
					selectBounding2D.size.y = bounding2D.size.y;
				}

				selectBounding2D.end.x = selectBounding2D.start.x + selectBounding2D.size.x;
				selectBounding2D.end.y = selectBounding2D.start.y + selectBounding2D.size.y;

				startX = selectBounding2D.start.x + selectBounding2D.size.x;
				startY = selectBounding2D.start.y + selectBounding2D.size.y;
				endX = selectBounding2D.start.x;
				endY = selectBounding2D.start.y;

				//
				// Select Actor and count new BoundinBox
				//
				final SnapshotArray<Actor> children = XY.scene.getChildren();

				for (Actor child : children){
					float centerX = child.getX() + (child.getWidth() * 0.5f);
					float centerY = child.getY() + (child.getHeight() * 0.5f);

					if (centerX > selectBounding2D.start.x && centerX < selectBounding2D.start.x + selectBounding2D.size.x &&
					centerY > selectBounding2D.start.y && centerY < selectBounding2D.start.y + selectBounding2D.size.y){

						Bounding2D selectActor = new Bounding2D();
						selectActor.actor = child;
						selectActor.start.x = child.getX();
						selectActor.start.y = child.getY();
						selectActor.size.x = child.getWidth();
						selectActor.size.y = child.getHeight();
						selectActor.end.x = selectActor.start.x + selectActor.size.x;
						selectActor.end.y = selectActor.start.y + selectActor.size.y;

						selectActors.add(selectActor);
						selectNames.add(selectActor.actor.getName());

						if (child.getX() < startX){
							startX = child.getX();
						}
						if (child.getY() < startY){
							startY = child.getY();
						}
						if (child.getX() + child.getWidth() > endX){
							endX = child.getX() + child.getWidth();
						}
						if (child.getY() + child.getHeight() > endY){
							endY = child.getY() + child.getHeight();
						}

						lastChild = child;
					}
				}
			}

			countSelectActor = selectActors.size;
			if (countSelectActor > 0){
				selectBounding2D.start.x = startX;
				selectBounding2D.start.y = startY;
				selectBounding2D.size.x = endX - startX;
				selectBounding2D.size.y = endY - startY;
				selectBounding2D.end.x = endX;
				selectBounding2D.end.y = endY;

				copySelectBounding2D.set(selectBounding2D);

				for (Bounding2D selectedActor : selectActors){
					selectedActor.deltaPosition.x = selectedActor.start.x - selectBounding2D.start.x;
					selectedActor.deltaPosition.y = selectedActor.start.y - selectBounding2D.start.y;
				}

				if (countSelectActor == 1){
					currentActor = lastChild;
				}
			}

			if (countSelectActor == 1){
				if (currentActor != null){
					XY.debug.getItemPropertiesPanel().setActor(currentActor);
					XY.debug.itemListPanel.setSelect(currentActor.getName());
				} else {
					countSelectActor = 0;
					XY.debug.getItemPropertiesPanel().setActor(null);
				}
			} else if (countSelectActor > 1){
				XY.debug.getItemPropertiesPanel().setActor(null);
			} else {
				XY.debug.getItemPropertiesPanel().setActor(null);
			}

			calculateBoundingBox();

			if (XY.debug != null){
				XY.debug.setBoundXY(selectBounding2D, selectActors, currentActor, countSelectActor);
			}

		}

		dragging = false;
		resize = false;
		cameraMove = false;
	}

	private void calculateBoundingBox(){

		if (selectActors.size > 0){
			selectBounding2D.start.x = selectActors.first().start.x;
			selectBounding2D.start.y = selectActors.first().start.y;
			selectBounding2D.end.x = selectActors.first().end.x;
			selectBounding2D.end.y = selectActors.first().end.y;
			for (Bounding2D selectedActor : selectActors){

				selectedActor.update();

				if (selectedActor.start.x < selectBounding2D.start.x){
					selectBounding2D.start.x = selectedActor.start.x;
				}
				if (selectedActor.start.y < selectBounding2D.start.y){
					selectBounding2D.start.y = selectedActor.start.y;
				}
				if (selectedActor.end.x > selectBounding2D.end.x){
					selectBounding2D.end.x = selectedActor.end.x;
				}
				if (selectedActor.end.y > selectBounding2D.end.y){
					selectBounding2D.end.y = selectedActor.end.y;
				}
			}
			selectBounding2D.size.x = selectBounding2D.end.x - selectBounding2D.start.x;
			selectBounding2D.size.y = selectBounding2D.end.y - selectBounding2D.start.y;
		}

	}

	/**
	 * @param x
	 * @param y
	 */
	@Override
	public void onDrag(int x, int y){

		if (XY.scene == null){
			return;
		}

		if (XY.mouseBusy){
			return;
		}

		if (countSelectActor == 0 || isOutSelectBox){
			bounding2D.size.x += x - lastX;
			bounding2D.size.y += y - lastY;
		} else if (dragging && !resize){

			if (snapToGrid){
				float gridXStep = (float) (XY.width / 48.0);
				float gridYStep = (float) (XY.height / 48.0);

				x = (int) ((int) ((x / gridXStep)) * gridXStep);
				y = (int) ((int) ((y / gridYStep)) * gridYStep);
			}

			if (controlKeyDown){
				x = (int) startX;
			} else if (shiftKeyDown){
				y = (int) startY;
			}

			float deltaX = x - lastX;
			float deltaY = y - lastY;

			for (Bounding2D selectActor : selectActors){
				if (deltaX != 0 || deltaY != 0){
					selectActor.actor.moveBy(deltaX, deltaY);
					selectActor.actor.setX(Math.round(selectActor.actor.getX()));
					selectActor.actor.setY(Math.round(selectActor.actor.getY()));
					selectActor.actor.setWidth(Math.round(selectActor.actor.getWidth()));
					selectActor.actor.setHeight(Math.round(selectActor.actor.getHeight()));
					if (selectActor.actor instanceof DataVO){
						((DataVO) selectActor.actor).updateVO();
					}
				}
			}

			selectBounding2D.setPosition(x - actorClickX, y - actorClickY);

		} else if (resize){

			if (snapToGrid){
				float gridXStep = (float) (XY.width / 24.0);
				float gridYStep = (float) (XY.height / 24.0);

				x = (int) ((int) ((x / gridXStep)) * gridXStep);
				y = (int) ((int) ((y / gridYStep)) * gridYStep);
			}

			float width = selectBounding2D.size.x;
			float height = selectBounding2D.size.y;
			float startX = selectBounding2D.start.x;
			float startY = selectBounding2D.start.y;

			if ((edge & Align.top) != 0 && (edge & Align.right) != 0){
				width += x - lastX;
				height += y - lastY;
			} else if ((edge & Align.left) != 0 && (edge & Align.bottom) != 0){
				width -= x - lastX;
				height -= y - lastY;
				startX += x - lastX;
				startY += y - lastY;
			} else if ((edge & Align.right) != 0){
				width += x - lastX;
			} else if ((edge & Align.top) != 0){
				height += y - lastY;
			} else if ((edge & Align.bottom) != 0){
				height -= y - lastY;
				if (height > POINT_SIZE * 2){
					startY += y - lastY;
				}
			} else if ((edge & Align.left) != 0){
				width -= x - lastX;
				if (width > POINT_SIZE * 2){
					startX += x - lastX;
				}
			}

			if (width < POINT_SIZE * 2){
				width = POINT_SIZE * 2;
			}
			if (height < POINT_SIZE * 2){
				height = POINT_SIZE * 2;
			}
			selectBounding2D.setBounds(Math.round(startX), Math.round(startY), Math.round(width), Math.round(height));

			float deltaWidth = selectBounding2D.size.x / copySelectBounding2D.size.x;
			float deltaHeight = selectBounding2D.size.y / copySelectBounding2D.size.y;

			for (Bounding2D selectedActor : selectActors){

				selectedActor.percentStart.x = (selectedActor.start.x - copySelectBounding2D.start.x) / copySelectBounding2D.size.x;
				selectedActor.percentStart.y = (selectedActor.start.y - copySelectBounding2D.start.y) / copySelectBounding2D.size.y;
				selectedActor.percentEnd.x = (selectedActor.end.x - copySelectBounding2D.start.x) / copySelectBounding2D.size.x;
				selectedActor.percentEnd.y = (selectedActor.end.y - copySelectBounding2D.start.y) / copySelectBounding2D.size.y;
				selectedActor.percentSize.x = selectedActor.size.x / copySelectBounding2D.size.x;
				selectedActor.percentSize.y = selectedActor.size.y / copySelectBounding2D.size.y;

				if ((edge & Align.top) != 0 && (edge & Align.right) != 0 || (edge & Align.left) != 0 && (edge & Align.bottom) != 0){
					selectedActor.actor.setHeight(Math.round(selectedActor.percentSize.y * deltaHeight * copySelectBounding2D.size.y));
					selectedActor.actor.setY(Math.round(selectedActor.percentStart.y * deltaHeight * copySelectBounding2D.size.y + selectBounding2D.start.y));
					selectedActor.actor.setWidth(Math.round(selectedActor.percentSize.x * deltaWidth * copySelectBounding2D.size.x));
					selectedActor.actor.setX(Math.round(selectedActor.percentStart.x * deltaWidth * copySelectBounding2D.size.x + selectBounding2D.start.x));
				} else if ((edge & Align.top) != 0 || (edge & Align.bottom) != 0){
					selectedActor.actor.setHeight(Math.round(selectedActor.percentSize.y * deltaHeight * copySelectBounding2D.size.y));
					selectedActor.actor.setY(Math.round(selectedActor.percentStart.y * deltaHeight * copySelectBounding2D.size.y + selectBounding2D.start.y));
				} else if ((edge & Align.left) != 0 || (edge & Align.right) != 0){
					selectedActor.actor.setWidth(Math.round(selectedActor.percentSize.x * deltaWidth * copySelectBounding2D.size.x));
					selectedActor.actor.setX(Math.round(selectedActor.percentStart.x * deltaWidth * copySelectBounding2D.size.x + selectBounding2D.start.x));
				}

				if (selectedActor.actor instanceof DataVO){
					((DataVO) selectedActor.actor).updateVO();
				}
			}

			lastSelectBounding2D.set(selectBounding2D);
		}

		if (XY.debug != null){
			XY.debug.setBoundXY(selectBounding2D, selectActors, currentActor, countSelectActor);
			if (countSelectActor == 1){
				XY.debug.itemPropertiesPanel.update();
			}
		}

		if (cameraMove){

			x = (int) XY.mouseScreen.x;
			y = (int) XY.mouseScreen.y;

			float moveX = x - lastX;
			float moveY = y - lastY;
			if (shiftKeyDown){
				XY.fixedCamera.translate(moveX * -2.0f, moveY * 2.0f);
			} else if (controlKeyDown){
				XY.fixedCamera.translate(moveX * -1.5f, moveY * 1.5f);
			} else {
				XY.fixedCamera.translate(moveX * -1.0f, moveY * 1.0f);
			}
			XY.fixedCamera.update();
		}

		lastX = x;
		lastY = y;
	}

	/**
	 * @param screenX
	 * @param screenY
	 */
	@Override
	public void onMouseMoved(int screenX, int screenY){

		Actor hit = null;
		if (XY.scene != null){
			hit = XY.scene.hit(screenX, screenY, false);
			if (XY.debug != null){
				XY.debug.setOver(hit);
			}
		}

		if (countSelectActor > 0){
			isRight = false;
			isTop = false;
			isLeft = false;
			isBotton = false;
			isOutSelectBox = false;
			if (
			(screenX > selectBounding2D.start.x && screenX < selectBounding2D.end.x &&
			screenY > selectBounding2D.start.y && screenY < selectBounding2D.end.y)
			){

				if (screenX > selectBounding2D.end.x - POINT_SIZE * 2 && screenX < selectBounding2D.end.x){
					isHand = false;
					isRight = true;
				}
				if (screenX > selectBounding2D.start.x && screenX < selectBounding2D.start.x + POINT_SIZE * 2){
					isHand = false;
					isLeft = true;
				}
				if (screenY > selectBounding2D.end.y - POINT_SIZE * 2 && screenY < selectBounding2D.end.y){
					isHand = false;
					isTop = true;
				}
				if (screenY > selectBounding2D.start.y && screenY < selectBounding2D.start.y + POINT_SIZE * 2){
					isHand = false;
					isBotton = true;
				}
				if (isRight && isTop){
					isHand = false;
				}
				if (isLeft && isBotton){
					isHand = false;
				}
			} else {
				isOutSelectBox = true;
			}
		}
	}

	/**
	 * @param keycode
	 */
	@Override
	public void onKeyUp(int keycode){

		//
		// Action on editor
		//
		if (XY.cfg.editor){
			if (keycode == Input.Keys.FORWARD_DEL && controlKeyDown && currentActor != null){
				Gdx.app.postRunnable(new Runnable(){
					@Override
					public void run(){
						deleteSelectedActors();
						if (XY.debug != null){
							XY.debug.updateGUI();
						}

					}
				});

			}
		}

		shiftKeyDown = false;
		controlKeyDown = false;
		altKeyDown = false;
	}

	/**
	 * @param keycode
	 */
	@Override
	public void onKeyDown(int keycode){

		if (keycode == Input.Keys.SHIFT_LEFT || keycode == Input.Keys.SHIFT_RIGHT){
			shiftKeyDown = true;
		}
		if (keycode == Input.Keys.CONTROL_LEFT || keycode == Input.Keys.CONTROL_RIGHT){
			controlKeyDown = true;
		}
		if (keycode == Input.Keys.ALT_LEFT || keycode == Input.Keys.ALT_RIGHT){
			altKeyDown = true;
		}
	}

	/**
	 *
	 */
	public void toFront(){
		if (currentActor != null && XY.cfg.editor){
			currentActor.toFront();
		}
	}

	/**
	 *
	 */
	public void toBack(){
		if (currentActor != null && XY.cfg.editor){
			currentActor.toBack();
		}
	}

	/**
	 *
	 */
	public void resetCamera(){
		XY.fixedCamera.position.set(XY.centerX, XY.centerY, XY.fixedCamera.position.z);
		XY.fixedCamera.update();
	}

	/**
	 *
	 */
	public void clearSelectActor(){
		if (XY.debug != null){
			XY.debug.getItemPropertiesPanel().setActor(null);
		}
		countSelectActor = 0;
		currentActor = null;
		selectActors.clear();
	}

	/**
	 * @param actorName
	 */
	public void setSelectActor(String actorName){

		selectActors.clear();
		float startX = selectBounding2D.start.x + selectBounding2D.size.x * 0.5f;
		float startY = selectBounding2D.start.y + selectBounding2D.size.y * 0.5f;
		float endX = selectBounding2D.start.x + selectBounding2D.size.x * 0.5f;
		float endY = selectBounding2D.start.y + selectBounding2D.size.y * 0.5f;

		Actor actor = XY.scene.findActor(actorName);

		if (actor != null){

			Bounding2D selectActor = new Bounding2D();
			selectActor.actor = actor;
			selectActor.start.x = actor.getX();
			selectActor.start.y = actor.getY();
			selectActor.size.x = actor.getWidth();
			selectActor.size.y = actor.getHeight();

			selectActor.end.x = selectActor.start.x + selectActor.size.x;
			selectActor.end.y = selectActor.start.y + selectActor.size.y;

			selectActors.add(selectActor);

			startX = actor.getX();
			startY = actor.getY();
			endX = actor.getX() + actor.getWidth();
			endY = actor.getY() + actor.getHeight();

			currentActor = actor;
		} else {
			currentActor = null;
		}

		countSelectActor = selectActors.size;
		if (countSelectActor > 0){
			selectBounding2D.start.x = startX;
			selectBounding2D.start.y = startY;
			selectBounding2D.size.x = endX - startX;
			selectBounding2D.size.y = endY - startY;
			selectBounding2D.end.x = endX;
			selectBounding2D.end.y = endY;

			copySelectBounding2D.set(selectBounding2D);

			for (Bounding2D bounding2D : selectActors){
				bounding2D.deltaPosition.x = bounding2D.start.x - selectBounding2D.start.x;
				bounding2D.deltaPosition.y = bounding2D.start.y - selectBounding2D.start.y;
			}

		}

		if (XY.debug != null){
			XY.debug.setBoundXY(selectBounding2D, selectActors, currentActor, countSelectActor);
		}

		if (countSelectActor == 1){
			XY.debug.getItemPropertiesPanel().setActor(currentActor);
		} else if (countSelectActor > 1){
			XY.debug.getItemPropertiesPanel().setActor(null);
		} else {
			XY.debug.getItemPropertiesPanel().setActor(null);
		}
	}

	/**
	 * @return
	 */
	public String getSelectActorAsJson(){

		StringBuilder sb = new StringBuilder();
		for (Bounding2D selectActor : SceneEditor.selectActors){
			Actor actor = selectActor.actor;

			if (actor == null){
				continue;
			}

			Log.debug(TAG, "Copy actor: " + actor.getName() + " class: " + actor.getClass().getName());
			sb.append(XY.json.toJson(actor));
			sb.append(lineSeparator);
		}

		return sb.toString();
	}

	/**
	 * @param copyJson
	 */
	public void addActorFromJson(String copyJson){

		if (copyJson == null){
			return;
		}
		String[] lines = copyJson.split(lineSeparator);
		if (lines == null){
			return;
		}

		for (String line : lines){
			if (line == null){
				continue;
			}
			if (line.trim().isEmpty()){
				continue;
			}
			XY.json.fromJson(null, line);
		}
	}

	/**
	 *
	 */
	public void deleteSelectedActors(){

		for (Bounding2D selectActor : selectActors){
			Actor actor = selectActor.actor;
			if (actor == null){
				continue;
			}
			XY.scene.removeActor(actor);
		}
		XY.scene.removeActor(currentActor);
		currentActor = null;
		clearSelectActor();
		if (XY.debug != null){
			XY.debug.updateGUI();
		}

	}

	/**
	 *
	 */
	public void actorAlignLeft(){
		for (Bounding2D bounding2D : selectActors){
			bounding2D.actor.setX(copySelectBounding2D.start.x);
		}
	}

	/**
	 *
	 */
	public void actorAlignRight(){
		for (Bounding2D bounding2D : selectActors){
			bounding2D.actor.setX(copySelectBounding2D.end.x - bounding2D.actor.getWidth());
		}
	}

	/**
	 *
	 */
	public void actorAlignCenterMiddle(){
		for (Bounding2D bounding2D : selectActors){
			bounding2D.actor.setX(copySelectBounding2D.start.x + copySelectBounding2D.size.x / 2 - bounding2D.actor.getWidth() / 2);
			bounding2D.actor.setY(copySelectBounding2D.start.y + copySelectBounding2D.size.y / 2 - bounding2D.actor.getHeight() / 2);
		}
	}

	/**
	 *
	 */
	public void actorAlignCenter(){
		for (Bounding2D bounding2D : selectActors){
			bounding2D.actor.setX(copySelectBounding2D.start.x + copySelectBounding2D.size.x / 2 - bounding2D.actor.getWidth() / 2);
		}
	}

	/**
	 *
	 */
	public void actorAlignTop(){
		for (Bounding2D bounding2D : selectActors){
			bounding2D.actor.setY(copySelectBounding2D.end.y - bounding2D.actor.getHeight());
		}
	}

	/**
	 *
	 */
	public void actorAlignMiddle(){
		for (Bounding2D bounding2D : selectActors){
			bounding2D.actor.setY(copySelectBounding2D.start.y + copySelectBounding2D.size.y / 2 - bounding2D.actor.getHeight() / 2);
		}
	}

	/**
	 *
	 */
	public void actorAlignBottom(){
		for (Bounding2D bounding2D : selectActors){
			bounding2D.actor.setY(copySelectBounding2D.start.y);
		}
	}

	/**
	 *
	 */
	public void actorAlignCenterTopPage(){
		for (Bounding2D selectActor : selectActors){
			selectActor.actor.setX(XY.centerX - selectActor.actor.getWidth() / 2);
			selectActor.actor.setY(XY.height - selectActor.actor.getHeight());
		}
	}

	/**
	 *
	 */
	public void actorAlignCenterMiddlePage(){
		for (Bounding2D selectActor : selectActors){
			selectActor.actor.setX(XY.centerX - selectActor.actor.getWidth() / 2);
			selectActor.actor.setY(XY.centerY - selectActor.actor.getHeight() / 2);
		}
	}

	/**
	 *
	 */
	public void actorAlignCenterBottomPage(){
		for (Bounding2D selectActor : selectActors){
			selectActor.actor.setX(XY.centerX - selectActor.actor.getWidth() / 2);
			selectActor.actor.setY(0);
		}
	}

	/**
	 *
	 */
	public void actorAlignLeftMiddlePage(){
		for (Bounding2D selectActor : selectActors){
			selectActor.actor.setX(0);
			selectActor.actor.setY(XY.centerY - selectActor.actor.getHeight() / 2);
		}
	}

	/**
	 *
	 */
	public void actorAlignRightMiddlePage(){
		for (Bounding2D selectActor : selectActors){
			selectActor.actor.setX(XY.width - selectActor.actor.getWidth());
			selectActor.actor.setY(XY.centerY - selectActor.actor.getHeight() / 2);
		}
	}

	/**
	 *
	 */
	public void actorAlignHalfTopPage(){
		for (Bounding2D selectActor : selectActors){
			selectActor.actor.setY(XY.centerY + XY.height / 4 - selectActor.actor.getHeight() / 2);
		}
	}

	/**
	 *
	 */
	public void actorAlignHalfBottomPage(){
		for (Bounding2D selectActor : selectActors){
			selectActor.actor.setY(XY.height / 4 - selectActor.actor.getHeight() / 2);
		}
	}

	public void actorAlignCenterHalfBottomPage(){
		for (Bounding2D selectActor : selectActors){
			selectActor.actor.setX(XY.centerX - selectActor.actor.getWidth() / 2);
			selectActor.actor.setY(XY.height / 4 - selectActor.actor.getHeight() / 2);
		}
	}

	public void actorAlignCenterHalfTopPage(){
		for (Bounding2D selectActor : selectActors){
			selectActor.actor.setX(XY.centerX - selectActor.actor.getWidth() / 2);
			selectActor.actor.setY(XY.centerY + XY.height / 4 - selectActor.actor.getHeight() / 2);
		}
	}

	/**
	 *
	 */
	public void actorAlignBottomPage(){
		for (Bounding2D selectActor : selectActors){
			selectActor.actor.setY(0);
		}
	}

	/**
	 *
	 */
	public void actorAlignTopPage(){
		for (Bounding2D selectActor : selectActors){
			selectActor.actor.setY(XY.height - selectActor.actor.getHeight());
		}
	}

	/**
	 *
	 */
	public void actorAlignLeftPage(){
		for (Bounding2D selectActor : selectActors){
			selectActor.actor.setX(0);
		}
	}

	/**
	 *
	 */
	public void actorAlignRightPage(){
		for (Bounding2D selectActor : selectActors){
			selectActor.actor.setX(XY.width - selectActor.actor.getWidth());
		}
	}

	/**
	 *
	 */
	public void actorAlignMiddlePage(){
		for (Bounding2D selectActor : selectActors){
			selectActor.actor.setY(XY.centerY - selectActor.actor.getHeight() / 2);
		}
	}

	/**
	 *
	 */
	public void actorAlignCenterPage(){
		for (Bounding2D selectActor : selectActors){
			selectActor.actor.setX(XY.centerX - selectActor.actor.getWidth() / 2);
		}
	}

	/**
	 * @param actor
	 * @param x
	 * @param y
	 */
	public void addActorToScene(Actor actor, float x, float y){

		if (!XY.cfg.editor){
			return;
		}

		Actor actorToAdd = actor;

		if (actor instanceof IconTextureRegion){
			actorToAdd = new Sprite(0, true, ((IconTextureRegion) actor).getRegionName());
			actorToAdd.setX(x);
			actorToAdd.setY(y);
			actorToAdd.setWidth(((IconTextureRegion) actor).getRegionWidth());
			actorToAdd.setHeight(((IconTextureRegion) actor).getRegionHeight());
		} else if (actor != null){
			actor.setX(x);
			actor.setY(y);
		}

		if (actorToAdd instanceof DataVO){
			((DataVO) actorToAdd).getVO().fromEditor = true;
		}


		if (XY.scene != null){
			String name = actorToAdd.getClass().getSimpleName() + getNameCount(actorToAdd);
			actorToAdd.setName(name);

			if (actorToAdd instanceof Image || actorToAdd instanceof Sprite){
				if (actorToAdd.getWidth() == 0 && actorToAdd.getHeight() == 0){
					actorToAdd.setWidth(64);
					actorToAdd.setHeight(64);
				}
			}

			Log.debug(TAG,
					  "Add actor to scene: " + actorToAdd.getName() + " pos: " + actorToAdd.getX() + ":" + actorToAdd.getY() + " width: " + actorToAdd
					  .getWidth() + ":" + actorToAdd.getHeight());


			XY.scene.addActor(actorToAdd);

			setSelectActor(actorToAdd.getName());
			XY.debug.itemListPanel.update();
		}
	}

	/**
	 * @param actor
	 * @return
	 */
	protected int getNameCount(Actor actor){
		int count = 1;
		for (Actor child : XY.scene.getChildren()){
			if (actor.getClass().equals(child.getClass())){
				count++;
			}
		}
		return count;
	}

	/**
	 *
	 */
	public void cutSelectActor(){

		if (!XY.cfg.editor){
			return;
		}

		copySelectActor();
		if (countSelectActor == 1){
			Log.debug(TAG, "Cut actor: " + currentActor.getName() + " class: " + currentActor.getClass().getName());
			if (XY.debug != null){
				XY.debug.setClipboard(currentActor);
			}
		} else if (countSelectActor > 1){
			Log.debug(TAG, "Cut actors: " + countSelectActor + " " + copyJson);
			if (XY.debug != null){
				XY.debug.setClipboard(countSelectActor);
			}
		}
		deleteSelectedActors();
	}

	/**
	 *
	 */
	public void copySelectActor(){
		if (!XY.cfg.editor){
			return;
		}

		copyJson = getSelectActorAsJson();
		if (countSelectActor == 1){
			Log.debug(TAG, "Copy actor: " + currentActor.getName() + " class: " + currentActor.getClass().getName());
			if (XY.debug != null){
				XY.debug.setClipboard(currentActor);
			}
		} else if (countSelectActor > 1){
			Log.debug(TAG, "Copy actors: " + countSelectActor + " " + copyJson);
			if (XY.debug != null){
				XY.debug.setClipboard(countSelectActor);
			}
		}
	}

	/**
	 *
	 */
	public void pasteSelectActor(){

		if (!XY.cfg.editor){
			return;
		}

		if (copyJson == null){
			return;
		}
		if (copyJson.trim().isEmpty()){
			return;
		}
		if (!copyJson.equals("")){

			Gdx.app.postRunnable(new Runnable(){
				@Override
				public void run(){
					addActorFromJson(copyJson);

					if (XY.debug != null){
						XY.debug.updateGUI();
					}
				}

			});
		}
	}

	/**
	 *
	 */
	public void duplicateSelectActor(){

		if (!XY.cfg.editor){
			return;
		}

		Gdx.app.postRunnable(new Runnable(){
			@Override
			public void run(){
				String duplicate = getSelectActorAsJson();
				addActorFromJson(duplicate);
				if (XY.debug != null){
					XY.debug.updateGUI();
				}
			}
		});

	}

}
