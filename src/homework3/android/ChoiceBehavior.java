package homework3.android;

import homework2.android.*;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Color;
import android.graphics.Point;

public class ChoiceBehavior implements Behavior {
	private Group group;
	private int state;
	private BehaviorEvent startEvent;
	private BehaviorEvent stopEvent;
	private int type;
	private boolean firstOnly;
	private ArrayList<GraphicalObject> selection = new ArrayList<GraphicalObject>();
	private Point eventPosition;

	public static final int SINGLE = 0;
	public static final int TOGGLE = 1;
	public static final int MULTIPLE = 2;

	public ChoiceBehavior() {
		this(0,false);
	}

	public ChoiceBehavior(int type, boolean firstOnly) {
		state = Behavior.IDLE;
		this.type = type;
		this.firstOnly = firstOnly;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public boolean isFirstOnly() {
		return firstOnly;
	}

	public void setFirstOnly(boolean firstOnly) {
		this.firstOnly = firstOnly;
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

	public List<GraphicalObject> getSelection () {
		return selection;
	}

	@Override
	public void start(BehaviorEvent event) {
		if (group.contains(event.getX(), event.getY())) {			
			eventPosition = group.parentToChild(new Point(event.getX(),event.getY()));

			for (GraphicalObject g:group.getChildren()) {
				if (g.getBoundingBox().contains(event.getX(), event.getY()) && (g instanceof Selectable)) {
					state = Behavior.RUNNING_INSIDE;				

					if ((type == TOGGLE || type == MULTIPLE) && ((Selectable)g).isSelected() == true) {
						((Selectable)g).setInterimSelected(false);
						((Selectable)g).setSelected(false);
					} else if (type == SINGLE || ((type == TOGGLE || type == MULTIPLE) && ((Selectable)g).isSelected() == false)) {
						((Selectable)g).setInterimSelected(true);
					}
				} else if (!g.getBoundingBox().contains(event.getX(), event.getY()) && (g instanceof Selectable) && (type != MULTIPLE) ){
					((Selectable)g).setSelected(false);
				}
			}
		}				
	}

	@Override
	public void running(BehaviorEvent event) {
		eventPosition = group.parentToChild(new Point(event.getX(),event.getY()));
		if (!firstOnly) {
			for (GraphicalObject g:group.getChildren()) {
				if (g.getBoundingBox().contains(event.getX(), event.getY()) && (g instanceof Selectable)) {
					if (type == SINGLE || type == MULTIPLE) {
						((Selectable)g).setInterimSelected(true);
					} else if (type == TOGGLE) {					
						((Selectable)g).setInterimSelected(!((Selectable)g).isInterimSelected());
					} 
				} else if (g.getBoundingBox().contains(event.getX(), event.getY()) == false && (g instanceof Selectable)) {
					if (type == SINGLE || type == TOGGLE) {
						((Selectable)g).setInterimSelected(false);
					}
				}
			} 
		}
	}

	@Override
	public void stop(BehaviorEvent event) {

		for (GraphicalObject g:group.getChildren()) {
			if ((g instanceof Selectable)) { 
				if (((Selectable)g).isInterimSelected() == true) {
					((Selectable)g).setInterimSelected(false);
					((Selectable)g).setSelected(true);
				} 
			} 		
		} 		
		state = Behavior.IDLE;
	}

	@Override
	public void cancel(BehaviorEvent event) {
		for (GraphicalObject g:group.getChildren()) {
			if (g instanceof Selectable) {
				if (((Selectable)g).isInterimSelected() == true) {
					((Selectable)g).setInterimSelected(false);
				} 
			}
		}	
		state = Behavior.IDLE;
	}
}
