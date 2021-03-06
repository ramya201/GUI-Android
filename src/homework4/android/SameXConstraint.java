package homework4.android;

import homework2.android.GraphicalObject;
import homework2.android.objects.OutlineRect;

import java.util.ArrayList;

public class SameXConstraint implements Solvable {

	@Override
	public int evaluate(ArrayList<Variable> params, GraphicalObject parent) {
		
		int value = params.get(0).getValue();
		((OutlineRect)parent).setX(value);
		
		return value;
	}

}
