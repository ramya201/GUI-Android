package homework3.android;


import homework2.android.GraphicalObject;
import homework2.android.Group;
import homework2.android.R;
import homework2.android.containers.SimpleGroup;
import homework2.android.objects.FilledRect;
import homework2.android.objects.OutlineRect;
import homework4.android.ConstraintSolver;
import homework5.android.Button;
import homework5.android.CheckBox;
import homework5.android.NumberSlider;
import homework5.android.Panel;
import homework5.android.BtnParameters;
import homework5.android.PanelType;
import homework5.android.RadioButton;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ImageButton;

public class InteractiveWindowGroup extends WindowGroup implements OnTouchListener{
	protected HashMap<String,Behavior> behaviors = new HashMap<String,Behavior>();
	protected String currSelection = null;
	protected SimpleGroup drawingGroup;

	//Parameters for Button Panel
	protected int choice = ChoiceBehavior.SINGLE;
	protected int layout = Group.VERTICAL;
	protected int offset = 5;
	protected boolean finalSelection = true;
	protected String indicator = null;
	protected Panel btnPanel = null;
	
	//Parameters for Button
	private String btnText = "Button";
	private int btnWidth = 100;
	private int btnHeight = 40;
	private int btnBckcolor = Color.LTGRAY;
	private int btnForecolor = Color.BLACK;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		drawingGroup = new SimpleGroup(0,0,400,600);
		addChild(drawingGroup);

		MoveBehavior move = new MoveBehavior();
		move.setGroup(drawingGroup);
		move.setStartEvent(new BehaviorEvent(BehaviorEvent.MOUSE_DOWN_ID, 0, BehaviorEvent.LEFT_MOUSE_KEY, 0, 0));
		move.setStopEvent(new BehaviorEvent(BehaviorEvent.MOUSE_UP_ID, 0, BehaviorEvent.LEFT_MOUSE_KEY, 0, 0));

		NewRectBehavior rect = new NewRectBehavior(false, Color.BLACK, 1, NewRectBehavior.OUTLINE);
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

		addBehavior("MoveBehavior",move);
		addBehavior("NewRectBehavior",rect);
		addBehavior("NewLineBehavior",line);
		addBehavior("ChoiceBehavior",choice);

		drawView.setOnTouchListener(this);
		ImageButton btnNewRect = (ImageButton)findViewById(R.id.rectBtn);
		btnNewRect.setLongClickable(true);
		
		btnNewRect.setOnLongClickListener(new View.OnLongClickListener() {
			public boolean onLongClick(View view) {
				Intent i = new Intent(getBaseContext(),BtnParameters.class);
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
		ImageButton btnPanel = (ImageButton)findViewById(R.id.panelBtn);
		btnPanel.setLongClickable(true);
		btnPanel.setOnLongClickListener(new View.OnLongClickListener() {
			public boolean onLongClick(View view) {
				Intent i = new Intent(getBaseContext(),PanelType.class);
				startActivityForResult(i, 40);
				return true;  
			}
		});
	}

	public void addBehavior(String key,Behavior inter) {
		behaviors.put(key,inter);
	}	
	public void removeBehavior(String key) {
		behaviors.remove(key);
	}
	public void newRect(View view) { 
		if (btnPanel != null) {
			Button btn = new Button(0,0,btnWidth,btnHeight,btnBckcolor,btnForecolor,btnText,btnPanel.isFinalSelected(),btnPanel);
			btn.setContext(getApplicationContext());
			redraw(btnPanel);
			
			ChoiceBehavior selection = (ChoiceBehavior) behaviors.get("ChoiceBehavior");
			selection.setGroup(btnPanel);
			selection.setType(choice);
			selection.setFirstOnly(false);
		} else {
			currSelection = null;
			println("Please add a Panel first");
		}
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
	public void drawPanel(View view) {
		currSelection = "homework3.android.NewRectBehavior";
		indicator = "ButtonPanel";
		
		NewRectBehavior rect = (NewRectBehavior) behaviors.get("NewRectBehavior");
		rect.setGroup(drawingGroup);
		rect.setColor(Color.DKGRAY);
		rect.setType(NewRectBehavior.OUTLINE);
	}
	
	public void addCheckBox(View view) {
		if (btnPanel != null) {
			CheckBox checkBox = new CheckBox(0,0,100,35,"CheckBox",btnPanel, getApplicationContext());
			redraw(btnPanel);
			
			ChoiceBehavior selection = (ChoiceBehavior) behaviors.get("ChoiceBehavior");
			selection.setGroup(btnPanel);
			selection.setType(ChoiceBehavior.MULTIPLE);
			selection.setFirstOnly(true);
		} else {
			currSelection = null;
			println("Please add a Panel first");
		}
	}
	
	public void addRadioBox(View view) {
		if (btnPanel != null) {
			RadioButton radioBtn = new RadioButton(0,0,100,35,"RadioButton",btnPanel, getApplicationContext());
			redraw(btnPanel);
			
			ChoiceBehavior selection = (ChoiceBehavior) behaviors.get("ChoiceBehavior");
			selection.setGroup(btnPanel);
			selection.setType(ChoiceBehavior.SINGLE);
			selection.setFirstOnly(true);
		} else {
			currSelection = null;
			println("Please add a Panel first");
		}
	}
	
	public void addSlider(View view) {
		if (btnPanel != null) {
			NumberSlider slider = new NumberSlider(0, 100, 5, btnPanel, getApplicationContext());
			redraw(btnPanel);
			
			ChoiceBehavior selection = (ChoiceBehavior) behaviors.get("ChoiceBehavior");
			selection.setGroup(slider);
			selection.setType(ChoiceBehavior.SINGLE);
			selection.setFirstOnly(true);
			
			MoveBehavior move = (MoveBehavior) behaviors.get("MoveBehavior");
			move.setGroup(slider);
		} else {
			currSelection = null;
			println("Please add a Panel first");
		}
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
		if (indicator == "ButtonPanel" && currSelection == "homework3.android.NewRectBehavior") {
			NewRectBehavior drawPanel = (NewRectBehavior) behaviors.get("NewRectBehavior");

			OutlineRect panelOutline = (OutlineRect) drawPanel.getNewObject();
			Panel btnPanel = new Panel(panelOutline.getX()+1,panelOutline.getY()+1,panelOutline.getWidth()-2,panelOutline.getHeight()-2,layout,offset,finalSelection,choice);
			drawingGroup.addChild(btnPanel);
			
			this.btnPanel = btnPanel;
			
			ChoiceBehavior selection = (ChoiceBehavior) behaviors.get("ChoiceBehavior");
			selection.setGroup(btnPanel);
		} 
		//ConstraintSolver.evaluate();
		
		for (GraphicalObject g:children) {
			redraw(g);
		}
		return true;
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if ((keyCode == KeyEvent.KEYCODE_DEL)) {	    	
			BehaviorEvent cancelEvent = new BehaviorEvent(BehaviorEvent.KEY_DOWN_ID,0,KeyEvent.KEYCODE_DEL,0,0);
			for (Behavior b: behaviors.values()) {
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
			btnText  = data.getStringExtra("Text");
			btnWidth = Integer.parseInt(data.getStringExtra("Width"));
			btnHeight = Integer.parseInt(data.getStringExtra("Height"));
			btnForecolor = Color.parseColor(data.getStringExtra("ForeColor"));
		}
		if (resultCode == 20) {
			String color = data.getStringExtra("Color");
			int lineThickness = data.getIntExtra("LineThickness", 1);

			NewLineBehavior b = (NewLineBehavior) behaviors.get("NewLineBehavior");

			b.setColor(Color.parseColor(color));
			b.setLineThickness(lineThickness);

		}
		if (resultCode == 30) {
			String choiceType = data.getStringExtra("Choice");

			ChoiceBehavior b = (ChoiceBehavior) behaviors.get("ChoiceBehavior");

			if (choiceType.equals("SINGLE")) {
				b.setType(ChoiceBehavior.SINGLE);
			} else if (choiceType.equals("TOGGLE")) {
				b.setType(ChoiceBehavior.TOGGLE);	
			} else if (choiceType.equals("MULTIPLE")) {
				b.setType(ChoiceBehavior.MULTIPLE);	
			}
		}
		if (resultCode == 40) {
			String choiceType = data.getStringExtra("Choice");

			if (choiceType.equals("SINGLE")) {
				choice = (ChoiceBehavior.SINGLE);
			} else if (choiceType.equals("TOGGLE")) {
				choice = (ChoiceBehavior.TOGGLE);	
			} else if (choiceType.equals("MULTIPLE")) {
				choice = (ChoiceBehavior.MULTIPLE);	
			}

			offset = data.getIntExtra("Offset", 0);

			String selection = data.getStringExtra("Selection");

			if (selection.equals("YES")) {
				finalSelection = true;
			} else if (selection.equals("NO")) {
				finalSelection = false;
			}

			String layout = data.getStringExtra("Layout");

			if (layout.equals("HORIZONTAL")) {
				this.layout = Group.HORIZONTAL;
			} else if (layout.equals("VERTICAL")) {
				this.layout = Group.VERTICAL;	
			} 						
		}
	}
}
