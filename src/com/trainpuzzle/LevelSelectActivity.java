package com.trainpuzzle;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.support.v4.app.NavUtils;
import com.trainpuzzle.R;

public class LevelSelectActivity extends Activity {
	
	public final static String LEVEL_NUMBER = "com.pavel.traingame";
    
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level_select);
        getActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_level_select, menu);
        return true;
    }

    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    
    public void levelOne(View view) {
    	   
        Intent intent = new Intent(this, LevelLoadedActivity.class);
        int level = 1;
        intent.putExtra(LEVEL_NUMBER, level);
        startActivity(intent);
    	
    }
    
    public void levelTwo(View view) {
 	   
        Intent intent = new Intent(this, LevelLoadedActivity.class);
        int level = 2;
        intent.putExtra(LEVEL_NUMBER, level);
        startActivity(intent);
    	
    }
    public void levelThree(View view) {
  	   
        Intent intent = new Intent(this, LevelLoadedActivity.class);
        int level = 3;
        intent.putExtra(LEVEL_NUMBER, level);
        startActivity(intent);
    	
    }

}
