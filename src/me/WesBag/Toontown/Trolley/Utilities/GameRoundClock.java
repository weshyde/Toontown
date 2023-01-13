package me.WesBag.Toontown.Trolley.Utilities;

import org.bukkit.scheduler.BukkitRunnable;

import me.WesBag.Toontown.Trolley.Minigames.Minigame;

public class GameRoundClock extends BukkitRunnable {
	
	private Minigame game;
	private int time;
	
	public GameRoundClock(Minigame game, int time) {
		this.game = game;
		this.time = time;
	}
	
	@Override
	public void run() {
		if (time <= 0) {
			cancel();
		}
		
		game.sendMessage(GamePrefixes.Trolley + time);
		
		time--;
	}

}
