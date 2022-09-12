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

public class MaxToon implements CommandExecutor {
	/*
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender instanceof Player) {
			Player player = (Player) sender;
			if (label.equalsIgnoreCase("maxtoon")) {
				if (!player.hasPermission("ttc.admin")) {
					player.sendMessage(Prefixes.NoPerm);
				}
				else if (args.length != 1) {
					player.sendMessage(Prefixes.Admin + ChatColor.RED + " Wrong Syntax! /maxtoon <player>");
				}
				
				else {
					Player target = Bukkit.getPlayerExact(args[0]);
					if (target == null)
						player.sendMessage(Prefixes.Admin + ChatColor.RED + " Your target " + args[0] + " is offline!");
					else {
						PlayerData pData = PlayerDataController.getPlayerData(target.getUniqueId());
						for (int i = 0; i < 6; i++) { //Change 6 to 7 to include drop eventually!
							pData.getPlayerData().set("gags." + BattleMenu.gagNames[0][i], 7);
							pData.getPlayerData().set("gags." + BattleMenu.gagNames[0][i] + "-exp", 10000);
							pData.getPlayerData().set("gags." + BattleMenu.gagNames[0][i] + "-amount", "10101010101010");
							player.sendMessage(Prefixes.Admin + ChatColor.GREEN + " Player has been successfully maxed!");
						}
						pData.savePlayerData();
					}
				}
			}
		}
		else
			sender.sendMessage("Only player's may use this command!");
		return false;
	}
	*/
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender instanceof Player) {
			Player player = (Player) sender;
			if (label.equalsIgnoreCase("maxtoon")) {
				if (!player.hasPermission("ttc.admin")) {
					player.sendMessage(Prefixes.NoPerm);
				}
				else if (args.length != 1) {
					player.sendMessage(Prefixes.Admin + ChatColor.RED + " Wrong Syntax! /maxtoon <player>");
				}
				
				else {
					Player target = Bukkit.getPlayerExact(args[0]);
					if (target == null)
						player.sendMessage(Prefixes.Admin + ChatColor.RED + " Your target " + args[0] + " is offline!");
					else {
						Toon toon = ToonsController.getToon(target.getUniqueId());
						if (toon == null)
							player.sendMessage(Prefixes.Admin + ChatColor.RED + " Failed to load target's Toon!");
						else {
							for (int i = 0; i < 7; i++) { //Change 6 to 7 to include drop eventually! I did 1/21/22 4:33pm
								toon.setGagsUnlocked(i, 7);
								toon.setGagsExp(i, 10000);							
							}
							toon.setAllGagsAmount(10);
							toon.setMaxHealth(137);
							toon.refreshToonGags();
							player.sendMessage(Prefixes.Admin + ChatColor.GREEN + " Player has been successfully maxed!");
							target.sendMessage(Prefixes.Admin + ChatColor.GREEN + " You have been maxed out!");
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
