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
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.graphics.g3d.*;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import pl.lib2xy.XY;
import pl.lib2xy.app.Scene;

public class G3DFogTest extends Scene{

	public PerspectiveCamera cam;
	public CameraInputController inputController;
	public ModelBatch modelBatch;
	public Model model;
	public ModelInstance instance;
	Environment lights;
	float delta = 0f, dir = 1;
	private InputMultiplexer inputMultiplexer = null;

	@Override
	public void initialize(){

		modelBatch = new ModelBatch();
		lights = new Environment();
		lights.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.0f, 0.0f, 0.0f, 1.f));
		lights.set(new ColorAttribute(ColorAttribute.Fog, 0.13f, 0.13f, 0.13f, 1f));
		lights.add(new DirectionalLight().set(0.8f, 0.8f, 0.8f, -1f, -0.8f, -0.2f));

		cam = new PerspectiveCamera(67, XY.stage.getViewport().getScreenWidth(), XY.stage.getViewport().getScreenHeight());
		cam.position.set(30f, 10f, 30f);
		cam.lookAt(0, 0, 0);
		cam.near = 0.1f;
		cam.far = 45f;
		cam.update();

		ModelBuilder modelBuilder = new ModelBuilder();
		model = modelBuilder.createBox(10f, 10f, 10f, new Material(ColorAttribute.createDiffuse(Color.GREEN)), Usage.Position | Usage.Normal);
		instance = new ModelInstance(model);
	}

	@Override
	public void update(float delta){
		animate();
	}

	@Override
	public void render(float delta){
		modelBatch.begin(cam);
		modelBatch.render(instance, lights);
		modelBatch.end();
	}

	private void animate(){

		delta = Gdx.graphics.getDeltaTime();

		instance.transform.val[14] += delta * 4 * dir;

		if (Math.abs(instance.transform.val[14]) > 10){
			dir *= -1;
		}
	}

	@Override
	public void dispose(){
		modelBatch.dispose();
		model.dispose();
	}
}
