package org.bigbluebutton.app.video;

public class VideoClientService {

	private VideoEventSender sender;
	
	public void sendMessage(String message) {
		System.out.println("*************** Recieved message from client [" + message + "]");
		sender.sendMessage(message);
	}
	
	public void setVideoEventSender(VideoEventSender s) {
		sender = s;
	}
}
