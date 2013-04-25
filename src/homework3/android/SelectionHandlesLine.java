package homework3.android;

import homework2.android.GraphicalObject;
import homework2.android.objects.Line;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.Rect;

public class SelectionHandlesLine extends SelectionHandles {
	
	
	public SelectionHandlesLine() {
		super();
	}

	public SelectionHandlesLine(int x, int y, int width, int height, int color) {
		super(x, y, width, height, color);
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
				Rect[] feedbackRects = new Rect[4];
				
				Line l = (Line) children.get(0);
				
				Point r1_start = group.childToParent(new Point(x,y));
				Point r1_end = group.childToParent(new Point(((x+10)), (y+10)));
				feedbackRects[0] = new Rect(r1_start.x, r1_start.y, r1_end.x, r1_end.y);
				
				
				Point r2_start = group.childToParent(new Point((x+width-11),y));
				Point r2_end = group.childToParent(new Point((x+width-1),(y+10)));			
				feedbackRects[1] = new Rect(r2_start.x, r2_start.y, r2_end.x, r2_end.y);

				Point r3_start = group.childToParent(new Point(x,(y+height-11)));
				Point r3_end = group.childToParent(new Point((x+10),(y+height-1)));			
				feedbackRects[2] = new Rect(r3_start.x, r3_start.y, r3_end.x, r3_end.y);

				Point r4_start = group.childToParent(new Point((x+width-11),(y+height-11)));
				Point r4_end = group.childToParent(new Point(x+width-1,y+height-1));			
				feedbackRects[3] = new Rect(r4_start.x, r4_start.y, r4_end.x, r4_end.y);
				
				for (Rect r:feedbackRects) {
					if (r.contains(l.getX1(), l.getY1()) || r.contains(l.getX2(), l.getY2())) {
						graphics.drawRect(r, p);
					}
				}
			}
		}
							
		for (GraphicalObject g:children) {
			g.draw(graphics,clipShape);
		}
	}	

}
