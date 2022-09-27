package me.WesBag.Toontown.BattleCore;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;


import me.WesBag.Toontown.Main;
import me.WesBag.Toontown.BattleCore.Cogs.Cog;
import me.WesBag.Toontown.BattleCore.Gags.Throw.Throw;
import me.WesBag.Toontown.BattleCore.Gags.Toonup.Toonup;
import me.WesBag.Toontown.BattleCore.Gags.Trap.Trap;
import me.WesBag.Toontown.BattleCore.Toons.Toon;
import me.WesBag.Toontown.BattleCore.Toons.ToonsController;
import me.WesBag.Toontown.BattleCore.Gags.Gag;
import me.WesBag.Toontown.BattleCore.Gags.Drop.Drop;
import me.WesBag.Toontown.BattleCore.Gags.Lure.Lure;
import me.WesBag.Toontown.BattleCore.Gags.Sound.Sound;
import me.WesBag.Toontown.BattleCore.Gags.Squirt.Squirt;
import me.WesBag.Toontown.Files.BattleData;
import me.WesBag.Toontown.Files.CogType;
import me.WesBag.Toontown.Streets.StreetBattlePositioning;
import me.WesBag.Toontown.BattleCore.Toons.ShtickerBook.ShtickerBook;
import me.WesBag.Toontown.Tasks.CustomEvents.BattleFinishEvent;
import net.citizensnpcs.api.event.NPCCollisionEvent;
import net.citizensnpcs.api.event.NPCRightClickEvent;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.npc.ai.NPCHolder;
import net.citizensnpcs.trait.waypoint.LinearWaypointProvider;
import net.citizensnpcs.trait.waypoint.Waypoints;

public class BattleCore implements Listener {
	public static Main main;
	public static Logger PluginLogger;
	public static List<BattleData> allBattles = new ArrayList<BattleData>();
	public HashMap<UUID, Integer> countdowns;
	public final int[] jChances = { 1, 5, 10, 40, 60, 80 };

	public final static String[][] gagNames = new String[][] {
			{ "toonup", "trap", "lure", "sound", "throw", "squirt", "drop" },
			{ "Feather", "Banana-Peel", "Dollar-Bill", "Bike-Horn", "Cupcake", "Squirting-Flower", "Flower-Pot" },
			{ "Megaphone", "Rake", "Small-Magnet", "Whistle", "Fruit-Pie-Slice", "Glass-Of-Water", "Sandbag" },
			{ "Lipstick", "Marbles", "Five-Dollar-Bill", "Trumpet", "Cream-Pie-Slice", "Squirt-Gun", "Anvil" },
			{ "Bamboo-Cane", "Quicksand", "Big-Magnet", "Aoogah", "Fruit-Pie", "Seltzer-Bottle", "Big-Weight" },
			{ "Pixie-Dust", "Trapdoor", "Ten-Dollar-Bill", "Elephant-Trunk", "Cream-Pie", "Fire-Hose", "Safe" },
			{ "Juggling-Balls", "TNT", "Hypno-Goggles", "Fog-Horn", "Birthday-Cake", "Storm-Cloud", "Grand-Piano" },
			{ "High-Dive", "Railroad", "Presentation", "Opera", "Wedding-Cake", "Geyser", "Toontanic" }, };
	public volatile Inventory WinInv;
	public volatile Inventory targetInv;
	public volatile Inventory gagLobbyInv;

	public BattleCore(Main mn) {
		main = mn;
		countdowns = new HashMap<>();
		allBattles = new ArrayList<>();
	}

	public void targetBattleGUI(BattleData inputBattleData) {
		targetInv = Bukkit.createInventory(null, 9, ChatColor.GOLD + "" + "Choose Target");
		for (int i = 0; i < 9; i++) {
			targetInv.setItem(i, createGuiItem(Material.RED_STAINED_GLASS_PANE, ChatColor.STRIKETHROUGH + " "));
		}

		for (int i = 0; i < inputBattleData.getBattleNPCList().size(); i++) {
			targetInv.setItem((((i + 1) * 2) - 1),
					createGuiItem(Material.GREEN_STAINED_GLASS_PANE, ChatColor.GREEN + "" + "Attack " + (i + 1)));
		} // 1, 3, 5, 7 gui slots
	}

	public void playerHealGUI(BattleData inputBattleData) {
		targetInv = Bukkit.createInventory(null, 9, ChatColor.GOLD + "" + "Choose Player");
		for (int i = 0; i < 9; i++) {
			targetInv.setItem(i, createGuiItem(Material.RED_STAINED_GLASS_PANE, ChatColor.STRIKETHROUGH + " "));
		}

		for (int i = 0; i < inputBattleData.getBattlePlayerList().size(); i++) {
			targetInv.setItem((((i + 1) * 2) - 1),
					createGuiItem(Material.PINK_STAINED_GLASS_PANE, ChatColor.LIGHT_PURPLE + "" + "Heal " + (i + 1)));
		}
	}

	public void openTargetInventory(final HumanEntity e, BattleData inputBattleData, boolean isTU) {
		// if (!isTU)
		// targetBattleGUI(inputBattleData) //Change this to work with new invs stored
		// in battle data, (add tu menu gui init to b attle data too?)
		// else
		// playerHealGUI(inputBattleData);
		// openInventoryLater(e, targetInv);
		inputBattleData.refreshTargetInventories();
		openInventoryLater(e, inputBattleData.getPlayerTargetInv(e.getUniqueId()));
	}

	public void gagLobbyGUI(BattleData inputBattleData) {
		gagLobbyInv = Bukkit.createInventory(null, 18, ChatColor.GOLD + "" + "Waiting on other players");
		for (int i = 0; i < 18; i++) {
			gagLobbyInv.setItem(i, createGuiItem(Material.RED_STAINED_GLASS_PANE, ChatColor.RED + ""));
		}

		List<UUID> tempPlayers = inputBattleData.getBattlePlayerList();

		for (int i = 0; i < tempPlayers.size(); i++) {
			UUID playerUUID = tempPlayers.get(i);
			Player tempP = Bukkit.getPlayer(playerUUID);
			Gag tempGag = inputBattleData.getGag(playerUUID);
			int playerTarget = inputBattleData.getPlayerTarget(playerUUID);
			// int playerGagLocation =
			if (inputBattleData.playerDoneChoosing(playerUUID))
				gagLobbyInv.setItem((((i + 1) * 2) - 1),
						createGuiItem(Material.GREEN_STAINED_GLASS_PANE, tempP.getName(),
								"Gag: " + tempGag.getGagName(), "Damage: " + String.valueOf(tempGag.getDamage()),
								"Target: " + String.valueOf(playerTarget)));
			else
				gagLobbyInv.setItem((((i + 1) * 2) - 1),
						createGuiItem(Material.BLUE_STAINED_GLASS_PANE, tempP.getName(), "Still choosing"));
		}
	}

	public void openGagLobbyInventory(final HumanEntity e, BattleData inputBattleData) {
		closeInventoryLater(e);
		if (!inputBattleData.playersDoneChoosing()) {
			// gagLobbyGUI(inputBattleData); //Potentially add a loop to close and reopen
			// all players done rechoosing to update gagLobbyGUI
			// openInventoryLater(e, gagLobbyInv);
			// Changed 2/14/22 to be inside BattleData ^^^^
			inputBattleData.refreshLobbyInventories();
			openInventoryLater(e, inputBattleData.getPlayerLobbyInv(e.getUniqueId()));
		} else
			playerAttack(inputBattleData);
	}

	public void WinBattleGUI() {
		WinInv = Bukkit.createInventory(null, 54, ChatColor.GOLD + "" + "Exp");
		initWinInvItems();
	}

	public void initWinInvItems() {
		WinInv.setItem(0, createGuiItem(Material.PURPLE_CONCRETE, ChatColor.DARK_PURPLE + "" + "Toonup"));
		WinInv.setItem(9, createGuiItem(Material.YELLOW_CONCRETE, ChatColor.YELLOW + "" + "Trap"));
		WinInv.setItem(18, createGuiItem(Material.GREEN_CONCRETE, ChatColor.GREEN + "" + "Lure"));
		WinInv.setItem(27, createGuiItem(Material.BLUE_CONCRETE, ChatColor.BLUE + "" + "Sound"));
		WinInv.setItem(36, createGuiItem(Material.ORANGE_CONCRETE, ChatColor.GOLD + "" + "Throw"));
		WinInv.setItem(45, createGuiItem(Material.MAGENTA_CONCRETE, ChatColor.LIGHT_PURPLE + "" + "Squirt"));
	}

	protected ItemStack createGuiItem(final Material material, final String name, final String... lore) {
		final ItemStack item = new ItemStack(material, 1);
		final ItemMeta meta = item.getItemMeta();

		meta.setDisplayName(name);
		meta.setLore(Arrays.asList(lore));
		item.setItemMeta(meta);

		return item;
	}

	public void startStreetBattle(UUID playerUUID, UUID npcUUID) {
		Player p = Bukkit.getPlayer(playerUUID);
		Location battleKey = p.getLocation();
		
		//Implementing new battle positioning system here 9-15-22
		NPC npc = main.registry.getByUniqueId(npcUUID);
		Location battleLoc = StreetBattlePositioning.anchorStreetBattle(ToonsController.getToon(playerUUID).getRegionLocationID(), npc.getStoredLocation());
		if (battleLoc == null) {
			p.sendMessage(ChatColor.BLUE + "[Battle] " + ChatColor.WHITE + "No suitable street battle location found!");
			return;
		}
		
		for (BattleData bData : allBattles) {
			if (battleKey.distance(bData.getKey()) < 10) {
				p.sendMessage(ChatColor.BLUE + "[Battle] " + ChatColor.WHITE + "Too close to an existing battle!");
				return;
			}
		}
		
		BattleData battleData = new BattleData(main, playerUUID, npcUUID, battleKey, battleLoc);
		allBattles.add(battleData);

		//NPC npc = main.registry.getByUniqueId(npcUUID); Moved up for new battle posing sys
		((LinearWaypointProvider) npc.getOrAddTrait(Waypoints.class).getCurrentProvider()).setPaused(true);
		//LinearWaypointProvider lwp;
		String npcName = npc.getFullName();
		String levelStr = npcName.replaceAll("[^0-9]", "");
		int level = Integer.parseInt(levelStr);
		Cog cog = new Cog(level, npc.getFullName(), npc.getEntity(), npc);
		battleData.addCog(npcUUID, cog);
		Player player = Bukkit.getPlayer(playerUUID);
		Toon toon = battleData.getBattleToon(playerUUID);
		// tempToon.loadToonGagAmounts(); FUCK THIS LINE
		// NPC npc = main.registry.getByUniqueId(npcs.get(0));
		
		
		/* Replacing to implement new street battle posing sys
		Location npcL = npc.getStoredLocation();
		Vector v = npcL.getDirection().clone();
		v.setY(0);
		v.normalize();
		v.multiply(5);
		Location l = npcL.add(v);
		Vector lv = v.multiply(-1);
		l.setDirection(lv);
		l.add(2.0, 0.0, 0.0);
		player.teleport(l);
		*/
		
		npc.teleport(battleLoc, TeleportCause.PLUGIN);
		Location playerLoc = battleLoc.clone();
		
		//Player Yaw calculations
		if (battleLoc.getYaw() > 50) {
			playerLoc.setYaw(battleLoc.getYaw()-180);// = anchorLocation.getYaw() - 180;
		}
		else {
			playerLoc.setYaw(battleLoc.getYaw()+180);
		}
		
		//Player position calculations
		if (battleLoc.getYaw() == 180) {
			playerLoc.add(0, 0, -3);
		}
		else if (battleLoc.getYaw() == -90) {
			playerLoc.add(3, 0, 0);
		}
		else if (battleLoc.getYaw() == 0) {
			playerLoc.add(0, 0, 3);
		}
		else if (battleLoc.getYaw() == 90) {
			playerLoc.add(-3, 0, 0);
		}
		player.teleport(playerLoc);
		
		
		// playerAmountDirectionChange += 2.0;
		// tempToon.loadToonGags(); Commented and changed to below 1/15/22 11:33pm
		toon.refreshToonGags();
		toon.getToon().sendMessage("Loaded toon gags before starting battle!");
		player.getInventory().setContents(toon.getGag2Inventory().getContents());
		openInventoryLater(player, toon.getGagInventory());
	}

	//OUTDATED?
	public boolean startStreetBattle(List<UUID> players, List<UUID> npcs) { // Made for switching to new "Toon" object
																			// with stored inventory

		Player p = Bukkit.getPlayer(players.get(0));
		Location battleKey = p.getLocation();

		for (BattleData tempData : allBattles) { // Makes sure there is no battle nearby
			if (battleKey.distance(tempData.getKey()) < 10) {
				p.sendMessage(ChatColor.RED + "Battle is too close to an existing battle!");
				return false;
			}
		}
		

		BattleData battleData = new BattleData(main, players, npcs, battleKey);
		allBattles.add(battleData);

		for (UUID npcUUID : npcs) {
			NPC npc = main.registry.getByUniqueId(npcUUID);
			String npcName = npc.getFullName();
			String levelStr = npcName.replaceAll("[^0-9]", "");
			int level = Integer.parseInt(levelStr);
			Cog tempCog = new Cog(level, npc.getFullName(), npc.getEntity(), npc);
			battleData.addCog(npcUUID, tempCog);
		}

		// double playerAmountDirectionChange = 0.0;
		for (UUID pUUID : players) {
			Player tempP = Bukkit.getPlayer(pUUID);
			Toon tempToon = battleData.getBattleToon(pUUID);
			// tempToon.loadToonGagAmounts(); FUCK THIS LINE
			NPC npc = main.registry.getByUniqueId(npcs.get(0));
			Location npcL = npc.getStoredLocation();
			Vector v = npcL.getDirection().clone();
			v.setY(0);
			v.normalize();
			v.multiply(5);
			Location l = npcL.add(v);
			Vector lv = v.multiply(-1);
			l.setDirection(lv);
			l.add(2.0, 0.0, 0.0);
			tempP.teleport(l);
			// playerAmountDirectionChange += 2.0;
			// tempToon.loadToonGags(); Commented and changed to below 1/15/22 11:33pm
			tempToon.refreshToonGags();
			tempToon.getToon().sendMessage("Loaded toon gags before starting battle!");
			tempP.getInventory().setContents(tempToon.getGag2Inventory().getContents());
			openInventoryLater(tempP, tempToon.getGagInventory());
		}

		return false;
	}

	public void joinBattle2(UUID playerUUID, BattleData inputBattleData) {
		int playersInBattle = inputBattleData.getBattlePlayerList().size();
		// int loopAmount = 0;
		if (playersInBattle < 4) {
			if (inputBattleData.isPlayersTurn() && !inputBattleData.playersDoneChoosing()) {
				inputBattleData.addPlayerActiveBattle(playerUUID);
			} else {
				inputBattleData.addPlayerQueue(playerUUID);
				Bukkit.getPlayer(playerUUID).sendMessage(
						ChatColor.BLUE + "[Battle] " + ChatColor.WHITE + "Waiting until the player's turn");
			}
			inputBattleData.sendAllMessage(ChatColor.BLUE + "[Battle] " + ChatColor.WHITE
					+ Bukkit.getPlayer(playerUUID).getName() + " has joined the battle");
		} else {
			Bukkit.getPlayer(playerUUID)
					.sendMessage(ChatColor.BLUE + "[Battle] " + ChatColor.WHITE + "This battle is full");
		}
	}

	//No longer needed? 9-20-22
	public void joinBattle(List<UUID> iPlayers, List<UUID> iNpcs, BattleData inputBattleData) {
		int playersInBattle = inputBattleData.getBattlePlayerList().size();
		inputBattleData.sendAllMessage("Size of list of players to join: " + iPlayers.size());
		List<UUID> playersToRemove = new ArrayList<>();
		int loopAmount = 0;
		while (playersInBattle < 4 && loopAmount < iPlayers.size()) {
			for (UUID pUUID : iPlayers) {
				if (inputBattleData.isPlayersTurn() && !inputBattleData.playersDoneChoosing())
					inputBattleData.addPlayerActiveBattle(pUUID);
				else
					inputBattleData.addPlayerQueue(pUUID);
				playersToRemove.add(pUUID);
				inputBattleData.sendAllMessage(ChatColor.BLUE + "[Battle] " + ChatColor.WHITE
						+ Bukkit.getPlayer(pUUID).getName() + " has joined the battle!");
				Bukkit.getPlayer(pUUID).sendMessage(ChatColor.BLUE + "[Battle] " + ChatColor.WHITE
						+ "Please wait until the player's turn to play!");
				playersInBattle++;
				loopAmount++;
			}
		}
		iPlayers.removeAll(playersToRemove);

		if (playersInBattle == 4) {
			for (UUID pUUID : iPlayers) {
				Bukkit.getPlayer(pUUID)
						.sendMessage(ChatColor.BLUE + "[Battle] " + ChatColor.WHITE + "This battle is full");
			}
		}
	}

	public void activateTrap(BattleData inputBattleData, UUID cog) {
		Gag tempTrap = inputBattleData.getCogTrap(cog);
		Cog tempCog = inputBattleData.getCog(cog);
		Player tempPlayer = Bukkit.getPlayer(tempTrap.getOwner());
		System.out.println("Trap activated!");
		inputBattleData.removeTrap(tempTrap.getOwner(), cog);
		inputBattleData.addPlayerExp(tempTrap.getOwner(), tempTrap.getTrack(), tempTrap.getExp());
		inputBattleData.unLure(cog);
		int entityLife = tempCog.getCogHealth() / 2;
		entityLife -= tempTrap.getDamage();
		if (entityLife < 0)
			entityLife = 0;

		inputBattleData.sendAllMessage(ChatColor.BLUE + "[Battle] " + ChatColor.WHITE + tempPlayer.getName() + " did "
				+ String.valueOf(tempTrap.getDamage()) + " damage to " + tempCog.getCogFullName() + " [" + ChatColor.RED
				+ entityLife + ChatColor.WHITE + "]");

		if (entityLife == 0) {
			cogDead(cog, inputBattleData);
		} else {
			tempCog.setCogHealth(entityLife * 2);
		}

	}

	public static BattleData getBattleDataGeneral(Location l) { //
		for (BattleData data : allBattles) {
			if (data.getKey().distance(l) < 10) {
				return data;
			}
		}
		return null;
	}

	public static BattleData getBattleDataNPC(NPC npc) {
		for (BattleData data : allBattles) {
			if (data.getKey().distance(npc.getStoredLocation()) < 10) {
				if (data.getBattleNPCList().contains(npc.getUniqueId()))
					return data;
			}
		}
		return null;
	}

	public static BattleData getBattleDataPlayer(Player p) {
		for (BattleData data : allBattles) {
			if (data.getKey().distance(p.getLocation()) < 10) {
				if (data.getBattlePlayerList().contains(p.getUniqueId()))
					return data;
			}
		}
		return null;
	}

	public void nextBattleRound(BattleData inputBattleData, boolean playersTurn) {

		inputBattleData.setPlayersTurn(playersTurn);
		System.out.println("-----nextBattleRound ran!");
		if (playersTurn) {
			if (!inputBattleData.getPlayersQueue().isEmpty()) {
				inputBattleData.addPlayerActiveBattle(inputBattleData.getPlayersQueue());
				// inputBattleData.removePlayerQueue(inputBattleData.getPlayerQueue());
			}
			inputBattleData.resetPlayerTargets();
			List<UUID> tempPlayers = inputBattleData.getBattlePlayerList();
			for (UUID tempUUID : tempPlayers) {
				Player tempPlayer = Bukkit.getPlayer(tempUUID);
				Toon tempToon = inputBattleData.getBattleToon(tempUUID);
				tempPlayer.getInventory().setContents(tempToon.getGag2Inventory().getContents());
				openInventoryLater(tempPlayer, tempToon.getGagInventory());
			}
			finishPlayerRoundLater(inputBattleData);
		}

		else {
			if (inputBattleData.isSpecialMode()) // WIP: For buildings, bosses, etc
				inputBattleData.getCogBuilding().nextBuildingRound();
			chooseCogAttack(inputBattleData);
		}
	}

	public void chooseCogAttack(BattleData inputBattleData) {
		List<UUID> cogs = inputBattleData.getBattleNPCList();
		int atkFreq = 0;
		int newAtkFreq = 0;
		for (UUID cogUUID : cogs) {
			Cog tempCog = inputBattleData.getCog(cogUUID);
			double r = (Math.random() * (99 - 0) + 0);
			for (int i = 0; i < tempCog.getNumOfCogAttacks(); i++) {
				newAtkFreq += tempCog.getAttackFreq(i);
				if (r > atkFreq && r < newAtkFreq) {
					// choosenAttack = i;
					tempCog.setSelectedAttack(i);
					break;
				}
				atkFreq += tempCog.getAttackFreq(i);
			}
		}
		inputBattleData.nextLureRound();
		cogAttack(inputBattleData);
	}

	public void playerDead(Player inputPlayer, BattleData inputBattleData) {
		final PotionEffect slownessPotion = new PotionEffect(PotionEffectType.SLOW, 100, 3);
		inputPlayer.sendMessage("You died!");
		inputPlayer.setHealth(1);
		inputPlayer.addPotionEffect(slownessPotion);
		// inputPlayer.teleport(inputPlayer.getBedSpawnLocation());
		// inputPlayer.teleport();
		inputBattleData.removePlayer(inputPlayer.getUniqueId());
		if (inputBattleData.getBattlePlayerList().isEmpty())
			finishBattle(inputBattleData, true);
		// inputPlayer.addPotionEffect(slownessPotion, true);
	}

	public boolean cogDead(UUID cogUUID, BattleData inputBattleData) {
		Cog tempCog = inputBattleData.getCog(cogUUID);
		// List<UUID> playersWhoDestroyed = inputBattleData.getBattlePlayerList();
		inputBattleData.sendAllMessage(
				ChatColor.BLUE + "[Battle] " + ChatColor.WHITE + tempCog.getJustCogName() + " has been defeated!");
		inputBattleData.removeNPC(cogUUID);
		inputBattleData.addDeadCog(tempCog);
		main.registry.getByUniqueId(cogUUID).despawn();
		if (inputBattleData.getBattleNPCList().isEmpty()) {
			finishBattle(inputBattleData, false);
			return true;
		}
		return false;
	}

	public void finishBattle(BattleData inputBattleData, boolean playersLost) {
		// Bukkit.getServer().broadcastMessage("finishBattle has been called!");

		if (inputBattleData.isSpecialMode() && !playersLost) { // Special Mode WIP: For buildings, bosses, factories,
																// etc
			inputBattleData.sendAllMessage(ChatColor.RED + "[DEBUG] " + ChatColor.WHITE
					+ "Special Mode On! Keeping battle active! Calling next floor...");
			// inputBattleData.getCogBuilding().nextFloor();
			// Instead of calling next floor, spawn next floor NPC and start counter to
			// kickout those who dont goto next floor when it ends
			// inputBattleData.getCogBuilding().spawnNextFloorNPC();
			inputBattleData.getCogBuilding().startNextFloorTimer();
			if (inputBattleData.getCogBuilding().getLevels() != -1)
				return;
		}

		// List<UUID> tempPlayers = inputBattleData.getBattlePlayerList();
		// List<UUID> tempNpcs = inputBattleData.getBattleNPCList();
		// List<Toon> tempToons = new ArrayList<>();
		// List<Cog> tempCogs = new ArrayList<>();
		// for (UUID pUUID : tempPlayers)
		// tempToons.add(inputBattleData.getBattleToon(pUUID));
		// for (UUID cUUID : tempNpcs)
		// tempCogs.add(inputBattleData.getCog(cUUID));

		// BattleFinishEvent battleFinishEvent = new BattleFinishEvent(tempToons,
		// tempCogs);
		// Bukkit.getPluginManager().callEvent(battleFinishEvent);
		inputBattleData.battleOver();
		System.out.println("-----------Battle has been set to over!");

		if (!playersLost) {
			inputBattleData.sendAllMessage(ChatColor.BLUE + "[Battle] " + ChatColor.WHITE + "The battle has ended!");

			List<UUID> tempPlayers = inputBattleData.getBattlePlayerList();

			List<Toon> tempToons = new ArrayList<>();
			List<Cog> tempCogs = inputBattleData.getDeadCogs();
			for (UUID pUUID : tempPlayers)
				tempToons.add(inputBattleData.getBattleToon(pUUID));
			// for (UUID cUUID : tempNpcs)
			// tempCogs.add(inputBattleData.getCog(cUUID));
			for (Toon toon : tempToons) {
				for (Cog cog : tempCogs) {
					String cogType = cog.getCogType().toString();
					int index = CogType.valueOf(cogType).ordinal() + 1;
					int a = index % 8;
					int b = (int) Math.floor(index/8);
					toon.addCogGallery(b, (a-1), 1);
					System.out.println("Added Cog! a: " + a + " b: " + b);
				}
				if (toon.getShtickerBookSize() != 0)
					ShtickerBook.refreshGalleryPage(toon); //IF MORE PAGES HAVE BEEN ADDED !!!BEFORE!!! SINCE 2/19/22, THIS IS BROKEN
				//ShtickerBook.loadGalleryPage(toon);
			}
			BattleFinishEvent battleFinishEvent = new BattleFinishEvent(tempToons, tempCogs);
			Bukkit.getPluginManager().callEvent(battleFinishEvent);

			for (UUID pUUID : tempPlayers) {
				Toon pToon = inputBattleData.getBattleToon(pUUID);
				// pToon.updateAllToonGagAmounts(); Commented 12/9/21 6:33pm
				// PlayerData pData = pToon.getToonData();
				int[] playerBattleExps = { 0, 0, 0, 0, 0, 0, 0 };
				for (int i = 0; i < 7; i++) // { Commented 1/22/22 5:54pm to fix potential extra exp bug
					playerBattleExps[i] = (int) inputBattleData.getPlayerExp(pUUID, i);

				pToon.updateToonGagExp2(playerBattleExps); // Commented 12/9/21 6:33pm -- Uncommented 1/15/22 11:24pm
				// data.saveConfig();

				Player tempP = Bukkit.getPlayer(pUUID);
				tempP.closeInventory();
				tempP.sendMessage("Closed Inv. Battle Finished!");
				tempP.getInventory().clear(); // Added to remove any reminants of drop gags 1/21/22 4:13pm
				pToon.loadToonGagExpInv2();
				tempP.getInventory().setContents(pToon.getGagExp2Inventory().getContents());
				openInventoryLater(tempP, pToon.getGagExpInventory(), 40);
				// openWinInventory(tempP);
				// Players won, calc exp + task stuff
			}
		}
		// }

		else {
			if (inputBattleData.isSpecialMode()) {
				inputBattleData.getCogBuilding().finishBuilding(true);
			}

			inputBattleData.sendAllMessage(ChatColor.BLUE + "[Battle] " + ChatColor.WHITE + "You lost! :(");

			List<UUID> tempNpcs = inputBattleData.getBattleNPCList();

			// for (UUID pUUID : tempPlayers) {
			// Player tempPlayer = Bukkit.getPlayer(pUUID);
			// Location tempL = tempPlayer.getBedSpawnLocation();
			// tempPlayer.teleport(tempL);
			// //tempPlayer.setBe
			// }

			for (UUID npcUUID : tempNpcs) {
				main.registry.getByUniqueId(npcUUID).despawn();
			}
			// Cogs won, send all players back to playground, remove all remaining cogs
		}

		// for (UUID npcUUID : tempNpcs) {
		// if (inputBattleData.getCog(npcUUID) != null) {
		// Cog tempCog = inputBattleData.getCog(npcUUID);
		// tempCog = null;
		// }
		// }
		// inputBattleData.removePlayerBattle(tempNpcs);
		// inputBattleData.removeNPCBattle(tempNpcs);
		inputBattleData.deleteAll();

		allBattles.remove(inputBattleData);
		inputBattleData = null;

	}

	public void cogAttack(BattleData inputBattleData) {
		List<UUID> tempPlayers = inputBattleData.getBattlePlayerList();
		List<UUID> tempNpcs = inputBattleData.getBattleNPCList();
		int playerAmount = tempPlayers.size();

		for (UUID cogUUID : tempNpcs) {
			Cog tempCog = inputBattleData.getCog(cogUUID);
			if (inputBattleData.isLured(cogUUID)) {
				inputBattleData.sendAllMessage(ChatColor.BLUE + "[Battle] " + ChatColor.WHITE + tempCog.getJustCogName()
						+ " is lured. Skipping attack!");
				continue;
			}
			boolean missed = false;
			int selectedAttack = tempCog.getSelectedAttack();
			System.out.println("Attack Acc: " + tempCog.getAttackAcc(selectedAttack));
			System.out.println("Attack Dmg: " + tempCog.getAttackDmg(selectedAttack));
			System.out.println("Attack Freq: " + tempCog.getAttackFreq(selectedAttack));
			int random = (int) (Math.random() * 99 + 1);
			if (random < tempCog.getAttackAcc(selectedAttack) - 1)
				missed = true;
			inputBattleData.sendAllMessage(ChatColor.BLUE + "[Battle] " + ChatColor.WHITE + tempCog.getJustCogName()
					+ " used " + tempCog.getAttackName(selectedAttack));

			if (!missed) {
				List<UUID> targetList = new ArrayList<>();
				System.out.println("Selected Attack: " + selectedAttack);
				if (!tempCog.getAttackHitAll(selectedAttack)) {
					// System.out.println("Selected Attack: " + selectedAttack);
					System.out.println("Single Hit");
					int r = new Random().nextInt(playerAmount);
					targetList.add(tempPlayers.get(r));
					// UUID choosenPlayerUUID = tempPlayers.get(r);
					// Player choosenPlayer = Bukkit.getPlayer(tempPlayers.get(r));
				} else {
					System.out.println("All Hit");
					targetList.addAll(tempPlayers);
				}

				for (UUID pUUID : targetList) {
					Player p = Bukkit.getPlayer(pUUID);
					int playerLife = (int) p.getHealth() / 2;

					playerLife -= tempCog.getAttackDmg(selectedAttack);
					if (playerLife < 0)
						playerLife = 0;

					inputBattleData.sendAllMessage(ChatColor.BLUE + "[Battle] " + ChatColor.WHITE
							+ tempCog.getJustCogName() + " did " + String.valueOf(tempCog.getAttackDmg(selectedAttack))
							+ " damage to " + p.getName() + " [" + ChatColor.RED + playerLife + ChatColor.WHITE + "]");

					if (playerLife == 0) {
						playerDead(p, inputBattleData);
						System.out.println("A player died!");
					} else
						p.setHealth(playerLife * 2);
				}
			}

			else {
				inputBattleData.sendAllMessage(
						ChatColor.BLUE + "[Battle] " + ChatColor.WHITE + tempCog.getJustCogName() + " missed!");
			}
		}
		// inputBattleData.sendAllMessage("Starting next battle round!");
		startingNewRound(inputBattleData, true);
	}

	public void playerAttack(BattleData inputBattleData) {
		List<UUID> tempPlayers = inputBattleData.getBattlePlayerList();
		List<UUID> tempNpcs = inputBattleData.getBattleNPCList();
		HashMap<Integer, List<UUID>> attackOrder = new HashMap<Integer, List<UUID>>();
		System.out.println("PlayerAttack: Size of tempPlayers: " + tempPlayers.size());
		for (UUID pUUID : tempPlayers) { // Loops and groups player's UUID based on their selected gag track
			Bukkit.getPlayer(pUUID).closeInventory();
			Gag tempGag = inputBattleData.getGag(pUUID);
			int tempTrack = tempGag.getTrack() - 1;
			System.out.println("Player Gag Track: " + tempTrack);
			if (!attackOrder.containsKey(tempTrack)) {
				List<UUID> tempList = new ArrayList<UUID>();
				tempList.add(pUUID);

				attackOrder.put(tempTrack, tempList);
			} else {
				List<UUID> tempList = attackOrder.get(tempTrack);
				tempList.add(pUUID);
				attackOrder.put(tempTrack, tempList);
			}
		}
		for (int i = 0; i < 7; i++) {
			if (attackOrder.containsKey(i)) { // Check if each track has Player's UUID within, if so, contiues
				inputBattleData.sendAllMessage("Track " + (i + 1) + " now going!");
				boolean anyCogLured = false;
				double dmgMulti = 1;
				List<UUID> deadCogs = new ArrayList<UUID>();
				for (UUID pUUID : attackOrder.get(i)) {
					Player tempPlayer = Bukkit.getPlayer(pUUID);
					Gag tempGag = inputBattleData.getGag(pUUID);
					Toon tempToon = inputBattleData.getBattleToon(pUUID);
					tempToon.getToon().playSound(tempToon.getToon().getLocation(), tempGag.getSound(), 1f, 1f);
					tempToon.getToon().sendMessage(
							"Gag Track: " + tempGag.getTrack() + " NumInTrack: " + tempGag.getNumInTrack());
					tempToon.usedGagAmount(tempGag.getTrack() - 1, tempGag.getNumInTrack() - 1);
					List<UUID> targetList = new ArrayList<UUID>();
					boolean missed = false;

					Random r = new Random();
					if (r.nextInt(100) > tempGag.getAcc() - 1)
						missed = true;
					// System.out.println("GAG DEBUG");
					// System.out.println("Gag: " + tempGag.getGagName());
					// System.out.println("HitAll: " + tempGag.getHitAll());

					if (i != 0) { // If gag not toon up
						if (tempGag.getHitAll())
							targetList.addAll(tempNpcs);
						else
							targetList.add(tempNpcs.get(inputBattleData.getPlayerTarget(pUUID) - 1));
						for (UUID cogUUID : targetList) // Checks if any of the cogs are lured. If yes, can't miss.
														// Eventually implement adding "(cogs lured / total cogs) * 100"
														// to accuracy of gag
							if (inputBattleData.isLured(cogUUID)) {
								if (tempGag.getTrack() != 7) {
									missed = false;
									anyCogLured = true;
									dmgMulti = 1.5;
								} else
									missed = true;
							}
					}

					else { // Gag is toonup
						if (tempGag.getHitAll())
							targetList.addAll(tempPlayers);
						else
							targetList.add(tempPlayers.get(inputBattleData.getPlayerTarget(pUUID) - 1));
					}
					// System.out.println("targetList size: " + targetList.size());

					if (!missed) {

						if (i > 2) { // Drop, squirt, throw, sound in order
							for (UUID cogUUID : targetList) {
								Cog tempCog = inputBattleData.getCog(cogUUID);
								int entityLife = tempCog.getCogHealth() / 2;
								String npcName = tempCog.getJustCogName();

								entityLife -= tempGag.getDamage() * dmgMulti;
								if (entityLife < 0)
									entityLife = 0;

								inputBattleData.sendAllMessage(
										ChatColor.BLUE + "[Battle] " + ChatColor.WHITE + tempPlayer.getName() + " did "
												+ String.valueOf(tempGag.getDamage() * dmgMulti) + " damage to "
												+ npcName + " [" + ChatColor.RED + entityLife + ChatColor.WHITE + "]");
								if (entityLife == 0) {
									// inputBattleData.sendAllMessage(ChatColor.BLUE + "[Battle] " + ChatColor.WHITE
									// + npcName + " has been defeated!");
									deadCogs.add(cogUUID);
									// cogDead(cogUUID, inputBattleData);
								}

								else {
									tempCog.setCogHealth(entityLife * 2);
								}

							}
							inputBattleData.addPlayerExp(pUUID, tempGag.getTrack(), tempGag.getExp());

						} else if (i == 2) { // Lure

							for (UUID cogUUID : targetList) {
								Cog tempCog = inputBattleData.getCog(cogUUID);
								String npcName = tempCog.getJustCogName();

								System.out.println("-----------Player lured cog!");
								// inputBattleData.lure(pUUID, tempCogUUID);
								inputBattleData.lure(pUUID, cogUUID, tempGag.getRounds());
								inputBattleData.sendAllMessage(ChatColor.BLUE + "[Battle] " + ChatColor.WHITE
										+ tempPlayer.getName() + " lured " + npcName + "!");
								// inputBattleData.addPlayerExp(pUUID, tempGag.getTrack(), tempGag.getExp());
								if (inputBattleData.isTrapped(cogUUID))
									activateTrap(inputBattleData, cogUUID);
							}
							inputBattleData.addPlayerExp(pUUID, tempGag.getTrack(), tempGag.getExp());

						} else if (i == 1) { // Trap
							for (UUID cogUUID : targetList) {
								Cog tempCog = inputBattleData.getCog(cogUUID);
								String npcName = tempCog.getJustCogName();
								inputBattleData.placeTrap(pUUID, cogUUID);
								inputBattleData.sendAllMessage(ChatColor.BLUE + "[Battle] " + ChatColor.WHITE
										+ tempPlayer.getName() + " placed a trap in front of " + npcName + "!");
								System.out.println("---------------Player placed trap!");
							}

						} else { // Toonup
							for (UUID playerUUID : targetList) {
								Player tempPlayerHealed = Bukkit.getPlayer(playerUUID);
								int entityLife = (int) tempPlayerHealed.getHealth() / 2;
								int maxEntityLife = (Integer) inputBattleData.getBattleToon(playerUUID).getMaxHealth();
								entityLife += tempGag.getHeal();
								if (entityLife > maxEntityLife)
									entityLife = maxEntityLife;
								inputBattleData.sendAllMessage(ChatColor.BLUE + "[Battle] " + ChatColor.WHITE
										+ tempPlayer.getName() + " healed 6 points to " + tempPlayerHealed.getName()
										+ " [" + ChatColor.LIGHT_PURPLE + entityLife + ChatColor.WHITE + "]");
								tempPlayerHealed.setHealth(entityLife * 2);
							}
							inputBattleData.addPlayerExp(pUUID, tempGag.getTrack(), tempGag.getExp());

						}
					} else {
						inputBattleData.sendAllMessage(
								ChatColor.BLUE + "[Battle] " + ChatColor.WHITE + tempPlayer.getName() + " missed!");
					}

					if (!deadCogs.isEmpty() /* && !inputBattleData.isNpcsEmpty() */) { // Commented 1/17/21 8:34pm for
																						// testing, may not need?
						boolean toReturn = false;
						for (UUID cUUID : deadCogs) {
							// System.out.println("CogDead1");
							if (cogDead(cUUID, inputBattleData)) // To fix long standing getJustCogName() error?!??
																	// 1/21/22 5:40pm
								toReturn = true;
						}
						if (toReturn == true) // Changed to return here so it loops through all dead cogs 1/27/22 4:26pm
							return;
					}
					if (anyCogLured) {
						for (UUID cogUUID : targetList) {
							if (inputBattleData.isLured(cogUUID)) {
								// if (isCogDead()) Maybe add isCogDead check here so it doesn't say its unlured
								// when the hit kills it?
								inputBattleData.sendAllMessage(ChatColor.BLUE + "[Battle] " + ChatColor.WHITE
										+ inputBattleData.getCog(cogUUID).getCogFullName() + " has been unlured!");
								inputBattleData.unLure(cogUUID);
							}
						}
					}
				}
			}
		}
		startingNewRound(inputBattleData, false);
	}

	@EventHandler
	public void onGUIClick(final InventoryClickEvent e) {

		if (!e.getView().getTitle().contains("Choose Target") && !e.getView().getTitle().contains("Waiting for")
				&& !e.getView().getTitle().contains("Gags") && !e.getView().getTitle().contains("Exp")
				&& !(e.getInventory().contains(Material.LIGHT_BLUE_CONCRETE) && e.getRawSlot() > 53
						&& !e.getView().getTitle().contains("Map"))) // Last line includes drop gags
			return;
		e.setCancelled(true);

		final Player p = (Player) e.getWhoClicked();
		BattleData battleData = getBattleDataPlayer(p);
		if (battleData == null) {
			p.sendMessage("You have no battle data!");
			return;
		}
		// if (e.getView().getTitle().contains("Gags"))
		if (e.getView().getTitle().contains("Gags")
				|| (e.getInventory().contains(Material.LIGHT_BLUE_CONCRETE) && e.getRawSlot() > 53)) { // Checks if gag
																										// inventory or
																										// player
																										// inventory has
																										// gags
			// if (e.getView().getTitle().contains("Gags") ||
			// e.getInventory().contains(Material.LIGHT_BLUE_CONCRETE)) { //Last line checks
			// for start of drop gags, to include
			final ItemStack clickedItem = e.getCurrentItem();
			if (clickedItem == null || clickedItem.getType() == Material.BARRIER) // Checks if null or if player is
																					// clicking barrier (out of that
																					// gag)
				return;
			outerloop: for (int i = 1; i < 8; i++) {
				for (int j = 0; j < 7; j++) {
					if (ChatColor.stripColor(clickedItem.getItemMeta().getDisplayName()).equals(gagNames[i][j])) {
						// p.sendMessage("You clicked on a gag in track: " + j);
						switch (j + 1) {
						case 1:
							Gag toonupGag = Toonup.useToonupGag(p.getUniqueId(), main, gagNames[i][j], j + 1, i);

							battleData.addGag(p.getUniqueId(), toonupGag);
							if (!toonupGag.getHitAll())
								openTargetInventory(p, battleData, true);
							else {
								battleData.setPlayerTarget(p.getUniqueId(), 0);
								openGagLobbyInventory(p, battleData);
							}
							break;
						case 2:
							Gag trapGag = Trap.useTrapGag(p.getUniqueId(), main, gagNames[i][j], j + 1, i);
							battleData.addGag(p.getUniqueId(), trapGag);
							if (!trapGag.getHitAll())
								openTargetInventory(p, battleData, false);
							else {
								battleData.setPlayerTarget(p.getUniqueId(), 0);
								openGagLobbyInventory(p, battleData);
							}
							break;
						case 3:
							Gag lureGag = Lure.useLureGag(p.getUniqueId(), main, gagNames[i][j], j + 1, i);
							battleData.addGag(p.getUniqueId(), lureGag);
							if (!lureGag.getHitAll())
								openTargetInventory(p, battleData, false);
							else {
								battleData.setPlayerTarget(p.getUniqueId(), 0);
								openGagLobbyInventory(p, battleData);
							}
							break;
						case 4:
							Gag soundGag = Sound.useSoundGag(p.getUniqueId(), main, gagNames[i][j], j + 1, i);
							battleData.addGag(p.getUniqueId(), soundGag);
							battleData.setPlayerTarget(p.getUniqueId(), 0);
							openGagLobbyInventory(p, battleData);
							break;
						case 5:
							Gag throwGag = Throw.useThrowGag(p.getUniqueId(), main, gagNames[i][j], j + 1, i);
							battleData.addGag(p.getUniqueId(), throwGag);
							if (!throwGag.getHitAll())
								openTargetInventory(p, battleData, false);
							else {
								battleData.setPlayerTarget(p.getUniqueId(), 0);
								openGagLobbyInventory(p, battleData);
							}
							break;
						case 6:
							Gag squirtGag = Squirt.useSquirtGag(p.getUniqueId(), main, gagNames[i][j], j + 1, i);
							battleData.addGag(p.getUniqueId(), squirtGag);
							if (!squirtGag.getHitAll())
								openTargetInventory(p, battleData, false);
							else {
								battleData.setPlayerTarget(p.getUniqueId(), 0);
								openGagLobbyInventory(p, battleData);
							}
							break;
						case 7:
							Gag dropGag = Drop.useDropGag(p.getUniqueId(), main, gagNames[i][j], j + 1, i);
							battleData.addGag(p.getUniqueId(), dropGag);
							if (!dropGag.getHitAll())
								openTargetInventory(p, battleData, false);
							else {
								battleData.setPlayerTarget(p.getUniqueId(), 0);
								openGagLobbyInventory(p, battleData);
							}
							break;
						default:
							System.out.println("Null default");
							break;
						}
						break outerloop; // Maybe remove? Check if works without
					}
				}
			}
		}

		else if (e.getView().getTitle().contains("Choose Target")) {
			int selectedGag = (int) e.getRawSlot();
			if (selectedGag < 1 || selectedGag > 7)
				return;
			if (e.getCurrentItem().getType() != Material.RED_STAINED_GLASS_PANE)
				return;

			switch (selectedGag) {
			case 1:
				battleData.setPlayerTarget(p.getUniqueId(), 1);
				openGagLobbyInventory(p, battleData);
				break;
			case 3:
				battleData.setPlayerTarget(p.getUniqueId(), 2);
				openGagLobbyInventory(p, battleData);
				break;
			case 5:
				battleData.setPlayerTarget(p.getUniqueId(), 3);
				openGagLobbyInventory(p, battleData);
				break;
			case 7:
				battleData.setPlayerTarget(p.getUniqueId(), 4);
				openGagLobbyInventory(p, battleData);
				break;
			default:
				System.out.println("ERROR: Player clicked an unclickable target! (onGUIClick)");
				break;
			}
		}

	}

	@EventHandler
	public void onInventoryDrag(final InventoryDragEvent e) {
		// if (e.getInventory().equals(targetInv) || e.getIn)
		if (e.getView().getTitle().contains("Choose Target") || e.getView().getTitle().contains("Exp")
				|| e.getView().getTitle().contains("Waiting for") || e.getView().getTitle().contains("Exp")
				|| e.getInventory().contains(Material.LIGHT_BLUE_CONCRETE)) {
			e.setCancelled(true);
		}
	}

	@EventHandler
	public void onNPCRightClick(NPCRightClickEvent e) {
		NPC npc = e.getNPC();
		Player p = e.getClicker();
		String unformattedNPCName = ChatColor.stripColor(npc.getFullName());
		if (unformattedNPCName.contains("Level")) {
			boolean playerInBattle = false;
			boolean cogInBattle = false;
			BattleData battleData = getBattleDataPlayer(p);

			if (battleData == null)
				battleData = getBattleDataNPC(npc);

			if (battleData != null) {
				if (battleData.getBattlePlayerList().contains(p.getUniqueId()))
					playerInBattle = true;
				if (battleData.getBattleNPCList().contains(npc.getUniqueId()))
					cogInBattle = true;
			}
			// p.sendMessage("You right clicked a cog!");
			// List<UUID> players = new ArrayList<UUID>();
			// List<UUID> npcs = new ArrayList<UUID>();
			// players.add(p.getUniqueId());
			// npcs.add(npc.getUniqueId());
			if (playerInBattle) {
				p.sendMessage("You're already in battle!");
			}

			else if (cogInBattle) {
				// joinBattle(players, npcs, battleData);
				joinBattle2(p.getUniqueId(), battleData);
			}

			else {
				//npc.getNavigator().getPathStrategy().stop();
				// startStreetBattle(players, npcs); Commented 2/15/22 9:33pm so change method
				// to accept a single uuid for player and npc
				startStreetBattle(p.getUniqueId(), npc.getUniqueId());
			}
		}
	}

	@EventHandler
	public void onNPCCollision(NPCCollisionEvent e) {
		if (!(e.getCollidedWith() instanceof Player)) return;

		NPC npc = e.getNPC();
		BattleData battleData = null;

		if (e.getCollidedWith().hasMetadata("NPC")) { // Collided with NPC
			NPCHolder npcH = (NPCHolder) e.getCollidedWith();
			NPC collidedNPC = npcH.getNPC();
			if (getBattleDataNPC(collidedNPC) == null) return;

			battleData = getBattleDataNPC(collidedNPC);
		}

		else { // Collided with player
			Player collidedPlayer = (Player) e.getCollidedWith();
			if (getBattleDataPlayer(collidedPlayer) == null) return;

			battleData = getBattleDataPlayer(collidedPlayer);
		}

		if (battleData != null) { // Battle Exists
			//npc.getNavigator().getPathStrategy().stop() Replacing with working line below
			((LinearWaypointProvider) npc.getOrAddTrait(Waypoints.class).getCurrentProvider()).setPaused(true);

			int totalCogs = battleData.getDeadCogAmount() + battleData.getCurrentCogAmount();
			int totalToons = battleData.getCurrentPlayerAmount();
			int jChance = jChances[(totalToons - totalCogs) + 2];

			Random r = new Random();
			int rolledNum = r.nextInt(100 - 1) + 1;

			if (rolledNum <= jChance && battleData.getBattleNPCList().size() < 4) { // rolledNum lets them join and less
																					// than 4 cogs in battle
				battleData.addNPC(npc.getUniqueId());
			} else {
				npc.despawn();
			}
		}
	}

	@EventHandler
	public void onPlayerMoveInBattle(PlayerMoveEvent e) {
		if (e.getTo().getBlockX() == e.getFrom().getBlockX() && e.getTo().getBlockY() == e.getFrom().getBlockY()
				&& e.getTo().getBlockZ() == e.getFrom().getBlockZ())
			return; // The player hasn't moved
		BattleData battleData = getBattleDataPlayer(e.getPlayer());
		if (battleData != null) {
			e.getPlayer().sendMessage("Has battle data! Cant move!");
			// e.setCancelled(true); Commented 2/6/22 11am for cog building testing!!!!
		}

	}

	public void startTimer(final UUID player) {
		countdowns.put(player, Bukkit.getScheduler().runTaskTimer(main, new Runnable() {
			int i = 21;

			// @Override
			public void run() {
				Player p = Bukkit.getPlayer(player);
				i--;
				if (i > 0) {
					ItemStack it = new ItemStack(Material.PAPER, i);
					ItemMeta im = it.getItemMeta();
					im.setDisplayName("CountDown");
					it.setItemMeta(im);
					p.getInventory().setItem(0, it);
				} else {
					p.closeInventory();
					stopTimer(player);
					countdowns.remove(player);
					Bukkit.getScheduler().runTaskLater(main, new Runnable() {
						@Override
						public void run() {
							// nextPlRound(player, true);
						}
					}, 20L);
				}
			}
		}, 0, 20).getTaskId());
	}

	public void stopTimer(UUID player) {
		Bukkit.getScheduler().cancelTask(countdowns.get(player));
		// Bukkit.getPlayer(player).getInventory().setItem(0, new
		// ItemStack(Material.BLACK_STAINED_GLASS_PANE, 1, (byte) 15));
		// Bukkit.getPlayer(player).getInventory().setItem(0,
		// createGuiItem(Material.BLACK_STAINED_GLASS_PANE, ChatColor.RED + "" +
		// "Countdown");
	}

	public void startingNewRound(BattleData inputBattleData, boolean playersTurn) {
		new BukkitRunnable() {

			@Override
			public void run() {
				System.out.println("startingnewRound-battleOver: " + String.valueOf(inputBattleData.isBattleOver()));
				if (!inputBattleData.isBattleOver())
					nextBattleRound(inputBattleData, playersTurn);
			}

		}.runTaskLater(main, 40);
	}

	public static void openInventoryLater(HumanEntity e, Inventory inv) {
		new BukkitRunnable() {
			@Override
			public void run() {
				if (inv == null) return; //Fix? Added 4-21-22 3:41pm
				e.openInventory(inv);
			}
		}.runTaskLater(main, 1);
	}

	public void openInventoryLater(HumanEntity e, Inventory inv, long ticks) {
		new BukkitRunnable() {
			@Override
			public void run() {
				e.openInventory(inv);
			}
		}.runTaskLater(main, ticks);
	}

	public void closeInventoryLater(HumanEntity e) {
		new BukkitRunnable() {
			@Override
			public void run() {
				if (e != null)
					e.closeInventory();
			}
		}.runTaskLater(main, 1);
	}

	public void closeInventoryLater(HumanEntity e, long ticks) {
		new BukkitRunnable() {
			@Override
			public void run() {
				if (e != null)
					e.closeInventory();
			}
		}.runTaskLater(main, ticks);
	}

	public void finishPlayerRoundLater(BattleData inputBattleData) {
		new BukkitRunnable() {
			@Override
			public void run() {
				if (inputBattleData != null)
					inputBattleData.finishPlayerRound();
			}

		}.runTaskLater(main, 3);
	}

	@EventHandler
	public void onBattleInvsClosed(InventoryCloseEvent e) {
		if (!(e.getPlayer() instanceof Player))
			return;
		if (!e.getView().getTitle().contains("Gags") && !e.getView().getTitle().contains("Target")
				&& !e.getView().getTitle().contains("Exp") && !e.getView().getTitle().contains("Waiting for"))
			return;
		if (e.getView().getTitle().contains("Exp")) {
			e.getPlayer().getInventory().clear();
			return;
		}
		if (getBattleDataPlayer((Player) e.getPlayer()) == null)
			return;
		BattleData battleData = getBattleDataPlayer((Player) e.getPlayer());
		if (!battleData.isInBattle(e.getPlayer().getUniqueId()))
			return;
		new BukkitRunnable() {
			@Override
			public void run() {
				// e.getPlayer().sendMessage("Now running onGagClosed!");
				// if (getBattleDataPlayer((Player) e.getPlayer()) == null)
				// return;

				// BattleData battleData = getBattleDataPlayer((Player) e.getPlayer());

				// if (!battleData.isInBattle(e.getPlayer().getUniqueId()))
				// return;
				if (e.getInventory() == null) return;
				
				if (e.getView().getTitle().contains("Gags")) {
					if (!battleData.getPlayerGagSelected(e.getPlayer().getUniqueId()) && !battleData.isBattleOver())
						openInventoryLater(e.getPlayer(),
								ToonsController.getToon(e.getPlayer().getUniqueId()).getGagInventory());
				} else if (e.getView().getTitle().contains("Target")) {
					if (!battleData.playerDoneChoosing(e.getPlayer().getUniqueId()))
						openInventoryLater(e.getPlayer(), battleData.getPlayerTargetInv(e.getPlayer().getUniqueId()));
				} else if (e.getView().getTitle().contains("Waiting for")) {
					if (!battleData.playersDoneChoosing())
						openInventoryLater(e.getPlayer(), battleData.getPlayerLobbyInv(e.getPlayer().getUniqueId()));
				}
				/*
				 * System.out.println("----Out 6"); switch(e.getView().getTitle()) {
				 * case("Gags"): System.out.println("----Out 7"); if
				 * (!battleData.getPlayerGagSelected(e.getPlayer().getUniqueId()))
				 * openInventoryLater(e.getPlayer(),
				 * ToonsController.getToon(e.getPlayer().getUniqueId()).getGagInventory());
				 * break; case("Target"): System.out.println("----Out 8"); if
				 * (!battleData.playerDoneChoosing(e.getPlayer().getUniqueId()))
				 * openInventoryLater(e.getPlayer(),
				 * battleData.getPlayerTargetInv(e.getPlayer().getUniqueId())); break;
				 * case("Waiting for"): System.out.println("----Out 9"); if
				 * (!battleData.playersDoneChoosing()) openInventoryLater(e.getPlayer(),
				 * battleData.getPlayerLobbyInv(e.getPlayer().getUniqueId())); break; default:
				 * break; } System.out.println("----Out 10");
				 */
				/*
				 * if (e.getView().getTitle().contains("Gags")) { if
				 * (!battleData.getPlayerGagSelected(e.getPlayer().getUniqueId()))
				 * openInventoryLater(e.getPlayer(),
				 * ToonsController.getToon(e.getPlayer().getUniqueId()).getGagInventory()); }
				 * else if (e.getView().getTitle().contains("Target")) { if
				 * (!battleData.playerDoneChoosing(e.getPlayer().getUniqueId())) {
				 * System.out.println("Player closed Target inv too soon! Reopening...");
				 * openInventoryLater(e.getPlayer(), targetInv); } }
				 */
				// else if (e.getView().getTitle().contains(""))

			}
		}.runTaskLater(main, 1);
	}
}
