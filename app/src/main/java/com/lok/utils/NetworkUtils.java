package com.lok.utils;

public class NetworkUtils {

	// 16进制转字节
	public static byte[] hexStringToBytes(String hexString) {
		char[] hex = hexString.toCharArray();
		int length = hex.length / 2;
		byte[] rawData = new byte[length];

		for (int i = 0; i < length; i++) {
			int high = Character.digit(hex[i * 2], 16);
			int low = Character.digit(hex[i * 2 + 1], 16);
			int value = (high << 4) | low;
			if (value > 127)
				value -= 256;
			rawData[i] = (byte) value;
		}
		return rawData;
	}

	public static byte[] hex2Byte(String str) {
		byte[] bytes = new byte[str.length() / 2];
		for (int i = 0; i < bytes.length; i++) {
			bytes[i] = (byte) Integer.parseInt(str.substring(2 * i, 2 * i + 2),
					16);
		}
		return bytes;
	}
	
	public static String uriSetting(String ipAddress) {
		StringBuilder builder = new StringBuilder();
		builder.append("http://");
		builder.append(ipAddress);
		return builder.toString();
	}

}
