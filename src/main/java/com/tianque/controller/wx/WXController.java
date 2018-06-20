package com.tianque.controller.wx;

import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianque.wx.CoreService;

@Controller
@RequestMapping("/mywx")
public class WXController {
	private Logger logger = LoggerFactory.getLogger(WXController.class);

	@Value("${wx.gztoken}")
	private String token;
	@Value("${wx.appID}")
	private String appID;
	@Value("${wx.appSecret}")
	private String appSecret;

	/**
	 * 微信回调模式的验证方法
	 * 
	 * @param msg_signature
	 * @param timestamp
	 * @param nonce
	 * @param echostr
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public String callback(String signature, String timestamp, String nonce, String echostr) {
		logger.debug("{} - {} - {} - {}", signature, timestamp, nonce, echostr);
		String sEchoStr = null;

		// 排序
		String sortString = sort(token, timestamp, nonce);
		// 加密
		String mytoken = DigestUtils.sha1Hex(sortString);
		if (mytoken.equals(signature)) {
			sEchoStr = echostr;
		}
		return sEchoStr;
	}

	@RequestMapping(method = RequestMethod.POST, produces = "application/xml;charset=utf-8")
	@ResponseBody
	public String msgService(HttpServletRequest request) {
		// 调用核心业务类接收消息、处理消息
		String respXml = CoreService.processRequest(request);
		return respXml;
	}

	public static String sort(String token, String timestamp, String nonce) {
		String[] strArray = { token, timestamp, nonce };
		Arrays.sort(strArray);

		StringBuilder sbuilder = new StringBuilder();
		for (String str : strArray) {
			sbuilder.append(str);
		}

		return sbuilder.toString();
	}


}
