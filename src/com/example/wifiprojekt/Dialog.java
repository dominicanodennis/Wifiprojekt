package com.example.wifiprojekt;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;

public class Dialog extends DialogFragment {

	Activity activity;
	AlertDialog dialog;
	AlertDialog.Builder builder;
	String detailProvider;

	public Dialog(final Activity a, String detailProvider) {
		this.activity = a;
		this.detailProvider = detailProvider;
		builder = new AlertDialog.Builder(a);

		builder.setPositiveButton("Tracken", new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				MainActivity main = new MainActivity();

//				Intent intent = new Intent(activity,
//						TrackingActivity.class);
//				startActivity(intent);

			}
		});
		builder.setNeutralButton("zurück", new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				// dismiss();

			}
		});

	}

	public void setzPositiveButton(Activity a, Activity b) {
		builder.setPositiveButton("Tracken", new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {

//				Intent intent = new Intent(this.a, this.b);
//				startActivity(intent);

			}
		});
	}

	public void showDialog() {

		builder.setTitle("Wifi Detailinformation");
		builder.setMessage(detailProvider);

		AlertDialog alert = builder.create();
		alert.show();

	}

}
