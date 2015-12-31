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

package pl.lib2xy.debug.gui;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import pl.lib2xy.app.ResourceManager;

public class BorderExpandFrom extends BorderFrom{

	protected Image expandButton;

	public BorderExpandFrom(Skin skin){
		super(skin);
		setupExpand();
	}

	public BorderExpandFrom(Skin skin, Actor actor){
		super(skin, actor);
		setupExpand();
	}

	public BorderExpandFrom(Skin skin, Actor actor, String header, boolean scroll){
		super(skin, actor, header, scroll, 0, 0);
		setupExpand();
	}

	private void setupExpand(){

		expandButton = ResourceManager.getIconFromClass("sm_expand");
		expandButton.setOrigin(getWidth() / 2, getHeight() / 2);
		expandButton.setRotation(180);
		expandButton.setX(getWidth() - expandButton.getWidth() - 2.0F);
		expandButton.setY(getHeight() - expandButton.getHeight() - 1.0F);

		//		headerTable.add(expandButton).right().expandX();
		//		headerTable.invalidate();

		expandButton.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y){
				if ((int) expandButton.getRotation() == 0){
					expandButton.setRotation(180);
					mainTable.setVisible(true);
					iconTable.setVisible(true);
					iconTable2.setVisible(true);
					iconTable3.setVisible(true);
				} else {
					expandButton.setRotation(0);
					mainTable.setVisible(false);
					iconTable.setVisible(false);
					iconTable2.setVisible(false);
					iconTable3.setVisible(false);
				}

				mainTable.pack();
				mainTable.invalidate();

				pack();
				layout();
				invalidateHierarchy();
				invalidate();
			}
		});
	}
}
