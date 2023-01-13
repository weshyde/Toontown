package me.WesBag.Toontown.Trolley.Utilities;

import org.bukkit.scheduler.BukkitRunnable;

public class NewMinigameClock extends BukkitRunnable {
	
	private GameManager gameManager;
	private int time;
	
	public NewMinigameClock(GameManager gameManager) {
		this.gameManager = gameManager;
		this.time = 90;
	}
	
	@Override
	public void run() {
		time--;
		if (time <= 0) {
			cancel();
			gameManager.setGameState(GameState.REWARD);
			return;
		}
		else if (time % 10 == 0 || time <= 5) {
			gameManager.sendMessage(GamePrefixes.Trolley + " " + time + " seconds left!");
		}
	}
	
	public int getTimeLeft() {
		return time;
	}

}
