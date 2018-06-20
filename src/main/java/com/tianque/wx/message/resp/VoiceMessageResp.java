package com.tianque.wx.message.resp;

public class VoiceMessageResp extends BaseMessageResp {
	// 语音
	private Voice Voice;

	public Voice getVoice() {
		return Voice;
	}

	public void setVoice(Voice voice) {
		Voice = voice;
	}
}