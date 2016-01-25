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

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import pl.lib2xy.XY;
import pl.lib2xy.app.ResourceManager;
import pl.lib2xy.app.Scene;
import pl.lib2xy.gui.ui.Label;
import pl.lib2xy.gui.ui.Sprite;
import pl.lib2xy.gui.ui.TextButton;

/**
 *
 */
public class GameAsteroids extends Scene{

	//
	// Score
	//
	private static int maxScore = 0;
	//
	// Bullets
	//
	private final int MAX_BULLETS = 1000;
	final Array<Bullet> bullets = new Array<>(MAX_BULLETS);
	//
	// Asteroids
	//
	private final int MAX_ASTEROIDS = 10;
	final Array<Asteroid> asteroids = new Array<>(MAX_ASTEROIDS);
	//
	// GUI
	//
	Label scoreLabel;
	Label lifeLabel;
	TextButton backButton;

	//
	// Ship
	//
	private Ship ship;
	private int shipLife = 3;
	private float bulletTimeRate = 0.2f;
	private float bulletTime = 0.0f;
	private float asteroidsTimeRate = 10.0f;
	private float asteroidsTime = 0.0f;
	//
	// Keys
	//
	private boolean keyLeft = false;
	private boolean keyRight = false;
	private boolean keyUp = false;
	private boolean keyDown = false;
	private boolean keyFire = false;
	private int score = 0;

	//---------------------------------------------------------------
	// Ship Class
	//---------------------------------------------------------------

	/**
	 *
	 */
	public static class Ship extends Sprite{

		private float deAcceleration = 0.3f;
		private float maxVelocity = 100;
		private Vector2 velocity = new Vector2(0, 0);
		private float angle = 90;
		private Vector2 direction = new Vector2(0, 0);
		private float energy = 100f;

		private TextureRegion texture;

		public Ship(){
			texture = ResourceManager.getTextureRegion("violet_ship");
			setCenter(XY.centerX, XY.centerY);
			size.set(64, 64);
			setX(position.x);
			setY(position.y);
			setWidth(size.x);
			setHeight(size.y);
			setBounds(position.x, position.y, size.x, size.y);
			setCenterPosition(position.x, position.y);
			setColor(Color.WHITE);
			setDrawable(new TextureRegionDrawable(texture));
			addAngle(0);
			energy = 100f;
		}

		public void restart(){
			energy = 100f;
		}

		public void update(float osDeltaTime){

			float speedVectorLength = velocity.len();

			if (speedVectorLength > 0.0f){
				velocity.x = (float) (velocity.x - velocity.x / speedVectorLength * deAcceleration * osDeltaTime);
				velocity.y = (float) (velocity.y - velocity.y / speedVectorLength * deAcceleration * osDeltaTime);
			}

			if (velocity.x > maxVelocity){
				velocity.x = maxVelocity;
			}
			if (velocity.y > maxVelocity){
				velocity.y = maxVelocity;
			}
			if (velocity.x < 0){
				velocity.x = 0;
				velocity.y = 0;
			}
			if (velocity.y < 0){
				velocity.x = 0;
				velocity.y = 0;
			}

			center.x = center.x + velocity.x * direction.x * osDeltaTime;
			center.y = center.y + velocity.y * direction.y * osDeltaTime;
			if (center.x > XY.width){
				center.x = 0;
			}
			if (center.x < 0){
				center.x = XY.width;
			}
			if (center.y > XY.height){
				center.y = 0;
			}
			if (center.y < 0){
				center.y = XY.height;
			}

			setCenter(center.x, center.y);
			setOrigin(size.x / 2, size.y / 2);
			setRotation(angle + 270);
		}

		public void addSpeed(float acceleration){
			velocity.x = velocity.x + acceleration;
			velocity.y = velocity.y + acceleration;
		}

		public float getAngle(){
			return angle;
		}

		public void setAngle(float angle){
			this.angle = angle;
			if (this.angle > 360){
				this.angle = 0;
			}
			if (this.angle < 0){
				this.angle = 360;
			}

			direction.set((float) Math.cos(this.angle * MathUtils.degreesToRadians), (float) Math.sin(this.angle * MathUtils.degreesToRadians)).nor();
		}


		public void addAngle(float angle){
			this.angle = this.angle + angle;
			setAngle(this.angle);
		}

		@Override
		public void draw(Batch batch, float parentAlpha){
			super.draw(batch, parentAlpha);
		}

		public void onCollision(){
			energy = energy - XY.deltaTime * 5;
		}

		public boolean isAllive(){
			return energy > 0;
		}

		public int getEnergy(){
			return Float.valueOf(energy).intValue();
		}
	}

	//---------------------------------------------------------------
	// Bullet Class
	//---------------------------------------------------------------

	/**
	 *
	 */
	public static class Bullet extends Sprite{

		private Vector2 velocity = new Vector2(200, 200);
		private float angle = 90;
		private Vector2 direction = new Vector2(0, 0);
		private boolean active = false;
		private TextureRegion texture = ResourceManager.getTextureRegion("bullet1");

		public Bullet(){
			position.set(0, 0);
			size.set(16, 16);
			setCenter(center.x, center.y);
			setWidth(size.x);
			setHeight(size.y);
			setBounds(position.x, position.y, size.x, size.y);
			setColor(Color.WHITE);
			setDrawable(new TextureRegionDrawable(texture));
			setAngle(0);
		}

		public Vector2 getPosition(){
			return position;
		}

		public float getAngle(){
			return angle;
		}

		public void setAngle(float angle){
			this.angle = angle;
			if (this.angle > 360){
				this.angle = 0;
			}
			if (this.angle < 0){
				this.angle = 360;
			}

			direction.set((float) Math.cos(this.angle * MathUtils.degreesToRadians), (float) Math.sin(this.angle * MathUtils.degreesToRadians)).nor();
		}

		public boolean isActive(){
			return active;
		}

		public void setActive(boolean active){
			this.active = active;
			setVisible(active);
		}

		public void update(float osDeltaTime){

			center.x = center.x + velocity.x * direction.x * osDeltaTime;
			center.y = center.y + velocity.y * direction.y * osDeltaTime;
			if (center.x > XY.width || center.y > XY.height || center.x < 0 || center.y < 0){
				setActive(false);
			}
			setCenter(center.x, center.y);
			setOrigin(size.x / 2, size.y / 2);
			setRotation(angle + 270);
		}

		public void onCollision(){
			setActive(false);
		}
	}

	//---------------------------------------------------------------
	// Asteroid Class
	//---------------------------------------------------------------

	/**
	 *
	 */
	public static class Asteroid extends Sprite{

		private Vector2 velocity = new Vector2(200, 200);
		private float angle = 90;
		private Vector2 direction = new Vector2(0, 0);
		private boolean active = false;
		private TextureRegion texture = ResourceManager.getTextureRegion("asteroids1");

		private float timeDirectionChange = 10f;
		private float timeDirection = 0f;
		private float rotation = 0;
		private float rotationSpeed = 0;

		private float energy = 20f;

		public Asteroid(){

			center.set((float) Math.random() * XY.width, (float) Math.random() * XY.height);
			velocity.set((float) Math.random() * 100, (float) Math.random() * 100);
			rotationSpeed = 10 + (float) Math.random() * 20;

			timeDirectionChange = 10 + MathUtils.random(10);

			size.set(64, 64);
			setCenter(center.x, center.y);
			setWidth(size.x);
			setHeight(size.y);
			setBounds(position.x, position.y, size.x, size.y);
			setColor(Color.WHITE);
			setDrawable(new TextureRegionDrawable(texture));
			setAngle((float) Math.random() * 360);
		}

		public void update(float osDeltaTime){

			timeDirection += osDeltaTime;
			if (timeDirection > timeDirectionChange){
				timeDirection = 0;
				timeDirectionChange = 10 + MathUtils.random(10);
				setAngle((float) Math.random() * 360);
				velocity.set((float) Math.random() * 100, (float) Math.random() * 100);
			}

			center.x = center.x + velocity.x * direction.x * osDeltaTime;
			center.y = center.y + velocity.y * direction.y * osDeltaTime;
			if (center.x > XY.width){
				center.x = 0;
			}
			if (center.x < 0){
				center.x = XY.width;
			}
			if (center.y > XY.height){
				center.y = 0;
			}
			if (center.y < 0){
				center.y = XY.height;
			}
			setCenter(center.x, center.y);
			setOrigin(size.x / 2, size.y / 2);

			rotation = rotation + osDeltaTime * rotationSpeed;
			if (rotation > 360){
				rotation = 0;
			}
			setRotation(angle + rotation);
		}

		public void render(){

		}

		public void setAngle(float angle){
			this.angle = angle;
			if (this.angle > 360){
				this.angle = 0;
			}
			if (this.angle < 0){
				this.angle = 360;
			}

			direction.set((float) Math.cos(this.angle * MathUtils.degreesToRadians), (float) Math.sin(this.angle * MathUtils.degreesToRadians)).nor();
		}

		public boolean isActive(){
			return active;
		}

		public void setActive(boolean active){
			this.active = active;
			setVisible(active);
		}


		public void onCollision(){
			energy = energy - XY.deltaTime * 40f;
		}

		public boolean isAllive(){
			return energy > 0;
		}
	}

	//---------------------------------------------------------------
	// Main Class method
	//---------------------------------------------------------------

	/**
	 *
	 */
	@Override
	public void initialize(){

		ship = new Ship();

		score = 0;
		shipLife = 3;

		bullets.clear();
		for (int i = 0; i < MAX_BULLETS; i++){
			Bullet bullet = new Bullet();
			bullet.setName("bullet_" + i);
			bullet.setActive(false);
			bullets.add(bullet);
			addActor(bullet);
		}

		asteroids.clear();
		for (int i = 0; i < MAX_ASTEROIDS; i++){
			Asteroid asteroid = new Asteroid();
			asteroid.setName("asteroid_" + i);
			asteroid.setActive(true);
			asteroids.add(asteroid);
			addActor(asteroid);
		}

		ship.setName("ship");
		addActor(ship);

		backButton = (TextButton) findActor("backButton");
		scoreLabel = (Label) findActor("scoreLabel");
		lifeLabel = (Label) findActor("lifeLabel");


		if (scoreLabel != null){
			scoreLabel.setText("Score: " + score);
		}
		if (lifeLabel != null){
			lifeLabel.setText("Life: " + shipLife + "/" + ship.getEnergy());
		}

		if (backButton != null){

			backButton.addListener(new ChangeListener(){
				@Override
				public void changed(ChangeEvent event, Actor actor){
					setScene("Main");
				}
			});
		}

	}

	@Override
	public void update(float delta){

		updateKeys();
		updateFireBullet();

		for (Bullet bullet : bullets){
			if (bullet.isActive()){
				bullet.update(XY.deltaTime);
			}
		}

		for (Asteroid asteroid : asteroids){
			if (asteroid.isActive()){
				asteroid.update(XY.deltaTime);
			}
		}

		ship.update(XY.deltaTime);

		detectBulletCollision();
		detectAsteroidCollision();
		detectShipCollision();
	}

	private void detectShipCollision(){
		for (Asteroid asteroid : asteroids){
			if (asteroid.getRectangle().overlaps(ship.getRectangle())){
				shipToAsteroidCollision(ship, asteroid);
			}
		}


	}


	private void detectAsteroidCollision(){
		for (Bullet bullet : bullets){
			if (bullet.isActive()){
				for (Asteroid asteroid : asteroids){
					if (asteroid.isActive()){
						if (bullet.getRectangle().overlaps(asteroid.getRectangle())){
							bulletToAsteroidCollision(bullet, asteroid);
						}
					}
				}

			}
		}
	}

	private void detectBulletCollision(){

		for (int i = 0; i < asteroids.size; i++){
			for (int j = 0; i < asteroids.size; i++){

				if (i != j){
					Asteroid asteroid = asteroids.get(i);
					Asteroid asteroid2 = asteroids.get(j);

					if (asteroid.isActive() && asteroid2.isActive()){
						if (asteroid.getRectangle().overlaps(asteroid2.getRectangle())){
							asteroidToAsteroidCollision(asteroid, asteroid2);
						}
					}

				}
			}

		}
	}

	private void asteroidToAsteroidCollision(Asteroid asteroid, Asteroid asteroid2){

	}

	private void shipToAsteroidCollision(Ship ship, Asteroid asteroid){

		ship.onCollision();
		if (!ship.isAllive()){
			shipLife--;
			ship.restart();
		}
		if (shipLife < 1){
			// ToDo: GameHighScore class
			// GameHighScore.addHighScore(score);
			setScene("Main");
		}
		if (lifeLabel!=null){
			lifeLabel.setText("Life: " + shipLife + "/" + ship.getEnergy());
		}
	}

	private void bulletToAsteroidCollision(Bullet bullet, Asteroid asteroid){
		score++;
		if (score > maxScore){
			maxScore = score;
		}
		scoreLabel.setText("Score: " + score);

		asteroid.onCollision();
		if (!asteroid.isAllive()){
			asteroid.setActive(false);
		}

		bullet.onCollision();
	}

	private void updateFireBullet(){

		if (keyFire){
			bulletTime = bulletTime + XY.deltaTime;
			if (bulletTime > bulletTimeRate){
				bulletTime = 0;
				fireBullet(ship.getCenter(), ship.getAngle());
			}
		}
	}

	private void fireBullet(Vector2 position, float angle){
		for (Bullet bullet : bullets){
			if (!bullet.isActive()){
				bullet.setCenter(position.x, position.y);
				bullet.setAngle(angle);
				bullet.setActive(true);
				break;
			}
		}
	}

	private void updateKeys(){
		if (keyLeft){
			ship.addAngle(20.0f * XY.deltaTime);
		}
		if (keyRight){
			ship.addAngle(-20.0f * XY.deltaTime);
		}
		if (keyUp){
			ship.addSpeed(-20.0f * XY.deltaTime);
		}
		if (keyDown){
			ship.addSpeed(100.0f * XY.deltaTime);
		}
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
		if (keycode == Keys.LEFT){
			keyLeft = true;
		} else if (keycode == Keys.RIGHT){
			keyRight = true;
		} else if (keycode == Keys.UP){
			keyUp = true;
		} else if (keycode == Keys.DOWN){
			keyDown = true;
		} else if (keycode == Keys.SPACE || keycode == Keys.CONTROL_LEFT){
			keyFire = true;
		}
	}

	@Override
	public void onKeyUp(int keycode){
		if (keycode == Keys.LEFT){
			keyLeft = false;
		} else if (keycode == Keys.RIGHT){
			keyRight = false;
		} else if (keycode == Keys.UP){
			keyUp = false;
		} else if (keycode == Keys.DOWN){
			keyDown = false;
		} else if (keycode == Keys.SPACE || keycode == Keys.CONTROL_LEFT){
			keyFire = false;
		}
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
