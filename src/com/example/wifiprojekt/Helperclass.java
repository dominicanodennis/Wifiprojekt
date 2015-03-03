package com.example.wifiprojekt;

import java.util.List;

import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;

public class Helperclass {

	int netId;
	WifiManager wifimanger;

	public void disableAllNetworks(int netId, WifiManager wifimanager) {
		this.netId = netId;
		this.wifimanger = wifimanager;
		List<WifiConfiguration> wificonfigliste = wifimanger
				.getConfiguredNetworks();

		for (WifiConfiguration config : wificonfigliste) {
			this.netId = config.networkId;
			wifimanger.disableNetwork(this.netId);
		}

	}

	public void enableAllNetworks(int netId, WifiManager wifimanager) {
		this.netId = netId;
		this.wifimanger = wifimanager;
		List<WifiConfiguration> wificonfigliste = wifimanger
				.getConfiguredNetworks();

		for (WifiConfiguration config : wificonfigliste) {
			this.netId = config.networkId;
			wifimanger.enableNetwork(this.netId, false);
		}

	}
}
