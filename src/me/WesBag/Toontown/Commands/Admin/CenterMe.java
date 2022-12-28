package me.WesBag.Toontown.Commands.Admin;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.WesBag.Toontown.Commands.Prefixes;
import net.md_5.bungee.api.ChatColor;

public class CenterMe implements CommandExecutor {
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender instanceof Player) {
			Player player = (Player) sender;
			if (label.equalsIgnoreCase("centerme")) {
				if (!player.hasPermission("ttc.admin")) {
					player.sendMessage(Prefixes.NoPerm);
				}
				else {
					Location l = player.getLocation();
					boolean negX = false;
					boolean negZ = false;
					
					double X = l.getX();
					if (X < 0) {
						negX = true;
						X*= -1;
					}
					X = Math.floor(X);
					X += 0.5;
					if (negX == true) X *= -1;
					l.setX(X);
					
					double Y = l.getY();
					Y = Math.round(Y);
					l.setY(Y);
					
					double Z = l.getZ();
					if (Z < 0) {
						negZ = true;
						Z*= -1;
					}
					Z= Math.floor(Z);
					Z += 0.5;
					if (negZ == true) Z*= -1;
					l.setZ(Z);
					
					float yaw = l.getYaw();
					if (yaw > -120 && yaw < -60) {
						yaw = -90;
					}
					else if ((yaw < 30 && yaw > 0)|| (yaw > -30 && yaw < 0)) {
						yaw = 0;
					}
					else if (yaw < 120 && yaw > 60) {
						yaw = 90;
					}
					else if ((yaw < -150 && yaw > -180)|| (yaw > 150 && yaw < 180)) {
						yaw = 180;
					}
					else {
						yaw = -1;
					}
					l.setYaw(yaw);
					
					l.setPitch(0f);
					
					if (yaw == -1) {
						player.sendMessage(Prefixes.Admin + ChatColor.RED + " Error: Too rough to round");
					}
					else {
						player.teleport(l);
						player.sendMessage(Prefixes.Admin + ChatColor.GREEN + " Successfully centered");
					}
				}
			}
		}
		return false;
	}

}
