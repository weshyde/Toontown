package me.WesBag.Toontown.Trolley.Utilities;

import org.bukkit.scheduler.BukkitRunnable;

import me.WesBag.Toontown.Trolley.NewTrolley;
import me.WesBag.Toontown.Trolley.Trolley;

public class NextGameCountdown extends BukkitRunnable {
	
	private NewTrolley trolley;
	private int time;
	
	public NextGameCountdown(NewTrolley trolley) {
		this.trolley = trolley;
		this.time = 15;
	}
	
	@Override
	public void run() {
		time--;
		
		if (time <= 0) {
			cancel();
			this.trolley.startNextGame();
		}
	}

}
