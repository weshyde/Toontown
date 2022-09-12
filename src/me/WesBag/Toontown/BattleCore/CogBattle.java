package me.WesBag.Toontown.BattleCore;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.WesBag.Toontown.Main;
import me.WesBag.Toontown.BattleCore.Cogs.Cog;
import me.WesBag.Toontown.BattleCore.Toons.Toon;
import me.WesBag.Toontown.BattleCore.Toons.ToonsController;
import net.md_5.bungee.api.ChatColor;
import me.WesBag.Toontown.BattleCore.Gags.Gag;

public class CogBattle {
	
	private Main main;
	
	private Location key;
	
	private List<UUID> players = new ArrayList<>();
	private List<UUID> npcs = new ArrayList<>();
	private List<UUID> joiningPlayerQueue = new ArrayList<>();
	private List<Cog> deadCogs = new ArrayList<>();
	
	private Map<UUID, Toon> toons = new HashMap<>();
	private Map<UUID, Cog> cogs = new HashMap<>();
	private Map<UUID, Gag> gags = new HashMap<>();
	private Map<UUID, Integer> playersTargets = new HashMap<>();
	private Map<Integer, Integer> trackExp = new HashMap<>();
	private Map<UUID, Map<Integer, Integer>> exps = new HashMap<>();
	private Map<UUID, Integer> lureRounds = new HashMap<>();
	private Map<Gag, UUID> trapOwners = new HashMap<>(); //One of these VVV is unnessesary?
	private Map<UUID, Gag> placedTraps = new HashMap<>();
	private Map<UUID, Block> trapBlocks = new HashMap<>();
	private Map<UUID, Boolean> playersGagSelected = new HashMap<>();
	private Map<UUID, Boolean> playersTargetSelected = new HashMap<>();
	private Map<UUID, Inventory> playersGagLobbyInv = new HashMap<>();
	private Map<UUID, Inventory> playersTargetInv = new HashMap<>();
	
	private boolean battleOver = false;
	private boolean specialMode;
	private boolean playersTurn = false;
	
	public CogBattle(Main main, UUID playerUUID, UUID npcUUID, Location key) {
		this.main = main;
		this.key = key;
		
		players.add(playerUUID);
		npcs.add(npcUUID);
		
		Toon toon = ToonsController.getToon(playerUUID);
		toons.put(playerUUID, toon);
		
		setSpecialMode(false);
	}
	
	public CogBattle(Main main, List<UUID> playersUUID, List<UUID> npcsUUID, Location key) {
		this.main = main;
		this.key = key;
		
		players.addAll(playersUUID);
		npcs.addAll(npcsUUID);
		
		for (UUID pUUID : playersUUID) {
			Toon toon = ToonsController.getToon(pUUID);
			toons.put(pUUID, toon);
		}
		//Toon toon = ToonsController.getToon(playes)
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
	
	public void placeTrap(UUID trapPlacer, UUID cog) {
		if (placedTraps.containsKey(cog)) {
			removeTrap(trapPlacer, cog);
			sendAllMessage(ChatColor.BLUE + "[Battle] " + ChatColor.WHITE + getCog(cog).getJustCogName() + " trap has been removed!");
		}
		else {
			Gag trapGag = getGag(trapPlacer);
			trapOwners.put(trapGag, trapPlacer);
			placedTraps.put(cog, trapGag);
			Location cogL = main.registry.getByUniqueId(cog).getStoredLocation();
			//Location cogL = main.re
			//cogL.add(0.0, 1.0, 0.0);
			Block trapBlock = cogL.getBlock().getLocation().add(cogL.getDirection().normalize()).getBlock();
			trapBlock.setType(Material.FLOWER_POT);
			trapBlocks.put(cog, trapBlock);
		}
	}
	
	public Gag getTrap(UUID cog) {
		if (placedTraps.containsKey(cog)) {
			return placedTraps.get(cog);
		}
		else {
			return null;
		}
	}
	
	public UUID getTrapOwner(UUID cog) {
		if (placedTraps.containsKey(cog)) {
			return placedTraps.get(cog).getOwner();
		}
		else {
			return null;
		}
	}
	
	public boolean isTrapped(UUID cog) {
		if (placedTraps.containsKey(cog)) {
			return true;
		}
		else {
			return false;
		}
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
	
	public void lure(UUID lurer, UUID npcUUID, int rounds) {
		if (!lureRounds.containsKey(npcUUID)) {
			lureRounds.put(npcUUID, rounds);
		}
	}
	
	public void unLure(UUID npcUUID) {
		if (lureRounds.containsKey(npcUUID)) {
			lureRounds.remove(npcUUID);
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
	
	public void playerRoundOver() {
		gags.clear();
		playersTargets.clear();
		playersGagSelected.clear();
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
	
	
	public void sendAllMessage(String msg) {
		for (UUID pUUID : players) {
			Bukkit.getPlayer(pUUID).sendMessage(msg);
		}
	}
	
	public void addGag(UUID pUUID, Gag gag) {
		gags.put(pUUID, gag);
		playersGagSelected.put(pUUID, true);
	}
	
	public Gag getGag(UUID pUUID) {
		Gag gag = null;
		if (gags.containsKey(pUUID)) {
			gag = gags.get(pUUID);;
		}
		return gag;
	}
	
	public Location getKey() {
		return key;
	}
	
	public void addPlayer(UUID pUUID) {
		players.add(pUUID);
	}
	
	public void addPlayers(List<UUID> pUUIDs) {
		players.addAll(pUUIDs);
	}
	
	public void removePlayer(UUID pUUID) {
		if (players.contains(pUUID)) {
			players.remove(pUUID);
		}
	}
	
	public void removePlayers(List<UUID> pUUIDs) {
		for (UUID pUUID : pUUIDs) {
			if (players.contains(pUUID)) {
				players.remove(pUUID);
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
	
	public List<UUID> getPlayers() {
		return players;
	}
	
	public List<UUID> getNpcs() {
		return npcs;
	}
	
	public void deadCog(Cog deadCog) {
		deadCogs.add(deadCog);
	}
	
	public boolean isCogDead(Cog cog) {
		if (deadCogs.contains(cog)) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public List<Cog> getDeadCogs() {
		return deadCogs;
	}
	
	public void setPlayerTarget(UUID pUUID, int target) {
		playersTargets.put(pUUID, target);
		playersTargetSelected.put(pUUID, true);
	}
	
	public int getPlayerTarget(UUID pUUID) {
		if (playersTargets.containsKey(pUUID)) {
			return playersTargets.get(pUUID);
		}
		else {
			return -1;
		}
	}
	
	public boolean checkPlayerTarget(UUID pUUID) {
		if (playersTargets.containsKey(pUUID)) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public void resetPlayersTargets() {
		for (UUID pUUID : players) {
			playersTargets.put(pUUID, -1);
			playersTargetSelected.put(pUUID, false);
		}
	}
	
	public void setPlayersTurn(boolean playersTurn) {
		this.playersTurn = playersTurn;
	}
	
	public boolean getPlayerTurn() {
		return playersTurn;
	}
	
	public void setSpecialMode(boolean specialMode) {
		this.specialMode = specialMode;
	}
	
	public boolean getSpecialMode() {
		return specialMode;
	}
	
	public void addPlayerExp(UUID pUUID, int track, int exp) {
		track--;
		System.out.println("Add Player Exp Called:");
		System.out.println("Track: " + track + " Exp: " + exp);
		if (exps.get(pUUID) != null) {
			int totalExp = exp;
			if (exps.get(pUUID).get(track) != null)
				totalExp += exps.get(pUUID).get(track);
			exps.get(pUUID).put(track, totalExp);
		} else {
			Map<Integer, Integer> tempTrackExp = new HashMap<>();
			tempTrackExp.put(track, exp);
			exps.put(pUUID, tempTrackExp);
		}
	}
	
	public int getPlayerExp(UUID playerUUID, int track) {
		if (exps.containsKey(playerUUID))
			if (exps.get(playerUUID).get(track) != null)
				return exps.get(playerUUID).get(track);
		return 0;
	}
	
	public Cog getCog(UUID npcUUID) {
		if (cogs.containsKey(npcUUID)) {
			return cogs.get(npcUUID);
		}
		else {
			return null;
		}
	}
	
	public Toon getToon(UUID pUUID) {
		if (toons.containsKey(pUUID)) {
			return toons.get(pUUID);
		}
		else {
			return null;
		}
	}
	
	protected ItemStack createGuiItem(final Material material, final String name, final String... lore) {
		final ItemStack item = new ItemStack(material, 1);
		final ItemMeta meta = item.getItemMeta();

		meta.setDisplayName(name);
		meta.setLore(Arrays.asList(lore));
		item.setItemMeta(meta);

		return item;
	}
	
}
