package homework3.android;

import homework2.android.*;

public abstract class NewBehavior implements Behavior {
	private boolean onePoint;
	
	public NewBehavior (boolean onePoint) {
		this.onePoint = onePoint;
	}
    public abstract GraphicalObject make (int x1, int y1, int x2, int y2);
    public abstract void resize (GraphicalObject gobj, int x1, int y1, int x2, int y2);
}
