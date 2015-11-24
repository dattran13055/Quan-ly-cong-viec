package com.example.duan;

import java.util.ArrayList;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

public class chitiet extends Activity {
	SQLiteDatabase database;
	// LV
	ListView lv_timkiem;
	ArrayList<String> ar_timkiem = new ArrayList<String>();
	ArrayAdapter<String> adt_timkiem = null;
	Spinner sp_timkiem;
	ArrayList<String> ar_timkiem_sp = new ArrayList<String>();
	ArrayAdapter<String> adt_timkiem_sp = null;
	String timkiem_sp;
	Button tim_ngay,tim_dokho,tim_tieude;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.chitiet);
		lv_timkiem = (ListView)findViewById(R.id.lv_timkiem);
		sp_timkiem = (Spinner)findViewById(R.id.sp_timkiem);
		adt_timkiem = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1,ar_timkiem);
		adt_timkiem_sp =  new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1,ar_timkiem_sp);
		tim_ngay = (Button)findViewById(R.id.btn_find_ngay);
		tim_dokho=(Button)findViewById(R.id.btn_find_sp);
		tim_tieude=(Button)findViewById(R.id.btn_find_tieude);
		// Lam Sp
		ar_timkiem_sp.add("Khó");
		ar_timkiem_sp.add("Thường");
		ar_timkiem_sp.add("Dễ");
		sp_timkiem.setAdapter(adt_timkiem_sp);
		//<----
		taodata();
		suly_sp();
		// Lam Spiner
		tim_dokho.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//timhtheodokho();
				
			}
		});
		
		
}
	public void taodata()
	{
		database = openOrCreateDatabase("QuanLyCongViec1", MODE_PRIVATE, null);
		String taobang = "create table if not exists CongViec(tenviec TEXT,noidung TEXT,dokho TEXT, dateuot DATE)";
		database.execSQL(taobang);
	}
	public void timtheongay()
	{
		
		
	}
	public void timhtheodokho()
	{
		Cursor c = database.query("CongViec",null, null, null, null, null,null);
		c.moveToFirst();
		while (c.isAfterLast() == false) 
		{
			if(c.getString(2)==timkiem_sp){
			ar_timkiem.add(c.getString(0)+"-"+ c.getString(2)+"-"+ c.getString(3));
			c.moveToNext();
			}
		};
		c.close();
		lv_timkiem.setAdapter(adt_timkiem);
		adt_timkiem.notifyDataSetChanged();
		Toast.makeText(getApplicationContext(),timkiem_sp,Toast.LENGTH_SHORT).show();
	}
	public void timtheotieude()
	{
		
		
	}
	public void suly_sp()
	{
		sp_timkiem.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				timkiem_sp=ar_timkiem_sp.get(arg2);
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				timkiem_sp=ar_timkiem_sp.get(0);
				
			}
		});
}
}