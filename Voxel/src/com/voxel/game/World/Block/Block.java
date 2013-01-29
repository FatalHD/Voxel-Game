package com.voxel.game.World.Block;

import org.lwjgl.util.vector.*;

public class Block {
	public static final Block[] ID = new Block[256];
	public static final Block Air = (new Block(0, 0)).name("air");
	public static final Block Stone = (new BlockStone(1, 1)).name("stone").colour(new Vector4f(0.5f, 0.5f, 0.5f, 1.0f));
	public static final Block Grass = (new BlockGrass(2, 2)).name("grass").colour(new Vector4f(0.0f, 0.3f, 0.0f, 1.0f));
	public static final Block Dirt = (new BlockDirt(3, 3)).name("dirt").colour(new Vector4f(0.4f, 0.3f, 0.1f, 1.0f));
	public static final Block CoreBlock = (new BlockCoreBlock(4, 4)).name("core block").colour(new Vector4f(0.0f, 0.0f, 0.0f, 1.0f));
	public static final Block Quartz = (new BlockQuartz(5, 4)).name("quartz");
	public static final Block Water = (new Block(6, 4)).name("water");

	private String name;
	private int id;
	private int textureID;
	public boolean top, bottom, front, back, right, left;
	private Vector4f c = null;

	protected Block(int i, int texture) {
		if (ID[i] != null) {
			throw new IllegalArgumentException("Block " + i + " is already occupied by " + ID[i] + " when trying to pass " + this);
		} else {
			ID[i] = this;
			this.id = i;
		}
	}

	public Block name(String s) {
		this.name = "block." + s;
		return this;
	}

	public Block colour(Vector4f colour) {
		this.c = colour;
		return this;
	}
	
	public Vector4f getColour() {
		return c;
	}

	public int getID() {
		return id;
	}

	public String getName() {
		return name;
	}

	public Block visibleSides(boolean top, boolean bottom, boolean front, boolean back, boolean right, boolean left) {
		this.top = top;
		this.bottom = bottom;
		this.front = front;
		this.back = back;
		this.right = right;
		this.left = left;
		return this;
	}

}
