package com.qrlab.qrfidelity.logic;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class QrFidelityReaderF {
	public static int USER = 1;
	public static int SSID = 2;
	public static int PASS = 3;
	public String getStuff(String str,int group) {
		//String str = "<ssid>Kitsopappas</ssid> <pass>7Zdd31s0</pass>";
		final Pattern pattern = Pattern.compile("<u>(.+?)</u><s>(.+?)</s><p>(.+?)</p>");
		final Matcher matcher = pattern.matcher(str);
		matcher.find();
		return matcher.group(group);

	}
	
}
