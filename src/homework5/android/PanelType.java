package homework5.android;

import homework2.android.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.NumberPicker;
import android.widget.Spinner;

public class PanelType extends Activity {
	private Spinner spinner_layout;
	private Spinner spinner_selection;
	private Spinner spinner_choice;
	private NumberPicker picker;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.btnpanel_options);
		
		spinner_layout = (Spinner) findViewById(R.id.layout);
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
		        R.array.layout_array, android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner_layout.setAdapter(adapter);
		
		spinner_selection = (Spinner) findViewById(R.id.Spinner01);
		ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this,
		        R.array.selection_array, android.R.layout.simple_spinner_item);
		adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner_selection.setAdapter(adapter1);
		
		spinner_choice = (Spinner) findViewById(R.id.Spinner02);
		ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,
		        R.array.choice_array, android.R.layout.simple_spinner_item);
		adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner_choice.setAdapter(adapter2);
		
		picker = (NumberPicker) findViewById(R.id.offsetPicker);
		picker.setMaxValue(20);
		picker.setMinValue(0);
	}

	public void submitChoice(View view) {	
		Intent i = new Intent();
		i.putExtra("Layout",spinner_layout.getSelectedItem().toString());
		i.putExtra("Choice", spinner_choice.getSelectedItem().toString());
		i.putExtra("Selection",spinner_selection.getSelectedItem().toString());
		i.putExtra("Offset",picker.getValue());
		setResult(40, i);
		this.finish();		
	}
}
