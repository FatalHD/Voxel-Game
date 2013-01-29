package com.voxel.game.Controls;

import org.lwjgl.input.Keyboard;

public class KeyboardControls {

	private boolean[] keys = new boolean[120];
	public boolean up, down, left, right, q, e, shift, esc;
	int tick = 0;

	public void update() {
		if (tick == 10) tick = 0;
		if (tick >= 1) tick += 1;

		if (Keyboard.isKeyDown(Keyboard.KEY_W)) {
			up = true;
		} else {
			up = false;
		}

		if (Keyboard.isKeyDown(Keyboard.KEY_S)) {
			down = true;
		} else {
			down = false;
		}

		if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
			left = true;
		} else {
			left = false;
		}

		if (Keyboard.isKeyDown(Keyboard.KEY_D)) {
			right = true;
		} else {
			right = false;
		}

		if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
			shift = true;
		} else {
			shift = false;
		}
		
		if (Keyboard.isKeyDown(Keyboard.KEY_Q)) {
			q = true;
		} else {
			q = false;
		}
		
		if (Keyboard.isKeyDown(Keyboard.KEY_E)) {
			e = true;
		} else {
			e = false;
		}
		

		if (Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {
			if (esc && tick == 0) {
				esc = false;
				tick = 1;
			} else if (tick == 0) {
				esc = true;
				tick = 1;
			}
		}
	}
}
