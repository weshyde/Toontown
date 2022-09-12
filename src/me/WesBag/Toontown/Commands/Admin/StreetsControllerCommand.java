package me.WesBag.Toontown.Commands.Admin;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.WesBag.Toontown.Commands.Prefixes;
import me.WesBag.Toontown.Streets.StreetCogsLoader;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import net.md_5.bungee.api.ChatColor;

public class StreetsControllerCommand implements CommandExecutor {
	
	public static final String[] allStreetNames = {"Loopy Lane", "Punchline Place", "Silly Street"};
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender instanceof Player) {
			Player player = (Player) sender;
			if (label.equalsIgnoreCase("streets")) {
				player.sendMessage("Num of Args: " + args.length);
				if (!player.hasPermission("ttc.admin")) {
					player.sendMessage(Prefixes.NoPerm);
				}
				//else if (args.length != 1 && args.length != 3) {
				//	player.sendMessage(Prefixes.Admin + ChatColor.RED + " Wrong Syntax! /streets for help");
				//}
				else {
					if (args.length == 0) {
						player.sendMessage(Prefixes.Admin + ChatColor.GREEN + " [Streets]");
						player.sendMessage(ChatColor.DARK_AQUA + " Options:");
						player.sendMessage(ChatColor.DARK_AQUA + "- clear <StreetNum> # Clears Street");
						player.sendMessage(ChatColor.DARK_AQUA + "- spawn <StreetNum> # Spawns cogs on street");
						player.sendMessage(ChatColor.DARK_AQUA + "- invasion <StreetNum> <CogSuitNum> <CogNameNum> # Starts Invasion");
						for (int i = 0; i < allStreetNames.length; i++) {
							player.sendMessage((i+1) + ": " + allStreetNames[i]);
						}
					}
					else if (args.length != 2 && args.length != 4) {
						player.sendMessage(Prefixes.Admin + ChatColor.RED + " Invalid Syntax");
					}
					else if (args.length == 2) {
						if (args[0].equalsIgnoreCase("clear")) {
							if (IsIntUtil.isInt(args[1])) {
								int streetNum = IsIntUtil.getInt(args[1]);
								if (streetNum > 0 && streetNum <= StreetCogsLoader.allStreetNPCs.size()) {
									List<UUID> streetCogs = new ArrayList<>(StreetCogsLoader.allStreetNPCs.get(streetNum-1));
									for (UUID cUUID : streetCogs) {
										StreetCogsLoader.allStreetNPCs.get(streetNum-1).clear();
										NPC cog = CitizensAPI.getNPCRegistry().getByUniqueId(cUUID);
										cog.destroy();
									}
									player.sendMessage(Prefixes.Admin + ChatColor.GREEN + " Successfully cleared street number " + streetNum + " of cogs");
								}
								else {
									player.sendMessage(Prefixes.Admin + ChatColor.RED + " Your street number is out of range! (1-" + StreetCogsLoader.allStreetNPCs.size() + ")");
								}
							}
							else {
								player.sendMessage(Prefixes.Admin + ChatColor.RED + " Your street number isn't an integer!");
							}
						//}
						//else {
						//	player.sendMessage(Prefixes.Admin + ChatColor.RED + " Wrong Syntax! /streets for help");
						//}
						}
						else if (args[0].equalsIgnoreCase("spawn")) {
							if (IsIntUtil.isInt(args[1])) {
								int streetNum = IsIntUtil.getInt(args[1]);
								if (streetNum > 0 && streetNum <= StreetCogsLoader.allStreetNPCs.size()) {
									List<UUID> streetCogs = new ArrayList<>(StreetCogsLoader.allStreetNPCs.get(streetNum-1));
									if (streetCogs.isEmpty()) {
										StreetCogsLoader.loadStreetCogs(streetNum);
										player.sendMessage(Prefixes.Admin + ChatColor.GREEN + " Successfully spawned cogs on street number " + streetNum);
									}
									else {
										player.sendMessage(Prefixes.Admin + ChatColor.RED + " This street already has cogs");
									}
								}
								else {
									player.sendMessage(Prefixes.Admin + ChatColor.RED + " Your street number is out of range! (1-" + StreetCogsLoader.allStreetNPCs.size() + ")");
								}
							}
							else {
								player.sendMessage(Prefixes.Admin + ChatColor.RED + " Your street number isn't an integer");
							}
						}
						else {
							player.sendMessage(Prefixes.Admin + ChatColor.RED + " Wrong Syntax! /streets for help");
						}
					}
					else if (args[0].equalsIgnoreCase("invasion")) {
						if (!IsIntUtil.isInt(args[1])) {
							player.sendMessage(Prefixes.Admin + ChatColor.RED + " Your street number isn't an integer");
						}
						else if (!IsIntUtil.isInt(args[2])) {
							player.sendMessage(Prefixes.Admin + ChatColor.RED + " Your cog suit number isn't an integer!");
						}
						else if (!IsIntUtil.isInt(args[3])) {
							player.sendMessage(Prefixes.Admin + ChatColor.RED + " Your cog name number isn't an integer!");
						}
						else {
							int streetNum = IsIntUtil.getInt(args[1]);
							int cogSuitNum = IsIntUtil.getInt(args[2]);
							int cogNameNum = IsIntUtil.getInt(args[3]);
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
					else {
						player.sendMessage(Prefixes.Admin + ChatColor.RED + " Invalid Syntax");
					}
				}
			}
		}
		return false;
	}
}
