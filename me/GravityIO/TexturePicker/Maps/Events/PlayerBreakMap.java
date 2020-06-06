package me.GravityIO.TexturePicker.Maps.Events;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.ItemFrame;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.hanging.HangingBreakByEntityEvent;
import org.bukkit.inventory.meta.MapMeta;

import me.GravityIO.TexturePicker.Maps.MapHandler;

public class PlayerBreakMap implements Listener {

	MapHandler mapsHandler = new MapHandler();

	@SuppressWarnings("deprecation")
	@EventHandler
	public void onBreakMap(EntityDamageByEntityEvent event) {
		if (event.getEntity() instanceof ItemFrame) {
			ItemFrame itemFrame = (ItemFrame) event.getEntity();
			if (itemFrame.getItem().getType() == Material.FILLED_MAP) {
				MapMeta map = (MapMeta) itemFrame.getItem().getItemMeta();
				if (MapHandler.containsId(map.getMapId())) {
					if (event.getDamager() instanceof Player) {
						Player player = (Player) event.getDamager();
						if (player.getGameMode() == GameMode.SURVIVAL) {
							itemFrame.getWorld().dropItem(itemFrame.getLocation(), MapHandler.getMap(map.getMapId()));
						}
						itemFrame.remove();
					}
				}
			}
		}
	}

	@EventHandler
	public void onBreakMapFrame(HangingBreakByEntityEvent event) {
		new EntityDamageByEntityEvent(event.getRemover(), event.getEntity(), DamageCause.ENTITY_ATTACK, 0);
	}

}
