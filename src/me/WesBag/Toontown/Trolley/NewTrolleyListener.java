package me.WesBag.Toontown.Trolley;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import me.WesBag.Toontown.Trolley.Utilities.GamePrefixes;
import net.citizensnpcs.api.event.NPCRightClickEvent;
import net.citizensnpcs.api.npc.NPC;
import net.md_5.bungee.api.ChatColor;

public class NewTrolleyListener implements Listener {
	
	@EventHandler
	public void onTrolleyRightClick(NPCRightClickEvent e) {
		if (ChatColor.stripColor(e.getNPC().getFullName()).contains("Trolley")) {
			NPC npc = e.getNPC();
			Player p = e.getClicker();
			if (NewTrolley.emptyTrolley(npc.getUniqueId())) { //Starting fresh
				NewTrolley trolley = new NewTrolley(p.getUniqueId());
				NewTrolley.takeTrolley(npc.getUniqueId(), trolley);
			}
			else { //Joining trolley
				NewTrolley trolley = NewTrolley.getActiveTrolley(npc.getUniqueId());
				if (trolley.isPlayerIn(p.getUniqueId())) {
					p.sendMessage(GamePrefixes.Trolley + " You're already on this trolley");
				}
				else if (!(trolley.isFull())) {
					trolley.addPlayer(p.getUniqueId());
					p.sendMessage(GamePrefixes.Trolley + " joined trolley");
				}
				else {
					p.sendMessage(GamePrefixes.Trolley + " Sorry, this trolley is full");
				}
			}
		}
	}
	
	@EventHandler
	public void onPlayerClickGUI(final InventoryClickEvent e) {
		if (e.getInventory() != NewTrolley.getNextGameInventory()) return;
		
		e.setCancelled(true);
		
		if (e.getView().getTitle().contains("Play Again?")) {
			final ItemStack clickedItem = e.getCurrentItem();
			if (clickedItem == null) return;
			
			NewTrolley trolley = NewTrolley.getTrolley(e.getWhoClicked().getUniqueId());
			if (trolley == null) return;
			
			if (clickedItem.getItemMeta().getDisplayName().contains("Yes")) {
				trolley.addNextGame(e.getWhoClicked().getUniqueId());
				e.getWhoClicked().sendMessage(GamePrefixes.Trolley + " Alright! Lets play again!");
				e.getWhoClicked().closeInventory();
			}
			else if (clickedItem.getItemMeta().getDisplayName().contains("No")) {
				trolley.removePlayer(e.getWhoClicked().getUniqueId());
				e.getWhoClicked().sendMessage(GamePrefixes.Trolley + " See you next time!");
				e.getWhoClicked().closeInventory();
				//Teleport player back to respective playground!
			}
		}
	}

}
