package me.WesBag.Toontown.Trolley.Minigames.CannonGame;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import me.WesBag.Toontown.Main;
import me.WesBag.Toontown.SchematicUtilities.SchematicPaster;
import me.WesBag.Toontown.Trolley.Utilities.GameInventorys;
import me.WesBag.Toontown.Trolley.Utilities.NewGameManager;
import net.md_5.bungee.api.ChatColor;

public class NewCannonGame {
	
	public static List<List<Location>> arenas = new ArrayList<>(); //Stored as: 0=Player1 Location, 1=WaterTower Location
	public static Map<Integer, Boolean> freeArenas = new HashMap<>();
	public static List<UUID> playersIn = new ArrayList<UUID>();
	
	private CannonGameListener listener;
	
	public NewCannonGame() {
		
	}
	
	public Location loadArena(NewGameManager gameManager) {
		List<Location> arenaLocations = getArena();
		if (arenaLocations == null) return null;
		
		this.listener = new CannonGameListener(gameManager);
		
		Location playerLocation = arenaLocations.get(0).clone();
		
		for (UUID uuid : gameManager.getPlayers()) {
			playersIn.add(uuid);
			Player p = Bukkit.getPlayer(uuid);
			p.teleport(playerLocation);
			playerLocation.add(5, 0, 0); //Move 5 for next player
			p.getInventory().clear();
			for (int i = 0; i < 9; i++) {
				p.getInventory().setItem(i, GameInventorys.createGuiItem(Material.FEATHER, ChatColor.RED + "Launch " + (i+1), 1));
			}
			File waterTower = new File(Main.getInstance().getDataFolder().getAbsolutePath() + "/schematics/WaterTower.schem");
			Location waterTowerLocation = arenaLocations.get(1);
			SchematicPaster.pasteSchematic2("world", waterTower, waterTowerLocation.getBlockX(), waterTowerLocation.getBlockY(), waterTowerLocation.getBlockZ(), 0, false);
		}
		return arenaLocations.get(0);
	}
	
	private List<Location> getArena() {
		List<Location> arenaLocations = null;
		int index = 0;
		while (index < (freeArenas.size()-1)) { //Loop until free arena is found OR reaches end of arenas without finding one
			if (freeArenas.get(index) == false) {
				arenaLocations = arenas.get(index);
				freeArenas.put(index, true);
				break;
			}
			else {
				index++;
			}
		}
		return arenaLocations;
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
