package me.WesBag.Toontown.Animations.Gag.Drop;

import org.bukkit.Location;
import org.bukkit.Material;

public class SandbagAnimation {
	
	@SuppressWarnings("deprecation")
	public SandbagAnimation(Location l) {
		l.getWorld().spawnFallingBlock(l.add(0, 10, 0), Material.DIRT, (byte) 0);
	}

}
