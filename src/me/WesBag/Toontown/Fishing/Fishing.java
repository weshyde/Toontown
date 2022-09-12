package me.WesBag.Toontown.Fishing;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import me.WesBag.Toontown.Main;
import me.WesBag.Toontown.BattleCore.Toons.Toon;
import me.WesBag.Toontown.BattleCore.Toons.ToonsController;
import me.WesBag.Toontown.Fishing.Fish.TTFish;


public class Fishing implements Listener {
	
	public static RegionManager regionManager = WorldGuard.getInstance().getPlatform().getRegionContainer().get(BukkitAdapter.adapt(Bukkit.getWorld("world")));
	
	private Map<Integer, List<TTFish>> fishInPond = new HashMap<>();
	private ProtectedRegion currentFishingRegion;
	private double rodRarityFactor;
	private int rodMaxWeight;
	private int lastFishRarity;
	private int castCost;
	private int rod;
	//private int playerJellybeans;
	
	public Fishing(Player p) {
		Toon toon = ToonsController.getToon(p.getUniqueId());
		ApplicableRegionSet regions = regionManager.getApplicableRegions(BukkitAdapter.asBlockVector(p.getLocation()));
		currentFishingRegion = null;
		for (ProtectedRegion region : regions) {
			System.out.println("Region: " + region.getId());
			if (region != null && FishController.regionFishingPonds.keySet().contains(region)) {
				fishInPond = FishController.regionFishingPonds.get(region);
				currentFishingRegion = region;
				break;
			}
		}
		if (currentFishingRegion == null) {
			Main.getInstance().getLogger().log(Level.SEVERE, "Fishing object created in an region with no fishing data!");
			return;
		}
		castCost = toon.getCurrentRod()+1;
		rod = toon.getCurrentRod();
		switch(toon.getCurrentRod()) {
		case 0:
			rodRarityFactor = 1/3.6;
			rodMaxWeight = 4;
			break;
		case 1:
			rodRarityFactor = 1/3.51;
			rodMaxWeight = 8;
			break;
		case 2:
			rodRarityFactor = 1/3.42;
			rodMaxWeight = 12;
			break;
		case 3:
			rodRarityFactor = 1/3.24;
			rodMaxWeight = 16;
			break;
		case 4:
			rodRarityFactor = 1/3.06;
			rodMaxWeight = 20;
			break;
		}
		
		
		ItemStack rod = new ItemStack(Material.FISHING_ROD);
		ItemStack exit = new ItemStack(Material.RED_CONCRETE);
		ItemMeta rodMeta = rod.getItemMeta();
		ItemMeta exitMeta = exit.getItemMeta();
		rodMeta.setDisplayName(ChatColor.GOLD + "Twig Rod");
		exitMeta.setDisplayName(ChatColor.RED + "Exit");
		rod.setItemMeta(rodMeta);
		exit.setItemMeta(exitMeta);
		rod.addEnchantment(Enchantment.LURE, 2);
		p.getInventory().setItem(0, rod);
		p.getInventory().setItem(8, exit);
	}
	
	public TTFish calcAndReturnFish() {
		int rarity = (int) Math.ceil(10 * (1 - Math.pow(Math.random(), rodRarityFactor)));
		if (rarity < 1)
			rarity = 1;
		if (rarity > 10)
			rarity = 10;
		lastFishRarity = rarity;
		System.out.println("Calced fishing rarity: " + rarity);
		List<TTFish> rarityFish = fishInPond.get(rarity);
		int removeAmt = 1;
		while (rarityFish.isEmpty()) {
			rarityFish = fishInPond.get(rarity-removeAmt);
			removeAmt++;
		}
		
		Random r = new Random();
		
		TTFish choosenFish = rarityFish.get(r.nextInt(rarityFish.size()));
		int loopProtect = 0;
		while(choosenFish.getRodNeeded() > rod) {
			choosenFish = rarityFish.get(r.nextInt(rarityFish.size()));
			if (loopProtect > 10) break;
			loopProtect++;
		}
		
		//int fishNum = r.nextInt(rarityFish.size());
		
		//return rarityFish.get(fishNum);
		return choosenFish;
	}
	
	public int calcCaughtFishWeight(TTFish fish) {
		return fish.getWeight(rodMaxWeight);
		
	}
	public int calcCaughtFishValue(TTFish fish, int weight) {
		return fish.getBeans2(weight, lastFishRarity);
	}
	
	public int getCastCost() {
		return castCost;
	}
	

}
