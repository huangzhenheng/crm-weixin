package com.tianque.redis;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.tianque.pojo.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml", "classpath:applicationContext-redis.xml" })
public class JsonTestCase {
	@Autowired
	RedisTemplate<String, User> redisTemplate;

	@Before
	public void setUp() {
		// key 的序列化方式
		redisTemplate.setKeySerializer(new StringRedisSerializer());
		redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<User>(User.class));
	}

	// @Test
	public void testSet() {

		User user = new User("zhangsan", "12121", "2424");
		redisTemplate.opsForValue().set("user:101", user);

	}

	@Test
	public void testGet() {
		User user = redisTemplate.opsForValue().get("user:101");
		System.out.println(user.getUsername());
	}
}
