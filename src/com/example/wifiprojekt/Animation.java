package com.example.wifiprojekt;

import android.content.Context;
import android.media.MediaPlayer;

import com.abhi.gif.lib.AnimatedGifImageView;
import com.abhi.gif.lib.AnimatedGifImageView.TYPE;

public class Animation {

	int rssi;
	private AnimatedGifImageView animatedGifImageView;
	int switchvalue;
	Context appcontext;
	MediaPlayer mp;

	public Animation(int rssi, Context appcontext) {
		super();
		this.rssi = rssi;
		this.appcontext = appcontext;
	}

	public AnimatedGifImageView setAnimation(AnimatedGifImageView view) {

		if (rssi <= 0 && rssi >= -50) {
			switchvalue = 1;
		} else if (rssi <= -51 && rssi >= -60) {
			switchvalue = 2;
		} else if (rssi <= -61 && rssi >= -70) {
			switchvalue = 3;
		} else if (rssi <= -71 && rssi >= -80) {
			switchvalue = 4;
		} else if (rssi <= -81 && rssi >= 90) {
			switchvalue = 5;
		} else if (rssi <= -91 && rssi >= -100) {
			switchvalue = 6;

		}
		switch (switchvalue) {
		case 1:
			view.setAnimatedGif(R.raw.gruen, TYPE.FIT_CENTER);
			mp = MediaPlayer.create(appcontext, R.raw.song_one);
			mp.start();
			break;
		case 2:
			view.setAnimatedGif(R.raw.gelb, TYPE.FIT_CENTER);
			mp = MediaPlayer.create(appcontext, R.raw.song_two);
			mp.start();
			break;
		case 3:
			view.setAnimatedGif(R.raw.lila, TYPE.FIT_CENTER);
			mp = MediaPlayer.create(appcontext, R.raw.song_three);
			mp.start();
			break;
		case 4:
			view.setAnimatedGif(R.raw.blau, TYPE.FIT_CENTER);
			mp = MediaPlayer.create(appcontext, R.raw.song_four);
			mp.start();
			break;
		case 5:
			view.setAnimatedGif(R.raw.rot, TYPE.FIT_CENTER);
			mp = MediaPlayer.create(appcontext, R.raw.song_five);
			mp.start();
			break;
		case 6:
			view.setAnimatedGif(R.raw.geiger_zaehler, TYPE.FIT_CENTER);
			mp = MediaPlayer.create(appcontext, R.raw.song_one);
			mp.start();
			break;

		default:
			animatedGifImageView.setAnimatedGif(R.raw.nowifi, TYPE.FIT_CENTER);

		}
		return view;

	}

}
