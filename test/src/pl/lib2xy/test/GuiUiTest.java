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

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.utils.Align;
import pl.lib2xy.XY;
import pl.lib2xy.app.Scene;
import pl.lib2xy.gui.GuiResource;
import pl.lib2xy.gui.GuiUtil;
import pl.lib2xy.gui.ui.*;

/**
 * @author Jarek
 */
public class GuiUiTest extends Scene{

	private List list = null;

	@Override
	public void initialize(){

		Window window = GuiResource.window("Basic UI elements", "basicUI");
		window.defaults().align(Align.top | Align.left).padLeft(5);
		window.setMovable(false);

		window.row();
		window.add("ImageButton:").left();
		window.add(new ImageButton("ui_x1/icon_img_colorize.png", XY.skin));
		window.add(new ImageButton("ui_x1/icon_img_camera_alt.png", XY.skin));
		window.add(new ImageButton("ui_x1/icon_zoom_in.png", XY.skin));
		window.add(new ImageButton("ui_x1/icon_zoom_out.png", XY.skin));
		window.add(new ImageButton("ui_x1/icon_vertical_align_center.png", XY.skin));
		window.add(new ImageButton("ui_x1/icon_format_align_center.png", XY.skin));
		window.row();
		window.add("IB Toggle").left();
		window.add(new ImageButton("ui_x1/icon_img_colorize.png", XY.skin, "toggle"));
		window.add(new ImageButton("ui_x1/icon_img_camera_alt.png", XY.skin, "toggle"));
		window.add(new ImageButton("ui_x1/icon_zoom_in.png", XY.skin, "toggle"));
		window.add(new ImageButton("ui_x1/icon_zoom_out.png", XY.skin, "toggle"));
		window.add(new ImageButton("ui_x1/icon_vertical_align_center.png", XY.skin, "toggle"));
		window.add(new ImageButton("ui_x1/icon_format_align_center.png", XY.skin, "toggle"));
		window.row();

		window.add("Label:").left();
		window.add(new Label("Label", XY.skin)).colspan(2).left();
		window.add(new Label("Label 2", XY.skin)).colspan(2).left();
		window.add(new Label("Label 3", XY.skin)).colspan(2).left();
		window.row();

		window.add("L. colors").left();
		window.add(new Label("Label", XY.skin, Color.RED)).colspan(2).left();
		window.add(new Label("Label 2", XY.skin, Color.BLUE)).colspan(2).left();
		window.add(new Label("Label 3", XY.skin, Color.PINK)).colspan(2).left();
		window.row();

		window.add("L. style").left();
		window.add(new Label("default", XY.skin, "default")).colspan(2).left();
		window.add(new Label("default-light", XY.skin, "default-light")).colspan(2).left();
		window.add(new Label("default-opaque", XY.skin, "default-opaque")).colspan(2).left();
		window.row();

		window.add("L. style color").left();
		window.add(new Label("default", XY.skin, "default", Color.RED)).colspan(2).left();
		window.add(new Label("default-light", XY.skin, "default-light", Color.PINK)).colspan(2).left();
		window.add(new Label("default-opaque", XY.skin, "default-opaque", Color.ORANGE)).colspan(2).left();
		window.row();

		window.add("TextButton").left();
		window.add(new TextButton("default", XY.skin, "default")).colspan(2).left();
		window.add(new TextButton("default-light", XY.skin, "default-light")).colspan(2).left();
		window.add(new TextButton("noborder", XY.skin, "noborder")).colspan(2).left();
		window.row();

		window.add("").left();
		window.add(new TextButton("toggle", XY.skin, "toggle")).colspan(2).left();
		window.add(new TextButton("toggle-light", XY.skin, "toggle-light")).colspan(2).left();
		window.add(new TextButton("tab", XY.skin, "tab")).colspan(2).left();
		window.row();

		window.add("Image").left();
		window.add(new Image("texture/def32.png")).colspan(2).left().width(64).height(64);
		window.add(new Image("ui_x1/icon_zoom_in.png")).colspan(2).left().width(64).height(64);
		window.add(new Image("texture/def256.png")).colspan(2).left().width(64).height(64);
		window.row();

		window.add("Button").left();
		window.add(new Button(XY.skin)).colspan(2).left();
		window.add(new Button(XY.skin)).colspan(2).left();
		window.add(new Button(XY.skin)).colspan(2).left();
		window.row();

		window.add("CheckBox").left();
		window.add(new CheckBox("CheckBox 1", XY.skin)).colspan(3).left();
		window.add(new CheckBox("default-light", XY.skin, "default-light")).colspan(2).left();
		window.row();

		window.add("CheckBox").left();
		window.add(new CheckBox("default-radio", XY.skin, "default-radio")).colspan(3).left();
		window.add(new CheckBox("default-light-radio", XY.skin, "default-light-radio")).colspan(3).left();
		window.row();

		window.add("Image Text Button").left();
		window.add(new ImageTextButton("default", "texture/def32.png", XY.skin)).left().colspan(3);
		window.add(new ImageTextButton("toggle", "texture/def32.png", XY.skin, "toggle")).left().colspan(3);
		window.row();

		GuiUtil.windowPosition(window, 0, 0);
		addActor(window);

		final Slider slider = GuiResource.slider(0, 10, 1, "slider");
		final SelectBox dropdown = GuiResource.selectBox(new String[]{"Android", "Windows", "Linux", "OSX"}, "combo");

		Window window2 = GuiResource.window("Basic UI elements", "basicUI");
		window2.defaults().align(Align.top | Align.left).padLeft(5);
		window2.setMovable(false);

		window2.add("Slider").left();
		window2.row();
		window2.add(slider).left();
		window2.row();
		window2.add("DropDown").left();
		window2.row();
		window2.add(dropdown).left();
		window2.row();

		window2.add("Label Style").left();
		window2.row();
		window2.add(new Label("default-light-opaque", XY.skin, "default-light-opaque")).left();
		window2.row();
		window2.add(new Label("default-title", XY.skin, "default-title")).left();
		window2.row();

		window2.add("Text Button").left();
		window2.row();
		window2.add(new TextButton("default-title", XY.skin, "default-title")).left();
		window2.row();
		window2.add(new TextButton("toggle-title", XY.skin, "toggle-title")).left();
		window2.row();

		window2.add("TextArea").left();
		window2.row();
		window2.add(new TextArea("TextArea", XY.skin)).left().expandX().fillX().height(50);
		window2.row();

		window2.add("TextField").left();
		window2.row();
		window2.add(new TextField("TextField", XY.skin)).left().expandX().fillX();
		window2.row();

		GuiUtil.windowPosition(window2, 13, 0);
		addActor(window2);
	}
}
