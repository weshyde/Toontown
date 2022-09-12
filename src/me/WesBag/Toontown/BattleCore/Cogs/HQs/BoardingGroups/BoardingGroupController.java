package me.WesBag.Toontown.BattleCore.Cogs.HQs.BoardingGroups;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.World;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import com.sk89q.worldguard.protection.regions.RegionContainer;

public class BoardingGroupController {
	public static List<ProtectedRegion> bgEligibleRegions = new ArrayList<>();
	
	public BoardingGroupController() {
		RegionContainer container = WorldGuard.getInstance().getPlatform().getRegionContainer();
		World world = Bukkit.getWorld("world");
		com.sk89q.worldedit.world.World wgWorld = BukkitAdapter.adapt(world);
		RegionManager regions = container.get(wgWorld);
		ProtectedRegion sbhqPR = regions.getRegion("SBHQ");
		
		
		
		
		bgEligibleRegions.add(sbhqPR);
	}
}
