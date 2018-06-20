package com.tianque.weixin;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.tianque.wx.util.WeixinUtil;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml", "classpath:applicationContext-redis.xml" })
public class WeiXinTest {

	@Autowired
	private WeixinUtil weixinUtil;


	public void testCase() throws ExecutionException, InterruptedException {
		System.err.println(weixinUtil.getUserInfo("oKjvv0frzxiT26JUI7s3AwV2tDJA"));

	}




	private LoadingCache<String, String> cache = CacheBuilder.newBuilder()
			.maximumSize(100)
			.refreshAfterWrite(7200, TimeUnit.SECONDS)
			.build(new CacheLoader<String, String>() {
				@Override
				public String load(String key) throws Exception {

					System.out.println("from cache");
					return "123123";
				}
			});

	@Test
	public void testCache() throws ExecutionException {

		System.out.println(cache.get(""));
	}


}
