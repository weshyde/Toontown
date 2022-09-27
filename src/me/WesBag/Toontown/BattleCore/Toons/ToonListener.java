package me.WesBag.Toontown.BattleCore.Toons;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import me.WesBag.Toontown.BattleCore.BattleCore;

public class ToonListener implements Listener {
	
	private Logger log;
	
	public ToonListener(Logger log) {
		this.log = log;
	}
	
	@EventHandler
	public void onToonJoin(PlayerJoinEvent e) {
		Toon newToon = new Toon(e.getPlayer());
		//newToon.firstLoad();
		ToonsController.allToons.put(e.getPlayer().getUniqueId(), newToon);
		System.out.println("TOON CREATED!");
		log.log(Level.INFO, "New toon was made for " + e.getPlayer().getName());
	}
	
	@EventHandler
	public void onToonLeave(PlayerQuitEvent e) {
		Toon tempToon = ToonsController.allToons.get(e.getPlayer().getUniqueId());
		
		if (BattleCore.getBattleDataPlayer(e.getPlayer()) != null)
			if (BattleCore.getBattleDataPlayer(e.getPlayer()).isSpecialMode()) //Cog Building, Factory, Boss battle, etc
				tempToon.killToon();
		
		ToonsController.allToons.remove(e.getPlayer().getUniqueId());
		tempToon.unloadToon();
		
		
		//tempToon.updateToonGags();
		tempToon = null;
	}
	
	@EventHandler
	public void onToonKick(PlayerKickEvent e) {
		Toon tempToon = ToonsController.allToons.get(e.getPlayer().getUniqueId());
		ToonsController.allToons.remove(e.getPlayer().getUniqueId());
		tempToon.unloadToon();
	}
}
