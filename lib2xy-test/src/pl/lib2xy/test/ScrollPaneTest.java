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

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import pl.lib2xy.XY;
import pl.lib2xy.app.Cfg;
import pl.lib2xy.app.Scene;

public class ScrollPaneTest extends Scene{

	private Table container;

	@Override
	public void initialize(){

		container = new Table();

		Table table = new Table();

		final ScrollPane scroll = new ScrollPane(table, XY.skin);

		InputListener stopTouchDown = new InputListener(){
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
				event.stop();
				return false;
			}
		};

		table.pad(10).defaults().expandX().space(4);
		for (int i = 0; i < 100; i++){
			table.row();
			table.add(new Label(i + "uno", XY.skin)).expandX().fillX();

			TextButton button = new TextButton(i + "dos", XY.skin);
			table.add(button);
			button.addListener(new ClickListener(){
				@Override
				public void clicked(InputEvent event, float x, float y){
					System.out.println("click " + x + ", " + y);
				}
			});

			Slider slider = new Slider(0, 100, 1, false, XY.skin);
			slider.addListener(stopTouchDown); // Stops touchDown events from propagating to the FlickScrollPane.
			table.add(slider);

			table.add(new Label(
			i + "tres long0 long1 long2 long3 long4 long5 long6 long7 long8 long9 long10 long11 long12 long13 long14 long15 long16 long17 long10 long11 long12 long13 long14 long15 long16 long17",
			XY.skin));
		}

		final TextButton flickButton = new TextButton("Flick Scroll", XY.skin.get("toggle", TextButton.TextButtonStyle.class));
		flickButton.setChecked(true);
		flickButton.addListener(new ChangeListener(){
			@Override
			public void changed(ChangeListener.ChangeEvent event, Actor actor){
				scroll.setFlickScroll(flickButton.isChecked());
			}
		});

		final TextButton fadeButton = new TextButton("Fade Scrollbars", XY.skin.get("toggle", TextButton.TextButtonStyle.class));
		fadeButton.setChecked(true);
		fadeButton.addListener(new ChangeListener(){
			@Override
			public void changed(ChangeListener.ChangeEvent event, Actor actor){
				scroll.setFadeScrollBars(fadeButton.isChecked());
			}
		});

		final TextButton smoothButton = new TextButton("Smooth Scrolling", XY.skin.get("toggle", TextButton.TextButtonStyle.class));
		smoothButton.setChecked(true);
		smoothButton.addListener(new ChangeListener(){
			@Override
			public void changed(ChangeListener.ChangeEvent event, Actor actor){
				scroll.setSmoothScrolling(smoothButton.isChecked());
			}
		});

		final TextButton onTopButton = new TextButton("Scrollbars On Top", XY.skin.get("toggle", TextButton.TextButtonStyle.class));
		onTopButton.addListener(new ChangeListener(){
			@Override
			public void changed(ChangeListener.ChangeEvent event, Actor actor){
				scroll.setScrollbarsOnTop(onTopButton.isChecked());
			}
		});

		container.add(scroll).expand().fill().colspan(4);
		container.row().space(10).padBottom(10);
		container.add(flickButton).right().expandX();
		container.add(onTopButton);
		container.add(smoothButton);
		container.add(fadeButton).left().expandX();

		container.setWidth(Cfg.gridX24);
		container.setHeight(Cfg.gridY24);
		addActor(container);
	}
}
