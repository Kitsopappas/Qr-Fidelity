package com.qrlab.qrfidelity.logic;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class QrFidelityReaderF {

	public String getSSID(String str) {
		//String str = "<ssid>Kitsopappas</ssid> <pass>7Zdd31s0</pass>";
		final Pattern pattern = Pattern.compile("<ssid>(.+?)</ssid>");
		final Matcher matcher = pattern.matcher(str);
		matcher.find();
		return matcher.group(1);

	}
	
	public String getPass(String str) {
		//String str = "<ssid> Kitsopappas </ssid> <pass>7Zdd31s0</pass>";
		final Pattern pattern = Pattern.compile("<pass>(.+?)</pass>");
		final Matcher matcher = pattern.matcher(str);
		matcher.find();
		return matcher.group(1);

	}

}
