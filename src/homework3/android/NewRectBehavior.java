package homework3.android;

import android.graphics.Color;
import android.graphics.Point;
import homework2.android.GraphicalObject;
import homework2.android.Group;
import homework2.android.objects.FilledRect;
import homework2.android.objects.OutlineRect;

public class NewRectBehavior extends NewBehavior {
	private Group group;
	private int state;
	private BehaviorEvent startEvent;
	private BehaviorEvent stopEvent;
	protected int color;
	protected int lineThickness;
	private GraphicalObject newObject;
	private Point eventPosition;
	private SelectionHandles selectGrp = null;
	private int type;

	public static final int  OUTLINE = 0;
	public static final int  FILLED = 1;

	public NewRectBehavior(boolean onePoint, int color, int lineThickness, int type) {
		super(onePoint);
		state = Behavior.IDLE;
		this.color = color;
		this.lineThickness = lineThickness;
		this.type = type;
	}

	public int getColor() {
		return color;
	}

	public int getLineThickness() {
		return lineThickness;
	}

	public void setColor(int color) {
		this.color = color;
	}

	public void setLineThickness(int lineThickness) {
		this.lineThickness = lineThickness;
	}

	@Override
	public Group getGroup() {
		return group;
	}

	@Override
	public void setGroup(Group group) {
		this.group = group;		
	}

	public GraphicalObject getNewObject() {
		return newObject;
	}
	
	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	@Override
	public int getState() {
		return state;
	}

	@Override
	public BehaviorEvent getStartEvent() {
		return startEvent;
	}

	@Override
	public void setStartEvent(BehaviorEvent mask) {
		this.startEvent = mask;

	}

	@Override
	public BehaviorEvent getStopEvent() {
		return stopEvent;
	}

	@Override
	public void setStopEvent(BehaviorEvent mask) {
		this.stopEvent = mask;
	}

	@Override
	public void start(BehaviorEvent event) {
		state = Behavior.RUNNING_INSIDE;
		eventPosition = group.parentToChild(new Point(event.getX(),event.getY()));
		newObject = make(eventPosition.x,eventPosition.y,eventPosition.x,eventPosition.y);
		this.group.addChild(newObject);		
	}

	@Override
	public void running(BehaviorEvent event) {
		Point newPosition = group.parentToChild(new Point(event.getX(),event.getY()));
		resize(newObject,eventPosition.x,eventPosition.y,newPosition.x,newPosition.y);		
	}

	@Override
	public void stop(BehaviorEvent event) {
		state = Behavior.IDLE;
		Point start = this.group.parentToChild(new Point(newObject.getBoundingBox().x,newObject.getBoundingBox().y));
		selectGrp = new SelectionHandles(start.x, start.y, newObject.getBoundingBox().width, newObject.getBoundingBox().height, Color.RED);
		this.group.removeChild(newObject);
		this.group.addChild(selectGrp);
		selectGrp.addChild(newObject);
	}

	@Override
	public void cancel(BehaviorEvent event) {
		this.group.removeChild(newObject);
		state = Behavior.IDLE;
	}

	@Override
	public GraphicalObject make(int x1, int y1, int x2, int y2) {
		GraphicalObject rect = null;
		if (type == NewRectBehavior.OUTLINE) {
			rect = new OutlineRect(x1,y1,x2-x1,y2-y1,color,lineThickness);
		} else if (type == NewRectBehavior.FILLED) {
			rect = new FilledRect(x1,y1,x2-x1,y2-y1,color);
			lineThickness = 0;
		}
		return rect;
	}

	@Override
	public void resize(GraphicalObject gobj, int x1, int y1, int x2, int y2) {
		if (type == NewRectBehavior.OUTLINE) {
			if (x2 > x1 && y2>y1) {
				((OutlineRect) gobj).setWidth(x2-x1+1);		
				((OutlineRect) gobj).setHeight(y2-y1+1);
			} else if (x2 < x1 && y2 > y1) {
				((OutlineRect) gobj).setX(x2);
				((OutlineRect) gobj).setWidth(x1-x2+1);
				((OutlineRect) gobj).setHeight(y2-y1+1);			
			} else if (x2>x1 && y2<y1) {
				((OutlineRect) gobj).setY(y2);
				((OutlineRect) gobj).setWidth(x2-x1+1);
				((OutlineRect) gobj).setHeight(y1-y2+1);
			} else if ( x2 < x1 && y2 < y1) {
				((OutlineRect) gobj).setX(x2);
				((OutlineRect) gobj).setY(y2);
				((OutlineRect) gobj).setWidth(x1-x2+1);
				((OutlineRect) gobj).setHeight(y1-y2+1);
			}
		} else if (type == NewRectBehavior.FILLED) {
			if (x2 > x1 && y2>y1) {
				((FilledRect) gobj).setWidth(x2-x1+1);		
				((FilledRect) gobj).setHeight(y2-y1+1);
			} else if (x2 < x1 && y2 > y1) {
				((FilledRect) gobj).setX(x2);
				((FilledRect) gobj).setWidth(x1-x2+1);
				((FilledRect) gobj).setHeight(y2-y1+1);			
			} else if (x2>x1 && y2<y1) {
				((FilledRect) gobj).setY(y2);
				((FilledRect) gobj).setWidth(x2-x1+1);
				((FilledRect) gobj).setHeight(y1-y2+1);
			} else if ( x2 < x1 && y2 < y1) {
				((FilledRect) gobj).setX(x2);
				((FilledRect) gobj).setY(y2);
				((FilledRect) gobj).setWidth(x1-x2+1);
				((FilledRect) gobj).setHeight(y1-y2+1);
			}
		}
	}
}
