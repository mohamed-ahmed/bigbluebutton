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
package org.bigbluebutton.conference;

import java.util.Iterator;
import java.util.Set;
import org.red5.server.api.Red5;import org.bigbluebutton.conference.messages.in.meetings.AllMeetingsStop;
import org.bigbluebutton.conference.messages.in.meetings.MeetingEnd;
import org.bigbluebutton.conference.messages.in.meetings.MeetingStart;
import org.bigbluebutton.conference.messages.in.meetings.MeetingStopped;
import org.bigbluebutton.conference.messages.in.users.UserJoin;
import org.bigbluebutton.conference.service.recorder.RecorderApplication;
import org.red5.logging.Red5LoggerFactory;
import org.red5.server.adapter.IApplication;
import org.red5.server.adapter.MultiThreadedApplicationAdapter;
import org.red5.server.api.IClient;
import org.red5.server.api.IConnection;
import org.red5.server.api.IContext;
import org.red5.server.api.scope.IScope;
import org.slf4j.Logger;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.support.AbstractApplicationContext;

public class BigBlueButtonApplication extends MultiThreadedApplicationAdapter {
	private static Logger log = Red5LoggerFactory.getLogger(BigBlueButtonApplication.class, "bigbluebutton");

	private RecorderApplication recorderApplication;
	private AbstractApplicationContext appCtx;
	private ConnectionInvokerService connInvokerService;
	private BigBlueButton bbb;
	
	private static final String APP = "BBB";
	
	@Override
	public boolean appConnect(IConnection conn, Object[] params) {
		log.debug("***** " + APP + " [ " + " appConnect *********");
		return super.appConnect(conn, params);
	}

	@Override
	public void appDisconnect(IConnection conn) {
		log.debug("***** " + APP + " [ " + " appDisconnect *********");
		super.appDisconnect(conn);
	}

	@Override
	public boolean appJoin(IClient client, IScope scope) {
		log.debug("***** " + APP + " [ " + " appJoin [ " + scope.getName() + "] *********");
		return super.appJoin(client, scope);
	}

	@Override
	public void appLeave(IClient client, IScope scope) {
		log.debug("***** " + APP + " [ " + " appLeave [ " + scope.getName() + "] *********");
		super.appLeave(client, scope);
	}
	
	@Override
	public boolean roomJoin(IClient client, IScope scope) {
		log.debug("***** " + APP + " [ " + " roomJoin [ " + scope.getName() + "] *********");
		return super.roomJoin(client, scope);
	}
	
	@Override
	public void roomLeave(IClient client, IScope scope) {
		log.debug("***** " + APP + " [ " + " roomLeave [ " + scope.getName() + "] *********");
		super.roomLeave(client, scope);
	}

	
	@Override
    public boolean appStart(IScope app) {
		log.debug("***** " + APP + " [ " + " appStart [ " + scope.getName() + "] *********");
        
        connInvokerService.start();
        
        return true;
    }
    
	@Override
    public void appStop(IScope app) {
		log.debug("***** " + APP + " [ " + " appStop [ " + scope.getName() + "] *********");
        connInvokerService.stop();
        super.appStop(app);
    }
    
	@Override
    public boolean roomStart(IScope room) {
		log.debug("***** " + APP + " [ " + " roomStart [ " + scope.getName() + "] *********");

		connInvokerService.addScope(room.getName(), room);

    	return super.roomStart(room);
    }	
	
	@Override
    public void roomStop(IScope scope) {

		log.debug("***** " + APP + " [ " + " roomStop [ " + scope.getName() + "] *********");
		String meetingID = scope.getName();
		
    	MeetingStopped message = new MeetingStopped(meetingID);
    	bbb.accept(message);
    	
		recorderApplication.destroyRecordSession(meetingID);
		connInvokerService.removeScope(meetingID);
		
		super.roomStop(scope);

    }
    	
	@Override
	public boolean roomConnect(IConnection connection, Object[] params) {
		log.debug("***** " + APP + " [ " + " roomConnect [ " + connection.getScope().getName() + "] *********");
		
        String username = ((String) params[0]).toString();
        String role = ((String) params[1]).toString();

        String meetingID = ((String)params[2]).toString();
               
        String voiceBridge = ((String) params[3]).toString();
		
		boolean record = (Boolean)params[4];
		
    	String externalUserID = ((String) params[5]).toString();
    	String internalUserID = ((String) params[6]).toString();

    	    	
		if (record == true) {
			recorderApplication.createRecordSession(meetingID);
		}
			

    	BigBlueButtonSession bbbSession = new BigBlueButtonSession(meetingID, internalUserID,  username, role, 
    			voiceBridge, record, externalUserID);
        connection.setAttribute(Constants.SESSION, bbbSession);        
        
		MeetingStart message = new MeetingStart(meetingID, voiceBridge, record);
		
		bbb.accept(message);
		
		UserJoin userJoin = new UserJoin(meetingID, internalUserID, externalUserID, username, role);
		
		bbb.accept(userJoin);
		
        connInvokerService.addConnection(bbbSession.getInternalUserID(), connection);
        
        return super.roomConnect(connection, params);
        
	}

	@Override
	public void roomDisconnect(IConnection conn) {
		log.debug("***** " + APP + " [ " + " roomDisconnect [ " + conn.getScope().getName() + "] *********");
		
        String remoteHost = Red5.getConnectionLocal().getRemoteAddress();
        int remotePort = Red5.getConnectionLocal().getRemotePort();    	
        String clientId = Red5.getConnectionLocal().getClient().getId();
    	log.info("***** " + APP + "[clientid=" + clientId + "] disconnnected from " + remoteHost + ":" + remotePort + ".");
    	
    	connInvokerService.removeConnection(getBbbSession().getInternalUserID());
    	
		BigBlueButtonSession bbbSession = (BigBlueButtonSession) Red5.getConnectionLocal().getAttribute(Constants.SESSION);
		log.info("User [" + bbbSession.getUsername() + "] disconnected from room [" + bbbSession.getRoom() +"]");
		super.roomDisconnect(conn);
	}
	
	public String getMyUserId() {
		BigBlueButtonSession bbbSession = (BigBlueButtonSession) Red5.getConnectionLocal().getAttribute(Constants.SESSION);
		assert bbbSession != null;
		return bbbSession.getInternalUserID();
	}
	
	public void setRecorderApplication(RecorderApplication a) {
		recorderApplication = a;
	}
	
	public void setApplicationListeners(Set<IApplication> listeners) {
		Iterator<IApplication> iter = listeners.iterator();
		while (iter.hasNext()) {
			super.addListener((IApplication) iter.next());
		}
	}

	private BigBlueButtonSession getBbbSession() {
		return (BigBlueButtonSession) Red5.getConnectionLocal().getAttribute(Constants.SESSION);
	}

	public void setConnInvokerService(ConnectionInvokerService connInvokerService) {
		this.connInvokerService = connInvokerService;
	}
	
	public void setBigBlueButton(BigBlueButton bbb) {
		this.bbb = bbb;
	}

}
