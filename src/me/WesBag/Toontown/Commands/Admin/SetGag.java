package me.WesBag.TTCore.Commands.AdminCommands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.WesBag.TTCore.BattleMenu.BattleMenu;
import me.WesBag.TTCore.BattleMenu.Toons.Toon;
import me.WesBag.TTCore.BattleMenu.Toons.ToonsController;
import me.WesBag.TTCore.Commands.Prefixes;
import me.WesBag.TTCore.Files.PlayerData;
import me.WesBag.TTCore.Files.PlayerDataController;
import net.md_5.bungee.api.ChatColor;

public class SetGag implements CommandExecutor {
	

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender instanceof Player) {
			Player player = (Player) sender;
			if (label.equalsIgnoreCase("setgag")) {
				if (!player.hasPermission("ttc.admin")) {
						player.sendMessage(Prefixes.NoPerm);
				}
				else if (args.length != 3) {
					player.sendMessage(Prefixes.Admin + ChatColor.RED + " Wrong Syntax! /setgag <player> <track> <gagAmount>");
				}
				
				else {
					Player target = Bukkit.getPlayerExact(args[0]);
					if (target == null)
						player.sendMessage(Prefixes.Admin + ChatColor.RED + " Your target " + args[0] + " is offline!");
					else {
						//int tempTrack = IsIntUtil.getInt(args[1]);
						if (!IsIntUtil.isInt(args[1]))
							player.sendMessage(Prefixes.Admin + ChatColor.RED + " Your track isn't an integer!");
						else if (!IsIntUtil.isInt(args[2]))
							player.sendMessage(Prefixes.Admin + ChatColor.RED + " Your gag number isn't an integer!");
						else {
							int tempTrack = IsIntUtil.getInt(args[1]);
							int tempGagNum = IsIntUtil.getInt(args[2]);
							
							if (tempTrack < 1 || tempTrack > 7)
								player.sendMessage(Prefixes.Admin + ChatColor.RED + " Your track number is out of range! (1-7)");
							else if (tempGagNum < 0 || tempGagNum > 7)
								player.sendMessage(Prefixes.Admin + ChatColor.RED + " Your gag number is out of range! (0-7)");
							else {
								Toon toon = ToonsController.getToon(target.getUniqueId());
								toon.setGagsUnlocked(tempTrack-1, tempGagNum);
								toon.setAllTrackGagAmount(tempTrack-1, 0);
								toon.setGagsExp(tempTrack-1, 0);
								
								player.sendMessage(Prefixes.Admin + ChatColor.GREEN + " Player's track " + tempTrack + " sucessfully set to " + tempGagNum + "!");
								target.sendMessage(Prefixes.Admin + ChatColor.GREEN + " Your track " + tempTrack + " has been set to " + tempGagNum + "!");
								/*
								PlayerData pData = PlayerDataController.getPlayerData(target.getUniqueId());
								if (pData.getPlayerData().contains("gags." + BattleMenu.gagNames[0][tempTrack-1])) {
									pData.getPlayerData().set("gags." + BattleMenu.gagNames[0][tempTrack-1], tempGagNum);
									player.sendMessage(Prefixes.Admin + ChatColor.GREEN + " Player's track " + tempTrack + " sucessfully set to " + tempGagNum + "!");
								}
								
								else {
									pData.getPlayerData().set("gags." + BattleMenu.gagNames[0][tempTrack-1], tempGagNum);
									pData.getPlayerData().set("gags." + BattleMenu.gagNames[0][tempTrack-1] + "-exp", 0);
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
		else
			sender.sendMessage("Only player's may use this command!");
		return false;
	}
}