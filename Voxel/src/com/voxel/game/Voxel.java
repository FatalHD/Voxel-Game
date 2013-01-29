package com.voxel.game;

import com.voxel.game.Utility.Logger;

public final class Voxel {

	private static VoxelEngine engine = new VoxelEngine();
	private static Logger log = new Logger();

	private Voxel() {
	}

	public static void main(String[] args) {
		try {
			engine.init();
			engine.run();
			engine.cleanUp();
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
		}
	}
}