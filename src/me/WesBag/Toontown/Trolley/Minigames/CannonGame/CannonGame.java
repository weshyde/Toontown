package me.WesBag.Toontown.Trolley.Minigames.CannonGame;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import me.WesBag.Toontown.Trolley.Utilities.GameInventorys;
import me.WesBag.Toontown.Trolley.Utilities.GameManager;
import me.WesBag.Toontown.Trolley.Utilities.RewardManager;
import net.md_5.bungee.api.ChatColor;

public class CannonGame {
	
	public static Material[] earnBlocks = {Material.WATER};
	public static List<Location> arenasFree = new ArrayList<Location>();
	public static List<UUID> playersInCG = new ArrayList<UUID>();
	public static int earnCondition = 1;
	public static int gameNum = 1;
	
	private CGListener listener;
	
	public CannonGame() {
		
	}
	
	public Location loadArena(GameManager gameManager, RewardManager rewardManager, List<UUID> players) {
		Location arena = getArena();
		if (arena == null) return null;
		
		listener = new CGListener(gameManager, rewardManager);
		
		int xMove = 5;
		for (UUID pUUID : players) {
			playersInCG.add(pUUID);
			Player p = Bukkit.getPlayer(pUUID);
			Location pL = arena.add(xMove, 0, 0);
			//xMove += 5;
			p.teleport(pL);
			p.getInventory().clear();
			int launchAmount = 1;
			for (int i = 0 ; i < 9; i++) {
				p.getInventory().setItem(i, GameInventorys.createGuiItem(Material.FEATHER, ChatColor.RED + "Launch " + launchAmount + "!", 1));
				launchAmount+= 1;
			}
		}
		
		return arena;
	}
	
	public static Location getArena() {
		Location takenArena = null;
		if (!arenasFree.isEmpty()) {
			takenArena = arenasFree.get(0);
			arenasFree.remove(0);
		}
		return takenArena;
	}
	
	public static void freeArena(Location l) {
		arenasFree.add(l);
	}
	
	public static boolean playerInGame(UUID pUUID) {
		if (playersInCG.contains(pUUID))
			return true;
		return false;
	}
	
}
