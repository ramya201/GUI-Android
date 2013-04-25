package homework4.android; 


import homework2.android.*;
import homework2.android.containers.*;
import homework2.android.objects.*;
import android.os.Bundle;
import android.util.Log;
import android.graphics.Color;

public class TestHomework4 extends TestFrame {
	public void onCreate(Bundle savedInstanceState) {
		Log.d("DV", "onCreate  == "+this);	
		super.onCreate(savedInstanceState);	
		Thread t = new Thread(new Runnable() {
			public void run() {
				Log.d("DV", "run()  == "+this);	
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
		Log.d("DV", "test()  == "+this);	

	try {

        println ("1. Creating blue, red & black rects");
        GraphicalObject blueRect = new OutlineRect (0, 0, 50, 80, Color.BLUE, 5);
        GraphicalObject redRect = new OutlineRect (100, 0, 50, 80, Color.RED, 1);
        GraphicalObject blackRect = new OutlineRect (200, 0, 80, 40, Color.BLACK, 3);
        Group windowgroup = new SimpleGroup (0, 0, 300, 400);
        Group group = new SimpleGroup (0, 0, 300, 400);
        addChild (windowgroup);

	windowgroup.addChild (group);
        group.addChild (blueRect);
        group.addChild (redRect);
        group.addChild(blackRect);
	redraw (windowgroup);

        println ("2. Moving blue to 30,30, Red & Black shouldn't move");
	pause();
	blueRect.moveTo(30,30);
	ConstraintSolver.evaluate();
	redraw (windowgroup);

	println ("3. Adding constraint to red rect to be at right of blue");
	
	//Get the property of the Object which is to be constrained - X of redRect
	Variable v1 = ((OutlineRect)redRect).getVar_x();	
	//Set constraint - RightConstraint() is a class which implements Solvable.
	v1.setEqn(new RightConstraint());	
	//Add the variables on which this property depends on to the constrained property's list of dependable variables 
	//- X of redRect depends on X & width of blueRect
	v1.addDep(((OutlineRect)blueRect).getVar_x());
	v1.addDep(((OutlineRect)blueRect).getVar_width());
	
	//Get the property of the Object which is to be constrained - Y of redRect
	Variable v2 = ((OutlineRect)redRect).getVar_y();	
	//Set constraint - SameYConstraint() is a class which implements Solvable.
	v2.setEqn(new SameYConstraint());	
	//Add the variables on which this property depends on to the constrained property's list of dependable variables 
	//- Y of redRect depends on Y of blueRect
	v2.addDep(((OutlineRect)blueRect).getVar_y());
	
	println ("4. Adding constraint to black rect to be at bottom of red");
	// Setting a constraint on blackRect to be below redRect
	Variable v3 = ((OutlineRect)blackRect).getVar_x();
	v3.setEqn(new SameXConstraint());
	v3.addDep(((OutlineRect)redRect).getVar_x());
	
	Variable v4 = ((OutlineRect)blackRect).getVar_y();
	v4.setEqn(new BottomConstraint());
	v4.addDep(((OutlineRect)redRect).getVar_y());
	v4.addDep(((OutlineRect)redRect).getVar_height());
	
	println ("Red should move to be at 80,30 & black should move to 80,115");
	pause();

	//Solve all constraints on all variables before redrawing
	ConstraintSolver.evaluate();
	redraw (windowgroup);

	println("5. Move Blue, red & black should move automatically");
	pause();
	blueRect.moveTo(0,0);
	ConstraintSolver.evaluate();
	redraw (windowgroup);

	println ("6. Creating lines with different colors");
	pause();
	GraphicalObject mainLine = new Line(0,120,50,180,Color.GREEN,3);
	GraphicalObject childLine = new Line(80,120,130,180,Color.BLACK,3);
	group.addChild(mainLine);
	group.addChild(childLine);
	
	ConstraintSolver.evaluate();
	redraw (windowgroup);
	
	println ("7. Constraining second line to follow color of first");
	
	//Setting color of childLine to be the same as mainLine
	Variable v5 = ((Line)childLine).getVar_color();
	v5.setEqn(new SameColorConstraint());
	v5.addDep(((Line)mainLine).getVar_color());
	
	ConstraintSolver.evaluate();
	redraw (windowgroup);
	
	println ("6. Changing color of first line to Magenta. Second line color should change automatically");
	pause();
	
	((Line)mainLine).setColor(Color.MAGENTA);
	ConstraintSolver.evaluate();
	redraw (windowgroup);
	
	println ("DONE. close the window to stop");

	} catch(Exception e) { println ("got an exception " + e); }
    }
}
