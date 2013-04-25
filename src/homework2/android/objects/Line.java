package homework2.android.objects;

import homework2.android.*;
import homework3.android.Selectable;
import homework4.android.Variable;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.Region;

public class Line implements GraphicalObject,Selectable {
	
	private int x1;
	private int x2;
	private int y1;
	private int y2;
	private int color;
	private int lineThickness;
	private Group group;
	private Matrix m;
	private boolean interimSelected;
	private boolean selected;
	
	private Variable var_x1;
	private Variable var_x2;
	private Variable var_y1;
	private Variable var_y2;
	private Variable var_color;
	private Variable var_lineThickness;
	
	public Line() {
		this(0,0,0,0,0,0);
	}

	public Line(int x1, int y1, int x2, int y2, int color, int lineThickness) {
		super();
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;		
		this.y2 = y2;
		this.color = color;
		this.lineThickness = lineThickness;
		
		var_x1 = new Variable(x1,this);
		var_y1 = new Variable(y1,this);
		var_x2 = new Variable(x2,this);
		var_y2 = new Variable(y2,this);
		var_color = new Variable(color,this);
		var_lineThickness = new Variable(lineThickness, this);		
	}

	public int getX1() {
		return x1;
	}

	public int getX2() {
		return x2;
	}

	public int getY1() {
		return y1;
	}

	public int getY2() {
		return y2;
	}

	public int getColor() {
		return color;
	}

	public int getLineThickness() {
		return lineThickness;
	}

	public Variable getVar_x1() {
		return var_x1;
	}

	public Variable getVar_x2() {
		return var_x2;
	}

	public Variable getVar_y1() {
		return var_y1;
	}

	public Variable getVar_y2() {
		return var_y2;
	}

	public Variable getVar_color() {
		return var_color;
	}

	public Variable getVar_lineThickness() {
		return var_lineThickness;
	}

	public void setX1(int x1) {
		BoundaryRectangle old = getBoundingBox();
		this.x1 = x1;
		var_x1.setValue(x1);
		var_x1.setOOD(true);
		group.resizeChild(this);
		group.damage(old.union(getBoundingBox()));		
	}

	public void setX2(int x2) {
		BoundaryRectangle old = getBoundingBox();
		this.x2 = x2;
		var_x2.setValue(x2);
		var_x2.setOOD(true);
		group.resizeChild(this);
		group.damage(old.union(getBoundingBox()));
	}

	public void setY1(int y1) {
		BoundaryRectangle old = getBoundingBox();
		this.y1 = y1;
		var_y1.setValue(y1);
		var_y1.setOOD(true);
		group.resizeChild(this);
		group.damage(old.union(getBoundingBox()));
	}

	public void setY2(int y2) {
		BoundaryRectangle old = getBoundingBox();
		this.y2 = y2;
		var_y2.setValue(y2);
		var_y2.setOOD(true);
		group.resizeChild(this);
		group.damage(old.union(getBoundingBox()));
	}

	public void setColor(int color) {
		BoundaryRectangle old = getBoundingBox();
		this.color = color;
		var_color.setValue(color);
		var_color.setOOD(true);
		group.damage(old.union(getBoundingBox()));
	}

	public void setLineThickness(int lineThickness) {
		BoundaryRectangle old = getBoundingBox();
		this.lineThickness = lineThickness;
		var_lineThickness.setValue(lineThickness);
		var_lineThickness.setOOD(true);
		group.damage(old.union(getBoundingBox()));
	}

	@Override
	public void draw(Canvas graphics, Path clipShape) {
		Paint p = new Paint();
		p.setStyle(Paint.Style.STROKE);
		p.setColor(color);			
		p.setStrokeWidth(lineThickness);
		if (group != null) {
			graphics.clipPath(clipShape,Region.Op.REPLACE);
			Point start = group.childToParent(new Point(x1, y1));
			Point stop = group.childToParent(new Point(x2,y2));			
			graphics.drawLine(start.x, start.y, stop.x, stop.y, p);			
		}		
	}

	@Override
	public BoundaryRectangle getBoundingBox() {
		int min_x = Math.min(x1,x2);
		int min_y = Math.min(y1,y2);
		Point start = group.childToParent(new Point(min_x,min_y));
		
		if (x1 == x2) {
			BoundaryRectangle child = new BoundaryRectangle((start.x - (lineThickness/2)),start.y,lineThickness, (y2-y1));
			return child.intersection(group.getBoundingBox());
		} else if (y1 == y2) {
			BoundaryRectangle child = new BoundaryRectangle(start.x,(start.y - (lineThickness/2)),(x2-x1),lineThickness);
			return child.intersection(group.getBoundingBox());
		} else {			
			BoundaryRectangle child = new BoundaryRectangle((start.x - lineThickness/2),(start.y - lineThickness/2),(Math.abs(x2-x1) + lineThickness),(Math.abs(y2-y1) + lineThickness));
			return child.intersection(group.getBoundingBox());
		}
	}

	@Override
	public void moveTo(int x, int y) {
		BoundaryRectangle old = getBoundingBox();
		
		int old_x1 = x1;
		int old_y1 = y1;
		int old_x2 = x2;
		int old_y2 = y2;
		
		if ( y2 > y1 && x2 > x1) {		
			x1 = x + lineThickness/2;
			y1 = y + lineThickness/2;
			x2 = x1 + (old_x2 - old_x1);
			y2 = y1 + (old_y2 - old_y1);			
		} else if ( y2 < y1 && x2 > x1) {
			x1 = x + lineThickness/2;		
			y1 = y + (old_y1 - old_y2) + lineThickness/2;
			x2 = x1 + (old_x2 - old_x1);
			y2 = y + lineThickness/2;
		} else if (y2 > y1 && x2 < x1) {
			x2 = x + lineThickness/2;
			y2 = y + (old_y2 - old_y1) + lineThickness/2;
			x1 = x2 + (old_x1 - old_x2);
			y1 = y + lineThickness/2;			
		} else if (y2 < y1 && x2 < x1) {
			x2 = x + lineThickness/2;
			y2 = y + lineThickness/2;
			x1 = x2 + (old_x1 - old_x2);
			y1 = y2 + (old_y1 - old_y2);
		} else if (x2 == x1) {
			if (y2 > y1) {
				x1 = x + lineThickness/2;
				y1 = y;
				x2 = x1;
				y2 = y1 + (old_y2 - old_y1);
			} else if (y2 < y1) {
				x2 = x + lineThickness/2;
				y2 = y;
				x1 = x2;
				y1 = y2 + (old_y1 - old_y2);
			}
		} else if ( y2 == y1) {
			if (x2 > x1) {
				x1 = x;
				y1 = y + lineThickness/2;
				x2 = x1 + (old_x2 - old_x1);
				y2 = y1;
			} else if (x2 < x1) {
				x2 = x;
				y2 = y + lineThickness/2;
				x1 = x2 + (old_x1 - old_x2);
				y1 = y2;
			}
		}
		var_x1.setValue(x1);
		var_x1.setOOD(true);
		var_y1.setValue(y1);
		var_y1.setOOD(true);
		var_x2.setValue(x2);
		var_x2.setOOD(true);
		var_y2.setValue(y2);
		var_y2.setOOD(true);
		group.resizeChild(this);
		group.damage(old.union(getBoundingBox()));
	}

	@Override
	public Group getGroup() {
		return group;
	}

	@Override
	public void setGroup(Group group) throws AlreadyHasGroupRunTimeException {
		if (group != null) {
			if (this.group==null) {
				this.group = group;	
				this.group.damage(getBoundingBox());
			} else {
				throw new AlreadyHasGroupRunTimeException();
			}			
		} else {
			this.group.damage(getBoundingBox());
			this.group = group;
		}		
	}

	@Override
	public boolean contains(int x, int y) {
		BoundaryRectangle rect = getBoundingBox();
		if (group == null) {
			return rect.contains(x, y);
		} else {
			Point testPt = group.childToParent(new Point(x,y));
			return rect.contains(testPt.x, testPt.y);
		}
	}

	@Override
	public void setAffineTransform(Matrix af) {
		m = af;		
	}

	@Override
	public Matrix getAffineTransform() {
		return m;
	}
	
	@Override
	public void setInterimSelected(boolean interimSelected) {
		this.interimSelected = interimSelected;		
	}

	@Override
	public boolean isInterimSelected() {
		return interimSelected;
	}

	@Override
	public void setSelected(boolean selected) {
		this.selected = selected;		
	}

	@Override
	public boolean isSelected() {
		return selected;
	}

}
