package me.WesBag.Toontown.Trolley.Minigames.IceSlide;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Boat;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import me.WesBag.Toontown.Main;
import me.WesBag.Toontown.Trolley.Minigames.CannonGame.CGListener;
import me.WesBag.Toontown.Trolley.Utilities.GameInventorys;
import me.WesBag.Toontown.Trolley.Utilities.GameManager;
import me.WesBag.Toontown.Trolley.Utilities.RewardManager;
import net.md_5.bungee.api.ChatColor;

public class IceSlide {
	
	public static List<Location> arenasFree = new ArrayList<>();
	public static List<UUID> playersIn = new ArrayList<>();
	
	private ISListener listener;
	private ISTimer roundTimer;
	private List<Boat> playerBoats = new ArrayList<>();
	
	public Location loadArena(GameManager gameManager, RewardManager rewardManager, List<UUID> players) {
		Location arena = getArena();
		if (arena == null) return null;
		
		this.listener = new ISListener(gameManager, rewardManager);
		
		int xMove = 0;
		for (UUID pUUID : players) {
			
			
			playersIn.add(pUUID);
			Player p = Bukkit.getPlayer(pUUID);
		
			
			Location pL = arena.add(xMove, 0, 0);
			xMove += 5;
			p.teleport(pL);
			p.getInventory().clear();
			int launchAmount = 1;
			for (int i = 0; i < 9; i++) {
				p.getInventory().setItem(i, GameInventorys.createGuiItem(Material.ICE, ChatColor.RED + "Launch " + launchAmount + "!", 1));
			}
			
			Boat tmpBoat = (Boat) p.getWorld().spawnEntity(p.getLocation(), EntityType.BOAT);
			playerBoats.add(tmpBoat);
			tmpBoat.addPassenger(p);
		}
		nextRound(true);
		return null;
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
		if (playersIn.contains(pUUID))
			return true;
		return false;
	}
	
	public ISListener getListener() {
		return listener;
	}
	
	public void finishGame() {
		for (Boat b : playerBoats) {
			b.remove();
		}
	}
	
	public boolean checkPlayersDone() {
		if (roundTimer != null)
			if (listener.playersDoneSize() == playersIn.size())
				return true;
		return false;
	}
	
	public void nextRound(boolean firstRun) {
		this.roundTimer = new ISTimer(this);
		
		if (firstRun)
			this.roundTimer.runTaskTimer(Main.getInstance(), 100, 20);
		else
			this.roundTimer.runTaskTimer(Main.getInstance(), 10, 20);
	}
	

}
