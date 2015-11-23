package com.example.duan;

import java.util.ArrayList;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class thongke extends Activity {
	SQLiteDatabase database;
	ArrayList<String> ar_hienthi = new ArrayList<String>();
	ListView lv_congviec1;
	ArrayAdapter<String> adt_hienthi = null;
	
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
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				String txt ="";
				ar_hienthi.remove(arg2);
				adt_hienthi.notifyDataSetChanged();
				database.delete("CongViec", "tenviec=? ",  new String[]{txt});
				Toast.makeText(getApplicationContext(), "" + arg2, Toast.LENGTH_SHORT).show();
				return false;
			}
		});
		
		

}
	public void taodata()
	{
		database = openOrCreateDatabase("QuanLyCongViec1", MODE_PRIVATE, null);
		String taobang = "create table if not exists CongViec(tenviec TEXT,noidung TEXT,dokho INT, dateuot DATE)";
		database.execSQL(taobang);
	}
	public void hienthi() {

		lv_congviec1.clearFocus();
		ar_hienthi.clear();
		Cursor c = database.query("CongViec", null, null, null, null, null,null);
		c.moveToFirst();
		while (c.isAfterLast() == false) 
		{
			ar_hienthi.add(c.getString(0)+ "\t\t\t" + "-" + "\t\t\t" + c.getString(2)+ "\t\t\t" + "-" + "\t\t\t" + c.getString(3));
			c.moveToNext();
		}
		c.close();
		lv_congviec1.setAdapter(adt_hienthi);
		adt_hienthi.notifyDataSetChanged();
	}
	
}