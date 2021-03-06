package homework4.android;

import homework2.android.GraphicalObject;
import homework2.android.objects.Line;

import java.util.ArrayList;

public class EdgeX1Constraint implements Solvable {

	@Override
	public int evaluate(ArrayList<Variable> params, GraphicalObject parent) {
		int value = params.get(0).getValue();
		((Line)parent).setX1(value);
		return value;
	}

}
