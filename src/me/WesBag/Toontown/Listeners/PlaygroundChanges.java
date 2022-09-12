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
