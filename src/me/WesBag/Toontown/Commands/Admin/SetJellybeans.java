package me.WesBag.TTCore.Commands.AdminCommands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.WesBag.TTCore.BattleMenu.Toons.Toon;
import me.WesBag.TTCore.BattleMenu.Toons.ToonsController;
import me.WesBag.TTCore.Commands.Prefixes;
import net.md_5.bungee.api.ChatColor;

public class SetJellybeans implements CommandExecutor {
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender instanceof Player) {
			Player player = (Player) sender;
			if (label.equalsIgnoreCase("setjellybeans")) {
				if (!player.hasPermission("ttc.admin")) {
					player.sendMessage(Prefixes.NoPerm);
				}
				else if (args.length != 2) {
					player.sendMessage(Prefixes.Admin + ChatColor.RED + " Wrong Syntax! /setjellybeans <player> <amount>");
				}
				
				else {
					Player target = Bukkit.getPlayerExact(args[0]);
					if (target == null)
						player.sendMessage(Prefixes.Admin + ChatColor.RED + " Your target " + args[0] + " is offline!");
					else if (!IsIntUtil.isInt(args[1])){
						player.sendMessage(Prefixes.Admin + ChatColor.RED + " Your jellybean amount isn't an integer!");
					}
					else {
						int jbs = IsIntUtil.getInt(args[1]);
						Toon toon = ToonsController.getToon(target.getUniqueId());
						toon.setJellybeans(jbs);
						player.sendMessage(Prefixes.Admin + ChatColor.GREEN + " Successfully set " + args[0] + "'s jellybeans to " + jbs);
						target.sendMessage(Prefixes.Admin + ChatColor.GREEN + " Your jellybeans have been set to " + jbs);
					}
				}
			}
		}
		return false;
	}
}
