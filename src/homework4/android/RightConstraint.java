package homework4.android;

import homework2.android.GraphicalObject;
import homework2.android.objects.*;

import java.util.ArrayList;

public class RightConstraint implements Solvable {

	@Override
	public int evaluate(ArrayList<Variable> params, GraphicalObject parent) {
		
		//Do Array bounds checking as appropriate
		int value = params.get(0).getValue() + params.get(1).getValue() + 5;
		((OutlineRect)parent).setX(value);

		return value;
	}

}
