package com.voxel.game;

public final class Voxel {

	private static VoxelEngine engine = new VoxelEngine();

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