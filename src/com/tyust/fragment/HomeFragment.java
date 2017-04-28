package com.tyust.fragment;





import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tyust.activity.CaptureActivity;
import com.tyust.activity.R;
import com.tyust.adept.HomeAdapter;
import com.tyust.entry.Aritle;
import com.tyust.service.PostService;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.SimpleAdapter.ViewBinder;
import android.widget.Toast;


public class HomeFragment extends Fragment implements OnClickListener{
	
	private Button bt_home_qgcode;
	private ListView listView;
	List<HashMap<String, String>> list;
	Handler handle=new Handler(){
		@Override
	    public void handleMessage(Message msg) {
	    	super.handleMessage(msg);
	        switch (msg.what)
	       {
	         case 111:
	        	 try {
	        	 ObjectMapper mapper=new ObjectMapper();
	        	 JavaType javaType = mapper.getTypeFactory().constructParametricType(List.class, Aritle.class); 
	             List<Aritle> list =  (List<Aritle>)mapper.readValue((String)msg.obj, javaType); 
	             Log.i("xxx",(String)msg.obj);
	             HomeAdapter adapter=new HomeAdapter(list);
	             listView.setAdapter(adapter);
	             listView.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {
						// TODO Auto-generated method stub
						Aritle aritle=(Aritle) parent.getAdapter().getItem(position);
						String atlid=aritle.getId();
						Toast.makeText(view.getContext(),atlid, 0).show();
					}
				});
	        	 Log.i("xxx", list.toString());
				} catch (JsonParseException e) {
					// TODO Auto-generated catch block
					Log.e("jsonerror", "JsonParseException");
				} catch (JsonMappingException e) {
					// TODO Auto-generated catch block
					Log.e("jsonerror", e.getMessage());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					Log.e("jsonerror", "IoExcept");
				}
	         break;
	       }
		}

		
	};
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
	      View view=View.inflate(getActivity(), R.layout.main_home_page, null);
	      bt_home_qgcode=(Button) view.findViewById(R.id.bt_home_qgcode);
	      bt_home_qgcode.setOnClickListener(this);
	      listView=(ListView) view.findViewById(R.id.lv_second_listView);
	      PostService post=new PostService("http://10.0.2.2:8080/PartProject/UserServlet", null, handle);
	      post.start();
	      
	      return view;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(v.getId()==R.id.bt_home_qgcode){
			Intent intent=new Intent(getActivity(),CaptureActivity.class);
			startActivity(intent);
		}
		
	}


}
