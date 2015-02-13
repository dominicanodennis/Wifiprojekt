package com.example.wifiprojekt;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;

public class Dialog {

	final CharSequence[] items = { "Take Photo From Gallery",
			"Take Photo From Camera" };
	Activity activity;
	AlertDialog dialog;
	AlertDialog.Builder builder;
	String detailProvader;

	public Dialog(Activity a, String detailProvader) {
		this.activity = a;
		this.detailProvader = detailProvader;
		builder = new AlertDialog.Builder(a);
	}

	public void showDialog() {

		builder.setTitle("Wifi Detailinformation");
		builder.setMessage(detailProvader);

		AlertDialog alert = builder.create();
		alert.show();
	}

}
