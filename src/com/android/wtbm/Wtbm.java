package com.android.wtbm;


//Copiright pujexx@android



import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;

public class Wtbm extends Activity {
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			
			this.finish();
			this.getParent().finish();
			
		}
		return super.onKeyDown(keyCode, event);
	}


}