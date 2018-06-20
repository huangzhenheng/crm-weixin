package com.tianque.testCases;

import java.util.List;

import org.junit.Test;

import com.google.common.collect.Lists;
import com.tianque.pojo.User;

public class ListTestDemo {

	private List<User> list_one = Lists.newArrayList();
	private List<User> list_two = Lists.newArrayList();

	@Test
	public void testCase() {
		User user = new User();
		list_one.add(user);
		list_two.add(user);

		for (User u1 : list_one) {
			u1.setRealname("张三");
		}
		for (User u2 : list_one) {
			System.out.println(u2.getRealname());
		}

	}

	// 设置list_one中元素的value，但是list_two中元素的value也发生了改变，证明集合中存放的是对象的引用。

}
