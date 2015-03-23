package de.compaso.wifisonde;

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
	TextView textFeld1, textFeld2;
	WifiManager wifiManager;
	WifiReceiver2 wifiReceiver2;
	String wifiListe[];
	List<ScanResult> scanResultate;
	int netId;
	int rssiLevel;
	int wifilevel = 0;
	String bssid, ssid;
	Button button1, button2;
	boolean back = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tracking);

		wifiManager = (WifiManager) getSystemService(WIFI_SERVICE);

		button1 = (Button) findViewById(R.id.button1);
		button2 = (Button) findViewById(R.id.button2);
		textFeld1 = (TextView) findViewById(R.id.textView1);
		textFeld2 = (TextView) findViewById(R.id.textView2);

		animatedGifImageView = ((AnimatedGifImageView) findViewById(R.id.animatedGifImageView1));
		animatedGifImageView.setAnimatedGif(R.raw.gruen, TYPE.FIT_CENTER);

		Helperclass helper1 = new Helperclass();
		helper1.disableAllNetworks(this.netId);

		Intent intent = getIntent();
		bssid = intent.getExtras().getString("mac");
		ssid = intent.getExtras().getString("wifiname");

		scanne();

	}

	public void restartActivity(View view) {
		finish();
		startActivity(getIntent());
		Helperclass helper3 = new Helperclass();
		helper3.disableAllNetworks(this.netId);

	}

	public void beendeApp(View view) {
		Helperclass helper5 = new Helperclass();
		helper5.enableAllNetworks(this.netId);

		System.exit(1);
	}

	public void backToWifiList(View view) {
		finish();
		Intent intent = new Intent(TrackingActivity.this, MainActivity.class);
		back = true;
		startActivity(intent);

	}

	public void disableAllNetworks() {
		List<WifiConfiguration> wificonfigliste = wifiManager
				.getConfiguredNetworks();

		for (WifiConfiguration config : wificonfigliste) {
			this.netId = config.networkId;
			wifiManager.disableNetwork(this.netId);
		}

	}

	public void enableAllNetworks() {
		List<WifiConfiguration> wificonfigliste = wifiManager
				.getConfiguredNetworks();
		for (WifiConfiguration config : wificonfigliste) {
			this.netId = config.networkId;
			wifiManager.enableNetwork(this.netId, true);
		}
	}

	public void scanne() {
		wifiReceiver2 = new WifiReceiver2();
		registerReceiver(wifiReceiver2, new IntentFilter(
				WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));
		wifiManager.startScan();

	}

	@Override
	public void onBackPressed() {
		Helperclass helper4 = new Helperclass();
		helper4.disableAllNetworks(this.netId);
		finish();
		Intent intent = new Intent(this, MainActivity.class);
		startActivity(intent);
		back = true;
		super.onBackPressed();
	}

	protected void onPause() {
		unregisterReceiver(wifiReceiver2);
		super.onPause();
	}

	@Override
	protected void onStop() {
		if (!back) {
			Helperclass helper4 = new Helperclass();
			helper4.enableAllNetworks(this.netId);
			enableAllNetworks();
		} else {
			back = false;
		}
		super.onStop();
	}

	@Override
	protected void onRestart() {
		if (!wifiManager.isWifiEnabled()) {
			wifiManager.setWifiEnabled(true);
			Helperclass helper6 = new Helperclass();
			helper6.disableAllNetworks(this.netId);

		} else {
			Helperclass helper7 = new Helperclass();
			helper7.disableAllNetworks(this.netId);

		}

		super.onRestart();
	}

	protected void onResume() {
		registerReceiver(wifiReceiver2, new IntentFilter(
				WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));
		if (!wifiManager.isWifiEnabled()) {
			wifiManager.setWifiEnabled(true);
			Helperclass helper6 = new Helperclass();
			helper6.disableAllNetworks(this.netId);

			disableAllNetworks();

		} else {
			Helperclass helper7 = new Helperclass();
			helper7.disableAllNetworks(this.netId);
			disableAllNetworks();

		}
		super.onResume();
	}

	class WifiReceiver2 extends BroadcastReceiver {
		public void onReceive(Context c, Intent intent) {
			ScanResult scanResult = null;
			scanResultate = wifiManager.getScanResults();

			if (scanResultate != null) {
				for (ScanResult result : scanResultate) {

					if (result.BSSID.equals(bssid)) {

						scanResult = result;
						break;
					}

				}
			}

			if (scanResult != null) {
				rssiLevel = scanResult.level;
				wifilevel = WifiManager.calculateSignalLevel(scanResult.level,
						100);
				ssid = scanResult.SSID;

				textFeld1.setText("Scane: " + ssid);
				textFeld2.setText("Signallevel: " + rssiLevel + " %");

				AnimationWithSound animation = new AnimationWithSound(
						rssiLevel, getApplicationContext());
				animation.setAnimationAndSound(animatedGifImageView);
			} else {
				textFeld1.setText("Suche: " + ssid);
				textFeld2.setText("ausserhalb der Reichweite");

				AnimationWithSound animation = new AnimationWithSound(-99,
						getApplicationContext());
				animation.setAnimationAndSound(animatedGifImageView);
			}
		}

	}

}
