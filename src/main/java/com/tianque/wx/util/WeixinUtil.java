package com.tianque.wx.util;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.gson.Gson;
import com.tianque.exception.WeiXinException;
import com.tianque.util.HttpUtil;
import com.tianque.wx.WxErrorCode;
import com.tianque.wx.menu.Menu;
import com.tianque.wx.pojo.WeixinUserInfo;

/**
 * 类名: WeixinUtil </br>
 * 包名： com.souvc.weixin.util 描述: 公众平台通用接口工具类 </br>
 */
@Named
public class WeixinUtil {

	private static Logger logger = LoggerFactory.getLogger(WeixinUtil.class);

	@Value("${wx.appID}")
	private String appID;
	@Value("${wx.appSecret}")
	private String appSecret;

	// 获取access_token的接口地址（GET） 限2000（次/天）
	private final String ACCESS_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid={0}&secret={1}";
	// 菜单创建（POST） 限1000（次/天）
	public static String MENU_CREATE_URL = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token={0}";

	// 获取微信用户信息
	public static String USER_INFO_URL = "https://api.weixin.qq.com/cgi-bin/user/info?access_token={0}&openid={1}";

	// 缓存token
	private LoadingCache<String, String> accessTokenCache = CacheBuilder.newBuilder()
			.maximumSize(100)
			.refreshAfterWrite(7200, TimeUnit.SECONDS)
			.build(new CacheLoader<String, String>() {
				@SuppressWarnings("unchecked")
				@Override
				public String load(String key) throws Exception {
					logger.info("从微信服务器中获取AccessToken");
					String url = MessageFormat.format(ACCESS_TOKEN_URL, appID, appSecret);
					String result = HttpUtil.getRequestText(url);
					Map<String, Object> map = new Gson().fromJson(HttpUtil.getRequestText(url), HashMap.class);
					if (map == null || map.containsKey("errcode")) {
						throw new WeiXinException(
								"获取AccessToken异常：" + WxErrorCode.ERRORCODEMAP.get(map.get("errcode")));
					}
					return map.get("access_token").toString();
				}
			});

	/**
	 * 获取access_token
	 */
	public String getAccessToken() {
		try {
			return accessTokenCache.get("");
		} catch (ExecutionException e) {
			throw new WeiXinException("从AccessTokenCache中获取值异常");
		}
	}


	/**
	 * 创建菜单
	 * 
	 * @param menu
	 *            菜单实例
	 * @param accessToken
	 *            有效的access_token
	 * @return 0表示成功，其他值表示失败
	 */
	@SuppressWarnings("unchecked")
	public int createMenu(Menu menu) {
		// 拼装创建菜单的url
		String url = MessageFormat.format(MENU_CREATE_URL, getAccessToken());
		System.err.println(url);
		// 调用接口创建菜单
		String result = HttpUtil.postStringWithRaw(url, new Gson().toJson(menu));
		Map<String, Object> map = new Gson().fromJson(result, HashMap.class);

		if (map == null || (Double) map.get("errcode") != 0) {
			throw new WeiXinException(
					"获取AccessToken异常：" + WxErrorCode.ERRORCODEMAP.get(map.get("errcode")));
		}

		return 0;
	}

	/**
	 * 获取用户信息
	 * @param openId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public  WeixinUserInfo getUserInfo(String openId) {
		WeixinUserInfo weixinUserInfo = null;
		String url = MessageFormat.format(USER_INFO_URL, getAccessToken(),openId);
		String result = HttpUtil.getRequestText(url);
		Map<String, Object> map = new Gson().fromJson(result, HashMap.class);

		if (map == null || map.containsKey("errcode")) {
			throw new WeiXinException(
					"获取用户信息异常：" + WxErrorCode.ERRORCODEMAP.get(map.get("errcode")));
		}

		try {
			weixinUserInfo = new Gson().fromJson(result, WeixinUserInfo.class);
		} catch (Exception e) {
			if (0 == weixinUserInfo.getSubscribe()) {
				logger.error("用户{}已取消关注", weixinUserInfo.getOpenid());
			}
		}
		return weixinUserInfo;
	}



}