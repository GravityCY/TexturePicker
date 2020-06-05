package me.GravityIO.TexturePicker.Maps;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MapHandler {
	static private List<Map> loadedMaps = new ArrayList<Map>();

	public Map createMap(File texture) {
		Map map = new Map(texture);
		loadedMaps.add(map);
		return map;
	}

	public boolean removeMap(String fileName) {
		loadedMaps.remove(getMap(fileName));
		return true;
	}

	public boolean isLoaded(String name) {
		if (getMap(name) != null)
			return true;
		return false;
	}

	public Map getMap(String name) {
		for (Map map : loadedMaps) {
			if (map.getFileName().equalsIgnoreCase(name)) {
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
		return getMap(name).getMapId();
	}

}
