package me.WesBag.TTCore.BattleMenu.Toons;

import java.util.Arrays;
import java.util.HashMap;
import java.util.UUID;
import java.util.logging.Level;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.sk89q.worldguard.protection.regions.ProtectedRegion;

import me.WesBag.TTCore.Main;
import me.WesBag.TTCore.BattleMenu.BattleMenu;
import me.WesBag.TTCore.Commands.AdminCommands.IsIntUtil;
import me.WesBag.TTCore.Files.CogType;
import me.WesBag.TTCore.Files.PlayerData;
import me.WesBag.TTCore.Files.PlayerDataController;
import me.WesBag.TTCore.Fishing.FishController;
import me.WesBag.TTCore.Skins.SkinGrabber;
import me.blackvein.quests.Quests;
import net.md_5.bungee.api.ChatColor;

public class Toon {
	
	//private static String[] toonGagNames = {"toonup", "trap", "lure", "sound", "throw", "squirt", "drop"};
	
	public static Quests qp = (Quests) Bukkit.getServer().getPluginManager().getPlugin("Quests");
	
	public final static Material[][] toonGagItems = new Material[][] {
		{ (Material.FEATHER), (Material.DIRT), (Material.STICK), (Material.STICK), (Material.STICK), (Material.STICK), (Material.STICK) }, // Level 1 gag Materials
		{ (Material.FEATHER), (Material.STICK), (Material.STICK), (Material.STICK), (Material.STICK), (Material.STICK), (Material.STICK) }, // Level 2 gag Materials and so on
		{ (Material.FEATHER), (Material.STICK), (Material.STICK), (Material.STICK), (Material.STICK), (Material.STICK), (Material.STICK) },
		{ (Material.FEATHER), (Material.STICK), (Material.STICK), (Material.STICK), (Material.STICK), (Material.STICK), (Material.STICK) },
		{ (Material.FEATHER), (Material.STICK), (Material.STICK), (Material.STICK), (Material.STICK), (Material.STICK), (Material.STICK) },
		{ (Material.FEATHER), (Material.STICK), (Material.STICK), (Material.STICK), (Material.STICK), (Material.STICK), (Material.STICK) },
		{ (Material.FEATHER), (Material.STICK), (Material.STICK), (Material.STICK), (Material.STICK), (Material.STICK), (Material.STICK) },
	};
	
	public final static String[][] toonGagNames = new String[][] { { "toonup", "trap", "lure", "sound", "throw", "squirt", "drop" },
		{ "Feather", "Banana-Peel", "Dollar-Bill", "Bike-Horn", "Cupcake", "Squirting-Flower", "Flower-Pot" },
		{ "Megaphone", "Rake", "Small-Magnet", "Whistle", "Fruit-Pie-Slice", "Glass-Of-Water", "Sandbag" },
		{ "Lipstick", "Marbles", "Five-Dollar-Bill", "Trumpet", "Cream-Pie-Slice", "Squirt-Gun", "Anvil" },
		{ "Bamboo-Cane", "Quicksand", "Big-Magnet", "Aoogah", "Fruit-Pie", "Seltzer-Bottle", "Big-Weight" },
		{ "Pixie-Dust", "Trapdoor", "Ten-Dollar-Bill", "Elephant-Trunk", "Cream-Pie", "Fire-Hose", "Safe" },
		{ "Juggling-Balls", "TNT", "Hypno-Goggles", "Fog-Horn", "Birthday-Cake", "Storm-Cloud", "Grand-Piano" },
		{ "High-Dive", "Railroad", "Presentation", "Opera", "Wedding-Cake", "Geyser", "Toontanic" }, 
	};
	
	//public final static String[] toonShtickerPageNames = new String[] {
	//		"Map", "Inventory", "Tasks", "Cog Gallery", "Fishing"
	//};
	
	public final static int[] maxCogGallery = {45, 45, 35, 30, 25, 20, 15, 10};
	
	private int health;
	private int maxHealth;
	private Player toon;
	private int skinNum;
	private PlayerData playerData;
	private UUID toonUUID;
	private String name;
	private int jellybeans;
	private int jellybeansPouch;
	private int jellybeansPouchMax;
	private int maxGags;
	private int maxTasks;
	private int currentRod;
	private int fishAmt;
	private int[][] fishGallery = {
			{0, 0, 0, 0, 0},
			{0, 0, 0, 0},
			{0, 0, 0, 0, 0},
			{0, 0, 0, 0, 0},
			{0, 0, 0, 0, 0},
			{0, 0, 0},
			{0, 0},
			{0, 0, 0},
			{0, 0, 0, 0, 0},
			{0, 0, 0},
			{0, 0, 0, 0, 0, 0},
			{0, 0, 0, 0},
			{0, 0, 0, 0},
			{0, 0, 0, 0, 0, 0, 0, 0},
			{0, 0, 0, 0, 0},
			{0},
			{0},
			{0}
	};
	private Map<String, List<Integer>> fishingBucket2 = new HashMap<>();
	private List<String> fishingBucket3 = new ArrayList<>();
	private ProtectedRegion currentRegion;
	private int currentTasklinePlayground;
	private int currentGagAmount;
	private int[] gagsUnlocked = {0, 0, 0, 0, 0, 0, 0};
	private int[] originalGags = {0, 0, 0, 0, 0, 0, 0};
	private int[] gagsExp = {0, 0, 0, 0, 0, 0, 0};
	private int[][] gagAmounts = {
			{ 0, 0, 0, 0, 0, 0, 0},
			{ 0, 0, 0, 0, 0, 0, 0},
			{ 0, 0, 0, 0, 0, 0, 0},
			{ 0, 0, 0, 0, 0, 0, 0},
			{ 0, 0, 0, 0, 0, 0, 0},
			{ 0, 0, 0, 0, 0, 0, 0},
			{ 0, 0, 0, 0, 0, 0, 0}
	};
	private int[][] cogGallery = {
			{ 0, 0, 0, 0, 0, 0, 0, 0},
			{ 0, 0, 0, 0, 0, 0, 0, 0},
			{ 0, 0, 0, 0, 0, 0, 0, 0},
			{ 0, 0, 0, 0, 0, 0, 0, 0}
	};
	
	private List<Inventory> toonShtickerBook = new ArrayList<>();
	
	private Inventory toonGagInv;
	private Inventory toonGagInv2;
	private Inventory toonGagExpInv;
	private Inventory toonGagExpInv2;
	
	public Toon(Player toon) {
		this.toon = toon;
		this.toonUUID = toon.getUniqueId();
		this.playerData = PlayerDataController.getPlayerData(toonUUID);
		//if (toon.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue()/2 < )
		
		//health = (int) toon.getHealth() / 2;
		//toon.setHealth(health*2);
		name = toon.getName();
		this.toonGagInv2 = Bukkit.createInventory(null, InventoryType.PLAYER);
		this.toonGagExpInv2 = Bukkit.createInventory(null, InventoryType.PLAYER);
		//Bukkit.create
		//this.toonGagInv2.clear();
		//this.toonGagExpInv2 = (PlayerInventory) Bukkit.createInventory(null, InventoryType.PLAYER);
		//this.toonGagExpInv2.clear();
		loadToonInvs();
		loadToonGagAmounts();
		loadToonData();
		loadToonTasksData();
		//toon.sendMessage("loadToonGagAmounts called!");
		loadToonGags();
		loadToonGagExps();
		loadToonShtickerBookInfo();
		loadFishingData();
		loadJellybeanData();
		
		
		playerData.savePlayerData();
		//toon.getInventory().setContents(toonGagInv2.getContents());
	}
	
	
	public void loadToonInvs() {
		toonGagInv = Bukkit.createInventory(null, 54, ChatColor.GOLD + "" + "Gags"); //Maybe make null?
		toonGagInv.setItem(0, createGuiItem(Material.PURPLE_CONCRETE, 1, ChatColor.DARK_PURPLE + "" + "Toon-Up")); // Eventually add + getPlayerXp(player) and make this method get passed player
		toonGagInv.setItem(9, createGuiItem(Material.YELLOW_CONCRETE, 1, ChatColor.YELLOW + "" + "Trap"));
		toonGagInv.setItem(18, createGuiItem(Material.GREEN_CONCRETE, 1, ChatColor.GREEN + "" + "Lure"));
		toonGagInv.setItem(27, createGuiItem(Material.BLUE_CONCRETE, 1, ChatColor.BLUE + "" + "Sound"));
		toonGagInv.setItem(36, createGuiItem(Material.ORANGE_CONCRETE, 1, ChatColor.GOLD + "" + "Throw"));
		toonGagInv.setItem(45, createGuiItem(Material.MAGENTA_CONCRETE, 1, ChatColor.LIGHT_PURPLE + "" + "Squirt"));
		//ADDING DROP (AND PUSH?)
		toonGagInv2.setItem(9, createGuiItem(Material.LIGHT_BLUE_CONCRETE, 1, ChatColor.AQUA + "" + "Drop"));
		
		toonGagExpInv = Bukkit.createInventory(null, 54, ChatColor.GOLD + "" + "Gag Exp"); //Maybe make null?
		toonGagExpInv.setItem(0, createGuiItem(Material.PURPLE_CONCRETE, 1, ChatColor.DARK_PURPLE + "" + "Toon-Up")); // Eventually add + getPlayerXp(player) and make this method get passed player
		toonGagExpInv.setItem(9, createGuiItem(Material.YELLOW_CONCRETE, 1, ChatColor.YELLOW + "" + "Trap"));
		toonGagExpInv.setItem(18, createGuiItem(Material.GREEN_CONCRETE, 1, ChatColor.GREEN + "" + "Lure"));
		toonGagExpInv.setItem(27, createGuiItem(Material.BLUE_CONCRETE, 1, ChatColor.BLUE + "" + "Sound"));
		toonGagExpInv.setItem(36, createGuiItem(Material.ORANGE_CONCRETE, 1, ChatColor.GOLD + "" + "Throw"));
		toonGagExpInv.setItem(45, createGuiItem(Material.MAGENTA_CONCRETE, 1, ChatColor.LIGHT_PURPLE + "" + "Squirt"));
		//ADDING DROP (PUSH?)
		//toonGagExpInv2.setItem(11, createGuiItem(Material.LIGHT_BLUE_CONCRETE, 1, ChatColor.AQUA + "" + "Drop"));
		toonGagExpInv2.setItem(9, createGuiItem(Material.LIGHT_BLUE_CONCRETE, 1, ChatColor.AQUA + "" + "Drop"));
	}
	
	public void loadToonGagAmounts() {
		String[] tempGagAmounts = {"", "", "", "", "", "", ""};
		//System.out.println("1: loading toon gag amounts!");
		//System.out.println("2: loading toon gag amounts!");
		//System.out.println("3: loading toon gag amounts!");
		for (int i = 0; i < 7; i++) {
			//System.out.println("toonGagNames[0]["+i+"]: " + toonGagNames[0][i]);
			if (playerData.getPlayerData().contains("gags." + toonGagNames[0][i] + "-amount")) {
				tempGagAmounts[i] = (String) playerData.getPlayerData().get("gags." + toonGagNames[0][i] + "-amount");
				//System.out.println("tempGagAmounts" + i + " " + tempGagAmounts[i]);
				for (int j = 0; j < 14; j += 2) {
					String gagAmountStr = "";
					gagAmountStr += String.valueOf(tempGagAmounts[i].charAt(j));
					gagAmountStr += String.valueOf(tempGagAmounts[i].charAt(j+1));
					//System.out.println("gagAmountStr: " + gagAmountStr);
					//for (int k = 0; k < 2; k++) {
					//	gagAmountStr += String.valueOf(tempGagAmounts[i].charAt(j + k));
					//}
					gagAmounts[j/2][i] = Integer.parseInt(gagAmountStr);
					//if (gagAmounts[j/2][i] != 0) {
					//	System.out.println("Found none zero!");
					//	System.out.println("j: " + j/2 + " i: " + i);
					//}
					//System.out.println("Done!");
				}
			}
			
			//else
			//	for (int j = 0; j < 7; j++)
			//		gagAmounts[i][j] = -1;
		}
		if (playerData.getPlayerData().contains("gags.max"))
			this.maxGags = (int) playerData.getPlayerData().get("gags.max");
		else {
			Main.getInstance().getLogger().log(Level.SEVERE, "Failed to load player's max gag amount! Trying to write in now...");
			this.maxGags = 20;
			playerData.getPlayerData().set("gags.max", 20);
		}
		
		if (playerData.getPlayerData().contains("gags.current"))
			this.currentGagAmount = (int) playerData.getPlayerData().get("gags.current");
		else {
			Main.getInstance().getLogger().log(Level.SEVERE, "Failed to load player's current gag amount! Trying to write in now...");
			this.currentGagAmount = 0;
			playerData.getPlayerData().set("gags.current", 0);
		}
		
		
		
		//Checks to make sure actual gag amount and recorded gag amount are the same
		int tempTotalGags = 0;
		for (int i = 0; i < 7; i++) {
			for (int j = 0; j < 7; j++) {
				tempTotalGags += gagAmounts[j][i];
			}
		}
		
		if (tempTotalGags != this.currentGagAmount) {
			Main.getInstance().getLogger().log(Level.SEVERE, "Actual and recorded gags are inconsistent! Equalizing now...");
			System.out.println("Recorderd: " + this.currentGagAmount);
			System.out.println("Actual: " + tempTotalGags);
			this.currentGagAmount = tempTotalGags;
		}
	}
	
	
	public void loadToonTasksData() {
		
		if (playerData.getPlayerData().contains("tasks.max")) {
			this.maxTasks = (int) playerData.getPlayerData().get("tasks.max");
		}
		else {
			Main.getInstance().getLogger().log(Level.SEVERE, "Failed to load player's max task amount! Trying to write in now...");
			this.maxTasks = 1;
			playerData.getPlayerData().set("tasks.max", 1);
		}
		
		if (playerData.getPlayerData().contains("tasks.playground"))
			this.currentTasklinePlayground = (int) playerData.getPlayerData().get("tasks.playground");
		else {
			Main.getInstance().getLogger().log(Level.SEVERE, "Failed to load player's current taskline playground! Trying to write in now...");
			this.currentTasklinePlayground = 0;
			playerData.getPlayerData().set("tasks.playground", 0);
		}	
	}
	
	public void loadToonGagExps() {
		for (int i = 0; i < 7; i++) {
			if (playerData.getPlayerData().contains("gags." + toonGagNames[0][i] + "-exp")) {
				gagsExp[i] = (int) playerData.getPlayerData().get("gags." + toonGagNames[0][i] + "-exp");
			}
		}
	}
	
	public void loadFishingData() {
		if (playerData.getPlayerData().contains("fishing.rod")) {
			this.currentRod = (int) playerData.getPlayerData().get("fishing.rod");
		}
		else {
			Main.getInstance().getLogger().log(Level.SEVERE, "Failed to load player's fishing data! Trying to write in now...");
			this.currentRod = 0;
			playerData.getPlayerData().set("fishing.rod", 0);
		}
		this.fishAmt = 0;
		//if (playerData.getPlayerData().contains("fishing.bucket")) {
		//	for (String key : playerData.getPlayerData().getConfigurationSection("fishing.bucket").getKeys(false)) {
		//		this.fishAmt++;
		//		this.fishingBucket2.put(key, playerData.getPlayerData().getIntegerList("fishing.bucket." + key));
		//	}
			//this.fishingBucket2 = (Map<String, int[]>) playerData.getPlayerData().getConfigurationSection("fishing.bucket").getValues(false);
			//this.fishingBucket2 = (Map<String, int[]>) playerData.getPlayerData().getMapList("fishing.bucket");
		//}
		
		//LOADING FISHING BUCKET3 METHOD
		if (playerData.getPlayerData().contains("fishing.bucket")) {
			fishingBucket3 = playerData.getPlayerData().getStringList("fishing.bucket");
		}
		
		if (playerData.getPlayerData().contains("fishing.gallery")) {
			int fishGalNum = 0;
			int fish = 0;
			int species = 0;
			//int[] fishGalInts = {5, 4, 5, 5, 5, 5, 3, 2, 3, 5, 3, 6, 4, 4, 8, 5, 1, 1, 1};
			int[] fishGalInts = {5, 3, 5, 5, 5, 3, 2, 3, 5, 3, 6, 4, 4, 8, 5, 1, 1, 1};
			String fishGalNumsSaved = (String) playerData.getPlayerData().get("fishing.gallery");
			int maxOfSpecies = fishGalInts[fishGalNum];
			for(int i=0; i < fishGalNumsSaved.length(); i++) {
				if (fish == maxOfSpecies) {
					if (fishGalNum > 16) break;
					fish = 0;
					species++;
					fishGalNum++;
					maxOfSpecies = fishGalInts[fishGalNum];
					//System.out.println("--species-- " + species);
					//System.out.println("MAX: " + maxOfSpecies);
				}
				//System.out.println("fish: " + fish);
				this.fishGallery[species][fish] = Character.getNumericValue(fishGalNumsSaved.charAt(i));
				fish++;
			}
		}
	}
	
	public void loadToonData() {
		if (playerData.getPlayerData().contains("laff"))
			this.maxHealth = (int) playerData.getPlayerData().get("laff");
		else {
			Main.getInstance().getLogger().log(Level.SEVERE, "Failed to load player's laff amount! Trying to write in now...");
			this.maxHealth = 15;
			playerData.getPlayerData().set("laff", 15);
		
		}
		if (playerData.getPlayerData().contains("skin")) {
			this.skinNum = (int) playerData.getPlayerData().getInt("skin");
			SkinGrabber.changeSkin(toon, skinNum); //Loads their chosen species skin if its set
		}
	}
	
	public void loadJellybeanData() {
		if (playerData.getPlayerData().contains("jellybeans.bank")) {
			this.jellybeans = playerData.getPlayerData().getInt("jellybeans.bank");
		}
		else {
			Main.getInstance().getLogger().log(Level.WARNING, "Player had no jellybean bank data! Writing in now...");
			playerData.getPlayerData().set("jellybeans.bank", 0);
			this.jellybeans = 0;
		}
		
		if (playerData.getPlayerData().contains("jellybeans.pouch")) {
			this.jellybeansPouch = playerData.getPlayerData().getInt("jellybeans.pouch");
		}
		else {
			Main.getInstance().getLogger().log(Level.WARNING, "Player had no jellybean pouch data! Writing in now...");
			playerData.getPlayerData().set("jellybeans.pouch", 0);
			this.jellybeansPouch = 0;
		}
		
		if (playerData.getPlayerData().contains("jellybeans.pouchmax")) {
			this.jellybeansPouchMax = playerData.getPlayerData().getInt("jellybeans.pouchmax");
		}
		else {
			Main.getInstance().getLogger().log(Level.WARNING, "Player had no jellybean pouch data! Writing in now...");
			playerData.getPlayerData().set("jellybeans.pouchmax", 25);
			this.jellybeansPouchMax = 25;
		}
	}
	
	public void loadToonShtickerBookInfo() {
		
		//COG GALLERY FIRST LOAD
		int x = 1; //REMEBER TO (x-1) when passing to array!
		int y = 0;
		for (CogType cogType : CogType.values()) {
			if (playerData.getPlayerData().contains("cogs.gallery." + cogType)) {
				cogGallery[y][(x-1)] = (int) playerData.getPlayerData().get("cogs.gallery." + cogType);
			}
			
			if (x % 8 == 0) {
				y++;
				x = 0;
			}
			x++;
		}
	}
	
	public void usedGagAmount(int trackNum, int gagNum) {
		gagAmounts[gagNum][trackNum] = (gagAmounts[gagNum][trackNum]) - 1;
		this.currentGagAmount--;
		//toon.sendMessage("TrackNum: " + trackNum + " gagNum: " + gagNum + " has been used!");
		//top.setItem(gagNum, null);
		//toonGagInv.setItem((((trackNum+1) *9) + gagNum) + 1, createGuiItem(toonGagItems[gagNum][trackNum], gagAmounts[gagNum][trackNum], ChatColor.WHITE + "" + toonGagNames[gagNum/*+1*/][trackNum+1]));
		System.out.println("player used gag, loading toon gags...");
		//loadToonGags(); Commented and changed to try new approach 1/15/21 11:31pm
		refreshToonGags();
	}
	
	public void loadToonInfo() {
		
		this.health = (int) toon.getHealth()/2;
		
		
		if (playerData.getPlayerData().contains("laff"))
			this.maxHealth = (int) playerData.getPlayerData().get("laff");
		else {
			Main.getInstance().getLogger().log(Level.SEVERE, "Failed to load player's laff amount! Trying to write in now...");
			this.maxHealth = 15;
			playerData.getPlayerData().set("laff", 15);
		
		}
		
		if (toon.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue()/2 < maxHealth)
			toon.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(maxHealth*2);
		
		//Refresh player health bar
		toon.setHealth(health*2);
	}
	
	
	public void loadToonGags() {
		System.out.println("toon.loadToonGags() has been called!");
		for (int i = 0; i < 7; i++)
			if (playerData.getPlayerData().contains("gags." + toonGagNames[0][i])) {
				gagsUnlocked[i] = (int) playerData.getPlayerData().get("gags." + toonGagNames[0][i]);
				//gagChanges[i] = gagsUnlocked[i];
				originalGags[i] = gagsUnlocked[i];
			}
	
		for (int i = 0; i < 7; i++)
			for (int j = 0; j < gagsUnlocked[i]; j++) {
				//toonGagInv.clear(i * 9 + 1 + j);
				if (gagAmounts[j][i] == 0) {
					
					//ADDING TO PUT IN DROP GAGS IN PLAYER INVENTORY
					if (i != 6)
						toonGagInv.setItem((i * 9 + 1 + j), createGuiItem(Material.BARRIER, 1, ChatColor.WHITE + "" + toonGagNames[j + 1][i])); //Catches if player has gag unlocked but none of gag, sets to barrier
					else
						toonGagInv2.setItem((i + 4 + j), createGuiItem(Material.BARRIER, 1, ChatColor.WHITE + "" + toonGagNames[j + 1][i]));
				}
				else {
					if (i != 6)
						toonGagInv.setItem((i * 9 + 1 + j), createGuiItem(toonGagItems[j][i], gagAmounts[j][i], ChatColor.WHITE + "" + toonGagNames[j + 1][i])); // Calcs GUI slot and sets item
					else
						toonGagInv2.setItem((i + 4 + j), createGuiItem(toonGagItems[j][i], gagAmounts[j][i], ChatColor.WHITE + "" + toonGagNames[j + 1][i]));
						
				}
				//toon.sendMessage(toonGagNames[j+1][i] + " = " + gagAmounts[0][i]);
			}
	}
	
	public void refreshCurrentGagAmount() {
		int tempTotalGags = 0;
		for (int i = 0; i < 7; i++) {
			for (int j = 0; j < 7; j++) {
				tempTotalGags += gagAmounts[j][i];
			}
		}
		this.currentGagAmount = tempTotalGags;
	}
	
	public void refreshToonGags() {
		System.out.println("Refreshing toon's gags!");
		
		for (int i = 0; i < 7; i++) {
			for (int j = 0; j < gagsUnlocked[i]; j++) {
				if (gagAmounts[j][i] == 0) {
					if (i != 6)
						toonGagInv.setItem((i * 9 + 1 + j), createGuiItem(Material.BARRIER, 1, ChatColor.WHITE + "" + toonGagNames[j + 1][i])); //Catches if player has gag unlocked but none of gag, sets to barrier
					else
						toonGagInv2.setItem((i + 4 + j), createGuiItem(Material.BARRIER, 1, ChatColor.WHITE + "" + toonGagNames[j + 1][i]));
				}
				else {
					if (i != 6)
						toonGagInv.setItem((i * 9 + 1 + j), createGuiItem(toonGagItems[j][i], gagAmounts[j][i], ChatColor.WHITE + "" + toonGagNames[j + 1][i]));
					else
						toonGagInv2.setItem((i + 4 + j), createGuiItem(toonGagItems[j][i], gagAmounts[j][i], ChatColor.WHITE + "" + toonGagNames[j + 1][i]));
				}
			}
		}
	}
	
	public void payBankAmount(int amt) {
		if (this.jellybeans - amt < 0) {
			Main.getInstance().getLogger().log(Level.SEVERE, "Player spent more from their bank than they have!");
		}
		else {
			this.jellybeans -= amt;
		}
	}
	
	public void payPouchAmount(int amt) {
		if (this.jellybeansPouch - amt < 0) {
			Main.getInstance().getLogger().log(Level.SEVERE, "Player spent more from their pouch than they have!");
		}
		else {
			this.jellybeansPouch -= amt;
		}
	}
	
	public void killToon() {
		for (int i = 0; i < 7; i++) //Sets all player's gag amounts to 0
			for (int j = 0; j < 7; j++)
				gagAmounts[j][i] = 0;
		this.health = 1;
	}
	
	public void updateToonGagAmounts(int[][] newGagAmounts) {
		for (int i = 0; i < 7; i++) {
			for (int j = 0; j < 7; j++) {
				if (newGagAmounts[j][i] != 0) {
					toon.sendMessage("Old amount: " + gagAmounts[j][i]);
					gagAmounts[j][i] += newGagAmounts[j][i];
					toon.sendMessage("New amount: " + gagAmounts[j][i]);
					toon.sendMessage(toonGagNames[j][i] + " has been increased by " + newGagAmounts[j][i]);
				}
			}
		}
		//loadToonGags(); Commented and changed to below 1/15/22 11:32pm
		refreshToonGags();
	}
	
	public void updateToonGagAmounts2(int[][] newGagAmounts, int total) {
		for (int i = 0; i < 7; i++) {
			for (int j = 0; j < 7; j++) {
				if (newGagAmounts[j][i] != gagAmounts[j][i]) {
					toon.sendMessage("Old amount: " + gagAmounts[j][i]);
					gagAmounts[j][i] = newGagAmounts[j][i];
					toon.sendMessage("New amount: " + gagAmounts[j][i]);
					//toon.sendMessage(toonGagNames[j][i] + " has been increased by " + newGagAmounts[j][i]);
				}
			}
		}
		refreshToonGags();
	}
	
	public void updateCurrentGagAmount(int amt) {
		this.currentGagAmount = amt;
	}

	public void loadToonGagExpInv2() {
		for (int i = 0; i < 7; i++) {
			if (i != 6)
				toonGagExpInv.setItem(i + (8 * i) + 1, createGuiItem(Material.APPLE, 1, ChatColor.WHITE + "" + gagsExp[i]));
			else
				toonGagExpInv2.setItem(10, createGuiItem(Material.APPLE, 1, ChatColor.WHITE + "" + gagsExp[i]));
		}
	}
	
	//public void updateToonGags() {
	//	for (int i = 0; i < 7; i++)
	//		if (gagChanges[i] != gagsUnlocked[i])
	//			playerData.getPlayerData().set("gags." + toonGagNames[0][i], gagChanges[i]);
	//}
	
	public void updateToonGagExp2(int[] expToAdd) {
		for (int i = 0; i < 7; i++) {
			if (expToAdd[i] != 0)
				gagsExp[i] += expToAdd[i];
		}
	}
	
	public void newFish(int species, int fish, int weight, int value) {
		//[row][col]
		fishAmt++;
		if (fishGallery[species][fish] == 0)
			fishGallery[species][fish] = 1;
		
		String fishName = FishController.allFishNames[species][fish]; //Swapped species and fish 4/5/22 4:06pm
		int loop = 0;
		List<Integer> fishValues = new ArrayList<>();
		fishValues.add(weight);
		fishValues.add(value);
		//int[] fishValues = {weight, value};
		System.out.println("Caught fish, got here! 6");
		while (fishingBucket2.containsKey(fishName)) { //Makes sure previous fish doesn't get overwriten
			loop++;
			//fishName += "-" + loop; Using regex below, quicker
			fishName.replaceFirst(".{2}$", "-" + loop);
			if (loop > 20) break;
		}
		fishingBucket2.put(fishName, fishValues);
		
		
		//NEW SAVING FISH VALUES METHOD: (WIP)
		String fishValues2 = "";
		if (species < 10) {
			fishValues2 += "0" + species;
		}
		else {
			fishValues2 += species;
		}
		
		if (fish < 10) {
			fishValues2 += "0" + fish;
		}
		else {
			fishValues2 += fish;
		}
		
		if (weight < 10) {
			fishValues2 += "0" + weight;
		}
		else {
			fishValues2 += weight;
		}
		
		if (value < 10) {
			fishValues2 += "0" + value;
		}
		else {
			fishValues2 += value;
		}
		fishingBucket3.add(fishValues2);
	}
	
	public void unloadToon() {
		for (int i = 0; i < 7; i++) {
			if (gagsExp[i] != 0) {
				playerData.getPlayerData().set("gags." + toonGagNames[0][i] + "-exp", gagsExp[i]);
			}
			
			if (originalGags[i] != gagsUnlocked[i])
				playerData.getPlayerData().set("gags." + toonGagNames[0][i], gagsUnlocked[i]);
			
			if (gagAmounts[0][i] != 0) {
				String newGagAmount = "";
				String tempAdd = "";
				for (int j = 0; j < 7; j++) {
					tempAdd = String.valueOf(gagAmounts[j][i]);
					if (tempAdd.length() < 2)
						tempAdd = 0 + tempAdd;
					newGagAmount += tempAdd;
				}
				playerData.getPlayerData().set("gags." + BattleMenu.gagNames[0][i] + "-amount", newGagAmount);
			}
		}	
			//if (playerData.getPlayerData().contains("laff")) {
		playerData.getPlayerData().set("laff", maxHealth);
			//}
		if (skinNum >= 0 || skinNum < 10) {
			playerData.getPlayerData().set("skin", skinNum);
		}
		
			//if (playerData.getPlayerData().contains("gags.max")) {
		playerData.getPlayerData().set("gags.max", maxGags);
			//}
			
			//if (playerData.getPlayerData().contains("gags.current")) {
		playerData.getPlayerData().set("gags.current", currentGagAmount);
			//}
			
			//if (playerData.getPlayerData().contains("tasks.max")) {
		playerData.getPlayerData().set("tasks.max", maxTasks);
			//}
			
			//if (playerData.getPlayerData().contains("tasks.playground")) {
		playerData.getPlayerData().set("tasks.playground", currentTasklinePlayground);
			//}
		//playerData.getPlayerData().set("fishing.bucket", fishingBucket2);
		//playerData.getPlayerData().createSection("fishing.bucket", fishingBucket2);
		//if (fishingBucket2.isEmpty()) { //CLEAR DATA SECTION IF BUCKET EMPTY
			//playerData.getPlayerData().set("fishing.bucket", "");
		//}
		//else {
		//	for (Map.Entry<String, List<Integer>> entry : fishingBucket2.entrySet()) { //Saving fishing bucket IF BUCKET NOT EMPTY
		//		playerData.getPlayerData().set("fishing.bucket." + entry.getKey(), entry.getValue());
		//	}
		//}
		
		
		//SAVING FISHING BUCKET3 METHOD:
		playerData.getPlayerData().set("fishing.bucket", fishingBucket3);
		
		
		String fishGalleryToSave = ""; //Loop to take all values in fishGallery and covert to a single string
		for (int i = 0; i < 18; i++) {
			for (int j = 0; j < fishGallery[i].length; j++) {
				//System.out.println("FishNum: " + j);
				//System.out.println("SpeciesNum: " + i);
				//System.out.println("-");
				fishGalleryToSave += fishGallery[i][j];
			}
		}
		playerData.getPlayerData().set("fishing.gallery", fishGalleryToSave);
		
		
		//Saving jellybeans to player datas
		playerData.getPlayerData().set("jellybeans.bank", jellybeans);
		playerData.getPlayerData().set("jellybeans.pouch", jellybeansPouch);
		playerData.getPlayerData().set("jellybeans.pouchmax", jellybeansPouchMax);
		
		//Loop to save cog gallery info
		int a = 1;
		int b = 0;
		//System.out.println("CogTypeSize: " + CogType.values().length);
		for (CogType cogType : CogType.values()) {
			if (cogGallery[b][(a-1)] != 0) { //[CogSuit][Cog]
				//System.out.println("Cog Gallery slot not empty! a: " + (a-1) + " b: " + b);
				int cogNum = cogGallery[b][(a-1)];
				//if (playerData.getPlayerData().contains("cogs.gallery." + cogType.toString())) { //This may be doubling the cogGallery num? Commented 4-3-22 6:47pm
				//	cogNum += (int) playerData.getPlayerData().get("cogs.gallery." + cogType.toString());
				//}
				playerData.getPlayerData().set("cogs.gallery." + cogType.toString(), cogNum);
			}
				
			if (a % 8 == 0) {
				b++;
				a -= 8;
			}
			a++;
		}
		playerData.savePlayerData();
	}
	
	public void openBookPage(int page) {
		if (toonShtickerBook.get(page) != null) {
			toon.getInventory().clear();
			toon.playSound(toon.getLocation(), Sound.ITEM_BOOK_PAGE_TURN, 1f, 1f);
			BattleMenu.openInventoryLater(toon, toonShtickerBook.get(page));
			if (page == 1) //Sets player inv with drop gags
				toon.getInventory().setContents(toonGagInv2.getContents());
			if (page != 0) //If first page, no previous page button
				toon.getInventory().setItem(27, createGuiItem(Material.RED_CONCRETE, 1, ChatColor.WHITE + "Previous Page"));
			if (page < toonShtickerBook.size() - 1) //If last page, no next page button
				toon.getInventory().setItem(35, createGuiItem(Material.ORANGE_CONCRETE, 1, ChatColor.WHITE + "Next Page"));
		}
		else {
			toon.sendMessage("Tried to open Shticker book page out of range! Page: " + page);
		}
	}
	
	public Player getToon() {
		return toon;
	}
	
	public int getCurrentRod() {
		return currentRod;
	}
	
	public boolean canBankAfford(int amt) {
		if (jellybeans - amt >= 0) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public boolean canPouchAfford(int amt) {
		if (jellybeansPouch - amt >= 0) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public int getJellybeansBank() {
		return jellybeans;
	}
	
	public int getJellybeansPouch() {
		return jellybeansPouch;
	}
	
	public int getJellybeansPouchMax() {
		return jellybeansPouchMax;
	}
	
	public int getHealth() {
		return health;
	}

	public int getMaxHealth() {
		return maxHealth;
	}
	
	public int getShtickerBookSize() {
		return toonShtickerBook.size();
	}
	
	public Inventory getShtickerBookPage(int page) {
		return toonShtickerBook.get(page);
	}
	
	public Map<String, List<Integer>> getFishingBucket() {
		return fishingBucket2;
	}
	
	public List<String> getFishingBucket3() {
		return fishingBucket3;
	}
	
	public int[][] getFishGallery() {
		return fishGallery;
	}
	
	public int getFishAmount() {
		return fishAmt;
	}
	
	public int[][] getCogGallery() {
		return cogGallery;
	}
	
	public int getCogGallery(int suit, int cog) {
		return cogGallery[suit][cog];
	}
	
	public void unlockGag(int track) {
		if (!(gagsUnlocked[track] > 6))
			gagsUnlocked[track] = gagsUnlocked[track] + 1;
		else
			Main.getInstance().getLogger().log(Level.SEVERE, "Tried to increase player's unlocked gag out of range!");
			
	}
	
	public void addCogGallery(int suit, int cog, int addAmt) {
		int maxAmt = maxCogGallery[cog];
		if (cogGallery[suit][cog] + addAmt > maxAmt)
			return;
		cogGallery[suit][cog] += addAmt;
	}
	
	public void setCurrentRegion(ProtectedRegion region) {
		this.currentRegion = region;
	}
	
	public void setGagsUnlocked(int track, int amount) {
		gagsUnlocked[track] = amount;
	}
	
	public void setGagsExp(int track, int amount) {
		gagsExp[track] = amount;
	}
	
	public void setSkinNum(int num) {
		this.skinNum = num;
	}
	
	public void setAllGagsAmount(int amount) {
		for (int i = 0; i < 7; i++) {
			for (int j = 0; j < 7; j++) {
				gagAmounts[j][i] = amount;
			}
		}
	}
	
	public void addShtickerBookPage(Inventory inv) {
		toonShtickerBook.add(inv);
	}
	
	
	public void setMaxGags(int max) {
		this.maxGags = max;
	}
	
	public void setCurrentRod(int rod) {
		if (rod < 0 || rod > 4) return;
		this.currentRod = rod;
	}
	
	public void setAllTrackGagAmount(int track, int amount) {
		for (int i = 0; i < 7; i++) {
			gagAmounts[i][track] = amount;
		}
		refreshCurrentGagAmount(); //Updates current gag amount
	}
	
	public void setTrackGagAmount(int track, int numInTrack, int amount) {
		gagAmounts[numInTrack][track] = amount;
		refreshCurrentGagAmount();
	}
	
	public void setJellybeans(int jbs) {
		this.jellybeans = jbs;
	}
	
	public void addBankJellybeans(int jbs) {
		if (this.jellybeans + jbs > 10000) {
			this.jellybeans = 10000;
			toon.sendMessage("You have max jellybeans in your bank!");
		}
		else {
			this.jellybeans = jellybeans + jbs;
		}
	}
	
	public void addPouchJellybeans(int jbs) {
		if (this.jellybeansPouch + jbs > jellybeansPouchMax) {
			this.jellybeansPouch = jellybeansPouchMax;
			if (jbs - jellybeansPouch > 0) {
				addBankJellybeans(jbs - jellybeansPouch);
			}
		}
		else {
			this.jellybeansPouch += jbs;
		}
	}
	
	public void setJellybeanPouchMax(int max) {
		this.jellybeansPouchMax = max;
	}
	
	public void setMaxHealth(int health) {
		this.maxHealth = health;
		if (toon.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue()/2 < maxHealth)
			toon.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(maxHealth*2);
		else if (toon.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue()/2 > maxHealth)
			toon.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(maxHealth*2);
	}
	

	
	public UUID getUUID() {
		return toonUUID;
	}
	
	public String getName() {
		return name;
	}
	
	public int getSkinNum() {
		return skinNum;
	}
	
	public int[] getGagsExp() {
		return gagsExp;
	}
	
	public ProtectedRegion getCurrentRegion() {
		return currentRegion;
	}
	
	public void incrementMaxTasks() {
		maxTasks++;
		if (maxTasks > 4)
			maxTasks = 4;
	}
	
	public void nextTasklinePlayground() {
		currentTasklinePlayground++;
		if (currentTasklinePlayground > 6)
			currentTasklinePlayground = 6;
	}
	
	public int getMaxTasks() {
		return maxTasks;
	}
	
	public int getCurrentTasklinePlayground() {
		return currentTasklinePlayground;
	}
	
	public Inventory getGagInventory() {
		return toonGagInv;
	}
	
	public Inventory getGag2Inventory() {
		return toonGagInv2;
	}
	
	public Inventory getGagExpInventory() {
		return toonGagExpInv;
	}
	
	public Inventory getGagExp2Inventory() {
		return toonGagExpInv2;
	}
	
	public PlayerData getToonData() {
		return playerData;
	}
	
	public int getMaxGags() {
		return maxGags;
	}
	
	public int getUnlockedTrackAmount(int track) {
		return gagsUnlocked[track];
	}
	
	public int getCurrentGagAmount() {
		return currentGagAmount;
	}
	
	

	
	public int[][] getGagAmounts() {
		return gagAmounts;
	}
	
	
	protected ItemStack createGuiItem(final Material material, final int amount, final String name, final String... lore) {
		final ItemStack item = new ItemStack(material, amount);
		final ItemMeta meta = item.getItemMeta();

		meta.setDisplayName(name);
		meta.setLore(Arrays.asList(lore));
		item.setItemMeta(meta);

		return item;
	}
}
