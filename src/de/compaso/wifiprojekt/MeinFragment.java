package de.compaso.wifiprojekt;

import com.example.wifiprojekt.R;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class MeinFragment extends Fragment{
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		return inflater.inflate(R.layout.fragment_one, container, false);
	}
	public static final MeinFragment newInstance()
	{
	    MeinFragment f = new MeinFragment();
	    return f;
	}
	

}