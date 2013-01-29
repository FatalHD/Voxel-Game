package com.voxel.game.World.Biomes;

public class Biomes {

	private String type;
	private int id;
	
	public static final Biomes[] ID = new Biomes[256];
	public static final Biomes Flat = (new Biomes(0)).type("flat");

	protected Biomes(int i) {
		if (ID[i] != null) {
			throw new IllegalArgumentException("Chunk " + i + " is already occupied by " + ID[i] + " when trying to pass " + this);
		} else {
			ID[i] = this;
			this.id = i;
		}
	}

	private Biomes type(String s) {
		this.type = "terrain." + s;
		return this;
	}

	public String getType() {
		return type;
	}

}
