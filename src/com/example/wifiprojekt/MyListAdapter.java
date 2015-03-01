package com.example.wifiprojekt;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/*
 * Ein eigener Listenadapter kann verwendet werden um die Daten
 * die du in eine Liste steckst wieder raus zu holen ohne sich
 * einen Arm au zu rei�en.
 */
public class MyListAdapter extends ArrayAdapter<String> {

	List<String> data;
	Context context;

	public MyListAdapter(Context context, int resource, List<String> data) {
		super(context, resource);
		this.context = context;
		this.data = data;
	}

	@Override
	public String getItem(int position) {
		return data.get(position);
	}

	@Override
	public int getPosition(String item) {
		return data.indexOf(item);
	}

	@Override
	public int getCount() {
		return data.size();
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		View view = convertView;

		if (view == null) {
			view = ((LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE))
					.inflate(R.layout.list_item, parent, false);
		}

		TextView textView = (TextView) view.findViewById(R.id.list_item_value);

		textView.setText(data.get(position));

		return view;
	}

}
