package com.osc.config;

import java.io.IOException;
import java.text.DecimalFormat;


public class Example {
	public static void main(String[] args) {
		DecimalFormat df = new DecimalFormat("####0.00");
		System.out.println("Value: " + df.format(2.1532432));
	}
}

class X{
	void display()throws IOException{
		System.out.println("Super");
	}
}
class Y extends X{
	 void display()throws IOException{
		
	}
}

