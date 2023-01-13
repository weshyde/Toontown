package me.WesBag.Toontown.Trolley.Minigames.CannonGame;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import me.WesBag.Toontown.Main;
import me.WesBag.Toontown.SchematicUtilities.SchematicPaster;
import me.WesBag.Toontown.Trolley.Minigames.Minigame;
import me.WesBag.Toontown.Trolley.Minigames.MinigameInterface;
import me.WesBag.Toontown.Trolley.Utilities.GameInventorys;
import me.WesBag.Toontown.Trolley.Utilities.GameManager;
import me.WesBag.Toontown.Trolley.Utilities.NewMinigameClock;
import net.md_5.bungee.api.ChatColor;

public class CannonGame implements MinigameInterface {
	
	//public static List<List<Location>> arenas = new ArrayList<>(); //Stored as: 0=Player1 Location, 1=WaterTower Location
	//public static Map<Integer, Boolean> freeArenas = new HashMap<>();
	//public static List<UUID> playersIn = new ArrayList<>();
	public static List<List<Location>> arenas = new ArrayList<>();
	public static Map<Integer, Boolean> freeArenas = new HashMap<>();
	public static List<UUID> playersIn = new ArrayList<>();
	
	private GameManager gameManager;
	private int arenaIndex;
	
	
	private CannonGameListener listener;
	private int XmoveAmt;
	
	public CannonGame(GameManager gameManager) {
		this.gameManager = gameManager;
		loadArena();
	}
	
	@Override
	public void start() {
		NewMinigameClock gameClock = new NewMinigameClock(gameManager);
		gameClock.runTaskTimer(Main.getInstance(), 0, 20);
		gameManager.passClock(gameClock);
	}
	
	@Override
	public void loadArena() {
		List<Location> arenaLocations = findArena();
		if (this.arenaIndex == -1) return;
		
		this.listener = new CannonGameListener(gameManager, this);
		
		Random r = new Random();
		XmoveAmt = r.nextInt(14); //Change depending if play area increases or decreases
		File waterTower = new File(Main.getInstance().getDataFolder().getAbsolutePath() + "/schematics/WaterTower.schem");
		Location waterTowerLocation = arenaLocations.get(1);
		SchematicPaster.pasteSchematic2("world", waterTower, (waterTowerLocation.getBlockX()+XmoveAmt), waterTowerLocation.getBlockY(), waterTowerLocation.getBlockZ(), 0, false);
		
		Location playerLocation = arenaLocations.get(0).clone();
		
		for (UUID uuid : gameManager.getPlayers()) {
			playersIn.add(uuid);
			Player p = Bukkit.getPlayer(uuid);
			p.teleport(playerLocation);
			playerLocation.add(5, 0, 0); //Move 5 for next player
			p.getInventory().clear();
			for (int i = 0; i < 9; i++) {
				p.getInventory().setItem(i, GameInventorys.createGuiItem(Material.FEATHER, ChatColor.RED + "Launch " + (i+1), (i+1)));
			}
		}
	}
	
	@Override
	public void unloadArena() {
		File clearWaterTower = new File(Main.getInstance().getDataFolder().getAbsolutePath() + "/schematics/WaterTowerClear.schem");
		Location clearLocation = arenas.get(arenaIndex).get(1);
		SchematicPaster.pasteSchematic2("world", clearWaterTower, (clearLocation.getBlockX()+XmoveAmt), clearLocation.getBlockY(), clearLocation.getBlockZ(), 0, false);
		
		for (UUID uuid : gameManager.getPlayers()) {
			playersIn.remove(uuid);
			Player p = Bukkit.getPlayer(uuid);
			p.getInventory().clear();
		}
		
		freeArenas.put(arenaIndex, false); //Free up arena
	}
	
	@Override
	public void sendMessage(String msg) {
		for (UUID uuid : playersIn) {
			Bukkit.getPlayer(uuid).sendMessage(msg);
		}
	}
	
	@Override
	public Location getArena() {
		if (this.arenaIndex > 0) {
			return arenas.get(arenaIndex).get(0);
		}
		else {
			return null;
		}
	}
	
	private List<Location> findArena() {
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
