package com.voxel.game.Graphics;

import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class SpriteSheet {

	private String path;
	public int SIZE;
	public int[] pixels;

	public static SpriteSheet tiles = new SpriteSheet("/textures/terrain.png",false);
	public static SpriteSheet map = new SpriteSheet("/textures/map.png",true);

	public SpriteSheet(String path,boolean enlarge) {
		this.path = path;
		load(enlarge);
	}

	private void load(boolean enlarge) {
		try {
			BufferedImage image = ImageIO.read((SpriteSheet.class.getResource(path)));
			if(enlarge==true){
				AffineTransform tx = new AffineTransform();
				tx.scale(3, 3);

			    AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
			    image = op.filter(image, null);
			}
			int w = image.getWidth();
			int h = image.getHeight();
			this.SIZE = image.getWidth();
			pixels = new int[SIZE * SIZE];
			image.getRGB(0, 0, w, h, pixels, 0, w);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
