package homework4.android;

import homework2.android.GraphicalObject;

import java.util.ArrayList;

public interface Solvable {
	
	//params is the list of variables the property depends on & parent is the Graphical Object the constrained property belongs to
	public int evaluate(ArrayList<Variable> params, GraphicalObject parent);
}
