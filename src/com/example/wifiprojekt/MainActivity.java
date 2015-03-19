package com.example.wifiprojekt;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity {

	WifiManager wifimanager;
	WifiReceiver wifiReceiver;
	ListView listview;
	String wifiliste[];
	ListAdapter adapter;
	Toast toast1, toast2, toast3, toast4;
	List<ScanResult> scanresultate;
	int netId;
	String bssid, ssid;
	ScanResult scanresult;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		listview = (ListView) findViewById(R.id.listView1);
		wifimanager = (WifiManager) getSystemService(Context.WIFI_SERVICE);

		if (!wifimanager.isWifiEnabled()) {
			toast4 = Toast.makeText(this, "schalte Wlan ein", 1500);
			toast4.setGravity(Gravity.CENTER, 0, 75);
			toast4.show();
			toast2 = Toast.makeText(this, "Wlannetze werden gesucht", 5000);
			toast2.setGravity(Gravity.CENTER, 0, 75);
			toast2.show();
			wifimanager.setWifiEnabled(true);

		} else {

			Helperclass helper1 = new Helperclass();
			helper1.disableAllNetworks(this.netId, this.wifimanager);

		}

		Helperclass helper2 = new Helperclass();
		helper2.disableAllNetworks(this.netId, this.wifimanager);

		scanne();

		listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// RssiRechner rssi2 = new RssiRechner();

				bssid = scanresultate.get(position).BSSID;
				ssid = scanresultate.get(position).SSID;

				// eventuelle if-abfrage ob bssid !null ist um zu verhindern dass 
				// TrackingActivity startet ohne bssid
				finish();
				Intent intent = new Intent(MainActivity.this,
						TrackingActivity.class);
				intent.putExtra("wifiname", bssid);
				startActivity(intent);

			}
		});

		if (listview != null) {
			toast1 = Toast.makeText(getApplicationContext(),
					"Bitte klicken Sie\ndas gewünschte Wlannetz", 10000);
			toast1.setGravity(Gravity.CENTER, 0, 75);
			toast1.show();

		}
	}

	public void disableAllNetworks() {
		List<WifiConfiguration> wificonfigliste = wifimanager
				.getConfiguredNetworks();

		for (WifiConfiguration config : wificonfigliste) {
			this.netId = config.networkId;
			wifimanager.disableNetwork(this.netId);
		}

	}

	public void enableAllNetworks() {
		List<WifiConfiguration> wificonfigliste = wifimanager
				.getConfiguredNetworks();
		for (WifiConfiguration config : wificonfigliste) {
			this.netId = config.networkId;
			wifimanager.enableNetwork(this.netId, true);
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
		Helperclass helper3 = new Helperclass();
		helper3.disableAllNetworks(this.netId, this.wifimanager);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
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

	@Override
	protected void onStop() {
		Helperclass helper4 = new Helperclass();
		helper4.enableAllNetworks(this.netId, this.wifimanager);
		enableAllNetworks();
		super.onStop();
	}

	@Override
	protected void onRestart() {
		if (!wifimanager.isWifiEnabled())
			wifimanager.setWifiEnabled(true);
		Helperclass helper5 = new Helperclass();
		helper5.disableAllNetworks(this.netId, this.wifimanager);
		super.onRestart();
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

			if (listview != null) {
				toast3 = Toast.makeText(getApplicationContext(),
						R.string.update_info, 1000);
				toast3.setGravity(Gravity.CENTER, 0, 75);
				toast3.show();
			}
		}

	}

}
