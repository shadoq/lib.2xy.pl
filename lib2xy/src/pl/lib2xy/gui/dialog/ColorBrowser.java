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

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lib2xy.gui.dialog;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import pl.lib2xy.XY;
import pl.lib2xy.app.Lang;
import pl.lib2xy.gui.GuiResource;
import pl.lib2xy.gui.GuiUtil;
import pl.lib2xy.gui.widget.WidgetColorListener;
import pl.lib2xy.gui.widget.WidgetInterface;

/**
 * @author Jarek
 */
public class ColorBrowser extends DialogBase implements WidgetInterface{

	private float colorR = 1;
	private float colorG = 1;
	private float colorB = 1;
	private float colorA = 1;
	public Color color = null;
	private Pixmap pixmap;
	private Texture texture;
	private TextureRegion region;
	private Image imageActor;
	private Slider sliderR;
	private Slider sliderG;
	private Slider sliderB;
	private Slider sliderA;
	private Label labelTextR = null;
	private Label labelTextG = null;
	private Label labelTextB = null;
	private Label labelTextA = null;
	private Label labelR = null;
	private Label labelG = null;
	private Label labelB = null;
	private Label labelA = null;
	private WidgetColorListener listener = null;

	@Override
	public void show(){
		show(false);
	}

	public void show(boolean alphaChanel){

		super.create();

		//
		// Wygenerowanie buttonow
		//
		sliderR = GuiResource.slider(0, 100, 1, "colorDialogSliderR");
		sliderG = GuiResource.slider(0, 100, 1, "colorDialogSliderG");
		sliderB = GuiResource.slider(0, 100, 1, "colorDialogSliderB");
		sliderA = GuiResource.slider(0, 100, 1, "colorDialogSliderA");

		labelTextR = GuiResource.label("R", "colorTextDialogLabelR");
		labelTextG = GuiResource.label("G", "colorTextDialogLabelG");
		labelTextB = GuiResource.label("B", "colorTextDialogLabelB");
		labelTextA = GuiResource.label("A", "colorTextDialogLabelA");

		labelR = GuiResource.label("red", "colorDialogLabelR");
		labelG = GuiResource.label("green", "colorDialogLabelG");
		labelB = GuiResource.label("blue", "colorDialogLabelB");
		labelA = GuiResource.label("alpha", "colorDialogLabelA");
		final Button buttonOk = GuiResource.textButton("Ok", "colorDialogButtonOk");

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
		buttonOk.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y){
				hide();
				if (listener != null){
					listener.Action(color);
				}
			}
		});

		sliderR.addListener(new ChangeListener(){
			@Override
			public void changed(ChangeListener.ChangeEvent event, Actor actor){
				float value = sliderR.getValue();
				colorR = value / 100;
				setBtnColorSampleColor();
			}
		});

		sliderG.addListener(new ChangeListener(){
			@Override
			public void changed(ChangeListener.ChangeEvent event, Actor actor){
				float value = sliderR.getValue();
				colorG = value / 100;
				setBtnColorSampleColor();
			}
		});

		sliderB.addListener(new ChangeListener(){
			@Override
			public void changed(ChangeListener.ChangeEvent event, Actor actor){
				float value = sliderB.getValue();
				colorB = value / 100;
				setBtnColorSampleColor();
			}
		});

		sliderA.addListener(new ChangeListener(){
			@Override
			public void changed(ChangeListener.ChangeEvent event, Actor actor){
				float value = sliderA.getValue();
				colorA = value / 100;
				setBtnColorSampleColor();
			}
		});

		//
		// Utworzenie okna
		//
		mainWindow.getTitleLabel().setText(Lang.get("colorBrowser"));
		mainWindow.row().fill().expandX();
		mainWindow.add(labelTextR);
		mainWindow.add(sliderR);
		mainWindow.add(labelR).left();
		mainWindow.row();
		mainWindow.add(labelTextG);
		mainWindow.add(sliderG);
		mainWindow.add(labelG).left();
		mainWindow.row();
		mainWindow.add(labelTextB);
		mainWindow.add(sliderB);
		mainWindow.add(labelB).left();

		if (alphaChanel){
			mainWindow.row();
			mainWindow.add(labelTextA);
			mainWindow.add(sliderA);
			mainWindow.add(labelA).left();
		}

		mainWindow.row();
		mainWindow.add(imageActor).align(Align.left).colspan(2);
		mainWindow.add(buttonOk).fillX().minWidth(50);

		mainWindow.pack();
		mainWindow.setModal(true);

		GuiUtil.windowPosition(mainWindow, 12, 12);
		XY.stage.addActor(mainWindow);

		//
		// Inicjacja stanów
		//
		sliderR.setValue(colorR * 100);
		sliderG.setValue(colorG * 100);
		sliderB.setValue(colorB * 100);
		sliderA.setValue(colorA * 100);
		setBtnColorSampleColor();

		super.show();
	}

	/**
	 * Zamkniecie okna i zwrocenie imputu
	 */
	@Override
	public void hide(){
		super.hide();
	}

	/**
	 * @param color
	 */
	public void set(Color color){
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
	public void set(float r, float g, float b){
		Color col = new Color(r, g, b, 1);
		set(col);
	}

	/**
	 * @param r
	 * @param g
	 * @param b
	 * @param a
	 */
	public void set(float r, float g, float b, float a){
		Color col = new Color(r, g, b, a);
		set(col);
	}

	public Color get(){
		return color;
	}

	public void setColorListener(WidgetColorListener colorListener){
		listener = colorListener;
	}

	/**
	 *
	 */
	private void setBtnColorSampleColor(){
		imageActor.setColor(colorR, colorG, colorB, colorA);
		color.set(colorR, colorG, colorB, colorA);

		if (labelR != null){
			labelR.setText(Float.toString(colorR * 100));
			labelG.setText(Float.toString(colorG * 100));
			labelB.setText(Float.toString(colorB * 100));
			labelA.setText(Float.toString(colorA * 100));
		}
		if (listener != null){
			listener.Action(color);
		}
	}
}
