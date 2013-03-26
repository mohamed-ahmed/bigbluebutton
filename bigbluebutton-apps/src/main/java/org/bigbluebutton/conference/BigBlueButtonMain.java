package org.bigbluebutton.conference;

import org.bigbluebutton.conference.messages.in.chat.PublicChatHistoryQuery;
import org.bigbluebutton.conference.messages.in.meetings.MeetingStart;
import org.bigbluebutton.conference.messages.in.meetings.MeetingStopped;
import org.bigbluebutton.conference.messages.in.users.UserJoin;
import org.bigbluebutton.conference.messages.in.users.UserLeave;
import org.bigbluebutton.conference.service.chat.ChatMessageVO;

public class BigBlueButtonMain {

	private BigBlueButton bbb;
	
	public void starMeeting(String meetingID, String voiceBridge, Boolean record) {
		MeetingStart message = new MeetingStart(meetingID, voiceBridge, record);
		
		bbb.accept(message);
	}
	
	public void userJoin(String meetingID, String internalUserID, String externalUserID,
			String username, String role) {
		
		UserJoin userJoin = new UserJoin(meetingID, internalUserID, externalUserID, username, role);		
		bbb.accept(userJoin);
	}
	
	public void stopMeeting(String meetingID) {
    	MeetingStopped message = new MeetingStopped(meetingID);
    	bbb.accept(message);
	}
	
	public void userLeft(String meetingID, String userID) {
		UserLeave msg = new UserLeave(meetingID, userID);
		bbb.accept(msg);
	}
	
	public void sendPublicChatHistory(String meetingID, String userID) {
		PublicChatHistoryQuery message = new PublicChatHistoryQuery(meetingID, userID);
		bbb.accept(message);
	}
	
	public void sendPublicMessage(String room, ChatMessageVO chatobj) {
		
	}
	
	public void sendPrivateMessage(ChatMessageVO chatobj) {
		
	}
	
	public void sendCurrentLayout(String meetingID, String userID) {
		
	}
	
	public void lockLayout(String meetingID, String layoutID) {
		
	}
	
	public void unlockLayout(String meetingID, String layoutID) {
		
	}
	
	public void setBigBlueButton(BigBlueButton bbb) {
		this.bbb = bbb;
	}
	
}
