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

package pl.lib2xy.test;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import pl.lib2xy.app.Log;
import pl.lib2xy.app.ResourceManager;
import pl.lib2xy.app.Scene;
import pl.lib2xy.gui.ui.Image;
import pl.lib2xy.gui.ui.Label;
import pl.lib2xy.gui.ui.TextButton;

/**
 *
 */
public class GameOXO extends Scene{

	private Block[][] blocks = new Block[3][3];
	private int turn = 0;
	private GameMode mode = GameMode.SINGLE_PLAYER;
	private State win = State.None;

	private int scoreX = 0;
	private int scoreO = 0;
	private int finishTurn = 0;

	private TextButton resetButton;
	private TextButton backButton;
	private Label scoreLabel;
	private Label turnCountLabel;
	private Label winLabel;

	public enum State{
		None,
		X,
		O
	}

	public enum GameMode{
		SINGLE_PLAYER,
		SINGLE_PLAYER_VS_COMPUTER,
		MULTI_PLAYER,
	}

	public static class Block{
		private Image image;

		private TextureRegionDrawable textureBlock;
		private TextureRegionDrawable textureBlockX;
		private TextureRegionDrawable textureBlockO;

		private boolean isClick = false;
		private State state = State.None;

		public Block(Image image){
			this.image = image;
			textureBlock = new TextureRegionDrawable(ResourceManager.getTextureRegion("block"));
			textureBlockX = new TextureRegionDrawable(ResourceManager.getTextureRegion("block_x"));
			textureBlockO = new TextureRegionDrawable(ResourceManager.getTextureRegion("block_o"));

			image.addListener(new ClickListener(){
				@Override
				public void clicked(InputEvent event, float x, float y){
					isClick = true;
					Log.log("Block", "Block click: " + event + " x: " + x + " y: " + y);
				}
			});

			image.setDrawable(textureBlock);
		}

		public boolean isClick(){
			return isClick;
		}

		public void setClick(boolean click){
			this.isClick = click;
		}

		public State getState(){
			return state;
		}

		public void setState(State state){
			this.state = state;
			switch (state){
				default:
					image.setDrawable(textureBlock);
					break;
				case X:
					image.setDrawable(textureBlockX);
					break;
				case O:
					image.setDrawable(textureBlockO);
					break;
			}
		}

		public void reset(){
			setState(State.None);
			setClick(false);
		}
	}


	private void checkTurn(){

		if (win != State.None){
			return;
		}

		boolean turnChange = false;

		for (Block[] blockRow : blocks){
			for (Block block : blockRow){
				if (block.isClick()){
					if (block.getState() == State.None){
						turnChange = true;
						if (turn % 2 == 0){
							block.setState(State.X);
						} else {
							block.setState(State.O);
						}
					}
				}
				block.setClick(false);
			}

		}

		if (turnChange){
			turn++;
		} else {
			checkWin();
			return;
		}

		if (mode == GameMode.SINGLE_PLAYER_VS_COMPUTER){
			computerTurn();
		}
		checkWin();
	}

	private void computerTurn(){
		for (int i = 0; i < 9; i++){

			int col = (int) random(3);
			int row = (int) random(3);

			if (blocks[col][row].getState() == State.None){
				if (turn % 2 == 0){
					blocks[col][row].setState(State.X);
				} else {
					blocks[col][row].setState(State.O);
				}
				return;
			}
		}
	}

	private void checkWin(){
		for (int i = 0; i < 3; i++){
			checkForColumn(i);
			checkForRow(i);
		}
		checkForDiagonal();
	}

	void checkForColumn(int j){
		if (
		blocks[j][0].getState() != State.None &&
		blocks[j][1].getState() != State.None &&
		blocks[j][2].getState() != State.None &&
		blocks[j][0].getState() == blocks[j][1].getState() &&
		blocks[j][0].getState() == blocks[j][2].getState()
		){
			if (blocks[j][0].getState() == State.X){
				playerXWin();
			} else if (blocks[j][0].getState() == State.O){
				playerOWin();
			}
		}
	}

	void checkForRow(int j){
		if (
		blocks[0][j].getState() != State.None &&
		blocks[1][j].getState() != State.None &&
		blocks[2][j].getState() != State.None &&
		blocks[0][j].getState() == blocks[1][j].getState() &&
		blocks[0][j].getState() == blocks[2][j].getState()
		){
			if (blocks[0][j].getState() == State.X){
				playerXWin();
			} else if (blocks[0][j].getState() == State.O){
				playerOWin();
			}
		}
	}


	void checkForDiagonal(){

		if (
		blocks[0][0].getState() != State.None &&
		blocks[1][1].getState() != State.None &&
		blocks[2][2].getState() != State.None &&
		blocks[0][0].getState() == blocks[1][1].getState() &&
		blocks[0][0].getState() == blocks[2][2].getState()
		){
			if (blocks[0][0].getState() == State.X){
				playerXWin();
			} else if (blocks[0][0].getState() == State.O){
				playerOWin();
			}
		}

		if (
		blocks[0][2].getState() != State.None &&
		blocks[1][1].getState() != State.None &&
		blocks[2][0].getState() != State.None &&
		blocks[0][2].getState() == blocks[1][1].getState() &&
		blocks[0][2].getState() == blocks[2][0].getState()
		){
			if (blocks[0][2].getState() == State.X){
				playerXWin();
			} else if (blocks[0][2].getState() == State.O){
				playerOWin();
			}
		}
	}

	private void playerOWin(){
		log("Player O Win");
		win = State.O;
		scoreO++;
		finishTurn++;
		setTextLabel();
		winLabel.setText("Win: Player O");
		showToast("Win Player O", 2);
	}

	private void playerXWin(){
		log("Player X Win");
		win = State.X;
		scoreX++;
		finishTurn++;
		setTextLabel();
		winLabel.setText("Win: Player X");
		showToast("Win Player X", 2);
	}

	private void setTextLabel(){
		turnCountLabel.setText("Turn: " + finishTurn);
		scoreLabel.setText("X: " + scoreX + " O: " + scoreO);
	}

	@Override
	public void initialize(){

		int k = 1;
		for (int i = 0; i < 3; i++){
			blocks[i] = new Block[3];
			for (int j = 0; j < 3; j++){

				final Actor blockActor = findActor("Image" + k);
				if (blockActor != null){
					blocks[i][j] = new Block((Image) blockActor);
				}
				k++;
			}
		}

		scoreLabel = (Label) findActor("Label1");
		turnCountLabel = (Label) findActor("Label2");
		winLabel = (Label) findActor("Label3");

		resetButton = (TextButton) findActor("TextButton2");
		backButton = (TextButton) findActor("TextButton1");

		if (resetButton != null){
			resetButton.addListener(new ClickListener(){
				@Override
				public void clicked(InputEvent event, float x, float y){

					for (int i = 0; i < 3; i++){
						for (int j = 0; j < 3; j++){
							blocks[i][j].reset();
						}
					}

					win = State.None;
				}
			});

		}
		if (backButton != null){
			backButton.addListener(new ClickListener(){
				@Override
				public void clicked(InputEvent event, float x, float y){
					setScene("Main");
				}
			});
		}
	}

	@Override
	public void update(float delta){

	}

	@Override
	public void render(float delta){

	}

	@Override
	public void resize(){

	}

	@Override
	public void pause(){

	}

	@Override
	public void dispose(){

	}

	@Override
	public void resume(){

	}

	@Override
	public void onClick(InputEvent event, int x, int y){

	}

	@Override
	public void onClick(InputEvent event, Actor actor, int x, int y){
		log("click ON: " + event + " actor: " + actor + " x: " + x + " y: " + y);
		checkTurn();
	}

	@Override
	public void onDrag(int x, int y){

	}

	@Override
	public void onTouchDown(int x, int y, int button){

	}

	@Override
	public void onTouchUp(int x, int y, int button){

	}

	@Override
	public void onKeyDown(int keycode){

	}

	@Override
	public void onKeyUp(int keycode){

	}

	@Override
	public void onMouseMoved(int screenX, int screenY){

	}

	@Override
	public void onZoom(float initialDistance, float distance){

	}

	@Override
	public void onTap(float x, float y, int count, int button){

	}

	@Override
	public void onPinch(Vector2 initialPointer1, Vector2 initialPointer2, Vector2 pointer1, Vector2 pointer2){

	}

	@Override
	public void onPan(float x, float y, float deltaX, float deltaY){

	}

	@Override
	public void onPanStop(float x, float y, int pointer, int button){

	}

	@Override
	public void onLongPress(float x, float y){

	}

	@Override
	public void onFling(float velocityX, float velocityY, int button){

	}
}
