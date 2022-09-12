package me.WesBag.Toontown.BattleCore.Cogs;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import net.raidstone.wgevents.events.RegionEnteredEvent;
import net.raidstone.wgevents.events.RegionLeftEvent;

public class CogLoader implements Listener {
	
	public static Map<String, Location> playgroundSpawns= new HashMap<>();
	
	@EventHandler
	public void onPlayerRegionEnter(RegionEnteredEvent e) {
		Player tempP = e.getPlayer();
		Location tempL = new Location(tempP.getWorld(), 100, 100, 100);
		tempP.setBedSpawnLocation(tempL);
		e.getPlayer().sendMessage("You are entering " + e.getRegion().getId());
	}
	
	@EventHandler
	public void onPlayerRegionLeave(RegionLeftEvent e) {
		e.getPlayer().sendMessage("You are leaving " + e.getRegion().getId());
	}
}
