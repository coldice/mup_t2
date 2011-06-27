package com.icepack.MeetUp1.common;

public class MUUser {
	public int id;
	public String username;
	public int roomId;
	
	public MUUser(int id, String username) {
		this.id = id;
		this.username = username;
		this.roomId = 0;
	}
	public MUUser(int id, String username, int roomId) {
		this.id = id;
		this.username = username;
		this.roomId = roomId;
	}
	public MUUser() {
		this.id = 0;
		this.username = null;
		this.roomId = 0;
	}
}
