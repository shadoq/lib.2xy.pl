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

package pl.lib2xy.utils;

import com.badlogic.gdx.utils.Base64Coder;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * Class to encode/decode text using XOR operations
 */
public class XorEncrypter{

	public static final String DEFAULT_ENCODING = "UTF-8";
	// Encoding text
	public static String hash = "S)9asd-0ikxz-0x{AASIPOsxia:LSKx0s0X)_SXklas;kxas0_)SA(Xks;xlaskx-12=we)S_X+_)A+_csackasl;ck";

	private XorEncrypter(){
	}

	public static String base64encode(String text){
		try {
			String rez = new String(Base64Coder.encode(text.getBytes(DEFAULT_ENCODING)));
			return rez;
		} catch (UnsupportedEncodingException e){
			return null;
		}
	}

	public static String base64decode(String text){

		try {
			return new String(Base64Coder.decode(text), DEFAULT_ENCODING);
		} catch (IOException e){
			return null;
		}
	}

	public static String encode(String text){
		return xorMessage(text, hash);
	}

	public static String xorMessage(String message, String key){
		try {
			if (message == null || key == null){
				return null;
			}

			char[] keys = key.toCharArray();
			char[] mesg = message.toCharArray();

			int ml = mesg.length;
			int kl = keys.length;
			char[] newmsg = new char[ml];

			for (int i = 0; i < ml; i++){
				newmsg[i] = (char) (mesg[i] ^ keys[i % kl]);
			}
			return new String(newmsg);
		} catch (Exception e){
			return null;
		}
	}

}