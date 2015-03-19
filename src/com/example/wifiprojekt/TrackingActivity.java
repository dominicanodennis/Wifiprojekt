package com.example.wifiprojekt;

import java.util.List;

import com.abhi.gif.lib.AnimatedGifImageView;
import com.abhi.gif.lib.AnimatedGifImageView.TYPE;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class TrackingActivity extends FragmentActivity {

	private AnimatedGifImageView animatedGifImageView;
	String empfangSSID;
	TextView textfeld1, textfeld2;
	WifiManager wifimanager;
	WifiReceiver2 wifiReceiver;
	String wifiliste[];
	List<ScanResult> scanresultate;
	int netId;
	int rssiLevel;
	int wifilevel = 0;
	String bssid, ssid;
	ScanResult scanresult;
	MainActivity main;
	Button button1, button2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tracking);

		wifimanager = (WifiManager) getSystemService(WIFI_SERVICE);

		button1 = (Button) findViewById(R.id.button1);
		button2 = (Button) findViewById(R.id.button2);
		textfeld1 = (TextView) findViewById(R.id.textView1);
		textfeld2 = (TextView) findViewById(R.id.textView2);

		animatedGifImageView = ((AnimatedGifImageView) findViewById(R.id.animatedGifImageView1));
		animatedGifImageView.setAnimatedGif(R.raw.gruen, TYPE.FIT_CENTER);

		Helperclass helper1 = new Helperclass();
		helper1.disableAllNetworks(this.netId, this.wifimanager);

		Intent intent = getIntent();
		bssid = intent.getExtras().getString("wifiname");

		scanne();

	}

	public void restartActivity(View view) {
		finish();
		startActivity(getIntent());
		Helperclass helper3 = new Helperclass();
		helper3.disableAllNetworks(this.netId, this.wifimanager);

	}

	public void beendeApp(View view) {
		System.exit(1);
	}

	public void backToWifiList(View view) {
		finish();
		Intent intent = new Intent(TrackingActivity.this, MainActivity.class);
		startActivity(intent);

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
		Helperclass helper4 = new Helperclass();
		helper4.disableAllNetworks(this.netId, this.wifimanager);
		finish();
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
		finish();
		Helperclass helper5 = new Helperclass();
		helper5.enableAllNetworks(this.netId, this.wifimanager);
		enableAllNetworks();
		super.onStop();
	}

	@Override
	protected void onRestart() {
		if (!wifimanager.isWifiEnabled()) {
			wifimanager.setWifiEnabled(true);
			Helperclass helper6 = new Helperclass();
			helper6.disableAllNetworks(this.netId, this.wifimanager);

		} else {
			Helperclass helper7 = new Helperclass();
			helper7.disableAllNetworks(this.netId, this.wifimanager);

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
			ScanResult result2 = null;
			scanresultate = wifimanager.getScanResults();

			// RssiRechner rssi = new RssiRechner();
			// eventuell if-Abfrage um zu prüfen übergebende bssid noch vorhanden ist
			// wenn nicht, abfangen

			
			for (ScanResult result : scanresultate) {

				if (result.BSSID.equals(bssid)) {

					result2 = result;
					break;
				}

			}

			rssiLevel = result2.level;
			wifilevel = WifiManager.calculateSignalLevel(result2.level, 100);
			ssid = result2.SSID;

			// funzt nicht, stürzt ab wenn network nicht mehr sichtbar bzw. das
			// ausgewählte
			// netzwerk ist ausser Reichweite

			// anderes Problem: wenn man in eine Area kommt wo ein bekanntes
			// bzw.
			// vom Smartphone gespeichertes Wlannetz kommt,
			// connected er das Netz und somit arbeitet die app nicht mehr
			// richtig
			// Tip: vielleicht muss man in onResume() nochmal disableNetwork()
			// aufrufen
			// um das Wkannetz zu disablen
			textfeld2.setText("Signallevel: " + rssiLevel + " %");
			textfeld1.setText("Tracke: " + ssid);

			Animation animation = new Animation(rssiLevel,
					getApplicationContext());
			animation.setAnimation(animatedGifImageView);

		}

	}

}
