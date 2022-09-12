package me.WesBag.Toontown.GagShop;

import java.util.Arrays;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.WesBag.Toontown.BattleCore.Toons.Toon;
import net.md_5.bungee.api.ChatColor;

public class GagShopData {
	
	private UUID gagDataOwner;
	private Inventory gagShopInv;
	private Toon toon;
	private int maxGags;
	private int currentGagAmount;
	private int[][] gagAmounts = {
			{ 0, 0, 0, 0, 0, 0, 0},
			{ 0, 0, 0, 0, 0, 0, 0},
			{ 0, 0, 0, 0, 0, 0, 0},
			{ 0, 0, 0, 0, 0, 0, 0},
			{ 0, 0, 0, 0, 0, 0, 0},
			{ 0, 0, 0, 0, 0, 0, 0},
			{ 0, 0, 0, 0, 0, 0, 0}
	};
	
	public GagShopData(UUID ownerUUID, Toon iToon) {
		gagDataOwner = ownerUUID;
		toon = iToon;
		Inventory tempGagInv = iToon.getGagInventory();
		maxGags = toon.getMaxGags();
		currentGagAmount = toon.getCurrentGagAmount();
		gagAmounts = toon.getGagAmounts();
		//Amount copying debug begin
		/*
		toon.getToon().sendMessage("Track: 5(4) NumInTrack: 1(0)");
		toon.getToon().sendMessage("Amount: " + gagAmounts[0][4]);
		
		toon.getToon().sendMessage("Track: 6(5) NumInTrack: 1(0)");
		toon.getToon().sendMessage("Amount: " + gagAmounts[0][5]);
		
		for (int i = 0; i < 7; i++) {
			for (int j = 0; j < 7; j++) {
				if (gagAmounts[j][i] != 0)
					toon.getToon().sendMessage("Eureka! NumInTrack: " + j + " Track: " + i + " Amount: " + gagAmounts[j][i]);
			}
		}
		*/
		//Debug end
		
		gagShopInv = Bukkit.createInventory(null, 54, ChatColor.GOLD + "" + "Gag Shop");
		gagShopInv.setContents(tempGagInv.getContents());
		gagShopInv.setItem(44, createGuiItem(Material.RED_CONCRETE, 1, ChatColor.DARK_RED + "" + "Delete"));
		gagShopInv.setItem(53, createGuiItem(Material.GREEN_CONCRETE, 1, ChatColor.GREEN + "" + "Done"));
	}
	
	public void increaseGagAmount(int track, int numInTrack) {
		currentGagAmount++;
		if (currentGagAmount > maxGags) {
			toon.getToon().sendMessage(GagShopController.GagShopTitle + ChatColor.WHITE + " You have max gags (" + currentGagAmount + "), upgrade your pouch to hold more!");
			currentGagAmount--;
			return;
		}
		if (!(gagAmounts[numInTrack][track] >= 49)) {
			gagAmounts[numInTrack][track] = gagAmounts[numInTrack][track] + 1;
			toon.getToon().sendMessage("Old : " + (gagAmounts[numInTrack][track] - 1) + " New :" + gagAmounts[numInTrack][track]);
		}
		
		else {
			toon.getToon().sendMessage("You already have the max of 50 of that gag!"); //Change to right maxes eventually!
		}
		//ItemStack tempItem = gagShopInv.getItem(itemSlot);
		//tempItem.setAmount(tempItem.getAmount() + 1);
		//gagShopInv.setItem(itemSlot, tempItem);
		
	}
	
	public void decreaseGagAmount(int track, int numInTrack) {
		currentGagAmount--;
		if (!((gagAmounts[numInTrack][track]) <= 1))
			gagAmounts[numInTrack][track] = gagAmounts[numInTrack][track] - 1;
		else
			toon.getToon().sendMessage("You already have zero of that gag!");
		
		
	}
	
	public Inventory getGagShopInv() {
		return gagShopInv;
	}
	
	public void setGagShopInv(Inventory freshInv) {
		gagShopInv = freshInv;
	}
	
	public int[][] getGagAmounts() {
		return gagAmounts;
	}
	
	public UUID getGagDataOwner() {
		return gagDataOwner;
	}
	
	public void doneWithGagShopData() {
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				//toon.getToon().sendMessage("Number!: " + gagAmounts[j][i]);
			}
		}
		//toon.updateToonGagAmounts(gagAmounts);
		toon.updateToonGagAmounts2(gagAmounts, currentGagAmount);
		//toon.updateCurrentGagAmount(currentGagAmount);
		GagShopController.removeGagData(gagDataOwner);
		GagShopController.removeInGagShop(gagDataOwner);
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
