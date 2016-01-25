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

package pl.lib2xy.gui.widget;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import pl.lib2xy.app.Cfg;
import pl.lib2xy.gui.GuiResource;

public class ColorSampler extends Table{

	protected Skin skin;
	protected Color color = null;
	protected float colorR = 1;
	protected float colorG = 1;
	protected float colorB = 1;
	protected float colorA = 1;
	protected Pixmap pixmap;
	protected Texture texture;
	protected TextureRegion region;
	protected Image imageActor;
	protected Slider sliderR;
	protected Slider sliderG;
	protected Slider sliderB;
	protected Slider sliderA;
	protected Label labelTextR = null;
	protected Label labelTextG = null;
	protected Label labelTextB = null;
	protected Label labelTextA = null;
	protected Label labelR = null;
	protected Label labelG = null;
	protected Label labelB = null;
	protected Label labelA = null;
	protected boolean alphaChanel = false;

	private ChangeListener listener;

	/**
	 *
	 */
	public ColorSampler(){
	}

	/**
	 *
	 * @param skin
	 */
	public ColorSampler(Skin skin){
		super(skin);
		this.skin = skin;
		initalize();
	}

	/**
	 *
	 * @param alphaChanel
	 * @param skin
	 */
	public ColorSampler(boolean alphaChanel, Skin skin){
		super(skin);
		this.skin = skin;
		this.alphaChanel = alphaChanel;
		initalize();
	}

	/**
	 *
	 */
	private void initalize(){

		//
		// Wygenerowanie buttonow
		//
		sliderR = GuiResource.slider(0, 100, 1, "colorDialogSliderR", skin);
		sliderG = GuiResource.slider(0, 100, 1, "colorDialogSliderG", skin);
		sliderB = GuiResource.slider(0, 100, 1, "colorDialogSliderB", skin);
		sliderA = GuiResource.slider(0, 100, 1, "colorDialogSliderA", skin);

		//
		// Fix slider FixScroll
		//
		sliderR.addListener(new InputListener(){
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
				event.stop();
				return false;
			}
		});

		sliderG.addListener(new InputListener(){
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
				event.stop();
				return false;
			}
		});

		sliderB.addListener(new InputListener(){
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
				event.stop();
				return false;
			}
		});

		sliderA.addListener(new InputListener(){
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
				event.stop();
				return false;
			}
		});

		labelTextR = GuiResource.label("R", "colorTextDialogLabelR", skin);
		labelTextG = GuiResource.label("G", "colorTextDialogLabelG", skin);
		labelTextB = GuiResource.label("B", "colorTextDialogLabelB", skin);
		labelTextA = GuiResource.label("A", "colorTextDialogLabelA", skin);

		labelR = GuiResource.label("red", "colorDialogLabelR", skin);
		labelG = GuiResource.label("green", "colorDialogLabelG", skin);
		labelB = GuiResource.label("blue", "colorDialogLabelB", skin);
		labelA = GuiResource.label("alpha", "colorDialogLabelA", skin);

		color = new Color();

		//
		// Tworzenie image textury podgladu
		//
		pixmap = new Pixmap(128, 32, Pixmap.Format.RGBA8888); // Pixmap.Format.RGBA8888);
		pixmap.setColor(1, 1, 1, 1);
		pixmap.fillRectangle(0, 0, 128, 32);
		texture = new Texture(pixmap);
		region = new TextureRegion(texture);
		imageActor = new Image(region);

		//
		// Podpiecie akcji do buttonow i sliderow
		//
		sliderR.addListener(new ChangeListener(){
			@Override
			public void changed(ChangeEvent event, Actor actor){
				float value = sliderR.getValue();
				colorR = value / 100;
				setBtnColorSampleColor();
			}
		});

		sliderG.addListener(new ChangeListener(){
			@Override
			public void changed(ChangeEvent event, Actor actor){
				float value = sliderG.getValue();
				colorG = value / 100;
				setBtnColorSampleColor();
			}
		});

		sliderB.addListener(new ChangeListener(){
			@Override
			public void changed(ChangeEvent event, Actor actor){
				float value = sliderB.getValue();
				colorB = value / 100;
				setBtnColorSampleColor();
			}
		});

		sliderA.addListener(new ChangeListener(){
			@Override
			public void changed(ChangeEvent event, Actor actor){
				float value = sliderA.getValue();
				colorA = value / 100;
				setBtnColorSampleColor();
			}
		});

		add(labelTextR).left().width(10);
		add(sliderR).left().fillX().expandX();
		add(labelR).left().width(20);
		row();
		add(labelTextG).left().width(10);
		add(sliderG).left().fillX().expandX();
		add(labelG).left().width(20);
		row();
		add(labelTextB).left().width(10);
		add(sliderB).left().fillX().expandX();
		add(labelB).left().width(20);

		if (alphaChanel){
			row();
			add(labelTextA).left().width(10);
			add(sliderA).left().fillX().expandX();
			add(labelA).left().width(20);
		}

		row();
		add().left().width(Cfg.gridX1);
		add(imageActor).left().colspan(2);

		//
		// Inicjacja stanów
		//
		if (colorR < 0){
			colorR = 0;
		}
		if (colorR > 1){
			colorR = 1;
		}
		if (colorG < 0){
			colorG = 0;
		}
		if (colorG > 1){
			colorG = 1;
		}
		if (colorB < 0){
			colorB = 0;
		}
		if (colorB > 1){
			colorB = 1;
		}
		if (colorA < 0){
			colorA = 0;
		}
		if (colorA > 1){
			colorA = 1;
		}

		sliderR.setValue(colorR * 100);
		sliderG.setValue(colorG * 100);
		sliderB.setValue(colorB * 100);
		sliderA.setValue(colorA * 100);
		setBtnColorSampleColor();
	}

	/**
	 *
	 * @param color
	 */
	public void setColor(Color color){
		colorR = color.r;
		colorG = color.g;
		colorB = color.b;
		colorA = color.a;
		if (sliderR != null){
			sliderR.setValue(colorR * 100);
			sliderG.setValue(colorG * 100);
			sliderB.setValue(colorB * 100);
			sliderA.setValue(colorA * 100);
			setBtnColorSampleColor();
		}
	}

	/**
	 * @param r
	 * @param g
	 * @param b
	 */
	public void setColor(float r, float g, float b){
		Color col = new Color(r, g, b, 1);
		setColor(col);
	}

	/**
	 * @param r
	 * @param g
	 * @param b
	 * @param a
	 */
	public void setColor(float r, float g, float b, float a){
		Color col = new Color(r, g, b, a);
		setColor(col);
	}

	/**
	 *
	 * @return
	 */
	public Color getColor(){
		return color;
	}

	/**
	 *
	 */
	public void setBtnColorSampleColor(){
		imageActor.setColor(colorR, colorG, colorB, colorA);
		color.set(colorR, colorG, colorB, colorA);

		if (labelR != null){
			labelR.setText(Float.toString(colorR * 100));
			labelG.setText(Float.toString(colorG * 100));
			labelB.setText(Float.toString(colorB * 100));
			labelA.setText(Float.toString(colorA * 100));
		}

		if (listener != null){
			listener.changed(new ChangeListener.ChangeEvent(), this);
		}
	}

	public boolean addListener(ChangeListener listener){
		this.listener = listener;
		return true;
	}


}
