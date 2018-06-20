package com.tianque.wx;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.tianque.util.HttpUtil;

/**
 * 调用图灵机器人api接口，获取智能回复内容
 * 
 * 
 */
public class TulingApiService {
	
	private final String APIKEY = "1ec0357d37204ea2b12f970cf6fee188";
	
	private final String APIURL = "http://www.tuling123.com/openapi/api?key={0}&info={1}&userid={2}";

	/**
	 * 调用图灵机器人api接口，获取智能回复内容，解析获取自己所需结果
	 * 
	 * @param content
	 * @return
	 */
	public String getTulingResult(String content, String userId) {
		/** 此处为图灵api接口，参数key需要自己去注册申请 */
		String url = "";
		try {
			url = MessageFormat.format(APIURL, APIKEY, URLEncoder.encode(content, "utf-8"), userId);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		/** 发送httpPost请求 */
		String result = HttpUtil.getRequestTextByPost(url);

		/** 请求失败处理 */
		if (null == result) {
			return "对不起，你说的话真是太高深了……";
		}

			Map<String, Object> map = new Gson().fromJson(result, HashMap.class);

			// 以code=100000为例，参考图灵机器人api文档
		if (100000 == (Double) map.get("code")) {
			result = (String) map.get("text");
		}

		return result;
	}
}