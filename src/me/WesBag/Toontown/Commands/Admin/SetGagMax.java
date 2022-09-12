package me.WesBag.Toontown.Commands.Admin;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.WesBag.Toontown.BattleCore.Toons.Toon;
import me.WesBag.Toontown.BattleCore.Toons.ToonsController;
import me.WesBag.Toontown.Commands.Prefixes;
import me.WesBag.Toontown.Files.PlayerData;
import me.WesBag.Toontown.Files.PlayerDataController;
import net.md_5.bungee.api.ChatColor;

public class SetGagMax implements CommandExecutor {
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender instanceof Player) {
			Player player = (Player) sender;
			if (label.equalsIgnoreCase("setgagmax")) {
				if (!player.hasPermission("ttc.admin")) {
					player.sendMessage(Prefixes.NoPerm);
				}
				else if (args.length != 2) {
					player.sendMessage(Prefixes.Admin + ChatColor.RED + " Wrong Syntax! /setgagmax <player> <gagPouchAmount>");
				}
				else {
					Player target = Bukkit.getPlayerExact(args[0]);
					if (target == null)
						player.sendMessage(Prefixes.Admin + ChatColor.RED + " Your target " + args[0] + " is offline!");
					else {
						if (!IsIntUtil.isInt(args[1])) {
							player.sendMessage(Prefixes.Admin + ChatColor.RED + " Your gag pouch amount isn't an integer!");
						}
						else {
							int newPouchSize = IsIntUtil.getInt(args[1]);
							
							if (newPouchSize < 0 || newPouchSize > 1000) {
								player.sendMessage(Prefixes.Admin + " Your new pouch size is out of range! (0-1000)");
							}
							else {
								Toon toon = ToonsController.getToon(target.getUniqueId());
								toon.setMaxGags(newPouchSize);
								player.sendMessage(Prefixes.Admin + ChatColor.GREEN + " Successfully set " + args[0] + " gag pouch size to " + newPouchSize + "!");
								target.sendMessage(Prefixes.Admin + ChatColor.GREEN + " Your gag pouch size has been set to " + newPouchSize + "!");
								/*
								PlayerData pData = PlayerDataController.getPlayerData(target.getUniqueId());
								if (pData.getPlayerData().contains("gags.max")) {
									player.sendMessage(Prefixes.Admin + ChatColor.GREEN + " Successfully set " + args[0] + " gag pouch size to " + newPouchSize + "!");
								}
								else {
									player.sendMessage(Prefixes.Admin + ChatColor.GREEN + args[0] + " didn't have have a max! Setting it to " + newPouchSize + "!");
								}
								pData.getPlayerData().set("gags.max", newPouchSize);
								pData.savePlayerData();
								target.sendMessage(Prefixes.Admin + ChatColor.GREEN + " Your gag pouch size has been set to " + newPouchSize + "!");
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
