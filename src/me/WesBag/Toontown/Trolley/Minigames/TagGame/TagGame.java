package me.WesBag.Toontown.Trolley.Minigames.TagGame;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import me.WesBag.Toontown.Main;
import me.WesBag.Toontown.Trolley.Utilities.GameInventorys;
import me.WesBag.Toontown.Trolley.Utilities.GameManager;
import me.WesBag.Toontown.Trolley.Utilities.GamePrefixes;
import me.WesBag.Toontown.Trolley.Utilities.RewardManager;

public class TagGame {
	
	public static List<Location> arenasFree = new ArrayList<>();
	public static List<UUID> playersInTG = new ArrayList<>();
	public static List<UUID> itPlayers = new ArrayList<>();
	public static Set<UUID> safePlayers = new HashSet<>();
	
	public Location loadArena(GameManager gameManager, RewardManager rewardManager, List<UUID> players) {
		Location arena = getArena();
		if (arena == null) return null;
		
		//listener = new TagGameListener();
		
		int xMove = 15;
		int yMove = 15;
		Random r = new Random();
		//int taggedPlayer = r.nextInt(playersIn.size());
		for (UUID pUUID : players) {
			playersInTG.add(pUUID);
			Player p = Bukkit.getPlayer(pUUID);
			Location pL = arena.add(xMove, 0, yMove);
			p.teleport(pL);
			p.getInventory().clear();
			//xMove += 15;
			//yMove += 15;
		}
		
		Player taggedPlayer = Bukkit.getPlayer(players.get(r.nextInt(players.size())));
		for (int i = 0; i < 9; i++) {
			taggedPlayer.getInventory().setItem(i, GameInventorys.createGuiItem(Material.RED_WOOL, ChatColor.RED + "You're it!", 1));
		}
		for (UUID pUUID : players) {
			Bukkit.getPlayer(pUUID).sendMessage(GamePrefixes.Trolley + ChatColor.WHITE + " " + taggedPlayer.getName() + " is it!");
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
		if (playersInTG.contains(pUUID)) {
			return true;
		}
		else {
			return false;
		}
	}
	
	
	public static boolean playerIsIt(UUID pUUID) {
		if (itPlayers.contains(pUUID)) {
			return true;
		}
		else {
			return false;
		}
	}
}
