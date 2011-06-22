package com.icepack.MeetUp1;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

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
	
	
	/**
	 * Handles setBudget message and set the budget of given month
	 * 
	 * @param jObj
	 *            the submitted body of the JSONMessage
	 * @throws JSONException
	 *             on Communication errors
	 */
	public int addDevice(double longitude, double latitude) {
		try {
			System.out.println("AddDev");
			JSONObject jObj = new JSONObject();
			jObj.put("long", longitude);
			jObj.put("lat", latitude);
			return jPost(ComConstants.ADD_DEVICE, jObj).getInt("key");
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
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
	public void deleteDevice(int key) {
		try {
			System.out.println("DelDev");
			JSONObject jObj = new JSONObject();
			jObj.put("key", key);
			jPost(ComConstants.DEL_DEVICE, jObj);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Handles getYearMessage and the YearsArray in JSON
	 * 
	 * @throws JSONException
	 *             on Communication errors
	 */
	public double[] getLocation(int key) {
		try {
			System.out.println("GetLocation");
			JSONObject jObj = new JSONObject();
			jObj.put("key", key);
			JSONObject jRes = jPost(ComConstants.GET_LOC, jObj);
			double[] loc = new double[2];
			loc[0] = jRes.getDouble("long");
			loc[1] = jRes.getDouble("lat");
			return loc;
		} catch (Exception e) {
			e.printStackTrace();
			return new double[1];
		}
	}


	/**
	 * Handles AddBookingEntryMessage
	 * 
	 * @param jObj
	 *            the submitted body of the JSONMessage
	 * @throws JSONException
	 *             on Communication errors
	 */
	public void setLocation(int key, double longitude, double latitude) {
		try {
			System.out.println("SetLocation");
			JSONObject jObj = new JSONObject();
			jObj.put("key", key);
			jObj.put("long", longitude);
			jObj.put("lat", latitude);
			jPost(ComConstants.SET_LOC, jObj);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
