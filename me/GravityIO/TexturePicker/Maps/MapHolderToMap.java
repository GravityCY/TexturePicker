package me.GravityIO.TexturePicker.Maps;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.ItemFrame;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.MapMeta;

public class MapHolderToMap {

	@SuppressWarnings("deprecation")
	static public void placeMap(Location blockLoc, BlockFace facing, String texture) {
		ItemFrame itemFrame = (ItemFrame) blockLoc.getWorld().spawn(blockLoc, ItemFrame.class);
		itemFrame.setFacingDirection(facing);
		ItemStack map = new ItemStack(Material.FILLED_MAP);
		MapMeta mapMeta = (MapMeta) map.getItemMeta();
		mapMeta.setMapId(MapHandler.findGetMapId(texture));
		map.setItemMeta(mapMeta);
		itemFrame.setItem(map);
	}

}
