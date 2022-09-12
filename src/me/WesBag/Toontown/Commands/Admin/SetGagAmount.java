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

public class SetGagAmount implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender instanceof Player) {
			Player player = (Player) sender;
			if (label.equalsIgnoreCase("setgagamount")) {
				if (!player.hasPermission("ttc.admin")) {
						player.sendMessage(Prefixes.NoPerm);
				}
				else if (args.length != 4) {
					player.sendMessage(Prefixes.Admin + ChatColor.RED + " Wrong Syntax! /setgagamount <player> <track> <gag> <amount>");
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
						else if (!IsIntUtil.isInt(args[3]))
							player.sendMessage(Prefixes.Admin + ChatColor.RED + " Your gag amount isn't an integer!");
						else {
							int tempTrack = IsIntUtil.getInt(args[1]);
							int tempGag = IsIntUtil.getInt(args[2]);
							int tempGagNum = IsIntUtil.getInt(args[3]);
							Toon toon = ToonsController.getToon(target.getUniqueId());
							int maxPlayerGags = toon.getMaxGags();
							int currentPlayerGags = toon.getCurrentGagAmount();
							//int maxPlayerGags = (Integer) PlayerDataController.getPlayerData(target.getUniqueId()).getPlayerData().get("gags.max");
							//int currentPlayerGags = (Integer) PlayerDataController.getPlayerData(target.getUniqueId()).getPlayerData().get("gags.current");
							if (tempTrack < 1 || tempTrack > 7)
								player.sendMessage(Prefixes.Admin + ChatColor.RED + " Your track number is out of range! (1-7)");
							else if (tempGag < 1 || tempGag > 7)
								player.sendMessage(Prefixes.Admin + ChatColor.RED + " Your gag number is out of range! (1-7");
							else if ((currentPlayerGags + tempGagNum) < 0 || (tempGagNum + currentPlayerGags) > maxPlayerGags || tempGagNum > 50)
								player.sendMessage(Prefixes.Admin + ChatColor.RED + " Your gag amount is out of the players max gag amount range! (0-50) Gags : " + currentPlayerGags + " / " + maxPlayerGags);
							else {
								if (toon.getUnlockedTrackAmount(tempTrack-1) > tempGag) {
									player.sendMessage(Prefixes.Admin + ChatColor.RED + " Error: Player doesn't have that track yet!");
								}
								else {
									toon.setTrackGagAmount(tempTrack-1, tempGag-1, tempGagNum);
									target.sendMessage(Prefixes.Admin + ChatColor.GREEN + " You were given " + tempGagNum + " " + BattleCore.gagNames[tempTrack][tempGag-1] + "!");
									player.sendMessage(Prefixes.Admin + ChatColor.GREEN + " Player's track " + tempTrack + " gag " + tempGag + " amount set to " + tempGagNum + "!");
								}
								
								/*
								PlayerData pData = PlayerDataController.getPlayerData(target.getUniqueId());
								if (pData.getPlayerData().contains("gags." + BattleMenu.gagNames[0][tempTrack-1] + "-amount")) {
									String tempGagAmount = (String) pData.getPlayerData().get("gags." + BattleMenu.gagNames[0][tempTrack-1] + "-amount");
									//pData.getPlayerData().get
									System.out.println("-----setgagamount debug");
									System.out.println("tempGagAmount: " + tempGagAmount);
									String newGagAmount = String.valueOf(tempGagNum);
									System.out.println("newGagAmount: " + newGagAmount);
									int minusAmt = 2;
									//boolean lessThanTen = false;
									if (tempGagNum < 10) {
										//char c = 0;
										//lessThanTen = true;
										minusAmt = 1;
										newGagAmount = 0 + newGagAmount;
										System.out.println("less than ten! newGagAmount: " + newGagAmount);
									}
									
									//String newGagAmount = tempGagAmount.substring(0, tempGag-2) + String.valueOf(false)
									String newFinalGagAmount = tempGagAmount.substring(0, (tempGag*2)-minusAmt) + String.valueOf(tempGagNum) + tempGagAmount.substring(tempGag*2);
									//String newFinalGagAmount = tempGagAmount.substring(0, (tempGag*2)-1) + String.valueOf(tempGagNum) + tempGagAmount.substring((tempGag*2));
									System.out.println("newFinalGagAmount: " + newFinalGagAmount);
									pData.getPlayerData().set("gags." + BattleMenu.gagNames[0][tempTrack-1] + "-amount", newFinalGagAmount);
									player.sendMessage(Prefixes.Admin + ChatColor.GREEN + " Player's track " + tempTrack + " gag " + tempGag + " amount to " + tempGagNum + "!");
									pData.savePlayerData();
								}
								
								else
									player.sendMessage(Prefixes.Admin + ChatColor.RED + " Error: Player doesn't have that gag track yet!");
								*/
								//else { //Add and fix eventually...
								//	String newGagAmount = "0000000";
								//	pData.getPlayerData().set("gags. " BattleMenu.gagNames[0][tempTrack-1], target)
								//	pData.getPlayerData().set("gags." + BattleMenu.gagNames[0][tempTrack-1] + "-amount", tempGagNum);
								//	pData.getPlayerData().set("gags." + BattleMenu.gagNames[0][tempTrack-1] + "-exp", 0);
								//	player.sendMessage(Prefixes.Admin + ChatColor.GREEN + " Player didn't have the track, adding to player file...");
								//}
							}
						}
					}
				}
			}
		}
		
		
		return false;
	}

}
