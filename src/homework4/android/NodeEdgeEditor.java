package homework4.android;


import java.util.HashMap;
import homework2.android.GraphicalObject;
import homework2.android.Group;
import homework2.android.objects.OutlineRect;
import homework3.android.Behavior;
import homework3.android.BehaviorEvent;
import homework3.android.EdgeBehavior;
import homework3.android.InteractiveWindowGroup;
import homework3.android.NewLineBehavior;
import android.graphics.Color;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;

public class NodeEdgeEditor extends InteractiveWindowGroup {
	protected HashMap<OutlineRect, OutlineRect> edges = new HashMap<OutlineRect,OutlineRect>();
	private OutlineRect start_node = null;
	private OutlineRect end_node = null;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		EdgeBehavior edge = new EdgeBehavior(false, Color.BLACK, 3);
		edge.setGroup(drawingGroup);
		edge.setStartEvent(new BehaviorEvent(BehaviorEvent.MOUSE_DOWN_ID, 0, BehaviorEvent.LEFT_MOUSE_KEY, 0, 0));
		edge.setStopEvent(new BehaviorEvent(BehaviorEvent.MOUSE_UP_ID, 0, BehaviorEvent.LEFT_MOUSE_KEY, 0, 0));
		
		addBehavior("EdgeBehavior",edge);
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {

		int action = event.getAction();
		BehaviorEvent e1 = null;

		int x = (int)event.getX();
		int y = (int)event.getY();

		switch(action) {
		case (MotionEvent.ACTION_DOWN) :
			e1 = new BehaviorEvent(BehaviorEvent.MOUSE_DOWN_ID, 0, BehaviorEvent.LEFT_MOUSE_KEY, x, y);
		break;
		case (MotionEvent.ACTION_MOVE) :
			e1 = new BehaviorEvent(BehaviorEvent.MOUSE_MOVE_ID, 0, BehaviorEvent.LEFT_MOUSE_KEY, x, y);	
		break;
		case (MotionEvent.ACTION_UP) :
			e1 = new BehaviorEvent(BehaviorEvent.MOUSE_UP_ID, 0, BehaviorEvent.LEFT_MOUSE_KEY, x, y);
		break;
		case (MotionEvent.ACTION_CANCEL) :
			e1 = new BehaviorEvent(BehaviorEvent.MOUSE_DOWN_ID, 0, BehaviorEvent.LEFT_MOUSE_KEY, x, y);
		break;
		case (MotionEvent.ACTION_OUTSIDE) :	                  
			e1 = new BehaviorEvent(BehaviorEvent.MOUSE_DOWN_ID, 0, BehaviorEvent.LEFT_MOUSE_KEY, x, y);
		break;
		} 

		
		for (Behavior b: behaviors.values()) {
			if (b.getClass().getName().equals(currSelection)) {
				if (b.getState() == Behavior.IDLE) {
					if (b.getStartEvent().matches(e1)) {
						
						if (currSelection.equals("homework3.android.EdgeBehavior")) {
							for (GraphicalObject node:drawingGroup.getChildren()) {
								if (((Group)node).getChildren().get(0) instanceof OutlineRect) {
									if (node.getBoundingBox().contains(x, y)) {
										b.start(e1);
										start_node = (OutlineRect) (((Group)node).getChildren().get(0));
										break;
									}
								}
							} 
						} else if (currSelection.equals("homework3.android.MoveBehavior")) {
							for (GraphicalObject node:drawingGroup.getChildren()) {
								if (node.getBoundingBox().contains(x, y) && ((Group)node).getChildren().get(0) instanceof OutlineRect) {
									b.start(e1);
								}
							}
						} else if (currSelection.equals("homework3.android.NewRectBehavior")) {
							b.start(e1);
						}
					}
				} else {
					if (b.getStopEvent().matches(e1) && b.getState() == Behavior.RUNNING_INSIDE) {
						if (currSelection.equals("homework3.android.EdgeBehavior")) {
							for (GraphicalObject node:drawingGroup.getChildren()) {
								if (((Group)node).getChildren().get(0) instanceof OutlineRect && node != start_node && edges.get(start_node) != ((Group)node).getChildren().get(0)) {
									if (node.getBoundingBox().contains(x, y)) {
										end_node = (OutlineRect) (((Group)node).getChildren().get(0));
										edges.put(start_node, end_node);
										((EdgeBehavior)b).setStart_node(start_node);
										((EdgeBehavior)b).setEnd_node(end_node);
										b.stop(e1);	
										break;
									}
								} else {
									//BehaviorEvent cancelEvent = new BehaviorEvent(BehaviorEvent.KEY_DOWN_ID,0,KeyEvent.KEYCODE_DEL,0,0);
									b.cancel(e1);
								}
							}
						} else 
							b.stop(e1);
					} else {
						b.running(e1);
					}
				} 
			}				    		
		}
		ConstraintSolver.evaluate();
		for (GraphicalObject g:children) {
			redraw(g);
		}
		return true;
	}
	
	public void newLine(View view) {     
		currSelection = "homework3.android.EdgeBehavior";
	}
}
