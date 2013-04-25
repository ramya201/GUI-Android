package homework3.android;

import android.graphics.Point;
import android.os.Parcel;
import android.os.Parcelable;
import homework2.android.*;
import homework2.android.objects.Line;
import homework2.android.objects.OutlineRect;

public class MoveBehavior implements Behavior {
	private Group group;
	private int state;
	private BehaviorEvent startEvent;
	private BehaviorEvent stopEvent;
	private Point originalPosition;
	private Point eventPosition;
	private GraphicalObject activeChild;

	public MoveBehavior() {
		state = Behavior.IDLE;
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
		startEvent = mask;		
	}

	@Override
	public BehaviorEvent getStopEvent() {
		return stopEvent;
	}

	@Override
	public void setStopEvent(BehaviorEvent mask) {
		stopEvent = mask;		
	}

	@Override
	public void start(BehaviorEvent event) {
		if (group.contains(event.getX(), event.getY())) {			
			eventPosition = group.parentToChild(new Point(event.getX(),event.getY()));

			for (GraphicalObject g:group.getChildren()) {
				if (g.getBoundingBox().contains(event.getX(), event.getY())) {
					activeChild = g;
					state = Behavior.RUNNING_INSIDE;
				} 
			} 
			originalPosition = group.parentToChild(new Point(activeChild.getBoundingBox().x, activeChild.getBoundingBox().y));			
		} 
	}

	@Override
	public void running(BehaviorEvent event) {
		Point newPosition = group.parentToChild(new Point(event.getX(),event.getY()));
		//BoundaryRectangle childUnclipped = new BoundaryRectangle(originalPosition.x,originalPosition.y,((SelectionHandles)activeChild).getWidth(),((SelectionHandles)activeChild).getHeight()); 
		
		if (group.contains(event.getX(), event.getY())) {
			if (activeChild!=null) {
				state = Behavior.RUNNING_INSIDE;
				activeChild.moveTo(originalPosition.x + (newPosition.x - eventPosition.x), originalPosition.y + (newPosition.y - eventPosition.y));		
			}
		} else {
			state = Behavior.RUNNING_OUTSIDE;
		}
	}

	@Override
	public void stop(BehaviorEvent event) {
		state = Behavior.IDLE;		
	}

	@Override
	public void cancel(BehaviorEvent event) {
		activeChild.moveTo(originalPosition.x, originalPosition.y);
		state = Behavior.IDLE;		
	}
}
