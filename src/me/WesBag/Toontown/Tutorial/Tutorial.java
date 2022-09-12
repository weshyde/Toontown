
package me.WesBag.Toontown.Tutorial;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import dev.jcsoftware.jscoreboards.JPerPlayerScoreboard;
import me.WesBag.Toontown.Main;
import me.WesBag.Toontown.BattleCore.Toons.Toon;
import me.WesBag.Toontown.BattleCore.Toons.ToonsController;
import me.WesBag.Toontown.Files.PlayerData;
import me.WesBag.Toontown.Skins.SkinGrabber;
import net.md_5.bungee.api.ChatColor;

public class Tutorial implements Listener {

	public static List<Location> availableStartLocations;
	public static Inventory speciesPage;
	public static Main main;
	
	private JPerPlayerScoreboard scoreboard;
	private String species = "<None>";
	private String name = "<None>";
	
	public Tutorial(Main main) {
		Tutorial.main = main;
	}
	
	public Tutorial(Player p) {
		scoreboard = new JPerPlayerScoreboard(
			(player) -> {
				return player.getDisplayName() + "'s Toon";
			},
			(player) -> {
				return Arrays.asList(
					"&bSpecies: &d" + species,
					"&bName: &d" + name
				);
			}
		);
		scoreboard.addPlayer(p);
		scoreboard.updateScoreboard();
	}
	
	public void loadStartLocations() {
		availableStartLocations = new ArrayList<>();
		
		World world = Bukkit.getWorld("world");
		
		Location l1 = new Location(world, 1, 1, 1);
		Location l2 = new Location(world, 1, 1, 1);
		Location l3 = new Location(world, 1, 1, 1);
		
		availableStartLocations.add(l1);
		availableStartLocations.add(l2);
		availableStartLocations.add(l3);
	}
	
	public void loadCharacterGUI() {
		speciesPage = Bukkit.createInventory(null, 54, ChatColor.AQUA + "Choose your species");
	}
	
	@EventHandler
	public void onPlayerClickCharacterGUI(final InventoryClickEvent e) {
		if (e.getInventory() != speciesPage) return;
		if (!(e.getWhoClicked() instanceof Player)) return;
		
		Player p = (Player) e.getWhoClicked();
		Toon toon = ToonsController.getToon(e.getWhoClicked().getUniqueId());
		final ItemStack clickedItem = e.getCurrentItem();
		switch(clickedItem.getItemMeta().getDisplayName()) {
		case"Cat":
			toon.setSkinNum(0);
			SkinGrabber.changeSkin(p, 0);
			break;
		case"Dog":
			toon.setSkinNum(1);
			SkinGrabber.changeSkin(p, 1);
			break;
		case"Duck":
			toon.setSkinNum(2);
			SkinGrabber.changeSkin(p, 2);
			break;
		case"Mouse":
			toon.setSkinNum(3);
			SkinGrabber.changeSkin(p, 3);
			break;
		case"Pig":
			toon.setSkinNum(4);
			SkinGrabber.changeSkin(p, 4);
			break;
		case"Rabbit":
			toon.setSkinNum(5);
			SkinGrabber.changeSkin(p, 5);
			break;
		}
		
	}
	
	
	@EventHandler
	public void onPlayerFirstJoinEvent(PlayerJoinEvent e) {
		Player p = (Player) e.getPlayer();
		if (!p.hasPlayedBefore()) { //Change to not p.hasPlayedBefore eventually
			PlayerData pData = new PlayerData(main, p.getUniqueId());
			
			if (!pData.getPlayerData().contains("laff")) {
				pData.getPlayerData().set("laff", 15);
				p.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(30);
				p.setHealth(30.0);
			}
			if (!pData.getPlayerData().contains("gags" + ".max")) {
				pData.getPlayerData().set("gags" + ".max", 100);
			}
			if (!pData.getPlayerData().contains("gags" + ".current")) {
				pData.getPlayerData().set("gags" + ".current", 2);
			}
			if (!pData.getPlayerData().contains("gags" + ".throw")) {
				pData.getPlayerData().set("gags" + ".throw", 1);
			}
			if (!pData.getPlayerData().contains("gags" + ".throw-exp")) {
				pData.getPlayerData().set("gags" + ".throw-exp", 0);
			}
			if (!pData.getPlayerData().contains("gags" + ".throw-amount")) {
				pData.getPlayerData().set("gags" + ".throw-amount", "01000000000000");
			}
			if (!pData.getPlayerData().contains("gags" + ".squirt")) {
				pData.getPlayerData().set("gags" + ".squirt", 1);
			}
			if (!pData.getPlayerData().contains("gags" + ".squirt-exp")) {
				pData.getPlayerData().set("gags" + ".squirt-exp", 0);
			}
			if (!pData.getPlayerData().contains("gags" + ".squirt-amount")) {
				pData.getPlayerData().set("gags" + ".squirt-amount", "01000000000000");
			}
			if (!pData.getPlayerData().contains("tasks.max")) {
				pData.getPlayerData().set("tasks.max", 1);
			}
			if (!pData.getPlayerData().contains("tasks.playground")) {
				pData.getPlayerData().set("tasks.playground", 0);
			}
			
			pData.savePlayerData();
				//if (!this.data2.getPlayerData().contains("))
			}
			World w = p.getWorld();
			Location l = new Location(w, 1354.5, 57, -152.5);
			p.teleport(l);
			
			//p.sendMessage(ChatColor.LIGHT_PURPLE + "Tutorial Tom" + ChatColor.GRAY + " >> " + ChatColor.WHITE + "Welcome to Toontown! Come over and see me when you're ready and I'll show you how to play!");
		//}
	}

/*	
	@EventHandler
	public void onRightClick(NPCRightClickEvent e) {
		NPC npc = e.getNPC();
		Player p = e.getClicker();
		if (npc.getName().contains("Tutorial Tom")) {
			p.sendMessage("You right clicked a cog!");
			//BattleMenuGUI();
		}
	}
*/
}

