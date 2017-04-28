package com.tyust.service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.util.EntityUtils;



import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

public class PostService extends Thread{
	
	String urlPath;
	Map<String,String> map;
	Handler handler;
	public PostService(String urlPath,Map<String,String> map,Handler handler){
		this.urlPath=urlPath;
		this.map=map;
		this.handler=handler;
	}
	 @Override
		public void run() {
			// TODO Auto-generated method stub
			super.run();
			try{
			StringBuilder builder=new StringBuilder(); //ƴ���ַ�   
	        //�ó���ֵ   
	        if(map!=null && !map.isEmpty())  
	        {  
	            for(Map.Entry<String, String> param:map.entrySet())  
	            {  
						builder.append(param.getKey()).append('=').append(URLEncoder.encode(param.getValue(), "utf-8")).append('&');
					
	            }  
	            builder.deleteCharAt(builder.length()-1);  
	        }  
	        //�����Content-Length: �����URL�Ķ��������ݳ���   
	        byte b[]=builder.toString().getBytes();  
	        URL url=new URL(urlPath);  
	        HttpURLConnection con=(HttpURLConnection)url.openConnection();  
	        if(null==con){
	        	
	        }
	        con.setRequestMethod("POST");  
	        con.setReadTimeout(5*1000);  
	        con.setDoOutput(true);//���������   
	        con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");//��������   
	        con.setRequestProperty("charset","utf-8");//����   
	        OutputStream outStream=con.getOutputStream();  
	        outStream.write(b);//д������   
	        outStream.flush();//ˢ���ڴ�   
	        outStream.close();  
	        //״̬���ǲ��ɹ�   
	        if(con.getResponseCode()==200)  
	        {  
	        	InputStream is=con.getInputStream();
	        	BufferedReader bw=new BufferedReader(new InputStreamReader(is));
	        	String rst="";
	        	String readLine=null;
	        	while((readLine=bw.readLine())!=null){
	        		rst+=readLine;
	        	}
	        	Message message=handler.obtainMessage();
	        	message.what=111;
	        	message.obj=rst;
	        	handler.sendMessage(message);
	        	bw.close();
	        	is.close();
	        }   
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}  
		}


	}  

