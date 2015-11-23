package com.example.duan;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

public class themmoicv extends Activity {
	SQLiteDatabase database;
	TextView txt_Date,txt_Time;
	EditText edt_congviec,edt_noidung;
	Button btn_Date,btn_Time,btn_luucv;
	ListView lv_congviec;
	Calendar cal;
	Date dateFinish;
	Date hourFinish;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.themmoicv);
		txt_Date=(TextView) findViewById(R.id.txtdate);
		txt_Time=(TextView) findViewById(R.id.txttime);
		edt_congviec=(EditText) findViewById(R.id.editcongviec);
		edt_noidung=(EditText) findViewById(R.id.editnoidung);
		btn_Date=(Button) findViewById(R.id.btndate);
		btn_Time=(Button) findViewById(R.id.btntime);
		btn_luucv=(Button) findViewById(R.id.btncongviec);
		lv_congviec=(ListView) findViewById(R.id.lvcongviec);
		taodata();
		getDefaultInfor();
		addEventFormWidgets();
		dokho();
		btn_luucv.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				themcv1();			
			}
		});
	}

	// Ngày Giờ
	public void getDefaultInfor()
	{
		cal=Calendar.getInstance();
		SimpleDateFormat dft=null;
		dft=new SimpleDateFormat("dd/MM/yyyy",Locale.getDefault());
		String strDate=dft.format(cal.getTime());
		txt_Date.setText(strDate);
		dft=new SimpleDateFormat("hh:mm a",Locale.getDefault());
		String strTime=dft.format(cal.getTime());
		txt_Time.setText(strTime);
		dft=new SimpleDateFormat("HH:mm",Locale.getDefault());
		txt_Time.setTag(dft.format(cal.getTime()));

		edt_congviec.requestFocus();

		dateFinish=cal.getTime();
		hourFinish=cal.getTime();
	}
	public void addEventFormWidgets()
	{
		btn_Date.setOnClickListener(new MyButtonEvent());
		btn_Time.setOnClickListener(new MyButtonEvent());
	}
	private class MyButtonEvent implements OnClickListener
	{
		@Override
		public void onClick(View v) {
			switch(v.getId())
			{
			case R.id.btndate:
				showDatePickerDialog();
				break;
			case R.id.btntime:
				showTimePickerDialog();
				break;
			}
		}

	}
	public void showDatePickerDialog()
	{
		OnDateSetListener callback=new OnDateSetListener() {
			public void onDateSet(DatePicker view, int year,
					int monthOfYear,
					int dayOfMonth) {
				//Mỗi lần thay đổi ngày tháng năm thì cập nhật lại TextView Date
				txt_Date.setText(
						(dayOfMonth) +"/"+(monthOfYear+1)+"/"+year);
				//Lưu vết lại biến ngày hoàn thành
				cal.set(year, monthOfYear, dayOfMonth);
				dateFinish=cal.getTime();
			}
		};
		String s=txt_Date.getText()+"";
		String strArrtmp[]=s.split("/");
		int ngay=Integer.parseInt(strArrtmp[0]);
		int thang=Integer.parseInt(strArrtmp[1])-1;
		int nam=Integer.parseInt(strArrtmp[2]);
		DatePickerDialog pic=new DatePickerDialog(
				themmoicv.this, 
				callback, nam, thang, ngay);
		pic.setTitle("Chọn ngày hoàn thành");
		pic.show();
	}
	
	public void showTimePickerDialog()
	{
		OnTimeSetListener callback=new OnTimeSetListener() {
			public void onTimeSet(TimePicker view, 
					int hourOfDay, int minute) {
				//Xử lý lưu giờ và AM,PM
				String s=hourOfDay +":"+minute;
				int hourTam=hourOfDay;
				if(hourTam>12)
					hourTam=hourTam-12;
				txt_Time.setText
				(hourTam +":"+minute +(hourOfDay>12?" PM":" AM"));
				//lưu giờ thực vào tag
				txt_Time.setTag(s);
				//lưu vết lại giờ vào hourFinish 
				cal.set(Calendar.HOUR_OF_DAY, hourOfDay);
				cal.set(Calendar.MINUTE, minute);
				hourFinish=cal.getTime();
			}
		};
		String s=txt_Time.getTag()+"";
		String strArr[]=s.split(":");
		int gio=Integer.parseInt(strArr[0]);
		int phut=Integer.parseInt(strArr[1]);
		TimePickerDialog time=new TimePickerDialog(
				themmoicv.this, 
				callback, gio, phut, true);
		time.setTitle("Chọn giờ hoàn thành");
		time.show();
	}
	
	public void taodata()
	{
		database = openOrCreateDatabase("QuanLyCongViec1", MODE_PRIVATE, null);
		String taobang = "create table if not exists CongViec(tenviec TEXT,noidung TEXT,dokho INT, dateuot DATE)";
		database.execSQL(taobang);
	}
	public void themcv1() {
		ContentValues values = new ContentValues();
		values.put("tenviec", edt_congviec.getText().toString());
		values.put("noidung", edt_noidung.getText().toString());
		//values.put("dateout", txt_Date.getText().toString());
		database.insert("CongViec", null, values);
		Toast.makeText(getApplicationContext(), "Luu Thanh Cong", Toast.LENGTH_SHORT).show();
	}
	public void dokho()
	{
		Spinner dokho;
		dokho = (Spinner)findViewById(R.id.sp_dokho);
		ArrayList<String> ar_dokho = new ArrayList<String>();
		ArrayAdapter<String> adt_dokho = null;
		adt_dokho = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1,ar_dokho);
		ar_dokho.add("Khó");
		ar_dokho.add("Thường");
		ar_dokho.add("Dễ");
		dokho.setAdapter(adt_dokho);
	}
}
	

	
