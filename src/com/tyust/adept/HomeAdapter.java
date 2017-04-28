package com.tyust.adept;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import com.tyust.activity.R;
import com.tyust.entry.Aritle;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.support.v4.util.LruCache;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class HomeAdapter extends BaseAdapter {
	private List<Aritle> list;
	private ListView listview;
	private LruCache<String, BitmapDrawable> mImageCache; 
	public HomeAdapter(List<Aritle> list){
		super();
		this.list=list;
		int maxCache = (int) Runtime.getRuntime().maxMemory();  
        int cacheSize = maxCache / 8;
		mImageCache = new LruCache<String, BitmapDrawable>(cacheSize) {  
            @SuppressLint("NewApi")
			@Override  
            protected int sizeOf(String key, BitmapDrawable value) {  
                return value.getBitmap().getByteCount();  
            }  
        };  
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		if (listview == null) {  
            listview = (ListView) parent;  
        }  
        ViewHolder holder = null;  
        if (convertView == null) {  
            convertView = LayoutInflater.from(parent.getContext()).inflate(  
                    R.layout.main_home_page_textview, null);  
            holder = new ViewHolder();  
            holder.iv= (ImageView) convertView.findViewById(R.id.iv_main_pic);  
            holder.title = (TextView) convertView.findViewById(R.id.tv_main_title);  
            holder.summary = (TextView) convertView.findViewById(R.id.tv_main_desc);  
            holder.date=(TextView) convertView.findViewById(R.id.tv_main_date);
            convertView.setTag(holder);  
        } else {  
            holder = (ViewHolder) convertView.getTag();  
        }  
        Aritle aritle = list.get(position);  
        holder.title.setText(aritle.getTitle());  
        holder.summary.setText(aritle.getDesc());  
        holder.iv.setTag(aritle.getUrl());  
        // 如果本地已有缓存，就从本地读取，否则从网络请求数据  
        if (mImageCache.get(aritle.getUrl()) != null) {  
            holder.iv.setImageDrawable(mImageCache.get(aritle.getUrl()));  
        } else {  
            ImageTask it = new ImageTask();  
            it.execute(aritle.getUrl());  
        }  
        return convertView;  
    }  
  
    class ViewHolder {  
        ImageView iv;  
        TextView title, summary,date;  
    }  
  
    class ImageTask extends AsyncTask<String, Void, BitmapDrawable> {  
  
        private String imageUrl;  
  
        @Override  
        protected BitmapDrawable doInBackground(String... params) {  
            imageUrl = params[0];  
            Bitmap bitmap = downloadImage();  
            BitmapDrawable db = new BitmapDrawable(listview.getResources(),  
                    bitmap);  
            // 如果本地还没缓存该图片，就缓存  
            if (mImageCache.get(imageUrl) == null) {  
                mImageCache.put(imageUrl, db);  
            }  
            return db;  
        }  
        
        @Override  
        protected void onPostExecute(BitmapDrawable result) {  
            // 通过Tag找到我们需要的ImageView，如果该ImageView所在的item已被移出页面，就会直接返回null  
            ImageView iv = (ImageView) listview.findViewWithTag(imageUrl);  
            if (iv != null && result != null) {  
                iv.setImageDrawable(result);  
            }  
        }  
  
        /** 
         * 根据url从网络上下载图片 
         *  
         * @return 
         */  
        private Bitmap downloadImage() {  
            HttpURLConnection con = null;  
            Bitmap bitmap = null;  
            try {  
                URL url = new URL(imageUrl);  
                con = (HttpURLConnection) url.openConnection();  
                con.setConnectTimeout(5 * 1000);  
                con.setReadTimeout(10 * 1000);  
                Log.i("xxxxxxxxxxxxxxxxx", imageUrl);
                bitmap = BitmapFactory.decodeStream(con.getInputStream());  
            } catch (MalformedURLException e) {  
               Log.e("downloading xxxxxxx", e.getMessage())  ;
            } catch (IOException e) {  
                Log.e("downloading.......", e.getMessage())  ;
            } finally {  
                if (con != null) {  
                    con.disconnect();  
                }  
            }  
  
            return bitmap;  
        }  
  
    }  
}
