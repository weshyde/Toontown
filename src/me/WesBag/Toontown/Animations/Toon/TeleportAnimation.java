package me.WesBag.Toontown.Animations.Toon;

import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

public class TeleportAnimation extends BukkitRunnable {
	
	public static UUID playerTeleporting;
	
	private int stage = 0;
	private Player p;
	private Location l;
	private Location toL;
	private World w;
	private Particle particle;
	
	public TeleportAnimation(Player p, Location toLocation) {
		playerTeleporting = p.getUniqueId();
		
		this.p = p;
		this.l = p.getLocation();
		this.w = l.getWorld();
		this.toL = toLocation;
		
		//this.particle = Particle.DRAGON_BREATH;
		this.particle = Particle.SQUID_INK;
		//particle.
		
		w.spawnParticle(particle, l, 50, 0.2, 0.0, 0.2, 0.0);
		
	}
	
	
	@Override
	public void run() { 
		stage++;
		if (stage < 5) {
			if (stage == 3) {
				PotionEffect tmpP = new PotionEffect(PotionEffectType.BLINDNESS, 50, 20);
				p.addPotionEffect(tmpP);
			}
			p.teleport(l.subtract(0, 1, 0));
			p.sendMessage("Teleport Stage: " + stage);
		}
		
		else {
			cancel();
			//PotionEffect tmpP = new PotionEffect(PotionEffectType.BLINDNESS, 30, 20);
			//p.addPotionEffect(tmpP);
			p.teleport(toL);
			return;
		}
		
	}
	

}
