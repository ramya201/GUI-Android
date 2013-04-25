package homework3.android;

import homework2.android.R;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class ChoiceType extends Activity {
	private Spinner spinner1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.choice_type);
		
		spinner1 = (Spinner) findViewById(R.id.choice);
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
		        R.array.choice_array, android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner1.setAdapter(adapter);
	}

	public void submitChoice(View view) {	
		Intent i = new Intent();
		i.putExtra("Choice", spinner1.getSelectedItem().toString());
		setResult(30, i);
		this.finish();		
	}
}
