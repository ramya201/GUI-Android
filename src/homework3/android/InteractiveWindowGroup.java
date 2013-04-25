package homework3.android;


import homework2.android.GraphicalObject;
import homework2.android.R;
import homework2.android.containers.SimpleGroup;
import java.util.ArrayList;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ImageButton;

public class InteractiveWindowGroup extends WindowGroup implements OnTouchListener{
	protected ArrayList<Behavior> behaviors = new ArrayList<Behavior>();
	protected String currSelection = null;
	protected SimpleGroup drawingGroup;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		drawingGroup = new SimpleGroup(0,0,400,680);
		addChild(drawingGroup);

		MoveBehavior move = new MoveBehavior();
		move.setGroup(drawingGroup);
		move.setStartEvent(new BehaviorEvent(BehaviorEvent.MOUSE_DOWN_ID, 0, BehaviorEvent.LEFT_MOUSE_KEY, 0, 0));
		move.setStopEvent(new BehaviorEvent(BehaviorEvent.MOUSE_UP_ID, 0, BehaviorEvent.LEFT_MOUSE_KEY, 0, 0));

		NewRectBehavior rect = new NewRectBehavior(false, Color.BLUE, 3);
		rect.setGroup(drawingGroup);
		rect.setStartEvent(new BehaviorEvent(BehaviorEvent.MOUSE_DOWN_ID, 0, BehaviorEvent.LEFT_MOUSE_KEY, 0, 0));
		rect.setStopEvent(new BehaviorEvent(BehaviorEvent.MOUSE_UP_ID, 0, BehaviorEvent.LEFT_MOUSE_KEY, 0, 0));

		NewLineBehavior line = new NewLineBehavior(false, Color.GREEN, 3);
		line.setGroup(drawingGroup);
		line.setStartEvent(new BehaviorEvent(BehaviorEvent.MOUSE_DOWN_ID, 0, BehaviorEvent.LEFT_MOUSE_KEY, 0, 0));
		line.setStopEvent(new BehaviorEvent(BehaviorEvent.MOUSE_UP_ID, 0, BehaviorEvent.LEFT_MOUSE_KEY, 0, 0));

		ChoiceBehavior choice = new ChoiceBehavior(ChoiceBehavior.SINGLE, true);
		choice.setGroup(drawingGroup);
		choice.setStartEvent(new BehaviorEvent(BehaviorEvent.MOUSE_DOWN_ID, 0, BehaviorEvent.LEFT_MOUSE_KEY, 0, 0));
		choice.setStopEvent(new BehaviorEvent(BehaviorEvent.MOUSE_UP_ID, 0, BehaviorEvent.LEFT_MOUSE_KEY, 0, 0));

		addBehavior(move);
		addBehavior(rect);
		addBehavior(line);
		addBehavior(choice);

		drawView.setOnTouchListener(this);
		ImageButton btnNewRect = (ImageButton)findViewById(R.id.rectBtn);
		btnNewRect.setLongClickable(true);
		
		btnNewRect.setOnLongClickListener(new View.OnLongClickListener() {
			  public boolean onLongClick(View view) {
			    Intent i = new Intent(getBaseContext(),Parameters.class);
			    i.putExtra("CallingBtn","rectBtn");
			    startActivityForResult(i, 10);
			    return true;  
			  }
			});
		
		ImageButton btnLineRect = (ImageButton)findViewById(R.id.lineBtn);
		btnLineRect.setLongClickable(true);
		
		btnLineRect.setOnLongClickListener(new View.OnLongClickListener() {
			  public boolean onLongClick(View view) {
			    Intent i = new Intent(getBaseContext(),Parameters.class);
			    i.putExtra("CallingBtn","lineBtn");
			    startActivityForResult(i, 20);
			    return true;  
			  }
			});
		
		ImageButton btnSelect = (ImageButton)findViewById(R.id.selectBtn);
		btnSelect.setLongClickable(true);
		btnSelect.setOnLongClickListener(new View.OnLongClickListener() {
			  public boolean onLongClick(View view) {
			    Intent i = new Intent(getBaseContext(),ChoiceType.class);
			    startActivityForResult(i, 30);
			    return true;  
			  }
			});
	}

	public void addBehavior(Behavior inter) {
		behaviors.add(inter);
	}	
	public void removeBehavior(Behavior inter) {
		behaviors.remove(inter);
	}
	public void newRect(View view) {    
		currSelection = "homework3.android.NewRectBehavior"; 
	}
	public void newLine(View view) {     
		currSelection = "homework3.android.NewLineBehavior";
	}
	public void move(View view) {     
		currSelection = "homework3.android.MoveBehavior";
	}
	public void select(View view) {
		currSelection = "homework3.android.ChoiceBehavior";
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

		for (Behavior b: behaviors) {
			if (b.getClass().getName().equals(currSelection)) {
				if (b.getState() == Behavior.IDLE) {
					if (b.getStartEvent().matches(e1)) {
						b.start(e1);
					}
				} else {
					if (b.getStopEvent().matches(e1) && b.getState() == Behavior.RUNNING_INSIDE) {
						b.stop(e1);
					} else {
						b.running(e1);
					}
				} 

			}				    		
		}
		for (GraphicalObject g:children) {
			redraw(g);
		}
		return true;
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if ((keyCode == KeyEvent.KEYCODE_DEL)) {	    	
			BehaviorEvent cancelEvent = new BehaviorEvent(BehaviorEvent.KEY_DOWN_ID,0,KeyEvent.KEYCODE_DEL,0,0);
			for (Behavior b: behaviors) {
				if (b.getState() == Behavior.RUNNING_INSIDE) {
					b.cancel(cancelEvent);
					break;
				}
			}
		}
		return true;
	}
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (resultCode == 10) {
			String color = data.getStringExtra("Color");
			int lineThickness = data.getIntExtra("LineThickness", 1);
			
			for (Behavior b:behaviors) {
				if (b instanceof NewRectBehavior) {
					((NewRectBehavior) b).setColor(Color.parseColor(color));
					((NewRectBehavior) b).setLineThickness(lineThickness);
				}
			}
		}
		if (resultCode == 20) {
			String color = data.getStringExtra("Color");
			int lineThickness = data.getIntExtra("LineThickness", 1);
			
			for (Behavior b:behaviors) {
				if (b instanceof NewLineBehavior) {
					((NewLineBehavior) b).setColor(Color.parseColor(color));
					((NewLineBehavior) b).setLineThickness(lineThickness);
				}
			}
		}
		if (resultCode == 30) {
			String choiceType = data.getStringExtra("Choice");
			
			for (Behavior b:behaviors) {
				if (b instanceof ChoiceBehavior) {
					if (choiceType.equals("SINGLE")) {
					((ChoiceBehavior) b).setType(ChoiceBehavior.SINGLE);
					} else if (choiceType.equals("TOGGLE")) {
						((ChoiceBehavior) b).setType(ChoiceBehavior.TOGGLE);	
					} else if (choiceType.equals("MULTIPLE")) {
						((ChoiceBehavior) b).setType(ChoiceBehavior.MULTIPLE);	
					}
				}
			}
		}
	
	}
}
