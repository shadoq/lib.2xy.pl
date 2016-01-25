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

package pl.lib2xy.items;

public class SliderVO extends ActorVO{

	public float value = 0;
	public float min = 0;
	public float max = 100;
	public float step = 1;

	public boolean vertical = false;

	public SliderVO(){
	}

	public SliderVO(SliderVO sliderVO){
		super(sliderVO);
		value = sliderVO.value;
		min = sliderVO.min;
		max = sliderVO.max;
		step = sliderVO.step;

		vertical = sliderVO.vertical;
	}
}
