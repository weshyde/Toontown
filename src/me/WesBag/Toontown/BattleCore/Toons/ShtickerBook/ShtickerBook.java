package me.WesBag.Toontown.BattleCore.Toons.ShtickerBook;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import me.WesBag.Toontown.Main;
import me.WesBag.Toontown.BattleCore.Cogs.CogsController;
import me.WesBag.Toontown.BattleCore.Toons.Toon;
import me.WesBag.Toontown.BattleCore.Toons.ToonsController;
import me.WesBag.Toontown.Commands.Admin.IsIntUtil;
import me.WesBag.Toontown.Files.CogType;
import me.WesBag.Toontown.Fishing.FishController;

public class ShtickerBook implements Listener {
	
	//public static Inventory mapPage;
	public static Map<UUID, Integer> shtickerPlayerPage = new HashMap<>();
	public static Main main;
	public void startupInit(Main main) {
		//mapPage = Bukkit.createInventory(null, 54);
		//for (int i = 0; i < 54; i++) {
		//	mapPage.setItem(i, createGuiItem(Material.WHITE_STAINED_GLASS_PANE, " "));
		//}
		ShtickerBook.main = main;
	}
	
	
	public static void openBook(Toon toon) {
		if (toon.getShtickerBookSize() == 0) {
			initBook(toon);
		}
		shtickerPlayerPage.put(toon.getUUID(), 0);
		toon.openBookPage(0);
	}
	
	public static void initBook(Toon toon) {
		if (toon.getShtickerBookSize() == 0) {
			loadMapPage(toon);
			loadInventoryPage(toon);
			loadTasksPage(toon);
			loadTrainingPage(toon);
			loadGalleryPage(toon);
			loadFishingPages(toon); //Multi-page!!!
		}
	}
	
	@EventHandler
	public void onPlayerInventoryClear(InventoryEvent e) {
		//e.getClass().getSimpleName()
	}
	
	public static void loadMapPage(Toon toon) {
		Player p = toon.getToon();
		
		Inventory mapPage = Bukkit.createInventory(null, 54, ChatColor.BLUE + "Map");
		for (int i = 0; i < 54; i++) {
			mapPage.setItem(i, createGuiItem(Material.WHITE_STAINED_GLASS_PANE, " "));
		}
		
		if (p.hasPermission("ttc.visit.ttc")) {
			if (p.hasPermission("ttc.teleport.ttc")) {
				mapPage.setItem(31, createGuiItem(Material.LIME_CONCRETE, "Teleport To Toontown Central"));
			}
			else {
				mapPage.setItem(31, createGuiItem(Material.LIME_CONCRETE, "Toontown Central"));
			}
		}
		
		if (p.hasPermission("ttc.visit.dd")) {
			if (p.hasPermission("ttc.teleport.dd")) {
				mapPage.setItem(34, createGuiItem(Material.BROWN_CONCRETE, "Teleport To Donald's Dock"));
			}
			else {
				mapPage.setItem(34, createGuiItem(Material.BROWN_CONCRETE, "Donald's Dock"));
			}
		}
		
		if (p.hasPermission("ttc.visit.dg")) {
			if (p.hasPermission("ttc.teleport.dg")) {
				mapPage.setItem(38, createGuiItem(Material.GREEN_CONCRETE, "Teleport To Daisy's Garden"));
			}
			else {
				mapPage.setItem(38, createGuiItem(Material.GREEN_CONCRETE, "Daisy's Garden"));
			}
		}
		
		if (p.hasPermission("ttc.visit.mml")) {
			if (p.hasPermission("ttc.teleport.mml")) {
				mapPage.setItem(23, createGuiItem(Material.PINK_CONCRETE, "Teleport To Minnie's Melodyland"));
			}
			else {
				mapPage.setItem(23, createGuiItem(Material.PINK_CONCRETE, "Minnie's Melodyland"));
			}
		}
		
		if (p.hasPermission("ttc.visit.b")) {
			if (p.hasPermission("ttc.teleport.b")) {
				mapPage.setItem(16, createGuiItem(Material.LIGHT_BLUE_CONCRETE, "Teleport To Brrgh"));
			}
			else {
				mapPage.setItem(16, createGuiItem(Material.LIGHT_BLUE_CONCRETE, "Brrgh"));
			}
		}
		
		if (p.hasPermission("ttc.visit.ddl")) {
			if (p.hasPermission("ttc.teleport.ddl")) {
				mapPage.setItem(4, createGuiItem(Material.PURPLE_CONCRETE, "Teleport To Donald's Dreamland"));
			}
			else {
				mapPage.setItem(4, createGuiItem(Material.PURPLE_CONCRETE, "Donald's Dreamland"));
			}
		}
		
		if (p.hasPermission("ttc.visit.sb")) {
			if (p.hasPermission("ttc.teleport.sb")) {
				mapPage.setItem(45, createGuiItem(Material.GRAY_CONCRETE, "Teleport To Sellbot HQ"));
			}
			else {
				mapPage.setItem(45, createGuiItem(Material.GRAY_CONCRETE, "Sellbot HQ"));
			}
		}
		
		if (p.hasPermission("ttc.visit.cb")) {
			if (p.hasPermission("ttc.teleport.cb")) {
				mapPage.setItem(0, createGuiItem(Material.GRAY_CONCRETE, "Teleport To Cashbot HQ"));
			}
			else {
				mapPage.setItem(0, createGuiItem(Material.GRAY_CONCRETE, "Cashbot HQ"));
			}
		}
		
		if (p.hasPermission("ttc.visit.lb")) {
			if (p.hasPermission("ttc.teleport.lb")) {
				mapPage.setItem(8, createGuiItem(Material.GRAY_CONCRETE, "Teleport To Lawbot HQ"));
			}
			else {
				mapPage.setItem(8, createGuiItem(Material.GRAY_CONCRETE, "Lawbot HQ"));
			}
		}
		
		if (p.hasPermission("ttc.visit.bb")) {
			if (p.hasPermission("ttc.teleport.bb")) {
				mapPage.setItem(53, createGuiItem(Material.GRAY_CONCRETE, "Teleport To Bossbot HQ"));
			}
			else {
				mapPage.setItem(53, createGuiItem(Material.GRAY_CONCRETE, "Bossbot HQ"));
			}
		}
		
		toon.addShtickerBookPage(mapPage);
	}
	
	public static void loadInventoryPage(Toon toon) {
		Inventory inventoryPage = Bukkit.createInventory(null, 54, ChatColor.BLUE + "Inventory");
		inventoryPage.setContents(toon.getGagInventory().getContents());
		toon.addShtickerBookPage(inventoryPage);
	}
	
	public static void loadTasksPage(Toon toon) {
		Inventory tasksPage = Bukkit.createInventory(null, 54, ChatColor.BLUE + "Tasks");
		//tasksPage.setItem(28, createGuiItem(Material));
		toon.addShtickerBookPage(tasksPage);
	}
	
	public static void loadTrainingPage(Toon toon) {
		Inventory trainingPage = Bukkit.createInventory(null, 54, ChatColor.BLUE + "Gag Training");
		toon.addShtickerBookPage(trainingPage);
	}
	
	public static void loadGalleryPage(Toon toon) {
		Inventory galleryPage = Bukkit.createInventory(null, 54, ChatColor.BLUE + "Cog Gallery");
		int slotAdd = 0;
		int cogTypeIndex = 0;
		for (int i = 0; i < 4; i++) {
			galleryPage.setItem((i*9), createGuiItem(Material.GRAY_CONCRETE, ChatColor.GRAY + CogsController.cogSuits[3-i]));
			for (int j = 0; j < 8; j++) {
				if (toon.getCogGallery(i, j) != 0) {
					galleryPage.setItem((j+1+slotAdd), createGuiItem(Material.LIGHT_GRAY_CONCRETE, ChatColor.GRAY + (CogType.values()[cogTypeIndex]).toString(), toon.getCogGallery(i, j)));
				}
				else {
					galleryPage.setItem((j+1+slotAdd), createGuiItem(Material.WHITE_CONCRETE, ChatColor.GRAY + "?"));
				}
				if ((j+1) % 8 == 0) slotAdd += 9;
				cogTypeIndex++;
			}
		}
		toon.addShtickerBookPage(galleryPage);
	}
	
	public static void refreshGalleryPage(Toon toon) {
		Inventory galleryPage = toon.getShtickerBookPage(4);
		int slotAdd = 0;
		int cogTypeIndex = 0;
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 8; j++) {
				if (toon.getCogGallery(i, j) != 0) {
					galleryPage.setItem((j+1+slotAdd), createGuiItem(Material.LIGHT_GRAY_CONCRETE, ChatColor.GRAY + (CogType.values()[cogTypeIndex]).toString(), toon.getCogGallery(i, j)));
				}
				else {
					galleryPage.setItem((j+1+slotAdd), createGuiItem(Material.WHITE_CONCRETE, ChatColor.GRAY + "?"));
				}
				if ((j+1) % 8 == 0) slotAdd += 9;
				cogTypeIndex++;
			}
		}
	}
	
	public static void loadFishingPages(Toon toon) {
		Inventory fishingPage1 = Bukkit.createInventory(null, 54, ChatColor.BLUE + "Fishing Bucket");
		int fishSlot = 0;
		//for (Map.Entry<String, List<Integer>> entry : toon.getFishingBucket().entrySet()) {
		//	fishingPage1.setItem(fishSlot, createGuiItem(Material.BLUE_CONCRETE, ChatColor.AQUA + entry.getKey(), 1, "Weight: " + entry.getValue().get(0), "Value: " + entry.getValue().get(1)));
		//	fishSlot += 2;
		//}
		//UPDATED FISHINGBUCKET3 METHOD
		for (String str : toon.getFishingBucket3()) {
			int species = IsIntUtil.getInt(str.subSequence(0, 2).toString());
			int fish = IsIntUtil.getInt(str.subSequence(2, 4).toString());
			int weight = IsIntUtil.getInt(str.subSequence(4, 6).toString());
			int value = IsIntUtil.getInt(str.subSequence(6, 8).toString());
			fishingPage1.setItem(fishSlot, createGuiItem(Material.BLUE_CONCRETE, ChatColor.AQUA + FishController.allFishNames[species][fish], "Weight: " + weight, "Value: " + value));
			fishSlot += 2;
		}
		toon.addShtickerBookPage(fishingPage1);
		
		Inventory fishingPage2 = Bukkit.createInventory(null, 54, ChatColor.BLUE + "Fishing Album Pg 1");
		Inventory fishingPage3 = Bukkit.createInventory(null, 54, ChatColor.BLUE + "Fishing Album Pg 2");
		Inventory fishingPage4 = Bukkit.createInventory(null, 54, ChatColor.BLUE + "Fishing Album Pg 3");
		
		int invRow = 0;
		
		for(int i = 0; i < 6; i++) {
			fishingPage2.setItem(invRow, createGuiItem(Material.BLUE_CONCRETE, FishController.allFishNames[i][0] + "es"));
			invRow += 9;
		}
		invRow = 0;
		for(int i = 6; i < 12; i++) {
			fishingPage3.setItem(invRow, createGuiItem(Material.BLUE_CONCRETE, FishController.allFishNames[i][0] + "es"));
			invRow += 9;
		}
		invRow = 0;
		for(int i = 12; i < 18; i++) {
			fishingPage4.setItem(invRow, createGuiItem(Material.BLUE_CONCRETE, FishController.allFishNames[i][0] + "es"));
			invRow += 9;
		}
		
		invRow = 0;
		int invCol = 1;
		
		int[][] tempGallery = toon.getFishGallery();
		for (int species = 0; species < tempGallery.length; species++) {
			invCol = 1;
			for (int fish = 0; fish < tempGallery[species].length; fish++) {
				if (tempGallery[species][fish] != 0) {
					if (invRow >= 45) invRow = 0;
					
					if (species < 6) {
						fishingPage2.setItem((invRow+invCol), createGuiItem(Material.WHITE_CONCRETE, FishController.allFishNames[species][fish]));
						//fishingPage2.setItem(fish, null);
					}
					else if (species < 12) {
						fishingPage3.setItem((invRow+invCol), createGuiItem(Material.WHITE_CONCRETE, FishController.allFishNames[species][fish]));
					}
					else {
						fishingPage4.setItem((invRow+invCol), createGuiItem(Material.WHITE_CONCRETE, FishController.allFishNames[species][fish]));
					}
				}
				invCol += 1;
			}
			invRow += 9;
		}
		toon.addShtickerBookPage(fishingPage2);
		toon.addShtickerBookPage(fishingPage3);
		toon.addShtickerBookPage(fishingPage4);
	}
	
	public static void refreshFishingPages(Toon toon) {
		if (toon.getShtickerBookSize() == 0) {
			initBook(toon);
		}
		Inventory fishingPage1 = toon.getShtickerBookPage(5);
		int fishSlot = 0;
		for (Map.Entry<String, List<Integer>> entry : toon.getFishingBucket().entrySet()) {
			fishingPage1.setItem(fishSlot, createGuiItem(Material.BLUE_CONCRETE, ChatColor.AQUA + entry.getKey(), 1, "Weight: " + entry.getValue().get(0), "Value: " + entry.getValue().get(1)));
			fishSlot += 2;
		}
		
		
		Inventory fishingPage2 = toon.getShtickerBookPage(6);
	}
	
	
	@EventHandler
	public void onPlayerClickShtickerBook(InventoryClickEvent e) {
		//Player p = (Player) e.getWhoClicked();
		if (!(e.getWhoClicked() instanceof Player)) return;
		if (e.getCurrentItem() == null || e.getCurrentItem().getType() == Material.AIR) return;
		if (!e.getView().getTitle().contains("Map") && !e.getView().getTitle().contains("Inventory") && !e.getView().getTitle().contains("Tasks") && !e.getView().getTitle().contains("Training") && !e.getView().getTitle().contains("Gallery") && !e.getView().getTitle().contains("Fishing")
				&& !(e.getInventory().getType().equals(InventoryType.PLAYER) && !e.getInventory().contains(Material.PURPLE_CONCRETE))) return; //Last bit checks if player is clicking their inventory while in shticker book
		if (ToonsController.getToon(e.getWhoClicked().getUniqueId()) == null) return;
		
		Toon toon = ToonsController.getToon(e.getWhoClicked().getUniqueId());
		e.setCancelled(true);
		String itemName = e.getCurrentItem().getItemMeta().getDisplayName();
		if (e.getRawSlot() > 53) {
			switch(e.getCurrentItem().getType()) {
			case RED_CONCRETE:
				int oldPage = shtickerPlayerPage.get(toon.getUUID());
				oldPage--;
				shtickerPlayerPage.put(toon.getUUID(), oldPage);
				toon.openBookPage(oldPage);
				break;
			case ORANGE_CONCRETE:
				int newPage = shtickerPlayerPage.get(toon.getUUID());
				newPage++;
				shtickerPlayerPage.put(toon.getUUID(), newPage);
				toon.openBookPage(newPage);
				break;
			default:
				break;
			}
		}
		else {
			switch(e.getView().getTitle()) {
			case("Map"):
				if (!e.getCurrentItem().getItemMeta().getDisplayName().contains("Teleport")) return;
				switch(e.getCurrentItem().getType()) {
				case LIME_CONCRETE:
					
					break;
				case BROWN_CONCRETE:
					
					break;
				case GREEN_CONCRETE:
					
					break;
				case PINK_CONCRETE:
					
					break;
				case LIGHT_BLUE_CONCRETE:
					
					break;
				case PURPLE_CONCRETE:
					
					break;
				case GRAY_CONCRETE:
					if (itemName.contains("Sellbot")) {
						
					}
					else if (itemName.contains("Cashbot")) {
						
					}
					else if (itemName.contains("Lawbot")) {
						
					}
					else if (itemName.contains("Bossbot")){ //Bossbot
						
					}
					
					break;
				default:
					break;
				}
				break;
			case("Inventory"):
				
				break;
			case("Tasks"):
				
				break;
			case("Training"):
				
				break;
			case("Gallery"):
				
				break;
			case("Fishing Bucket"):
				
				break;
			case("Fishing Album"):
				
				break;
			default: //Player Inventory?
				break;
			}
		}
	}
	
	protected static ItemStack createGuiItem(final Material material, final String name, final String... lore) {
		final ItemStack item = new ItemStack(material, 1);
		final ItemMeta meta = item.getItemMeta();

		meta.setDisplayName(name);
		meta.setLore(Arrays.asList(lore));
		item.setItemMeta(meta);

		return item;
	}
	
	protected static ItemStack createGuiItem(final Material material, final String name, int amt, final String... lore) {
		final ItemStack item = new ItemStack(material, amt);
		final ItemMeta meta = item.getItemMeta();

		meta.setDisplayName(name);
		meta.setLore(Arrays.asList(lore));
		item.setItemMeta(meta);

		return item;
	}

}
