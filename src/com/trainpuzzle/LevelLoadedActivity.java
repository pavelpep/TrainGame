package com.trainpuzzle;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.GestureDetector.OnDoubleTapListener;
import android.view.View;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageButton;
import android.widget.TextView;
import android.support.v4.app.NavUtils;


import com.trainpuzzle.R;
import com.trainpuzzle.controller.*;
import com.trainpuzzle.model.board.*;
import com.trainpuzzle.model.level.*;
import com.trainpuzzle.observe.Observer;
import com.trainpuzzle.ui.windows.BoardPanel;



public class LevelLoadedActivity extends Activity implements OnGestureListener, OnDoubleTapListener, Observer {
	
	private GameController gameController = new GameController();
	private Level level;
	private Board board;
	BoardPanel boardPanel;
	private GestureDetector gestureScanner;
	private Train train;
	private Track trackToBePlaced = new Track(new Connection(CompassHeading.EAST, CompassHeading.WEST),TrackType.STRAIGHT_TRACK);


	    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level_loaded);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        
        gestureScanner = new GestureDetector(this, this);
        
        
        // Get the message from the intent
        Intent intent = getIntent();
        int levelSelected = intent.getIntExtra(LevelSelectActivity.LEVEL_NUMBER, 1);
       
        TextView textView =(TextView)findViewById(R.id.textLevelNumber);
        textView.setText("Level " + levelSelected);
        
       
        
        gameController.startGame(levelSelected);
        this.level = gameController.getLevel();
        this.board = this.level.getBoard();
        this.train = gameController.getSimulator().getTrain();
        
        //register as observer
        board.register(this);
        train.register(this);
        
        
        boardPanel = new BoardPanel(this, board, train);
        ViewGroup myBoard = (ViewGroup) findViewById(R.id.myFrame);
        myBoard.addView(boardPanel, LayoutParams.MATCH_PARENT);
       
        //this.setContentView(boardPanel);
    
      
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_level_loaded, menu);
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
    

	
	public boolean onTouchEvent(MotionEvent me){
	    return gestureScanner.onTouchEvent(me);
	}
	
	public void notifyChange(Object object){
		boardPanel.postInvalidate();
	}
    
	public void startBtn(View view) {
		
		gameController.getSimulator().run();
	}
	
	public void resetBtn(View view) {
		gameController.getSimulator().reset();
	}
	
	public void straightBtn(View view) {
		trackToBePlaced = new Track(new Connection(CompassHeading.EAST, CompassHeading.WEST),TrackType.STRAIGHT_TRACK);
		ImageButton ib = (ImageButton)findViewById(R.id.btnRotate);
		ib.setImageBitmap(boardPanel.drawTrack(trackToBePlaced));
	}
	public void diagonalBtn(View view) {
		trackToBePlaced = new Track(new Connection(CompassHeading.NORTHWEST, CompassHeading.SOUTHEAST),TrackType.DIAGONAL_TRACK);
		ImageButton ib = (ImageButton)findViewById(R.id.btnRotate);
		ib.setImageBitmap(boardPanel.drawTrack(trackToBePlaced));
	}
	public void leftCurveBtn(View view) {
		trackToBePlaced = new Track(new Connection(CompassHeading.NORTHWEST, CompassHeading.SOUTH),TrackType.CURVELEFT_TRACK);
		ImageButton ib = (ImageButton)findViewById(R.id.btnRotate);
		ib.setImageBitmap(boardPanel.drawTrack(trackToBePlaced));
	}
	public void rightCurveBtn(View view) {
		trackToBePlaced = new Track(new Connection(CompassHeading.NORTHEAST, CompassHeading.SOUTH), TrackType.CURVERIGHT_TRACK);
		ImageButton ib = (ImageButton)findViewById(R.id.btnRotate);
		ib.setImageBitmap(boardPanel.drawTrack(trackToBePlaced));
	}
	
	public void rotateBtn(View view) {
		trackToBePlaced = new Track(trackToBePlaced);
		trackToBePlaced.rotateTrack();
		ImageButton ib = (ImageButton)findViewById(R.id.btnRotate);
		ib.setImageBitmap(boardPanel.drawTrack(trackToBePlaced));
	}



    
	public boolean onDown(MotionEvent arg0) {
		// TODO Auto-generated method stub
		return false;
		
	}

	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
			float velocityY) {
		// TODO Auto-generated method stub
		
		
	     return false;
	}

	public void onLongPress(MotionEvent e) {
		// TODO Auto-generated method stub
		Log.d("Long Press", " "); 
	}

	public void onShowPress(MotionEvent e) {
		// TODO Auto-generated method stub
		
	}

	public boolean onSingleTapUp(MotionEvent e) {
		
		Log.d("onSingleTapConfirmed", " "+ e.getX() + ", " + e.getY()); 
		
		gameController.placeTrack(trackToBePlaced, ((int)e.getY()-250 + boardPanel.getscrollY())/80, ((int)e.getX() + boardPanel.getscrollX())/80);
	
		return true;

	}
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
			float distanceY) {
		boardPanel.handleScroll(distanceX,distanceY);
		//Log.d("Scroll dx,dy", " " + distanceX + ", " + distanceY); 
		return true;
	}



	public boolean onSingleTapConfirmed(MotionEvent arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean onDoubleTap(MotionEvent e) {
		Log.d("onDoubleTap", " "+ e.getX() + ", " + e.getY());
		gameController.removeTrack(((int)e.getY()-250 + boardPanel.getscrollY())/80, ((int)e.getX() + boardPanel.getscrollX())/80);
		
		return true;
	}

	public boolean onDoubleTapEvent(MotionEvent e) {
		// TODO Auto-generated method stub
		return false;
	}
}
