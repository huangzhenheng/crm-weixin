package com.tianque.testCases;

public class Man {
	//static Man man = new Man();

	public Man() {
		System.out.println("1");
	}

	// 非static代码块则在创建对象时被调用，用于对所创建的对象进行初始化。
	{
		System.out.println("2");
	}

	// static代码块在类的初始化阶段被调用，用于对类进行初始化
	static {
		System.out.println("3");
	}

	public static void main(String[] args) {
		new Man();
	}
	// 在进行源码编译的时候，编译器会将构造器，非static代码块，以及非static成员变量(直接写在类中)的赋值语句进行合并，合并的过程如下：
	// 1、将非static成员变量(在类中)的赋值语句都合并到非static代码块中；
	// 具体为：不管是非static成员变量(在类中)的赋值语句，还是非static代码块，按照它们在类中定义的先后顺序进行合并。
	// 2、将合并后的非static代码块合并到构造器中；
	// 具体为：直接将非static代码块中的所有代码插入到构造器的第一行。
	// 同样的，在编译器对源码进行编译时，也会对static成员(在类中)的赋值语句以及static代码块进行代码合并，合并的顺序与他们在类中出现的顺序一致。
	
	//静态成员 与 静态代码块  按出现顺序合并 -》  构造器，非static代码块，以及非static成员变量(直接写在类中)的赋值语句进行合并 
	//直接将非static代码块中的所有代码插入到构造器的第一行
	//
	
}
