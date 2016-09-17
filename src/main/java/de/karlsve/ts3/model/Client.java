package de.karlsve.ts3.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import de.karlsve.ts3.ServerBot;
import de.stefan1200.jts3serverquery.JTS3ServerQuery;
import de.stefan1200.jts3serverquery.TS3ServerQueryException;

public class Client extends HashMap<String, String> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4217786462914758302L;
	
	private ServerBot handle;

	public Client(ServerBot handle, int clid) throws TS3ServerQueryException {
		this.handle = handle;
		this.put("clid", String.valueOf(clid));
		this.putAll(this.loadInfo());
	}
	
	private Map<String, String> loadInfo() throws TS3ServerQueryException {
		return handle.getQuery().getInfo(JTS3ServerQuery.INFOMODE_CLIENTINFO,
				Integer.valueOf(this.get("clid")));
	}
	
	public static List<Client> getClientList(ServerBot handle, String type) throws NumberFormatException, TS3ServerQueryException {
		Vector<Client> clients = new Vector<>();
		for(Map<String, String> client_entry : handle.getQuery().getList(JTS3ServerQuery.LISTMODE_CLIENTLIST, type)) {
			clients.add(new Client(handle, Integer.valueOf(client_entry.get("clid"))));
		}
		return clients;
	}

}
