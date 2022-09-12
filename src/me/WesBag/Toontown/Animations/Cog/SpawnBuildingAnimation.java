package me.WesBag.Toontown.Animations.Cog;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.FallingBlock;

public class SpawnBuildingAnimation {
	
	public static void spawnBuildingAnimation(Location l, Material mat) {
		//world.spawnFallingBlock(l, new MaterialData(Material.DIRT));
		World world = l.getWorld();
		BlockData b = mat.createBlockData();
		FallingBlock fb = world.spawnFallingBlock(l, b);
		fb.setDropItem(false);
		for (int i = 0; i < 10; i++) {
			fb = world.spawnFallingBlock(l.add(1, 0, 0), b);
			fb.setDropItem(false);
		}
	}
}
