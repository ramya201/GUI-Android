package homework2.android;


import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Bundle;
import android.graphics.Matrix;

import android.util.Log;


import android.graphics.Rect; //just to test

public class TestTestFrame extends TestFrame{
	 public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);	
		Thread t = new Thread(new Runnable() {
			public void run() {
				while(!drawView.getOnDrawFirstCalled()){
					try {
						Thread.sleep(200);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				test();
			}
		});
		t.start();
	 }
	 
	 
	 private void test(){
		println("First Pause");
		Rect r = new Rect(2,2,4,5);
		println("new Rect(2,2,4,5); w= " + r.width() + " h= " + r.height());
		
		pause();
		println("20 x's with sleep(250) in between");
		for (int x = 0; x < 20; x ++) {
				print("x");
				sleep(250);
		}
		println("");
		
		println("drawing two rectangles");
		pause();
		drawsomething();
		println("done");
		pause();
		println("hit back key to exit");
	 }
	 
	 
	 private class pretendrect implements GraphicalObject {
		int offsety = 0;
		public void draw(Canvas graphics, Path clipShape) {
			Log.d("DV", "pretendrect Draw drawing obj  == "+this);	
			Paint p = new Paint();
			p.setStyle(Paint.Style.STROKE);
			p.setColor(Color.RED);			
			p.setStrokeWidth(2);
			graphics.drawRect(4, offsety+4, 20, offsety+20, p);			
			p.setColor(Color.BLUE);	
			p.setStyle(Paint.Style.FILL);
			graphics.drawRect(20,offsety+20,24,offsety+24, p);

		};
		public BoundaryRectangle getBoundingBox() {return new BoundaryRectangle(3,offsety+3,21,21);};
		public void moveTo(int x, int y) {};
		public Group getGroup() {return null;};
		public void setGroup(Group group) {};
		public boolean contains(int x, int y) {return false;};
		public void setAffineTransform(Matrix af) {};
		public Matrix getAffineTransform() {return null;};
		public pretendrect(int o){
			offsety = o;
		}
	}
	private void drawsomething() {
		addChild(new pretendrect(0));
		println("drawing two other rectangles");
		pause();
		addChild(new pretendrect(40));
	}
	
}
