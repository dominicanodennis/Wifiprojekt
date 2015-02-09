package com.example.wifiprojekt;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;

import java.util.List;



import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity {

	WifiManager mainWifiObj;
	WifiScanReceiver wifiReciever;
	ListView list;
	String wifis[];
	Toast toast;

	@SuppressLint("ShowToast")
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		list = (ListView) findViewById(R.id.listView1);
		mainWifiObj = (WifiManager) getSystemService(Context.WIFI_SERVICE);
		wifiReciever = new WifiScanReceiver();
		mainWifiObj.startScan();

		toast = Toast.makeText(this, "Bitte klicken das gewünschte Wlannetz",
				5000);
		toast.setGravity(Gravity.CENTER, 0, 75);
		toast.show();

	}
	public void restartActivity(){
		finish();
		startActivity(getIntent());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			restartActivity();
			return true;
		}

		return super.onOptionsItemSelected(item);
	}

	protected void onPause() {
		unregisterReceiver(wifiReciever);
		super.onPause();
	}

	protected void onResume() {
		registerReceiver(wifiReciever, new IntentFilter(
				WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));
		super.onResume();
	}

	class WifiScanReceiver extends BroadcastReceiver {
		@SuppressLint("UseValueOf")
		public void onReceive(Context c, Intent intent) {
			List<ScanResult> wifiScanList = mainWifiObj.getScanResults();
			wifis = new String[wifiScanList.size()];
			for (int i = 0; i < wifiScanList.size(); i++) {
				wifis[i] = ((wifiScanList.get(i)).SSID.toString()+ " "+ wifiScanList.get(i).level);
			}

			list.setAdapter(new ArrayAdapter<String>(getApplicationContext(),
					android.R.layout.simple_list_item_1, wifis));
		}
	}

}