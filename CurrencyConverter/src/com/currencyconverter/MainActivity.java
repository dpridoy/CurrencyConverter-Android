package com.currencyconverter;

import com.currencyconverter.Connection;
import com.currencyconverter.MainActivity;
import com.currencyconverter.MainActivity.MyBroadcastReceiver;

import android.support.v7.app.ActionBarActivity;
import android.annotation.TargetApi;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class MainActivity extends ActionBarActivity {
	MyBroadcastReceiver br;
	public String givenValue;
	public Boolean bdtous;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		br=new MyBroadcastReceiver();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		IntentFilter filter=new IntentFilter(MyBroadcastReceiver.RESPONSE);
		filter.addCategory(Intent.CATEGORY_DEFAULT);
		registerReceiver(br, filter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	public void BDTOUS(View v){
		bdtous=true;
		EditText et=(EditText)findViewById(R.id.editText1);
		givenValue=et.getText().toString();
		Intent i=new Intent(MainActivity.this,Connection.class);
		i.putExtra(Connection.GivenValue, givenValue);
		i.putExtra(Connection.Type, bdtous);
		startService(i);
	}
	
	public void USTOBD(View v){
		bdtous=false;
		EditText et=(EditText)findViewById(R.id.editText1);
		givenValue=et.getText().toString();
		Intent i=new Intent(MainActivity.this,Connection.class);
		i.putExtra(Connection.GivenValue, givenValue);
		i.putExtra(Connection.Type, bdtous);
		startService(i);
	}
	
	public class MyBroadcastReceiver extends BroadcastReceiver{
		public static final String RESPONSE="com.converter.intent.action.PROCESS_RESPONSE";

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			String convertedValue=intent.getStringExtra(Connection.Value);
			TextView tv=(TextView)findViewById(R.id.textView1);
			tv.setText(convertedValue);
		}
	}
}
