package com.voxel.game.World;

import java.util.Random;
import org.lwjgl.util.vector.Vector3f;
import com.voxel.game.World.Chunk.Chunk;

public class WorldGen {

	private Chunk[][][] chunks;
	Random r = new Random();
	int _x = 5, _y = 1, _z = 5;

	public WorldGen() {
		chunks = new Chunk[_x][_y][_z];
		for (int x = 0; x < _x; x++) {
			for (int y = 0; y < _y; y++) {
				for (int z = 0; z < _z; z++) {
					chunks[x][y][z] = new Chunk(new Vector3f(x*16,0,z*16));
				}
			}
		}

		for (int x = 0; x < _x; x++) {
			for (int y = 0; y < _y; y++) {
				for (int z = 0; z < _z; z++) {
					chunks[x][y][z].generateChunk(this);
				}
			}
		}

		for (int x = 0; x < _x; x++) {
			for (int y = 0; y < _y; y++) {
				for (int z = 0; z < _z; z++) {
					chunks[x][y][z].renderData();
				}
			}
		}
	}

	public void render() {
		for (int x = 0; x < _x; x++) {
			for (int y = 0; y < _y; y++) {
				for (int z = 0; z < _z; z++) {
					chunks[x][y][z].render();
				}
			}
		}
	}

}
