package me.WesBag.TTCore.Commands.AdminCommands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.WesBag.TTCore.Commands.Prefixes;
import me.WesBag.TTCore.Streets.StreetCogsLoader;
import net.md_5.bungee.api.ChatColor;

public class StartInvasion implements CommandExecutor {
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender instanceof Player) {
			Player player = (Player) sender;
			if (label.equalsIgnoreCase("startinvasion")) {
				if (!player.hasPermission("ttc.admin")) {
						player.sendMessage(Prefixes.NoPerm);
				}
				else if (args.length != 3) {
					player.sendMessage(Prefixes.Admin + ChatColor.RED + " Wrong Syntax! /startinvasion <StreetNum> <CogSuitNum> <CogNameNum>");
				}
				else {
					if (!IsIntUtil.isInt(args[0])) {
						player.sendMessage(Prefixes.Admin + ChatColor.RED + " Your street number isn't an integer!");
					}
					else if (!IsIntUtil.isInt(args[1])) {
						player.sendMessage(Prefixes.Admin + ChatColor.RED + " Your cog suit number isn't an integer!");
					}
					else if (!IsIntUtil.isInt(args[2])) {
						player.sendMessage(Prefixes.Admin + ChatColor.RED + " Your cog name number isn't an integer!");
					}
					else {
						int streetNum = IsIntUtil.getInt(args[0]);
						int cogSuitNum = IsIntUtil.getInt(args[1]);
						int cogNameNum = IsIntUtil.getInt(args[2]);
						if (streetNum < 1 || streetNum > StreetCogsLoader.allPlaygroundStreets.size()) {
							player.sendMessage(Prefixes.Admin + ChatColor.RED + " Your street number is out of range! (1-" + StreetCogsLoader.allPlaygroundStreets.size() + ")");	
						}
						else if (cogSuitNum < 1 || cogSuitNum > 4) {
							player.sendMessage(Prefixes.Admin + ChatColor.RED + " Your cog suit number is out of range! (1-4)");
						}
						else if (cogNameNum < 1 || cogNameNum > 8) {
							player.sendMessage(Prefixes.Admin + ChatColor.RED + " Your cog name number is out of range! (1-8)");
						}
						else {
							StreetCogsLoader.startInvasion(--streetNum, --cogSuitNum, --cogNameNum);
							player.sendMessage(Prefixes.Admin + ChatColor.GREEN + " Successfully started invasion at street number: " + streetNum + "!");
						}
					}
				}
			}
		}
		return false;
	}
}
