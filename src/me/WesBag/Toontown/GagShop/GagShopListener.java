package me.WesBag.Toontown.GagShop;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.scheduler.BukkitRunnable;

import me.WesBag.Toontown.Main;
import me.WesBag.Toontown.BattleCore.BattleCore;
import me.WesBag.Toontown.BattleCore.Toons.Toon;
import me.WesBag.Toontown.BattleCore.Toons.ToonsController;
import me.WesBag.Toontown.Files.PlayerData;
import me.WesBag.Toontown.Files.PlayerDataController;
import net.citizensnpcs.api.event.NPCRightClickEvent;
import net.citizensnpcs.api.npc.NPC;

public class GagShopListener implements Listener {
	
	private Main main;
	private Logger log;
	
	public GagShopListener(Main main, Logger log) {
		this.main = main;
		this.log = log;
	}
	
	@EventHandler
	public void onGagClerkRightClick(NPCRightClickEvent e ) {
		NPC clerk = e.getNPC();
		Player player = e.getClicker();
		String unformattedClerkName = ChatColor.stripColor(clerk.getFullName());
		if (GagShopController.getInGagShop(player.getUniqueId()))
			return;
		else if (unformattedClerkName.contains("Clerk")) {
			Toon tempToon = ToonsController.getToon(player.getUniqueId());
			GagShopData gData = new GagShopData(player.getUniqueId(), tempToon);
			GagShopController.setGagData(player.getUniqueId(), gData);
			GagShopController.setInGagShop(player.getUniqueId());
			openGagShopInventoryLater(player, GagShop.getGagShop(tempToon));
		}
	}
	
	@EventHandler
	public void onPlayerCloseGagShop(InventoryCloseEvent e) {
		if (!(e.getPlayer() instanceof Player))
			return;
		
		if (e.getView().getTitle().contains("Shop")) {
			if (GagShopController.getInGagShop(e.getPlayer().getUniqueId())) {
				Toon tempToon = ToonsController.getToon(e.getPlayer().getUniqueId());
				if (tempToon != null) {
					//GagShop.loadPlayerGagShop(tempToon, e.getPlayer().getUniqueId());
					GagShopData gData = GagShopController.getGagShopData(e.getPlayer().getUniqueId());
					if (gData == null)
						return;
					openGagShopInventoryLater(e.getPlayer(), gData.getGagShopInv());
				}
				else
					log.log(Level.SEVERE, "Failed to load Toons in GagShopListener for " + e.getPlayer().getName() + "!");
			}
		}
	}
	
	public void openGagShopInventoryLater(HumanEntity e, Inventory inv) {
		new BukkitRunnable() {
			@Override
			public void run() {
				e.openInventory(inv);
			}
		}.runTaskLater(main, 1);
	}
	
	@EventHandler
	public void onGagShopClick(final InventoryClickEvent e) {
		if (!(e.getView().getTitle().contains("Shop"))) // || !(e.getView().getTitle().contains("Delete Gag Shop")))
			return;
		if (e.getCurrentItem() == null)
			return;
		if (e.getCurrentItem().getType() == Material.AIR)
			return;
		if (e.getRawSlot() > 54) //Potentially change to expand gag inv into player inv
			return;
		e.setCancelled(true);
		Player p = (Player) e.getWhoClicked();
		e.getWhoClicked().sendMessage("onGagShopClick!");
		
		GagShopData gData = GagShopController.getGagShopData(e.getWhoClicked().getUniqueId());
		String unformattedGagName = ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName());
		if (!(e.getView().getTitle().contains("Delete"))) {
			outerloop:
			for (int i = 1; i < 8; i++) {
				for (int j = 0; j < 7; j++) {
					if (unformattedGagName.equals(BattleCore.gagNames[i][j])) {
						//j = track | i = numIntrack
						e.getWhoClicked().sendMessage("Increased gag: " +  BattleCore.gagNames[i][j]);
						int itemSlot = e.getRawSlot();
						gData.increaseGagAmount(j, i-1);
						p.closeInventory();
						//p.updateInventory();
						//int newAmt = e.getCurrentItem().getAmount() + 1;
						break outerloop;
					}
					else if (unformattedGagName.equals("Done")) {
						gData.doneWithGagShopData();
						p.closeInventory();
						break outerloop;
					}
					
					else if (unformattedGagName.equals("Delete")) {
						Inventory newInv = Bukkit.createInventory(null, 54, ChatColor.GOLD + "" + "Gag Shop Delete");
						newInv.setContents(gData.getGagShopInv().getContents());
						gData.setGagShopInv(newInv);
						p.closeInventory();
						break outerloop;
					}
				}
			}
		}
		
		else { //Delete mode
			outerloop2:
			for (int i = 1; i < 8; i++) {
				for (int j = 0; j < 7; j++) {
					if (unformattedGagName.equals(BattleCore.gagNames[i][j])) {
						//j = track | i = numIntrack
						p.sendMessage("Decreased Gag!");
						gData.decreaseGagAmount(j, i);
						p.closeInventory();
						break outerloop2;
					}
					else if (unformattedGagName.equals("Done")) {
						gData.doneWithGagShopData();
						p.closeInventory();
						break outerloop2;
					}
					
					else if (unformattedGagName.equals("Buy")) {
						Inventory newInv = Bukkit.createInventory(null, 54, ChatColor.GOLD + "" + "Gag Shop");
						newInv.setContents(gData.getGagShopInv().getContents());
						gData.setGagShopInv(newInv);
						p.closeInventory();
						break outerloop2;
					}
				}
			}
		}
	}
}
