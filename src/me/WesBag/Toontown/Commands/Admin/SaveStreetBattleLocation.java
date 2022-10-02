package me.WesBag.Toontown.Commands.Admin;

import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.WesBag.Toontown.Main;
import me.WesBag.Toontown.Commands.Prefixes;
import me.WesBag.Toontown.Streets.StreetBattlePositioning;
import net.md_5.bungee.api.ChatColor;

public class SaveStreetBattleLocation implements CommandExecutor {
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender instanceof Player) {
			Player player = (Player) sender;
			if (label.equalsIgnoreCase("savestreetbattlelocation")) {
				if (!player.hasPermission("ttc.admin")) {
					player.sendMessage(Prefixes.NoPerm);
				}
				else if (args.length != 1) {
					player.sendMessage(ChatColor.AQUA + "/savestreetbattlelocation <StreetIDNum>");
					player.sendMessage(ChatColor.WHITE + "[" + ChatColor.DARK_AQUA + "StreetID" + ChatColor.WHITE + "]");
					player.sendMessage(ChatColor.WHITE + "Format = XY");
					player.sendMessage(ChatColor.WHITE + "X: PlaygroundNum, starting at 1 for TTC");
					player.sendMessage(ChatColor.WHITE + "Y: StreetNum, starting at 1 for LoopyLane, TTC");
				}
				else if (IsIntUtil.isInt(args[0])) {
					int streetNum = IsIntUtil.getInt(args[0]);
					List<Double> locationList = StreetBattlePositioning.locationToDoubles(player.getLocation());
					String locationStr = StreetBattlePositioning.doublesToString(locationList);
					int nextInt = getStreetLocationsSize(streetNum) + 1;
					int streetLocsNextInt = getNextStreetLocationNum(streetNum);
					
					if (streetNum == 11) {
						Main.fDataFile.getData().set("locations.loopyLane.battleLocs." + nextInt, locationStr);
						StreetBattlePositioning.streetLocs.put(streetLocsNextInt, locationList);
					}
					else if (streetNum == 12) {
						Main.fDataFile.getData().set("locations.punchlinePlace.battleLocs." + nextInt, locationStr);
						StreetBattlePositioning.streetLocs.put(streetLocsNextInt, locationList);
					}
					else if (streetNum == 13) {
						Main.fDataFile.getData().set("locations.sillyStreet.battleLocs." + nextInt, locationStr);
						StreetBattlePositioning.streetLocs.put(streetLocsNextInt, locationList);
					}
					else {
						player.sendMessage(Prefixes.Admin + ChatColor.RED + " Street number out of range!");
						return false; //Exits so file doesn't save
					}
					//Eventually expand once more streets have been implemented! 9-27-22
					Main.fDataFile.saveDataFile();
				}
				else {
					player.sendMessage(Prefixes.Admin + ChatColor.RED + " Street number not an int!");
				}
			}
		}
		return false;
	}
	
	//Helper function to return the size of the amount of locations on passed street
	public int getStreetLocationsSize(int streetNum) {
		streetNum *= 100;
		int size = 0;
		while (StreetBattlePositioning.streetLocs.containsKey(streetNum)) {
			size++;
			streetNum++;
		}
		return size;
	}
	
	//Helper function to return the next available street location id for passed street
	public int getNextStreetLocationNum(int streetNum) {
		streetNum *= 100;
		while (StreetBattlePositioning.streetLocs.containsKey(streetNum)) {
			streetNum++;
		}
		streetNum++;
		return streetNum;
	}

}
