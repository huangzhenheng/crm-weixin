package com.tianque.redis;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.google.gson.Gson;
import com.tianque.pojo.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml", "classpath:applicationContext-redis.xml" })
public class SpringRedisTestCase {
	@Autowired
	private RedisTemplate<String, String> redisTemplate;

	@Test
	public void testSet() {
		User user = new User();
		user.setId(1);
		user.setUsername("tom");
		user.setTel("121111111");
		Gson gson = new Gson();
		gson.toJson(user);
		ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
		valueOperations.set("myspring_data", "myspring_data");
	}

	@Test
	public void testGet() {
		
		System.out.println(redisTemplate.keys("*"));
		System.out.println(redisTemplate.opsForSet().distinctRandomMembers("user:2", 1));
	}

	// @Test
	public void testIncr() {

		redisTemplate.opsForValue().increment("post:4:viewnum", 1L);
		System.out.println(redisTemplate.opsForValue().get("post:4:viewnum"));
	}


}
