
/**
* BigBlueButton open source conferencing system - http://www.bigbluebutton.org/
* 
* Copyright (c) 2012 BigBlueButton Inc. and by respective authors (see below).
*
* This program is free software; you can redistribute it and/or modify it under the
* terms of the GNU Lesser General Public License as published by the Free Software
* Foundation; either version 3.0 of the License, or (at your option) any later
* version.
* 
* BigBlueButton is distributed in the hope that it will be useful, but WITHOUT ANY
* WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A
* PARTICULAR PURPOSE. See the GNU Lesser General Public License for more details.
*
* You should have received a copy of the GNU Lesser General Public License along
* with BigBlueButton; if not, see <http://www.gnu.org/licenses/>.
*
*/
package org.bigbluebutton.conference.service.recorder.users;

import org.bigbluebutton.conference.IMeetingListener;
import org.bigbluebutton.conference.User;
import org.bigbluebutton.conference.service.recorder.RecorderApplication;
import org.red5.logging.Red5LoggerFactory;
import org.slf4j.Logger;

public class UserEventRecorder implements IMeetingListener {
	private static Logger log = Red5LoggerFactory.getLogger(UserEventRecorder.class, "bigbluebutton");
	private final RecorderApplication recorder;
	private final String session;
	
	String name = "RECORDER:PARTICIPANT";
		
	public UserEventRecorder(String session, RecorderApplication recorder) {
		this.recorder = recorder;
		this.session = session;
	}

	@Override
	public void endAndKickAll() {
		UserEndAndKickAllRecordEvent ev = new UserEndAndKickAllRecordEvent();
		ev.setTimestamp(System.currentTimeMillis());
		ev.setMeetingId(session);
		recorder.record(session, ev);		
	}

	@Override
	public void participantJoined(User p) {
		UserJoinRecordEvent ev = new UserJoinRecordEvent();
		ev.setTimestamp(System.currentTimeMillis());
		ev.setUserId(p.intUserID);
		ev.setName(p.name);
		ev.setMeetingId(session);
		//ev.setStatus(p.getStatus().toString());
		ev.setRole(p.getRole());

		recorder.record(session, ev);
	}

	@Override
	public void participantLeft(User p) {
		UserLeftRecordEvent ev = new UserLeftRecordEvent();
		ev.setTimestamp(System.currentTimeMillis());
		ev.setUserId(p.intUserID);
		ev.setMeetingId(session);
		
		recorder.record(session, ev);
	}

	@Override
	public void participantStatusChange(User p, String status, Object value) {
		UserStatusChangeRecordEvent ev = new UserStatusChangeRecordEvent();
		ev.setTimestamp(System.currentTimeMillis());
		ev.setUserId(p.intUserID);
		ev.setMeetingId(session);
		ev.setStatus(status);
		ev.setValue(value.toString());
		
		recorder.record(session, ev);
	}

	@Override
	public void assignPresenter(String newPresenterUserID, String newPresenterName, String assignedBy) {
		log.debug("RECORD module:presentation event:assign_presenter");
		AssignPresenterRecordEvent event = new AssignPresenterRecordEvent();
		event.setMeetingId(session);
		event.setTimestamp(System.currentTimeMillis());
		event.setUserId(newPresenterUserID);
		event.setName(newPresenterName);
		event.setAssignedBy(assignedBy);
		
		recorder.record(session, event);
	}
	
	@Override
	public String getName() {
		return this.name;
	}

}
