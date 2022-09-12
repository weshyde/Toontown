package me.WesBag.Toontown.GagShop;

import java.util.Arrays;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.WesBag.Toontown.BattleCore.BattleCore;
import me.WesBag.Toontown.BattleCore.Toons.Toon;
import me.WesBag.Toontown.Files.PlayerData;
import net.md_5.bungee.api.ChatColor;

public class GagShop {
	
	/*
	public static void loadGagShop() {
		gagShop = Bukkit.createInventory(null, 54, ChatColor.GOLD + "" + "Gag Shop");
		gagShop.setItem(0, createGuiItem(Material.PURPLE_CONCRETE, 1, ChatColor.DARK_PURPLE + "" + "Toon-Up")); // Eventually add + getPlayerXp(player) and make this method get passed player
		gagShop.setItem(9, createGuiItem(Material.YELLOW_CONCRETE, 1, ChatColor.YELLOW + "" + "Trap"));
		gagShop.setItem(18, createGuiItem(Material.GREEN_CONCRETE, 1, ChatColor.GREEN + "" + "Lure"));
		gagShop.setItem(27, createGuiItem(Material.BLUE_CONCRETE, 1, ChatColor.BLUE + "" + "Sound"));
		gagShop.setItem(36, createGuiItem(Material.ORANGE_CONCRETE, 1, ChatColor.GOLD + "" + "Throw"));
		gagShop.setItem(45, createGuiItem(Material.MAGENTA_CONCRETE, 1, ChatColor.LIGHT_PURPLE + "" + "Squirt"));
	}
	*/
	Inventory gagShopInv;
	Toon toon;
	public GagShop(Toon pToon) {
		toon = pToon;
		Inventory oldGagShop;
		oldGagShop = pToon.getGagInventory();
		gagShopInv = Bukkit.createInventory(null, 54, ChatColor.GOLD + "" + "Gag Shop");
		gagShopInv.setContents(oldGagShop.getContents());
	}
	
	public static Inventory getGagShop(Toon pToon) {
		Inventory oldGagShop, gagShop;
		oldGagShop = pToon.getGagInventory();
		gagShop = Bukkit.createInventory(null, 54, ChatColor.GOLD + "" + "Gag Shop");
		gagShop.setContents(oldGagShop.getContents());
		//loadPlayerGagShop2(pToon);
		return gagShop;
	}
	
	public Inventory getGagShop() {
		return gagShopInv;
	}
	/*
	public static void loadPlayerGagShop(Toon pToon, UUID pUUID) {
		int[] playerGags = pToon.getGagsUnlocked();
		int[][] playerGagAmounts = pToon.getGagAmounts();
		for (int i = 0; i < 7; i++) {
			for (int j = 0; j < playerGags[i]; j++) {
				gagShop.setItem((i * 9 + 1 + j), createGuiItem(Toon.toonGagItems[j][i], playerGagAmounts[j][i], ChatColor.WHITE + "" + Toon.toonGagNames[j + 1][i]));
			}
		}
		//PlayerData pData = PlayerDataController.getPlayerData(pUUID);
	}
	*/
	
	//public void loadPlayerGagShop2(Toon pToon) {
	//	gagShop = pToon.getGagInventory();
	//}
	
	protected ItemStack createGuiItem(final Material material, final int amount, final String name, final String... lore) {
		final ItemStack item = new ItemStack(material, amount);
		final ItemMeta meta = item.getItemMeta();

		meta.setDisplayName(name);
		meta.setLore(Arrays.asList(lore));
		item.setItemMeta(meta);

		return item;
	}
}
