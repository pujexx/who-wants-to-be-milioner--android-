package com.android.wtbm;
//Copiright pujexx@android
import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.KeyEvent;

public class Splash extends Activity{
//	private final int SPLASH_DISPLAY_LENGHT = 5000;
	MediaPlayer pemutar;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash);
		pemutar = MediaPlayer.create(this, R.raw.opening);
		pemutar.start();
		Thread splashThread = new Thread() {
	         @Override
	         public void run() {
	            try {
	               int waited = 0;
	               while (waited < 18000) {
	                  sleep(100);
	                  waited += 100;
	               }
	            } catch (InterruptedException e) {
	               // do nothing
	            } finally {
	            	pemutar.stop();
	               finish();
	               Intent i = new Intent();
	               i.setClassName("com.android.wtbm",
	                              "com.android.wtbm.Welcome");
	               startActivity(i);
	            }
	         }
	      };
	      splashThread.start();
	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			pemutar.stop();
			finish();
			
		}
		return super.onKeyDown(keyCode, event);
	}


}
