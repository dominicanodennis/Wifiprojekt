package de.compaso.wifisonde;

import android.content.Context;
import android.media.MediaPlayer;

import com.abhi.gif.lib.AnimatedGifImageView;
import com.abhi.gif.lib.AnimatedGifImageView.TYPE;


public class AnimationWithSound {

	int rssi;
	private AnimatedGifImageView animatedGifImageView;
	int switchvalue;
	Context appcontext;
	MediaPlayer mp;

	public AnimationWithSound(int rssi, Context appcontext) {
		super();
		this.rssi = rssi;
		this.appcontext = appcontext;
	}

	public AnimatedGifImageView setAnimationAndSound(AnimatedGifImageView view) {

		if (rssi <= 0 && rssi >= -50) {
			switchvalue = 1;
		} else if (rssi <= -51 && rssi >= -60) {
			switchvalue = 2;
		} else if (rssi <= -61 && rssi >= -70) {
			switchvalue = 3;
		} else if (rssi <= -71 && rssi >= -80) {
			switchvalue = 4;
		} else if (rssi <= -81 && rssi >= -90) {
			switchvalue = 5;
		} else if (rssi <= -91 && rssi >= -100) {
			switchvalue = 6;

		}
		switch (switchvalue) {
		case 1:
			view.setAnimatedGif(R.raw.gruen, TYPE.FIT_CENTER);
			mp = MediaPlayer.create(appcontext, R.raw.song_one);
			break;
		case 2:
			view.setAnimatedGif(R.raw.gelb, TYPE.FIT_CENTER);
			mp = MediaPlayer.create(appcontext, R.raw.song_two);
			break;
		case 3:
			view.setAnimatedGif(R.raw.lila, TYPE.FIT_CENTER);
			mp = MediaPlayer.create(appcontext, R.raw.song_three);
			break;
		case 4:
			view.setAnimatedGif(R.raw.blau, TYPE.FIT_CENTER);
			mp = MediaPlayer.create(appcontext, R.raw.song_four);
			break;
		case 5:
			view.setAnimatedGif(R.raw.rot, TYPE.FIT_CENTER);
			mp = MediaPlayer.create(appcontext, R.raw.song_five);
			break;
		case 6:
			view.setAnimatedGif(R.raw.rot, TYPE.FIT_CENTER);
			mp = MediaPlayer.create(appcontext, R.raw.song_one);
			break;

		default:
			animatedGifImageView.setAnimatedGif(R.raw.rot, TYPE.FIT_CENTER);
			// mp = Mediaplayer.create(appcontext, R.raw.song_one);
		}
		
		if (mp!=null) {
			mp.start();
		}
		return view;

	}

}
