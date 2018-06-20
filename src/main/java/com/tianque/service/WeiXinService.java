package com.tianque.service;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.tianque.exception.WeiXinException;
import com.tianque.pojo.User;
import com.tianque.util.HttpUtil;
import com.tianque.util.WeiXinErrorCode;

@Named
@Transactional
public class WeiXinService{

    private static Logger logger = LoggerFactory.getLogger(WeiXinService.class);

    @Value("${wx.corpid}")
    private String corpid;
    @Value("${wx.secret}")
    private String secret;
	@Value("${wx.phoneBookSecret}")
	private String phoneBookSecret;
    private final String ACCESS_TOKEN_URL = "https://qyapi.weixin.qq.com/cgi-bin/gettoken?corpid={0}&corpsecret={1}";
    private final String CREATE_USER_URL = "https://qyapi.weixin.qq.com/cgi-bin/user/create?access_token={0}";
    private final String DEL_USER_URL = "https://qyapi.weixin.qq.com/cgi-bin/user/delete?access_token={0}&userid={1}";
    private final String USE_CODE_GET_USERID = "https://qyapi.weixin.qq.com/cgi-bin/user/getuserinfo?access_token={0}&code={1}";

	// LoadingCache guava缓存

	private LoadingCache<String, String> accessTokenCache = CacheBuilder.newBuilder()
			.maximumSize(100)
			.refreshAfterWrite(7200, TimeUnit.SECONDS)
			.build(new CacheLoader<String, String>() {
				@Override
				public String load(String key) throws Exception {
					logger.info("从微信服务器中获取AccessToken");

					String url = MessageFormat.format(ACCESS_TOKEN_URL, corpid, secret);
					String result = HttpUtil.getRequestText(url);
					Gson gson = new Gson();
					Map<String, Object> map = gson.fromJson(result, HashMap.class);

					if (map == null || (Double) map.get("errcode") != 0) {
						throw new WeiXinException(
								"获取AccessToken异常：" + WeiXinErrorCode.ERRORCODEMAP.get(map.get("errcode")));
                    }
					return map.get("access_token").toString();
				}
			});

	private LoadingCache<String, String> phoneBookAccessTokenCache = CacheBuilder.newBuilder()
			.maximumSize(100)
			.refreshAfterWrite(7200, TimeUnit.SECONDS)
			.build(new CacheLoader<String, String>() {

				@Override
				public String load(String key) throws Exception {
					logger.info("从微信服务器中获取通讯录AccessToken");

					String url = MessageFormat.format(ACCESS_TOKEN_URL, corpid, phoneBookSecret);
					String result = HttpUtil.getRequestText(url);
					Gson gson = new Gson();
					Map<String, Object> map = gson.fromJson(result, HashMap.class);

					if (map == null || (Double) map.get("errcode") != 0) {
						throw new WeiXinException(
								"获取通讯录AccessToken异常：" + WeiXinErrorCode.ERRORCODEMAP.get(map.get("errcode")));
					}
					return map.get("access_token").toString();
				}
			});

    /**
	 * 将系统用户绑定到微信企业号的通讯录中
	 * 
	 * @throws ExecutionException
	 */
	public void bindUserWeixin(User user, Integer[] roles) {
        Map<String,Object> data = Maps.newHashMap();
		data.put("userid", user.getUserid());
        data.put("name",user.getUsername());
        data.put("department",roles);
		data.put("email", user.getEmail());
		Gson gson = new Gson();
		String json = gson.toJson(data);
		String accessToken;
		try {
			accessToken = phoneBookAccessTokenCache.get("");
			String url = MessageFormat.format(CREATE_USER_URL, accessToken);
			String result = HttpUtil.postStringWithRaw(url, json);

			Map<String, Object> resultMap = gson.fromJson(result, HashMap.class);
			String errorCode = Integer.valueOf(Double.valueOf(resultMap.get("errcode").toString()).intValue())
					.toString();
			if (!errorCode.equals("0")) {
				throw new WeiXinException("添加通讯录成员异常:" + WeiXinErrorCode.ERRORCODEMAP.get(errorCode));
			}
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

    }




    /**
     * 从通讯录中删除成员
     * @param userid 微信中唯一的userId
     */
	public void delUser(String userid) {
        String accessToken = null;
        try {
            accessToken = phoneBookAccessTokenCache.get("");
            String url = MessageFormat.format(DEL_USER_URL,accessToken,userid);
			String result = HttpUtil.getRequestText(url);

            Map<String,Object> resultMap = new Gson().fromJson(result,HashMap.class);
            String errorCode = Integer.valueOf(Double.valueOf(resultMap.get("errcode").toString()).intValue()).toString();
            if(!errorCode.equals("0")) {
				throw new WeiXinException("从通讯录中删除成员异常:" + WeiXinErrorCode.ERRORCODEMAP.get(errorCode));
            }

        } catch (ExecutionException e) {
            throw new WeiXinException("从AccessTokenCache中获取值异常");
        }

    }

    /**
     * 通过菜单OAUTH验证中的code，获取userid
     */
    public String useCodeGetUserId(String code) {
        try {
            String accessToken = accessTokenCache.get("");
            String url = MessageFormat.format(USE_CODE_GET_USERID,accessToken,code);

			String result = HttpUtil.getRequestText(url);
            Map<String,Object> resultMap = new Gson().fromJson(result,HashMap.class);
			String errorCode = Integer.valueOf(Double.valueOf(resultMap.get("errcode").toString()).intValue())
					.toString();
			if (resultMap == null || !errorCode.equals("0")) {

				throw new WeiXinException("通过Code获取UserID异常:" + WeiXinErrorCode.ERRORCODEMAP.get(errorCode));
            }
            return result;
        } catch (ExecutionException e) {
            throw new WeiXinException("从AccessTokenCache中获取值异常");
        }

    }





}
