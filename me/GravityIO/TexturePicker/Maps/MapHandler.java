package me.GravityIO.TexturePicker.Maps;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;

public class MapHandler {
	static private List<Map> loadedMaps = new ArrayList<Map>();

	static public Map createMap(File texture, String fileTree) {
		Map map = new Map(texture, fileTree);
		loadedMaps.add(map);
		return map;
	}

	static public Map loadMapId(File texture, int mapId, String fileTree) {
		Map map = new Map(texture, mapId, fileTree);
		loadedMaps.add(map);
		return map;
	}

	static public boolean removeMap(String name) {
		name = ChatColor.stripColor(name);
		loadedMaps.remove(getMap(name));
		return true;
	}

	static public boolean contains(String name) {
		name = ChatColor.stripColor(name);
		if (getMap(name) != null)
			return true;
		return false;
	}

	static public boolean containsId(int id) {
		for (Map map : loadedMaps) {
			if (map.getMapId() == id) {
				return true;
			}
		}
		return false;
	}

	static public Map getMap(String name) {
		name = ChatColor.stripColor(name);
		for (Map map : loadedMaps) {
			if (map.getFileName().equalsIgnoreCase(name)) {
				return map;
			}
		}
		return null;
	}

	static public Map getMap(int id) {
		for (Map map : loadedMaps) {
			if (map.getMapId() == id) {
				return map;
			}
		}
		return null;
	}

	static public int getTotalLoadedMaps() {
		return loadedMaps.size();
	}

	static public List<Map> getLoadedMaps() {
		return loadedMaps;
	}

	static public List<String> getLoadedMapNames() {
		List<String> mapNames = new ArrayList<String>();
		for (Map map : loadedMaps) {
			mapNames.add(map.getFileName());
		}
		return mapNames;
	}

	static public int findGetMapId(String name) {
		name = ChatColor.stripColor(name);
		return getMap(name).getMapId();
	}

}
