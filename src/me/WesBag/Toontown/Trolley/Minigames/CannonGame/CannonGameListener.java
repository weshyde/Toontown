package me.WesBag.Toontown.Trolley.Minigames.CannonGame;


import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.util.Vector;

import me.WesBag.Toontown.Trolley.Utilities.GamePrefixes;
import me.WesBag.Toontown.Trolley.Utilities.GameState;
import me.WesBag.Toontown.Trolley.Utilities.NewGameManager;

public class CannonGameListener implements Listener {
	
	private NewGameManager gameManager;
	
	public CannonGameListener(NewGameManager gameManager) {
		this.gameManager = gameManager;
	}
	
	@EventHandler
	public void onPlayerRightClick(PlayerInteractEvent e) {
		if (!(NewCannonGame.isPlaying(e.getPlayer().getUniqueId()))) return;
		if (e.getItem().getItemMeta() == null) return;
		
		if (e.getItem().getItemMeta().getDisplayName().contains("Launch") && e.getItem().getType() == Material.FEATHER) {
			Player p = e.getPlayer();
			Vector v = p.getLocation().getDirection();
			for (int i = 1; i <= 9; i++) {
				String strNum = Integer.toString(i);
				if (e.getItem().getItemMeta().getDisplayName().contains(strNum)) {
					p.setVelocity(v.multiply(i/10));
					p.sendMessage(GamePrefixes.Trolley + " Launched! (" + i + ")");
				}
			}
		}
	}
	
	@EventHandler
	public void onPlayerMove(PlayerMoveEvent e) {
		if (!(NewCannonGame.isPlaying(e.getPlayer().getUniqueId()))) return;
		
		if (gameManager.getGameState() == GameState.STARTING || gameManager.getGameState() == GameState.LOBBY) {
			e.setCancelled(true);
			return;
		}
		Material landedOn = e.getTo().getBlock().getRelative(BlockFace.DOWN).getType();
		if (landedOn == Material.WATER || landedOn == Material.IRON_BLOCK) {
			gameManager.getRewardManager().addTimeReward(e.getPlayer().getUniqueId());
			gameManager.playerDone(e.getPlayer().getUniqueId());
			//gameManager.getRewardManager().giveTimeReward(e.getPlayer().getUniqueId());
			//gameManager.playerDone(e.getPlayer().getUniqueId());
			//this.rewardManager.giveTimeReward(e.getPlayer().getUniqueId());
			
		}
		else if (landedOn == Material.GRASS_BLOCK || landedOn == Material.DARK_OAK_PLANKS) {
			e.getPlayer().sendMessage(GamePrefixes.Trolley + " You've been reset");
			
			//PLAYER MISSED, TELEPORT BACK
			//e.getPlayer().sendMessage(GamePrefixes.Trolley + " You've been Reset!");
			//gameManager.resetPlayerLocation(e.getPlayer().getUniqueId());
		}
	}
}
