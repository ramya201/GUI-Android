package homework5.android;

import homework2.android.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

public class BtnParameters extends Activity {
	private EditText text;
	private EditText width;
	private EditText height;
	private Spinner foreColor;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.btn_parameters);
		
		text = 	(EditText) findViewById(R.id.LblText);
		width = (EditText) findViewById(R.id.widthText);
		height = (EditText) findViewById(R.id.heightText);
		
		foreColor = (Spinner) findViewById(R.id.color1);
		ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this,
		        R.array.colors_array, android.R.layout.simple_spinner_item);
		adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		foreColor.setAdapter(adapter1);
	}

	public void submit(View view) {	
		Intent i = new Intent();
		i.putExtra("Text",text.getText().toString());
		i.putExtra("Width",width.getText().toString());
		i.putExtra("Height",height.getText().toString());
		i.putExtra("ForeColor",foreColor.getSelectedItem().toString());
		setResult(10, i);
		this.finish();		
	}
}
