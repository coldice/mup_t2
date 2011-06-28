package com.icepack.MeetUp1;

/**
 * This class holds Contants about SocketCommunication
 * 
 * @author Alexander Hillmer
 * @version 1.0
 * 
 */
public class ComConstants {
	public static final byte ROOM_LOGIN= 1;
	public static final byte ROOM_GETUSERS = 2;
	public static final byte ROOM_LOGOUT = 3;
	public static final byte ROOM_GET = 4;
	public static final byte ROOM_CREATE = 11;
	public static final byte REGISTER = 5;
	public static final byte UNREGISTER = 6;
	public static final byte LOC_GET = 9;
	public static final byte LOC_SET = 10;
	public static final byte ERROR = 7;
	public static final byte NOERROR = 8;
	public static final byte LOGIN = 12;
	public static final byte LOGOUT = 14;
	public static final byte USER_INFO = 13;
	
	//Debug
	public static final byte TEST = 101;
}
