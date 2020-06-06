package me.GravityIO.TexturePicker.Maps;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;

public class MapHandler {
	static private List<Map> loadedMaps = new ArrayList<Map>();

	public Map createMap(File texture) {
		Map map = new Map(texture);
		loadedMaps.add(map);
		return map;
	}

	public boolean removeMap(String name) {
		name = ChatColor.stripColor(name);
		loadedMaps.remove(getMap(name));
		return true;
	}

	public boolean contains(String name) {
		name = ChatColor.stripColor(name);
		if (getMap(name) != null)
			return true;
		return false;
	}

	public boolean containsId(int id) {
		for (Map map : loadedMaps) {
			if (map.getMapId() == id) {
				return true;
			}
		}
		return false;
	}

	public Map getMap(String name) {
		name = ChatColor.stripColor(name);
		for (Map map : loadedMaps) {
			if (map.getFileName().equalsIgnoreCase(name)) {
				return map;
			}
		}
		return null;
	}

	public Map getMap(int id) {
		for (Map map : loadedMaps) {
			if (map.getMapId() == id) {
				return map;
			}
		}
		return null;
	}

	public int getTotalLoadedMaps() {
		return loadedMaps.size();
	}

	public List<Map> getLoadedMaps() {
		return loadedMaps;
	}

	public List<String> getLoadedMapNames() {
		List<String> mapNames = new ArrayList<String>();
		for (Map map : loadedMaps) {
			mapNames.add(map.getFileName());
		}
		return mapNames;
	}

	public int findGetMapId(String name) {
		name = ChatColor.stripColor(name);
		return getMap(name).getMapId();
	}

}
