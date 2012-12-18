package com.trainpuzzle.ui.windows;


import com.trainpuzzle.R;
import com.trainpuzzle.model.board.Board;
import com.trainpuzzle.model.board.CompassHeading;
import com.trainpuzzle.model.board.Connection;
import com.trainpuzzle.model.board.Tile;
import com.trainpuzzle.model.board.Track;
import com.trainpuzzle.model.board.Train;
import com.trainpuzzle.model.board.Landscape.LandscapeType;
import com.trainpuzzle.model.board.Obstacle.ObstacleType;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;


public class BoardPanel extends View {

private int scrollX = 0;

public int getscrollX() {
	return scrollX;
}
public int getscrollY() {
	return scrollY;
}

private int scrollY = 0;



private Bitmap grass = BitmapFactory.decodeResource(getResources(), R.drawable.grass2);
private Bitmap water = BitmapFactory.decodeResource(getResources(), R.drawable.water2);
private Bitmap rock = BitmapFactory.decodeResource(getResources(), R.drawable.rock);
private Bitmap trees = BitmapFactory.decodeResource(getResources(), R.drawable.trees);
private Bitmap redstation = BitmapFactory.decodeResource(getResources(), R.drawable.redstationfront);
private Bitmap greenstation = BitmapFactory.decodeResource(getResources(), R.drawable.greenstationfront);
private Bitmap straightTrack = BitmapFactory.decodeResource(getResources(), R.drawable.straighttrack);
private Bitmap permanentStraightTrack = BitmapFactory.decodeResource(getResources(), R.drawable.permanenttrack);
private Bitmap diagonalTrack = BitmapFactory.decodeResource(getResources(), R.drawable.diagonaltrack);
private Bitmap curveLeftTrack = BitmapFactory.decodeResource(getResources(), R.drawable.curvelefttrack);
private Bitmap curveRightTrack = BitmapFactory.decodeResource(getResources(), R.drawable.curverighttrack);
private Bitmap trainPic = BitmapFactory.decodeResource(getResources(), R.drawable.train);

private Board board = new Board();
private Train train = new Train();

public BoardPanel(Context context)
{
	super(context);
	
}
public BoardPanel(Context context, Board board, Train train)
{
	super(context);
	this.board = board;
	this.train = train;
	
}
public BoardPanel(Context context, AttributeSet attrs) {
 
	super( context, attrs );
}

public BoardPanel(Context context, AttributeSet attrs, int defStyle) {
 
	super( context, attrs, defStyle );
}

@Override
public void onDraw(Canvas canvas)
{
	canvas.drawColor(Color.BLACK);
	

	for (int x = 0; x < board.NUMBER_OF_COLUMNS; x++)
	{
		for (int y = 0; y < board.NUMBER_OF_ROWS; y++)
		{
	    //Log.d("Draw tile:", " " + x + "," + y);
		
		drawLandscape(canvas,x,y);
		drawObstacle(canvas,x,y);
		drawTrack(canvas,x,y);	
		}
	}
	
	drawTrain(canvas);
}

public void onDrawTile(Canvas canvas)
{
	
}
private void drawLandscape(Canvas canvas, int x, int y) {
	Bitmap tileImage;
	Tile tile = board.getTile(y,x);
	LandscapeType landscape = tile.getLandscapeType();
	switch (landscape)
	{
		case WATER:
			tileImage = water;
			canvas.drawBitmap(tileImage, x * tileImage.getWidth() - scrollX, y * tileImage.getHeight() - scrollY, null);
			break;
		case GRASS:
			tileImage = grass;
			canvas.drawBitmap(tileImage, (float)x * tileImage.getWidth() - scrollX,(float)y *  tileImage.getHeight() - scrollY, null);
			break;
		
	}
}
private void drawObstacle(Canvas canvas, int x, int y){
	Bitmap tileImage;
	Tile tile = board.getTile(y,x);
	if(tile.hasObstacle()){
		ObstacleType obstacle = tile.getObstacleType();
		switch (obstacle)
		{
			case ROCK:
				tileImage = rock;
				canvas.drawBitmap(tileImage, x * tileImage.getWidth() - scrollX, y * tileImage.getHeight() - scrollY, null);
				break;
			case TREES:
				tileImage = trees;
				canvas.drawBitmap(tileImage, x * tileImage.getWidth()- scrollX, y *  tileImage.getHeight() - scrollY, null);
				break;
			case RED_STATION:
				tileImage = redstation;
				canvas.drawBitmap(tileImage, x * tileImage.getWidth()- scrollX, y *  tileImage.getHeight() - scrollY, null);
				break;
			case GREEN_STATION:
				tileImage = greenstation;
				canvas.drawBitmap(tileImage, x * tileImage.getWidth()- scrollX, y *  tileImage.getHeight() - scrollY, null);
				break;
			
		}

	}

	
}
private void drawTrack(Canvas canvas,int x, int y){
	
	Tile tile = board.getTile(y,x);
	if(tile.hasTrack()){
        Bitmap trackImage = drawTrack(tile.getTrack());
		canvas.drawBitmap(trackImage, x * trackImage.getWidth() - scrollX, y * trackImage.getHeight() - scrollY, null);
	}
	
}

public Bitmap drawTrack(Track track){
	
    Bitmap bmComposite = Bitmap.createBitmap(grass.getWidth(), grass.getHeight(), grass.getConfig());
    Canvas canvas = new Canvas(bmComposite);
    
	Bitmap tileImage;
	
	Connection diagonal = new Connection(CompassHeading.NORTHWEST, CompassHeading.SOUTHEAST);
	Connection straight = new Connection(CompassHeading.EAST, CompassHeading.WEST);
	Connection curveLeft = new Connection(CompassHeading.NORTHWEST, CompassHeading.SOUTH);
	Connection curveRight = new Connection(CompassHeading.NORTHEAST, CompassHeading.SOUTH);
	
	
		for(Connection connection:track.getConnections()){
			
			
			for(int i = 0; i < 2; i++){
				if(connection.equals(straight)){
					if(track.isUnremovable()){
						tileImage = permanentStraightTrack;
						tileImage = rotateBitmap(tileImage, i*2);
						canvas.drawBitmap(tileImage,0,0,null);
						
					}
					else{
						tileImage = straightTrack;
						tileImage = rotateBitmap(tileImage, i*2);
						canvas.drawBitmap(tileImage,0,0,null);
					}

				}
				straight.rotate90Degrees();
				
				if(connection.equals(diagonal)){
					tileImage = diagonalTrack;
					tileImage = rotateBitmap(tileImage, i*2);
					canvas.drawBitmap(tileImage,0,0,null);
				}
				diagonal.rotate90Degrees();
			}
			for(int i = 0; i < 4; i++){	
				if(connection.equals(curveLeft)){
					tileImage = curveLeftTrack;
					tileImage = rotateBitmap(tileImage, i*2);
					canvas.drawBitmap(tileImage,0,0,null);
				}
				curveLeft.rotate90Degrees();
				if(connection.equals(curveRight)){
					tileImage = curveRightTrack;
					tileImage = rotateBitmap(tileImage, i*2);
					canvas.drawBitmap(tileImage,0,0,null);
				}
				curveRight.rotate90Degrees();
			}
		}
	return bmComposite;
}

private void drawTrain(Canvas canvas){
	Bitmap trainImage;
	int x = train.getLocation().getColumn();
	int y = train.getLocation().getRow();
	int headingInt = train.getHeading().ordinal();
	
	trainImage = rotateBitmap(trainPic, (headingInt + 5)% 8);		
	canvas.drawBitmap(trainImage, x * trainPic.getWidth() - scrollX, y * trainPic.getHeight() - scrollY, null);
		
}

void testDraw(Canvas canvas){
	

	String sucide_mission = 
"ffffffffffffffffffwwwww\n" +
"ffffffffffffffffffwfffw\n" +
"ffffffffffffffffffwfffw\n" +
"ffffffffffffffffffwfffw\n" + 
"ffffffffffffffffffwwfww\n" +
"fffffffffffffffffffwfw\n" + 
"fffffffffffffffffffwfw\n" +
"fffffffffffffffffffwfw\n" +
"fffffffffffffwwwwwwwfwwwwwfw\n" +
"fffffffffffffwfffffffffffffw\n" +
"fffffffffffffwfwwwwwfwwwwwfw\n" +
"fffffwwwwwfffwfwfffwfwfffwfw\n" +
"wwwwwwfffwwwwwfwwwwwfwwwwwfw\n" +
"fffffffffffffffffffffffffffw\n" +
"wwwwwwfffwwwwwwwwwwwfwwwwwfw\n" +
"     wwfww         wfw\n" +
"      wfw          wfw\n" +
"      wfw          wfw\n" +
"      wfw          wfw\n" +
"      wfw          wfw\n" +
"     wwfww         wfw\n" +
"     wfffwwfw      fff\n" +
"     wffffffw      www\n" +
"     wfffwwfw\n" +
"     wwwww";

	canvas.drawColor(Color.BLACK);
	int x = 0, y = 0;

	for (int i = 0; i < sucide_mission.length(); i++)
	{
		Bitmap tileImage;
		char tile = sucide_mission.charAt(i);

		Log.d("Draw tiles", Character.toString(tile) + " " + x + "," + y);

		switch (tile)
		{
			case 'w':
				tileImage = water;
				canvas.drawBitmap(tileImage, x - scrollX, y - scrollY, null);
				x += 40;
				break;
			case 'f':
				tileImage = grass;
				canvas.drawBitmap(tileImage, x - scrollX, y - scrollY, null);
				x += 40;
				break;
			case ' ':
				x += 40;
				break;
			case '\n':
				y += 40;
				x = 0;
				break;
		}

	}

	//canvas.drawBitmap(adapt, 0, 0, paint);
	//canvas.drawBitmap(corner, origin -scrollX , origin -scrollY, paint);

}

private Bitmap rotateBitmap(Bitmap orgBitmap, int multiplesOf45deg){
    int width = orgBitmap.getWidth();
    int height = orgBitmap.getHeight();

	Matrix matrix = new Matrix();
	

    // rotate the Bitmap
    matrix.postRotate(45*multiplesOf45deg);

    // recreate the new Bitmap
    Bitmap newBitmap = Bitmap.createBitmap(orgBitmap, 0, 0,
                      width, height, matrix, true);
    return newBitmap;
}

public void handleScroll(float distX, float distY)
 {
	Log.d("View: ", "width: " + this.getWidth() + " height: " + this.getHeight());
      // X-Axis ////////////////////////////////

      if(distX > 20.0)
      {
           if(scrollX < board.NUMBER_OF_COLUMNS*80 - this.getWidth())
           {
                scrollX += 40;
           }
      }
      else if(distX < -20.0)
      {
           if(scrollX >= 40)
           {
                scrollX -= 40;
           }
      }
      ////////////////////////////////////////////

      // Y-AXIS //////////////////////////////////
      if(distY > 20.0)
      {
           if(scrollY < board.NUMBER_OF_ROWS*80 - this.getHeight())
           {
                scrollY += 40;
           }
      }
      else if(distY < -20.0)
      {
           if(scrollY >= 40)
           {
                scrollY -= 40;
           }
      }              
      ////////////////////////////////////////////
      if((scrollX <= board.NUMBER_OF_COLUMNS*80) && (scrollY <= board.NUMBER_OF_ROWS*80))
      {
           //adapt = Bitmap.createBitmap(bmp, scrollX, scrollY, 320, 480);
           invalidate();
      }
 }
}