package com.beck.spaceblaster;

public class Ammo {
	//Every shot fired has speed and direction. We have 5 instances in our Panel class. 
	// Each instance contains separate values so that each shot moves independently.
	//Note: The int isactive is used to determine whether the ammo bitmap should be drawn (Panel.java).
	public float speedx = 0.0f;
	public float speedy = 0.0f;
	public float movingspeed =4f;
	public int isactive =0;
	public float positionx;
	public float positiony;

}
