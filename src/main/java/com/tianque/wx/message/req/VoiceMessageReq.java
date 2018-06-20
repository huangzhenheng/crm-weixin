package com.tianque.wx.message.req;

/**
 * 类名: VoiceMessage </br>
 * 描述: 请求消息之语音消息 </br>
 */
public class VoiceMessageReq extends BaseMessageReq {

	/**
	 * 语音消息媒体id，可以调用多媒体文件下载接口拉取数据。
	 */
	private String MediaId;

	/**
	 * 语音格式，如amr，speex等
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
