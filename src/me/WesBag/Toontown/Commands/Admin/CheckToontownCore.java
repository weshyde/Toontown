package me.WesBag.Toontown.Commands.Admin;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.WesBag.Toontown.Main;
import me.WesBag.Toontown.BattleCore.Toons.ToonsController;
import me.WesBag.Toontown.Commands.Prefixes;
import net.md_5.bungee.api.ChatColor;

public class CheckToontownCore implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender instanceof Player) {
			Player player = (Player) sender;
			if (label.equalsIgnoreCase("checktoontowncore")) {
				if (!player.hasPermission("ttc.owner")) {
					player.sendMessage(Prefixes.NoPerm);
				}
				
				else {
					//Makes sure all temp saved toons are actually online
					boolean failed = false;
					
					//Test 1
					for (UUID uuid : ToonsController.allToons.keySet()) {
						if (Bukkit.getPlayer(uuid) == null) {
							player.sendMessage(Prefixes.Admin + ChatColor.RED + " Offline player in saved toons! Fixing now...");
							player.sendMessage(Prefixes.Admin + ChatColor.RED + " UUID: " + uuid.toString());
							failed = true;
							ToonsController.allToons.remove(uuid);
						}
					}
					if (!failed)
						player.sendMessage(Prefixes.Admin + ChatColor.GREEN + " [Test 1] Successful!");
					
					failed = false;
					//Test 2
					
				}
			}
		}
		
		return false;
	}
	
	

}
