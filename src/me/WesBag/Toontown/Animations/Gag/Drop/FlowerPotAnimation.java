package me.WesBag.Toontown.Animations.Gag.Drop;

import org.bukkit.Location;
import org.bukkit.Material;

public class FlowerPotAnimation {
	
	@SuppressWarnings("deprecation")
	public FlowerPotAnimation(Location l) {
		l.getWorld().spawnFallingBlock(l.add(0, 10, 0), Material.FLOWER_POT, (byte) 0);
	}

}
