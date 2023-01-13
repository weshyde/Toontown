package me.WesBag.Toontown.Trolley.Minigames.IceSlide;

import org.bukkit.scheduler.BukkitRunnable;

public class IceSlideTimer extends BukkitRunnable {
	
	private NewIceSlide iceSlide;
	private int time;
	
	public IceSlideTimer(NewIceSlide iceSlide) {
		this.iceSlide = iceSlide;
		this.time = 7;
	}
	
	
	@Override
	public void run() {
		if (time <= 0) {
			iceSlide.nextRound();
			cancel();
		}
		
		time--;
	}

}
