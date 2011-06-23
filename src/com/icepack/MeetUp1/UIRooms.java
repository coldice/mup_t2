package com.icepack.MeetUp1;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.icepack.MeetUp1.common.MURoom;

public class UIRooms extends Activity {
	ListView roomListView;
	Button btnRefreshList;
	String roomdata_arr[];
	ArrayList<MURoom> roomList;
	int currentMenuState;
	ClientCommunicationHttp clientComm;
	UIHelper uiHelperRef;
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.tvrooms);
        roomListView = (ListView)findViewById(R.id.lsv_roomlist);
        btnRefreshList = (Button)findViewById(R.id.btn_rmrefresh);
        
        roomdata_arr = new String[1];
        roomdata_arr[0] = "loading...";
        roomList = new ArrayList<MURoom>();
        currentMenuState = 1;
        //ad
        //roomListView.setAdapter(adapter)
        //roomListView.setListAdapter(new ArrayAdapter<String>(this, R.layout.list_item, COUNTRIES));
        roomListView.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1 , roomdata_arr));
        
        ((MeetUp1Activity)this.getParent()).tabCallback2(this);
        
        //OnClick Listeners
        btnRefreshList.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	updateRoomList();
            	
            	
            }
        });
        
        roomListView.setClickable(true);
        roomListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

        	  @Override
        	  public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        		Object obj = roomListView.getItemAtPosition(position);
          	    String pname = (String) obj;
          	    
          	    if(currentMenuState==1) {
          	    	showRoomListMessage(" : joining "+pname+"..");
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
		//refresh the roomlist
		roomList.clear();
		
		roomList.add(new MURoom(-2, "neuen Raum erstellen"));
		
		//server call
		if(this.clientComm!=null)
		{
			this.roomList = clientComm.getRoomList();
		}
		
		
		//set up ui list
		int roomCount = roomList.size();
		roomdata_arr = new String[roomCount];
		
		for(int i=0;i<roomCount;i++)
		{
			roomdata_arr[i]=roomList.get(i).name;
		}
		roomListView.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1 , roomdata_arr));
		
	}
	
	public void showRoomListMessage(String message) {
		roomdata_arr = new String[1];
		roomdata_arr[0] = message;
		roomListView.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1 , roomdata_arr));
		
	}
	
	public void joinRoom(int roomId) {
		this.clientComm.LogInToRoom(this.uiHelperRef.getStOwnUserId(), roomId);
	}

}
