package com.tianque.testCases;

public class Tiger {
	static B1 t = new B1();

	public Tiger() {
		System.out.println("1");
	}

	{
		System.out.println("2");
	}
	static {
		System.out.println("3");
	}
}
