package homework2.android.containers;

import homework2.android.*;

import java.util.ArrayList;
import java.util.List;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;

public class ScaledGroup implements Group {

	private int x;
	private int y;
	private int width;
	private int height;
	private double scaleX;
	private double scaleY;
	private ArrayList<GraphicalObject> children = new ArrayList<GraphicalObject>();
	private Group group;
	
	public ScaledGroup() {
		this(0,0,0,0,0.0,0.0);
	}

	public ScaledGroup(int x, int y, int width, int height, double scaleX, double scaleY) {
		super();
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.scaleX = scaleX;
		this.scaleY = scaleY;
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
		this.x = x;
		damage(getBoundingBox());
	}

	public void setY(int y) {
		this.y = y;
		damage(getBoundingBox());
	}

	public void setWidth(int width) {
		this.width = width;
		damage(getBoundingBox());
	}

	public void setHeight(int height) {
		this.height = height;
		damage(getBoundingBox());
	}
	
	public void setScaleX(double scaleX) {
		this.scaleX = scaleX;
		damage(getBoundingBox());
	}

	public void setScaleY(double scaleY) {
		this.scaleY = scaleY;
		damage(getBoundingBox());
	}

	public double getScaleX() {
		return scaleX;
	}

	public double getScaleY() {
		return scaleY;
	}

	@Override
	public void draw(Canvas graphics, Path clipShape) {
		Paint p = new Paint();
		graphics.clipPath(clipShape);
		p.setColor(Color.TRANSPARENT);
		if (group != null) {
			Point left_top = group.childToParent(new Point(x , y));
			Point right_bottom = group.childToParent(new Point(x+width-1, y+height-1));
			graphics.drawRect(left_top.x,left_top.y, right_bottom.x, right_bottom.y, p);
		}
		BoundaryRectangle brect = getBoundingBox();
		Path groupShape = new Path();
		groupShape.addRect(brect.x,brect.y,(brect.x+brect.width),(brect.y+brect.height),Path.Direction.CCW);
		
		//Setting matrix transformation for scaling
		Matrix m = new Matrix();
		m.setScale((float) scaleX, (float) scaleY);
	   	
		graphics.setMatrix(m);

		for (GraphicalObject g:children) {
			g.draw(graphics,groupShape);
		}
		m.reset();
		graphics.setMatrix(m);
	}
	
	@Override
	public BoundaryRectangle getBoundingBox() {
		if (group == null) {
			return new BoundaryRectangle(x,y,width,height);
		} else {
			Point left_top = group.childToParent(new Point(x , y));
			return new BoundaryRectangle(left_top.x, left_top.y, width, height);
		}
	}

	@Override
	public void moveTo(int x, int y) {
		this.x = x;
		this.y = y;
		damage(getBoundingBox());
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
		// TODO Auto-generated method stub		
	}

	@Override
	public Matrix getAffineTransform() {
		// TODO Auto-generated method stub
		return null;
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
		damage(child.getBoundingBox());		
	}

	@Override
	public void bringChildToFront(GraphicalObject child) {
		children.remove(child);
		children.add(child);		
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
