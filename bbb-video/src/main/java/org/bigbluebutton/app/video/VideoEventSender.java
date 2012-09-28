package org.bigbluebutton.app.video;

import java.util.ArrayList;
import org.red5.server.api.so.ISharedObject;

public class VideoEventSender {
	private ISharedObject so;
	
	public void sendMessage(String message) {
		System.out.println("Sending Chat message [" + message + "]");
		ArrayList list = new ArrayList();
		list.add(message);
		so.sendMessage("receiveChatMessage", list);
	}
	
	public void setSharedObject(ISharedObject so) {
		this.so = so;
	}
}
