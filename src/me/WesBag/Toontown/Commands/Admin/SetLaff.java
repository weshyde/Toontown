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

public class SetLaff implements CommandExecutor {
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender instanceof Player) {
			Player player = (Player) sender;
			if (label.equalsIgnoreCase("setlaff")) {
				if (!player.hasPermission("ttc.admin")) {
						player.sendMessage(Prefixes.NoPerm);
				}
				else if (args.length != 2) {
					player.sendMessage(Prefixes.Admin + ChatColor.RED + " Wrong Syntax! /setlaff <player> <laff>");
				}
				
				else {
					Player target = Bukkit.getPlayerExact(args[0]);
					if (target == null)
						player.sendMessage(Prefixes.Admin + ChatColor.RED + " Your target " + args[0] + " is offline!");
					else {
						if (!IsIntUtil.isInt(args[1])) {
							player.sendMessage(Prefixes.Admin + ChatColor.RED + " Your laff amount isn't an integer!");
						}
						else {
							int newLaff = IsIntUtil.getInt(args[1]);
							
							if (newLaff < 15 || newLaff > 137) {
								player.sendMessage(Prefixes.Admin + ChatColor.RED + " Your laff amount is not in range! (15-137)");
							}
							
							else {
								Toon toon = ToonsController.getToon(target.getUniqueId());
								toon.setMaxHealth(newLaff);
								if (target.getAttribute(Attribute.GENERIC_MAX_HEALTH).getDefaultValue() < (double) newLaff*2) {
									target.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue((double) newLaff*2);
									target.setHealth((double) newLaff*2);
								}
								player.sendMessage(Prefixes.Admin + ChatColor.GREEN + " Successfully set " + args[0] + "'s laff to " + newLaff + "!");
								target.sendMessage(Prefixes.Admin + ChatColor.GREEN + " Your laff has been set to " + newLaff + "!");
								//if (target.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue() < (double) (newLaff*2)) {
								//	target.getAttribute(Attribute.GENE)
								//}
								/*
								PlayerData pData = PlayerDataController.getPlayerData(target.getUniqueId());
								
								if (pData.getPlayerData().contains("laff")) {
									pData.getPlayerData().set("laff", newLaff);
									target.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue((double) newLaff*2);
									player.sendMessage(Prefixes.Admin + ChatColor.GREEN + " Successfully set " + args[0] + "'s laff to " + newLaff + "!");
								}
								else {
									pData.getPlayerData().set("laff", newLaff);
									player.sendMessage(Prefixes.Admin + ChatColor.GREEN + " Player didn't have laff already set! Set to " + newLaff + "!");
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
