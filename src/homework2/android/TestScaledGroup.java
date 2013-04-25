package homework2.android;


import homework2.android.containers.*;
import homework2.android.objects.*;
import android.graphics.Color;
import android.os.Bundle;

public class TestScaledGroup extends TestFrame{
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
		
		int nObjects = 1;
		try {
			Bundle extras = getIntent().getExtras();
			nObjects = Integer.parseInt(extras.getString("nObject"));
			println("nObjects = " + nObjects);
		} catch (Exception e) {
			println("usage:  TestSimpleGroup [# of graphical objects]\n"
					+ "using " + nObjects + " objects by default");
		}

		println("creating black frame");
		addChild(new OutlineRect(9, 9, 202, 202, Color.BLACK, 1));

		println("creating ScaledGroup inside black frame of width & height 200");
		Group group = new ScaledGroup(10, 10, 200, 200, 1.0, 0.5);
		addChild(group);

		println("creating Rect(0, 20, 100, 100) ScaleX = 2.0, ScaleY = 0.5");
		GraphicalObject[] objects = new GraphicalObject[nObjects];
		for (int i = 0; i < nObjects; ++i) {
			objects[i] = new OutlineRect(0, 10, 100, 100, Color.RED, 1);
			try {
				group.addChild(objects[i]);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		redraw(group);
		pause();	
	}
}
