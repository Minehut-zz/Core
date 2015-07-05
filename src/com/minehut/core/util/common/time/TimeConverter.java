package com.minehut.core.util.common.time;

import com.minehut.core.util.common.chat.F;

/**
 * Created by Luke on 3/18/15.
 */
public class TimeConverter {
	public static int convert(String s) {
		if (s.contains("s")) {
			s = s.replace("s", "");
			return Integer.parseInt(s);
		} else if (s.contains("m")) {
			s = s.replace("m", "");
			return Integer.parseInt(s) * 60;
		} else if (s.contains("h")) {
			s = s.replace("h", "");
			return Integer.parseInt(s) * 3600;
		} else {
			F.log("couldn't convert time from string");
			return 0;
		}
	}
}
