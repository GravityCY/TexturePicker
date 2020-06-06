package me.GravityIO.TexturePicker.Maps.Events;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.ItemFrame;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.meta.MapMeta;
import org.bukkit.util.RayTraceResult;

import me.GravityIO.TexturePicker.Maps.Map;
import me.GravityIO.TexturePicker.Maps.MapHandler;

public class PlayerCreativeInteract implements Listener {

	MapHandler mapHandler = new MapHandler();

	@SuppressWarnings("deprecation")
	@EventHandler
	private void OnMiddleClick(InventoryClickEvent event) {
		Player player = (Player) event.getWhoClicked();
		if (player.getGameMode() == GameMode.CREATIVE) {
			if (event.getClick().isCreativeAction()) {
				if (event.getAction() == InventoryAction.PLACE_ALL) {
					if (event.getCursor() != null && event.getCursor().getType() == Material.FILLED_MAP) {
						RayTraceResult result = player.getWorld().rayTraceEntities(player.getEyeLocation(),
								player.getLocation().getDirection(), 6, p -> p instanceof ItemFrame);

						if (result.getHitEntity() != null) {
							MapMeta map = (MapMeta) ((ItemFrame) result.getHitEntity()).getItem().getItemMeta();
							if (MapHandler.containsId(map.getMapId())) {
								event.setCancelled(true);
								Map mapHolder = MapHandler.getMap(map.getMapId());
								if (!player.getInventory().contains(mapHolder)) {
									if (player.getInventory().firstEmpty() <= 8) {
										player.getInventory().addItem(mapHolder);
									}
								} else {
									if (player.getInventory().first(mapHolder) <= 8)
										player.getInventory().setHeldItemSlot(player.getInventory().first(mapHolder));
								}
							}
						}
					}
				}
			}
		}
	}
}
