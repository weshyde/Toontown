package me.WesBag.TTCore.Commands.AdminCommands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.WesBag.TTCore.BattleMenu.Toons.Toon;
import me.WesBag.TTCore.BattleMenu.Toons.ToonsController;
import me.WesBag.TTCore.Commands.Prefixes;
import me.WesBag.TTCore.Fishing.FishController;
import me.WesBag.TTCore.Fishing.Fish.TTFish;
import net.md_5.bungee.api.ChatColor;

public class FishingBucketMenuCommand implements CommandExecutor {
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender instanceof Player) {
			Player player = (Player) sender;
			if (label.equalsIgnoreCase("fishing")) {
				if (!player.hasPermission("ttc.admin")) {
					player.sendMessage(Prefixes.NoPerm);
				}
				else if (args.length == 0) {
					player.sendMessage(ChatColor.DARK_PURPLE + "[Fishing Admin Menu]");
					player.sendMessage("/fishing give <Player> <SpeciesNum> <FishNum>");
					player.sendMessage("/fishing clear <Player>");
					player.sendMessage("/fishing setrod <Player> <RodNum>");
					player.sendMessage(ChatColor.AQUA + "[Fish]");
					player.sendMessage("1. Balloon Fish");
					player.sendMessage("   (1) BalloonFish (2) HotAirBF (3) WeatherBF (4) WaterBF (5) RedBF");
					player.sendMessage("2. Clown Fish");
					player.sendMessage("   (1) ClownFish (2) SadCF (3) CircusCF (4) PartyCF");
					player.sendMessage("TBContinued! (4-9-22)");
				}
				else if (args.length > 1) {
					Player target = Bukkit.getPlayerExact(args[1]);
					if (target == null) {
						player.sendMessage(Prefixes.Admin + ChatColor.RED + " Your target " + args[0] + " is offline!");
						return false;
					}
					System.out.println("ARGS LENGTH: " + args.length);
					if (args[0].equalsIgnoreCase("give") && args.length == 4) {
						if (IsIntUtil.isInt(args[2]) && IsIntUtil.isInt(args[3])) {
							int species = IsIntUtil.getInt(args[2]);
							int fish = IsIntUtil.getInt(args[3]);
							if (species < 1 || species > 18) {
								player.sendMessage("Your species number is out of range! (1-18)");
							}
							else if (fish < 1 || fish > FishController.allFishNames[(species-1)].length) {
								player.sendMessage("Your fish number is out of range! (1-" + FishController.allFishNames[(species-1)].length + ")");
							}
							else {
								Toon toon = ToonsController.getToon(target.getUniqueId());
								species--;
								fish--;
								if (toon.getFishAmount() >= 20) {
									player.sendMessage(Prefixes.Admin + ChatColor.RED + " Error: Target already has the maximum amount of fish");
								}
								else {
									TTFish givenFish = FishController.allFish[species][fish];
									int weight = givenFish.getWeight(toon.getCurrentRod()+1);
									int value = givenFish.getBeans2(weight, 10);
									toon.newFish(species, fish, weight, value);
									player.sendMessage(Prefixes.Admin + ChatColor.GREEN + " Successfully gave a " + FishController.allFishNames[species][fish] + " to " + target.getName());
									target.sendMessage(Prefixes.Admin + ChatColor.GREEN + " You have been given a " + FishController.allFishNames[species][fish]);
								}
							}
						}
						else {
							player.sendMessage(" Invalid Syntax! /fishing for help");
						}
					}
					else if (args[1].equalsIgnoreCase("clear") && args.length == 2) {
						Toon toon = ToonsController.getToon(target.getUniqueId());
						toon.getFishingBucket3().clear();
						player.sendMessage(Prefixes.Admin + ChatColor.GREEN + " Successfully clear " + target.getName() + "'s fishing bucket");
						target.sendMessage(Prefixes.Admin + ChatColor.GREEN + " Your fishing bucket has been cleared");
					}
					else if (args[1].equalsIgnoreCase("setrod") && args.length == 3) {
						if (IsIntUtil.isInt(args[2])) {
							int setRod = IsIntUtil.getInt(args[2]);
							if (setRod < 1 || setRod > 5) {
								player.sendMessage(Prefixes.Admin + ChatColor.RED + " Rod number out of range! (1-5)");
							}
							else {
								Toon toon = ToonsController.getToon(target.getUniqueId());
								toon.setCurrentRod((setRod-1));
								player.sendMessage(Prefixes.Admin + ChatColor.GREEN + " Successfully set " + target.getName() + "'s rod to " + setRod);
								target.sendMessage(Prefixes.Admin + ChatColor.GREEN + " Your rod number has been set to " + setRod);
							}
						}
						else {
							player.sendMessage(Prefixes.Admin + ChatColor.RED + " Invalid Syntax! /fishing for help 1");
						}
						
					}
					
					else {
						player.sendMessage(Prefixes.Admin + ChatColor.RED + " Invalid Syntax! Type /fishing for help 2");
					}
				}
				else {
					player.sendMessage(Prefixes.Admin + ChatColor.RED + " Invalid Syntax! Type /fishing for help 3");
				}
			}
		}
		
		return false;
	}
}
