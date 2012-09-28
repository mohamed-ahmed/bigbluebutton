package org.bigbluebutton.app.video;

public class VideoClientService {

	private VideoEventSender sender;
	
	public void sendMessage(String message) {
		sender.sendMessage(message);
	}
	
	public void setVideoEventSender(VideoEventSender s) {
		sender = s;
	}
}
