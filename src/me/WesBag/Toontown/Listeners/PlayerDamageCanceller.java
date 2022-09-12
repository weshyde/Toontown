package me.WesBag.Toontown.Listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

public class PlayerDamageCanceller implements Listener {
	
	@EventHandler
	public void onCancelledPlayerDamage(EntityDamageEvent e) {
		if (e.getEntity() instanceof Player) {
			DamageCause damage = e.getCause();
			Player p = (Player) e.getEntity();
			switch(damage) {
			case FALL:
				e.setCancelled(true);
				p.sendMessage("Fall Damage Cancelled!");
				break;
			case DROWNING:
				e.setCancelled(true);
				p.sendMessage("Drowning Damage Cancelled!");
				break;
			case SUFFOCATION:
				e.setCancelled(true);
				p.sendMessage("Suffocation Damage Cancelled!");
				break;
			case FIRE:
				e.setCancelled(true);
				p.sendMessage("Fire Damage Cancelled!");
				break;
			default:
				e.setCancelled(false);
				break;
			}
		}
		/*
		if (e.getEntity() instanceof Player) {
			if (e.getCause() == DamageCause.FALL) {
				e.setCancelled(true);
			}
			else if (e.getCause() == DamageCause.DROWNING) {
				e.setCancelled(true);
			}
			else if (e.getCause() == DamageCause.SUFFOCATION) {
				e.setCancelled(true);
			}
			
		}
		*/
	}
	
	@EventHandler
	public void onHungerChange(FoodLevelChangeEvent e) {
		e.setCancelled(true);
		e.setFoodLevel(20);
	}
/*
	@EventHandler
	public void onPlayerJoinEvent(PlayerJoinEvent e) {
		Player p = (Player) e.getPlayer();
		if (p.hasPlayedBefore()) {
			p.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(30);
			p.sendMessage("Welcome! Your health was set to 15!");
			p.setHealth(30.0);
			p.setInvisible(false);
			World w = p.getWorld();
			Location l = new Location(w, -55.5, 64, -68.5);
			p.teleport(l);
		}
	}
*/
}
