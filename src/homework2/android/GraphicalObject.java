package homework2.android;

import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.Matrix;

public interface GraphicalObject {
	public void draw(Canvas graphics, Path clipShape);
	
	public BoundaryRectangle getBoundingBox();
	public void moveTo(int x, int y);
	public Group getGroup();
	public void setGroup(Group group);
	public boolean contains(int x, int y);
	
	public void setAffineTransform(Matrix af);
	public Matrix getAffineTransform();
}
