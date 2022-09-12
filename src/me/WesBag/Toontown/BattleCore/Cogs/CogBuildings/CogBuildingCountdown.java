package me.WesBag.TTCore.BattleMenu.Cogs.CogBuildings;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.scheduler.BukkitRunnable;

import me.WesBag.TTCore.BattleMenu.Cogs.CogsController;
import me.WesBag.TTCore.BattleMenu.Cogs.CogTrait.CogBuildingTrait;
import net.citizensnpcs.api.npc.NPC;
import net.md_5.bungee.api.ChatColor;

public class CogBuildingCountdown extends BukkitRunnable {
	
	public static List<UUID> inCountdownPlayers = new ArrayList<>();
	
	private List<UUID> players = new ArrayList<>();
	private NPC buildingNPC;
	private int time = 11; //Make time = 21 eventually
	private int levels;
	private int difficulty;
	private int suitNum;
	private CogBuildingCountdown countdown;
	private String buildingName;
	private Location location;
	private Location leaveLocation;
	private boolean buildingOver = false;
	private boolean timeResetAlready = false;
	
	//public CogBuildingCountdown(UUID player, NPC cogBuildingNPC, Location l) {
	//	this.countdown = this;
	//	this.players.add(player);
	//	this.buildingNPC = cogBuildingNPC;
	//	this.buildingName = cogBuildingNPC.getFullName();
		//buildingNPC.getStoredLocation().get
		//buildingNPC.getStoredLocation().setYaw(0);
	//	this.location = l;
	//	this.difficulty = buildingNPC.getOrAddTrait(CogBuildingTrait.class).getDifficulty();
	//}
	
	//New constructor for changing from starting Cog buildings by right clicking NPCs to walking onto iron blocks
	public CogBuildingCountdown(UUID player, NPC npc, Location l) {
		this.countdown = this;
		this.buildingNPC = npc;
		this.buildingName = npc.getFullName();
		this.location = l;
		this.players.add(player);
		this.difficulty = buildingNPC.getOrAddTrait(CogBuildingTrait.class).getDifficulty();
	}

	@Override
	public void run() {
		time--;
		if (time <= 0) {
			sendAllMessage("Starting building!");
			startBuilding();
			//CogBuildingController.buildingCountdowns.remove(buildingNPC.getUniqueId());
			cancel();
		}
		
		else if (time % 5 == 0 || time <= 5)
			sendAllMessage("Starting in " + time); 
		
	}
	
	public void sendAllMessage(String str) {
		for (UUID pUUID : players) {
			Bukkit.getPlayer(pUUID).sendMessage(ChatColor.GRAY + "[Building] " + ChatColor.WHITE + str);
		}
	}
	
	public void resetTime() {
		if (!timeResetAlready && !buildingOver) {
			time = 20;
			timeResetAlready = true;
		}
	}
	
	public boolean containsPlayer(UUID p) {
		if (players.contains(p))
			return true;
		else
			return false;
	}
	
	public int getPlayerAmount() {
		return players.size();
	}
	
	public NPC getNPC() {
		return buildingNPC;
	}
	
	public static void addCogBuildingPlayer(UUID p) {
		inCountdownPlayers.add(p);
	}
	
	public static void removeCogBuildingPlayer(UUID p) {
		if (inCountdownPlayers.contains(p))
			inCountdownPlayers.remove(p);
	}
	
	public static boolean playerInCogBuilding(UUID p) {
		if (inCountdownPlayers.contains(p))
			return true;
		return false;
	}
	
	public void addPlayer(UUID p) {
		players.add(p);
	}
	
	public void removePlayer(UUID p) {
		if (players.contains(p))
			players.remove(p);
		removeCogBuildingPlayer(p);
		if (players.size() < 1) {
			cancel();
			//CogBuildingController.buildingCountdowns.remove(buildingNPC.getUniqueId());
			buildingOver = true;
		}
	}
	
	private void startBuilding() {
		for (UUID pUUID : players) {
			Bukkit.getPlayer(pUUID).getInventory().clear();
			inCountdownPlayers.remove(pUUID);
		}
		String levelStr = buildingName.replaceAll("[^0-9]", "");
		this.levels = Integer.parseInt(levelStr);
		for (int i = 0; i < 4; i++ )
			if (buildingName.contains(CogsController.cogSuits[i]))
				this.suitNum = i;
		System.out.println("------Players size: " + players.size());
		CogBuilding cBuilding = new CogBuilding(players, levels, difficulty, suitNum, location, buildingNPC);
		//CogBuildingController.cogBuildings2.put(location.clone(), cBuilding);
		//CogBuildingController.cogBuildings.put(buildingNPC.getUniqueId(), cBuilding);
		//CogBuildingController.cogBuildingElevators.put(cBuilding.getElevatorLocation(), cBuilding);
	}

}
