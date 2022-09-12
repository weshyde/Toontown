package me.WesBag.Toontown.Trolley;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import me.WesBag.Toontown.Main;
import me.WesBag.Toontown.Trolley.Utilities.TrolleyCountdown;
import me.WesBag.Toontown.Trolley.Utilities.GameInventorys;
import me.WesBag.Toontown.Trolley.Utilities.GameManager;
import me.WesBag.Toontown.Trolley.Utilities.NextGameCountdown;

public class Trolley {
	
	private static Inventory nextGameInv;
	public static Map<UUID, Trolley> activeTrolleys = new HashMap<UUID, Trolley>();
	//public static ArrayList<Trolley> activeTrolleys = new ArrayList<Trolley>();
	public static ArrayList<UUID> takenTrolleyNpcs = new ArrayList<UUID>();
	public static Map<UUID, Trolley> allTrolleyPlayers = new HashMap<UUID, Trolley>();
	//public static ArrayList<UUID> trolleyPlayers = new ArrayList<UUID>();
	
	private GameManager gameManager;
	private TrolleyCountdown trolleyCountdown;
	private ArrayList<UUID> trolleyPlayers = new ArrayList<UUID>();
	private ArrayList<UUID> nextGamePlayers = new ArrayList<UUID>();
	private NextGameCountdown nextGameTimer;
	
	public Trolley(UUID firstUUID) {
		trolleyPlayers.add(firstUUID);
		
		this.trolleyCountdown = new TrolleyCountdown(gameManager, this, 10, trolleyPlayers);
		this.trolleyCountdown.runTaskTimer(Main.getInstance(), 0, 20);
		
		if (nextGameInv == null) {
			nextGameInv = Bukkit.createInventory(null, 9, ChatColor.LIGHT_PURPLE + "Play Again?");
			nextGameInv.setItem(2, GameInventorys.createGuiItem(Material.GREEN_STAINED_GLASS_PANE, ChatColor.GREEN + "Yes", 1));
			nextGameInv.setItem(6, GameInventorys.createGuiItem(Material.RED_STAINED_GLASS_PANE, ChatColor.RED + "No", 1)); 
		}
	}
	
	public void startGames() {
		this.gameManager = new GameManager(Main.getInstance(), this, trolleyPlayers);
	}
	
	public void startNextGame() {
		if (nextGamePlayers.size() != trolleyPlayers.size()) {
			for (UUID uuid : trolleyPlayers) {
				//if (!nextPlayers.contains(uuid))
					
					//Send players in this if statement by to their respective playground
			}
			trolleyPlayers.clear();
			for (UUID uuid : nextGamePlayers) {
				trolleyPlayers.add(uuid);
			}
		}
		startGames();
	}
	
	public void nextGame(UUID pUUID) {
		nextGamePlayers.add(pUUID);
	}
	
	public static void takeTrolley(UUID npcUUID, Trolley trolley) {
		activeTrolleys.put(npcUUID, trolley);
	}
	
	public static void freeTrolley(UUID npcUUID) {
		if (activeTrolleys.containsKey(npcUUID))
			activeTrolleys.remove(npcUUID);
	}
	
	public static Inventory getNextGameInv() {
		return nextGameInv;
	}
	
	public static boolean checkTrolley(UUID npcUUID) {
		if (activeTrolleys.containsKey(npcUUID))
			return true;
		else
			return false;
	}
	
	public static Trolley getTrolley(UUID npcUUID) {
		if (activeTrolleys.containsKey(npcUUID))
			return activeTrolleys.get(npcUUID);
		else
			return null;
	}
	
	public static Trolley getTrolleyFromPlayer(UUID pUUID) {
		if (allTrolleyPlayers.containsKey(pUUID)) {
			return allTrolleyPlayers.get(pUUID);
		}
		return null;
	}
	
	public int getPlayerAmount() {
		if (trolleyPlayers.isEmpty())
			return 0;
		return trolleyPlayers.size();
	}
	
	public boolean checkPlayer(UUID pUUID) {
		if (trolleyPlayers.contains(pUUID))
			return true;
		else
			return false;
	}
	
	public void addPlayer(UUID pUUID) {
		if (!trolleyPlayers.contains(pUUID)) {
			if (trolleyPlayers.size() < 4)
				trolleyPlayers.add(pUUID);
			else
				Bukkit.getPlayer(pUUID).sendMessage("Sorry, this trolley is full!");
		}
		else
			Bukkit.getPlayer(pUUID).sendMessage("You're already on the trolley!");
	}
	
	public void removePlayer(UUID pUUID) {
		if (trolleyPlayers.contains(pUUID))
			trolleyPlayers.remove(pUUID);
	}
	
	public void preGameRemovePlayer(UUID pUUID) {
		if (trolleyPlayers.contains(pUUID)) {
			trolleyPlayers.remove(pUUID);
			trolleyCountdown.setTime(10);
		}
	}
	
	public void nextGameGUI() {
		this.nextGameTimer = new NextGameCountdown(this);
		this.nextGameTimer.runTaskTimer(Main.getInstance(), 0, 20);
		this.gameManager = null;
		for (UUID pUUID : trolleyPlayers) {
			Player p = Bukkit.getPlayer(pUUID);
			GameInventorys.openInventoryLater(p, nextGameInv);
		}
	}

}
