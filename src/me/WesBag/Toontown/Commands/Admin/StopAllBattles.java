package me.WesBag.Toontown.Commands.Admin;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.WesBag.Toontown.BattleCore.BattleCore;
import me.WesBag.Toontown.Commands.Prefixes;
import me.WesBag.Toontown.Files.BattleData;
import net.md_5.bungee.api.ChatColor;

public class StopAllBattles implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender instanceof Player) {
			Player player = (Player) sender;
			if (label.equalsIgnoreCase("stopallbattles")) {
				if (player.hasPermission("ttc.owner")) {
					for (BattleData battleData : BattleCore.allBattles) {
						battleData.sendAllMessage(ChatColor.BLUE + "[Battle] " + ChatColor.DARK_RED + "Sorry, all battles have been cancelled temporarily");
						battleData.deleteAll();
						battleData = null;
					}
					BattleCore.allBattles.clear();
					sender.sendMessage(Prefixes.Admin + ChatColor.GREEN + " All battles have been stopped!");
					sender.sendMessage("all battles amount: " + BattleCore.allBattles.size());
				}
				else {
					player.sendMessage(Prefixes.NoPerm);
				}
			}
		}
		
		else {
			sender.sendMessage("Only players can use this command!");
		}
		return false;
	}

	
	
}
