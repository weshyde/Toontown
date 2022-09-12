package me.WesBag.TTCore.Commands.CommandTesting;

import java.io.File;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.WesBag.TTCore.Main;
import me.WesBag.TTCore.Animations.CogAnimations.SpawnBuildingAnimation;
import me.WesBag.TTCore.BattleMenu.Cogs.CogBuildings.CogBuildingController;
import me.WesBag.TTCore.Commands.Prefixes;
import me.WesBag.TTCore.SchematicUtils.SchematicPaster;
import me.WesBag.TTCore.Streets.StreetCogsLoader;
import me.WesBag.TTCore.Streets.StreetsController;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.ai.NavigatorParameters;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.npc.ai.StraightLineNavigationStrategy;
import net.citizensnpcs.trait.waypoint.LinearWaypointProvider;
import net.citizensnpcs.trait.waypoint.Waypoint;
import net.citizensnpcs.trait.waypoint.Waypoints;

public class TestCommand implements CommandExecutor {
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender instanceof Player) {
			Player player = (Player) sender;
			
			if (label.equalsIgnoreCase("spawnbuilding")) {
				if (!player.hasPermission("ttc.admin")) {
					player.sendMessage(Prefixes.NoPerm);
				}
				else if (args.length != 1) {
					player.sendMessage(Prefixes.Admin + ChatColor.RED + " Wrong Syntax! /spawnbuilding <Cog-Takeover-Name>");
				}
				else {
					String cogName = args[0];
					cogName.replace("-", " ");
					CogBuildingController.spawnBuilding(player, player.getLocation(), cogName);
				}
			}
			/*
			else if (label.equalsIgnoreCase("testbuildingpaste")) {
				if (!player.hasPermission("ttc.admin")) {
					player.sendMessage(Prefixes.NoPerm);
				}
				else if (args.length != 4) {
					player.sendMessage(Prefixes.Admin + ChatColor.RED + " Wrong Syntax! /testbuildingpaste <Stories> <X> <Y> <Z>");
				}
				else {
					int levels = Integer.parseInt(args[0]);
					int x = Integer.parseInt(args[1]);
					int y = Integer.parseInt(args[2]);
					int z = Integer.parseInt(args[3]);
					//File file2 = new File();
					File file = new File(Main.getInstance().getDataFolder().getAbsolutePath() + "/schematics/" + levels +  "-Floor.schem");
					SchematicPaster.pasteSchematic2("world", file, x, y, z, true);
				}
			}
			*/
			else if (label.equalsIgnoreCase("testpathing")) {
				if (!player.hasPermission("ttc.admin")) {
					player.sendMessage(Prefixes.NoPerm);
				}
				else if (args.length != 4) {
					player.sendMessage(Prefixes.Admin + ChatColor.RED + " Wrong Syntax! /testpathing <NPCID> <LOCATION NUM> <DISTANCE MARGIN> <SPEED>");
				}
				else {
					int npcID = Integer.parseInt(args[0]);
					int locationNum = Integer.parseInt(args[1]);
					double distanceMargin = Double.parseDouble(args[2]);
					float baseSpeed = Float.parseFloat(args[3]);
					NPC npc = CitizensAPI.getNPCRegistry().getById(npcID);					
					Location loc = StreetCogsLoader.loopyLaneLocations.get(locationNum);
					//StreetsController.loopyLaneLocationMap.put(npc.getUniqueId(), locationNum);
					//npc.getNavigator().getLocalParameters().
					//npc.getNavigator().getLocalParameters().baseSpeed(0.8f);
					//npc.getNavigator().getLocalParameters().distanceMargin(distanceMargin);
					if (!npc.hasTrait(Waypoints.class)) {
						npc.getOrAddTrait(Waypoints.class);
						player.sendMessage(Prefixes.Admin + ChatColor.GREEN + " Adding waypoints trait to NPC!");
					}
					player.sendMessage(Prefixes.Admin + ChatColor.GREEN + " NPC Waypoint set with distance margin of " + distanceMargin);
					//npc.getNavigator().get
					//NavigatorParameters np = new NavigatorParameters();
					//np.pathDistanceMargin(0.8);
					//np.useNewPathfinder(true);
					//player.sendMessage("NPC Base Speed: " + np.baseSpeed());
					//np.baseSpeed((float) 1.3);
					//StraightLineNavigationStrategy slns = new StraightLineNavigationStrategy(npc, loc, np);
					//npc.getOrAddTrait(Waypoints.class).getCurrentProvider().se;
					((LinearWaypointProvider) npc.getOrAddTrait(Waypoints.class).getCurrentProvider()).addWaypoint(new Waypoint(loc));
					npc.getNavigator().getLocalParameters().distanceMargin(distanceMargin);
					npc.getNavigator().getLocalParameters().baseSpeed(baseSpeed);
					npc.getNavigator().getLocalParameters().useNewPathfinder(true);
					//npc.getNavigator()
					//npc.getOrAddTrait(Waypoints.class).getCurrentProvider().st
					//((LinearWaypointProvider) npc.getOrAddTrait(Waypoints.class).getCurrentProvider()).addWaypoint(new Waypoint(waypointLocation));
				}
			}
			
			else if (label.equalsIgnoreCase("testbuildinganimation")) {
				if (!player.hasPermission("ttc.admin")) {
					player.sendMessage(Prefixes.NoPerm);
				}
				else if (args.length != 1) {
					player.sendMessage(Prefixes.Admin + ChatColor.RED + " Wrong Syntax! /testbuildinganimation <Material>");
				}
				else {
					//Material material = (Material) args[0];
					Material material = Material.valueOf(args[0]);
					if (material == null) {
						player.sendMessage("Invalid Material");
						return false;
					}
					SpawnBuildingAnimation.spawnBuildingAnimation(player.getLocation().add(0, 10, 0), material);
					player.sendMessage(Prefixes.Admin + ChatColor.GREEN + " Spawning blocks above you!");
				}
			}
			
			else if (label.equalsIgnoreCase("testspawncogs")) {
				if (!player.hasPermission("ttc.admin")) {
					player.sendMessage(Prefixes.NoPerm);
				}
				else {
					StreetCogsLoader.loadAllStreetCogs();
					player.sendMessage(Prefixes.Admin + ChatColor.GREEN + " Loading and spawning all cogs!");
				}
			}
		}
		
		
		return false;
	}
}
