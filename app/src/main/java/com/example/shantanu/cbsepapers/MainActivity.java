package com.example.shantanu.cbsepapers;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.os.Environment;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


public class MainActivity extends AppCompatActivity {

    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;

    private ListView mDrawerList;
    private ArrayAdapter<String> mAdapter;

    int i;
    TextView gradeTextView;
    Button[] subButton;
    String[] subList,subCode;
    int grade;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences preferences = getSharedPreferences("Subjects",MODE_PRIVATE);
        final SharedPreferences.Editor editor = preferences.edit();
        grade = preferences.getInt("grade",12);

        gradeTextView = (TextView) findViewById(R.id.tvSubject);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        gradeTextView.setText("Class "+grade);
        LinearLayout layout = (LinearLayout) findViewById(R.id.layout);

        Resources res = getResources();
        subList = res.getStringArray(R.array.G12subs);
        subCode = res.getStringArray(R.array.G12subcodes);

        mDrawerList = (ListView) findViewById(R.id.navList);
        addItems();
        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                grade = 12 - i;
                editor.putInt("grade", grade);
                editor.commit();
                gradeTextView.setText("Class " + Integer.toString(grade));

            }
        });


        subButton = new Button[subList.length];
        for(i=0;i<subList.length;i++)
        {
            subButton[i] = new Button(this);
            subButton[i].setText(subList[i]);
            subButton[i].setLayoutParams(new ViewGroup.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT)
            );
            layout.addView(subButton[i]);
            subButton[i].setId(i);
            subButton[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    openList(subList[view.getId()],subCode[view.getId()]);
                }
            });
        }

        if(preferences.getBoolean("first", true )){
            copyFilesToSdCard();
            Intent i = new Intent(this,First.class);
            i.putExtra("isFromSettings", false);
            startActivity(i);

        }

        editor.putBoolean("first", false);



        mDrawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        setDrawer();

    }

    private void addItems(){
        String[] grades = {"CBSE 12","CBSE 11"};
        mAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,grades);
        mDrawerList.setAdapter(mAdapter);

    }

    private void setDrawer(){
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                R.string.open,R.string.closed) {

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                invalidateOptionsMenu();
            }

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                invalidateOptionsMenu();
            }
        };
        mDrawerToggle.setDrawerIndicatorEnabled(true);
        mDrawerLayout.setDrawerListener(mDrawerToggle);


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


	public void openList(String sub,String subcode){
		Intent i = new Intent(this, PaperList.class);
        i.putExtra("subcode",subcode);
		i.putExtra("sub" , sub);
        i.putExtra("grade",grade);
		startActivity(i);
	}


    @Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_main, menu);
		return true;
	}

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
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

        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
		return super.onOptionsItemSelected(item);
	}
}
