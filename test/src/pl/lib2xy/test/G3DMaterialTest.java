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

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.BlendingAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.TextureAttribute;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.math.Vector3;
import pl.lib2xy.app.ResourceManager;
import pl.lib2xy.app.Scene;

public class G3DMaterialTest extends Scene{

	float angleY = 0;
	Model model;
	ModelInstance modelInstance;
	ModelBatch modelBatch;
	TextureAttribute textureAttribute;
	ColorAttribute colorAttribute;
	BlendingAttribute blendingAttribute;
	Material material;
	Texture texture;
	Camera perspectiveCamera;
	private float counter = 0.f;

	@Override
	public void initialize(){

		texture = ResourceManager.getTexture("texture/def256.jpg");

		ModelBuilder builder = new ModelBuilder();
		model = builder.createBox(1, 1, 1, new Material(), Usage.Position | Usage.Normal | Usage.TextureCoordinates);
		model.manageDisposable(texture);
		modelInstance = new ModelInstance(model);

		// Create material attributes. Each material can contain x-number of attributes.
		textureAttribute = new TextureAttribute(TextureAttribute.Diffuse, texture);
		colorAttribute = new ColorAttribute(ColorAttribute.Diffuse, Color.ORANGE);
		blendingAttribute = new BlendingAttribute(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

		material = modelInstance.materials.get(0);

		modelBatch = new ModelBatch();

		perspectiveCamera = new PerspectiveCamera(45, 4, 4);
		perspectiveCamera.position.set(3, 3, 3);
		perspectiveCamera.direction.set(-1, -1, -1);
	}

	@Override
	public void update(float delta){
		counter = (counter + Gdx.graphics.getDeltaTime()) % 1.f;
		blendingAttribute.opacity = 0.25f + Math.abs(0.5f - counter);
		perspectiveCamera.update();
		modelInstance.transform.rotate(Vector3.Y, 30 * Gdx.graphics.getDeltaTime());
	}

	@Override
	public void render(float delta){

		modelBatch.begin(perspectiveCamera);
		modelBatch.render(modelInstance);
		modelBatch.end();
	}

	@Override
	public void onTouchUp(int x, int y, int button){
		if (!material.has(TextureAttribute.Diffuse)){
			material.set(textureAttribute);
		} else if (!material.has(ColorAttribute.Diffuse)){
			material.set(colorAttribute);
		} else if (!material.has(BlendingAttribute.Type)){
			material.set(blendingAttribute);
		} else {
			material.clear();
		}
	}

	@Override
	public void dispose(){
		model.dispose();
		modelBatch.dispose();
	}
}