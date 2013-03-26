package org.bigbluebutton.conference.messages.in.meetings;

import org.bigbluebutton.conference.messages.in.AbstractMessageIn;

public class MeetingStart extends AbstractMessageIn {
	public final String voiceBridge;
	public final Boolean record;
	
	public MeetingStart(String meetingID, String voiceBridge, Boolean record) {
		super(meetingID);
		this.voiceBridge = voiceBridge;
		this.record = record;
	}
}
