package com.tianque.wx.message.req;

/**
 * ����: ImageMessage </br>
 * ����: ������Ϣ֮ͼƬ��Ϣ </br>
 */
public class ImageMessageReq extends BaseMessageReq {
	/**
	 * ͼƬ����
	 */
	private String PicUrl;
	/**
	 * ͼƬ��Ϣý��id�����Ե��ö�ý���ļ����ؽӿ���ȡ���ݡ�
	 */
	private String MediaId;

	public String getPicUrl() {
		return PicUrl;
	}

	public void setPicUrl(String picUrl) {
		PicUrl = picUrl;
	}

	public String getMediaId() {
		return MediaId;
	}

	public void setMediaId(String mediaId) {
		MediaId = mediaId;
	}

}
