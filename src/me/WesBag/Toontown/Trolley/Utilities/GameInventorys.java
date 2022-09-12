package me.WesBag.Toontown.Trolley.Utilities;

import java.util.Arrays;

import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import me.WesBag.Toontown.Main;

public class GameInventorys {
	
	public static ItemStack createGuiItem(final Material material, final String name, final int amount, final String... lore) {
		final ItemStack item = new ItemStack(material, amount);
		final ItemMeta meta = item.getItemMeta();

		meta.setDisplayName(name);
		meta.setLore(Arrays.asList(lore));
		item.setItemMeta(meta);

		return item;
	}
	
	public static void openInventoryLater(HumanEntity e, Inventory inv) {
		new BukkitRunnable() {
			@Override
			public void run() {
				e.openInventory(inv);
			}
		}.runTaskLater(Main.getInstance(), 1);
	}
}
