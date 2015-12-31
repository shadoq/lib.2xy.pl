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

package pl.lib2xy.math.grid;

import com.badlogic.gdx.graphics.Pixmap;

public class Grid{

	//
	// The world array (y-axis, x-axis)
	//
	protected int[][] world;
	protected int worldWidth = 0;
	protected int worldHeight = 0;
	protected boolean loop = false;

	public Grid(){
		this(8, 8, true);
	}

	public Grid(int width, int height){
		this(width, height, true);
	}

	public Grid(int width, int height, boolean loop){
		this.worldWidth = width;
		this.worldHeight = height;
		this.loop = loop;
		createEmpty();
	}

	public void setSize(int worldWidth, int worldHeight){
		this.worldWidth = worldWidth;
		this.worldHeight = worldHeight;
		createEmpty();
	}

	public void setLoop(boolean loop){
		this.loop = loop;
	}

	public int getWorldWidth(){
		return worldWidth;
	}

	public int getWorldHeight(){
		return worldHeight;
	}

	public boolean isLoop(){
		return loop;
	}

	public void createEmpty(){
		world = new int[worldWidth][worldHeight];
		for (int y = 0; y < worldHeight; y++){
			world[y] = new int[worldWidth];
			for (int x = 0; x < worldWidth; x++){
				world[y][x] = 0;
			}
		}
	}

	public void fillRandom(float factor, int bottomValue, int topValue){
		for (int y = 0; y < worldHeight; y++){
			for (int x = 0; x < worldWidth; x++){
				if (Math.random() > factor){
					world[y][x] = topValue;
				} else {
					world[y][x] = bottomValue;
				}
			}
		}
	}

	public void clear(){
		for (int y = 0; y < worldHeight; y++){
			for (int x = 0; x < worldWidth; x++){
				world[y][x] = 0;
			}
		}
	}


	public int getCell(int x, int y){

		if (loop){
			if (y < 0){
				y = worldHeight + y;
			}
			y = y % worldHeight;

			if (x < 0){
				x = worldWidth + x;
			}
			x = x % worldWidth;
		} else {
			if (y < 0 || y > worldHeight){
				throw new ArrayIndexOutOfBoundsException();
			}
			if (x < 0 || x > worldWidth){
				throw new ArrayIndexOutOfBoundsException();
			}
		}
		return world[y][x];
	}

	public void setCell(int x, int y, int value){
		if (loop){
			if (y < 0){
				y = worldHeight + y;
			}
			y = y % worldHeight;

			if (x < 0){
				x = worldWidth + x;
			}
			x = x % worldWidth;
		} else {
			if (y < 0 || y > worldHeight){
				throw new ArrayIndexOutOfBoundsException();
			}
			if (x < 0 || x > worldWidth){
				throw new ArrayIndexOutOfBoundsException();
			}
		}
		world[y][x] = value;
	}

	public int countNeighbour(int x, int y, int valueToCount){

		int counter = 0;
		for (int yCounter = -1; yCounter < 2; yCounter++){

			for (int xCounter = -1; xCounter < 2; xCounter++){

				if (xCounter == 0 && yCounter == 0){
					continue;
				}

				int xCell = x + xCounter;
				int yCell = y + yCounter;

				if (loop){
					if (yCell < 0){
						yCell = worldHeight + yCell;
					}
					yCell = yCell % worldHeight;

					if (xCell < 0){
						xCell = worldWidth + xCell;
					}
					xCell = xCell % worldWidth;
				} else {
					if (yCell < 0 || yCell > worldHeight){
						throw new ArrayIndexOutOfBoundsException();
					}
					if (xCell < 0 || xCell > worldWidth){
						throw new ArrayIndexOutOfBoundsException();
					}
				}

				if (world[yCell][xCell] == valueToCount){
					counter++;
				}
			}
		}
		return counter;
	}


	public int countAll(int valueToCount){

		int counter = 0;
		for (int y = 0; y < worldHeight; y++){
			for (int x = 0; x < worldWidth; x++){
				if (world[y][x] == valueToCount){
					counter++;
				}
			}
		}
		return counter;
	}

	public void printWorld(){
		for (int y = 0; y < worldHeight; y++){
			System.out.print("[");
			for (int x = 0; x < worldWidth; x++){
				System.out.print(world[y][x] + "|");
			}
			System.out.print("]");
			System.out.println();
		}
	}

	@Override
	public String toString(){
		return "Grid{" +
		" worldWidth=" + worldWidth +
		", worldHeight=" + worldHeight +
		", loop=" + loop +
		'}';
	}

	public Pixmap toPixmap(){

		Pixmap pixmap = new Pixmap(worldWidth, worldHeight, Pixmap.Format.RGBA8888);

		for (int y = 0; y < worldHeight; y++){
			for (int x = 0; x < worldWidth; x++){
				pixmap.drawPixel(x, y, world[y][x] << 24 | world[y][x] << 16 | world[y][x] << 8 | 255);
			}
		}

		return pixmap;
	}

	public String getAsString(){
		StringBuilder sb = new StringBuilder();
		sb.append(worldWidth).append("x").append(worldHeight).append("\n");
		for (int y = 0; y < worldHeight; y++){
			for (int x = 0; x < worldWidth; x++){
				sb.append(world[y][x]).append("|");
			}
			sb.append("\n");
		}
		return sb.toString();
	}
}

