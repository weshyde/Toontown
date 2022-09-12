package me.WesBag.Toontown.BattleCore.Cogs.CogBuildings;

import org.bukkit.scheduler.BukkitRunnable;

public class NextLevelCountdown extends BukkitRunnable {
	
	private CogBuilding building;
	private int time;
	
	public NextLevelCountdown(CogBuilding building) {
		this.building = building;
		this.time = 71;
	}
	
	@Override
	public void run() {
		time--;
		
		if (time <= 0) {
			cancel();
			this.building.nextFloor();
		}
		else if (time % 10 == 0 || time <= 5)
			building.sendAllMessage("Next Floor in " + time); 
	}

}
