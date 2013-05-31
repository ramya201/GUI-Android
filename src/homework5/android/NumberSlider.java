package homework5.android;

import java.io.IOException;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import homework2.android.containers.LayoutGroup;
import homework2.android.containers.SimpleGroup;
import homework2.android.objects.FilledRect;
import homework2.android.objects.Icon;
import homework2.android.objects.Line;
import homework2.android.objects.OutlineRect;
import homework2.android.objects.Text;
import homework3.android.ChoiceBehavior;
import homework4.android.ConstraintSolver;
import homework4.android.EdgeX1Constraint;
import homework4.android.EdgeY1Constraint;
import homework4.android.RightConstraint;
import homework4.android.Variable;

public class NumberSlider extends SimpleGroup {

	private Line line;
	private FilledRect slider;
	private Text currValue;
	private Text minValue;
	private Text maxValue;
	private Button incrementButton;
	private Button decrementButton;
	private int min;
	private int max;
	private int increment;
	private Panel panel;
	private Context context;
	
	public NumberSlider(int min, int max, int increment, Panel panel, Context context) {
		super(0,0,panel.getWidth(),panel.getHeight());
		
		this.min = min;
		this.max = max;
		this.increment = increment;
		this.panel = panel;
		this.context = context;
		
		line = new Line (26,(panel.getHeight()/2), (panel.getWidth() - 26), (panel.getHeight()/2), Color.GREEN,5);
		slider = new FilledRect(26,((panel.getHeight()/2)-15),26,30, Color.DKGRAY);
		minValue = new Text(String.valueOf(min), 26, (panel.getHeight()/2)+40,Typeface.create(Typeface.SANS_SERIF, Typeface.BOLD),12,Color.BLACK);
		maxValue = new Text(String.valueOf(max), (panel.getWidth() - 52), (panel.getHeight()/2)+40,Typeface.create(Typeface.SANS_SERIF, Typeface.BOLD),12,Color.BLACK);
		currValue = new Text(String.valueOf(min), panel.getWidth()/2 - 10, (panel.getHeight()/2) - 32,Typeface.create(Typeface.SANS_SERIF, Typeface.BOLD),14,Color.RED);
		
		Panel p1 = new Panel(0,((panel.getHeight()/2)-15), 26,30,LayoutGroup.VERTICAL, 0, false, ChoiceBehavior.SINGLE);
		Panel p2 = new Panel((panel.getWidth() - 26),((panel.getHeight()/2)-15), 26,30,LayoutGroup.VERTICAL, 0, false, ChoiceBehavior.SINGLE);
		this.panel.addChild(this);
		addChild(line);
		addChild(slider);
		addChild(minValue);
		addChild(maxValue);
		addChild(currValue);
		addChild(p1);
		addChild(p2);
		
		try {
			decrementButton = new Button(0,0,26,30, Color.LTGRAY, Color.BLACK,new Icon(loadImageFully("left.gif"), -5,-5),false,p1);		
			incrementButton = new Button(0,0,26,30, Color.LTGRAY, Color.BLACK,new Icon(loadImageFully("right.gif"), -5,-5),false,p2);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
/*		Variable v1 = line.getVar_x1();	
		v1.setEqn(new EdgeX1Constraint());	
		v1.addDep(line.getVar_x1());
		
		Variable v2 = line.getVar_y1();	
		v2.setEqn(new EdgeY1Constraint());	
		v2.addDep(line.getVar_y1());*/
		
	}
		
	public Bitmap loadImageFully(final String filename) throws IOException {
		Bitmap myBitmap = BitmapFactory.decodeStream(this.context.getAssets().open(filename));
		if (myBitmap != null) return myBitmap;
		throw new IOException("cannot load file: " + filename);
	}
	
	public void calculateValue() {
		
	}

}
