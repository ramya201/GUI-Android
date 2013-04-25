package homework3.android;

import android.graphics.Color;
import android.graphics.Point;
import homework2.android.objects.Line;
import homework2.android.objects.OutlineRect;
import homework4.android.EdgeX1Constraint;
import homework4.android.EdgeX2Constraint;
import homework4.android.EdgeY1Constraint;
import homework4.android.EdgeY2Constraint;
import homework4.android.Variable;

public class EdgeBehavior extends NewLineBehavior {
	protected OutlineRect start_node;
	protected OutlineRect end_node;

	public EdgeBehavior(boolean onePoint, int color, int lineThickness) {
		super(onePoint, color, lineThickness);
	}

	public OutlineRect getStart_node() {
		return start_node;
	}

	public OutlineRect getEnd_node() {
		return end_node;
	}

	public void setStart_node(OutlineRect start_node) {
		this.start_node = start_node;
	}

	public void setEnd_node(OutlineRect end_node) {
		this.end_node = end_node;
	}
	
	@Override
	public void stop(BehaviorEvent event) {
		super.stop(event);
		
		((Line)newObject).setX1(start_node.getX());
		((Line)newObject).setY1(start_node.getY());
		
		((Line)newObject).setX2(end_node.getX());
		((Line)newObject).setY2(end_node.getY());
		
		Variable v1 = ((Line)newObject).getVar_x1();
		v1.setEqn(new EdgeX1Constraint());
		v1.addDep(start_node.getVar_x());
		v1.addDep(start_node.getVar_width());
		
		Variable v2 = ((Line)newObject).getVar_y1();
		v2.setEqn(new EdgeY1Constraint());
		v2.addDep(start_node.getVar_y());
		v2.addDep(start_node.getVar_height());
		
		Variable v3 = ((Line)newObject).getVar_x2();
		v3.setEqn(new EdgeX2Constraint());
		v3.addDep(end_node.getVar_x());
		
		Variable v4 = ((Line)newObject).getVar_y2();
		v4.setEqn(new EdgeY2Constraint());
		v4.addDep(end_node.getVar_y());
	}

	

}
