package me.GravityIO.TexturePicker.TextureWorld;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;

public class ChunkBreaker {
	public void unloadImage(File texture) {

		int currentChunk = chunkData.getInt("chunks#" + texture.getName());
		chunkData.set("chunks#" + texture.getName(), null);
		chunkData.set("totalchunks", chunkData.getInt("totalchunks") - 1);
		for (int x = 0; x < 128; x++) {
			for (int y = 0; y < 128; y++) {
				new Location(Bukkit.getWorld("TextureWorld"), (x - 64) + 128 * currentChunk, 3, y - 64).getBlock()
						.setType(Material.GRASS_BLOCK);
			}
		}

		try {
			chunkData.save(chunkDataFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("Finished unloading");
	}
}
