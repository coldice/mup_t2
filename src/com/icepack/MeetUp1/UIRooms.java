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
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.tvrooms);
        roomListView = (ListView)findViewById(R.id.lsv_roomlist);
        btnRefreshList = (Button)findViewById(R.id.btn_rmrefresh);
        
        roomdata_arr = new String[1];
        roomdata_arr[0] = "loading...";
        roomList = new ArrayList<MURoom>();
        //ad
        //roomListView.setAdapter(adapter)
        //roomListView.setListAdapter(new ArrayAdapter<String>(this, R.layout.list_item, COUNTRIES));
        roomListView.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1 , roomdata_arr));
        
        
        //OnClick Listeners
        btnRefreshList.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	updateRoomList();
            	
            	
            }
        });
        
        roomListView.setClickable(true);
        roomListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

        	  @Override
        	  public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {

        	    Object o = roomListView.getItemAtPosition(position);
        	    String pname = o.getClass().toString();
        	    
        	    showRoomListMessage("entered: "+pname);
        	  }
        	});
	}
	
	public void updateRoomList() {
		//refresh the roomlist
		roomList.clear();
		
		//TODO: implement server get here
		
		roomList.add(new MURoom(-1, "testentry"));
		//
		
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
		roomList.clear();
		roomList.add(new MURoom(-1, message));
	}

}
