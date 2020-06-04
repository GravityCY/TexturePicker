package me.GravityIO.TexturePicker.TextureWorld;

import java.util.ArrayList;
import java.util.List;

class Helper {

}

public class ChunkHandler {
	static public int totalImages = 0;
	static public List<ImageChunk> imageChunks = new ArrayList<ImageChunk>();

	// Place an image in the texture world
	public boolean loadImage(String name) {
		name = formatName(name);
		return false;
	}

	// remove an image in the texture world
	public boolean unloadImage(String name) {
		name = formatName(name);
		return false;
	}

	// Checks if an image is in the texture world
	public boolean isLoaded(String name) {
		name = formatName(name);
		for (ImageChunk image : imageChunks) {
			if (image.getName().equalsIgnoreCase(name)) {
				return true;
			}
		}
		return false;
	}

	// Get the name and chunkIndex of each loaded chunk in the texture world
	public List<ImageChunk> getLoadedChunks() {
		return imageChunks;
	}

	// Get the name of each loaded chunk in the texture world
	public List<String> getLoadedChunkNames() {
		List<String> names = new ArrayList<String>();
		for (ImageChunk a : imageChunks) {
			names.add(a.getName());
		}
		return names;
	}

	// Formats the name to replace the . with _ "texture.png" -> "texture_png"
	public String formatName(String name) {
		name = name.replace('.', '_');
		return name;
	}

}