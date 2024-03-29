package me.WesBag.Toontown.Trolley.Utilities;

import org.bukkit.scheduler.BukkitRunnable;

public class NewCountdownClock extends BukkitRunnable {
	
	private GameManager gameManager;
	private GameState newGameState;
	private int time;
	
	public NewCountdownClock(GameManager gameManager, int time, GameState gameState) {
		this.gameManager = gameManager;
		this.time = time;
		this.newGameState = gameState;
	}
	
	@Override
	public void run() {
		if (time <= 0) {
			cancel();
			this.gameManager.setGameState(newGameState);
			return;
		}
		this.gameManager.sendMessage(GamePrefixes.Trolley + " " + time);
			
		time--;
	}

}
