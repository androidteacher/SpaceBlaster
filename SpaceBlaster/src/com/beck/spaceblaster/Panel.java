package com.beck.spaceblaster;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;



public class Panel extends SurfaceView implements SurfaceHolder.Callback{
	//GLOBAL VARIABLES
	private CanvasThread canvasthread;
    //Variable used to hold our bitmap
	Bitmap bm;
	Bitmap ammopic;
    //Variable used to hold the current rotation value of the bitmap
    private float rotation = 20;
    //A Matrix is used to define size, position, and rotation 
    //of a graphic. We can set up a matrix and use it when drawing.
    private Matrix position; 
    
    //We need 5 Matrices so that each shot can move separately.
    private Matrix[] ammomatrix = new Matrix[5];
    
    //x and y hold the width and height of our canvas.
    int x;
    int y;
    int start = 0;
    float speed = 0;
    float positionx = 0;
	float positiony = 0;
	//We need 5 Ammo Objects.
	Ammo[] ammo = new Ammo[5];
    //END GLOBAL VARIABLES
    
    
    public Panel(Context context, AttributeSet attrs) {
		super(context, attrs); 
		// TODO Auto-generated constructor stub
	   
		getHolder().addCallback(this);
	    canvasthread = new CanvasThread(getHolder(), this);
	    setFocusable(true);
	    //Here we load our Bitmap variable with the graphic ship.png in the drawable-hdpi folder.
	    bm = BitmapFactory.decodeResource(getResources(), R.drawable.ship);
	    ammopic = BitmapFactory.decodeResource(getResources(), R.drawable.ammo);
	    //Here we initialize our position Matrix.
        position = new Matrix();
      
        //We initialize our ammo objects using this simple for loop. There will be 5.
        
        for (int i =0; i < ammo.length; i++)
        {
        	ammo[i] = new Ammo();
        }
      
        //We initialize 5 Matrices. Each Matrix corresponds to an instance of ammo. 
        for (int i =0; i < ammomatrix.length; i++)
        {
        	ammomatrix[i] = new Matrix();
        }
      
	}

	 


	 public Panel(Context context) {
		   super(context);
		    getHolder().addCallback(this);
		  
		    setFocusable(true);

	    }
	
	@Override
	public void onDraw(Canvas canvas) {
       //x and y are **global** variables defined at the top of this class. We need to know how big our screen
	   // is. The center on a phone will be different than the center point on a
	   // tablet.
		
		if (GetterSetter.button3pressed == 1)
		{
			speed = speed +.04f;
		}
		else
			if (speed > 0)
		{
			speed= speed - .01f;
		}
		
		x = canvas.getWidth();
        y = canvas.getHeight();			   
	   
        //Within the update method, we determine position and speed of all objects. 
        update();	
        
        canvas.drawColor(Color.BLACK);
		
        //The Matrix position is updated within the update() method.
	    
	    canvas.drawBitmap(bm,position, null);
		
      //Here we loop through all of the ammo matrices. If the corresponding ammo instance has
	  // the isactive integer set to 1, it will draw the bitmap to the screen.
      for(int i = 0; i < ammomatrix.length; i++)
	    {
	    	if (ammo[i].isactive == 1)
	    	{
	    		canvas.drawBitmap(ammopic,ammomatrix[i], null);
	    	}
	    }
	   
	  
		
	}

	 public void update() {
	      
	      
		 
		 //Every time an ammo object reaches the edge of the screen, --see below-- , ammodestroyed is increased
		 //by one. Once 4 shots have reached the edged, all ammunition is reset to default values.
		 if (GetterSetter.ammodestroyed > 3)
		 {
			 //Just making sure justtouched isn't 1 here.
			 GetterSetter.justtouched = 0;
			 //We are ready to fire 4 new shots.
			 GetterSetter.shotsfired = 0;
			 //We tell the program nothing has reached the edge.
			 GetterSetter.ammodestroyed = 0;
			 //the resetammo method reinitializes all ammo to default values.
			 resetammo();
		 }
		 //Matrix m is a **local** variable. We will configure m and use it
		 // set the value of our global Matrix position.
		
		 Matrix m = new Matrix();
		 
		 
		 //Matrix localammomatrix is a **local** variable. We will configure it and use it
		 // set the value of our global Matrix --ammomatrix-- .
		 
		//declare and initialize our local matrix array. 
		 Matrix[] localammomatrix = new Matrix[5];
		 
		 for (int i =0; i < localammomatrix.length; i++)
	        {
	        	localammomatrix[i] = new Matrix();
	        }
	      
		
			
		
		
	       
		   //Current rotation value
		   m.postRotate(rotation, bm.getWidth()/2, bm.getHeight()/2);
		   
		   //This is where we define the center of the screen.
		   //x = canvas.getWidth();   --Look in onDraw
		   //y = canvas.getHeight();  --Look in on Draw
		   //Example: If the canvas width is 10, then x/2 would equal 5, --the horizontal center.
		   //We use positionx and positiony to draw the center of the bitmap to the center of the screen.
		   
		   if (start == 0)
		   {
		   positionx = ((x/2) - bm.getWidth()/2);
	       positiony = ((y/2) - bm.getHeight()/2);
		   start = 1;
		   }
		 
		   float speedx = (float) Math.sin(rotation*(Math.PI/180)) * speed;
		   float speedy = (float) Math.cos(rotation*(Math.PI/180)) * speed;
		  
		  //If the user touches the screen, we want to get the current position, rotation, and speed of the ship.
		  //We load this information into the current ammo instance and use it to send it flying off in the correct
		  //direction.
		 
		   if (GetterSetter.justtouched == 1 && GetterSetter.shotsfired < 5)
			{
			   GetterSetter.justtouched =0;
			
			//Note: ammo[GetterSetter.shotsfired] is an array position equaling either, 0,1,2,3,4
			//Every time the screen is touched, the shotsfired value goes up by one. 
			   float ammospeedx = (float) Math.sin(rotation*(Math.PI/180)) * (ammo[GetterSetter.shotsfired].movingspeed + speed);
			   float ammospeedy = (float) Math.cos(rotation*(Math.PI/180)) * (ammo[GetterSetter.shotsfired].movingspeed + speed);
			   
			 //Load the current ammo object with all the relevant info.
			  ammo[GetterSetter.shotsfired].isactive = 1;
			  ammo[GetterSetter.shotsfired].speedx= ammospeedx;
			  ammo[GetterSetter.shotsfired].speedy = ammospeedy;
			  ammo[GetterSetter.shotsfired].positionx = positionx;
			  ammo[GetterSetter.shotsfired].positiony = positiony;
						
			}
	
		   //Set X Boundary for ship
		   if((positionx + speedx ) > (x - 200) || (positionx + speedx) < 0 +200) 
		   		{
		
		   speed = 0;
		   	 
		   		}
		  //Set Y Boundary for ship
		   if((positiony - speedy ) > (y - 50) || (positiony - speedy) < (0 + 20)) 
			   {
				
			   speed = 0;
			   	 
			   }
	     
	      //Move the Ship
		   positionx += speedx;
		   positiony -= speedy;
		 
		  //Move each active ammo instance by looping through them, changing the position value, and assigning 
		  //the new position information to the correct GLOBAL ammomatrix.
		   for(int i =0; i<ammo.length; i++)
		   {
			   if (ammo[i].isactive == 1)
			   {
				//Move the ammo picture.
				 ammo[i].positionx += ammo[i].speedx;
				 ammo[i].positiony -= ammo[i].speedy;
				 //Set up our local matrix correctly. 
				 localammomatrix[i].postTranslate(ammo[i].positionx, ammo[i].positiony);
				 //Assign this info to our global matrix so the drawing can happen.
				 ammomatrix[i].set(localammomatrix[i]);
			
				//Set the X boundary for the ammo and increment ammodestroyed when reached.
				   if((ammo[i].positionx + ammo[i].speedx ) > x || (ammo[i].positionx + ammo[i].speedx) < 0 ) 
				   {
					
					   ammo[i].isactive =0;
					   GetterSetter.ammodestroyed++;
					   	 
				   }
				 
				   //Set the Y boundary for the ammo and increment ammodestroyed when reached.
				   if((ammo[i].positiony + ammo[i].speedy ) > y || (ammo[i].positiony + ammo[i].speedy) < 0 ) 
				   {
					
					   ammo[i].isactive =0;
					   GetterSetter.ammodestroyed++;
					   	 
				   }
				
			   }
		   }
		   
		   
	      
	       m.postTranslate(positionx, positiony);

	        // This method sets the value of of our **global** variable (position) to the **local** variable (m)
	        position.set(m);

	        //update the rotation value. 
	        //We call update every frame refresh in onDraw.
	       if (GetterSetter.button1pressed == 1)
	       {
	    	rotation += 2;   
	       }
	       if (GetterSetter.button2pressed == 1)
	       {
	    	rotation -= 2;   
	       }
	       if (rotation == 360 || rotation == -360)
	       {
	    	   rotation = 0;
	       }
	    }


   public void resetammo()
   {
	   for (int i =0; i > ammo.length; i++)
	   {
		   ammo[i] = new Ammo();
	   }
   }
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		// TODO Auto-generated method stub
		
	}
	
	public void surfaceCreated(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		  canvasthread = new CanvasThread(getHolder(), this);
		canvasthread.setRunning(true);
	    canvasthread.start();

		
	}

	public void surfaceDestroyed(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		boolean retry = true;
		canvasthread.setRunning(false);
		while (retry) {
			try {
				canvasthread.join();
				retry = false;
			} catch (InterruptedException e) {
				// we will try it again and again...
			}
		}

	 
	 
	 
	
	}


}   