package me.WesBag.Toontown.Commands.Admin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.WesBag.Toontown.Main;
import me.WesBag.Toontown.Commands.Prefixes;
import me.WesBag.Toontown.Streets.StreetBattlePositioning;
import me.WesBag.Toontown.Trolley.Minigames.CannonGame.CannonGame;
import me.WesBag.Toontown.Trolley.Minigames.IceSlide.NewIceSlide;
import me.WesBag.Toontown.Trolley.Minigames.TagGame.ToonTag;
import net.md_5.bungee.api.ChatColor;

public class CreateMinigameMenu implements CommandExecutor {
	
	private Map<UUID, Integer> makingMode = new HashMap<>();
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender instanceof Player) {
			Player player = (Player) sender;
			if (label.equalsIgnoreCase("createminigame")) {
				player.sendMessage("CannonGame arenas size = " + CannonGame.arenas.size());
				player.sendMessage("IceSlide arenas size = " + NewIceSlide.arenas.size());
				if (!player.hasPermission("ttc.admin")) {
					player.sendMessage(Prefixes.NoPerm);
				}
				else if (args.length != 1) {
					player.sendMessage(Prefixes.Admin + ChatColor.RED + " Wrong Syntax! /createminigame <Minigame #>");
					player.sendMessage(ChatColor.LIGHT_PURPLE + "[Minigame #s]");
					player.sendMessage(ChatColor.AQUA + "1 - CannonGame");
					player.sendMessage(ChatColor.AQUA + "2 - IceSlide");
					player.sendMessage(ChatColor.AQUA + "3 - ToonTag");
				}
				else if (makingMode.containsKey(player.getUniqueId())) { //Already ran command, making minigame
					int game = makingMode.get(player.getUniqueId());
					if (game == 1) { //Cannon game
						int num = CannonGame.arenas.size() + 1;
						if (args[0].equalsIgnoreCase("set1")) {
							Main.fDataFile.getData().set("minigames.cannongame." + num + ".1", StreetBattlePositioning.doublesToString(StreetBattlePositioning.locationToDoubles(player.getLocation())));
							player.sendMessage(Prefixes.Admin + ChatColor.GREEN + " Successfully set players spawn locations");
						}
						else if (args[0].equalsIgnoreCase("set2")) {
							Main.fDataFile.getData().set("minigames.cannongame." + num + ".2", StreetBattlePositioning.doublesToString(StreetBattlePositioning.locationToDoubles(player.getLocation())));
							player.sendMessage(Prefixes.Admin + ChatColor.GREEN + " Successfully set water tower spawn location");
						}
						else if (args[0].equalsIgnoreCase("done")) {
							Main.fDataFile.saveDataFile();
							
							if (!(Main.fDataFile.getData().contains("minigames.cannongame." + num + ".1")) || (!(Main.fDataFile.getData().contains("minigames.cannongame." + num + ".2")))) {
								player.sendMessage(Prefixes.Admin + ChatColor.RED + " FAILED! Not enough locations set, fix data file!");
								return false;
							}
							
							Location l1 = StreetBattlePositioning.stringToLocation(Main.fDataFile.getData().getString("minigames.cannongame." + num + ".1"));
							Location l2 = StreetBattlePositioning.stringToLocation(Main.fDataFile.getData().getString("minigames.cannongame." + num + ".2"));
							List<Location> ls = new ArrayList<>();
							ls.add(l1);
							ls.add(l2);
							CannonGame.arenas.add(ls);
							CannonGame.freeArenas.put((num-1), false);
							makingMode.remove(player.getUniqueId());
							player.sendMessage(Prefixes.Admin + ChatColor.GREEN + " Successfully saved new minigame arena");
						}
						else {
							player.sendMessage(Prefixes.Admin + ChatColor.RED + " Incorrect argument");
						}
					}
					else if (game == 2) {
						int num = NewIceSlide.arenas.size() + 1;
						if (args[0].equalsIgnoreCase("set1")) {
							Main.fDataFile.getData().set("minigames.iceslide." + num + ".1", StreetBattlePositioning.doublesToString(StreetBattlePositioning.locationToDoubles(player.getLocation())));
							player.sendMessage(Prefixes.Admin + ChatColor.GREEN + " Successfully set players spawn locations");
						}
						else if (args[0].equalsIgnoreCase("set2")) {
							Main.fDataFile.getData().set("minigames.iceslide." + num + ".2", StreetBattlePositioning.doublesToString(StreetBattlePositioning.locationToDoubles(player.getLocation())));
							player.sendMessage(Prefixes.Admin + ChatColor.GREEN + " Successfully set center location");
						}
						else if (args[0].equalsIgnoreCase("set3")) {
							Main.fDataFile.getData().set("minigames.iceslide." + num + ".3", StreetBattlePositioning.doublesToString(StreetBattlePositioning.locationToDoubles(player.getLocation())));
							player.sendMessage(Prefixes.Admin + ChatColor.GREEN + " Successfully set corner1 location");
						}
						else if (args[0].equalsIgnoreCase("set4")) {
							Main.fDataFile.getData().set("minigames.iceslide." + num + ".4", StreetBattlePositioning.doublesToString(StreetBattlePositioning.locationToDoubles(player.getLocation())));
							player.sendMessage(Prefixes.Admin + ChatColor.GREEN + " Successfully set corner2 location");
						}
						else if (args[0].equalsIgnoreCase("done")) {
							Main.fDataFile.saveDataFile();
							
							if (!(Main.fDataFile.getData().contains("minigames.iceslide." + num + ".1")) || (!(Main.fDataFile.getData().contains("minigames.iceslide." + num + ".4")))) {
								player.sendMessage(Prefixes.Admin + ChatColor.RED + " FAILED! Not enough locations set, fix data file!");
								return false;
							}
							
							Location l1 = StreetBattlePositioning.stringToLocation(Main.fDataFile.getData().getString("minigames.iceslide." + num + ".1"));
							Location l2 = StreetBattlePositioning.stringToLocation(Main.fDataFile.getData().getString("minigames.iceslide." + num + ".2"));
							Location l3 = StreetBattlePositioning.stringToLocation(Main.fDataFile.getData().getString("minigames.iceslide." + num + ".3"));
							Location l4 = StreetBattlePositioning.stringToLocation(Main.fDataFile.getData().getString("minigames.iceslide." + num + ".4"));
							List<Location> ls = new ArrayList<>();
							ls.add(l1);
							ls.add(l2);
							ls.add(l3);
							ls.add(l4);
							NewIceSlide.arenas.add(ls);
							NewIceSlide.freeArenas.put((num-1), false);
							makingMode.remove(player.getUniqueId());
							player.sendMessage(Prefixes.Admin + ChatColor.GREEN + " Successfully saved new minigame arena");
						}
						else {
							player.sendMessage(Prefixes.Admin + ChatColor.RED + " Incorrect argument");
						}
					}
					else if (game == 3) {
						int num = ToonTag.arenas.size() + 1;
						if (args[0].equalsIgnoreCase("set1")) {
							Main.fDataFile.getData().set("minigames.toontag." + num + ".1", StreetBattlePositioning.doublesToString(StreetBattlePositioning.locationToDoubles(player.getLocation())));
							player.sendMessage(Prefixes.Admin + ChatColor.GREEN + " Successfully set players spawn locations");
						}
						else if (args[0].equalsIgnoreCase("set2")) {
							Main.fDataFile.getData().set("minigames.toontag." + num + ".2", StreetBattlePositioning.doublesToString(StreetBattlePositioning.locationToDoubles(player.getLocation())));
							player.sendMessage(Prefixes.Admin + ChatColor.GREEN + " Successfully set corner1 location");
						}
						else if (args[0].equalsIgnoreCase("set3")) {
							Main.fDataFile.getData().set("minigames.toontag." + num + ".3", StreetBattlePositioning.doublesToString(StreetBattlePositioning.locationToDoubles(player.getLocation())));
							player.sendMessage(Prefixes.Admin + ChatColor.GREEN + " Successfully set corner2 location");
						}
						else if (args[0].equalsIgnoreCase("done")) {
							Main.fDataFile.saveDataFile();
							//Implement safety check to make sure all locations have been set! 1-1-23
							
							Location l1 = StreetBattlePositioning.stringToLocation(Main.fDataFile.getData().getString("minigames.toontag." + num + ".1"));
							Location l2 = StreetBattlePositioning.stringToLocation(Main.fDataFile.getData().getString("minigames.toontag." + num + ".2"));
							Location l3 = StreetBattlePositioning.stringToLocation(Main.fDataFile.getData().getString("minigames.toontag." + num + ".3"));
							
							List<Location> ls = new ArrayList<>();
							ls.add(l1);
							ls.add(l2);
							ls.add(l3);
							ToonTag.arenas.add(ls);
							ToonTag.freeArenas.put((num-1), false);
							makingMode.remove(player.getUniqueId());
							player.sendMessage(Prefixes.Admin + ChatColor.GREEN + " Successfully saved new minigame arena");
						}
						else {
							player.sendMessage(Prefixes.Admin + ChatColor.RED + " Incorrect argument");
						}
					}
					else {
						player.sendMessage(Prefixes.Admin + ChatColor.RED + " Game index somehow out of range!");
						makingMode.remove(player.getUniqueId());
					}
				}
				else if (!IsIntUtil.isInt(args[0])){
					player.sendMessage(Prefixes.Admin + ChatColor.RED + " Minigame number must be an integer");
				}
				else {
					int id = IsIntUtil.getInt(args[0]);
					if (id == 1) { //Cannon game
						player.sendMessage(Prefixes.Admin + ChatColor.GREEN + " CannonGame needs two locations: First Player location and WaterTower Location");
						player.sendMessage(ChatColor.GREEN + "Use '/createminigame set1' to set the first player location to your currnet location");
						player.sendMessage(ChatColor.GREEN + "Use '/createminigame set2' to set the water tower location to your current location");
						player.sendMessage(ChatColor.GREEN + "Use '/createminigame done' to finish and save");
						makingMode.put(player.getUniqueId(), 1);
					}
					else if (id == 2) {
						player.sendMessage(Prefixes.Admin + ChatColor.GREEN + " IceSlide needs four locations:");
						player.sendMessage(ChatColor.GREEN + "Player 1 Spawn - Use '/createminigame set1' to set the first player location to your currnet location");
						player.sendMessage(ChatColor.AQUA + "Center Location - Use '/createminigame set2' to set the center location to your current location");
						player.sendMessage(ChatColor.GREEN + "Corner1 Location - Use '/createminigame set3' to set the first corner location to your current location");
						player.sendMessage(ChatColor.AQUA + "Opposite Corner2 Location - Use '/createminigame set4' to set the second corner location to your current location");
						player.sendMessage(ChatColor.GREEN + "Use '/createminigame done' to finish and save");
						makingMode.put(player.getUniqueId(), 2);
					}
					else if (id == 3) {
						player.sendMessage(Prefixes.Admin + ChatColor.GREEN + " ToonTag needs three locations:");
						player.sendMessage(ChatColor.GREEN + "Player 1 Spawn - Use '/createminigame set1' to set the first player location to your currnet location");
						player.sendMessage(ChatColor.GREEN + "Corner1 Location - Use '/createminigame set2' to set the first corner location to your current location");
						player.sendMessage(ChatColor.AQUA + "Opposite Corner2 Location - Use '/createminigame set3' to set the second corner location to your current location");
						player.sendMessage(ChatColor.GREEN + "Use '/createminigame done' to finish and save");
						makingMode.put(player.getUniqueId(), 3);
					}
					else {
						player.sendMessage(Prefixes.Admin + ChatColor.RED + " Minigame number out of range (1-5)");
					}
				}
			}
		}
		return false;
	}
}
