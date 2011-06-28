/**
 * 
 */
package com.icepack.MeetUp1;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.icepack.MeetUp1.common.MUUser;

/**
 * @author AH
 *
 */
public class Settings {
	private String serverIp;
	private int serverPort;
	private boolean autologin;
	private String username;
	private String password;
	private int userId;
	SharedPreferences spref;

	public Settings(SharedPreferences spref) {
		this.spref = spref;
		this.serverIp = this.spref.getString("serverip", "31.18.21.44");
		this.serverPort = this.spref.getInt("serverport", 23232);
		this.userId = this.spref.getInt("userid", 2);
		this.autologin = this.spref.getBoolean("autologin", true);
		this.username = this.spref.getString("username", "Ibo");
		this.password = this.spref.getString("password", "test2");
	}
	//DEBUG
	public Settings() {
		this.serverIp = "31.18.21.44";
		this.serverPort = 23232;
		this.userId = 2;
	}
	public boolean save() {
		Editor editor = spref.edit();
		editor.putString("serverip", this.serverIp);
		editor.putInt("serverport", this.serverPort);
		editor.putInt("userid", this.userId);
		editor.putBoolean("autologin", this.autologin);
		editor.putString("username", this.username);
		editor.putString("password", this.password);
		return editor.commit();
	}
	public String getServerIp() {
		return serverIp;
	}
	public void setServerIp(String serverIp) {
		this.serverIp = serverIp;
	}
	public int getServerPort() {
		return serverPort;
	}
	public void setServerPort(int serverPort) {
		this.serverPort = serverPort;
	}
	public boolean getAutologin() {
		return this.autologin;
	}
	public void setAutologin(boolean autologin) {
		this.autologin = autologin;
	}
	public String getUsername() {
		return this.username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return this.password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public int getUserId() {
		return this.userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	
}
