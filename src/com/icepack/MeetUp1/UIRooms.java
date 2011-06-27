package com.icepack.MeetUp1;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.icepack.MeetUp1.common.MURoom;
import com.icepack.MeetUp1.common.MUUser;

public class UIRooms extends Activity {
	ListView roomListView;
	Button btnRefreshList;
	Button btnCreateRoom;
	String roomdata_arr[];
	ArrayList<MURoom> roomList;
	ArrayList<MUUser> userList;
	int currentMenuState;
	ClientCommunicationHttp clientComm;
	UIHelper uiHelperRef;
	
	EditText inpCreateName;
	LinearLayout createForm;
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.tvrooms);
        roomListView = (ListView)findViewById(R.id.lsv_roomlist);
        btnCreateRoom = (Button)findViewById(R.id.btn_createRoom);
        btnRefreshList = (Button)findViewById(R.id.btn_rmrefresh);
        inpCreateName = (EditText)findViewById(R.id.inp_roomname);
        
        createForm = (LinearLayout)findViewById(R.id.createForm);
        createForm.setVisibility(8);
        
        //roomdata_arr = new String[1];
        //roomdata_arr[0] = "loading...";  // Init Replace
        roomList = new ArrayList<MURoom>();
        userList = new ArrayList<MUUser>();
        currentMenuState = 1;
        
        //ad
        //roomListView.setAdapter(adapter)
        //roomListView.setListAdapter(new ArrayAdapter<String>(this, R.layout.list_item, COUNTRIES));
        //roomListView.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1 , roomdata_arr));  // Init Replace
        
        ((MeetUp1Activity)this.getParent()).tabCallback2(this);
        
        // Init Content
        updateRoomList();
        
        
        
        //OnClick Listeners
        btnRefreshList.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	updateRoomList();
            }
        });
        
        btnCreateRoom.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	createRoom();
            }
        });
        
        roomListView.setClickable(true);
        roomListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

        	  @Override
        	  public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        		Object obj = roomListView.getItemAtPosition(position);
          	    String pname = (String) obj;
          	    
          	    if(currentMenuState==1) {
          	    	if(roomList.get(position).id == -2) {
          	    		createForm.setVisibility(0);
          	    		inpCreateName.setText("Id"+uiHelperRef.getStOwnUserId()+" room");
          	    	} else {
          	    		joinRoom(position);
          	    		updateUserList(position);
          	    	}
          	    }
          	    else if(currentMenuState==2) {
          	    	//TODO User Action
          	    }
              }
          });

        	    
	}
	
	public void setClientComm(ClientCommunicationHttp newClientComm) {
		this.clientComm = newClientComm;
	}
	
	public void setClientUIHelper(UIHelper newUIHelper) {
		this.uiHelperRef = newUIHelper;
	}
	
	public void updateRoomList() {
		createForm.setVisibility(8);
		
		//refresh the roomlist
		roomList.clear();
		
		//server call
		if(this.clientComm!=null)
		{
			showRoomListMessage("con server:"+uiHelperRef.getStServerIp());
			this.roomList = clientComm.getRoomList();
		}
		
		roomList.add(0, new MURoom(-2, "neuen Raum erstellen"));
		
		//set up ui list
		int roomCount = roomList.size();
		roomdata_arr = new String[roomCount];
		
		for(int i=0;i<roomCount;i++)
		{
			roomdata_arr[i]=roomList.get(i).name;
		}
		roomListView.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1 , roomdata_arr));
		currentMenuState = 1;
	}
	public void showRoomListMessage(String message) {
		roomdata_arr = new String[1];
		roomdata_arr[0] = message;
		roomListView.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1 , roomdata_arr));
		
	}
	public void updateUserList(int userId) {
		createForm.setVisibility(8);
		
		//refresh the roomlist
		userList.clear();
		
		//server call
		if(this.clientComm!=null)
		{
			//showRoomListMessage("con server:"+uiHelperRef.getStServerIp());
			this.userList = clientComm.getUserList(uiHelperRef.getStOwnUserId());
		}
		
		//set up ui list
		int userCount = userList.size();
		String[] userdata_arr = new String[userCount];
		
		for(int i=0;i<userCount;i++)
		{
			userdata_arr[i]=userList.get(i).username;
		}
		roomListView.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1 , userdata_arr));
		currentMenuState = 2;
	}
	public void joinRoom(int roomId) {
		this.clientComm.LogInToRoom(this.uiHelperRef.getStOwnUserId(), roomId);
		//this.showRoomListMessage("joining Room: "+roomId); Replace UserList
		this.uiHelperRef.resetUserListFlag = 1;
	}
	
	public void createRoom() {
		String roomName = inpCreateName.getText().toString();
		int roomId = this.clientComm.createRoom(this.uiHelperRef.getStOwnUserId(), roomName);
		updateUserList(roomId);
	}

}
