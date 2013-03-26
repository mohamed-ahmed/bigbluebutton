package org.bigbluebutton.conference.messages.in.users;

import org.bigbluebutton.conference.messages.in.AbstractMessageIn;

public class UserJoin extends AbstractMessageIn {

	public final String internalUserID;
	public final String externalUserID;
	public final String username;
	public final String role;
	
	public UserJoin(String meetingID, String internalUserID, String externalUserID, String username, String role) {
		super(meetingID);
		this.internalUserID = internalUserID;
		this.externalUserID = externalUserID;
		this.username = username;
		this.role = role;
	}
}
