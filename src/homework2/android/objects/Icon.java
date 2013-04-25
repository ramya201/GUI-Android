package homework2.android.objects;

import homework2.android.*;
import homework3.android.Selectable;
import homework4.android.Variable;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.Region;

public class Icon implements GraphicalObject {
	private Bitmap image;
	private int x;
	private int y;
	private Group group;
	private Matrix m;
	
	private Variable var_x;
	private Variable var_y;
		
	public Icon() {
		this(null,0,0);
	}

	public Icon(Bitmap image, int x, int y) {
		this.image = image;
		this.x = x;
		this.y = y;
		
		var_x = new Variable(x,this);
		var_y = new Variable(y,this);
	}

	public Bitmap getImage() {
		return image;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public void setImage(Bitmap image) {
		BoundaryRectangle old = getBoundingBox();
		this.image = image;
		group.resizeChild(this);
		group.damage(old.union(getBoundingBox()));
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

	@Override
	public void draw(Canvas graphics, Path clipShape) {
		Paint p = new Paint();		
		if (group != null) {
			graphics.clipPath(clipShape,Region.Op.REPLACE);
			Point left_top = group.childToParent(new Point(x,y));			
			graphics.drawBitmap(image, left_top.x, left_top.y, p);			
		}				
	}

	@Override
	public BoundaryRectangle getBoundingBox() {
		Point left_top = group.childToParent(new Point(x,y));
		BoundaryRectangle child = new BoundaryRectangle(left_top.x,left_top.y,image.getWidth(),image.getHeight());
		return child.intersection(group.getBoundingBox());
	}

	@Override
	public void moveTo(int x, int y) {
		BoundaryRectangle old = getBoundingBox();
		this.x = x;
		this.y = y;
		var_x.setValue(x);
		var_x.setOOD(true);
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
	}

	@Override
	public Matrix getAffineTransform() {
		return m;
	}

}
