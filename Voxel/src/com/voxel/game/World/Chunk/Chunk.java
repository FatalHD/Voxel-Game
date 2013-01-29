package com.voxel.game.World.Chunk;

import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.lwjgl.BufferUtils;
import org.lwjgl.util.vector.*;

import com.voxel.game.World.WorldGen;
import com.voxel.game.World.Block.Block;

import static org.lwjgl.opengl.GL11.*;

public class Chunk {
	private Vector3f position;
	private final Vector3f size = new Vector3f(16, 16 * 8, 16);
	private int[][][] blockID;

	private FloatBuffer vertBuffer;
	private FloatBuffer normBuffer;
	private FloatBuffer coloBuffer;
	int vertexArrayFloats = 0;
	int normalArrayFloats = 0;
	int colourArrayFloats = 0;

	private int glListIndex = 0;

	public Chunk(Vector3f position) {
		this.position = position;
		blockID = new int[(int) size.x][(int) size.y][(int) size.z];
	}

	public void generateChunk(WorldGen world) {
		Random random = new Random();
		for (int z = 0; z < (int) size.z; z++) {
			for (int x = 0; x < (int) size.x; x++) {
				for (int y = 0; y < (int) size.y; y++) {
					int r = random.nextInt(3);
					int r2 = random.nextInt(10);
					if (y <= 32) {
						 blockID[x][y][z] = 1;
						if(r==0){
							blockID[x][y][z] = 3;
							blockID[x][y+1][z] = 3;
							
						}
					}
					blockID[x][0][z] = 4;
					blockID[x][32][z] = 2;
				}
			}
		}
		for (int z = 0; z < (int) size.z; z++) {
			for (int x = 0; x < (int) size.x; x++) {
				int r = random.nextInt(3);
				blockID[x][r][z] = 4;
			}
		}
	}

	public void renderData() {
		List<float[]> vertexArray = new ArrayList<float[]>();
		List<float[]> normalArray = new ArrayList<float[]>();
		List<float[]> colourArray = new ArrayList<float[]>();

		// System.out.println("Render");
		for (int z = 0; z < (int) size.z; z++) {
			for (int x = 0; x < (int) size.x; x++) {
				for (int y = 0; y < (int) size.y; y++) {
					if (blockID[x][y][z] != 0) {
						// System.out.println("["+x+"]["+y+"]["+z+"] = "+blockID[x][y][z]);
						if (blockID[x][y][z] != 0) {
							Vector3f pos1 = new Vector3f(position.x + x, position.y + y, position.z + z);
							Vector3f pos2 = new Vector3f(pos1.x + 1, pos1.y + 1, pos1.z + 1);
							Vector4f colour = null;
							if (blockID[x][y][z] == 4) colour = Block.CoreBlock.getColour();
							if (blockID[x][y][z] == 3) colour = Block.Dirt.getColour();
							if (blockID[x][y][z] == 2) colour = Block.Grass.getColour();
							if (blockID[x][y][z] == 1) colour = Block.Stone.getColour();

							if ((y == size.y - 1) || (blockID[x][y + 1][z] == 0)) {
								vertexArray.add(new float[] { pos2.x, pos2.y, pos1.z, pos1.x, pos2.y, pos1.z, pos1.x, pos2.y, pos2.z, pos2.x, pos2.y, pos2.z });
								normalArray.add(new float[] { 0f, 1f, 0f, 0f, 1f, 0f, 0f, 1f, 0f, 0f, 1f, 0f });
								colourArray.add(new float[] { colour.x, colour.y, colour.z, colour.w, colour.x, colour.y, colour.z, colour.w, colour.x, colour.y, colour.z, colour.w, colour.x, colour.y, colour.z, colour.w });
							}

							if ((y == 0) || (blockID[x][y - 1][z] == 0)) {
								vertexArray.add(new float[] { pos2.x, pos1.y, pos2.z, pos1.x, pos1.y, pos2.z, pos1.x, pos1.y, pos1.z, pos2.x, pos1.y, pos1.z });
								normalArray.add(new float[] { 0f, -1f, 0f, 0f, -1f, 0f, 0f, -1f, 0f, 0f, -1f, 0f });
								colourArray.add(new float[] { colour.x, colour.y, colour.z, colour.w, colour.x, colour.y, colour.z, colour.w, colour.x, colour.y, colour.z, colour.w, colour.x, colour.y, colour.z, colour.w });
							}

							if ((z == size.z - 1) || (blockID[x][y][z + 1] == 0)) {
								vertexArray.add(new float[] { pos2.x, pos2.y, pos2.z, pos1.x, pos2.y, pos2.z, pos1.x, pos1.y, pos2.z, pos2.x, pos1.y, pos2.z });
								normalArray.add(new float[] { 0f, 0f, 1f, 0f, 0f, 1f, 0f, 0f, 1f, 0f, 0f, 1f });
								colourArray.add(new float[] { colour.x, colour.y, colour.z, colour.w, colour.x, colour.y, colour.z, colour.w, colour.x, colour.y, colour.z, colour.w, colour.x, colour.y, colour.z, colour.w });
							}

							if ((z == 0) || (blockID[x][y][z - 1] == 0)) {
								vertexArray.add(new float[] { pos1.x, pos2.y, pos1.z, pos2.x, pos2.y, pos1.z, pos2.x, pos1.y, pos1.z, pos1.x, pos1.y, pos1.z });
								normalArray.add(new float[] { 0f, 0f, -1f, 0f, 0f, -1f, 0f, 0f, -1f, 0f, 0f, -1f });
								colourArray.add(new float[] { colour.x, colour.y, colour.z, colour.w, colour.x, colour.y, colour.z, colour.w, colour.x, colour.y, colour.z, colour.w, colour.x, colour.y, colour.z, colour.w });
							}

							if ((x == size.x - 1) || (blockID[x + 1][y][z] == 0)) {
								vertexArray.add(new float[] { pos2.x, pos2.y, pos1.z, pos2.x, pos2.y, pos2.z, pos2.x, pos1.y, pos2.z, pos2.x, pos1.y, pos1.z });
								normalArray.add(new float[] { 1f, 0f, 0f, 1f, 0f, 0f, 1f, 0f, 0f, 1f, 0f, 0f });
								colourArray.add(new float[] { colour.x, colour.y, colour.z, colour.w, colour.x, colour.y, colour.z, colour.w, colour.x, colour.y, colour.z, colour.w, colour.x, colour.y, colour.z, colour.w });
							}

							if ((x == 0) || (blockID[x - 1][y][z] == 0)) {
								vertexArray.add(new float[] { pos1.x, pos2.y, pos2.z, pos1.x, pos2.y, pos1.z, pos1.x, pos1.y, pos1.z, pos1.x, pos1.y, pos2.z });
								normalArray.add(new float[] { -1f, 0f, 0f, -1f, 0f, 0f, -1f, 0f, 0f, -1f, 0f, 0f });
								colourArray.add(new float[] { colour.x, colour.y, colour.z, colour.w, colour.x, colour.y, colour.z, colour.w, colour.x, colour.y, colour.z, colour.w, colour.x, colour.y, colour.z, colour.w });
							}
						}
					}
				}
			}
		}

		int numFloats = 0;
		for (float[] a : vertexArray) {
			numFloats += a.length;
		}
		vertBuffer = BufferUtils.createFloatBuffer(numFloats);
		for (float[] a : vertexArray) {
			vertBuffer.put(a);
		}
		vertBuffer.flip();
		numFloats = 0;

		for (float[] a : normalArray) {
			numFloats += a.length;
		}
		normBuffer = BufferUtils.createFloatBuffer(numFloats);
		for (float[] a : normalArray) {
			normBuffer.put(a);
		}
		normBuffer.flip();
		numFloats = 0;

		for (float[] a : colourArray) {
			numFloats += a.length;
		}
		coloBuffer = BufferUtils.createFloatBuffer(numFloats);
		for (float[] a : colourArray) {
			coloBuffer.put(a);
		}
		coloBuffer.flip();

		if (glListIndex != 0) {
			glDeleteLists(glListIndex, 1);
			glListIndex = 0;
		}
	}

	public void render() {
		if (glListIndex == 0) {
			glListIndex = glGenLists(1);
			glNewList(glListIndex, GL_COMPILE);
			glPushMatrix();
			glVertexPointer(3, 0, vertBuffer);
			glNormalPointer(0, normBuffer);
			glColorPointer(4, 0, coloBuffer);
			glColor3f(0.4f, 1.0f, 0.45f);
			glDrawArrays(GL_QUADS, 0, vertBuffer.limit() / 3);
			glPopMatrix();
			glEndList();
		}
		glCallList(glListIndex);
	}

}