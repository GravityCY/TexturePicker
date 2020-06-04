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
		int sX = startPos.getBlockX();
		int height = 3;
		int sY = startPos.getBlockZ();

		ListIterator<Material> it = convertedMats.listIterator(0);

		for (int x = sX; x > (sX - 128); x--) {
			for (int y = sX; y < (sX + 128); y++) {
				new Location(Bukkit.getWorld("TextureWorld"), x, height, y).getBlock().setType(it.next());
			}
		}
		
		System.out.println("Placed at " + sX + " " + height + " " + sY);
	}

}
