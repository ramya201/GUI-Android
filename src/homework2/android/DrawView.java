package homework2.android;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Bitmap;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;


// use double buffering, like explained here:
// http://stackoverflow.com/questions/2423327/android-view-ondraw-always-has-a-clean-canvas

public class DrawView extends View {	

	private boolean onDrawFirstCalled = false;
	private Path savedPath = null; // these are used to avoid allocating memory in draw or invalidate
	private Rect savedRect = null;
	private Paint savedPaint = null;
    private Canvas backingCanvas = null; //all drawing done to this off-screen canvas/bitmap
    private Bitmap backingBitmap = null;

    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        if (backingBitmap != null) {
        	backingBitmap .recycle();
        }
        backingCanvas= new Canvas();
        backingBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        backingCanvas.setBitmap(backingBitmap);
        backingCanvas.drawColor(Color.WHITE); 
		//Log.d("DV", "size changed to "+w+" "+h+" backingCanvas = "+backingCanvas);	
   }
    public void destroy() {
        if (backingBitmap != null) {
        	backingBitmap.recycle();
        	backingBitmap = null;
    		//Log.d("DV", "destroy backingBitmap");	
        }
    }

	public DrawView(final Context context, final AttributeSet aSet){
		super(context, aSet);
		savedPaint = new Paint();
		savedPath = new Path();
		onDrawFirstCalled = false;
		setBackgroundColor(Color.WHITE); //this should be ignored since a full-size bitmap is copied over it
		savedRect = new Rect();
	}

    // only supports one object at a time, so this should only be used either for test code, or for
	// the single, one top-level group
	public void setGraphicalObject(final GraphicalObject g1, BoundaryRectangle r){
		//Log.d("DV", "setGraphicalObject drawing obj  == "+g1+" backingCanvas  = "+backingCanvas);	
		//Log.d("DV", "setGraphicalObject BoundaryRectangle r == "+r.x + " " + r.y + " " +r.width + " " + r.height);

		if (backingCanvas != null) {
			savedPaint.setColor(Color.WHITE);	
			savedPaint.setStyle(Paint.Style.FILL);
			savedPath.reset();
			savedPath.addRect(r.x,r.y,r.x+r.width,r.y+r.height, android.graphics.Path.Direction.CCW);
			//Log.d("DV", "setGraphicalObject: erasing white rect == "+r.x + " " + r.y + " " +(r.x+r.width) + " " + (r.y+r.height));				
			backingCanvas.clipPath(savedPath, android.graphics.Region.Op.REPLACE);
			backingCanvas.drawRect(r.x,r.y, r.x+r.width,r.y+r.height, savedPaint);
			
			//Log.d("DV", "setGraphicalObject drawing "+ g1);
			g1.draw(backingCanvas, savedPath);

		}

	}

	public boolean getOnDrawFirstCalled(){
		return onDrawFirstCalled;
	}

	@Override
	public void onDraw(Canvas canvas) {	
		int w = backingBitmap.getWidth();
		int h = backingBitmap.getHeight();
		//draw the bitmap to the real canvas c
        //Log.d("DV", "onDraw copying backingbitmap to screen " + backingBitmap+" size "+w+" "+h);
        canvas.clipRect(0,0,w,h,android.graphics.Region.Op.REPLACE);
        backingCanvas.clipRect(0,0,w,h,android.graphics.Region.Op.REPLACE);
        
        savedRect.set(0,0,w,h);
        canvas.drawBitmap(backingBitmap, savedRect, savedRect, null);

		// Make sure the testing procedure starts after the first onDraw is called 
		if(!onDrawFirstCalled)
			onDrawFirstCalled = true;	
	}

	public void redraw(){
		//Log.d("DV", "redraw: called; postInvalidate");				
		postInvalidate();  // do the whole area
	}
}
