package me.GravityIO.TexturePicker.TextureWorld;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.bukkit.GameRule;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.WorldType;

import me.GravityIO.TexturePicker.Main;

public class Setup {

	Main main;

	final WorldCreator world = new WorldCreator("TextureWorld").type(WorldType.FLAT).generateStructures(false);

	public Setup(Main main) {
		// TODO Auto-generated constructor stub
		this.main = main;
	}

	public void createTextureWorld() {
		setGamerules(main.getServer().createWorld(world));
	}

	private void setGamerules(World world) {
		world.setGameRule(GameRule.DO_MOB_SPAWNING, false);
		world.setGameRule(GameRule.ANNOUNCE_ADVANCEMENTS, false);
		world.setGameRule(GameRule.COMMAND_BLOCK_OUTPUT, false);
		world.setGameRule(GameRule.DISABLE_RAIDS, true);
		world.setGameRule(GameRule.DO_DAYLIGHT_CYCLE, false);
		world.setGameRule(GameRule.DO_ENTITY_DROPS, false);
		world.setGameRule(GameRule.DO_FIRE_TICK, false);
		world.setGameRule(GameRule.DO_INSOMNIA, false);
		world.setGameRule(GameRule.DO_MOB_LOOT, false);
		world.setGameRule(GameRule.DO_TILE_DROPS, false);
		world.setGameRule(GameRule.DO_TRADER_SPAWNING, false);
		world.setGameRule(GameRule.DO_PATROL_SPAWNING, false);
		world.setGameRule(GameRule.DO_WEATHER_CYCLE, false);
		world.setGameRule(GameRule.FIRE_DAMAGE, false);
		world.setGameRule(GameRule.MOB_GRIEFING, false);
		world.setGameRule(GameRule.RANDOM_TICK_SPEED, 0);
	}

	public boolean createTextureFolder() {
		// TODO Auto-generated method stub
		File textureFolder = new File(main.getDataFolder(), "textures");
		File texture1 = new File(textureFolder, "texture1.png");

		try {
			if (!main.getDataFolder().exists()) {
				main.getDataFolder().mkdir();
				main.getDataFolder().createNewFile();
			}
			if (!textureFolder.exists()) {
				textureFolder.mkdir();
				textureFolder.createNewFile();
			}
			if (!texture1.exists()) {
				BufferedImage img = ImageIO.read(getClass().getClassLoader().getResource("textures/texture1.png"));
				ImageIO.write(img, "png", texture1);
				texture1.createNewFile();
			}
			return true;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}
}
