package com.example.shantanu.cbsepapers;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;


public class PaperList extends AppCompatActivity {

	TextView title;
	Button b1,b2,b3;
	String subject;
	String grade;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_paper_list);
		title = (TextView) findViewById(R.id.tvSub);
		Bundle b = getIntent().getExtras();
		Boolean B1 = null,B2=null,B3=null;
		final SharedPreferences colors = getPreferences(MODE_PRIVATE);

		grade = Integer.toString(b.getInt("grade"));

		subject = b.getString("sub");
		b1 = (Button) findViewById(R.id.b1);
		b2 = (Button) findViewById(R.id.b2);
		b3 = (Button) findViewById(R.id.b3);
		switch (subject){
			case "PHY":
				title.setText("Physics");
				B1 = colors.getBoolean("PHY01",false);
				B2 = colors.getBoolean("PHY02",false);
				B3 = colors.getBoolean("PHY03",false);
				break;
			case "MAT":
				title.setText("Maths");
				B1 = colors.getBoolean("MAT01",false);
				B2 = colors.getBoolean("MAT02",false);
				B3 = colors.getBoolean("MAT03",false);
				break;
			case "CHM":
				title.setText("Chemistry");
				B1 = colors.getBoolean("CHM01",false);
				B2 = colors.getBoolean("CHM02",false);
				B3 = colors.getBoolean("CHM03",false);
				break;
			case "BIO":
				title.setText("Biology");
				B1 = colors.getBoolean("BIO01",false);
				B2 = colors.getBoolean("BIO02",false);
				B3 = colors.getBoolean("BIO03",false);
				break;
			case "ENG":
				title.setText("English");
				B1 = colors.getBoolean("ENG01",false);
				B2 = colors.getBoolean("ENG02",false);
				B3 = colors.getBoolean("ENG03",false);
				break;
			case "CS":
				title.setText("Computer Science");
				B1 = colors.getBoolean("CS01",false);
				B2 = colors.getBoolean("CS02",false);
				B3 = colors.getBoolean("CS03",false);
				break;
		}
		/*if(B1){
			b1.setBackgroundColor(Color.rgb(99,33,33));
		}
		if(B2){
			b2.setBackgroundColor(Color.rgb(99,33,33));
		}
		if(B3){
			b3.setBackgroundColor(Color.rgb(99,33,33));
		}*/
		b1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				OpenPDF("01");

				SharedPreferences.Editor editor = colors.edit();
				editor.putBoolean(subject+"01",true);
				editor.commit();
			}
		});
		b2.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				OpenPDF("02");

				SharedPreferences.Editor editor = colors.edit();
				editor.putBoolean(subject + "02", true);
				editor.commit();
			}
		});
		b3.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				OpenPDF("03");

				SharedPreferences.Editor editor = colors.edit();
				editor.putBoolean(subject + "03", true);
				editor.commit();
			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_paper_list, menu);
		return true;
	}

	void OpenPDF(String index){
		File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/CBSEPapers/"+grade+subject+index+".pdf");

		Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.setDataAndType(Uri.fromFile(file), "application/pdf");
		intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
		try {
			startActivity(intent);
		} catch (Exception e) {
			Context c = getApplicationContext();
			Toast.makeText(c, "You need to install a pdf reader first!! ", Toast.LENGTH_LONG).show();
			e.printStackTrace();
		}
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();

		//noinspection SimplifiableIfStatement
		if (id == R.id.action_settings) {
			return true;
		}

		return super.onOptionsItemSelected(item);
	}
}
