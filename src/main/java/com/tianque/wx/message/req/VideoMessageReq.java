package com.tianque.wx.message.req;

/**
 * ����: VideoMessage </br>
 * ����: ������Ϣ֮��Ƶ��Ϣ </br>
 */
public class VideoMessageReq extends BaseMessageReq {

	/**
	 * ��Ƶ��Ϣý��id�����Ե��ö�ý���ļ����ؽӿ���ȡ���ݡ�
	 */
	private String MediaId;
	/**
	 * ��Ƶ��Ϣ����ͼ��ý��id�����Ե��ö�ý���ļ����ؽӿ���ȡ���ݡ�
	 */
	private String ThumbMediaId;

	public String getMediaId() {
		return MediaId;
	}

	public void setMediaId(String mediaId) {
		MediaId = mediaId;
	}

	public String getThumbMediaId() {
		return ThumbMediaId;
	}

	public void setThumbMediaId(String thumbMediaId) {
		ThumbMediaId = thumbMediaId;
	}

}
