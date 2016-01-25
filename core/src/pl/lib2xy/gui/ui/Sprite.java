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

package pl.lib2xy.gui.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ArrayMap;
import com.badlogic.gdx.utils.ObjectMap;
import pl.lib2xy.XY;
import pl.lib2xy.app.ResourceManager;
import pl.lib2xy.gui.GuiUtil;
import pl.lib2xy.interfaces.DataVO;
import pl.lib2xy.interfaces.GuiInterface;
import pl.lib2xy.items.ActorVO;
import pl.lib2xy.items.SpriteVO;

public class Sprite extends Actor implements GuiInterface, DataVO{

	private static final String TAG = "Sprite";
	protected final Vector2 position = new Vector2();
	protected final Vector2 center = new Vector2();
	protected final Vector2 size = new Vector2();
	protected final Rectangle rectangle = new Rectangle();
	protected Array<String> textures = new Array<String>();
	protected ObjectMap<String, Animation> animations = new ObjectMap<>();

	protected Animation animation;
	protected String animationName;
	protected TextureRegion keyFrame;

	protected float time = 0;

	public SpriteVO data = new SpriteVO();

	public Sprite(){
		super();
	}

	public Sprite(float duration, boolean single, String... texNames){
		data.singleImage = single;
		data.duration = duration;

		StringBuilder sb = new StringBuilder();
		for (String tex : textures){
			sb.append(tex);
			sb.append(",");
		}
		if (sb.lastIndexOf(",") > -1){
			sb.deleteCharAt(sb.lastIndexOf(","));
		}
		data.textures = sb.toString();

		setTextures(duration, 0, single, true, texNames);
	}

	public Sprite(SpriteVO spriteVO){
		data = spriteVO;
		setVO(spriteVO);
	}

	@Override
	public void draw(Batch batch, float parentAlpha){

		Color color = getColor();
		batch.setColor(color.r, color.g, color.b, color.a * parentAlpha);

		if (data.animationActive && animation != null){
			time = time + XY.deltaTime;
			keyFrame = animation.getKeyFrame(time, data.animationLooping);
			if (keyFrame != null){
				batch.draw(keyFrame, getX(), getY(), getOriginX(), getOriginY(), getWidth(), getHeight(),
						   getScaleX(), getScaleY(), getRotation());
			}
		} else if (keyFrame != null){
			batch.draw(keyFrame, getX(), getY(), getOriginX(), getOriginY(), getWidth(), getHeight(),
					   getScaleX(), getScaleY(), getRotation());
		}
	}


	@Override
	public void setX(float x){
		this.position.x = x;
		super.setX(x);
		center.set(position.x + size.x / 2, position.y + size.y / 2);
		this.rectangle.set(position.x, position.y, size.x, size.y);
	}

	@Override
	public void setY(float y){
		this.position.y = y;
		super.setY(y);
		center.set(position.x + size.x / 2, position.y + size.y / 2);
		this.rectangle.set(position.x, position.y, size.x, size.y);
	}

	@Override
	public void sizeBy(float size){
		super.sizeBy(size);
		this.size.add(size, size);
		center.set(position.x + this.size.x / 2, position.y + this.size.y / 2);
		this.rectangle.set(position.x, position.y, this.size.x, this.size.y);
	}

	@Override
	public void sizeBy(float width, float height){
		super.sizeBy(width, height);
		this.size.add(width, height);
		center.set(position.x + size.x / 2, position.y + size.y / 2);
		this.rectangle.set(position.x, position.y, size.x, size.y);
	}

	@Override
	public void setHeight(float height){
		super.setHeight(height);
		this.size.y = height;
		center.set(position.x + size.x / 2, position.y + size.y / 2);
		this.rectangle.set(position.x, position.y, size.x, size.y);
	}

	@Override
	public void setWidth(float width){
		super.setWidth(width);
		this.size.x = width;
		center.set(position.x + size.x / 2, position.y + size.y / 2);
		this.rectangle.set(position.x, position.y, size.x, size.y);
	}


	public void setCenter(float x, float y){
		center.set(x, y);
		position.set(x - size.x / 2, y - size.y / 2);
		this.rectangle.set(position.x, position.y, size.x, size.y);
		super.setX(position.x);
		super.setY(position.y);
	}

	@Override
	public void setPosition(float x, float y){
		super.setPosition(x, y);
		position.set(x, y);
		center.set(position.x + size.x / 2, position.y + size.y / 2);
		this.rectangle.set(position.x, position.y, size.x, size.y);
	}

	public void setCenterPosition(float x, float y){
		center.set(x, y);
		position.set(x - size.x / 2, y - size.y / 2);
		this.rectangle.set(position.x, position.y, size.x, size.y);
	}

	public Vector2 getPosition(){
		return position;
	}

	public Vector2 getCenter(){
		return center;
	}

	public Vector2 getSize(){
		return size;
	}

	public Rectangle getRectangle(){
		return rectangle;
	}

	public float getTime(){
		return time;
	}

	public void setTime(float time){
		this.time = time;
	}

	public void setDrawable(TextureRegionDrawable drawable){
		data.duration = 0;
		animation = null;
		keyFrame = drawable.getRegion();
	}

	public void setAnimationName(String animationName){
		if (animations.containsKey(animationName)){
			this.animationName = animationName;
			this.animation = animations.get(animationName);
		}
	}

	public void setTextures(float duration, int frames, boolean single, boolean loop, String... textureName){

		if (textureName == null){
			return;
		}
		if (textureName.length == 0){
			return;
		}
		textures.clear();
		animations.clear();

		if (single){

			if (frames == 0){
				frames = textureName.length;
			}
			if (duration < 0.1){
				duration = 1f / frames;
			}

			for (String texture : textureName){
				Array<TextureRegion> textureRegions = new Array<TextureRegion>();
				textures.add(texture.trim());
				for (int i = 1; i < frames + 1; i++){
					if (!texture.trim().isEmpty()){
						TextureRegion textureRegion = ResourceManager.getTextureRegion(texture.trim(), i);
						if (textureRegion != null){
							textureRegions.add(textureRegion);
						}
					}
				}

				if (textureRegions.size == 0){
					keyFrame = ResourceManager.getTextureRegion(texture.trim());
					if (data.width == 0 || data.height == 0){
						setSize(keyFrame.getRegionWidth(), keyFrame.getRegionHeight());
						data.width = keyFrame.getRegionWidth();
						data.height = keyFrame.getRegionHeight();
					}
					animation = ResourceManager.getAnimation(texture, duration, keyFrame);
					animations.put(texture, animation);
					animationName = texture;
				} else {
					animation = ResourceManager.getAnimation(texture, duration, textureRegions);
					animations.put(texture, animation);
					animationName = texture;
				}
			}
		} else {
			if (frames == 0){
				frames = textureName.length;
			}
			if (duration < 0.1){
				duration = 1f / frames;
			}

			if (textureName.length == 1){
				textures.add(textureName[0]);
				animation = ResourceManager.getAnimation(textureName[0], frames, duration);
				animations.put(textureName[0], animation);
				animationName = textureName[0];
			} else {

				Array<TextureRegion> textureRegions = new Array<TextureRegion>();
				for (String texture : textureName){
					if (!texture.trim().isEmpty()){
						textureRegions.add(ResourceManager.getTextureRegion(texture));
						textures.add(texture);
					}
				}
				animation = new Animation(duration, textureRegions);
				animations.put(textureName[0], animation);
				animationName = textureName[0];
			}
		}

		StringBuilder sb = new StringBuilder();
		for (String tex : textures){
			sb.append(tex);
			sb.append(",");
		}
		if (sb.lastIndexOf(",") > -1){
			sb.deleteCharAt(sb.lastIndexOf(","));
		}
		data.textures = sb.toString();

		data.duration = duration;
		data.animationLooping = loop;
		data.singleImage = single;
		data.frames = frames;
	}

	public void setTextures(String... textureName){
		setTextures(data.duration, data.frames, data.singleImage, data.animationLooping, textureName);
	}

	public void setAnimation(Animation animation){
		this.animation = animation;
	}

	public String getAnimationName(){
		return animationName;
	}

	public Animation getAnimation(String animationName){
		if (animations.containsKey(animationName)){
			return animations.get(animationName);
		}
		return null;
	}


	@Override
	public String toString(){
		StringBuilder sb = new StringBuilder();
		for (String tex : textures){
			sb.append(tex);
			sb.append(",");
		}
		if (sb.lastIndexOf(",") > -1){
			sb.deleteCharAt(sb.lastIndexOf(","));
		}
		return "Duration: " + data.duration + " Frames: " + data.frames + " Textures: " + sb.toString();
	}

	public void setVO(SpriteVO spriteVO){
		data = spriteVO;
		GuiUtil.setActorVO(data, this);
		String textures = data.textures;
		if (textures.length() > 1 && textures.charAt(textures.length() - 1) != ','){
			textures = textures + ",";
		}

		setTextures(data.duration, data.frames, data.singleImage, data.animationLooping, data.textures.split(","));
	}

	public void updateVO(){
		GuiUtil.updateActorVO(data, this);
		StringBuilder sb = new StringBuilder();
		for (String tex : textures){
			sb.append(tex);
			sb.append(",");
		}
		if (sb.lastIndexOf(",") > -1){
			sb.deleteCharAt(sb.lastIndexOf(","));
		}
		data.textures = sb.toString();
	}

	@Override
	public ActorVO getVO(){
		return data;
	}


	@Override
	public Array<String[]> getGuiDefinition(){
		Array<String[]> guiDef = new Array<>();
		guiDef.add(new String[]{this.getClass().getSimpleName(), "", "LABEL", ""});
		guiDef.add(new String[]{"textures", "textures", "TEXTURE_LIST", ""});
		guiDef.add(new String[]{"duration", "duration", "SPINNER", "1.0"});
		guiDef.add(new String[]{"active", "active", "CHECKBOX", "true"});
		guiDef.add(new String[]{"looping", "looping", "CHECKBOX", "true"});
		guiDef.add(new String[]{"single", "single", "CHECKBOX", "true"});
		guiDef.add(new String[]{"frameCount", "frameCount", "SPINNER_INT", "1"});
		guiDef.add(new String[]{"name", "name", "TEXT", ""});
		Label.getGuiDefinition(guiDef);
		return guiDef;
	}

	@Override
	public ArrayMap<String, String> getValues(){
		ArrayMap<String, String> values = new ArrayMap<>();

		updateVO();

		values.put("textures", data.textures);
		values.put("duration", data.duration + "");
		values.put("active", data.animationActive + "");
		values.put("looping", data.animationLooping + "");
		values.put("single", data.singleImage + "");
		values.put("frameCount", data.frames + "");

		values.put("name", data.name);
		values.put("x", data.x + "");
		values.put("y", data.y + "");
		values.put("width", data.width + "");
		values.put("height", data.height + "");
		values.put("originX", data.originX + "");
		values.put("originY", data.originY + "");
		values.put("rotation", data.rotation + "");
		values.put("color", data.color.toString());
		values.put("touchable", data.touchable + "");
		values.put("zindex", data.zIndex + "");
		values.put("visible", data.visible + "");
		return values;
	}

	@Override
	public void setValues(String changeKey, ArrayMap<String, String> values){

		if (Integer.valueOf(values.get("zindex")) < 0){
			values.put("zindex", "0");
		}

		if (changeKey.equals("active")){
			data.animationActive = Boolean.parseBoolean(values.get("active"));
		}
		if (changeKey.equals("looping")){
			data.animationLooping = Boolean.parseBoolean(values.get("looping"));
		}
		if (changeKey.equals("single")){
			data.singleImage = Boolean.parseBoolean(values.get("single"));
		}

		if (changeKey.equals("textures")){
			data.textures = values.get("textures");
		}

		if (changeKey.equals("frameCount")){
			data.frames = Integer.parseInt(values.get("frameCount"));
		}

		if (changeKey.equals("duration")){
			float tmpDur = Float.parseFloat(values.get("duration"));
			if (tmpDur < 0){
				tmpDur = 0.0f;
			}
			data.duration = tmpDur;
		}

		if (changeKey.equals("name")){
			data.name = values.get("name");
		}
		if (changeKey.equals("x")){
			data.x = Float.valueOf(values.get("x"));
		}
		if (changeKey.equals("y")){
			data.y = Float.valueOf(values.get("y"));
		}
		if (changeKey.equals("width")){
			data.width = Float.valueOf(values.get("width"));
		}
		if (changeKey.equals("height")){
			data.height = Float.valueOf(values.get("height"));
		}
		if (changeKey.equals("originX")){
			data.originX = Float.valueOf(values.get("originX"));
		}
		if (changeKey.equals("originY")){
			data.originY = Float.valueOf(values.get("originY"));
		}
		if (changeKey.equals("rotation")){
			data.rotation = Float.valueOf(values.get("rotation"));
		}
		if (changeKey.equals("color")){
			data.color = Color.valueOf(values.get("color"));
		}
		if (changeKey.equals("touchable")){
			data.touchable = values.get("touchable");
		}
		if (changeKey.equals("visible")){
			data.visible = Boolean.valueOf(values.get("visible"));
		}
		if (changeKey.equals("zindex")){
			data.zIndex = (int) (float) (Integer.valueOf(values.get("zindex")));
		}
		setVO(data);
	}
}
