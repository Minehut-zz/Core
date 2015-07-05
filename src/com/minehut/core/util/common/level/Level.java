package com.minehut.core.util.common.level;

import com.minehut.core.util.common.chat.C;

/**
 * Created by Luke on 3/16/15.
 */
public class Level {
	public static String getLevelColor(int i) {
		if (i < 10) {
			return C.gray;
		} else if (i < 20) {
			return C.white;
		} else if (i < 30) {
			return C.yellow;
		} else if (i < 50) {
			return C.blue;
		} else if (i < 70) {
			return C.dgreen;
		} else if (i < 100) {
			return C.green;
		} else if (i < 130) {
			return C.aqua;
		} else if (i < 160) {
			return C.gold;
		} else if (i < 200) {
			return C.dred;
		} else {
			return C.red;
		}
	}
}
