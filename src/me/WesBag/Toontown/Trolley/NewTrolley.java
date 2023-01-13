package me.WesBag.Toontown.Trolley;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import me.WesBag.Toontown.Main;
import me.WesBag.Toontown.BattleCore.Toons.ToonsController;
import me.WesBag.Toontown.Trolley.Utilities.GameInventorys;
import me.WesBag.Toontown.Trolley.Utilities.GameManager;
import me.WesBag.Toontown.Trolley.Utilities.NewTrolleyCountdown;
import me.WesBag.Toontown.Trolley.Utilities.NextGameCountdown;

public class NewTrolley {
	
	public static Map<UUID, NewTrolley> activeTrolleys = new HashMap<>(); //NPC mapping to Trolley
	public static Map<UUID, NewTrolley> trolleyPlayers = new HashMap<>(); //Player mapping to trolley
	private static Inventory nextGameInventory;
	
	private List<UUID> players = new ArrayList<>();
	private List<UUID> nextGamePlayers = new ArrayList<>();
	private int playground;
	private GameManager gameManager;
	private NewTrolleyCountdown countdown;
	private NextGameCountdown nextGameCountdown;
	
	public NewTrolley(UUID playerOne) {
		players.add(playerOne);
		trolleyPlayers.put(playerOne, this);
		playground = (ToonsController.getToon(playerOne).getRegionLocationID()/10);
		this.countdown = new NewTrolleyCountdown(this, playerOne);
		this.countdown.runTaskTimer(Main.getInstance(), 0, 20);
		
		if (nextGameInventory == null) {
			nextGameInventory = Bukkit.createInventory(null, 9, ChatColor.LIGHT_PURPLE + "Play Again?");
			nextGameInventory.setItem(2, GameInventorys.createGuiItem(Material.GREEN_STAINED_GLASS_PANE, ChatColor.GREEN + "Yes", 1));
			nextGameInventory.setItem(6, GameInventorys.createGuiItem(Material.RED_STAINED_GLASS_PANE, ChatColor.RED + "No", 1));
		}
	}
	
	public void start() {
		this.gameManager = new GameManager(Main.getInstance(), this, players, 0/*playground*/); //Eventaully change back to playground once Trolley npc is within playground
	}
	
	public void startNextGame() {
		System.out.println("Start next game called");
		if (nextGamePlayers.isEmpty()) {
			System.out.println("No one playing again - Ending");
			for (UUID uuid : activeTrolleys.keySet()) {
				if (activeTrolleys.get(uuid) == this) {
					activeTrolleys.remove(uuid);
					return;
				}
			}
		}
		if (nextGamePlayers.size() != players.size()) {
			players.removeAll(nextGamePlayers);
			for (UUID uuid : players) {
				//Send back to playground
				trolleyPlayers.remove(uuid);
			}
			players.clear();
			players.addAll(nextGamePlayers);
			nextGamePlayers.clear();
		}
		start();
	}
	
	public boolean isFull() {
		if (players.size() == 4) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public void addPlayer(UUID uuid) {
		players.add(uuid);
		countdown.addPlayer(Bukkit.getPlayer(uuid));
		trolleyPlayers.put(uuid, this);
	}
	
	public void removePlayer(UUID uuid) {
		players.remove(uuid);
	}
	
	public boolean isPlayerIn(UUID uuid) {
		if (players.contains(uuid)) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public void addNextGame(UUID uuid) {
		this.nextGamePlayers.add(uuid);
	}
	
	public void nextGameGUI() {
		this.nextGameCountdown = new NextGameCountdown(this);
		this.nextGameCountdown.runTaskTimer(Main.getInstance(), 0, 20);
		GameManager.allGameManagers.remove(this.gameManager);
		this.gameManager = null;
		for (UUID uuid : players) {
			Player p = Bukkit.getPlayer(uuid);
			GameInventorys.openInventoryLater(p, nextGameInventory);
		}
	}
	
	public static void takeTrolley(UUID uuid, NewTrolley trolley) {
		activeTrolleys.put(uuid, trolley);
	}
	
	public static NewTrolley getActiveTrolley(UUID uuid) {
		return activeTrolleys.get(uuid);
	}
	
	public static boolean emptyTrolley(UUID uuid) {
		if (activeTrolleys.containsKey(uuid)) {
			return false;
		}
		else {
			return true;
		}
	}
	
	public static NewTrolley getTrolley(UUID uuid) {
		if (trolleyPlayers.containsKey(uuid)) {
			return trolleyPlayers.get(uuid);
		}
		else {
			return null;
		}
	}
	
	public static Inventory getNextGameInventory() {
		return nextGameInventory;
	}

}
