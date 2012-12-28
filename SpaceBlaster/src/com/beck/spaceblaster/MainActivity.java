package com.beck.spaceblaster;

import java.lang.reflect.Field;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {
     float x;
     float y;
     TextView tv;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
         tv = (TextView) findViewById(R.id.textView1);
       
         tv.setText("This program is designed for my Android programming class. \nThe code can " +
         		"be found at github: androidteacher.\n" +
         		"The lessons to go along with this tutorial can be found at:\n " +
         		"http://linuxclassroom.com/bitmap1/ \n" +
         		"Touch the Screen to Fire!") ;
       //Right Button
         final Button button = (Button) findViewById(R.id.button1);
        button.setOnTouchListener(new View.OnTouchListener() {
			
			public boolean onTouch(View v, MotionEvent event) {
				  if (event.getAction() == MotionEvent.ACTION_DOWN ) 
				  { 
				  GetterSetter.button1pressed = 1;
				  
				  return true;
				  }
				 
				  if (event.getAction() == MotionEvent.ACTION_UP ) 
				  { 
					  GetterSetter.button1pressed = 0;
					 
						  return true;
				  }
				return false;
			}
		
        });
        //Left Button
        final Button button2 = (Button) findViewById(R.id.button2);
        button2.setOnTouchListener(new View.OnTouchListener() {
			
			public boolean onTouch(View v, MotionEvent event) {
				  if (event.getAction() == MotionEvent.ACTION_DOWN ) 
				  { 
				  GetterSetter.button2pressed = 1;
				 				  return true;
				  }
				 
				  if (event.getAction() == MotionEvent.ACTION_UP ) 
				  { 
					  GetterSetter.button2pressed = 0;
					  
					  return true;
				  }
				return false;
			}
		
        });
       //Fired Button
        final Button button3 = (Button) findViewById(R.id.button3);
        button3.setOnTouchListener(new View.OnTouchListener() {
			
			public boolean onTouch(View v, MotionEvent event) {
				  if (event.getAction() == MotionEvent.ACTION_DOWN ) 
				  { 
				  GetterSetter.button3pressed = 1;
				  
				  return true;
				  }
				 
				  if (event.getAction() == MotionEvent.ACTION_UP ) 
				  { 
					  GetterSetter.button3pressed = 0;
					  
					  return true;
				  }
				return false;
			}
		
        });
         
        /*
         * HACK:  Show the overflow dots on devices
         * that have a menu button
         */
           try {
               ViewConfiguration config = ViewConfiguration.get(this);
               Field menuKeyField = ViewConfiguration.class.getDeclaredField("sHasPermanentMenuKey");
               if(menuKeyField != null) {
                   menuKeyField.setAccessible(true);
                   menuKeyField.setBoolean(config, false);
               }
           } catch (Exception ex) {
               // Ignore
           }
           
           /*
            * END OVERFLOW HACK
            *
            */
           
    }
    @Override
    public boolean onTouchEvent(MotionEvent e)
    {
      
        x = e.getX();
        y = e.getY();
        //Every Time the screen is touched we toggle the justtouched value and increase shotsfired.
        //We'll be using this data in our Panel class.
        GetterSetter.shotsfired++;
        GetterSetter.justtouched = 1;
        
        
             return true;
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
}
