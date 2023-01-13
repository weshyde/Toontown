package me.WesBag.Toontown.Trolley.Minigames;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;

import me.WesBag.Toontown.Trolley.Utilities.GameManager;

public class Minigame {
	
	public static List<List<Location>> arenas = new ArrayList<>();
	public static Map<Integer, Boolean> freeArenas = new HashMap<>();
	public static List<UUID> playersIn = new ArrayList<>();
	
	protected GameManager gameManager;
	protected int arenaIndex;
	
	public void start() {
		
	}
	
	public void loadArena() {
		
	}
	
	public void unloadArena() {
		
	}
	
	//public void nextRound() {
		
	//}
	
	public void sendMessage(String msg) {
		for (UUID uuid : playersIn) {
			Bukkit.getPlayer(uuid).sendMessage(msg);
		}
	}
	
	protected List<Location> findArena() {
		List<Location> arenaLocations = null;
		int index = 0;
		while (index < freeArenas.size()) { //Loop until free arena is found OR reaches end of arenas without finding one
			if (freeArenas.get(index) == false) {
				arenaLocations = arenas.get(index);
				freeArenas.put(index, true);
				break;
			}
			else {
				index++;
			}
		}
		if (arenaLocations == null) {
			this.arenaIndex = -1;
		}
		else {
			this.arenaIndex = index;
		}
		return arenaLocations;
	}
	
	public Location getArena() {
		if (this.arenaIndex > 0) {
			return arenas.get(arenaIndex).get(0);
		}
		else {
			return null;
		}
	}
	
	public static void freeArena(int index) {
		freeArenas.put(index, false);
	}
	
	public static boolean isPlaying(UUID uuid) {
		if (playersIn.contains(uuid)) {
			return true;
		}
		else {
			return false;
		}
	}
}
