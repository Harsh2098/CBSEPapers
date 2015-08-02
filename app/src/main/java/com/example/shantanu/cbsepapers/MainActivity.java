package com.example.shantanu.cbsepapers;

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


public class MainActivity extends ActionBarActivity {

    Button bP,bE,bM,bC,bB,bCS ;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LinearLayout layout = (LinearLayout) findViewById(R.id.layout);
        bP = new Button(this);
		bE = new Button(this);
		bM = new Button(this);
		bC = new Button(this);
		bB = new Button(this);
		bCS = new Button(this);

        SharedPreferences preferences = getSharedPreferences("Subjects",MODE_PRIVATE);
        if(preferences.getBoolean("first", true )){
            copyFilesToSdCard();
            Intent i = new Intent(this,First.class);
            i.putExtra("isFromSettings", false);
            startActivity(i);

        }
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("first", false);
        if(preferences.getBoolean("MAT",true)){
            bM.setText("Maths");
            bM.setLayoutParams(new ViewGroup.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT)
            );
            layout.addView(bM);
        }if(preferences.getBoolean("PHY",true)){
            bP.setText("Physics");
            bP.setLayoutParams(new ViewGroup.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT)
            );
            layout.addView(bP);
        }if(preferences.getBoolean("BIO",true)){
            bB.setText("Biology");
            bB.setLayoutParams(new ViewGroup.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT)
            );
            layout.addView(bB);
        }if(preferences.getBoolean("CS",true)){
            bCS.setText("Computer Science");
            bCS.setLayoutParams(new ViewGroup.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT)
            );
            layout.addView(bCS);
        }if(preferences.getBoolean("ENG",true)){
            bE.setText("English");
            bE.setLayoutParams(new ViewGroup.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT)
            );
            layout.addView(bE);
        }if(preferences.getBoolean("CHM",true)){
            bC.setText("Chemistry");
            bC.setLayoutParams(new ViewGroup.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT)
            );
            layout.addView(bC);
        }

        bP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openList("PHY");
            }
        });
		bCS.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v){
				openList("CS");
			}
		});
		bB.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				openList("BIO");
			}
		});
		bC.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v){
				openList("CHM");
			}
		});
		bM.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v){
				openList("MAT");
			}
		});
		bE.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v){
				openList("ENG");
			}

		});


    }




    final static String TARGET_BASE_PATH = Environment.getExternalStorageDirectory().getPath()+"/CBSEPapers/";

    private void copyFilesToSdCard() {
        copyFileOrDir(""); // copy all files in assets folder in my project
    }

    private void copyFileOrDir(String path) {
        AssetManager assetManager = this.getAssets();
        String assets[] = null;
        try {
            Log.i("tag", "copyFileOrDir() "+path);
            assets = assetManager.list(path);
            if (assets.length == 0) {
                copyFile(path);
            } else {
                String fullPath =  TARGET_BASE_PATH + path;
                Log.i("tag", "path="+fullPath);
                File dir = new File(fullPath);
                if (!dir.exists() && !path.startsWith("images") && !path.startsWith("sounds") && !path.startsWith("webkit"))
                    if (!dir.mkdirs())
                        Log.i("tag", "could not create dir "+fullPath);
                for (int i = 0; i < assets.length; ++i) {
                    String p;
                    if (path.equals(""))
                        p = "";
                    else
                        p = path + "/";

                    if (!path.startsWith("images") && !path.startsWith("sounds") && !path.startsWith("webkit"))
                        copyFileOrDir( p + assets[i]);
                }
            }
        } catch (IOException ex) {
            Log.e("tag", "I/O Exception", ex);
        }
    }

    private void copyFile(String filename) {
        AssetManager assetManager = this.getAssets();

        InputStream in = null;
        OutputStream out = null;
        String newFileName = null;
        try {
            Log.i("tag", "copyFile() "+filename);
            in = assetManager.open(filename);
            if (filename.endsWith(".jpg")) // extension was added to avoid compression on APK file
                newFileName = TARGET_BASE_PATH + filename.substring(0, filename.length()-4);
            else
                newFileName = TARGET_BASE_PATH + filename;
            out = new FileOutputStream(newFileName);

            byte[] buffer = new byte[1024];
            int read;
            while ((read = in.read(buffer)) != -1) {
                out.write(buffer, 0, read);
            }
            in.close();
            in = null;
            out.flush();
            out.close();
            out = null;
        } catch (Exception e) {
            Log.e("tag", "Exception in copyFile() of "+newFileName);
            Log.e("tag", "Exception in copyFile() "+e.toString());
        }

    }


	public void openList(String s){
		Intent i = new Intent(this, PaperList.class);
		i.putExtra("sub" , s);
		startActivity(i);
	}


    @Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();

		switch (id){
            case R.id.changeSubs:
                Intent i = new Intent(this,First.class);
                i.putExtra("isFromSettings",true);
                startActivity(i);
                return true;
        }

		return super.onOptionsItemSelected(item);
	}
}
