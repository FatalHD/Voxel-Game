package com.voxel.game.Controls;

import org.lwjgl.input.Mouse;

public class MouseControls {

	public int dx, dy, lb;
	
	public void update(boolean grab) {
		dx = Mouse.getDX();
		dy = -Mouse.getDY();
		Mouse.setGrabbed(grab);
		if(Mouse.isButtonDown(0)) lb = 1;
		else
			lb = 0;
	}
	
}
