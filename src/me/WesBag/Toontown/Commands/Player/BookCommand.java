package me.WesBag.TTCore.Commands.PlayerCommands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.WesBag.TTCore.BattleMenu.Toons.Toon;
import me.WesBag.TTCore.BattleMenu.Toons.ToonsController;
import me.WesBag.TTCore.ShtickerBook.ShtickerBook;

public class BookCommand implements CommandExecutor {
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender instanceof Player) {
			Player player = (Player) sender;
			if (label.equalsIgnoreCase("book")) {
				//Toon toon = ToonsController.getToon(player.getUniqueId());
				ShtickerBook.openBook(ToonsController.getToon(player.getUniqueId()));
			}
		}
		return false;
	}
}
