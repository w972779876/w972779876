package com.tyust.activity;


import java.util.ArrayList;
import java.util.List;


import com.tyust.fragment.FavoFragment;
import com.tyust.fragment.HomeFragment;
import com.tyust.fragment.InfoFragment;
import com.tyust.fragment.SetFragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Toast;



@SuppressLint("ResourceAsColor")
public class MainActivity extends FragmentActivity implements OnClickListener{
	private ViewPager vp_content;
	private View homeView,infoView,collectView,settingView;
	private List<Fragment> viewList;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		vp_content=(ViewPager) findViewById(R.id.vp_content);
		LayoutInflater inflater=getLayoutInflater();  
       homeView=findViewById(R.id.ll_main_home);
       infoView=findViewById(R.id.ll_main_product);
       collectView=findViewById(R.id.ll_main_save);
       settingView=findViewById(R.id.ll_mian_setting);
       homeView.setOnClickListener(this);
       settingView.setOnClickListener(this);
       infoView.setOnClickListener(this);
       collectView.setOnClickListener(this);
       viewList=new ArrayList<Fragment>();
       viewList.add(new HomeFragment());
       viewList.add(new InfoFragment());
       viewList.add(new FavoFragment());
       viewList.add(new SetFragment());
       vp_content.setAdapter(new MenuAdept(getSupportFragmentManager()));
       vp_content.setCurrentItem(0);
       homeView.setBackgroundColor(R.color.bright);
       vp_content.setOnPageChangeListener(new OnPageChangeListener() {
		
		@Override
		public void onPageSelected(int i) {
			// TODO Auto-generated method stub
			selectColor(i);
		}
		
		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void onPageScrollStateChanged(int arg0) {
			// TODO Auto-generated method stub
			
		}
	});
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()){
		case R.id.ll_main_home:vp_content.setCurrentItem(0);break;
		case R.id.ll_main_product:vp_content.setCurrentItem(1);break;
		case R.id.ll_main_save:vp_content.setCurrentItem(2);break;
		case R.id.ll_mian_setting:vp_content.setCurrentItem(3);break;
		}
	}
	private void selectColor(int i) {  
        // TODO Auto-generated method stub  
        switch (i) {  
        case 0:  
        	homeView.setBackgroundResource(R.color.bright);  
        	infoView.setBackgroundResource(R.color.dark);  
        	collectView.setBackgroundResource(R.color.dark);  
        	settingView.setBackgroundResource(R.color.dark);  
            break;  
        case 1:  
        	homeView.setBackgroundResource(R.color.dark);  
        	infoView.setBackgroundResource(R.color.bright);  
        	collectView.setBackgroundResource(R.color.dark);  
        	settingView.setBackgroundResource(R.color.dark);  
            break;  
        case 2:  
        	homeView.setBackgroundResource(R.color.dark);  
        	infoView.setBackgroundResource(R.color.dark);  
        	collectView.setBackgroundResource(R.color.bright);  
        	settingView.setBackgroundResource(R.color.dark);  
  
            break;  
        case 3:  
  
        	homeView.setBackgroundResource(R.color.dark);  
        	infoView.setBackgroundResource(R.color.dark);  
        	collectView.setBackgroundResource(R.color.dark);  
        	settingView.setBackgroundResource(R.color.bright);  
            break;  
        }
	}
	class MenuAdept extends FragmentPagerAdapter {

		public MenuAdept(FragmentManager fm) {
			super(fm);
			// TODO Auto-generated constructor stub
		}

		@Override
		public Fragment getItem(int arg0) {
			// TODO Auto-generated method stub
			return viewList.get(arg0);
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return viewList.size();
		}
		
	}
}

