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

public class OutlineRect implements GraphicalObject {

	private int x;
	private int y;
	private int width;
	private int height;
	private int color;
	private int lineThickness;
	private Group group;
	private Matrix m;
	
	private Variable var_x;
	private Variable var_y;
	private Variable var_width;
	private Variable var_height;
	private Variable var_color;
	private Variable var_lineThickness;


	public OutlineRect() {
		this(0,0,0,0,0xffffff,0);
	}

	public OutlineRect(int x, int y, int width, int height, int color,
			int lineThickness) {
		super();
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.color = color;
		this.lineThickness = lineThickness;
		m = new Matrix();
		
		var_x = new Variable(x,this);
		var_y = new Variable(y,this);
		var_width = new Variable(width,this);
		var_height = new Variable(height,this);
		var_color = new Variable(color,this);
		var_lineThickness = new Variable(lineThickness,this);
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public int getColor() {
		return color;
	}

	public int getLineThickness() {
		return lineThickness;
	}

	public Matrix getM() {
		return m;
	}

	public Variable getVar_x() {
		return var_x;
	}

	public Variable getVar_y() {
		return var_y;
	}

	public Variable getVar_width() {
		return var_width;
	}

	public Variable getVar_height() {
		return var_height;
	}

	public Variable getVar_color() {
		return var_color;
	}

	public Variable getVar_lineThickness() {
		return var_lineThickness;
	}

	public void setX(int x) {
		BoundaryRectangle old = getBoundingBox();
		this.x = x;
		var_x.setValue(x);
		var_x.setOOD(true);
		group.resizeChild(this);
		group.damage(old.union(getBoundingBox()));
	}

	public void setY(int y) {
		BoundaryRectangle old = getBoundingBox();
		this.y = y;
		var_y.setValue(y);
		var_y.setOOD(true);
		group.resizeChild(this);
		group.damage(old.union(getBoundingBox()));
	}

	public void setWidth(int width) {
		BoundaryRectangle old = getBoundingBox();
		this.width = width;
		var_width.setValue(width);
		var_width.setOOD(true);
		group.resizeChild(this);
		group.damage(old.union(getBoundingBox()));
	}

	public void setHeight(int height) {
		BoundaryRectangle old = getBoundingBox();
		this.height = height;
		var_height.setValue(height);
		var_height.setOOD(true);
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
		graphics.clipPath(clipShape,Region.Op.REPLACE);
		if (group != null) {		
			Point left_top = group.childToParent(new Point((x+(lineThickness/2)),(y+(lineThickness/2))));
			Point right_bottom = null;
			if (lineThickness % 2 != 0) {
				right_bottom = group.childToParent(new Point(((x+width-1)-(lineThickness/2)), ((y+height-1)-(lineThickness/2))));
			} else if (lineThickness % 2 == 0) {
				right_bottom = group.childToParent(new Point((x+width-(lineThickness/2)), (y+height-(lineThickness/2))));
			}
			graphics.drawRect(left_top.x, left_top.y, right_bottom.x, right_bottom.y, p);
		}		
	}

	@Override
	public BoundaryRectangle getBoundingBox() {		
		if (group == null) {
			BoundaryRectangle child = new BoundaryRectangle(x, y ,width, height);
			return child.intersection(group.getBoundingBox());
		} else {
			Point left_top = group.childToParent(new Point(x,y));
			BoundaryRectangle child = new BoundaryRectangle(left_top.x,left_top.y, width,height);
			return child.intersection(group.getBoundingBox());

		}
	}

	@Override
	public void moveTo(int x, int y) {
		BoundaryRectangle old = getBoundingBox();
		this.x = x;
		var_x.setValue(x);
		var_x.setOOD(true);
		this.y = y;
		var_y.setValue(y);
		var_y.setOOD(true);
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
		//group.resizeChild(this);
	}

	@Override
	public Matrix getAffineTransform() {
		return m;
	}
}
