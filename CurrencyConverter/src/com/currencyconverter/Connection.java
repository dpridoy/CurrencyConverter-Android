package com.currencyconverter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import com.currencyconverter.MainActivity.MyBroadcastReceiver;

import android.app.IntentService;
import android.content.Intent;

public class Connection extends IntentService {
	public String val;
	public static String Value="convertedValue",GivenValue="myvalue",Type="type";
	public boolean bdtous;
	private String dataString="";
	private String rate="";
	int a,b;
	private String[] cRate=new String[2];

	public Connection() {
		super("Connection");
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		// TODO Auto-generated method stub
		try {
			bdtous=intent.getExtras().getBoolean(Connection.Type);
			val=intent.getExtras().getString(Connection.GivenValue);
			HttpClient httpclient=new DefaultHttpClient();
			HttpGet httpget=new HttpGet("http://www.hrhafiz.com/converter/index.php");
			HttpResponse response=httpclient.execute(httpget);
			InputStream content=response.getEntity().getContent();
			
			BufferedReader buffer=new BufferedReader(new InputStreamReader(content));
			String s="";
			while((s=buffer.readLine())!=null){
				dataString+=s;
			}
			int i=0;
			for(String temp:dataString.split(",",2)){
				cRate[i]=temp;
				i++;
			}
			if(bdtous){
				rate=cRate[1].substring(cRate[1].indexOf(":")+1,cRate[1].indexOf("}"));
				val=String.valueOf(Double.valueOf(val)*Double.valueOf(rate));
			}
			else{
				rate=cRate[0].substring(cRate[0].indexOf(":")+1,cRate[0].indexOf("}"));
				val=String.valueOf(Double.valueOf(val)*Double.valueOf(rate));
			}
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Intent broadcastIntent =new Intent();
		broadcastIntent.setAction(MyBroadcastReceiver.RESPONSE);
		broadcastIntent.addCategory(Intent.CATEGORY_DEFAULT);
		broadcastIntent.putExtra(Value, val);
		sendBroadcast(broadcastIntent);
	}

}
