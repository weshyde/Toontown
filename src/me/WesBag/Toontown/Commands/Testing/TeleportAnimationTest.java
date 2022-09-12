package me.WesBag.Toontown.Commands.Testing;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.WesBag.Toontown.Main;
import me.WesBag.Toontown.Animations.Toon.TeleportAnimation;
import me.WesBag.Toontown.Commands.Prefixes;

public class TeleportAnimationTest implements CommandExecutor {
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender instanceof Player) {
			Player player = (Player) sender;
			if (label.equalsIgnoreCase("teleporttest")) {
				if (!player.hasPermission("ttc.admin"))
					player.sendMessage(Prefixes.NoPerm);
				
				TeleportAnimation tAnim = new TeleportAnimation(player, player.getLocation().add(0, 5, 0));
				tAnim.runTaskTimer(Main.getInstance(), 0, 7);
			}
		}
		return false;
	}

}
