package com.example.duan;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class list_view extends Activity {
	SQLiteDatabase database;
	TextView tencv,noidung,ngayhoanthanh,dokho;
	ListView lv_congviec1;
	ArrayList<String> ar_hienthi = new ArrayList<String>();
	ArrayAdapter<String> adt_hienthi = null;
	int a;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list_view);
		taodata();
		Intent callerIntent=getIntent();
		Bundle packageFromCaller = callerIntent.getBundleExtra("MyPackage");
		a=packageFromCaller.getInt("chuyen1");
		lv_congviec1=(ListView)findViewById(R.id.list_cv);
		adt_hienthi = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1,ar_hienthi);
		tencv = (TextView)findViewById(R.id.tv_tencv);
		noidung = (TextView)findViewById(R.id.tv_noidung);
		ngayhoanthanh = (TextView)findViewById(R.id.tv_ngayhoanthanh);
		dokho = (TextView)findViewById(R.id.tv_dokho);
		hienthi();
		
		

}
	
	public void taodata()
	{
		database = openOrCreateDatabase("QuanLyCongViec1", MODE_PRIVATE, null);
		String taobang = "create table if not exists CongViec(tenviec TEXT,noidung TEXT,dokho TEXT, dateuot DATE)";
		database.execSQL(taobang);
	}
	
	public void hienthi() {
		String[] ar1;
		ar_hienthi = new ArrayList<String>();
		Cursor c = database.query("CongViec", null, null, null, null, null,null);
		c.moveToFirst();
		while (c.isAfterLast() == false) 
		{
			ar_hienthi.add(c.getString(0)+"-"+c.getString(1)+"-"+ c.getString(2)+"-"+ c.getString(3));
			c.moveToNext();
			
		}
		ar1 = ar_hienthi.get(a).split("-");
		tencv.setText(ar1[0]);
		noidung.setText(ar1[1]);
		dokho.setText(ar1[2]);
		ngayhoanthanh.setText(ar1[3]);
	}

}
