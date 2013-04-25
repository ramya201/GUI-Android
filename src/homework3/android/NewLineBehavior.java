package homework3.android;

import homework2.android.*;
import homework2.android.objects.Line;
import android.graphics.Color;
import android.graphics.Point;

public class NewLineBehavior extends NewBehavior {
	protected Group group;
	protected int state;
	protected BehaviorEvent startEvent;
	protected BehaviorEvent stopEvent;
	protected int color;
	protected int lineThickness;
	protected GraphicalObject newObject;
	protected Point eventPosition;
	protected SelectionHandlesLine selectGrp;

	public NewLineBehavior(boolean onePoint, int color, int lineThickness) {
		super(onePoint);
		state = Behavior.IDLE;
		this.color = color;
		this.lineThickness = lineThickness;
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
		newObject = make(event.getX(),event.getY(),event.getX(),event.getY());
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
		selectGrp = new SelectionHandlesLine(start.x, start.y, newObject.getBoundingBox().width, newObject.getBoundingBox().height, Color.RED);
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
		Line line = new Line(x1,y1,x2,y2,color,lineThickness);
		return line;
	}

	@Override
	public void resize(GraphicalObject gobj, int x1, int y1, int x2, int y2) {
		((Line) gobj).setX2(x2);		
		((Line) gobj).setY2(y2);
	}
}
