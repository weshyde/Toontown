package me.WesBag.Toontown.Commands.Admin;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.WesBag.Toontown.BattleCore.BattleCore;
import me.WesBag.Toontown.BattleCore.Toons.Toon;
import me.WesBag.Toontown.BattleCore.Toons.ToonsController;
import me.WesBag.Toontown.Commands.Prefixes;
import me.WesBag.Toontown.Files.PlayerData;
import me.WesBag.Toontown.Files.PlayerDataController;
import net.md_5.bungee.api.ChatColor;

public class SetGagExp implements CommandExecutor {
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender instanceof Player) {
			Player player = (Player) sender;
			if (label.equalsIgnoreCase("setgagexp")) {
				if (!player.hasPermission("ttc.admin")) {
						player.sendMessage(Prefixes.NoPerm);
				}
				else if (args.length != 3) {
					player.sendMessage(Prefixes.Admin + ChatColor.RED + " Wrong Syntax! /setgagexp <player> <track> <expAmount>");
				}

				else {
					Player target = Bukkit.getPlayerExact(args[0]);
					if (target == null)
						player.sendMessage(Prefixes.Admin + ChatColor.RED + " Your target " + args[0] + " is offline!");
					else {
						if (!IsIntUtil.isInt(args[1]))
							player.sendMessage(Prefixes.Admin + ChatColor.RED + " Your track isn't an integer!");
						else if (!IsIntUtil.isInt(args[2]))
							player.sendMessage(Prefixes.Admin + ChatColor.RED + " Your exp amount isn't an integer!");
						else {
							int tempTrack = IsIntUtil.getInt(args[1]);
							int tempExpNum = IsIntUtil.getInt(args[2]);
							
							if (tempTrack < 1 || tempTrack > 7)
								player.sendMessage(Prefixes.Admin + ChatColor.RED + " Your track number is out of range! (1-7)");
							else if (tempExpNum < 0 || tempExpNum > 10000)
								player.sendMessage(Prefixes.Admin + ChatColor.RED + " Your exp amount is out of range! (0-10000)");
							else {
								Toon toon = ToonsController.getToon(target.getUniqueId());
								toon.setGagsExp(tempTrack-1, tempExpNum);
								player.sendMessage(Prefixes.Admin + ChatColor.GREEN + " Player's track " + tempTrack + " exp sucessfully set to " + tempExpNum + "!");
								target.sendMessage(Prefixes.Admin + ChatColor.GREEN + " Your track " + tempTrack + " exp has been set to " + tempExpNum + "!");
								/*
								PlayerData pData = PlayerDataController.getPlayerData(target.getUniqueId());
								if (pData.getPlayerData().contains("gags." + BattleMenu.gagNames[0][tempTrack-1])) {
									pData.getPlayerData().set("gags." + BattleMenu.gagNames[0][tempTrack-1] + "-exp", tempExpNum);
									player.sendMessage(Prefixes.Admin + ChatColor.GREEN + " Player's track " + tempTrack + " exp sucessfully set to " + tempExpNum + "!");
								}
								
								else {
									pData.getPlayerData().set("gags." + BattleMenu.gagNames[0][tempTrack-1], 0);
									pData.getPlayerData().set("gags." + BattleMenu.gagNames[0][tempTrack-1] + "-exp", tempExpNum);
									pData.getPlayerData().set("gags." + BattleMenu.gagNames[0][tempTrack-1] + "-amount", 0000000);
									player.sendMessage(Prefixes.Admin + ChatColor.GREEN + " Player didn't have the track, adding to player file...");
								}
								pData.savePlayerData();
								*/
							}
						}
					}
				}
			}
		}
		
		return false;
	}

}
