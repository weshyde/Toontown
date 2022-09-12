package me.WesBag.Toontown.Trolley.Minigames.CatchingGame;

import org.bukkit.event.Listener;

import me.WesBag.Toontown.Trolley.Utilities.GameManager;
import me.WesBag.Toontown.Trolley.Utilities.RewardManager;

public class CatchGameListener implements Listener {
	
	private GameManager gameManager;
	private RewardManager rewardManager;
	
	public CatchGameListener() {
		
	}
	
	public CatchGameListener(GameManager gameManager, RewardManager rewardManager) {
		this.gameManager = gameManager;
		this.rewardManager = rewardManager;
	}

}
