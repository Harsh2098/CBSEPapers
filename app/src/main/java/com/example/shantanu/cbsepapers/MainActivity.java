package com.example.shantanu.cbsepapers;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


public class MainActivity extends ActionBarActivity {

    Button bP,bE,bM,bC,bB ;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bP= (Button) findViewById(R.id.bPhysics);
		bE= (Button) findViewById(R.id.bEnglish);
		bM= (Button) findViewById(R.id.bMaths);
		bC= (Button) findViewById(R.id.bChemistry);
		bB= (Button) findViewById(R.id.bBiology);
        copyFilesToSdCard();
        bP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/CBSEPapers/paperP.pdf");

                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setDataAndType(Uri.fromFile(file), "application/pdf");
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
				try {
					startActivity(intent);
				} catch (Exception e) {
					Context c = getApplicationContext();
					Toast.makeText(c,"You need to install a pdf reader first!! ", Toast.LENGTH_LONG).show();
					e.printStackTrace();
				}
			}
        });
		bB.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/CBSEPapers/paperB.pdf");

				Intent intent = new Intent(Intent.ACTION_VIEW);
				intent.setDataAndType(Uri.fromFile(file), "application/pdf");
				intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
				try {
					startActivity(intent);
				} catch (Exception e) {
					Context c = getApplicationContext();
					Toast.makeText(c,"You need to install a pdf reader first!! ", Toast.LENGTH_LONG).show();
					e.printStackTrace();
				}
			}
		});
		bC.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/CBSEPapers/paperC.pdf");

				Intent intent = new Intent(Intent.ACTION_VIEW);
				intent.setDataAndType(Uri.fromFile(file), "application/pdf");
				intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
				try {
					startActivity(intent);
				} catch (Exception e) {
					Context c = getApplicationContext();
					Toast.makeText(c,"You need to install a pdf reader first!! ", Toast.LENGTH_LONG).show();
					e.printStackTrace();
				}
			}
		});
		bM.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/CBSEPapers/paperM.pdf");

				Intent intent = new Intent(Intent.ACTION_VIEW);
				intent.setDataAndType(Uri.fromFile(file), "application/pdf");
				intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
				try {
					startActivity(intent);
				} catch (Exception e) {
					Context c = getApplicationContext();
					Toast.makeText(c,"You need to install a pdf reader first!! ", Toast.LENGTH_LONG).show();
					e.printStackTrace();
				}
			}
		});
		bE.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/CBSEPapers/paperE.pdf");

				Intent intent = new Intent(Intent.ACTION_VIEW);
				intent.setDataAndType(Uri.fromFile(file), "application/pdf");
				intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
				try {
					startActivity(intent);
				} catch (Exception e) {
					Context c = getApplicationContext();
					Toast.makeText(c,"You need to install a pdf reader first!! ", Toast.LENGTH_LONG).show();
					e.printStackTrace();
				}
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

		//noinspection SimplifiableIfStatement
		if (id == R.id.action_settings) {
			return true;
		}

		return super.onOptionsItemSelected(item);
	}
}
