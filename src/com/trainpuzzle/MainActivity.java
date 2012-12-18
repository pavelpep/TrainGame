package com.trainpuzzle;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import com.trainpuzzle.R;


public class MainActivity extends Activity {
	public final static String LEVEL_NUMBER = "com.pavel.traingame";
    
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
       
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    
    public void selectLevel(View view) {
   
        Intent intent = new Intent(this, LevelSelectActivity.class);
        String message = "hello world";
        intent.putExtra(LEVEL_NUMBER, message);
        startActivity(intent);
    	
        //LevelSelect levelSelect = new LevelSelect(gameController);
    }

}
