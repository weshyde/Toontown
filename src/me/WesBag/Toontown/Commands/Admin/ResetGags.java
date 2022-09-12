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

public class ResetGags implements CommandExecutor {
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender instanceof Player) {
			Player player = (Player) sender;
			if (label.equalsIgnoreCase("resetgags")) {
				if (!player.hasPermission("ttc.admin")) {
						player.sendMessage(Prefixes.NoPerm);
					}
				
				else if (args.length != 1) {
					player.sendMessage(Prefixes.Admin + ChatColor.RED + " Wrong Syntax! /resetgags <player>");
				}
				
				else {
					Player target = Bukkit.getPlayerExact(args[0]);
					if (target == null)
						player.sendMessage(Prefixes.Admin + ChatColor.RED + " Your target " + args[0] + " is offline!");
					else {
						Toon toon = ToonsController.getToon(target.getUniqueId());
						for (int i = 0; i < 7; i++) {
							toon.setGagsUnlocked(i, 0);
							toon.setGagsExp(i, 0);
						}
						toon.setAllGagsAmount(0);
						toon.setGagsUnlocked(5, 1);
						toon.setGagsUnlocked(6, 1);
						//toon.setMaxHealth(15);
						/*
						PlayerData pData = PlayerDataController.getPlayerData(target.getUniqueId());
						for (int i = 0; i < 7; i++) {
							if (pData.getPlayerData().contains("gags." + BattleMenu.gagNames[0][i])) {
								pData.getPlayerData().set("gags." + BattleMenu.gagNames[0][i], 0);
							}
							if (pData.getPlayerData().contains("gags." + BattleMenu.gagNames[0][i] + "-exp")) {
								pData.getPlayerData().set("gags." + BattleMenu.gagNames[0][i] + "-exp", 0);
							}
							if (pData.getPlayerData().contains("gags." + BattleMenu.gagNames[0][i] + "-amount")) {
								pData.getPlayerData().set("gags." + BattleMenu.gagNames[0][i] + "-amount", "00000000000000");
							}
						}
						pData.savePlayerData();
						*/
						player.sendMessage(Prefixes.Admin + ChatColor.GREEN + " Sucessfully reset " + args[0] + " gags and exp!");
						target.sendMessage(Prefixes.Admin + ChatColor.GREEN + " Your gags have been reset!");
					}
				}
			}
		}
		
		return false;
	}
	
}
