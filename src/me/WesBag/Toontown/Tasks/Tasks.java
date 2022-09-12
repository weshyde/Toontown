package me.WesBag.Toontown.Tasks;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import net.citizensnpcs.api.event.NPCRightClickEvent;
import net.citizensnpcs.api.npc.NPC;

public class Tasks implements Listener {
	@EventHandler
	public void onRightClick(NPCRightClickEvent e) {
		NPC npc = e.getNPC();
		Player p = e.getClicker();
		
		if (npc.getName().contains("HQ")) {
			p.sendMessage("You clicked a Toon HQ Officer!");
		}
	}
}
