package me.WesBag.Toontown.Trolley.Minigames.CannonGame;

import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.util.Vector;

import me.WesBag.Toontown.Trolley.Utilities.GameManager;
import me.WesBag.Toontown.Trolley.Utilities.GamePrefixes;
import me.WesBag.Toontown.Trolley.Utilities.GameState;
import me.WesBag.Toontown.Trolley.Utilities.RewardManager;

public class CGListener implements Listener {
	
	private GameManager gameManager;
	private RewardManager rewardManager;
	
	public CGListener() {
		
	}
	
	public CGListener(GameManager gameManager, RewardManager rewardManager) {
		this.gameManager = gameManager;
		this.rewardManager = rewardManager;
	}
	
	@EventHandler
	public void onPlayerRightClick(PlayerInteractEvent e) {
		//e.getPlayer().sendMessage("Got this far 1!");
		if (!(CannonGame.playerInGame(e.getPlayer().getUniqueId()))) return;
		if (e.getItem().getItemMeta() == null) return;
		
		//e.getPlayer().sendMessage("Got this far 2!");
		//e.getPlayer().sendMessage("gameManager null? " + String.valueOf(this.gameManager == null));
		//e.getPlayer().sendMessage("rewardManager null?" + String.valueOf(this.rewardManager == null));
		//if (this.gameManager == null || this.rewardManager == null) return;
		//e.getPlayer().sendMessage("Got this far 3!");
		//e.getPlayer().sendMessage("Contains launch? " + String.valueOf(e.getItem().getItemMeta().getDisplayName().contains("Launch")));
		//e.getPlayer().sendMessage("Feather? " + String.valueOf(e.getItem().getType() == Material.FEATHER));
		if (e.getItem().getItemMeta().getDisplayName().contains("Launch") && e.getItem().getType() == Material.FEATHER) {
			Player p = e.getPlayer();
			Vector v = p.getLocation().getDirection();
			for (int i = 1; i < 9; i+= 1) {
				String strNum = Integer.toString(i);
				if (e.getItem().getItemMeta().getDisplayName().contains(strNum)) {
					p.sendMessage("Launched! (" + i + ")");
					p.setVelocity(v.multiply(i/10));
				}
			}
		}
	}
	
	@EventHandler
	public void onPlayerMove(PlayerMoveEvent e) {
		if (!(CannonGame.playerInGame(e.getPlayer().getUniqueId()))) return;
		GameManager tempGM = GameManager.getGameManager(e.getPlayer().getUniqueId());
		if (tempGM == null) return;
		//if (gameManager == null || rewardManager == null) return;
		//e.getPlayer().sendMessage("Got this far!");
		if (tempGM.getState() == GameState.STARTING || tempGM.getState() == GameState.LOBBY) {
			e.setCancelled(true);
			return;
		}

		
		if (e.getTo().getBlock().getRelative(BlockFace.DOWN).getType() == Material.WATER || e.getTo().getBlock().getRelative(BlockFace.DOWN).getType() == Material.IRON_BLOCK) {
			tempGM.getRewardManager().giveTimeReward(e.getPlayer().getUniqueId());
			tempGM.playerDone(e.getPlayer().getUniqueId());
			//this.rewardManager.giveTimeReward(e.getPlayer().getUniqueId());
			
		}
		
		else if (e.getTo().getBlock().getRelative(BlockFace.DOWN).getType() == Material.GRASS_BLOCK || e.getTo().getBlock().getRelative(BlockFace.DOWN).getType() == Material.DARK_OAK_PLANKS) {
			//PLAYER MISSED, TELEPORT BACK
			e.getPlayer().sendMessage(GamePrefixes.Trolley + " You've been Reset!");
			tempGM.resetPlayerLocation(e.getPlayer().getUniqueId());
		}
	}
	

}
