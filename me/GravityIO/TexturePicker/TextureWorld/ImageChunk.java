package me.GravityIO.TexturePicker.TextureWorld;

import org.bukkit.util.Vector;

public class ImageChunk {

	private int currentChunk;
	private Vector location;
	private String imageName;

	public ImageChunk(String imageName, int currentChunk, Vector location) {
		this.imageName = imageName;
		this.currentChunk = currentChunk;
		this.location = location;
		ChunkHandler.totalImages++;
	}

	public String getName() {
		return imageName;
	}
	
	public int getCurrentChunk() {
		return currentChunk;
	}

	public Vector getVectorTopLeft() {
		return location;
	}
}
