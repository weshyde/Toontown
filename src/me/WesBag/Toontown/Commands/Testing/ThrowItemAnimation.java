package me.WesBag.Toontown.Commands.Testing;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.WesBag.Toontown.Commands.Prefixes;
import me.WesBag.Toontown.Commands.Admin.IsIntUtil;
import net.md_5.bungee.api.ChatColor;

public class ThrowItemAnimation implements CommandExecutor {
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender instanceof Player) {
			Player player = (Player) sender;
			if (label.equalsIgnoreCase("throwitem")) {
				if (!player.hasPermission("ttc.admin"))
					player.sendMessage(Prefixes.NoPerm);
				else if (args.length != 1)
					player.sendMessage(Prefixes.Admin + ChatColor.RED + " Wrong Syntax! /throwitem <CustomModelData>");
				else if (!IsIntUtil.isInt(args[0]))
					player.sendMessage(Prefixes.Admin + ChatColor.RED + " Your custom model data must be an integer!");
				else {
					int customDataInt = IsIntUtil.getInt(args[0]);
					if (customDataInt < 1 || customDataInt > 10000)
						player.sendMessage(Prefixes.Admin + ChatColor.RED + " Your custom model data integer is out of range! (1-10000)");
					else {
						Snowball projectile = player.launchProjectile(Snowball.class);
						ItemStack is = new ItemStack(Material.GOLDEN_SWORD);
						//is.setItemMeta((ItemMeta) im)
						//MaterialData md;
						//md.setData(0);
						//is.setData(1000);
						//CustomModelData;
						ItemMeta im = is.getItemMeta();
						im.setCustomModelData(customDataInt);
						is.setItemMeta(im);
						//projectile.setVelocity(0.1);
						projectile.setItem(is);
						//projectile.setVelocity(projectile.getVelocity().divide(4));
						//player.playSound(player.getLocation(), Sound.BLOCK_COPPER_PLACE, 1f, 1f);
						player.sendMessage(Prefixes.Admin + ChatColor.GREEN + " You successfully launched item with CMD: " + customDataInt + "!");
						//projectile.setprojectile.getVelocity()
					}
				}
				
			}
			
		}
		return false;
	}

}
