package com.example.wifiprojekt;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.example.wifiprojekt.MainActivity.WifiReceiver;

import android.support.v7.app.ActionBarActivity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class TrackingActivity extends ActionBarActivity {

	String empfangSSID;
	TextView textfeld1, textfeld2;
	WifiManager wifimanager;
	WifiReceiver2 wifiReceiver;
	String wifiliste[];
	List<ScanResult> scanresultate;
	int netId, wifilevel;
	String bssid;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tracking);

		wifimanager = (WifiManager) getSystemService(WIFI_SERVICE);
		Intent intent = getIntent();
		empfangSSID = intent.getExtras().getString("wifiname");

		textfeld1 = (TextView) findViewById(R.id.textView1);
		textfeld1.setText(empfangSSID);
		textfeld2 = (TextView) findViewById(R.id.textView2);

		disableAllNetworks();
		scanne();
		disableAllNetworks();
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
		wifiReceiver = new WifiReceiver2();
		registerReceiver(wifiReceiver, new IntentFilter(
				WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));
		wifimanager.startScan();

	}

	@Override
	public void onBackPressed() {

		Intent intent = new Intent(this, MainActivity.class);
				
		startActivity(intent);

		super.onBackPressed();
	}

	protected void onPause() {
		unregisterReceiver(wifiReceiver);
		super.onPause();
	}

	@Override
	protected void onStop() {
		enableAllNetworks();
		super.onStop();
	}

	@Override
	protected void onRestart() {
		if (!wifimanager.isWifiEnabled()) {
			wifimanager.setWifiEnabled(true);
			disableAllNetworks();
		} else {
			disableAllNetworks();
		}

		super.onRestart();
	}

	protected void onResume() {
		registerReceiver(wifiReceiver, new IntentFilter(
				WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));
		super.onResume();
	}

	class WifiReceiver2 extends BroadcastReceiver {
		public void onReceive(Context c, Intent intent) {
			scanresultate = wifimanager.getScanResults();
			wifiliste = new String[scanresultate.size()];

			// Collections.sort(scanresultate, new Comparator<ScanResult>() {
			//
			// @Override
			// public int compare(ScanResult lhs, ScanResult rhs) {
			// return (lhs.level > rhs.level ? -1
			// : (lhs.level == rhs.level ? 0 : 1));
			//
			// }
			// });
			RssiRechner rssi = new RssiRechner();

			for (int i = 0; i < scanresultate.size(); i++) {
				wifiliste[i] = ((scanresultate.get(i)).SSID.toString() + "  "
						+ rssi.rechneRSSIinProzent(scanresultate.get(i).level) + "%");
				//
				// if (scanresultate.get(i) != null
				// && sca

				// wifilevel =
				// rssi.rechneRSSIinProzent(scanresultate.get(i).level);
				// textfeld2.setText(wifilevel);

				textfeld2.setText(wifiliste[0].toString());

			}

		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.tracking, menu);
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

}
