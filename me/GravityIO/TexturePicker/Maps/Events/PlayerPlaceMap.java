package me.GravityIO.TexturePicker.Maps.Events;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import me.GravityIO.TexturePicker.Main;
import me.GravityIO.TexturePicker.Maps.MapHandler;
import me.GravityIO.TexturePicker.Maps.MapHolderToMap;

public class PlayerPlaceMap implements Listener {

	Main main;

	public PlayerPlaceMap(Main main) {
		this.main = main;
	}

	@EventHandler
	private void onMapPlace(PlayerInteractEvent event) {
		if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
			Block clickedBlock = event.getClickedBlock();

			if (event.getHand() == EquipmentSlot.HAND) {
				if (event.getPlayer().getInventory().getItemInMainHand() != null) {
					if (event.getPlayer().getInventory().getItemInMainHand().getType() != Material.AIR) {
						ItemStack itemInHand = event.getPlayer().getInventory().getItemInMainHand();

						if (MapHandler.contains(itemInHand.getItemMeta().getDisplayName())) {
							event.setCancelled(true);

							Location blockLoc = clickedBlock.getRelative(event.getBlockFace()).getLocation();
							new BukkitRunnable() {

								@Override
								public void run() {
									try {
										MapHolderToMap.placeMap(blockLoc, event.getBlockFace(),
												itemInHand.getItemMeta().getDisplayName());
										if (event.getPlayer().getGameMode() == GameMode.SURVIVAL)
											itemInHand.setAmount(itemInHand.getAmount() - 1);
										event.getPlayer().updateInventory();
									} catch (IllegalArgumentException e) {
									}

								}
							}.runTaskLater(main, 1);
						}
					}
				}
			}
		}
	}

}
