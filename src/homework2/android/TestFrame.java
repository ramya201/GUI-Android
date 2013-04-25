package homework2.android;


import java.io.IOException;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Path;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class TestFrame extends Activity implements Group {

	public DrawView drawView;
	public TextView debugTextView;	
	private String debugString;
	private static final boolean useConsole = false; // false -> use a TextView at the bottom of the window
	private static final String LOG_TAG = "TestFrame";

	@Override
	public void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.test);

		// title is set in AndroidManifest.xml
		// canvas is setup in onDraw in DrawView

		debugTextView = (TextView) findViewById(R.id.debugText);	
		debugTextView.setTextColor(Color.WHITE);
		debugTextView.setTextSize(16);		

		// if useConsole is true, print of log message in LogCat 
		if(useConsole){
			debugTextView.setText("Messages printed in LogCat");					
		}
		// else, initialize debugString, which is the output string for the debug text area
		else{					
			debugString = "";
		}

		println("Starting TestFrame");

		drawView = (DrawView) findViewById(R.id.drawView);		
		drawView.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(final View v){
				unpause();
			}
		});
	}

	BoundaryRectangle savedClipRect;
	public void redraw(final GraphicalObject child) {	
		// if savedClipRect is not null, redraw the canvas
		// else, print a message		
		if (savedClipRect != null) {
			drawView.setGraphicalObject(child, savedClipRect);
			drawView.redraw();
			savedClipRect = null;			

				/* runOnUiThread(new Runnable() {
				@Override
				public void run() {
					drawView.redraw();
					savedClipRect = null;			
				}
			});	
			 */
		} else
			println("no clip rectangle");

	}
    
	// note that clipRect is a BoundaryRectangle, and is therefore the size of the area to be drawn
	// it is NOT 1 pixel bigger (unlike Android's rectangles)
	public void addClipRect(final BoundaryRectangle r) {
		if (savedClipRect != null)
			savedClipRect.add(r);
		else if (r==null)
			savedClipRect = null;
		else
			savedClipRect = new BoundaryRectangle(r);
	}

	//
	// Group interface
	//

	// This class is NOT a correct implementation of Group.
	// It's just a test harness that displays GraphicalObjects
	// on the screen.
	java.util.ArrayList<GraphicalObject> children = new java.util.ArrayList<GraphicalObject>();

	@Override
	public void addChild(final GraphicalObject child) {
		child.setGroup(this);
		addClipRect(child.getBoundingBox());
		redraw(child);
	}

	@Override
	public void removeChild(final GraphicalObject child) {
	}


	@Override
	public void bringChildToFront(final GraphicalObject child) {
	}

	@Override
	public void resizeChild(final GraphicalObject child) {
	}

	@Override
	public void resizeToChildren() {
	}

	@Override
	public void damage(final BoundaryRectangle rectangle) {
		addClipRect(rectangle);
	}

	@Override
	public void draw(final Canvas graphics, final Path clipRect) {
	}

	@Override
	public BoundaryRectangle getBoundingBox() {
		// return the bounding box of the whole canvas
		return new BoundaryRectangle(0, 0, drawView.getWidth(), drawView.getHeight());
	}

	@Override
	public void moveTo(final int x, final int y) {
	}

	@Override
	public Group getGroup() {
		return null;
	}

	@Override
	public void setGroup(final Group group) {
	}

	@Override
	public java.util.List<GraphicalObject> getChildren() {
		return new java.util.ArrayList<GraphicalObject>(children);
	}

	@Override
	public Point parentToChild(final Point pt) {
		return pt;
	}

	@Override
	public Point childToParent(final Point pt) {
		return pt;
	}


	@Override
	public boolean contains(final int x, final int y){
		// check if x, y is within the canvas
		if(x>=0 && x <= drawView.getWidth() && y >= 0 && y <= drawView.getHeight()){
			return true;
		}
		else{
			return false;
		}
	}


	// 
	// Message output
	//

	public void print(final String s){
		if(useConsole){			
			Log.i(LOG_TAG, s);
		}
		else{
			debugString += s;			
			runOnUiThread(new Runnable() {
				@Override
				public void run() {
					debugTextView.setText(debugString);				
				}
			});								
		}

	}

	public void println(final String s){
		if(useConsole){			
			Log.i(LOG_TAG, s);
		}
		else{
			debugString += s;
			debugString += "\n";
			runOnUiThread(new Runnable() {
				@Override
				public void run() {
					debugTextView.setText(debugString);				
				}
			});		
		}

	}


	//
	// Random selections
	//

	private static java.util.Random r = new java.util.Random();

	public int random(final int n) {
		return r.nextInt(n);
	}

	public int random(final int[] things) {
		return things[random(things.length)];
	}

	public Object random(final Object[] things) {
		return things[random(things.length)];
	}

	//
	// Load image
	// load an image file from the "assets" part of the project
	//
	public Bitmap loadImageFully(final String filename) throws IOException {
		Bitmap myBitmap = BitmapFactory.decodeStream(this.getAssets().open(filename));
		if (myBitmap != null) return myBitmap;
		throw new IOException("cannot load file: " + filename);
	}


	// 
	// Sleeping 
	// The function must be called in a non-UI thread

	public void sleep(final int msec) {
		try {
			Thread.sleep(msec);
		} catch (InterruptedException e) {
		}
	}

	//
	// Pause and resume the application
	// The function must be called in a non-UI thread

	public void pause() {
		println("click to continue...");
		synchronized (this) {
			try {
				wait();
			} catch (InterruptedException e) {
			}
		}
	}

	public void unpause() {
		synchronized (this) {
			notify();
		}
	}

	private Matrix ax;
	@Override
	public Matrix getAffineTransform() {
		return ax;
	}

	@Override
	public void setAffineTransform(final Matrix af) {
		ax = af;
	}

}
