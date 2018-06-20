package com.tianque.wx.message.req;

/**
 * 类名: VideoMessage </br>
 * 描述: 请求消息之视频消息 </br>
 */
public class VideoMessageReq extends BaseMessageReq {

	/**
	 * 视频消息媒体id，可以调用多媒体文件下载接口拉取数据。
	 */
	private String MediaId;
	/**
	 * 视频消息缩略图的媒体id，可以调用多媒体文件下载接口拉取数据。
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
