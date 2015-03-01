package com.example.wifiprojekt;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.widget.TextView;

public class TargetActivity extends FragmentActivity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.target_layout);
		
		Bundle bundle = getIntent().getExtras();
		
		String value = (String) bundle.get("inputValue");
		
		TextView textView = (TextView) findViewById(R.id.target_data_output);
		
		textView.setText("passed value: " + value);
	}

}
