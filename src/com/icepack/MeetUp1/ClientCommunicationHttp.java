package com.icepack.MeetUp1;


import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.icepack.MeetUp1.common.MULocation;
import com.icepack.MeetUp1.common.MURoom;
import com.icepack.MeetUp1.common.MUUser;

/**
 * This example demonstrates the use of the {@link ResponseHandler} to simplify the process of processing the HTTP response and releasing associated resources.
 */
public class ClientCommunicationHttp {
	private int port;
	private String host;
	//private HttpClient httpclient;
	private HttpClient httpclient;

	/**
	 * Creates ClientCommunicationHttp Object.
	 * 
	 * @throws Exception
	 *             on Communication IO errors
	 */
	public ClientCommunicationHttp(String host, int port) {
		this.port = port;
		this.host = host;
		//httpclient = new DefaultHttpClient();
		httpclient = new DefaultHttpClient();
	}

	/**
	 * Sends HTTP Request to Server and returns the body of the Result
	 * 
	 * @throws IOException
	 *             on Communication IO errors
	 * @throws Exception
	 *             on DB errors
	 * @throws JSONException
	 *             on Message errors
	 */
	private JSONObject jPost(int type, JSONObject jObj) throws IOException,Exception, JSONException {
		JSONObject jEntity = new JSONObject();
		jEntity.put("type", type);
		jEntity.put("body", jObj);
		StringEntity entity = new StringEntity(jEntity.toString());

		HttpPost httppost = new HttpPost("http://" + host + ":" + port);
		httppost.setEntity(entity);
		HttpResponse response = httpclient.execute(httppost);
		HttpEntity resEntity = response.getEntity();
		if (resEntity != null) {
			long len = resEntity.getContentLength();
			if (len != -1 && len < 4096) {
				throw new IOException("Response out of Bound"); // Why exactly?
			} else {
				InputStream is = resEntity.getContent();
				ByteBuffer length = ByteBuffer.allocate(Integer.SIZE / 8);
				is.read(length.array());
				ByteBuffer message = ByteBuffer.allocate(length.getInt());
				is.read(message.array());
				JSONObject jRes = new JSONObject(new String(message.array()));
				is.close();
				if (jRes.getInt("type") == ComConstants.ERROR) {
					throw new Exception("Error on Server");
				} else
					return jRes.getJSONObject("body");
			}
		} else
			throw new IOException("No Response");
	}
	private JSONObject jPost(int type) throws IOException,Exception, JSONException {
		JSONObject jEntity = new JSONObject();
		jEntity.put("type", type);
		jEntity.put("body", new JSONObject());
		StringEntity entity = new StringEntity(jEntity.toString());

		HttpPost httppost = new HttpPost("http://" + host + ":" + port);
		httppost.setEntity(entity);
		HttpResponse response = httpclient.execute(httppost);
		HttpEntity resEntity = response.getEntity();
		if (resEntity != null) {
			long len = resEntity.getContentLength();
			if (len != -1 && len < 4096) {
				throw new IOException("Response out of Bound"); // Why exactly?
			} else {
				InputStream is = resEntity.getContent();
				ByteBuffer length = ByteBuffer.allocate(Integer.SIZE / 8);
				is.read(length.array());
				ByteBuffer message = ByteBuffer.allocate(length.getInt());
				is.read(message.array());
				JSONObject jRes = new JSONObject(new String(message.array()));
				is.close();
				if (jRes.getInt("type") == ComConstants.ERROR) {
					throw new Exception("Error on Server");
				} else
					return jRes.getJSONObject("body");
			}
		} else
			throw new IOException("No Response");
	}
	
	/**
	 * Handles setBudget message and set the budget of given month
	 * 
	 * @param jObj
	 *            the submitted body of the JSONMessage
	 * @throws JSONException
	 *             on Communication errors
	 */
	public boolean LogInToRoom(int userId, int roomId) {
		try {
			JSONObject jObj = new JSONObject();
			jObj.put("userid", userId);
			jObj.put("roomid", roomId);
			jPost(ComConstants.ROOM_LOGIN, jObj);
			return true;
			
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	public boolean LogOutOfRoom(int userId) {
		try {
			JSONObject jObj = new JSONObject();
			jObj.put("userid", userId);
			jPost(ComConstants.ROOM_LOGOUT, jObj);
			return true;
			
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	
	public boolean setLocation(int userId, MULocation loc) {
		try {
			JSONObject jObj = new JSONObject();
			JSONObject jLoc = new JSONObject();
			jLoc.put("userid", userId);
			jLoc.put("long", loc.longitude);
			jLoc.put("lat", loc.latitude);
			jLoc.put("time", loc.time);
			jObj.put("loc", jLoc);
			jPost(ComConstants.LOC_SET, jObj);
			return true;
			
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public ArrayList<MULocation> getLocation(int userId, int lastId) {
		try {
			JSONObject jObj = new JSONObject();
			jObj.put("userid", userId);
			jObj.put("lastid", lastId);
			JSONObject jRes = jPost(ComConstants.LOC_GET, jObj);
			JSONArray jLocs = jRes.getJSONArray("locs");
			ArrayList<MULocation> locs = new ArrayList<MULocation>();
			for(int i=0; i< jLocs.length(); i++) {
				JSONObject loc = jLocs.getJSONObject(i);
				locs.add(new MULocation(loc.getDouble("lat"),loc.getDouble("long"), loc.getLong("time")));
			}
			return locs;
		} catch (Exception e) {
			e.printStackTrace();
			return new ArrayList<MULocation>();
		}
	}

	/**
	 * Handles getMonthMessage and return the months of given year
	 * 
	 * @param jObj
	 *            the submitted body of the JSONMessage
	 * @throws JSONException
	 *             on Communication errors
	 */
	public ArrayList<MURoom> getRoomList() {
		try {
			JSONObject jRes = jPost(ComConstants.ROOM_GET);
			JSONArray jRooms = jRes.getJSONArray("rooms");
			ArrayList<MURoom> rooms = new ArrayList<MURoom>();
			for(int i=0; i< jRooms.length(); i++) {
				JSONObject jRoom = jRooms.getJSONObject(i);
				rooms.add(new MURoom(jRoom.getInt("id"),jRoom.getString("name")));
			}
			return rooms;
		} catch (Exception e) {
			e.printStackTrace();
			return new ArrayList<MURoom>();
		}
	}
	public int createRoom(int userId, String name) {
		try {
			JSONObject jObj = new JSONObject();
			jObj.put("name", name);
			jObj.put("userid", userId);
			JSONObject jRes = jPost(ComConstants.ROOM_CREATE, jObj);
			return jRes.getInt("roomid");
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}
	
	public ArrayList<MUUser> getUserList(int userId) {
		try {
			JSONObject jObj = new JSONObject();
			jObj.put("userid", userId);
			JSONObject jRes = jPost(ComConstants.ROOM_GETUSERS, jObj);
			JSONArray jUsers = jRes.getJSONArray("users");
			ArrayList<MUUser> users = new ArrayList<MUUser>();
			for(int i=0; i< jUsers.length(); i++) {
				JSONObject jUser = jUsers.getJSONObject(i);
				users.add(new MUUser(jUser.getInt("userid"),jUser.getString("name")));
			}
			return users;
		} catch (Exception e) {
			e.printStackTrace();
			return new ArrayList<MUUser>();
		}
	}
}
