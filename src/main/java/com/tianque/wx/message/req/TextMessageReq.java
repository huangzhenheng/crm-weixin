package com.tianque.wx.message.req;

/**
 * ����: TextMessage </br>
 * ����: ������Ϣ֮�ı���Ϣ </br>
 */
public class TextMessageReq extends BaseMessageReq {
	/**
	 * ��Ϣ����
	 */
	private String Content;

	public String getContent() {
		return Content;
	}

	public void setContent(String content) {
		Content = content;
	}

}
