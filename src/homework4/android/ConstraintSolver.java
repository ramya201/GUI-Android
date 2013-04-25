package homework4.android;

import homework2.android.GraphicalObject;

import java.util.ArrayList;

public class ConstraintSolver {
	private static ArrayList<Variable> params = new ArrayList<Variable>();
	private static ArrayList<GraphicalObject> modifiedObjects = new ArrayList<GraphicalObject>();
	
	public static void addParams(Variable var) {
		params.add(var);
		var.setOOD(true);
	}
	
	public static void removeParams(Variable var) {
		params.remove(var);
	}
	
	public static void evaluate() {
		for ( Variable var: params) {
			if (var.isOOD()) {
				var.setOOD(false);
				for (Variable dep_var: var.getParams()) {
					dep_var.evaluate();
					dep_var.setOOD(true);
					modifiedObjects.add(dep_var.getParent());
					evaluate();
				}
			}
		}
		for (GraphicalObject g: modifiedObjects) {
			g.getGroup().damage(g.getBoundingBox());
		}
		modifiedObjects.clear();
	}
}
