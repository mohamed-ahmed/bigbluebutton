package org.bigbluebutton.conference;

import java.util.Map;

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
	
	public void removePresentation(String meetingID, String presentationID) {
		
	}
	
	public void sendPresentationInfo(String meetingID, String userID) {
		
	}
	
	public void gotoSlide(String meetingID, Integer page) {
		
	}
	
	public void sharePresentation(String meetingID, String presentationID, Boolean share) {
		
	}
	
	public void sendCursorUpdate(String meetingID, Double xPercent,Double yPercent) {
		
	}
	
	public void resizeAndMoveSlide(String meetingID, Double xOffset,Double yOffset,Double widthRatio,Double heightRatio) {
		
	}
	
	public void kickUser(String meetingID, String userID) {
		
	}
	
	public void assignPresenter(String meetingID, String newPresenterID, String assignedByID) {
		
	}
	
	public void sendUsers(String meetingID, String userID) {
		
	}
	
	public void setUserStatus(String meetingID, String userID, String statusName, Object statusValue) {
		
	}
	
	public void muteAllUsers(String meetingID, Boolean mute) {
		
	}
	
	public void muteUser(String meetingID, String userID, Boolean mute) {
		
	}
	
	public void lockMuteUser(String meetingID, String userID, Boolean lock) {
		
	}
	
	public void kickUSerFromVoid(String meetingID, String userID) {
		
	}
	
	public void setBigBlueButton(BigBlueButton bbb) {
		this.bbb = bbb;
	}
	
}
