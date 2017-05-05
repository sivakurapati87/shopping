package com.osc.config;


public class Example {
	public static void main(String[] args) {
		String s="sivakurapatiisok now siva";
		for(int i=0;i<s.length();i=i+3){
			System.out.println(s.substring(i,(i+3)>s.length()?s.length():(i+3)));
		}
	}
}
