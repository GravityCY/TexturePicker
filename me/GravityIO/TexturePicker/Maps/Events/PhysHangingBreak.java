package me.GravityIO.TexturePicker.Maps.Events;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.ItemFrame;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.hanging.HangingBreakEvent;
import org.bukkit.event.hanging.HangingBreakEvent.RemoveCause;
import org.bukkit.inventory.meta.MapMeta;

import me.GravityIO.TexturePicker.Maps.MapHandler;

public class PhysHangingBreak implements Listener {

	@SuppressWarnings("deprecation")
	@EventHandler
	private void itemFrameBreak(HangingBreakEvent event) {
		if (event.getCause() == RemoveCause.PHYSICS) {
			if (event.getEntity().getType() == EntityType.ITEM_FRAME) {
				ItemFrame itemFrame = (ItemFrame) event.getEntity();
				if (itemFrame.getItem().getType() == Material.FILLED_MAP) {
					MapMeta mapMeta = (MapMeta) itemFrame.getItem().getItemMeta();
					if (MapHandler.containsId(mapMeta.getMapId())) {
						event.setCancelled(true);
						itemFrame.getWorld().dropItem(itemFrame.getLocation(), MapHandler.getMap(mapMeta.getMapId()));
						itemFrame.remove();
					}
				}
			}
		}
	}

}
