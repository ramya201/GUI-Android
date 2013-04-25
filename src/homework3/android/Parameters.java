package homework3.android;

import homework2.android.R;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.NumberPicker;
import android.widget.Spinner;

public class Parameters extends Activity {
	private Spinner spinner;
	private NumberPicker picker;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.rect_parameters);
		
		spinner = (Spinner) findViewById(R.id.color);
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
		        R.array.colors_array, android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(adapter);
		
		picker = (NumberPicker) findViewById(R.id.numberPicker1);
		picker.setMaxValue(20);
		picker.setMinValue(1);
	}
	
	public void submitChoice(View view) {
		Intent parent = this.getIntent();
		String type = parent.getStringExtra("CallingBtn");
		
	
		Intent i = new Intent();
		i.putExtra("Color", spinner.getSelectedItem().toString());
		i.putExtra("LineThickness",picker.getValue());
		if (type.equals("rectBtn")) {
			setResult(10, i);
		} else if (type.equals("lineBtn")) {
			setResult(20,i);
		}
		this.finish();
		
	}

}
