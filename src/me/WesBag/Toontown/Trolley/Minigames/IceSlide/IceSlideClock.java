package me.WesBag.Toontown.Trolley.Minigames.IceSlide;

import org.bukkit.scheduler.BukkitRunnable;

import me.WesBag.Toontown.Trolley.Utilities.GamePrefixes;

public class IceSlideClock extends BukkitRunnable {
	
	private NewIceSlide iceSlide;
	private int time;
	
	public IceSlideClock(NewIceSlide iceSlide) {
		this.iceSlide = iceSlide;
		this.time = 10;
	}
	
	@Override
	public void run() {
		if (time <= 0) {
			iceSlide.launchPlayers();
			cancel();
		}
		else if ((time % 5 == 0) || (time <= 5)) {
			iceSlide.sendMessage(GamePrefixes.Trolley + " " + time + " seconds to choose");
		}
		
		time--;
	}

}
