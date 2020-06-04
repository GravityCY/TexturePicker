package me.GravityIO.TexturePicker.TextureWorld;

import org.bukkit.util.Vector;

public class ImageChunk {

	private int currentChunk;
	private Vector location;
	private String imageName;
	protected String cameFrom;

	public ImageChunk(String imageName, int currentChunk, Vector location, String cameFrom) {
		this.imageName = imageName;
		this.currentChunk = currentChunk;
		this.location = location;
		this.cameFrom = cameFrom;
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
