package com.example.shantanu.cbsepapers;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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
	Button[] PaperButtons;
	String subject;
	String subcode;
	String grade;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_paper_list);
		title = (TextView) findViewById(R.id.tvSub);
		Bundle b = getIntent().getExtras();

		final SharedPreferences colors = getPreferences(MODE_PRIVATE);

		grade = Integer.toString(b.getInt("grade"));
		subject = b.getString("sub");
		subcode = b.getString("subcode");
		title.setText(subject);

		PaperButtons = new Button[3];
		for(int i=0;i<3;i++){
			String buttonID = "b" + Integer.toString(i+1);
			int res = getResources().getIdentifier(buttonID,"id",this.getPackageName());
			PaperButtons[i] = (Button) findViewById(res);
			PaperButtons[i].setId(i);
			PaperButtons[i].setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					String postfixIndex = String.format("%02d",view.getId()+1);
					OpenPDF(postfixIndex);

				}
			});

		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_paper_list, menu);
		return true;
	}

	void OpenPDF(String index){
		File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/CBSEPapers/"+grade+subcode+index+".pdf");

		Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.setDataAndType(Uri.fromFile(file), "application/pdf");
		intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
		try {
			startActivity(intent);
		} catch (Exception e) {
			Context c = getApplicationContext();
			AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
			alertDialogBuilder.setMessage("You need to install a PDF reader. Do you want to install one now?");
			alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialogInterface, int i) {
					Intent marketIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=com.adobe.reader"));
					marketIntent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY | Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET|Intent.FLAG_ACTIVITY_MULTIPLE_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
					startActivity(marketIntent);
				}
			});
			alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialogInterface, int i) {
					finish();
				}
			});
			AlertDialog a = alertDialogBuilder.create();
			a.show();

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
