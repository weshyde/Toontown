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
import me.WesBag.Toontown.Trolley.Minigames.CannonGame.NewCannonGame;
import net.md_5.bungee.api.ChatColor;

public class CreateMinigameMenu implements CommandExecutor {
	
	private Map<UUID, Integer> makingMode = new HashMap<>();
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender instanceof Player) {
			Player player = (Player) sender;
			if (label.equalsIgnoreCase("createminigame")) {
				if (!player.hasPermission("ttc.admin")) {
					player.sendMessage(Prefixes.NoPerm);
				}
				else if (args.length != 1) {
					player.sendMessage(Prefixes.Admin + ChatColor.RED + " Wrong Syntax! /createminigame <Minigame #>");
					player.sendMessage(ChatColor.LIGHT_PURPLE + "[Minigames]");
					player.sendMessage(ChatColor.AQUA + "1 - CannonGame");
				}
				else if (makingMode.containsKey(player.getUniqueId())) { //Already ran command, making minigame
					int game = makingMode.get(player.getUniqueId());
					if (game == 1) { //Cannon game
						int num = NewCannonGame.arenas.size() + 1;
						if (args[0].equalsIgnoreCase("set1")) {
							Main.fDataFile.getData().set("minigames.cannongame." + num + ".1", StreetBattlePositioning.doublesToString(StreetBattlePositioning.locationToDoubles(player.getLocation())));
						}
						else if (args[0].equalsIgnoreCase("set2")) {
							Main.fDataFile.getData().set("minigames.cannongame." + num + ".2", StreetBattlePositioning.doublesToString(StreetBattlePositioning.locationToDoubles(player.getLocation())));
						}
						else if (args[0].equalsIgnoreCase("done")) {
							Main.fDataFile.saveDataFile();
							Location l1 = StreetBattlePositioning.stringToLocation(Main.fDataFile.getData().getString("minigames.cannongame." + num + ".1"));
							Location l2 = StreetBattlePositioning.stringToLocation(Main.fDataFile.getData().getString("minigames.cannongame." + num + ".2"));
							List<Location> ls = new ArrayList<>();
							ls.add(l1);
							ls.add(l2);
							NewCannonGame.arenas.add(ls);
							NewCannonGame.freeArenas.put((num-1), false);
						}
						else {
							player.sendMessage(Prefixes.Admin + ChatColor.RED + " Incorrect usage, use set1 or set2");
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
