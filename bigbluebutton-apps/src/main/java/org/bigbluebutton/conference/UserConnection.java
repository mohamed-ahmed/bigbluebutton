package org.bigbluebutton.conference;

import org.red5.server.api.IConnection;

public class UserConnection {
	public final String connID;
	public final IConnection conn;
	
	public UserConnection(String connID, IConnection conn) {
		this.connID = connID;
		this.conn = conn;
	}
}
