package me.GravityIO.TexturePicker.TextureWorld;

import java.util.List;
import java.util.ListIterator;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.util.Vector;

public class ChunkPlacer {

	// Place a list of materials at the top left of map chunk until x + 128 y + 128
	public void placeBlocks(List<Material> convertedMats, Vector startPos) {
		int sX = startPos.getBlockX() - 128;
		int sY = startPos.getBlockZ();

		ListIterator<Material> it = convertedMats.listIterator(0);

			for (int y = sY; y < (sY + 128); y++) {
				for (int x = sX; x < (sX + 128); x++) {
					new Location(Bukkit.getWorld("TextureWorld"), x, 3, y).getBlock().setType(it.next());
			}
		}
	}

}
