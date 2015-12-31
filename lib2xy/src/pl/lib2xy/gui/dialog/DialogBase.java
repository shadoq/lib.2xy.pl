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

package pl.lib2xy.gui.dialog;

import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import pl.lib2xy.gui.GuiResource;
import pl.lib2xy.gui.widget.WidgetInterface;

/**
 * @author Jarek
 */
public class DialogBase implements WidgetInterface{

	protected float fadeDuration = 0.2f;
	protected Window mainWindow;

	public void create(){
		mainWindow = GuiResource.windowDialog("WidgetBase", "WidgetBase");
		mainWindow.setColor(1f, 1f, 1f, 0f);
		mainWindow.setVisible(false);
		mainWindow.setModal(true);
		mainWindow.setMovable(false);
	}

	@Override
	public void show(){
		mainWindow.setVisible(true);
		if (fadeDuration > 0){
			mainWindow.addAction(Actions.fadeIn(fadeDuration, Interpolation.fade));
		}
	}

	@Override
	public void hide(){
		mainWindow.addAction(Actions.sequence(Actions.fadeOut(fadeDuration, Interpolation.fade), Actions.removeActor()));
	}
}
