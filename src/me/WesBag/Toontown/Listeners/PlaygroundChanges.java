package me.WesBag.Toontown.Listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import me.WesBag.Toontown.BattleCore.Toons.Toon;
import me.WesBag.Toontown.BattleCore.Toons.ToonsController;
import me.WesBag.Toontown.Files.DataManager;
import net.raidstone.wgevents.events.RegionEnteredEvent;
import net.raidstone.wgevents.events.RegionLeftEvent;

public class PlaygroundChanges implements Listener {
	
	public DataManager data;
	
	String[] pgLocationNames = {"ttc", "dd", "dg", "mml", "b", "ddl", "sb", "cb", "lb", "bb"};
	
	@EventHandler
	public void onPlayerPlaygroundEnter(RegionEnteredEvent e) {
		if (!(e.getPlayer() instanceof Player)) return;
		e.getPlayer().sendMessage("Welcome to " + e.getRegion().getId().toString().toUpperCase() + "!");
		if (e.getRegionName().contains("all")) return;
		Toon toon = ToonsController.getToon(e.getPlayer().getUniqueId());
		
		if (toon == null) return; //This is a TEMP FIX for when this event runs before toon creation on join 9-25-22 
			
		String regionName = e.getRegionName();
		int ID = 0;
		regionName.toLowerCase();
		if (regionName.contains("ttc")) {
			ID = 10;
			if (regionName.contains("loopy")) {
				ID += 1;
			}
			else if (regionName.contains("punchline")) {
				ID += 2;
			}
			else if (regionName.contains("silly")) {
				ID += 3;
			}
		}
		
		toon.setRegionLocationID(ID);
		//for (int i = 0; i < pgLocationNames.length; i++) {
		//	if (e.getRegion().getId().toString() == pgLocationNames[i]) {
		//		Location tempL = (Location) data.getConfig().get("locations." + pgLocationNames[i]);
		//		e.getPlayer().setBedSpawnLocation(tempL, true);
		//		e.getPlayer().sendMessage("Bed location changed!");
		//	}
		//}
	}
	
	@EventHandler
	public void onPlayerPlaygroundLeave(RegionLeftEvent e) {
		
	}
}
