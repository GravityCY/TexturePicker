package me.GravityIO.TexturePicker.TextureWorld;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.util.Vector;

import me.GravityIO.TexturePicker.Main;

public class ChunkHandler {
	static protected List<ImageChunk> imageChunks = new ArrayList<ImageChunk>();

	Main main;

	public ChunkHandler(Main main) {
		this.main = main;
	}

	final private Converter converter = new Converter();
	final private ChunkFinder chunkFinder = new ChunkFinder(this);
	final private ChunkPlacer chunkPlacer = new ChunkPlacer();
	final private ChunkBreaker chunkBreaker = new ChunkBreaker(this);

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

	public void unloadImage(String name) {
		chunkBreaker.unloadImage(name);
	}

	public Vector findGetVector(String name) {
		for (ImageChunk c : ChunkHandler.imageChunks) {
			if (c.getName().equalsIgnoreCase(name)) {
				return c.getVectorTopLeft();
			}
		}
		return null;
	}

	public boolean findRemove(String name) {
		List<ImageChunk> chunks = new ArrayList<ImageChunk>(ChunkHandler.imageChunks);
		for (ImageChunk c : chunks) {
			if (c.getName().equalsIgnoreCase(name)) {
				ChunkHandler.imageChunks.remove(c);
				return true;
			}
		}
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

	public boolean contains(String name) {
		for (ImageChunk c : ChunkHandler.imageChunks) {
			if (c.getName().equalsIgnoreCase(name)) {
				System.out.println(c.getName());
				return true;
			}
		}
		return false;
	}

	public boolean containsVector(Vector vector) {
		for (ImageChunk c : ChunkHandler.imageChunks) {
			if (c.getVectorTopLeft().equals(vector)) {
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
		File chunkData = new File(main.getDataFolder(), "chunkData.yml");
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