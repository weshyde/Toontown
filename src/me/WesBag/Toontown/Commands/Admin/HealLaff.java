package me.WesBag.TTCore.Commands.AdminCommands;

import org.bukkit.Bukkit;
import org.bukkit.attribute.Attribute;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.WesBag.TTCore.BattleMenu.Toons.Toon;
import me.WesBag.TTCore.BattleMenu.Toons.ToonsController;
import me.WesBag.TTCore.Commands.Prefixes;
import me.WesBag.TTCore.Files.PlayerData;
import me.WesBag.TTCore.Files.PlayerDataController;
import net.md_5.bungee.api.ChatColor;

public class HealLaff implements CommandExecutor {
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender instanceof Player) {
			Player player = (Player) sender;
			if (label.equalsIgnoreCase("heallaff")) {
				if (!player.hasPermission("ttc.admin")) {
					player.sendMessage(Prefixes.NoPerm);
				}
				else if (args.length != 1) {
					player.sendMessage(Prefixes.Admin + ChatColor.RED + " Wrong Syntax! /heallaff <player>");
				}
				else {
					Player target = Bukkit.getPlayerExact(args[0]);
					if (target == null)
						player.sendMessage(Prefixes.Admin + ChatColor.RED + " Your target " + args[0] + " is offline!");
					else if (!IsIntUtil.isInt(args[1])) {
						
					}
					else {
						Toon toon = ToonsController.getToon(target.getUniqueId());
						int maxLife = toon.getMaxHealth();
						toon.getToon().setHealth(maxLife*2);
						player.sendMessage(Prefixes.Admin + ChatColor.GREEN + " Successfully healed " + args[0] + " to max!");
						target.sendMessage(Prefixes.Admin + ChatColor.GREEN + " You have been healed!");
						/*
						PlayerData pData = PlayerDataController.getPlayerData(target.getUniqueId());
						if (pData.getPlayerData().contains("laff")) {
							int maxLifeInt = (int) pData.getPlayerData().get("laff");
							double maxLifeFinal = maxLifeInt * 2;
							target.setHealth(maxLifeFinal);
							player.sendMessage(Prefixes.Admin + ChatColor.GREEN + " Successfully set " + args[0] + " laff up to " + maxLifeInt + "!");
							target.sendMessage(Prefixes.Admin + ChatColor.GREEN + " You have been healed to your max laff!");
						}
						
						else {
							pData.getPlayerData().set("laff", 15);
							target.setHealth(30.0);
							target.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(30);
							player.sendMessage(Prefixes.Admin + ChatColor.GREEN + " Player had no laff in their data! Setting to 15...");
							pData.savePlayerData();
						}
						*/
					}
				}
			}
		}
		
		return false;
	}
}
