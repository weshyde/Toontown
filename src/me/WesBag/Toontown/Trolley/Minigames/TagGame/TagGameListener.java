package me.WesBag.Toontown.Trolley.Minigames.TagGame;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.scheduler.BukkitRunnable;

import me.WesBag.Toontown.Main;
import me.WesBag.Toontown.Trolley.Utilities.GameInventorys;
import me.WesBag.Toontown.Trolley.Utilities.GamePrefixes;
import net.md_5.bungee.api.ChatColor;

public class TagGameListener implements Listener {
	
	@EventHandler
	public void onPlayerIntersectPlayer(EntityDamageByEntityEvent e) {
		if (!(e.getDamager() instanceof Player)) return;
		if (!(e.getEntity() instanceof Player)) return;
		if (!(TagGame.playerIsIt(e.getDamager().getUniqueId()))) return;
		
		e.setCancelled(true);
		Player oldIt = Bukkit.getPlayer(e.getDamager().getUniqueId());
		Player newIt = Bukkit.getPlayer(e.getEntity().getUniqueId());
		if (TagGame.safePlayers.contains(oldIt.getUniqueId())) {
			newIt.sendMessage(GamePrefixes.Trolley + " That player is safe");
			return;
		}
		TagGame.itPlayers.remove(oldIt.getUniqueId());
		TagGame.safePlayers.add(oldIt.getUniqueId());
		TagGame.itPlayers.add(newIt.getUniqueId());
		
		oldIt.getInventory().clear();
		for (int i = 0; i < 9; i++) {
			newIt.getInventory().setItem(i, GameInventorys.createGuiItem(Material.RED_WOOL, ChatColor.RED + "You're it!", 1));
		}
	}
	
	public void safeRemoveTask(UUID player) {
		new BukkitRunnable() {
			
			@Override
			public void run() {
				if (TagGame.safePlayers.contains(player)) {
					TagGame.safePlayers.remove(player);
				}
			}
		}.runTaskLater(Main.getInstance(), 80);
	}

}
