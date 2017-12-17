package com.app.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommonUtil {

	public static String replaceSpecialChars(String str) {
		Pattern pt = Pattern.compile("[^a-zA-Z0-9]");
		Matcher match = pt.matcher(str);
		while (match.find()) {
			String s = match.group();
			str = str.replaceAll("\\" + s, "");
		}
		return str.trim();
	}
	
	public static String increaseDig(int number, int digits) {
		return String.format("%0"+digits+"d", number);
	}
}