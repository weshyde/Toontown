package me.WesBag.Toontown.Trolley.Utilities;

import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.player.PlayerMoveEvent;

public class GameListener implements Listener {
	
	private Material[] earnBlocks;
	private Item[] earnItems;
	private GameManager gameManager;
	private GameListener gameListener;
	private int conditionType = 0;
	
	public GameListener(GameManager gameManager, int conditionType, Material[] earnBlocks) {
		this.gameManager = gameManager;
		this.gameListener = this;
		this.conditionType = conditionType;
		this.earnBlocks = earnBlocks.clone();
	}
	
	public GameListener(GameManager gameManager, int conditionType, Item[] earnItems) {
		this.gameManager = gameManager;
		this.conditionType = conditionType;
		this.earnItems = earnItems.clone();
		//Listener test = this.gameListener;
		//HandlerList.unregisterAll(this.gameListener);
	}
	/*
	@EventHandler
	public void onPlayerMove(PlayerMoveEvent e) {
		if (conditionType != 1) return;
		if (earnBlocks == null) return;
		
		for (int i = 0; i < earnBlocks.length; i++) {
			if (e.getTo().getBlock().getType() == earnBlocks[i]) {
				//ADD PLAYER POINTS FROM CLOCK
			}
		}
	}
	
	@EventHandler
	public void onPlayerMove2(PlayerMoveEvent e) {
		if (conditionType != 2) return;
		if (earnBlocks == null) return;
		
		for (int i = 0; i < earnBlocks.length; i++) {
			if (e.getTo().getBlock().getType() == earnBlocks[i]) {
				//ADD PLAYER POINTS
			}
		}
	}
	
	@EventHandler
	public void onPlayerPickupItem(EntityPickupItemEvent e) {
		if (conditionType != 3) return;
		if (!(e.getEntity() instanceof Player)) return;
		
		for (int i = 0; i < earnItems.length; i++) {
			if (e.getItem() == earnItems[i]) {
				//ADD PLAYER POINTS
			}
		}
		
	}
	*/

}
