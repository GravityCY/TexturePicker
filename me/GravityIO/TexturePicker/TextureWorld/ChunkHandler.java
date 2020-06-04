package me.GravityIO.TexturePicker.TextureWorld;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.util.Vector;

import me.GravityIO.TexturePicker.Main;

public class ChunkHandler {
	static protected int totalLoadedImages = 0;
	static protected List<ImageChunk> imageChunks = new ArrayList<ImageChunk>();

	private Converter converter = new Converter();
	private ChunkFinder chunkFinder = new ChunkFinder();
	private ChunkPlacer chunkPlacer = new ChunkPlacer();

	// Place an image in the texture world
	public boolean loadImage(File texture) {
		if (converter.isConverted(texture.getName())) {
			ImageChunk lastImageChunk = imageChunks.isEmpty() == false ? imageChunks.get(imageChunks.size() - 1) : null;
			Vector startPos = chunkFinder.findAvailableChunk(lastImageChunk, texture);
			List<Material> convertedMats = converter.getConverted(texture.getName());
			chunkPlacer.placeBlocks(convertedMats, startPos);
			return true;
		} else {
			ImageChunk lastImageChunk = imageChunks.isEmpty() == false ? imageChunks.get(imageChunks.size() - 1) : null;
			Vector startPos = chunkFinder.findAvailableChunk(lastImageChunk, texture);
			List<Material> convertedMats = converter.convertImage(texture);
			chunkPlacer.placeBlocks(convertedMats, startPos);
			return true;
		}
	}

	// remove an image in the texture world
	public boolean unloadImage(String name) {
		return false;
	}

	// Checks if an image is in the texture world
	public boolean isLoaded(String name) {
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

	public void createChunkDataYml() {
		// TODO Auto-generated method stub
		File chunkData = new File(Main.getPlugin(Main.class).getDataFolder(), "chunkData.yml");
		if (!chunkData.exists()) {
			try {
				chunkData.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {

		}
	}

}