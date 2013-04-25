package homework4.android;

import homework2.android.GraphicalObject;
import homework2.android.objects.Line;
import homework2.android.objects.OutlineRect;

import java.util.ArrayList;

public class SameColorConstraint implements Solvable {

	@Override
	public int evaluate(ArrayList<Variable> params, GraphicalObject parent) {
		int value = params.get(0).getValue();
		((Line)parent).setColor(value);

		return value;
	}

}
