package me.WesBag.Toontown.Files;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.WesBag.Toontown.Main;
import me.WesBag.Toontown.BattleCore.BattleCore;
import me.WesBag.Toontown.BattleCore.Cogs.Cog;
import me.WesBag.Toontown.BattleCore.Cogs.CogBuildings.CogBuilding;
import me.WesBag.Toontown.BattleCore.Cogs.CogBuildings.CogBuildingController;
import me.WesBag.Toontown.BattleCore.Gags.Gag;
import me.WesBag.Toontown.BattleCore.Toons.Toon;
import me.WesBag.Toontown.BattleCore.Toons.ToonsController;
import net.citizensnpcs.api.npc.NPC;
import net.md_5.bungee.api.ChatColor;

import java.util.List;
import java.util.Map;
import java.util.Random;

public class BattleData {
	private Main main;
	private List<UUID> players = new ArrayList<>();
	private List<UUID> joiningPlayerQueue = new ArrayList<>();
	private List<UUID> joiningCogQueue = new ArrayList<>();
	private Map<UUID, Toon> toons = new HashMap<>();
	private List<UUID> npcs = new ArrayList<UUID>();
	private Map<UUID, Cog> cogs = new HashMap<UUID, Cog>();
	private Map<UUID, Gag> gags = new HashMap<>();
	private List<Cog> deadCogs = new ArrayList<>();
	//private List<String> bdDeadCogs2 = new ArrayList<String>(); //Added 1/27/22 to replace HashMap of dead cogs for passing cogs to the BattleFinishEvent
	private Map<UUID, Integer> playersTargets = new HashMap<UUID, Integer>();
	//private Map<Integer, Integer> trackExp = new HashMap<>();
	private Map<UUID, Map<Integer, Integer>> exps = new HashMap<>();
	private Location key;
	private Map<UUID, Integer> lureRounds = new HashMap<>();
	private Map<Gag, UUID> trapOwners = new HashMap<>();
	private Map<UUID, Gag> placedTraps = new HashMap<>();
	private Map<UUID, Block> trapBlocks = new HashMap<>();
	private Map<UUID, String> traps = new HashMap<>();
	private Map<UUID, Boolean> playersGagSelected = new HashMap<>();
	private Map<UUID, Boolean> playersTargetSelected = new HashMap<>();
	private Map<UUID, Inventory> playersGagLobbyInv = new HashMap<>();
	private Map<UUID, Inventory> playersTargetInv = new HashMap<>();
	private boolean battleOver = false;
	private boolean specialMode = false;
	private boolean playersTurn = true;
	
	private Location cogDefaultLoc;
	private Location playerDefaultLoc;
	private int streetLocID; //Only used when its a street battle to identity street anchor location
	//private float playerYaw, playerPitch; //Default yaw and pitch for teleporting players
	private float cogYaw, cogPitch; //Needed? TBD 9-15-22
	
	private CogBuilding cogBuilding = null;

	public BattleData(Main mn, List<UUID> inputPlayers, List<UUID> inputNpcs, Location tempKey) {
		this.main = mn;
		key = tempKey;
		players.addAll(inputPlayers);
		npcs.addAll(inputNpcs);
		for (UUID pUUID : inputPlayers) {
			Toon tempToon = ToonsController.getToon(pUUID);
			toons.put(pUUID, tempToon);
		}
		initBattleInventories();
	}
	//Constructor for changing startStreetBattle to only accept a uuid, not a list of uuids. Added new anchorLoc 9-15-22 for battle positioning
	public BattleData(Main main, UUID playerUUID, UUID npcUUID, Location key, Location anchorLocation) {
		this.main = main;
		this.key = key;
		this.cogDefaultLoc = anchorLocation;
		players.add(playerUUID);
		npcs.add(npcUUID);
		Toon toon = ToonsController.getToon(playerUUID);
		toons.put(playerUUID, toon);
		initBattleInventories();
	}
	
	//----WARNING: This constructor will eventually need to take anchor location as well----
	public BattleData(Main mn, List<UUID> players, List<UUID> npcs, Location key, boolean specialMode, CogBuilding cBuilding) {
		this.specialMode = specialMode;
		this.cogBuilding = cBuilding;
		
		this.main = mn;
		this.key = key;
		players.addAll(players);
		npcs.addAll(npcs);
		for (UUID pUUID : players) {
			Toon toon = ToonsController.getToon(pUUID);
			toons.put(pUUID, toon);
		}
		initBattleInventories();
	}
	
	public void initBattleInventories() {
		for (UUID pUUID : players) {
			//Target Inventory
			Inventory targetInv = Bukkit.createInventory(null, 9, ChatColor.GOLD + "" + "Choose Target");
			for (int i = 0; i < 9; i++) {
				targetInv.setItem(i, createGuiItem(Material.WHITE_STAINED_GLASS_PANE, " "));
			}
			
			for (int i = 0; i < npcs.size(); i++) {
				targetInv.setItem((((i + 1) * 2) - 1), createGuiItem(Material.RED_STAINED_GLASS_PANE, ChatColor.RED + "" + "Attack " + (i + 1)));
			}
			playersTargetInv.put(pUUID, targetInv);
			
			//Lobby Inv
			Inventory lobbyInv = Bukkit.createInventory(null, 18, ChatColor.GOLD + "" + "Waiting for other players");
			for (int i = 0; i < 18; i++) {
				lobbyInv.setItem(i, createGuiItem(Material.WHITE_STAINED_GLASS_PANE, ChatColor.WHITE + " "));
			}
			int looped = 0;
			for (UUID pUUID2 : players) {
				Player tempP = Bukkit.getPlayer(pUUID2);
				Gag tempGag = getGag(pUUID2);
				int tempTarget = getPlayerTarget(pUUID2);
				if (playerDoneChoosing(pUUID2))
					lobbyInv.setItem((((looped + 1) * 2) - 1), createGuiItem(Material.GREEN_STAINED_GLASS_PANE, tempP.getName(), "Gag: " + tempGag.getGagName(), "Damage: " + String.valueOf(tempGag.getDamage()), "Target: " + tempTarget));
				else
					lobbyInv.setItem((((looped + 1) * 2) - 1), createGuiItem(Material.BLUE_STAINED_GLASS_PANE, tempP.getName(), "Still choosing"));
				looped++;
			}
			playersGagLobbyInv.put(pUUID, lobbyInv);
		}
	}
	
	public void refreshTargetInventories() {
		for (UUID pUUID : players) {
			Inventory targetInv = null;
			if (playersTargetInv.containsKey(pUUID)) {
				targetInv = playersTargetInv.get(pUUID);
			}
			else {
				targetInv = Bukkit.createInventory(null, 9, ChatColor.GOLD + "" + "Choose Target");
			}
			
			for (int i = 0; i < npcs.size(); i++) {
				targetInv.setItem((((i + 1) * 2) - 1), createGuiItem(Material.RED_STAINED_GLASS_PANE, ChatColor.RED + "" + "Attack " + (i + 1)));
			}
			playersTargetInv.put(pUUID, targetInv);
		}
	}
	
	public void refreshLobbyInventories() {
		for (UUID pUUID : players) {
			Inventory lobbyInv = null;
			if (playersGagLobbyInv.containsKey(pUUID)) {
				lobbyInv = playersGagLobbyInv.get(pUUID);
			}
			else {
				lobbyInv = Bukkit.createInventory(null, 18, ChatColor.GOLD + "" + "Choose Target");
			}
			int looped = 0;
			for (UUID pUUID2 : players) {
				Player tempP = Bukkit.getPlayer(pUUID2);
				Gag tempGag = getGag(pUUID2);
				int tempTarget = getPlayerTarget(pUUID2);
				if (playerDoneChoosing(pUUID2))
					lobbyInv.setItem((((looped + 1) * 2) - 1), createGuiItem(Material.GREEN_STAINED_GLASS_PANE, tempP.getName(), "Gag: " + tempGag.getGagName(), "Damage: " + String.valueOf(tempGag.getDamage()), "Target: " + tempTarget));
				else
					lobbyInv.setItem((((looped + 1) * 2) - 1), createGuiItem(Material.BLUE_STAINED_GLASS_PANE, tempP.getName(), "Still choosing"));
				looped++;
			}
			playersGagLobbyInv.put(pUUID, lobbyInv);
		}
	}
	
	public void initPlayerBattleInventorys(UUID pUUID) {
		Inventory targetInv = Bukkit.createInventory(null, 9, ChatColor.GOLD + "" + "Choose Target");
		for (int i = 0; i < 9; i++) {
			targetInv.setItem(i, createGuiItem(Material.WHITE_STAINED_GLASS_PANE, " "));
		}
		
		for (int i = 0; i < npcs.size(); i++) {
			targetInv.setItem((((i + 1) * 2) - 1), createGuiItem(Material.RED_STAINED_GLASS_PANE, ChatColor.RED + "" + "Attack " + (i + 1)));
		}
		playersTargetInv.put(pUUID, targetInv);
		
		//Lobby Inv
		Inventory lobbyInv = Bukkit.createInventory(null, 18, ChatColor.GOLD + "" + "Waiting for other players");
		for (int i = 0; i < 18; i++) {
			lobbyInv.setItem(i, createGuiItem(Material.WHITE_STAINED_GLASS_PANE, ChatColor.WHITE + " "));
		}
		int looped = 0;
		for (UUID pUUID2 : players) {
			Player tempP = Bukkit.getPlayer(pUUID2);
			Gag tempGag = getGag(pUUID2);
			int tempTarget = getPlayerTarget(pUUID2);
			if (playerDoneChoosing(pUUID2))
				lobbyInv.setItem((((looped + 1) * 2) - 1), createGuiItem(Material.GREEN_STAINED_GLASS_PANE, tempP.getName(), "Gag: " + tempGag.getGagName(), "Damage: " + String.valueOf(tempGag.getDamage()), "Target: " + tempTarget));
			else
				lobbyInv.setItem((((looped + 1) * 2) - 1), createGuiItem(Material.BLUE_STAINED_GLASS_PANE, tempP.getName(), "Still choosing"));
			looped++;
		}
		playersGagLobbyInv.put(pUUID, lobbyInv);
	}

	public void placeTrap(UUID trapPlacer, UUID cog) {
		if (placedTraps.containsKey(cog)) {
			removeTrap(trapPlacer, cog);
			sendAllMessage(ChatColor.BLUE + "[Battle] " + ChatColor.WHITE + getCog(cog).getJustCogName() + " trap has been removed!");
		} else {
			Gag trapGag = getGag(trapPlacer);
			trapOwners.put(trapGag, trapPlacer);
			placedTraps.put(cog, trapGag);
			Location l = main.registry.getByUniqueId(cog).getStoredLocation();
			l.add(0.0, 1.0, 0.0);
			Block trapBlock = l.getBlock().getLocation().add(l.getDirection().normalize()).getBlock();
			trapBlock.setType(Material.FLOWER_POT);
			trapBlocks.put(cog, trapBlock);
		}
	}

	public Gag getCogTrap(UUID cog) {
		if (placedTraps.containsKey(cog))
			return placedTraps.get(cog);
		return null;
	}

	public UUID getCogTrapOwner(UUID cog) {
		if (placedTraps.containsKey(cog)) {
			Gag tempTrap = placedTraps.get(cog);
			return tempTrap.getOwner();
		}
		return null;
	}

	public boolean isTrapped(UUID cog) {
		if (placedTraps.containsKey(cog))
			return true;
		return false;
	}

	public void removeTrap(UUID trapPlacer, UUID cog) {
		if (placedTraps.containsKey(cog))
			placedTraps.remove(cog);
		if (trapOwners.containsValue(trapPlacer))
			trapOwners.remove(getGag(trapPlacer));
		if (trapBlocks.containsKey(cog)) {
			Block tempTrapBlock = trapBlocks.get(cog);
			if (tempTrapBlock.getType() != Material.AIR)
				tempTrapBlock.setType(Material.AIR);
			else
				System.out.println("----removeTrap Error: Asked to remove trap block thats already air!");
			trapBlocks.remove(cog);
		}
	}

	public Location getKey() {
		return key;
	}

	// Overloading methods to allow adding both a single player to the players set
	// along with a list to players set.
	public void addPlayer(UUID pUUID) {
		players.add(pUUID);
	}
	
	public void addPlayers(List<UUID> pUUIDs) {
		players.addAll(pUUIDs);
	}
	
	public void addPlayerActiveBattle(UUID uuid) {
		System.out.println("Added player to active battle!");
		//Location newPlayerLocation = Bukkit.getPlayer(players.get(players.size() - 1)).getLocation();
		//newPlayerLocation.add(2.0, 0.0, 0.0);
		//Bukkit.getPlayer(uuid).teleport(newPlayerLocation);
		players.add(uuid);
		initPlayerBattleInventorys(uuid);
		if (joiningPlayerQueue.contains(uuid))
			joiningPlayerQueue.remove(uuid);
		Toon tempToon = ToonsController.getToon(uuid);
		toons.put(uuid, tempToon);
		Player tempPlayer = Bukkit.getPlayer(uuid);
		tempPlayer.getInventory().setContents(tempToon.getGag2Inventory().getContents());
		BattleCore.openInventoryLater(tempPlayer, tempToon.getGagInventory());
		refreshPlayerLocations(1);
	}
	
	public void addPlayerActiveBattle(List<UUID> uuids) {
		for (UUID player : uuids) {
			Location newPlayerLocation = Bukkit.getPlayer(players.get(players.size() - 1)).getLocation();
			newPlayerLocation.add(2.0, 0.0, 0.0);
			Bukkit.getPlayer(player).teleport(newPlayerLocation);
			Toon tempToon = ToonsController.getToon(player);
			toons.put(player, tempToon);
			players.add(player);
			initPlayerBattleInventorys(player);
			Player tempPlayer = Bukkit.getPlayer(player);
			//Toon tempToon = inputBattleData.getBattleToon(tempUUID);
			tempPlayer.getInventory().setContents(tempToon.getGag2Inventory().getContents());
			BattleCore.openInventoryLater(tempPlayer, tempToon.getGagInventory());
		}
		refreshPlayerLocations(uuids.size());
		joiningPlayerQueue.removeAll(uuids);
	}

	public void removePlayer(UUID pUUID) {
		players.remove(pUUID);
		refreshPlayerLocations(-1);
	}
	
	public void removePlayers(List<UUID> pUUIDs) {
		for (UUID pUUID : pUUIDs) {
				players.remove(pUUID);
		}
		int rmvAmt = players.size();
		rmvAmt *= -1;
		refreshPlayerLocations(rmvAmt);
	}

	public void addPlayerQueue(UUID pUUID) {
		joiningPlayerQueue.add(pUUID);
	}
	
	public void addPlayersQueue(List<UUID> pUUIDs) {
		joiningPlayerQueue.addAll(pUUIDs);
	}
	
	public List<UUID> getPlayersQueue() {
		return joiningPlayerQueue;
	}
	
	public void removePlayerQueue(UUID pUUID) {
		if (joiningPlayerQueue.contains(pUUID)) {
			joiningPlayerQueue.remove(pUUID);
		}
	}
	
	public void removePlayersQueue(List<UUID> pUUIDs) {
		for (UUID pUUID : pUUIDs) {
			if (joiningPlayerQueue.contains(pUUID)) {
				joiningPlayerQueue.remove(pUUID);
			}
		}
	}

	public void addNPC(UUID npcUUID) {
		npcs.add(npcUUID);
	}
	
	public void addNPCs(List<UUID> npcUUIDs) {
		npcs.addAll(npcUUIDs);
	}
	
	public void removeNPC(UUID npcUUID) {
		if (npcs.contains(npcUUID)) {
			npcs.remove(npcUUID);
		}
	}
	
	public void removeNPCs(List<UUID> npcUUIDs) {
		for (UUID npcUUID : npcUUIDs) {
			if (npcs.contains(npcUUID)) {
				npcs.remove(npcUUID);
			}
		}
	}
	
	//public void addDeadPlayer(UUID uuid) {
	//	bdDeadPlayers.add(uuid);
	//}
	
	//public void addDeadNpc(UUID uuid) {
	//	bdDeadNpcs.add(uuid);
	//}
	
	//public void addDeadCog(String cogName) {
	//	if (bdDeadCogs.containsKey(cogName)) {
	//		int newInt = bdDeadCogs.get(cogName) + 1;
	//		bdDeadCogs.put(cogName, newInt);
	//	}
	//	else
	//		bdDeadCogs.put(cogName, 1);
	//}
	
	//public void addDeadCog2(Cog deadCog) {
	//	if (bdDeadCogs2.containsKey(deadCog)) {
	//		int newInt = bdDeadCogs2.get(deadCog) + 1;
	//		bdDeadCogs2.put(deadCog, newInt);
	//	}
	//	else
	//		bdDeadCogs2.put(deadCog, 1);
	//}
	
	public void refreshPlayerLocations(int amtChange) {
		float L0_5x = 0.5f, L0_5z = 0.5f;
		float L1x = 1f, L1z = 1f;
		float R1x = 1f, R1z = 1f;
		float L1_5x = 1.5f, L1_5z = 1.5f;
		float R2x = 2f, R2z = 2f;
		if (playerDefaultLoc.getYaw() == 180) {
			L0_5x *= -1; L0_5z = 0f;
			L1x *= -1; L1z = 0f;
			R1z = 0f;
			L1_5x *= -1; L1_5z = 0f;
			R2z = 0f;
		}
		else if (playerDefaultLoc.getYaw() == -90) {
			L0_5x = 0; L0_5z *= -1;
			L1x = 0; L1z *= -1;
			R1x = 0;
			L1_5x = 0; L1_5z *= -1;
			R2x = 0;
		}
		else if (playerDefaultLoc.getYaw() == 0) {
			L0_5z = 0;
			L1z = 0;
			R1x *= -1; R1z = 0;
			L1_5z = 0;
			R2x *= -1; R2z = 0;
		}
		else if (playerDefaultLoc.getYaw() == 90) {
			L0_5x = 0;
			L1x = 0;
			R1x = 0; R1z *= -1;
			L1_5x = 0;
			R2x = 0; R2z *= -1;
		}
		
		Player p1 = Bukkit.getPlayer(players.get(0));
		int orgAmt = players.size() - amtChange;
		if (orgAmt == 1) {
			if (amtChange == 1) { //Joining
				p1.teleport(p1.getLocation().add(L1x, 0, L1z));
				Player p2 = Bukkit.getPlayer(players.get(1));
				p2.teleport(p1.getLocation().add(R2x, 0, R2z));
			}
			else if (amtChange == 2) { //Joining
				p1.teleport(p1.getLocation().add(L1x, 0, L1z));
				Player p2 = Bukkit.getPlayer(players.get(1));
				p2.teleport(p1.getLocation().add(R1x, 0, R1z));
				Player p3 = Bukkit.getPlayer(players.get(2));
				p3.teleport(p2.getLocation().add(R1x, 0, R1z));
			}
			else if (amtChange == 3) { //Joining
				p1.teleport(p1.getLocation().add(L1_5x, 0, L1_5z));
				Player p2 = Bukkit.getPlayer(players.get(1));
				p2.teleport(p1.getLocation().add(R1x, 0, R1z));
				Player p3 = Bukkit.getPlayer(players.get(2));
				p3.teleport(p2.getLocation().add(R1x, 0, R1z));
				Player p4 = Bukkit.getPlayer(players.get(3));
				p4.teleport(p3.getLocation().add(R1x, 0, R1z));
			}
		}
		else if (orgAmt == 2) {
			if (amtChange == 1) { //Joining
				Player p2 = Bukkit.getPlayer(players.get(1));
				p2.teleport(p1.getLocation().add(L1x, 0, L1z));
				Player p3 = Bukkit.getPlayer(players.get(2));
				p3.teleport(p2.getLocation().add(R1x, 0, R1z));
			}
			else if (amtChange == 2) { //Joining
				p1.teleport(p1.getLocation().add(L0_5x, 0, L0_5z));
				Player p2 = Bukkit.getPlayer(players.get(1));
				p2.teleport(p1.getLocation().add(L1x, 0, L1z));
				Player p3 = Bukkit.getPlayer(players.get(2));
				p3.teleport(p2.getLocation().add(L1x, 0, L1z));
				Player p4 = Bukkit.getPlayer(players.get(3));
				p4.teleport(p3.getLocation().add(L1x, 0, L1z));
			}
		}
		else if (orgAmt == 3) {
			if (amtChange == 1) { //Joining
				p1.teleport(p1.getLocation().add(L0_5x, 0, L0_5z));
				Player p2 = Bukkit.getPlayer(players.get(1));
				p2.teleport(p1.getLocation().add(R1x, 0, R1z));
				Player p3 = Bukkit.getPlayer(players.get(2));
				p3.teleport(p2.getLocation().add(R1x, 0, R1z));
				Player p4 = Bukkit.getPlayer(players.get(3));
				p4.teleport(p3.getLocation().add(R1x, 0, R1z));
			}
		}
		//LEAVING
		else if (amtChange == -1) {
			if (orgAmt == 4) {
				p1.teleport(playerDefaultLoc);
				p1.teleport(p1.getLocation().add(L1x, 0, L1z));
				Player p2 = Bukkit.getPlayer(players.get(1));
				p2.teleport(p1.getLocation().add(R1x, 0, R1z));
				Player p3 = Bukkit.getPlayer(players.get(2));
				p3.teleport(p2.getLocation().add(R1x, 0, R1z));
			}
			else if (orgAmt == 3) {
				p1.teleport(playerDefaultLoc);
				p1.teleport(p1.getLocation().add(L1x, 0, R1z));
				Player p2 = Bukkit.getPlayer(players.get(1));
				p2.teleport(p1.getLocation().add(R1x, 0, R1z));
			}
			else if (orgAmt == 2) {
				p1.teleport(playerDefaultLoc);
			}
			//No 1 case cause battle would be over
		}
	}
	
	public void refreshCogLocations(int amtChange) {
		float L0_5x = 0.5f, L0_5z = 0.5f;
		float L1x = 1f, L1z = 1f;
		float R1x = 1f, R1z = 1f;
		float L1_5x = 1.5f, L1_5z = 1.5f;
		float R2x = 2f, R2z = 2f;
		if (cogDefaultLoc.getYaw() == 180) {
			L0_5x *= -1; L0_5z = 0f;
			L1x *= -1; L1z = 0f;
			R1z = 0f;
			L1_5x *= -1; L1_5z = 0f;
			R2z = 0f;
		}
		else if (cogDefaultLoc.getYaw() == -90) {
			L0_5x = 0; L0_5z *= -1;
			L1x = 0; L1z *= -1;
			R1x = 0;
			L1_5x = 0; L1_5z *= -1;
			R2x = 0;
		}
		else if (cogDefaultLoc.getYaw() == 0) {
			L0_5z = 0;
			L1z = 0;
			R1x *= -1; R1z = 0;
			L1_5z = 0;
			R2x *= -1; R2z = 0;
		}
		else if (cogDefaultLoc.getYaw() == 90) {
			L0_5x = 0;
			L1x = 0;
			R1x = 0; R1z *= -1;
			L1_5x = 0;
			R2x = 0; R2z *= -1;
		}
		
		NPC npc1 = main.registry.getByUniqueId(npcs.get(0));
		int orgAmt = npcs.size() - amtChange;
		if (orgAmt == 1) {
			if (amtChange == 1) { //Joining
				npc1.teleport(npc1.getStoredLocation().add(L1x, 0, L1z), TeleportCause.PLUGIN);
				NPC npc2 = main.registry.getByUniqueId(npcs.get(1));
				npc2.teleport(npc1.getStoredLocation().add(R2x, 0, R2z), TeleportCause.PLUGIN);
			}
			else if (amtChange == 2) { //Joining
				npc1.teleport(npc1.getStoredLocation().add(L1x, 0, L1z), TeleportCause.PLUGIN);
				NPC npc2 = main.registry.getByUniqueId(npcs.get(1));
				npc2.teleport(npc1.getStoredLocation().add(R1x, 0, R1z), TeleportCause.PLUGIN);
				NPC npc3 = main.registry.getByUniqueId(npcs.get(2));
				npc3.teleport(npc2.getStoredLocation().add(R1x, 0, R1z), TeleportCause.PLUGIN);
			}
			else if (amtChange == 3) { //Joining
				npc1.teleport(npc1.getStoredLocation().add(L1_5x, 0, L1_5z), TeleportCause.PLUGIN);
				NPC npc2 = main.registry.getByUniqueId(npcs.get(1));
				npc2.teleport(npc1.getStoredLocation().add(R1x, 0, R1z), TeleportCause.PLUGIN);
				NPC npc3 = main.registry.getByUniqueId(npcs.get(2));
				npc3.teleport(npc2.getStoredLocation().add(R1x, 0, R1z), TeleportCause.PLUGIN);
				NPC npc4 = main.registry.getByUniqueId(npcs.get(3));
				npc4.teleport(npc3.getStoredLocation().add(R1x, 0, R1z), TeleportCause.PLUGIN);
			}
		}
		else if (orgAmt == 2) {
			if (amtChange == 1) { //Joining
				NPC npc2 = main.registry.getByUniqueId(npcs.get(1));
				npc2.teleport(npc1.getStoredLocation().add(L1x, 0, L1z), TeleportCause.PLUGIN);
				NPC npc3 = main.registry.getByUniqueId(npcs.get(2));
				npc3.teleport(npc2.getStoredLocation().add(R1x, 0, R1z), TeleportCause.PLUGIN);
			}
			else if (amtChange == 2) { //Joining
				npc1.teleport(npc1.getStoredLocation().add(L0_5x, 0, L0_5z), TeleportCause.PLUGIN);
				NPC npc2 = main.registry.getByUniqueId(npcs.get(1));
				npc2.teleport(npc1.getStoredLocation().add(L1x, 0, L1z), TeleportCause.PLUGIN);
				NPC npc3 = main.registry.getByUniqueId(npcs.get(2));
				npc3.teleport(npc2.getStoredLocation().add(L1x, 0, L1z), TeleportCause.PLUGIN);
				NPC npc4 = main.registry.getByUniqueId(npcs.get(3));
				npc4.teleport(npc3.getStoredLocation().add(L1x, 0, L1z), TeleportCause.PLUGIN);
			}
		}
		else if (orgAmt == 3) {
			if (amtChange == 1) { //Joining
				npc1.teleport(npc1.getStoredLocation().add(L0_5x, 0, L0_5z), TeleportCause.PLUGIN);
				NPC npc2 = main.registry.getByUniqueId(npcs.get(1));
				npc2.teleport(npc1.getStoredLocation().add(R1x, 0, R1z), TeleportCause.PLUGIN);
				NPC npc3 = main.registry.getByUniqueId(npcs.get(2));
				npc3.teleport(npc2.getStoredLocation().add(R1x, 0, R1z), TeleportCause.PLUGIN);
				NPC npc4 = main.registry.getByUniqueId(npcs.get(3));
				npc4.teleport(npc3.getStoredLocation().add(R1x, 0, R1z), TeleportCause.PLUGIN);
			}
		}
		//LEAVING
		else if (amtChange == -1) {
			if (orgAmt == 4) {
				npc1.teleport(cogDefaultLoc, TeleportCause.PLUGIN);
				npc1.teleport(npc1.getStoredLocation().add(L1x, 0, L1z), TeleportCause.PLUGIN);
				NPC npc2 = main.registry.getByUniqueId(npcs.get(1));
				npc2.teleport(npc1.getStoredLocation().add(R1x, 0, R1z), TeleportCause.PLUGIN);
				NPC npc3 = main.registry.getByUniqueId(npcs.get(2));
				npc3.teleport(npc2.getStoredLocation().add(R1x, 0, R1z), TeleportCause.PLUGIN);
			}
			else if (orgAmt == 3) {
				npc1.teleport(cogDefaultLoc, TeleportCause.PLUGIN);
				npc1.teleport(npc1.getStoredLocation().add(L1x, 0, L1z), TeleportCause.PLUGIN);
				NPC npc2 = main.registry.getByUniqueId(npcs.get(1));
				npc2.teleport(npc1.getStoredLocation().add(R1x, 0, R1z), TeleportCause.PLUGIN);
			}
			else if (orgAmt == 2) {
				npc1.teleport(cogDefaultLoc, TeleportCause.PLUGIN);
			}
			//No 1 case cause battle would be over
		}
	}
	
	public void addDeadCog(Cog deadCog) {
		deadCogs.add(deadCog);
	}
	
	public List<Cog> getDeadCogs() {
		return deadCogs;
	}
	
	public boolean isCogDead(Cog cog) {
		if (deadCogs.contains(cog)) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public boolean isNpcsEmpty() {
		if (npcs.isEmpty()) {
			return true;
		}
		else {
			return false;
		}
	}

	public void addCog(UUID cogUUID, Cog cog) {
		cogs.put(cogUUID, cog);
		if (cogs.size() > 1) { //If not the first cog, refresh cog locations. (Because first cog is already placed correctly)
			refreshCogLocations(1);
		}
	}

	public UUID getCogUUID(int target) {
		if (target > npcs.size()) {
			return null;
		}
		else {
			return npcs.get(target);
		}
	}

	public Cog getCog(UUID npcUUID) {
		if (cogs.containsKey(npcUUID)) {
			return cogs.get(npcUUID);
		}
		else {
			return null;
		}
	}
	
	public boolean isSpecialMode() {
		return specialMode;
	}
	
	public Inventory getPlayerLobbyInv(UUID pUUID) {
		if (playersGagLobbyInv.containsKey(pUUID)) {
			return playersGagLobbyInv.get(pUUID);
		}
		else {
			return null;
		}
	}
	
	public Inventory getPlayerTargetInv(UUID pUUID) {
		if (playersTargetInv.containsKey(pUUID)) {
			return playersTargetInv.get(pUUID);
		}
		else {
			return null;
		}
	}
	
	public CogBuilding getCogBuilding() {
		return cogBuilding;
	}
	
	public int getDeadCogAmount() {
		return deadCogs.size();
	}
	
	public int getCurrentCogAmount() {
		return npcs.size();
	}
	
	public int getCurrentPlayerAmount() {
		return players.size();
	}

	public List<UUID> getBattlePlayerList() {
		return players;
	}
	

	public List<UUID> getBattleNPCList() {
		return npcs;
	}
	
	
	public Toon getBattleToon(UUID playerUUID) {
		if (toons.containsKey(playerUUID)) {
			return toons.get(playerUUID);
		}
		else {
			return null;
		}
	}

	public boolean isInBattle(UUID uuid) {
		if (players.contains(uuid)) {
			return true;
		} else if (npcs.contains(uuid)) {
			return true;
		}
		else {
			return false;
		}
	}

	public boolean isPlayerInBattle(UUID pUUID) {
		if (players.contains(pUUID)) {
			return true;
		}
		else {
			return false;
		}
	}

	public void addGag(UUID playerUUID, Gag gag) {
		gags.put(playerUUID, gag);
		playersGagSelected.put(playerUUID, true);
	}

	public Gag getGag(UUID pUUID) {
		Gag gag = null;
		if (gags.containsKey(pUUID)) {
			gag = gags.get(pUUID);;
		}
		return gag;
	}
	
	public void setPlayersTurn(boolean input) {
		playersTurn = input;
	}

	public void setPlayerTarget(UUID playerUUID, int target) {
		playersTargets.put(playerUUID, target);
		playersTargetSelected.put(playerUUID, true);
		System.out.println("Set player target!");
	}

	public int getPlayerTarget(UUID playerUUID) {
		if (playersTargets.containsKey(playerUUID)) {
			return playersTargets.get(playerUUID);
		}
		else {
			return -1;
		}
	}

	public boolean checkPlayerTarget(UUID playerUUID) {
		if (playersTargets.containsKey(playerUUID)) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public void resetPlayerTargets() {
		for (UUID pUUID : players) {
			playersTargets.put(pUUID, -1);
			playersTargetSelected.put(pUUID, false);
		}
	}

	public void addPlayerExp(UUID playerUUID, int track, int exp) {
		track--;
		System.out.println("Add Player Exp Called:");
		System.out.println("Track: " + track + " Exp: " + exp);
		if (exps.get(playerUUID) != null) {
			int totalExp = exp;
			if (exps.get(playerUUID).get(track) != null)
				totalExp += exps.get(playerUUID).get(track);
			exps.get(playerUUID).put(track, totalExp);
		} else {
			Map<Integer, Integer> tempTrackExp = new HashMap<>();
			tempTrackExp.put(track, exp);
			exps.put(playerUUID, tempTrackExp);
		}
	}

	public int getPlayerExp(UUID playerUUID, int track) {
		if (exps.containsKey(playerUUID)) {
			if (exps.get(playerUUID).get(track) != null) {
				return exps.get(playerUUID).get(track);
			}
		}
		return 0;
	}

	public boolean calcPlayerHit(int gagAcc, int trackExp, int targetDefense, int bonus) {
		Random random = new Random();
		int attackAcc = gagAcc + trackExp + targetDefense + bonus;
		int temp = random.nextInt(attackAcc - 0 + 1) + 0;
		if (temp > attackAcc) {
			return true;
		}
		return false;
	}
	
	public void addToBattleLocationY(int yInc) {
		this.key.add(0, yInc, 0);
	}
	
	public boolean isPlayersTurn() {
		return playersTurn;
	}

	public boolean playerDoneChoosing(UUID pUUID) {
		if (playersTargets.containsKey(pUUID)) {
			return true;
		}
		else {
			return false;
		}
	}

	public boolean playersDoneChoosing() {
		if (playersTargets.size() == players.size()) {
			return true;
		}
		else {
			return false;
		}
	}

	public boolean isLured(UUID npcUUID) {
		if (lureRounds.containsKey(npcUUID)) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public float getDefaultPlayerYaw() {
		return playerDefaultLoc.getYaw();
	}
	
	public void setDefaultPlayerLocation(Location l) {
		this.playerDefaultLoc = l;
	}
	
	public Location getDefaultPlayerLocation(Location l) {
		return playerDefaultLoc.clone();
	}
	/*
	public void lure(UUID iLurer, List<UUID> cogs, int rounds) {
		for (UUID cogUUID : cogs) {
			if (!lures.containsKey(cogUUID))
				lures.put(cogUUID, rounds);
		}
	}

	public void lure(UUID iLurer, List<UUID> cogs) {
		int tempRounds = getGag(iLurer).getRounds();
		for (UUID cogUUID : cogs) {
			if (!lures.containsKey(cogUUID))
				lures.put(cogUUID, tempRounds);
		}
	}
	*/
	public void lure(UUID lurer, UUID npcUUID, int rounds) {
		if (!lureRounds.containsKey(npcUUID)) {
			lureRounds.put(npcUUID, rounds);
		}
	}
	/*
	public void lure(UUID iLurer, UUID cog) {
		int tempRounds = getGag(iLurer).getRounds();
		if (!lures.containsKey(cog))
			lures.put(cog, tempRounds);
	}
	*/
	public void unLure(UUID npcUUID) {
		if (lureRounds.containsKey(npcUUID)) {
			lureRounds.remove(npcUUID);
		}
	}
	
	public void battleOver() {
		battleOver = true;
	}
	
	public boolean isBattleOver() {
		return battleOver;
	}

	public boolean getPlayerGagSelected(UUID pUUID) {
		if (playersGagSelected.containsKey(pUUID)) {
			return playersGagSelected.get(pUUID);
		}
		else {
			return false;
		}
	}

	public void setPlayerGagSeleted(UUID pUUID) {
		playersGagSelected.put(pUUID, true);
	}

	public void nextLureRound() {
		sendAllMessage("Next Lure Round Ran!");
		if (!lureRounds.isEmpty()) {
			for(UUID npcUUID : lureRounds.keySet()) {
				int updatedRounds = lureRounds.get(npcUUID);
				if (updatedRounds > 0) {
					lureRounds.put(npcUUID, updatedRounds);
				}
				else {
					lureRounds.remove(npcUUID);
					sendAllMessage(ChatColor.BLUE + "[Battle] " + ChatColor.WHITE + cogs.get(npcUUID).getJustCogName() + " is now unlured!");
				}
			}
		}
	}

	public void sendAllMessage(String msg) {
		for (UUID player : players) {
			Bukkit.getPlayer(player).sendMessage(msg);
		}
	}

	public void finishPlayerRound() {
		// System.out.println("Clearing bdGags and bdPlayerTarget!");
		gags.clear();
		playersTargets.clear();
		playersGagSelected.clear();
	}
	
	protected ItemStack createGuiItem(final Material material, final String name, final String... lore) {
		final ItemStack item = new ItemStack(material, 1);
		final ItemMeta meta = item.getItemMeta();

		meta.setDisplayName(name);
		meta.setLore(Arrays.asList(lore));
		item.setItemMeta(meta);

		return item;
	}
	public void deleteAll() {
		System.out.println("CLEARING ALL BATTLEDATA OBJECT");
		cogs.clear();
		players.clear();
		npcs.clear();
		gags.clear();
		playersTargets.clear();
		//trackExp.clear();
		exps.clear();
		lureRounds.clear();
		traps.clear();
		trapBlocks.clear();
		trapOwners.clear();
		placedTraps.clear();
		joiningPlayerQueue.clear();
		playersGagSelected.clear();
		playersTargetSelected.clear();
		playersGagLobbyInv.clear();
		playersTargetInv.clear();
		key = null;
		main = null;
	}
}
