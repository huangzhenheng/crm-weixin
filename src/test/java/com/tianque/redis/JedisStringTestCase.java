package com.tianque.redis;

import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class JedisStringTestCase {
	@Autowired
	private JedisPool jedisPool;
	private Jedis jedis = null;

	// @Before
	// public void setUp() {
	// jedis = new Jedis("192.168.75.133");
	// }

	@Before
	public void setUp() {
		jedis = jedisPool.getResource();
	}

	@After
	public void close() {
		if (jedis != null) {
			jedis.close();
		}
	}

	@Test
	public void testSet() {
		jedis.set("jedis-user", "jedis-name");
	}

	@Test
	public void testExistsAndGet() {
		if (jedis.exists("jedis-user")) {
			String value = jedis.get("jedis-user");
			System.out.println(value);
			Assert.assertEquals("jedis-user", value);
		}
	}

	@Test
	public void testIncr() {
		String key = "post:1:viewnum";
		jedis.incr(key);
		System.out.println(jedis.get("post:1:viewnum"));
	}

	@Test
	public void testIncrBy() {
		String key = "post:1:viewnum";
		jedis.incrBy(key, 10);
		System.out.println(jedis.get("post:1:viewnum"));
	}

	@Test
	public void testDecr() {
		String key = "post:1:viewnum";
		jedis.decr(key);
		System.out.println(jedis.get("post:1:viewnum"));
	}

	@Test
	public void testIncrByFloat() {
		String key = "user:1:money";
		System.out.println(jedis.get(key));

		jedis.incrByFloat(key, 4.5F);

		System.out.println(jedis.get(key));
	}

	@Test
	public void testAppend() {
		jedis.append("jedis", "丰");
		System.out.println(jedis.get("jedis"));
	}

	@Test
	public void testStrLen() {
		System.out.println(jedis.strlen("jedis"));
	}

	@Test
	public void testMset() {
		jedis.mset("k1", "v1", "k2", "v2");
	}

	@Test
	public void testMget() {
		List<String> lists = jedis.mget("k1", "k2");
		for (String str : lists) {
			System.out.println(str);
		}
	}

}
