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

import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.TextureAttribute;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.math.Vector3;
import pl.lib2xy.XY;
import pl.lib2xy.app.Log;
import pl.lib2xy.app.ResourceManager;
import pl.lib2xy.app.Scene;
import pl.lib2xy.gfx.g3d.Mesh;
import pl.lib2xy.gfx.g3d.simpleobject.Sphere;

/**
 * @author Jarek
 */
public class G3DModelTest extends Scene{

	private Camera perspectiveCamera;
	private Mesh s3Mesh;
	private Model model;
	private ModelInstance modelInstance;
	private ModelBatch renderBatch;
	private InputMultiplexer inputMultiplexer = null;
	private float angleY = 0f;
	private float angleX = 0f;
	private float touchStartX = 0;
	private float touchStartY = 0;

	@Override
	public void initialize(){

		perspectiveCamera = new PerspectiveCamera(45, XY.width, XY.height);
		perspectiveCamera.position.set(5, 5, 5);
		perspectiveCamera.lookAt(0, 0, 0);
		perspectiveCamera.update();

		ModelBuilder mb = new ModelBuilder();
		model = mb.createSphere(5, 5, 5, 5, 5, new Material(ColorAttribute.createDiffuse(Color.WHITE),
															TextureAttribute.createDiffuse(ResourceManager.getTexture("texture/def256.jpg"))),
								VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal | VertexAttributes.Usage.TextureCoordinates);
		modelInstance = new ModelInstance(model);
		renderBatch = new ModelBatch();
	}

	@Override
	public void update(float delta){
	}

	@Override
	public void render(float delta){

		renderBatch.begin(perspectiveCamera);
		renderBatch.render(modelInstance);
		renderBatch.end();
	}

	@Override
	public void onTouchDown(int x, int y, int button){
		touchStartX = x;
		touchStartY = y;
	}

	@Override
	public void onDrag(int x, int y){
		float deltaX = (x - touchStartX) * 0.5f;
		float deltaY = (y - touchStartX) * 0.01f;

		perspectiveCamera.rotateAround(new Vector3(), Vector3.X, deltaX);
		perspectiveCamera.rotateAround(new Vector3(), Vector3.Y, deltaY);
		perspectiveCamera.update();

		touchStartX = x;
		touchStartY = y;
	}
}
