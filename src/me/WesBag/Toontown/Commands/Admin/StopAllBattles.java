package me.WesBag.TTCore.Commands.AdminCommands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.WesBag.TTCore.BattleMenu.BattleMenu;
import me.WesBag.TTCore.Commands.Prefixes;
import me.WesBag.TTCore.Files.BattleData;
import net.md_5.bungee.api.ChatColor;

public class StopAllBattles implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender instanceof Player) {
			Player player = (Player) sender;
			if (label.equalsIgnoreCase("stopallbattles")) {
				if (player.hasPermission("ttc.owner")) {
					for (BattleData battleData : BattleMenu.allBattles) {
						battleData.sendAllMessage(ChatColor.BLUE + "[Battle] " + ChatColor.DARK_RED + "Sorry, all battles have been cancelled temporarily");
						battleData.deleteAll();
						battleData = null;
					}
					BattleMenu.allBattles.clear();
					sender.sendMessage(Prefixes.Admin + ChatColor.GREEN + " All battles have been stopped!");
					sender.sendMessage("all battles amount: " + BattleMenu.allBattles.size());
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
