package com.voxel.game.Entity;

public class Entity {
	public int x, y;
	private boolean removed = false;
	
	public void update(){
		
	}
	
	public void render(){
		
	}
	
	public void remove(){
		removed = true;
	}
	
	public boolean isRemoved(){
		return removed;
	}
	
}
