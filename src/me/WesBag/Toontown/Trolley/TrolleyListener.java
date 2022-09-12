package me.WesBag.Toontown.Trolley;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import me.WesBag.Toontown.Trolley.Utilities.GamePrefixes;
import net.citizensnpcs.api.event.NPCRightClickEvent;
import net.citizensnpcs.api.npc.NPC;

public class TrolleyListener implements Listener {
	
	@EventHandler
	public void onTrolleyRightClick(NPCRightClickEvent e) {
		NPC npc = e.getNPC();
		Player p = e.getClicker();
		String unformattedNPCName = ChatColor.stripColor(npc.getFullName());
		if (unformattedNPCName.contains("Trolley")) {
			if (Trolley.checkTrolley(npc.getUniqueId())) { //PLAYER JOINING TROLLEY
				Trolley trolley = Trolley.getTrolley(npc.getUniqueId());
				trolley.addPlayer(p.getUniqueId());
			}
			
			else {
				Trolley trolley = new Trolley(p.getUniqueId()); //PLAYER JOINING EMPTY TROLLEY
				Trolley.takeTrolley(npc.getUniqueId(), trolley);
				//Trolley.addTrolley(trolley);
			}
		}
	}
	
	@EventHandler
	public void onPlayerClickGUI(final InventoryClickEvent e) {
		if (e.getInventory() != Trolley.getNextGameInv())
			return;
		e.getWhoClicked().sendMessage("TrolleyListener: Worked!");
		e.setCancelled(true);
		
		if (e.getView().getTitle().contains("Play Again?")) {
			final ItemStack clickedItem = e.getCurrentItem();
			if (clickedItem == null) return;
			
			Trolley trolley = Trolley.getTrolleyFromPlayer(e.getWhoClicked().getUniqueId());
			if (trolley == null) return;
			
			if (clickedItem.getItemMeta().getDisplayName().contains("Yes")) {
				trolley.nextGame(e.getWhoClicked().getUniqueId());
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
