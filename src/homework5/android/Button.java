package homework5.android;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.widget.Toast;
import homework2.android.GraphicalObject;
import homework2.android.containers.SimpleGroup;
import homework2.android.objects.FilledRect;
import homework2.android.objects.OutlineRect;
import homework2.android.objects.Text;
import homework3.android.Selectable;

public class Button extends SimpleGroup implements Selectable  {
	private OutlineRect outline;
	private FilledRect btnFace;
	private Text label;
	private GraphicalObject btnDisplay;
	private boolean finalSelected;
	private Panel btnPanel;
	private Context context;
	private boolean value;

	protected boolean interimSelected;
	protected boolean selected;

	public Button(int x, int y, int width, int height, int bckcolor,int forecolor, String text, boolean finalSelected,Panel btnPanel) {
		super(x,y,width,height);
		outline = new OutlineRect(0,0,width-2,height-2,Color.DKGRAY,3);
		btnFace = new FilledRect(0,0,width-4,height-4,bckcolor);
		label = new Text(text, width/3,height/2,Typeface.create(Typeface.SANS_SERIF, Typeface.NORMAL), 12, forecolor);
		this.finalSelected = finalSelected;
		this.btnPanel = btnPanel;

		this.btnPanel.addChild(this);
		addChild(outline);
		addChild(btnFace);
		addChild(label);
	}
	
	public Button(int x, int y, int width, int height, int bckcolor,int forecolor, GraphicalObject btnDisplay, boolean finalSelected,Panel btnPanel) {
		super(x,y,width,height);
		outline = new OutlineRect(0,0,width-2,height-2,Color.DKGRAY,2);
		btnFace = new FilledRect(0,0,width-4,height-4,bckcolor);
		this.btnDisplay = btnDisplay;
		this.finalSelected = finalSelected;
		this.btnPanel = btnPanel;

		this.btnPanel.addChild(this);
		addChild(outline);
		addChild(btnFace);
		addChild(this.btnDisplay);
	}
	
	public Text getLabel() {
		return label;
	}
	
	public GraphicalObject getBtnDisplay() {
		return btnDisplay;
	}
	
	public boolean isFinalSelected() {
		return finalSelected;
	}
	
	public Panel getBtnPanel() {
		return btnPanel;
	}
	public boolean getValue() {
		return value;
	}	
	public void setLabel(Text label) {
		this.label = label;
	}
	
	public void setBtnDisplay(GraphicalObject btnDisplay) {
		this.btnDisplay = btnDisplay;
	}
	
	public void setFinalSelected(boolean finalSelected) {
		this.finalSelected = finalSelected;
	}
	
	public void setBtnPanel(Panel btnPanel) {
		this.btnPanel = btnPanel;
	}
	
	public void setContext(Context context) {
		this.context = context;
	}
	
	@Override
	public void setInterimSelected(boolean interimSelected) {
		this.interimSelected = interimSelected;
		if (interimSelected) {
			outline.setColor(Color.DKGRAY);
			btnFace.setX(2);
			btnFace.setY(2);
		} else {
			outline.setColor(Color.DKGRAY);
			btnFace.setX(0);
			btnFace.setY(0);
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
			outline.setColor(Color.DKGRAY);
			btnFace.setX(2);
			btnFace.setY(2);
		} else {
			outline.setColor(Color.DKGRAY);
			btnFace.setX(0);
			btnFace.setY(0);
		}
		damage(getBoundingBox());
		onClick();
	}

	public void onClick() {
		if (selected) {
			Toast.makeText(context, "Button " + label.getText() + " was clicked!", Toast.LENGTH_SHORT).show();
			value = true;
		} 
	}
	@Override
	public boolean isSelected() {
		return selected;
	}

}
