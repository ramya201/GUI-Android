package homework2.android.objects;

import homework2.android.AlreadyHasGroupRunTimeException;
import homework2.android.BoundaryRectangle;
import homework2.android.GraphicalObject;
import homework2.android.Group;
import homework3.android.Selectable;
import homework4.android.Variable;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.Region;

public class FilledRect implements GraphicalObject {
	
	private int x;
	private int y;
	private int width;
	private int height;
	private int color;
	private Group group;
	private Matrix m;
	
	private Variable var_x;
	private Variable var_y;
	private Variable var_width;
	private Variable var_height;
	private Variable var_color;
	
	public FilledRect() {
		this(0,0,0,0,0);
	}

	public FilledRect(int x, int y, int width, int height, int color) {
		super();
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.color = color;
		
		var_x = new Variable(x,this);
		var_y = new Variable(y,this);
		var_width = new Variable(width,this);
		var_height = new Variable(height,this);
		var_color = new Variable(color,this);
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

	@Override
	public void draw(Canvas graphics, Path clipShape) {
		Paint p = new Paint();
		p.setStyle(Paint.Style.FILL);
		p.setColor(color);			
		if (group != null) {
			graphics.clipPath(clipShape,Region.Op.REPLACE);
			Point left_top = group.childToParent(new Point(x,y));
			Point right_bottom = group.childToParent(new Point(((x+width)), (y+height)));			
			graphics.drawRect(left_top.x, left_top.y, right_bottom.x, right_bottom.y, p);			
		}		
	}

	@Override
	public BoundaryRectangle getBoundingBox() {
		if (group == null) {
			return new BoundaryRectangle(x, y ,width, height);
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
		this.y = y;
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


}
