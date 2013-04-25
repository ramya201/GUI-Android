package homework4.android;

import homework2.android.GraphicalObject;
import homework2.android.objects.OutlineRect;

import java.util.ArrayList;

public class BottomConstraint implements Solvable {

	@Override
	public int evaluate(ArrayList<Variable> params, GraphicalObject parent) {
		int value = params.get(0).getValue() + params.get(1).getValue() + 5;
		((OutlineRect)parent).setY(value);

		return value;
	}

}
