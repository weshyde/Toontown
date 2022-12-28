package me.WesBag.Toontown.Trolley.Minigames.CatchingGame;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import me.WesBag.Toontown.Trolley.Utilities.GameManager;
import me.WesBag.Toontown.Trolley.Utilities.RewardManager;

public class CatchingGame {
	
	public static List<Location> arenasFree = new ArrayList<>();
	public static List<UUID> playersInCG = new ArrayList<>();
	
	private CatchingGameListener listener;
	
	public CatchingGame() {
		
	}
	
	public Location loadArena(GameManager gameManager, RewardManager rewardManager, List<UUID> players) {
		Location arena = getArena();
		if (arena == null) return null;
		
		listener = new CatchingGameListener(gameManager, rewardManager);
		
		int xMove = 5;
		for (UUID pUUID : players) {
			playersInCG.add(pUUID);
			Player p = Bukkit.getPlayer(pUUID);
			Location pL = arena.add(xMove, 0, 0);
			
			p.teleport(pL);
			p.getInventory().clear();
		}
		
		return arena;
	}
	
	public static Location getArena() {
		Location arenaLocation = null;
		if (!arenasFree.isEmpty()) {
			arenaLocation = arenasFree.get(0);
			arenasFree.remove(0);
		}
		return arenaLocation;
	}
}
