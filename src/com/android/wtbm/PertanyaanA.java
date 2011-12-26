package com.android.wtbm;


//Copyright pujexx@android


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;

import android.media.MediaPlayer;
import android.os.Bundle;

import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

public class PertanyaanA extends Activity {
	private JSONObject jSoal;
	TextView pertanyaan_view;
	RadioButton jawaban_a;
	RadioButton jawaban_b;
	RadioButton jawaban_c;
	RadioButton jawaban_d;
	private String kunci;
	private String jwb;
	private AlertDialog confirm;
	TextView nyawa;
	TextView title_level;
	MediaPlayer backsound;
	MediaPlayer jawabansound;
	MediaPlayer benar;
	MediaPlayer salah;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		// setContentView();
		setContentView(R.layout.soal);
		backsound = MediaPlayer.create(PertanyaanA.this, R.raw.backsound);
		jawabansound = MediaPlayer.create(this, R.raw.soundjawaban);
		benar = MediaPlayer.create(PertanyaanA.this, R.raw.benar);
		salah = MediaPlayer.create(PertanyaanA.this, R.raw.salah);
		backsound.start();
		backsound.setLooping(true);
		pertanyaan_view = (TextView) findViewById(R.id.pertanyaan);
		jawaban_a = (RadioButton) findViewById(R.id.jawaban_a);
		jawaban_b = (RadioButton) findViewById(R.id.jawaban_b);
		jawaban_c = (RadioButton) findViewById(R.id.jawaban_c);
		jawaban_d = (RadioButton) findViewById(R.id.jawaban_d);
		nyawa = (TextView)findViewById(R.id.nyawa);
		title_level = (TextView)findViewById(R.id.title_pertanyaan);
		getPertanyaan();

	}
	
	public void getPertanyaan() {
		try {
			jSoal = new JSONObject(User.SOAL);
			JSONArray pertanyaan = jSoal.getJSONArray("result");

			String pertanyaan1 = pertanyaan.getJSONObject(User.LEVEL)
					.getString("pertanyaan").toString();
			String jawaban_a_1 = pertanyaan.getJSONObject(User.LEVEL)
					.getString("jawaban_a").toString();
			String jawaban_b_1 = pertanyaan.getJSONObject(User.LEVEL)
					.getString("jawaban_b").toString();
			String jawaban_c_1 = pertanyaan.getJSONObject(User.LEVEL)
					.getString("jawaban_c").toString();
			String jawaban_d_1 = pertanyaan.getJSONObject(User.LEVEL)
					.getString("jawaban_d").toString();
			kunci = pertanyaan.getJSONObject(User.LEVEL).getString("kunci")
					.toString();

			pertanyaan_view.setText(pertanyaan1);
			jawaban_a.setText("A.  "+jawaban_a_1);
			jawaban_b.setText("B.  "+jawaban_b_1);
			jawaban_c.setText("C.  "+jawaban_c_1);
			jawaban_d.setText("D.  "+jawaban_d_1);
			nyawa.setText("Tinggal "+Integer.toString(User.NYAWA)+" kesempatan lagi");
			title_level.setText("UANG ANDA :\n"+level(User.LEVEL));

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void jawab() {

		if (jawaban_a.isChecked()) {
			jwb = "a";
		} else if (jawaban_b.isChecked()) {
			jwb = "b";
		} else if (jawaban_c.isChecked()) {
			jwb = "c";
		} else if (jawaban_d.isChecked()) {
			jwb = "d";
		} else {
			jwb = "";
		}
	}

	public void rule() {
		if (User.NYAWA != 1) {
			
			if (jwb.equals(kunci)) {
				if (User.LEVEL != 14) {
					Toast.makeText(PertanyaanA.this, "Selamat jawaban anda benar dan masuk ke level selanjutnya", Toast.LENGTH_LONG).show();
					benar.start();
					User.LEVEL = User.LEVEL + 1;
					getPertanyaan();
				}else {
					User.LEVEL = 0;
					User.NYAWA = 3;
					backsound.stop();
					Intent win = new Intent(PertanyaanA.this,Wtbm.class);
					
					startActivity(win);
					this.finish();
					
				}
			} else {
				Toast.makeText(PertanyaanA.this, "Jawaban anda salah yang benar adalah : "+kunci, Toast.LENGTH_LONG).show();
				salah.start();
				User.LEVEL = User.LEVEL + 1;
				User.NYAWA = User.NYAWA - 1;
				getPertanyaan();
			}
		} else {
			Toast.makeText(this, "Maaf anda kurang beruntung", Toast.LENGTH_LONG).show();
			User.LEVEL = 0;
			User.NYAWA = 3;
			backsound.stop();
			jawabansound.stop();
			finish();

		}
	}

	public String level(int level) {
		String rupiah = null;
		switch (level) {
		case 0:
			rupiah = "Rp. 50.000,-";
			break;
		case 1:
			rupiah = "Rp. 125.000,-";
			break;
		case 2:
			rupiah = "Rp. 250.000,-";
			
			break;
		case 3:
			rupiah = "Rp. 500.000,-";
			break;
		case 4:
			rupiah = "Rp. 1000.000,-";
			break;
		case 5:
			rupiah = "Rp. 2000.000,-";
			break;
		case 6:
			rupiah = "Rp. 4000.000,-";
			break;
		case 7:
			rupiah = "Rp. 8000.000,-";
			break;
		case 8:
			rupiah = "Rp. 16.000.000,-";
			break;
		case 9:
			rupiah = "Rp. 32.000.000,-";
			break;
		case 10:
			rupiah = "Rp. 64.000.000,-";
			break;
		case 11:
			rupiah = "Rp. 125 Juta,-"; 
			break;
		case 12:
			rupiah = "Rp. 250 Juta,-";
			break;
		case 13:
			rupiah = "Rp. 500 Juta,-";
			break;
		case 14:
			rupiah = "Rp. 1 Milyar,-";
			break;

		default:
			break;
		}
		return rupiah;
	}
	public void Confirm(){
		confirm = new AlertDialog.Builder(PertanyaanA.this).create();
		confirm.setTitle("Apakah Anda yakin");
		confirm.setMessage("Apakah anda yakin ?");
		confirm.setButton("Ya",  new DialogInterface.OnClickListener() {
			
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				
				jawab();
				if (jwb.equals("")) {
					Toast.makeText(PertanyaanA.this, "Anda belum memilih jawaban",
							Toast.LENGTH_LONG).show();
				} else {
					rule();
					jawaban_a.setChecked(false);
					jawaban_b.setChecked(false);
					jawaban_c.setChecked(false);
					jawaban_d.setChecked(false);

				}
				
				
			}
		});
		confirm.setButton2("Tidak", new DialogInterface.OnClickListener() {
			
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				return ;
			}
		});
		confirm.show();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			return false;
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		menu.add(0, 0, 0, "jawab");
		menu.add(0, 1, 0, "Keluar");
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
		case 0:
			jawabansound.start();
			Confirm();
			
			break;
		case 1:
			User.LEVEL = 0;
			User.NYAWA = 3;
			backsound.stop();
			finish();
			break;
		default:
			break;
		}
		return super.onMenuItemSelected(featureId, item);
	}

}
