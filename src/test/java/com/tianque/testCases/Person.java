package com.tianque.testCases;

public class Person {
	static Person person;

	public Person() {
		System.out.println("2");
		System.out.println("1");
	}

	static {
		person = new Person();
		System.out.println("3");
	}


}
