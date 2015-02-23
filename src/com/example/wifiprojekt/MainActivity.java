package com.example.wifiprojekt;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.internal.widget.AdapterViewCompat.OnItemLongClickListener;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity {

	WifiManager wifimanager;
	WifiReceiver wifiReceiver;
	ListView listview;
	String wifiliste[];
	ListAdapter adapter;
	Toast toast1, toast2, toast3;
	List<ScanResult> scanresultate;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		listview = (ListView) findViewById(R.id.listView1);
		wifimanager = (WifiManager) getSystemService(Context.WIFI_SERVICE);

		if (!wifimanager.isWifiEnabled()) {
			toast2 = Toast.makeText(this, "Wlannetze werden gesucht", 5000);
			toast2.setGravity(Gravity.CENTER, 0, 75);
			toast2.show();
			wifimanager.setWifiEnabled(true);
		}
		scanne();
		listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				RssiRechner rssi2 = new RssiRechner();
				Dialog action = new Dialog(MainActivity.this,
						("Wifiname:       " + scanresultate.get(position).SSID)
								.toString()
								+ " \n"
								+ "Netzstärke:    "
								+ rssi2.rechneRSSIinProzent(scanresultate
										.get(position).level)
								+ " %"
								+ " \n"
								+ "Macadresse:  "
								+ scanresultate.get(position).BSSID);
				
				action.showDialog();
				String ssid = scanresultate.get(position).BSSID;

			}
		});

		if (listview != null) {
			toast1 = Toast.makeText(getApplicationContext(),
					"Bitte klicken Sie\ndas gewünschte Wlannetz", 10000);
			toast1.setGravity(Gravity.CENTER, 0, 75);
			toast1.show();

		}
	}

	public void scanne() {
		wifiReceiver = new WifiReceiver();
		registerReceiver(wifiReceiver, new IntentFilter(
				WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));
		wifimanager.startScan();

	}

	public void restartActivity() {
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
		unregisterReceiver(wifiReceiver);
		super.onPause();
	}

	protected void onResume() {
		registerReceiver(wifiReceiver, new IntentFilter(
				WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));
		super.onResume();
	}

	class WifiReceiver extends BroadcastReceiver {
		public void onReceive(Context c, Intent intent) {
			scanresultate = wifimanager.getScanResults();
			wifiliste = new String[scanresultate.size()];

			Collections.sort(scanresultate, new Comparator<ScanResult>() {

				@Override
				public int compare(ScanResult lhs, ScanResult rhs) {
					// TODO Auto-generated method stub
					return (lhs.level > rhs.level ? -1
							: (lhs.level == rhs.level ? 0 : 1));

				}
			});
			RssiRechner rssi = new RssiRechner();

			for (int i = 0; i < scanresultate.size(); i++) {
				wifiliste[i] = ((scanresultate.get(i)).SSID.toString() + "  "
						+ rssi.rechneRSSIinProzent(scanresultate.get(i).level) + "%");

			}

			List<String> wifiliste2 = new ArrayList<String>(
					Arrays.asList(wifiliste));
			listview.setAdapter(new ArrayAdapter<String>(
					getApplicationContext(),
					android.R.layout.simple_list_item_1, wifiliste2));
			

			// adapter = new ListAdapter(MainActivity.this, wifiliste2);
			// listview.setAdapter(adapter);
			// adapter.notifyDataSetChanged();

			if (listview != null) {
				toast3 = Toast.makeText(getApplicationContext(),
						R.string.update_info, 1000);
				toast3.setGravity(Gravity.CENTER, 0, 75);
				toast3.show();
			}
		}

	}

}
