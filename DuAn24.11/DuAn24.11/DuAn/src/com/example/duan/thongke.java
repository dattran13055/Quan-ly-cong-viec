package com.example.duan;

import java.util.ArrayList;


import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

public class thongke extends Activity {
	SQLiteDatabase database;
	ArrayList<String> ar_hienthi = new ArrayList<String>();
	ListView lv_congviec1;
	ArrayAdapter<String> adt_hienthi = null;
	int chuyen;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.thongke);
		lv_congviec1=(ListView)findViewById(R.id.list_cv);
		adt_hienthi = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1,ar_hienthi);
		taodata();
		hienthi();
		
		// Long click => xoa
		lv_congviec1.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				String txt ="";
				String[] ar1;
				ar1 = ar_hienthi.get(arg2).split("-");
				txt = ar1[0];
				ar_hienthi.remove(arg2);
				adt_hienthi.notifyDataSetChanged();
				database.delete("CongViec", "tenviec=?",  new String[]{txt});
				return false;
			}
		});
	// Hien Thi Chi Tiet
		lv_congviec1.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Intent it = new Intent(thongke.this, list_view.class);
				Bundle bundle=new Bundle();
				chuyen = arg2;
				bundle.putInt("chuyen1", chuyen);
				it.putExtra("MyPackage", bundle);
				startActivity(it);
				
				
			}
		});
		
		
}
	public void taodata()
	{
		database = openOrCreateDatabase("QuanLyCongViec1", MODE_PRIVATE, null);
		String taobang = "create table if not exists CongViec(tenviec TEXT,noidung TEXT,dokho TEXT, dateuot DATE)";
		database.execSQL(taobang);
	}
	public void hienthi() {

		lv_congviec1.clearFocus();
		ar_hienthi = new ArrayList<String>();
		Cursor c = database.query("CongViec", null, null, null, null, null,null);
		c.moveToFirst();
		while (c.isAfterLast() == false) 
		{
			ar_hienthi.add(c.getString(0)+"-"+c.getString(2)+"-"+ c.getString(3));
			c.moveToNext();
		}
		c.close();
		adt_hienthi = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_expandable_list_item_1, ar_hienthi);
		lv_congviec1.setAdapter(adt_hienthi);
		adt_hienthi.notifyDataSetChanged();
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stu
		hienthi();
		super.onResume();
	}
	
}