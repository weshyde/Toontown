package me.WesBag.TTCore.Commands.AdminCommands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.WesBag.TTCore.BattleMenu.Toons.ToonsController;
import me.WesBag.TTCore.Commands.Prefixes;
import me.WesBag.TTCore.Skins.SkinGrabber;
import net.md_5.bungee.api.ChatColor;

public class ChangeSkin implements CommandExecutor {
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender instanceof Player) {
			Player player = (Player) sender;
			if (label.equalsIgnoreCase("changeskin")) {
				if (!player.hasPermission("ttc.admin")) {
					player.sendMessage(Prefixes.NoPerm);
				}
				else if (args.length != 2) {
					player.sendMessage(Prefixes.Admin + ChatColor.RED + " Invalid Syntax! /changeskin <Player> <SkinNum>");
				}
				else {
					if (Bukkit.getPlayerExact(args[0]) == null) {
						player.sendMessage(Prefixes.Admin + ChatColor.RED + "Your target is offline");
					}
					else if (!IsIntUtil.isInt(args[1])) {
						player.sendMessage(Prefixes.Admin + ChatColor.RED + "Your skin number isn't an integer");
					}
					else {
						int skinNum = IsIntUtil.getInt(args[1]);
						if (skinNum < 0 || skinNum > 10) {
							player.sendMessage("Your skin number is out of range (1-10)");
						}
						else { //Final
							Player target = Bukkit.getPlayerExact(args[0]);
							ToonsController.getToon(target.getUniqueId()).setSkinNum(skinNum);
							SkinGrabber.changeSkin(target, skinNum);
							player.sendMessage(Prefixes.Admin + ChatColor.GREEN + "Successfully set " + target.getName() + "'s skin to number " + skinNum);
							target.sendMessage(Prefixes.Admin + ChatColor.GREEN + "Your skin has been set to number " + skinNum);
						}
					}
				}
			}
		}
		return false;
	}
}
