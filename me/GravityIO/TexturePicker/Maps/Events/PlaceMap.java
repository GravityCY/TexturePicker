package me.GravityIO.TexturePicker.Maps.Events;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.ItemFrame;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.MapMeta;
import org.bukkit.scheduler.BukkitRunnable;

import me.GravityIO.TexturePicker.Main;
import me.GravityIO.TexturePicker.Maps.MapHandler;

public class PlaceMap implements Listener {

	MapHandler mapsHandler = new MapHandler();
	Main main;

	public PlaceMap(Main main) {
		this.main = main;
	}

	@SuppressWarnings("deprecation")
	@EventHandler
	private void onMapPlace(PlayerInteractEvent event) {
		if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
			Block clickedBlock = event.getClickedBlock();

			if (event.getHand() == EquipmentSlot.HAND) {
				if (event.getPlayer().getInventory().getItemInMainHand() != null) {
					if (event.getPlayer().getInventory().getItemInMainHand().getType() != Material.AIR) {
						ItemStack itemInHand = event.getPlayer().getInventory().getItemInMainHand();

						if (mapsHandler.contains(itemInHand.getItemMeta().getDisplayName())) {
							event.setCancelled(true);

							ItemStack mapHolder = itemInHand;
							Location blockLoc = clickedBlock.getRelative(event.getBlockFace()).getLocation();
							new BukkitRunnable() {

								@Override
								public void run() {
									ItemFrame itemFrame = (ItemFrame) blockLoc.getWorld().spawn(blockLoc,
											ItemFrame.class);
									itemFrame.setFacingDirection(event.getBlockFace());
									ItemStack map = new ItemStack(Material.FILLED_MAP);
									MapMeta mapMeta = (MapMeta) map.getItemMeta();
									mapMeta.setMapId(
											mapsHandler.findGetMapId(mapHolder.getItemMeta().getDisplayName()));
									map.setItemMeta(mapMeta);
									itemFrame.setItem(map);

									if (event.getPlayer().getGameMode() == GameMode.SURVIVAL)
										itemInHand.setAmount(itemInHand.getAmount() - 1);
									event.getPlayer().updateInventory();

								}
							}.runTaskLater(main, 1);
						}
					}
				}
			}
		}
	}
}
