package homework3.android;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.Region;
import homework2.android.AlreadyHasGroupRunTimeException;
import homework2.android.BoundaryRectangle;
import homework2.android.GraphicalObject;
import homework2.android.Group;

public class SelectionHandles implements Group, Selectable {
	protected int x;
	protected int y;
	protected int width;
	protected int height;
	protected Group group;
	protected int color;
	protected Matrix m;
	protected ArrayList<GraphicalObject> children = new ArrayList<GraphicalObject>();
	protected boolean interimSelected;
	protected boolean selected;
	
	
		
	public SelectionHandles() {
		this(0,0,0,0,0);
	}

	public SelectionHandles( int x, int y, int width, int height, int color) {
		super();
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.color = color;
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

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public void setColor(int color) {
		this.color = color;
	}

	@Override
	public void draw(Canvas graphics, Path clipShape) {
		Paint p = new Paint();
		p.setStyle(Paint.Style.FILL);
		graphics.clipPath(clipShape);
		p.setColor(Color.TRANSPARENT);

		if (group != null) {
			Point left_top = group.childToParent(new Point(x , y));
			Point right_bottom = group.childToParent(new Point(x+width, y+height));
			graphics.drawRect(left_top.x,left_top.y, right_bottom.x, right_bottom.y, p);
			
			if (interimSelected || selected) {	
				p.setColor(color);
				Point r1_start = group.childToParent(new Point(x,y));
				Point r1_end = group.childToParent(new Point(((x+10)), (y+10)));			
				graphics.drawRect(r1_start.x, r1_start.y, r1_end.x, r1_end.y, p);
				
				Point r2_start = group.childToParent(new Point((x+width-11),y));
				Point r2_end = group.childToParent(new Point((x+width-1),(y+10)));			
				graphics.drawRect(r2_start.x, r2_start.y, r2_end.x, r2_end.y, p);

				Point r3_start = group.childToParent(new Point(x,(y+height-11)));
				Point r3_end = group.childToParent(new Point((x+10),(y+height-1)));			
				graphics.drawRect(r3_start.x, r3_start.y, r3_end.x, r3_end.y, p);

				Point r4_start = group.childToParent(new Point((x+width-11),(y+height-11)));
				Point r4_end = group.childToParent(new Point(x+width-1,y+height-1));			
				graphics.drawRect(r4_start.x, r4_start.y, r4_end.x, r4_end.y, p);
			}
		}
							
		for (GraphicalObject g:children) {
			g.draw(graphics,clipShape);
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
	public void setInterimSelected(boolean interimSelected) {
		this.interimSelected = interimSelected;
		group.damage(getBoundingBox());
	}

	@Override
	public boolean isInterimSelected() {
		return interimSelected;
	}

	@Override
	public void setSelected(boolean selected) {
		this.selected = selected;
		group.damage(getBoundingBox());
	}

	@Override
	public boolean isSelected() {
		return selected;
	}

	@Override
	public void moveTo(int x, int y) {
		BoundaryRectangle old = getBoundingBox();
		this.x = x;
		this.y = y;
		children.get(0).moveTo(x, y);
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
		int tempX = children.get(0).getBoundingBox().x;
		int tempY = children.get(0).getBoundingBox().y;
		int tempWidth = children.get(0).getBoundingBox().width;
		int tempHeight = children.get(0).getBoundingBox().height;
		this.x = tempX;
		this.y = tempY;
		this.width = tempWidth;
		this.height = tempHeight;
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
		return group.parentToChild(pt);
	}

	@Override
	public Point childToParent(Point pt) {
		return group.childToParent(pt);
	}
}
