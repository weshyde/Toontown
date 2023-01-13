package me.WesBag.Toontown.Trolley.Minigames.TagGame;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.AreaEffectCloud;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.WesBag.Toontown.Main;
import me.WesBag.Toontown.Trolley.Minigames.MinigameInterface;
import me.WesBag.Toontown.Trolley.Utilities.GameInventorys;
import me.WesBag.Toontown.Trolley.Utilities.GameManager;
import me.WesBag.Toontown.Trolley.Utilities.GamePrefixes;

public class ToonTag implements MinigameInterface {
	
	public static List<List<Location>> arenas = new ArrayList<>(); //Stored as 0=Player 1 Location, 1=Corner1, 2=Opposite corner2
	public static Map<Integer, Boolean> freeArenas = new HashMap<>();
	public static List<UUID> playersIn = new ArrayList<>();
	private static ItemStack candy;
	
	private GameManager gameManager;
	private int arenaIndex;
	
	private Player it;
	private TagGameCooldownTimer itTimer;
	private Player oldIt;
	private List<ItemStack> candies = new ArrayList<>();
	
	public ToonTag(GameManager gameManager) {
		if (candy == null) {
			candy = new ItemStack(Material.DIAMOND_BLOCK, 1);
			ItemMeta candyMeta = candy.getItemMeta();
			candyMeta.setDisplayName("ToonTag");
			candy.setItemMeta(candyMeta);
		}
		this.gameManager = gameManager;
		loadArena();
	}

	@Override
	public void start() {
		Random r = new Random();
		it = Bukkit.getPlayer(gameManager.getPlayers().get(r.nextInt(gameManager.getPlayers().size())));
		for (int i = 0; i < 9; i++) {
			it.getInventory().setItem(i, GameInventorys.createGuiItem(Material.RED_CONCRETE, ChatColor.RED + "You're it!", 1));
			it.setWalkSpeed(0.25f);
		}
		
		gameManager.sendMessage(GamePrefixes.Trolley + ChatColor.WHITE + " " + it.getName() + " is it!");
		
	}

	@Override
	public void loadArena() {
		List<Location> arenaLocations = findArena();
		if (this.arenaIndex == -1) return;
		
		Location playerLocation = arenaLocations.get(0).clone();
		
		for (UUID uuid : gameManager.getPlayers()) {
			playersIn.add(uuid);
			Player p = Bukkit.getPlayer(uuid);
			p.teleport(playerLocation);
			p.getInventory().clear();
		}
		
		spawnCandies();
	}

	@Override
	public void unloadArena() {
		it.setWalkSpeed(0.2f);
		if (itTimer != null) itTimer.cancel();
		
		//for (ItemStack candy : candies) {
		//	((Entity) candy).remove();
		//}
		
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

	@Override
	public void sendMessage(String msg) {
		// TODO Auto-generated method stub
		
	}
	
	public void makeIt(UUID uuid) {
		it.setWalkSpeed(0.2f);
		it.getInventory().clear();
		oldIt = it;
		gameManager.sendMessage(GamePrefixes.Trolley + ChatColor.WHITE + " " + it.getName() + " is now it!");
		it = Bukkit.getPlayer(uuid);
		it.setWalkSpeed(0.25f);
		for (int i = 0; i < 9; i++) {
			it.getInventory().setItem(i, GameInventorys.createGuiItem(Material.RED_CONCRETE, ChatColor.RED + "You're it!", 1));
		}
		if (itTimer != null) itTimer.cancel();
		itTimer = new TagGameCooldownTimer(this);
		itTimer.runTaskTimer(Main.getInstance(), 0, 20);
	}
	
	public boolean isPlayerSafe(UUID uuid) {
		if (oldIt == null) {
			return false;
		}
		else if (oldIt.getUniqueId() == uuid) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public void removeOldIt() {
		this.oldIt = null;
	}
	
	public boolean isPlayerIt(UUID uuid) {
		if (it == null) {
			return false;
		}
		else if (uuid == it.getUniqueId()) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public void pickupCandy(Player p) {
		gameManager.getRewardManager().addReward(p.getUniqueId(), 2);
		p.sendMessage(GamePrefixes.Trolley + ChatColor.WHITE + " item picked up");
		spawnCandy();
	}
	
	private void spawnCandy() {
		List<Location> arenaLocations = arenas.get(arenaIndex);
		Location l = randomLocationBetweenBounds(arenaLocations.get(1), arenaLocations.get(2));
		l.getWorld().dropItem(l, candy);
	}
	
	private void spawnCandies() {
		List<Location> arenaLocations = arenas.get(arenaIndex);
		
		for (int i = 0; i < 5; i++) {
			Location l = randomLocationBetweenBounds(arenaLocations.get(1), arenaLocations.get(2));
			l.getWorld().dropItem(l, candy);
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
	
	private Location randomLocationBetweenBounds(Location loc1, Location loc2) {
		double minX = Math.min(loc1.getX(), loc2.getX());
        double minZ = Math.min(loc1.getZ(), loc2.getZ());

        double maxX = Math.max(loc1.getX(), loc2.getX());
        double maxZ = Math.max(loc1.getZ(), loc2.getZ());

        return new Location(loc1.getWorld(), randomDouble(minX, maxX), loc1.getY(), randomDouble(minZ, maxZ));
	}
	
	private double randomDouble(double min, double max) {
		return min + ThreadLocalRandom.current().nextDouble(Math.abs(max - min + 1));
	}

}
