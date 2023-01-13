package me.WesBag.Toontown.Trolley.Minigames.TagGame;

import org.bukkit.scheduler.BukkitRunnable;

public class TagGameCooldownTimer extends BukkitRunnable {
	
	private ToonTag tagGame;
	private int time;
	
	public TagGameCooldownTimer(ToonTag tagGame) {
		this.tagGame = tagGame;
		this.time = 3;
	}
	
	@Override
	public void run() {
		if (time <= 0) {
			tagGame.removeOldIt();
			System.out.println("REMOVED OLD IT");
			cancel();
		}
		
		time--;
	}

}
