package com.voxel.game.Entity.Mobs;

import org.lwjgl.opengl.Display;

import com.voxel.game.Controls.KeyboardControls;
import com.voxel.game.Controls.MouseControls;
import com.voxel.game.Entity.Camera;

public class Player extends Mobs {
	public Camera cam;
	public KeyboardControls key;
	public MouseControls mouse;

	public Player() {
		cam = new Camera(70.0f, (float) Display.getWidth() / (float) Display.getHeight(), 0.01f, 100f);
		key = new KeyboardControls();
		mouse = new MouseControls();
	}

	public void update(double delta) {
		key.update();
		if (key.esc) mouse.update(false);
		if (!key.esc) mouse.update(true);
		if (!key.esc) {
			running = key.shift;
			if (key.up) cam.move(1*delta, 1, running);
			if (key.down) cam.move(-1*delta, 1, running);
			if (key.left) cam.move(1*delta, 0, running);
			if (key.right) cam.move(-1*delta, 0, running);
			if (key.q) cam._y-=0.3f;
			if (key.e) cam._y+=0.3f;
			if (mouse.dx != 0) cam.rotate(1, mouse.dx);
			if (mouse.dy != 0) cam.rotate(0, mouse.dy);
		}
	}

	public void render() {
		cam.setView();
	}

}
