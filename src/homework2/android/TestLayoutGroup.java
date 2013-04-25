package homework2.android;

import homework2.android.containers.*;
import homework2.android.objects.*;
import android.graphics.Color;
import android.os.Bundle;

public class TestLayoutGroup extends TestFrame{
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
		
		// After implementing OutlineRect and LayoutGroup, uncomment the testing code
		
		int nObjects = 4;
		try {
			Bundle extras = getIntent().getExtras();
			nObjects = Integer.parseInt(extras.getString("nObject"));
			println("nObjects = " + nObjects);
		} catch (Exception e) {
			println("usage:  TestLayoutGroup [# of graphical objects]\n"
					+ "using " + nObjects + " objects by default");
		}

		println("creating black frame");
		addChild(new OutlineRect(9, 9, 481, 181, Color.BLACK, 1));

		println("creating LayoutGroup inside black frame");
		Group group = new LayoutGroup(10, 10, 480, 180, Group.HORIZONTAL, 0);
		addChild(group);

		println("creating random Rects");
		GraphicalObject[] objects = new GraphicalObject[nObjects];
		int[] colors = { Color.BLACK, Color.RED, Color.BLUE };
		for (int i = 0; i < nObjects; ++i) {
			objects[i] = new OutlineRect(random(200), random(200), 30 + random(20),
					30 + random(20), random(colors), 1 + random(5));
			group.addChild(objects[i]);
		}

		redraw(group);
		pause();

		println("shuffling rectangles 10 times");
		GraphicalObject front = objects[objects.length - 1];
		for (int i = 0; i < 10; ++i) {
			GraphicalObject gobj;
			while ((gobj = (GraphicalObject) random(objects)) == front)
				;
			group.bringChildToFront(gobj);
			front = gobj;
			redraw(group);
			sleep(1000);
		}

		pause();

		println("doubling rectangle widths");
		for (int i = 0; i < objects.length; ++i) {
			OutlineRect r = (OutlineRect) objects[i];
			r.setWidth(r.getWidth() * 2);
			redraw(group);
			sleep(1000);
		}
		println("close the window to exit");		
	}
}
