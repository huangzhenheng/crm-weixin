package com.tianque.wx.message.req;

/**
 * ����: LinkMessage </br>
 * ����: ������Ϣ֮������Ϣ </br>
 */
public class LinkMessageReq extends BaseMessageReq {
	/**
	 * ��Ϣ����
	 */
	private String Title;
	/**
	 * ��Ϣ����
	 */
	private String Description;
	/**
	 * ��Ϣ����
	 */
	private String Url;

	public String getTitle() {
		return Title;
	}

	public void setTitle(String title) {
		Title = title;
	}

	public String getDescription() {
		return Description;
	}

	public void setDescription(String description) {
		Description = description;
	}

	public String getUrl() {
		return Url;
	}

	public void setUrl(String url) {
		Url = url;
	}

}
