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
import me.WesBag.Toontown.Trolley.Utilities.GameManager;

public class CannonGameListener implements Listener {
	
	private GameManager gameManager;
	private CannonGame cannonGame;
	
	public CannonGameListener() {
		
	}
	
	public CannonGameListener(GameManager gameManager, CannonGame cannonGame) {
		this.gameManager = gameManager;
		this.cannonGame = cannonGame;
	}
	
	@EventHandler
	public void onPlayerRightClick(PlayerInteractEvent e) {
		if (!(CannonGame.isPlaying(e.getPlayer().getUniqueId()))) return;
		//if (!(this.cannonGame.isPlayingLocal(e.getPlayer().getUniqueId()))) return;
		if (e.getItem().getItemMeta() == null) return;
		if (GameManager.getGameManager(e.getPlayer().getUniqueId()).getGameState() != GameState.RUNNING) return;
		
		if (e.getItem().getItemMeta().getDisplayName().contains("Launch") && e.getItem().getType() == Material.FEATHER) {
			Player p = e.getPlayer();
			Vector v = p.getLocation().getDirection();
			for (int i = 1; i <= 9; i++) {
				String strNum = Integer.toString(i);
				if (e.getItem().getItemMeta().getDisplayName().contains(strNum)) {
					p.setVelocity(v.multiply(i));
					p.sendMessage(GamePrefixes.Trolley + " Launched! (" + i + ")");
				}
			}
		}
	}
	
	@EventHandler
	public void onPlayerMove(PlayerMoveEvent e) {
		if (!(CannonGame.isPlaying(e.getPlayer().getUniqueId()))) return;
		//if (this.gameManager == null) e.getPlayer().sendMessage("Game manager null");
		//if (this.cannonGame == null) e.getPlayer().sendMessage("Cannon game null");
		//if (!(this.cannonGame.isPlayingLocal(e.getPlayer().getUniqueId()))) return;
		GameManager gameManager = GameManager.getGameManager(e.getPlayer().getUniqueId());
		
		
		if (gameManager.getGameState() == GameState.STARTING || gameManager.getGameState() == GameState.LOBBY) {
			e.setCancelled(true);;
		}
		else {
			Material landedOn = e.getTo().getBlock().getRelative(BlockFace.DOWN).getType();
			if (landedOn == Material.WATER || landedOn == Material.IRON_BLOCK) {
				gameManager.getRewardManager().addTimeReward(e.getPlayer().getUniqueId());
				gameManager.playerDone(e.getPlayer().getUniqueId());
				
			}
			else if (landedOn == Material.GRASS_BLOCK || landedOn == Material.DARK_OAK_PLANKS) {
				e.getPlayer().sendMessage(GamePrefixes.Trolley + " You've been reset");
				gameManager.resetPlayer(e.getPlayer().getUniqueId());
			}
		}
	}
}
