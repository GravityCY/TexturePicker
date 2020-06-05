package me.GravityIO.TexturePicker.TextureWorld;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.util.Vector;

public class ChunkBreaker {

	ChunkHandler chunkHandler;

	public ChunkBreaker(ChunkHandler chunkHandler) {
		this.chunkHandler = chunkHandler;
	}

	public void unloadImage(String name) {
		Vector position = chunkHandler.findGetVector(name);
		int sX = position.getBlockX();
		int sY = position.getBlockZ();

		for (int x = sX; x > (sX - 128); x--) {
			for (int y = sY; y < (sY + 128); y++) {
				new Location(Bukkit.getWorld("TextureWorld"), x, 3, y).getBlock().setType(Material.GRASS_BLOCK);
			}
		}
		chunkHandler.findRemove(name);
	}
}
