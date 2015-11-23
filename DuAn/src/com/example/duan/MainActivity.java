package com.example.duan;

import android.app.TabActivity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

@SuppressWarnings("deprecation")
public class MainActivity extends TabActivity {
	SQLiteDatabase database;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		TabHost tabHost = getTabHost();
		taodata();


		TabSpec themcv = tabHost.newTabSpec("themcv");
		themcv.setIndicator("Thêm Công Việc");
		Intent photosIntent = new Intent(getBaseContext(), themmoicv.class);
		themcv.setContent(photosIntent);

		TabSpec thongke = tabHost.newTabSpec("thongke");
		thongke.setIndicator("Công Việc Hôm Nay");
		Intent songsIntent = new Intent(getBaseContext(), thongke.class);
		thongke.setContent(songsIntent);

		TabSpec chitiet = tabHost.newTabSpec("chitiet");
		chitiet.setIndicator("Tìm Kiếm");
		Intent videosIntent = new Intent(getBaseContext(), chitiet.class);
		chitiet.setContent((videosIntent));

		tabHost.addTab(themcv);
		tabHost.addTab(thongke);
		tabHost.addTab(chitiet);
	}
	
	
	public void taodata()
	{
		database = openOrCreateDatabase("QuanLyCongViec", MODE_PRIVATE, null);
		String taobang = "create table if not exists CongViec(tenviec TEXT,noidung TEXT,dokho INT, datein DATE, dateuot DATE,dahoanthanh INT)";
		database.execSQL(taobang);
	}

}
