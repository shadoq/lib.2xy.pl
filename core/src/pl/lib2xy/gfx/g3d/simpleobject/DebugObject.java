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

package pl.lib2xy.gfx.g3d.simpleobject;

import com.badlogic.gdx.graphics.Mesh;
import pl.lib2xy.app.Log;

import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

/**
 * @author Jarek
 */
public class DebugObject{

	public static void debugMesh(Mesh mesh){

		int numVertices = mesh.getNumVertices();
		int numIndices = mesh.getNumIndices();
		ShortBuffer indicesBuffer = mesh.getIndicesBuffer();
		FloatBuffer verticesBuffer = mesh.getVerticesBuffer();

		Log.log("debugMesh", "NumVertices: " + numVertices);
		Log.log("debugMesh", "NumIndices: " + numIndices);
		Log.log("debugMesh", "vertexSize (bytes): " + mesh.getVertexSize());

		Log.log("debugMesh", "Indices Buffer:", 1);
		StringBuilder buffer = new StringBuilder(32);
		buffer.append('[');
		buffer.append(indicesBuffer.get(0));
		for (int i = 1; i < numIndices; i++){
			buffer.append(", ");
			buffer.append(indicesBuffer.get(i));
		}
		buffer.append(']');
		Log.log("debugMesh", buffer.toString());

		Log.log("debugMesh", "Vertices Buffer:", 1);
		StringBuilder buffer2 = new StringBuilder(32);
		buffer2.append('[');
		buffer2.append(verticesBuffer.get(0));
		for (int i = 1; i < numIndices; i++){
			buffer2.append(", ");
			buffer2.append(verticesBuffer.get(i));
		}
		buffer.append(']');
		Log.log("debugMesh", buffer2.toString());
	}
}
