package me.WesBag.Toontown.Trolley.Minigames.TagGame;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ItemMergeEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.EquipmentSlot;

import me.WesBag.Toontown.Trolley.Utilities.GameManager;
import me.WesBag.Toontown.Trolley.Utilities.GamePrefixes;
import me.WesBag.Toontown.Trolley.Utilities.GameState;
import net.md_5.bungee.api.ChatColor;

public class ToonTagListener implements Listener {
	
	@EventHandler
	public void onTag(PlayerInteractAtEntityEvent e) {
		if (!(ToonTag.isPlaying(e.getPlayer().getUniqueId()))) return;
		if (e.getRightClicked().getType() != EntityType.PLAYER) return;
		if (e.getHand().equals(EquipmentSlot.OFF_HAND)) return;
		
		GameManager gameManager = GameManager.getGameManager(e.getPlayer().getUniqueId());
		if (!(((ToonTag) gameManager.getGame()).isPlayerIt(e.getPlayer().getUniqueId()))) return;
		if (((ToonTag) gameManager.getGame()).isPlayerSafe(e.getRightClicked().getUniqueId())) {
			e.getPlayer().sendMessage(GamePrefixes.Trolley + ChatColor.RED + " That player is currently on safety cooldown");
		}
		else {
			((ToonTag) gameManager.getGame()).makeIt(e.getRightClicked().getUniqueId());
		}
	}
	
	@EventHandler
	public void onPlayerMove(PlayerMoveEvent e) {
		if (!(ToonTag.isPlaying(e.getPlayer().getUniqueId()))) return;
		if (GameManager.getGameManager(e.getPlayer().getUniqueId()).getGameState() == GameState.RUNNING) return;
		
		e.setCancelled(true);
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onPlayerPickup(PlayerPickupItemEvent e) {
		if (!(ToonTag.isPlaying(e.getPlayer().getUniqueId()))) return;
		
		e.setCancelled(true);
		
		GameManager gameManager = GameManager.getGameManager(e.getPlayer().getUniqueId());
		if (((ToonTag) gameManager.getGame()).isPlayerIt(e.getPlayer().getUniqueId())) return;
		if (e.getItem().getItemStack().getType() != Material.DIAMOND_BLOCK) return;
		
		e.getItem().remove();
		
		((ToonTag)gameManager.getGame()).pickupCandy(e.getPlayer());
	}
	
	@EventHandler
	public void onCandyMerge(ItemMergeEvent e) {
		if (!(e.getEntity().getItemStack().getItemMeta().getDisplayName().equalsIgnoreCase("ToonTag"))) return;
		e.setCancelled(true);
	}

}
