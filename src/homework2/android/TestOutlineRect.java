package homework2.android;

import homework2.android.objects.*;
import android.os.Bundle;
import android.graphics.Color;


public class TestOutlineRect extends TestFrame {
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
		
		// After implementing OutlineRect, uncomment the testing code 
			 
		int lineThickness = 1;
		try {
			Bundle extras = getIntent().getExtras();  			
			lineThickness = Integer.parseInt(extras.getString("lineThickness"));
			println("line thickness = " + lineThickness);
		} catch (Exception e) {
			println("usage:  TestRect [line thickness]\n"
			+ "using line thickness = "
			+ lineThickness + " by default");
		}
		println("creating OutlineRect");
		
		OutlineRect r = new OutlineRect(10, 10, 50, 50, Color.RED, lineThickness);
		addChild(r);
		pause();
		println("moving rectangle with setX(), setY()");
		for (int x = 10; x < 150; x += 30) {
			r.setX(x);
			for (int y = 10; y < 150; y += 30) {
				r.setY(y);
				redraw(r);
				sleep(100);
			}
		}
		println("final bounding box is " + r.getBoundingBox());
		println("final x/y position is "
		+ r.getX() + "," + r.getY());
		pause();
		println("changing to blue");
		r.setColor(Color.BLUE);
		redraw(r);
		pause();
		println("moving rectangle with moveTo ()");
		for (int x = 10; x < 150; x += 30) {
			for (int y = 10; y < 150; y += 30) {
				r.moveTo(x, y);
				redraw(r);
				sleep(100);
			}
		}
		println("final bounding box is " + r.getBoundingBox());
		println("final x/y position is "
		+ r.getX() + "," + r.getY());
		pause();
		println("doubling line thickness to " + lineThickness * 2);
		r.setLineThickness(lineThickness * 2);
		redraw(r);
		pause();
		println("moving rectangle with moveTo ()");
		for (int x = 10; x < 150; x += 30) {
			for (int y = 10; y < 150; y += 30) {
				r.moveTo(x, y);
				redraw(r);
				sleep(100);
			}
		}
		println("final bounding box is " + r.getBoundingBox());
		println("final x/y position is "
		+ r.getX() + "," + r.getY());
		println("hit back key to exit");
		

		
	}
	

}
