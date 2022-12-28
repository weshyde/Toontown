package me.WesBag.Toontown.Trolley.Minigames.CatchingGame;

import org.bukkit.event.Listener;

import me.WesBag.Toontown.Trolley.Utilities.GameManager;
import me.WesBag.Toontown.Trolley.Utilities.RewardManager;

public class CatchingGameListener implements Listener {
	
	private GameManager gameManager;
	private RewardManager rewardManager;
	
	public CatchingGameListener() {
		
	}
	
	public CatchingGameListener(GameManager gameManager, RewardManager rewardManager) {
		this.gameManager = gameManager;
		this.rewardManager = rewardManager;
	}

}
