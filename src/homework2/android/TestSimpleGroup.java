package homework2.android;

import homework2.android.containers.*;
import homework2.android.objects.*;
import android.graphics.Color;
import android.os.Bundle;

public class TestSimpleGroup extends TestFrame {
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Thread t = new Thread(new Runnable() {
			public void run() {
				while (!drawView.getOnDrawFirstCalled()) {
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

	private void test() {
		// After implementing OutlineRect and SimpleGroup, uncomment the testing
		// code

		int nObjects = 4;
		try {
			Bundle extras = getIntent().getExtras();
			nObjects = Integer.parseInt(extras.getString("nObject"));
			println("nObjects = " + nObjects);
		} catch (Exception e) {
			println("usage:  TestSimpleGroup [# of graphical objects]\n"
					+ "using " + nObjects + " objects by default");
		}

		println("creating black frame");
		addChild(new OutlineRect(9, 9, 182, 182, Color.BLACK, 1));

		println("creating SimpleGroup inside black frame");
		Group group = new SimpleGroup(10, 10, 180, 180);
		addChild(group);

		println("creating Rects at random places");
		GraphicalObject[] objects = new GraphicalObject[nObjects];
		int[] colors = { Color.BLACK, Color.RED, Color.BLUE };
		for (int i = 0; i < nObjects; ++i) {
			objects[i] = new OutlineRect(-20 + random(200), -20 + random(200),
					random(100), random(100), random(colors), 1);
			try {
				group.addChild(objects[i]);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		redraw(group);
		pause();

		println("moving rectangles 1000 times");
		println("hit back key to stop");
		for (int i = 0; i < 1000; ++i) {
			GraphicalObject gobj = (GraphicalObject) random(objects);
			gobj.moveTo(-20 + random(200), -20 + random(200));
			group.bringChildToFront(gobj);
			redraw(group);
			sleep(500);
		}

	}
}
