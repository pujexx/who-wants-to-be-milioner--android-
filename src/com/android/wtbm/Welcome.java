package com.android.wtbm;

//Copiright pujexx@android

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Activity;

import android.content.Intent;


import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;

import android.widget.ImageButton;
import android.widget.Toast;

public class Welcome extends Activity {
	private ImageButton newgame;

	private static String urlnewgame = "http://ksweb.student.uad.ac.id/demo/kos/index.php/api/getsoal/";
	private String ID_PHONE;
	MediaPlayer startsound;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.welcome);
		startsound = MediaPlayer.create(Welcome.this, R.raw.startsound);
		ID_PHONE = android.provider.Settings.Secure.getString(
				getContentResolver(),
				android.provider.Settings.Secure.ANDROID_ID);
		Log.d("ID_PHONE", ID_PHONE);

		newgame = (ImageButton) findViewById(R.id.btn_newgame);

		newgame.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// getPertanyaan();
				String url = urlnewgame + ID_PHONE;
				startsound.start();
				if (getPertanyaan(url)) {

					getPertanyaan(url);
					Intent intent = new Intent(Welcome.this, PertanyaanA.class);
					startActivity(intent);

				} else {
					Toast.makeText(Welcome.this, "Harus ada koneksi !",
							Toast.LENGTH_LONG).show();
				}

				// Toast.makeText(Welcome.this, "Jalan",
				// Toast.LENGTH_LONG).show();
			}
		});
	}

	public boolean getPertanyaan(String url) {
		HttpClient client = new DefaultHttpClient();

		HttpGet request = new HttpGet(url);
		try {
			HttpResponse response = client.execute(request);
			// Toast.makeText(Welcome.this,request(response),
			// Toast.LENGTH_LONG).show();
			User.SOAL = request(response);
			Log.d("Soal", User.SOAL);
			return true;
		} catch (Exception ex) {

			Toast.makeText(Welcome.this, "Failed Connect to server!",
					Toast.LENGTH_LONG).show();
			return false;
		}
	}

	public static String request(HttpResponse response) {
		String result = "";
		try {
			InputStream in = response.getEntity().getContent();
			BufferedReader reader = new BufferedReader(
					new InputStreamReader(in));
			StringBuilder str = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				str.append(line);
			}
			in.close();
			result = str.toString();
		} catch (Exception ex) {
			result = "Error";
		}
		return result;
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK) {

			finish();

		}
		return super.onKeyDown(keyCode, event);
	}

}
