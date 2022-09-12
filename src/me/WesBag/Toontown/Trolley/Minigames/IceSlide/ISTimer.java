package me.WesBag.Toontown.Trolley.Minigames.IceSlide;

import org.bukkit.scheduler.BukkitRunnable;

public class ISTimer extends BukkitRunnable {
	
	private int time = 10;
	private IceSlide iceSlide;
	
	public ISTimer(IceSlide iceSlide) {
		this.iceSlide = iceSlide;
	}
	
	@Override
	public void run() {
		time--;
		if (time <= 0) {
			cancel();
			iceSlide.getListener().launchPlayers();
		}
		
		else if (iceSlide.checkPlayersDone()) {
			cancel();
			//time = 0;
			iceSlide.getListener().launchPlayers();
		}
			
		
	}

}
