package me.WesBag.Toontown.Commands.Player;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.WesBag.Toontown.BattleCore.Cogs.HQs.BoardingGroups.BoardingGroup;
import me.WesBag.Toontown.Commands.Prefixes;
import net.md_5.bungee.api.ChatColor;

public class BoardingGroupCommand implements CommandExecutor {
	
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		//CHECK IF PLAYER IS IN BOARDING GROUP ELIGIBLE REGION FIRST, IF NOT, RETURN!
		
		
		if (sender instanceof Player) {
			Player player = (Player) sender;
			if (label.equalsIgnoreCase("boardinggroup")) {
				if (args.length == 0) {
					//HELP MENU
				}
				else if (args[0].equalsIgnoreCase("invite") && args.length == 2) {
					Player target = Bukkit.getPlayerExact(args[1]);
					if (target == null) {
						player.sendMessage(Prefixes.BoardingGroup + ChatColor.RED + "Invited player is offline");
					}
					else if (false){ //CHECK IF INVITED PLAYER IS IN ELIGIBLE REGION
						
					}
					else if (BoardingGroup.isPlayerInBoardingGroup(target.getUniqueId())) {
						player.sendMessage(Prefixes.BoardingGroup + ChatColor.RED + target.getName() + " is already in a group");
					}
					else if (BoardingGroup.isPlayerInBoardingGroup(player.getUniqueId())) {
						//if (BoardingGroup.getBoardingGroup(null))
					}
					else { //CREATE BG, THEN INVITE TARGET PLAYER
						target.sendMessage(Prefixes.BoardingGroup + ChatColor.DARK_AQUA + player.getName() + " has invited you to join their boarding group");
					}
				}
				else if (args[0].equalsIgnoreCase("edit")) {
					if (BoardingGroup.getBoardingGroup(player.getUniqueId()) == null) {
						player.sendMessage(Prefixes.BoardingGroup + ChatColor.RED + "Create your own boarding group to edit");
					}
				}
				else if (args[0].equalsIgnoreCase("close")) {
					if (BoardingGroup.getBoardingGroup(player.getUniqueId()) == null) {
						player.sendMessage(Prefixes.BoardingGroup + ChatColor.RED + "Create your own boarding group to close");
					}
				}
				else if (args[0].equalsIgnoreCase("remove") && args.length == 2) {
					if (BoardingGroup.getBoardingGroup(player.getUniqueId()) == null) {
						player.sendMessage(Prefixes.BoardingGroup + ChatColor.RED + "Create your own boarding group to remove");
					}
					
				}
				else if (args[0].equalsIgnoreCase("start")) {
					if (BoardingGroup.getBoardingGroup(player.getUniqueId()) == null) {
						player.sendMessage(Prefixes.BoardingGroup + ChatColor.RED + "Create your own boarding group to start");
					}
					
				}
				else {
					player.sendMessage(Prefixes.BoardingGroup + ChatColor.RED + "Invalid Syntax");
				}
			}
		}
		return false;
	}
}
