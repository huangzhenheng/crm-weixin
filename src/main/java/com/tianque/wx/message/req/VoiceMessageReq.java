package com.tianque.wx.message.req;

/**
 * ����: VoiceMessage </br>
 * ����: ������Ϣ֮������Ϣ </br>
 */
public class VoiceMessageReq extends BaseMessageReq {

	/**
	 * ������Ϣý��id�����Ե��ö�ý���ļ����ؽӿ���ȡ���ݡ�
	 */
	private String MediaId;

	/**
	 * ������ʽ����amr��speex��
	 */
	private String Format;

	public String getMediaId() {
		return MediaId;
	}

	public void setMediaId(String mediaId) {
		MediaId = mediaId;
	}

	public String getFormat() {
		return Format;
	}

	public void setFormat(String format) {
		Format = format;
	}

}
