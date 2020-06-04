package me.GravityIO.TexturePicker.TextureWorld;

import java.io.File;

import org.bukkit.util.Vector;

public class ChunkFinder {

	public Vector findAvailableChunk(ImageChunk imageChunk, File texture) {

		if (imageChunk != null) {
			System.out.println("imageChunk != null");
			if (imageChunk.cameFrom == "down") {
				Vector position = imageChunk.getVectorTopLeft().add(new Vector(0, 0, 128));
				ChunkHandler.totalLoadedImages++;
				ChunkHandler.imageChunks
						.add(new ImageChunk(texture.getName(), ChunkHandler.totalLoadedImages - 1, position, "right"));
				return position;
			}
			if (imageChunk.cameFrom == "right") {
				Vector position = imageChunk.getVectorTopLeft().add(new Vector(128, 0, 0));
				ChunkHandler.totalLoadedImages++;
				ChunkHandler.imageChunks.add(
						new ImageChunk(texture.getName(), ChunkHandler.totalLoadedImages - 1, imageChunk.getVectorTopLeft(), "up"));
				return position;
			}
			if (imageChunk.cameFrom == "up") {
				Vector position = imageChunk.getVectorTopLeft().add(new Vector(0, 0, -128));
				ChunkHandler.totalLoadedImages++;
				ChunkHandler.imageChunks
						.add(new ImageChunk(texture.getName(), ChunkHandler.totalLoadedImages - 1, position, "left"));
				return position;
			}
			if (imageChunk.cameFrom == "left") {
				Vector position = imageChunk.getVectorTopLeft().add(new Vector(-128, 0, 0));
				ChunkHandler.totalLoadedImages++;
				ChunkHandler.imageChunks
						.add(new ImageChunk(texture.getName(), ChunkHandler.totalLoadedImages - 1, position, "down"));
				return position;
			}
		} else {
			Vector position = new Vector(64, 3, -186);
			ChunkHandler.totalLoadedImages++;
			ChunkHandler.imageChunks.add(new ImageChunk(texture.getName(), ChunkHandler.totalLoadedImages - 1, position, "down"));
			return position;
		}
		return null;

	}

}
