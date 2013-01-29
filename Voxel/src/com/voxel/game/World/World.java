package com.voxel.game.World;

import static org.lwjgl.opengl.GL11.*;

import com.voxel.game.Entity.Mobs.Player;

public class World {

	public Player player;
	private WorldGen world;

	public World() {
		player = new Player();
		world = new WorldGen();
	}

	public void update(double delta) {
		player.update(delta);
		if (player.mouse.lb == 1) {
			System.out.println("Click");
		}
	}

	public void render() {
		glClearColor(77 / 255.0f, 129 / 255.0f, 225 / 255.0f, 1.0f);
		player.render();
		world.render();
		//player.cam.setView2();
	}

}
