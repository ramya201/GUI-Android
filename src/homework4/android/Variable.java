package homework4.android;

import homework2.android.GraphicalObject;

import java.util.ArrayList;

public class Variable {
	//Value of variable
	private int value;
	
	// List of variables the given variable depends on
	private ArrayList<Variable> dep = new ArrayList<Variable>();
	
	//List of variables which depend on the given variable
	private ArrayList<Variable> params = new ArrayList<Variable>();
	
	//Constraint code
	private Solvable eqn;
	
	//Signifies if Variable is OutOfDate
	private boolean OOD;
	
	//The GraphicalObject the property variable belongs to
	private GraphicalObject parent;
		
	public Variable() {
		value = 0;
		OOD = false;		
	}
	public Variable(int value, GraphicalObject parent) {
		this.value = value;
		this.parent = parent;
		OOD = false;
	}
	public Variable(Solvable eqn) {
		value = 0;
		OOD = false;
		this.eqn = eqn;
	}
	public Variable(int value,GraphicalObject parent, Solvable eqn) {
		this.value = value;
		this.parent = parent;
		this.eqn = eqn;
		OOD = false;
	}

	public int getValue() {
		return value;
	}

	public ArrayList<Variable> getDep() {
		return dep;
	}

	public Solvable getEqn() {
		return eqn;
	}
	public ArrayList<Variable> getParams() {
		return params;
	}
	public boolean isOOD() {
		return OOD;
	}
	public GraphicalObject getParent() {
		return parent;
	}
	public void setValue(int value) {
		this.value = value;
	}
	public void setDep(ArrayList<Variable> dep) {
		this.dep = dep;
	}
	public void setEqn(Solvable eqn) {
		this.eqn = eqn;
	}
	public void setParent(GraphicalObject parent) {
		this.parent = parent;
	}
	public void setOOD(boolean oOD) {
		OOD = oOD;
	}
	public void addDep(Variable var){
		dep.add(var);
		var.addParam(this);
		ConstraintSolver.addParams(var);
	}
	private void addParam(Variable var) {
		params.add(var);
	}
	public void removeDep(Variable var) {
		dep.remove(var);
		var.getParams().remove(this);
		if (var.getParams().isEmpty()) {
			ConstraintSolver.removeParams(var);
		}
	}
	public void clearDep() {
		for (Variable var: dep) {
			var.getParams().remove(this);
			
			if (var.getParams().isEmpty()) {
				ConstraintSolver.removeParams(var);
			}
		}
		dep.clear();
	}
	public int evaluate(){
		if (! eqn.equals(null)) {
			value = eqn.evaluate(dep,parent);
		}
		return value;
	}
}
