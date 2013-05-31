package homework5.android;

import android.content.Context;
import android.widget.Toast;
import homework2.android.GraphicalObject;
import homework2.android.containers.LayoutGroup;
import homework3.android.Selectable;

public class Panel extends LayoutGroup {

private boolean finalSelected;
private int selectionType;
private Context context;

public Panel(int x, int y, int width, int height, int layout, int offset, boolean finalSelected,int selectionType) {
	super(x,y,width,height,layout,offset);
	this.finalSelected = finalSelected;
	this.selectionType = selectionType;
}

public Panel getButtonPanel() {
	return this;
}

public boolean isFinalSelected() {
	return finalSelected;
}

public void setContext(Context context) {
	this.context = context;
}

public void onSelectionChanged() {
	for (GraphicalObject b:getChildren()) {
		if (((Selectable)b).isSelected()) {
			Toast.makeText(context, "Button " + ((Button)b).getLabel().getText() + " is currently selected!", Toast.LENGTH_SHORT).show();
		}
	}
}

}
