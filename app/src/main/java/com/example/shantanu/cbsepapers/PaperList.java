package com.example.shantanu.cbsepapers;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;


public class PaperList extends ActionBarActivity {

	TextView title;
	Button b1,b2,b3;
	String subject;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_paper_list);
		title = (TextView) findViewById(R.id.tvSub);
		Bundle b = getIntent().getExtras();

		subject = b.getString("sub");
		b1 = (Button) findViewById(R.id.b1);
		b2 = (Button) findViewById(R.id.b2);
		b3 = (Button) findViewById(R.id.b3);
		switch (subject){
			case "PHY":title.setText("Physics");
				break;
			case "MAT":title.setText("Maths");
				break;
			case "CHM":title.setText("Chemistry");
				break;
			case "BIO":title.setText("Biology");
				break;
			case "ENG":title.setText("English");
				break;
			case "CS":title.setText("Computer Science");
				break;
		}
		b1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				OpenPDF("01");
				b3.setBackgroundColor(Color.rgb(99,33,33));
			}
		});
		b2.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				OpenPDF("02");
				b3.setBackgroundColor(Color.rgb(99,33,33));
			}
		});
		b3.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				OpenPDF("03");
				b3.setBackgroundColor(Color.rgb(99,33,33));
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
		File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/CBSEPapers/"+subject+index+".pdf");

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
