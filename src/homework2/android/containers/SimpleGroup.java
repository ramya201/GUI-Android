package homework2.android.containers;

import homework2.android.*;
import homework4.android.Variable;

import java.util.ArrayList;
import java.util.List;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.Point;

public class SimpleGroup implements Group {
	
	private int x;
	private int y;
	private int width;
	private int height;
	private ArrayList<GraphicalObject> children = new ArrayList<GraphicalObject>();
	private Group group;
	private Matrix m;
	
	private Variable var_x;
	private Variable var_y;
	private Variable var_width;
	private Variable var_height;

	public SimpleGroup() {
		this(0,0,0,0);
	}

	public SimpleGroup(int x, int y, int width, int height) {
		super();
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		
		var_x = new Variable(x,this);
		var_y = new Variable(y,this);
		var_width = new Variable(width,this);
		var_height = new Variable(height,this);
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

	public void setX(int x) {
		BoundaryRectangle old = getBoundingBox();
		this.x = x;
		var_x.setValue(x);
		var_x.setOOD(true);
		damage(old.union(getBoundingBox()));
	}

	public void setY(int y) {
		BoundaryRectangle old = getBoundingBox();
		this.y = y;
		var_y.setValue(y);
		var_y.setOOD(true);
		damage(old.union(getBoundingBox()));		
	}

	public void setWidth(int width) {
		BoundaryRectangle old = getBoundingBox();
		this.width = width;
		var_width.setValue(width);
		var_width.setOOD(true);
		damage(old.union(getBoundingBox()));
	}

	public void setHeight(int height) {
		BoundaryRectangle old = getBoundingBox();
		this.height = height;
		var_height.setValue(height);
		var_height.setOOD(true);
		damage(old.union(getBoundingBox()));
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

	@Override
	public void draw(Canvas graphics, Path clipShape) {
		Paint p = new Paint();
		graphics.clipPath(clipShape);
		p.setStyle(Style.FILL);
		p.setColor(Color.TRANSPARENT);
		if (group != null) {
			Point left_top = group.childToParent(new Point(x , y));
			Point right_bottom = group.childToParent(new Point(x+width, y+height));
			graphics.drawRect(left_top.x,left_top.y, right_bottom.x, right_bottom.y, p);
		}
		BoundaryRectangle brect = getBoundingBox();
		Path groupShape = new Path();
		groupShape.addRect(brect.x,brect.y,(brect.x+brect.width),(brect.y+brect.height),Path.Direction.CCW);
		for (GraphicalObject g:children) {
			g.draw(graphics,groupShape);
		}
	}
	
	@Override
	public BoundaryRectangle getBoundingBox() {
		if (group == null) {
			return new BoundaryRectangle(x,y,width,height);
		} else {
			Point left_top = group.childToParent(new Point(x , y));
			BoundaryRectangle child = new BoundaryRectangle(left_top.x, left_top.y, width, height);
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
		damage(old.union(getBoundingBox()));
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
		// TODO Auto-generated method stub
		return m;
	}

	@Override
	public void addChild(GraphicalObject child)
			throws AlreadyHasGroupRunTimeException {
		child.setGroup(this);
		children.add(child);
	}

	@Override
	public void removeChild(GraphicalObject child) {		
		child.setGroup(null);
		children.remove(child);
	}

	@Override
	public void resizeChild(GraphicalObject child) {
		group.damage(getBoundingBox());
	}

	@Override
	public void bringChildToFront(GraphicalObject child) {
		BoundaryRectangle old = getBoundingBox();
		children.remove(child);
		children.add(child);
		group.damage(old.union(getBoundingBox()));		
	}

	@Override
	public void resizeToChildren() {
		BoundaryRectangle old = getBoundingBox();
		BoundaryRectangle brect = children.get(0).getBoundingBox();
		for (GraphicalObject g:children) {
			brect = brect.union(g.getBoundingBox());			
		}
		this.width = brect.width + 1;
		this.height = brect.height + 1;
		group.damage(old.union(getBoundingBox()));
	}

	@Override
	public void damage(BoundaryRectangle rectangle) {
		if (group != null) {	
			group.damage(rectangle);
		}
	}

	@Override
	public List<GraphicalObject> getChildren() {
		return children;
	}

	@Override
	public Point parentToChild(Point pt) {
		int x = pt.x - this.x;
		int y = pt.y - this.y;
		return new Point(x,y);
	}

	@Override
	public Point childToParent(Point pt) {
		Point conv_pt = group.childToParent(new Point(this.x,this.y));
		int x = conv_pt.x + pt.x;
		int y = conv_pt.y + pt.y;
		return new Point(x,y);
	}
}
