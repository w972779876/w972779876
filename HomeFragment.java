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
	        	 JavaType javaType = mapper.getTypeFactory().constructParametricType(List.class, Map.class);  
	        	 list=(List<HashMap<String,String>>) mapper.readValue( msg.obj.toString(), javaType);
	        	 for(HashMap m:list){
	        		 String url=(String) m.get("url");
	        		 if(url!=null&&url.length()!=0){
	        			 getBitmap(url);
	        		 }
	        	 }
	        	 Log.i("xxx", list.toString());
				} catch (JsonParseException e) {
					// TODO Auto-generated catch block
					Log.e("jsonerror", "JsonParseException");
				} catch (JsonMappingException e) {
					// TODO Auto-generated catch block
					Log.e("jsonerror", "JsonMappingException");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					Log.e("jsonerror", "IoExcept");
				}
	         Log.i("xxx",(String)msg.obj);
	   	  SimpleAdapter adapter = new SimpleAdapter(getActivity(), 
				     list,//数据源 
				     R.layout.main_home_page_textview,//显示布局
				     new String[] {"title", "desc","date","url"}, //数据源的属性字段
				     new int[] {R.id.tv_main_title,R.id.tv_main_desc,R.id.tv_main_date,R.id.iv_main_pic}); //布局里的控件id
				    //添加并且显示
	   	  			adapter.setViewBinder(new ViewBinder() {
						
						@Override
						public boolean setViewValue(View view, Object data,
								String textRepresentation) {
							// TODO Auto-generated method stub
							if(view instanceof ImageView  && data instanceof Bitmap){  
			                    ImageView iv = (ImageView) view;  
			                  
			                    iv.setImageBitmap((Bitmap) data);  
			                    return true;  
			                }else  
			                return false;
						}
					});
				    listView.setAdapter(adapter);
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
	      List<HashMap<String, String>> mylist = new ArrayList<HashMap<String, String>>();
		  for(int i=0;i<10;i++) {
		      HashMap<String, String> map = new HashMap<String, String>();
		      map.put("itemTitle", "This is Title");
		      map.put("itemText", "This is text");
		      map.put("data","2017-05-03" );
		      mylist.add(map);
		  }
		  Log.i("xxx",mylist.toString());
//	      for(Aritle atl:list){
//	    	  HashMap<String, String> map = new HashMap<String, String>();
//	    	  map.put("title", atl.getTitle());
//	    	  map.put("desc", atl.getDesc());
//	    	  map.put("data", atl.getDate());
//	    	  mylist.add(map);
//	      }
	
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
	private Bitmap getBitmap(String imageUrl) {
		// TODO Auto-generated method stub
		Bitmap mBitmap = null;  
        try {  
            URL url = new URL(imageUrl);  
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();  
            InputStream is = conn.getInputStream();  
            mBitmap = BitmapFactory.decodeStream(is);  
              
        } catch (MalformedURLException e) {  
            e.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
          
        return mBitmap;  
	}
}
