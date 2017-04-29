package com.osc.config;


public class Example {
	public static void main(String[] args) {
		String s = "siva kurapati";
		System.out.println(s.getBytes());
		for(byte b:s.getBytes()){
			System.out.println(b);
		}
		System.out.println(Character.toChars(105));
	}
}
