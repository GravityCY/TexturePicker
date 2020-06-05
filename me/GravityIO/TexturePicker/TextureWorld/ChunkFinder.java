package me.GravityIO.TexturePicker.TextureWorld;

import java.io.File;

import org.bukkit.util.Vector;

public class ChunkFinder {

	ChunkHandler chunkHandler;

	public ChunkFinder(ChunkHandler chunkHandler) {
		this.chunkHandler = chunkHandler;
	}

	public Vector findAvailableChunk(ImageChunk imageChunk, File texture) {

		if (imageChunk != null) {
			if (imageChunk.cameFrom == "down") {
				if (!chunkHandler.containsVector(imageChunk.getVectorTopLeft().clone().add(new Vector(0, 0, 128)))) {
					Vector position = imageChunk.getVectorTopLeft().clone().add(new Vector(0, 0, 128));
					ChunkHandler.imageChunks.add(new ImageChunk(texture.getName(),
							(ChunkHandler.imageChunks.size() - 1), position, "right"));
					return position;
				} else {
					Vector position = imageChunk.getVectorTopLeft().clone().add(new Vector(-128, 0, 0));
					ChunkHandler.imageChunks.add(
							new ImageChunk(texture.getName(), ChunkHandler.imageChunks.size(), position, "down"));
					return position;
				}
			}
			if (imageChunk.cameFrom == "right") {
				if (!chunkHandler.containsVector(imageChunk.getVectorTopLeft().clone().add(new Vector(128, 0, 0)))) {
					Vector position = imageChunk.getVectorTopLeft().clone().add(new Vector(128, 0, 0));
					ChunkHandler.imageChunks.add(new ImageChunk(texture.getName(),
							(ChunkHandler.imageChunks.size() - 1), position, "up"));
					return position;
				} else {
					Vector position = imageChunk.getVectorTopLeft().clone().add(new Vector(0, 0, 128));
					ChunkHandler.imageChunks.add(
							new ImageChunk(texture.getName(), (ChunkHandler.imageChunks.size()), position, "right"));
					return position;
				}
			}
			if (imageChunk.cameFrom == "up") {
				if (!chunkHandler.containsVector(imageChunk.getVectorTopLeft().clone().add(new Vector(0, 0, -128)))) {
					Vector position = imageChunk.getVectorTopLeft().clone().add(new Vector(0, 0, -128));
					ChunkHandler.imageChunks.add(
							new ImageChunk(texture.getName(), (ChunkHandler.imageChunks.size()), position, "left"));
					return position;
				} else {
					Vector position = imageChunk.getVectorTopLeft().clone().add(new Vector(128, 0, 0));
					ChunkHandler.imageChunks.add(
							new ImageChunk(texture.getName(), (ChunkHandler.imageChunks.size()), position, "up"));
					return position;
				}
			}
			if (imageChunk.cameFrom == "left") {
				if (!chunkHandler.containsVector(imageChunk.getVectorTopLeft().clone().add(new Vector(-128, 0, 0)))) {
					Vector position = imageChunk.getVectorTopLeft().clone().add(new Vector(-128, 0, 0));
					ChunkHandler.imageChunks.add(
							new ImageChunk(texture.getName(), (ChunkHandler.imageChunks.size()), position, "down"));
					return position;
				} else {
					Vector position = imageChunk.getVectorTopLeft().clone().add(new Vector(0, 0, -128));
					ChunkHandler.imageChunks.add(
							new ImageChunk(texture.getName(), (ChunkHandler.imageChunks.size()), position, "left"));
					return position;
				}
			}
		} else {
			Vector position = new Vector(64, 3, -64);
			ChunkHandler.imageChunks
					.add(new ImageChunk(texture.getName(), (ChunkHandler.imageChunks.size()), position, "down"));

			return position;
		}
		return null;

	}

}
