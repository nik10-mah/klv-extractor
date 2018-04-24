package com.insonix.klv.utils;

/**
 * Copyright (c) Codice Foundation
 *
 * <p>
 * This is free software: you can redistribute it and/or modify it under the
 * terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or any later version.
 *
 * <p>
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details. A copy of the GNU Lesser General Public License is distributed along
 * with this program and can be found at
 * <http://www.gnu.org/licenses/lgpl.html>.
 */

public class Utilities {

	public static byte[] longToBytes(long value) {
		int size = 8;

		byte[] bytes = new byte[size];

		for (int i = 0; i < size; i++) {
			long mask = 0xFFL << (8 * i);
			bytes[size - 1 - i] = (byte) ((value & mask) >> (8 * i));
		}
		return bytes;
	}

	public static byte[] intToBytes(int value) {
		int size = 4;

		byte[] bytes = new byte[size];

		for (int i = 0; i < size; i++) {
			int mask = 0xFF << (8 * i);
			bytes[size - 1 - i] = (byte) ((value & mask) >> (8 * i));
		}
		return bytes;
	}

	public static byte[] shortToBytes(short value) {
		int size = 2;

		byte[] bytes = new byte[size];

		for (int i = 0; i < size; i++) {
			short mask = (short) (0xFF << (8 * i));
			bytes[size - 1 - i] = (byte) ((value & mask) >> (8 * i));
		}
		return bytes;
	}

	public static byte[] hexStringToByteArray(String s) {
		int len = s.length();
		byte[] data = new byte[len / 2];
		for (int i = 0; i < len; i += 2) {
			data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4) + Character.digit(s.charAt(i + 1), 16));
		}
		return data;
	}

	public static byte[] hexToByteArray(String s) {
		String[] split = s.split("\\s");
		byte[] arr = new byte[split.length];
		for (int i = 0; i < split.length; i++) {
			arr[i] = (byte) (short) Short.decode(split[i]);
		}
		return arr;
	}

	private final static char[] hexArray = "0123456789ABCDEF".toCharArray();

	/**
	 * Uses right shift
	 * 
	 * @param bytes
	 * @return
	 */
	public static String bytesToHex(byte[] bytes) {
		char[] hexChars = new char[bytes.length * 2];
		for (int j = 0; j < bytes.length; j++) {
			int v = bytes[j] & 0xFF;
			hexChars[j * 2] = hexArray[v >>> 4];
			hexChars[j * 2 + 1] = hexArray[v & 0x0F];
		}
		return new String(hexChars);
	}

	/**
	 * User String.format("%02X ", b)
	 * 
	 * @param bytes
	 * @return Hex String
	 */
	public static StringBuilder bytesToHexString(byte[] bytes) {

		StringBuilder sb = new StringBuilder();
		for (byte b : bytes) {
			sb.append(String.format("%02X ", b));
		}

		return sb;
	}
}
