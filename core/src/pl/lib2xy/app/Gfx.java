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
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ImmediateModeRenderer;
import com.badlogic.gdx.graphics.glutils.ImmediateModeRenderer20;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import pl.lib2xy.XY;
import pl.lib2xy.gfx.g3d.shaders.BaseShader;

public class Gfx{

	private static final boolean LOG = true;
	private static final String TAG = "Gfx";

	public static Texture whiteTexture;
	public static TextureRegion whiteTextureRegion;
	private static Texture textureToDraw;
	private static Pixmap pixmapToDraw;
	private static Graphics graphics;
	private static GL20 gl;
	private static Stage stage;
	private static Skin skin;
	private static Camera fixedCamera;
	private static ShapeRenderer shapeRenderer;
	private static SpriteBatch spriteBatch;
	private static Color currentColor = Color.WHITE;
	private static Color backgroundColor = Color.BLACK;
	private static BitmapFont font;
	private static Texture defaultTexture = new Texture(1, 1, Pixmap.Format.RGBA8888);
	private static ImmediateModeRenderer immediateRenderer = new ImmediateModeRenderer20(false, true, 1);
	public static ShaderProgram defaultShader;
	public static ShaderProgram defaultShader2;
	private static Mesh mesh;
	private static Mesh meshFlip;

	private Gfx(){

	}

	public static void initalize(final Graphics graphicsIn, final GL20 glIn, final Stage stageIn, final Skin skinIn,
								 final Camera fixedCameraIn, final ShapeRenderer shapeRendererIn,
								 final SpriteBatch spriteBatchIn){

		if (LOG){
			Log.debug(TAG, "Initalize Gfx", 1);
		}

		graphics = graphicsIn;
		gl = glIn;
		stage = stageIn;
		skin = skinIn;
		fixedCamera = fixedCameraIn;
		shapeRenderer = shapeRendererIn;
		spriteBatch = spriteBatchIn;

		font = new BitmapFont();

		Pixmap pixmap = new Pixmap(2, 2, Pixmap.Format.RGBA8888);
		pixmap.setColor(1, 1, 1, 1);
		pixmap.fill();
		defaultTexture = new Texture(pixmap, false);

		pixmap = new Pixmap(2, 2, Pixmap.Format.RGB888);
		pixmap.setColor(Color.WHITE);
		pixmap.fill();
		whiteTexture = new Texture(pixmap, false);
		whiteTextureRegion = new TextureRegion(whiteTexture);

		createMesh();
	}

	private static void createMesh(){
		String vertexShader
		= "attribute vec4 a_position;   \n"
		+ "attribute vec2 a_texCoord;   \n"
		+ "varying vec2 v_texCoord;     \n"
		+ "void main()                  \n"
		+ "{                            \n"
		+ "   gl_Position = a_position; \n"
		+ "   v_texCoord = a_texCoord;  \n"
		+ "}                            \n";

		String fragmentShader = "#ifdef GL_ES\n"
		+ "precision mediump float;\n" + "#endif\n"
		+ "varying vec2 v_texCoord;                            \n"
		+ "uniform sampler2D u_texture0;                        \n"
		+ "uniform sampler2D u_texture1;                        \n"
		+ "void main()                                         \n"
		+ "{                                                   \n"
		+ "  gl_FragColor = texture2D( u_texture0, v_texCoord ) * texture2D( u_texture1, v_texCoord); \n"
		+ "}                                                   \n";
		defaultShader = new ShaderProgram(vertexShader, fragmentShader);

		String vertexShader2
		= "attribute vec4 a_position;   \n"
		+ "attribute vec2 a_texCoord;   \n"
		+ "varying vec2 v_texCoord;     \n"
		+ "void main()                  \n"
		+ "{                            \n"
		+ "   gl_Position = a_position; \n"
		+ "   v_texCoord = a_texCoord;  \n"
		+ "}                            \n";

		String fragmentShader2 = "#ifdef GL_ES\n"
		+ "precision mediump float;\n" + "#endif\n"
		+ "varying vec2 v_texCoord;                            \n"
		+ "uniform sampler2D u_texture0;                        \n"
		+ "void main()                                         \n"
		+ "{                                                   \n"
		+ "  gl_FragColor = texture2D( u_texture0, v_texCoord );\n"
		+ "}                                                   \n";
		defaultShader2 = new ShaderProgram(vertexShader2, fragmentShader2);

		mesh = new Mesh(true, 4, 6, new VertexAttribute(Usage.Position, 2, "a_position"), new VertexAttribute(
		Usage.TextureCoordinates, 2, "a_texCoord"));
		meshFlip = new Mesh(true, 4, 6, new VertexAttribute(Usage.Position, 2, "a_position"), new VertexAttribute(
		Usage.TextureCoordinates, 2, "a_texCoord"));
		float[] vertices = {-1.0f, 1.0f,
							0.0f, 0.0f,
							-1.0f, -1.0f,
							0.0f, 1.0f,
							1.0f, -1.0f,
							1.0f, 1.0f,
							1.0f, 1.0f,
							1.0f, 0.0f
		};
		float[] verticesFlip = {-1.0f, 1.0f,
								0.0f, 1.0f,
								-1.0f, -1.0f,
								0.0f, 0.0f,
								1.0f, -1.0f,
								1.0f, 0.0f,
								1.0f, 1.0f,
								1.0f, 1.0f
		};

		short[] indices = {0, 1, 2, 0, 2, 3};
		mesh.setVertices(vertices);
		mesh.setIndices(indices);
		meshFlip.setVertices(verticesFlip);
		meshFlip.setIndices(indices);

	}

	public static void clear(){
		gl.glClearColor(0, 0, 0, 1);
		gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
	}

	public static void clear(Color color){
		gl.glClearColor(color.r, color.g, color.b, color.a);
		gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
	}

	public static void clear(float red, float green, float blue){
		gl.glClearColor(red, green, blue, 1.0f);
		gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
	}

	public static void setColor(Color color){
		currentColor = color;
		shapeRenderer.setColor(currentColor);
	}

	public static void setColor(float red, float green, float blue, float alpha){
		currentColor.set(red, green, blue, alpha);
		shapeRenderer.setColor(red, green, blue, alpha);
	}

	public static void setPixel(float x, float y){
		shapeRenderer.begin(ShapeType.Point);
		shapeRenderer.identity();
		shapeRenderer.setColor(currentColor);
		shapeRenderer.point(x, y, 0);
		shapeRenderer.end();
	}

	/**
	 * Sets the color of a pixel using a color
	 *
	 * @param x
	 * @param y
	 * @param c
	 */
	public static void setPixel(float x, float y, Color c){
		shapeRenderer.begin(ShapeType.Point);
		shapeRenderer.identity();
		shapeRenderer.setColor(c);
		shapeRenderer.point(x, y, 0);
		shapeRenderer.end();
	}

	public static void clearPixel(float x, float y){
		shapeRenderer.begin(ShapeType.Point);
		shapeRenderer.identity();
		shapeRenderer.setColor(backgroundColor);
		shapeRenderer.point(x, y, 0);
		shapeRenderer.end();
	}

	public static void drawFPS(){
		spriteBatch.setProjectionMatrix(fixedCamera.combined);
		Color oldColor = font.getColor();
		font.setColor(Color.WHITE);
		drawString(5, 15, "FPS: " + XY.graphics.getFramesPerSecond() + " h: " + (Gdx.app.getJavaHeap() / 1024) + " kb"
		+ " m: " + (Gdx.app.getNativeHeap() / 1024) + " kb" + " d: " + graphics.getDeltaTime());
		font.setColor(oldColor);
	}

	public static void drawRectangle(float x, float y, float w, float h, float angle){
		shapeRenderer.begin(ShapeType.Line);
		shapeRenderer.setProjectionMatrix(fixedCamera.combined);
		shapeRenderer.setColor(currentColor);
		shapeRenderer.identity();
		shapeRenderer.translate(x + w / 2, y + w / 2, 0);
		shapeRenderer.rotate(0, 0, 1, angle);
		shapeRenderer.rect(-w / 2, -w / 2, w, h);
		shapeRenderer.end();
	}

	public static void drawLine(float p1x, float p1y, float p2x, float p2y){
		shapeRenderer.begin(ShapeType.Line);
		shapeRenderer.identity();
		shapeRenderer.setColor(currentColor);
		shapeRenderer.line(p1x, p1y, p2x, p2y);
		shapeRenderer.end();
	}

	public static void drawLine(float p1x, float p1y, float p2x, float p2y, Color c){
		shapeRenderer.begin(ShapeType.Line);
		shapeRenderer.identity();
		shapeRenderer.setColor(c);
		shapeRenderer.line(p1x, p1y, p2x, p2y);
		shapeRenderer.end();
	}

	public static void drawFilledRectangle(float x, float y, float w, float h, float angle){
		shapeRenderer.begin(ShapeType.Filled);
		shapeRenderer.identity();
		shapeRenderer.translate(x + w / 2, y + w / 2, 0);
		shapeRenderer.rotate(0, 0, 1, angle);
		shapeRenderer.rect(-w / 2, -w / 2, w, h);
		shapeRenderer.end();
	}

	public static void drawFilledRectangle(float x, float y, float w, float h, float angle, Color c){
		shapeRenderer.setColor(c);
		drawFilledRectangle(x, y, w, h, angle);
	}

	public static void drawCircle(float centerX, float centerY, float radius, Color c){
		shapeRenderer.setColor(c);
		drawCircle(centerX, centerY, radius);
	}

	public static void drawCircle(float centerX, float centerY, float radius){
		shapeRenderer.begin(ShapeType.Line);
		shapeRenderer.identity();
		shapeRenderer.circle(centerX, centerY, radius);
		shapeRenderer.end();
	}

	public static void drawFilledCircle(float centerX, float centerY, float radius, Color c){
		shapeRenderer.begin(ShapeType.Filled);
		shapeRenderer.setColor(c);
		shapeRenderer.identity();
		shapeRenderer.circle(centerX, centerY, radius);
		shapeRenderer.end();
	}

	public static void drawString(float posX, float posY, String str){
		spriteBatch.begin();
		font.draw(spriteBatch, str, posX, posY);
		spriteBatch.end();
	}

	public static void drawString(float posX, float posY, String str, Color color, float size){
		spriteBatch.begin();
		font.setColor(color);
		font.draw(spriteBatch, str, posX, posY);
		spriteBatch.end();
	}

	public static void drawString(float posX, float posY, String string, BitmapFont font){
		spriteBatch.begin();
		font.draw(spriteBatch, string, posX, posY);
		spriteBatch.end();
	}

	public static void drawCenterString(float posY, String string, BitmapFont font){
		float width = 1.0f;
		drawString((XY.width - width) / 2.0f, posY, string, font);
	}

	public static void drawBackground(){
		spriteBatch.setProjectionMatrix(fixedCamera.combined);
		spriteBatch.begin();
		spriteBatch.setColor(Color.WHITE);
		spriteBatch.disableBlending();
		spriteBatch.draw(defaultTexture, 0, 0, XY.width, XY.height);
		spriteBatch.enableBlending();
		spriteBatch.end();
	}

	public static void drawBackground(Color color){
		spriteBatch.setProjectionMatrix(fixedCamera.combined);
		spriteBatch.begin();
		spriteBatch.setColor(color);
		spriteBatch.enableBlending();
		spriteBatch.draw(defaultTexture, 0, 0, XY.width, XY.height);
		spriteBatch.end();
	}

	public static void drawBackground(Pixmap pixmap){
		Texture texture = new Texture(pixmap);
		spriteBatch.setProjectionMatrix(fixedCamera.combined);
		spriteBatch.begin();
		spriteBatch.setColor(Color.WHITE);
		spriteBatch.disableBlending();
		spriteBatch.draw(texture, 0, 0, XY.width, XY.height);
		spriteBatch.enableBlending();
		spriteBatch.end();
	}

	public static void drawBackground(Texture texture){
		spriteBatch.setProjectionMatrix(fixedCamera.combined);
		spriteBatch.begin();
		spriteBatch.setColor(Color.WHITE);
		spriteBatch.disableBlending();
		spriteBatch.draw(texture, 0, 0, XY.width, XY.height);
		spriteBatch.enableBlending();
		spriteBatch.end();
	}

	public static void drawBackground(Texture texture, Color color){
		spriteBatch.setProjectionMatrix(fixedCamera.combined);
		spriteBatch.setColor(color);
		spriteBatch.begin();
		spriteBatch.disableBlending();
		spriteBatch.draw(texture, 0, 0, XY.width, XY.height);
		spriteBatch.enableBlending();
		spriteBatch.end();
	}

	public static void drawBackground(Texture text, ShaderProgram shader){
		spriteBatch.setProjectionMatrix(fixedCamera.combined);
		spriteBatch.setShader(shader);
		spriteBatch.setColor(Color.WHITE);
		spriteBatch.begin();
		spriteBatch.disableBlending();
		spriteBatch.draw(text, 0, 0, XY.width, XY.height);
		spriteBatch.enableBlending();
		spriteBatch.end();
		spriteBatch.setShader(null);
	}

	public static void drawFlipBackground(Texture texture){
		spriteBatch.setProjectionMatrix(fixedCamera.combined);
		spriteBatch.begin();
		spriteBatch.disableBlending();
		spriteBatch.draw(texture, 0, XY.height, XY.width, -XY.height);
		spriteBatch.enableBlending();
		spriteBatch.end();
	}

	public static void drawFlipBackground(Texture text, ShaderProgram shader){
		spriteBatch.setProjectionMatrix(fixedCamera.combined);
		spriteBatch.setShader(shader);
		spriteBatch.setColor(Color.WHITE);
		spriteBatch.begin();
		spriteBatch.disableBlending();
		spriteBatch.draw(text, 0, XY.height, XY.width, -XY.height);
		spriteBatch.enableBlending();
		spriteBatch.end();
		spriteBatch.setShader(null);
	}


	public static void drawBackground(BaseShader shader){
		spriteBatch.setProjectionMatrix(fixedCamera.combined);
		shader.begin();
		shader.setProjectionMatrix(spriteBatch.getProjectionMatrix());
		shader.setModelViewMatrix(spriteBatch.getTransformMatrix());
		spriteBatch.setShader(shader.getShader());
		spriteBatch.begin();
		spriteBatch.setColor(1f, 1f, 1f, 1f);
		spriteBatch.enableBlending();
		spriteBatch.draw(defaultTexture, 0, 0, XY.width, XY.height);
		spriteBatch.end();
		spriteBatch.setShader(null);
		shader.end();
	}

	public static void drawBackground(BaseShader shader, float startX, float startY, float endX, float endY){
		spriteBatch.setProjectionMatrix(fixedCamera.combined);
		shader.begin();
		shader.setProjectionMatrix(spriteBatch.getProjectionMatrix());
		shader.setModelViewMatrix(spriteBatch.getTransformMatrix());
		spriteBatch.setShader(shader.getShader());
		spriteBatch.begin();
		spriteBatch.setColor(1f, 1f, 1f, 1f);
		spriteBatch.enableBlending();
		spriteBatch.draw(defaultTexture, startX, startY, endX - startX, endY - startY);
		spriteBatch.end();
		spriteBatch.setShader(null);
		shader.end();
	}

	/**
	 * @param batch
	 * @param shader
	 * @param startX
	 * @param startY
	 * @param endX
	 * @param endY
	 */
	public static void drawBackground(Batch batch, BaseShader shader, float startX, float startY, float endX, float endY){

		final Matrix4 projectionMatrix = batch.getProjectionMatrix();
		batch.setProjectionMatrix(fixedCamera.combined);
		shader.begin();
		shader.setStartPosition(startX * 2, startY * 2);
		shader.setResolutionShader(endX - startX, endY - startY);
		shader.setProjectionMatrix(batch.getProjectionMatrix());
		shader.setModelViewMatrix(batch.getTransformMatrix());
		batch.setShader(shader.getShader());
		batch.setColor(1f, 1f, 1f, 1f);
		batch.enableBlending();
		batch.draw(defaultTexture, startX, startY, endX - startX, endY - startY);
		batch.setShader(null);
		batch.setProjectionMatrix(projectionMatrix);
	}

	/**
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @param stepsX
	 * @param stepsY
	 */
	public static void drawGrid(int x, int y, int width, int height, int stepsX, int stepsY){

		float stepX = ((float) width / (float) stepsX);
		float stepY = ((float) height / (float) stepsY);
		float startX = x;
		float startY = y;
		float endX = x + width;
		float endY = y + height;

		int centerX = stepsX / 2;
		int centerY = stepsY / 2;

		stepsX++;
		stepsY++;

		shapeRenderer.setProjectionMatrix(fixedCamera.combined);
		shapeRenderer.begin(ShapeType.Line);
		shapeRenderer.identity();

		shapeRenderer.line(startX + 1, startY, startX + 1, endY);
		for (int i = 0; i < stepsX; i++){
			shapeRenderer.line(startX, startY, startX, endY);
			if (i == centerX){
				shapeRenderer.line(startX - 1, startY, startX - 1, endY);
				shapeRenderer.line(startX + 1, startY, startX + 1, endY);
			}
			startX = (startX + stepX);
		}

		shapeRenderer.line(startX - stepX - 1, startY, startX - stepX - 1, endY);

		startX = x;
		startY = y;
		shapeRenderer.line(startX, startY + 1, endX, startY + 1);

		for (int i = 0; i < stepsX; i++){
			shapeRenderer.line(startX, startY, endX, startY);
			if (i == centerY){
				shapeRenderer.line(startX, startY - 1, endX, startY - 1);
				shapeRenderer.line(startX, startY + 1, endX, startY + 1);
			}
			startY = (startY + stepY);
		}
		shapeRenderer.line(startX, startY - stepY - 1, endX, startY - stepY - 1);

		shapeRenderer.end();
	}

	/**
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @param stepsX
	 * @param stepsY
	 */
	public static void drawTick(int x, int y, int width, int height, int stepsX, int stepsY){

		float stepX = ((float) width / (float) stepsX);
		float stepY = ((float) height / (float) stepsY);

		int  startX = x;
		int  startY = y;
		int  endX = x + width;
		int  endY = y + height;

		int centerX = stepsX / 2;
		int centerY = stepsY / 2;

		stepsX++;
		stepsY++;

		shapeRenderer.setProjectionMatrix(fixedCamera.combined);
		shapeRenderer.begin(ShapeType.Point);
		shapeRenderer.identity();

		for (int iX = 0; iX < stepsX; iX++){
			for (int iY = 0; iY < stepsY; iY++){
				shapeRenderer.point((int) (iX * stepX), (int) (iY * stepY), 0);
			}
		}

		shapeRenderer.end();
	}

	public static void drawSquare(float x, float y, float width, float height){
		shapeRenderer.begin(ShapeType.Line);
		shapeRenderer.identity();
		shapeRenderer.setColor(currentColor);
		shapeRenderer.rect(x, y, width, height);
		shapeRenderer.end();
	}


}
