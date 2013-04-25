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
import android.graphics.Paint.Style;

public class LayoutGroup implements Group {

	private int x;
	private int y;
	private int width;
	private int height;
	private int layout;
	private int offset;
	private ArrayList<GraphicalObject> children = new ArrayList<GraphicalObject>();
	private Group group;
	private Matrix m;
	
	public LayoutGroup() {
		this(0,0,0,0,1,0);
	}

	public LayoutGroup(int x, int y, int width, int height, int layout, int offset) {
		super();
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.layout = layout;
		this.offset = offset;
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

	public int getLayout() {
		return layout;
	}

	public int getOffset() {
		return offset;
	}

	public void setX(int x) {
		BoundaryRectangle old = getBoundingBox();
		this.x = x;
		damage(old.union(getBoundingBox()));
	}

	public void setY(int y) {
		BoundaryRectangle old = getBoundingBox();
		this.y = y;
		damage(old.union(getBoundingBox()));
	}

	public void setWidth(int width) {
		BoundaryRectangle old = getBoundingBox();
		this.width = width;
		damage(old.union(getBoundingBox()));
	}

	public void setHeight(int height) {
		BoundaryRectangle old = getBoundingBox();
		this.height = height;
		damage(old.union(getBoundingBox()));
	}

	public void setLayout(int layout) {
		BoundaryRectangle old = getBoundingBox();
		this.layout = layout;
		damage(old.union(getBoundingBox()));
	}

	public void setOffset(int offset) {
		BoundaryRectangle old = getBoundingBox();
		this.offset = offset;
		damage(old.union(getBoundingBox()));
	}

	@Override
	public void draw(Canvas graphics, Path clipShape) {
		Paint p = new Paint();
		graphics.clipPath(clipShape);
		p.setStyle(Style.FILL);
		p.setColor(Color.TRANSPARENT);
		if (group!= null) {
			Point left_top = group.childToParent(new Point(x , y));
			Point right_bottom = group.childToParent(new Point(x+width, y+height));
			graphics.drawRect(left_top.x,left_top.y, right_bottom.x, right_bottom.y, p);
		}
		BoundaryRectangle brect = getBoundingBox();
		Path groupShape = new Path();
		groupShape.addRect(brect.x,brect.y,(brect.x+brect.width),(brect.y+brect.height),Path.Direction.CCW);
		int x1 = 0;
		int y1 = 0;
		for (GraphicalObject g:children) {
			g.moveTo(x1, y1);
			g.draw(graphics,groupShape);
			if (layout == 1) {
				x1 = x1 + (int)g.getBoundingBox().getWidth() + offset;
			} else if (layout == 2) {
				y1 = y1 + (int)g.getBoundingBox().getHeight() + offset;
			}
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
		this.y = y;
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
		damage(old.union(getBoundingBox()));	
	}

	@Override
	public void resizeToChildren() {
		BoundaryRectangle old = getBoundingBox();
		BoundaryRectangle brect = children.get(0).getBoundingBox();
		for (GraphicalObject g:children) {
			brect = brect.union(g.getBoundingBox());			
		}
		this.width =  brect.width + 1;
		this.height = brect.height + 1;
		damage(old.union(getBoundingBox()));
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
		int x = this.x + pt.x;
		int y = this.y + pt.y;
		return new Point(x,y);
	}

}
