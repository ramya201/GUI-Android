package homework2.android;


import homework3.android.InteractiveWindowGroup;
import homework4.android.NodeEdgeEditor;
import homework3.android.WindowGroup;
import homework4.android.TestHomework4;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Homework2AndroidActivity extends Activity {
    /** Called when the activity is first created. */
	
	private Button bTestTestFrame;
	private Button bTestOutlineRect;
	private Button bTestAll;
	private Button bTestSimpleGroup;
	private Button bTestLayoutGroup;
	private Button bTestScaledGroup;
	private Button bTestHomework2;

	private Context context;   
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        context = this;
        
        bTestTestFrame = (Button) findViewById(R.id.testTestFrame);
        bTestTestFrame.setText("TestTestFrame");
        bTestTestFrame.setOnClickListener(new OnClickListener(){
        	public void onClick(View v){        		
        		 Intent i = new Intent(context, TestTestFrame.class);                    
        	     startActivity(i);
        	}
        });
        
        bTestOutlineRect = (Button) findViewById(R.id.testRect);
        //bTestOutlineRect.setEnabled(false);
        bTestOutlineRect.setText("TestOutlineRect");
        bTestOutlineRect.setOnClickListener(new OnClickListener(){
        	public void onClick(View v){
        		Intent i = new Intent(context, TestOutlineRect.class);      
        		i.putExtra("lineThickness", "");
        		startActivity(i);
        	}
        });
        	
        
        bTestAll = (Button) findViewById(R.id.testAll);
        //bTestAll.setEnabled(false);
        bTestAll.setText("TestAllObjects");
        bTestAll.setOnClickListener(new OnClickListener(){
        	public void onClick(View v){
        		Intent i = new Intent(context, TestAllObjects.class);      
        		startActivity(i);  
        	}
        });
        
        bTestLayoutGroup = (Button) findViewById(R.id.testLayoutGroup);
        //bTestLayoutGroup.setEnabled(false);
        bTestLayoutGroup.setText("TestLayoutGroup");
        bTestLayoutGroup.setOnClickListener(new OnClickListener(){
        	public void onClick(View v){
        		Intent i = new Intent(context, TestLayoutGroup.class);      
        		i.putExtra("nObject", "");
        		startActivity(i);        		
        	}
        });
        
        bTestSimpleGroup = (Button) findViewById(R.id.testSimpleGroup);
        //bTestSimpleGroup.setEnabled(false);
        bTestSimpleGroup.setText("TestSimpleGroup");
        bTestSimpleGroup.setOnClickListener(new OnClickListener(){
        	public void onClick(View v){
        		Intent i = new Intent(context, TestSimpleGroup.class);      
        		i.putExtra("nObject", "");
        		startActivity(i); 
        	}
        });
        
        bTestHomework2 = (Button) findViewById(R.id.testHomework2);
        //bTestHomework2.setEnabled(false);
        bTestHomework2.setText("TestHomework2");
        bTestHomework2.setOnClickListener(new OnClickListener(){
        	public void onClick(View v){
        		Log.d("DV", "onClick  View  == "+v);	
        		Intent i = new Intent(context, TestHomework2.class);      
        		Log.d("DV", "starting Activity  i  == "+i);	
        		startActivity(i); 
        	}
        });
        
        bTestHomework2 = (Button) findViewById(R.id.testDrawingEditor);
        //bTestHomework2.setEnabled(false);
        bTestHomework2.setText("TestDrawingEditor");
        bTestHomework2.setOnClickListener(new OnClickListener(){
        	public void onClick(View v){
        		Log.d("DV", "onClick  View  == "+v);	
        		Intent i = new Intent(context, InteractiveWindowGroup.class);      
        		Log.d("DV", "starting Activity  i  == "+i);	
        		startActivity(i); 
        	}
        });
        bTestHomework2 = (Button) findViewById(R.id.testHomework4);
        //bTestHomework2.setEnabled(false);
        bTestHomework2.setText("TestHomework4");
        bTestHomework2.setOnClickListener(new OnClickListener(){
        	public void onClick(View v){
        		Log.d("DV", "onClick  View  == "+v);	
        		Intent i = new Intent(context, TestHomework4.class);      
        		Log.d("DV", "starting Activity  i  == "+i);	
        		startActivity(i); 
        	}
        });
        bTestHomework2 = (Button) findViewById(R.id.nodeEdge);
        //bTestHomework2.setEnabled(false);
        bTestHomework2.setText("TestNodeEdgeEditor");
        bTestHomework2.setOnClickListener(new OnClickListener(){
        	public void onClick(View v){
        		Log.d("DV", "onClick  View  == "+v);	
        		Intent i = new Intent(context, NodeEdgeEditor.class);      
        		Log.d("DV", "starting Activity  i  == "+i);	
        		startActivity(i); 
        	}
        });

        
    }
}