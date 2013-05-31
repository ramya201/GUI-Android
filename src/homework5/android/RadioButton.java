package homework5.android;

import java.io.IOException;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.widget.Toast;
import homework2.android.GraphicalObject;
import homework2.android.containers.SimpleGroup;
import homework2.android.objects.FilledRect;
import homework2.android.objects.Icon;
import homework2.android.objects.OutlineRect;
import homework2.android.objects.Text;
import homework3.android.Selectable;

public class RadioButton extends SimpleGroup implements Selectable {
	private OutlineRect outer;
	private OutlineRect inner;
	private Text label;
	private GraphicalObject display;
	private Panel panel;
	private Context context;
	private Icon check;
	private boolean finalSelected = true;
	private boolean value;

	protected boolean interimSelected;
	protected boolean selected;

	public RadioButton(int x, int y, int width, int height, String text, Panel panel, Context context) {
		super(x,y,width,height);
		outer = new OutlineRect(3,3,20,23,Color.DKGRAY,2);
		inner = new OutlineRect(5,5,18,20,Color.LTGRAY,2);
		label = new Text(text, 35 ,20,Typeface.create(Typeface.SANS_SERIF, Typeface.NORMAL), 12, Color.BLACK);
		this.panel = panel;
		this.context = context;

		this.panel.addChild(this);
		addChild(outer);
		addChild(inner);
		addChild(label);
		
		try {
			check = new Icon(loadImageFully("dot.png"), 6, 6);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public RadioButton(int x, int y, int width, int height, GraphicalObject display,Panel panel, Context context) {
		super(x,y,width,height);
		outer = new OutlineRect(3,3,25,28,Color.DKGRAY,2);
		inner = new OutlineRect(5,5,23,25,Color.LTGRAY,2);
		this.display = display;
		this.panel = panel;
		this.context = context;

		this.panel.addChild(this);
		this.panel.addChild(this);
		addChild(outer);
		addChild(inner);
		addChild(display);
		
		try {
			check = new Icon(loadImageFully("check.gif"), 6, 6);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public Text getLabel() {
		return label;
	}
	
	public GraphicalObject getBtnDisplay() {
		return display;
	}
		
	public Panel getBtnPanel() {
		return panel;
	}
	
	public boolean getValue() {
		return value;
	}
	
	public void setLabel(Text label) {
		this.label = label;
	}
	
	public void display(GraphicalObject display) {
		this.display = display;
	}
	
	public void setPanel(Panel panel) {
		this.panel = panel;
	}
	
	public void setContext(Context context) {
		this.context = context;
	}
	
	@Override
	public void setInterimSelected(boolean interimSelected) {
		this.interimSelected = interimSelected;
		if (interimSelected) {
			this.addChild(check);
		} else {
			this.removeChild(check);
		}
		damage(getBoundingBox());
	}

	@Override
	public boolean isInterimSelected() {
		return interimSelected;
	}

	@Override
	public void setSelected(boolean selected) {
		this.selected = selected;
		if (selected && finalSelected) {
			if (check.getGroup() == null) {
				this.addChild(check);
			}
		} else {
			if (this.getChildren().contains(check)) {
				this.removeChild(check);
			}
		}
		damage(getBoundingBox());
		onClick();
	}

	public void onClick() {
		if (selected) {
			Toast.makeText(context, "RadioButton " + label.getText() + " changed check!", Toast.LENGTH_SHORT).show();
			value = true;
		} 
	}
	@Override
	public boolean isSelected() {
		return selected;
	}
	public Bitmap loadImageFully(final String filename) throws IOException {
		Bitmap myBitmap = BitmapFactory.decodeStream(context.getAssets().open(filename));
		if (myBitmap != null) return myBitmap;
		throw new IOException("cannot load file: " + filename);
	}

}
