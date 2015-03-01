package com.example.wifiprojekt;

import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class MyFragmentDialog extends DialogFragment {
	int num;
	Bundle infos;

	/*
	 * Du kannst mit dem DialogFragment FragmentTransactions ausf�hren... Das
	 * sollte die Kapazit�ten die du ben�tigst �bersteigen. Also ein normaler
	 * Dialog sollte es auch tuhen
	 */

	public static MyFragmentDialog newInstance(int which, Bundle infos) {
		MyFragmentDialog myFragmentDialog = new MyFragmentDialog();

		Bundle args = new Bundle();
		args.putInt("num", which);
		myFragmentDialog.setArguments(args);

		myFragmentDialog.infos = infos;

		return myFragmentDialog;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		num = getArguments().getInt("num");
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.dialog_layout, container);

		Button cancel = (Button) view.findViewById(R.id.button_dialog_left);

		getDialog().setTitle("Passing value dialog");
		getDialog().setCanceledOnTouchOutside(true);

		cancel.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				getDialog().dismiss();
			}
		});

		Button next = (Button) view.findViewById(R.id.button_dialog_right);
		next.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity(), TargetActivity.class);
				intent.putExtras(infos);
				startActivity(intent);
				getDialog().dismiss();
			}
		});

		return view;
	}

}
