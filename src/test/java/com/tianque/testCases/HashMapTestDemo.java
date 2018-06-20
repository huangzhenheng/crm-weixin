package com.tianque.testCases;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.junit.Test;

public class HashMapTestDemo {
	public static HashMap<String, String> hashMap = new HashMap<>();
	public static HashMap<String, String> hashMap1 = new HashMap<>(10000);
	private static ConcurrentMap<String, String> concurrentMap = new ConcurrentHashMap<>();


	@Test
	public void TestCase() throws InterruptedException {

		testMap(hashMap, "普通的hashMap");
		// 同步安全方法1，
		testMap(Collections.synchronizedMap(hashMap), "加锁的hashMap");
		testMap(concurrentMap, "线程安全带的ConcurrentHashMap");
		testMap(hashMap, "设置初始容量的hashMap");
	}

	public void testMap(final Map<String, String> map,String msg) throws InterruptedException {

		// 线程一
		Thread t1 = new Thread() {
			@Override
			public void run() {
				for (int i = 0; i < 25; i++) {
					map.put(String.valueOf(i), String.valueOf(i));
				}
			}
		};

		// 线程二
		Thread t2 = new Thread() {
			@Override
			public void run() {
				for (int j = 25; j < 50; j++) {
					map.put(String.valueOf(j), String.valueOf(j));
				}
			}
		};

		// 线程三
		Thread t3 = new Thread() {
			@Override
			public void run() {
				for (int m = 50; m < 75; m++) {
					map.put(String.valueOf(m), String.valueOf(m));
				}
			}
		};
		// 线程四
		Thread t4 = new Thread() {
			@Override
			public void run() {
				for (int n = 75; n < 100; n++) {
					map.put(String.valueOf(n), String.valueOf(n));
				}
			}
		};

		t1.start();
		t2.start();
		t3.start();
		t4.start();

		Thread.sleep(4000);

		for (int k = 0; k < 100; k++) {
			// 如果key和value不同，说明线程put的过程中出现异常。
			if (!String.valueOf(k).equals(map.get(String.valueOf(k)))) {
				System.err.println(msg + ":" + map.getClass().getName() + "->" + k + ":" + map.get(String.valueOf(k)));
			}
		}

	}
}
