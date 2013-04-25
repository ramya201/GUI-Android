package homework2.android.objects;

import homework2.android.*;
import homework3.android.Selectable;
import homework4.android.Variable;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.Region;
import android.graphics.Typeface;

public class Text implements GraphicalObject {
	private String text;
	private int x;
	private int y;
	private Typeface font;
	private int fontSize;
	private int color;
	
	private Variable var_x;
	private Variable var_y;
	private Variable var_fontSize;
	private Variable var_color;
	
	private Group group;
	private Matrix m;
	
	public Text() {
		this(null,0,0,null,0,0);
	}

	public Text(String text, int x, int y, Typeface font, int fontSize,
			int color) {
		super();
		this.text = text;
		this.x = x;
		this.y = y;
		this.font = font;
		this.fontSize = fontSize;
		this.color = color;
		
		var_x = new Variable(x,this);
		var_y = new Variable(y,this);
		var_fontSize = new Variable(fontSize, this);
		var_color = new Variable(color, this);		
	}

	public String getText() {
		return text;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public Typeface getFont() {
		return font;
	}

	public int getFontSize() {
		return fontSize;
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

	public Variable getVar_fontSize() {
		return var_fontSize;
	}

	public Variable getVar_color() {
		return var_color;
	}

	public void setText(String text) {
		BoundaryRectangle old = getBoundingBox();
		this.text = text;
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

	public void setFont(Typeface font) {
		BoundaryRectangle old = getBoundingBox();
		this.font = font;
		group.resizeChild(this);
		group.damage(old.union(getBoundingBox()));
	}

	public void setFontSize(int fontSize) {
		BoundaryRectangle old = getBoundingBox();
		this.fontSize = fontSize;
		var_fontSize.setValue(fontSize);
		var_fontSize.setOOD(true);
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
		p.setColor(color);
		p.setTypeface(font);
		p.setTextSize(fontSize);
		if (group != null) {
			graphics.clipPath(clipShape,Region.Op.REPLACE);
			Point origin = group.childToParent(new Point(x,y));			
			graphics.drawText(text, origin.x, origin.y, p);			
		}		
	}

	@Override
	public BoundaryRectangle getBoundingBox() {
		Rect r = new Rect();
		Paint p = new Paint();
		p.setTypeface(font);
		p.setTextSize(fontSize);
		p.getTextBounds(text, 0, text.length(), r);
		Point origin = group.childToParent(new Point(x,y));	
		BoundaryRectangle child = new BoundaryRectangle(origin.x + r.left, origin.y + r.top, r.width(),r.height());
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
