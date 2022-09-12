package me.WesBag.Toontown.BattleCore.Cogs;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import me.WesBag.Toontown.Main;
import me.WesBag.Toontown.BattleCore.Cogs.Bossbot.Bossbot;
import me.WesBag.Toontown.BattleCore.Cogs.Cashbot.Cashbot;
import me.WesBag.Toontown.BattleCore.Cogs.Lawbot.Lawbot;
import me.WesBag.Toontown.BattleCore.Cogs.Sellbot.Sellbot;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;

public class CogsController {
	Main main;
	HashMap<UUID, String> cogsType;
	Bossbot bossbot;
	Lawbot lawbot;
	Cashbot cashbot;
	Sellbot sellbot;
	
	public static final String[] cogSuits = {"Bossbot", "Lawbot", "Cashbot", "Sellbot"};
	public static final String[] bbCogNames = {"Flunky", "Pencil Pusher", "Yesman", "Micromanager", "Downsizer", "Head Hunter", "Corporate Raider", "The Big Cheese"};
	public static final String[] lbCogNames = {"Bottom Feeder", "Bloodsucker", "Double Talker", "Ambulance Chaser", "Back Stabber", "Spin Doctor", "Legal Eagle", "Big Wig"};
	public static final String[] cbCogNames = {"Short Change", "Penny Pincher", "Tightwad", "Bean Counter", "Number Cruncher", "Money Bags", "Loan Shark", "Robber Baron"};
	public static final String[] sbCogNames = {"Cold Caller", "Telemarketer", "Name Dropper", "Glad Handler", "Mover And Shaker", "Two Face", "The Mingler", "Mr Hollywood"};
	public static final String[] cogNames[] = {bbCogNames, lbCogNames, cbCogNames, sbCogNames};
	
	public CogsController(Main inputMain) {
		main = inputMain;
		cogsType = new HashMap<>();
		bossbot = new Bossbot(main);
		lawbot = new Lawbot(main);
		cashbot = new Cashbot(main);
		sellbot = new Sellbot(main);
	}
	
	public void startAllCogs() {
		//ONCE STREETS ARE DETECTABLE (WORLD GUARD REGIONS) SPAWN A RANDOM FEW OF APPRIOATE COGS ON EACH STREET
		NPC npc = CitizensAPI.getNPCRegistry().createNPC(EntityType.PLAYER, "Flunky Level 1");
		Location l = new Location(Bukkit.getWorlds().get(0), -62.5, 63.0, -71.5);
		//l.setX(0);
		npc.spawn(l);
		//npc.data().set(NPC.PLAYER_SKIN_UUID_METADATA, "1144");
	}
	
	public void stopAllCogs() {
		//public static Iterable<NPCRegistry> CitizensAPI.getNPCRegistries();
		Iterable<NPC> tempNPCS = CitizensAPI.getNPCRegistry().sorted();
		for (NPC tempNPC : tempNPCS) {
			if (tempNPC.getName().contains("Level")) {
				//tempNPC.destroy();
				tempNPC.despawn();
			}
		}
		
	}
	
	/*
	public void loadCogProfile(NPC npc, YamlConfiguration yml) {
		LivingEntity le = (LivingEntity) npc.getEntity();
		int level = 1; //Get level from somewhere
	}
	
	public void loadCogProfile(NPC npc, YamlConfiguration) 
	*/
	
	/*  https://www.spigotmc.org/threads/holographicdisplays-with-moving-npcs.419183/
	        PacketPlayOutMount packet = new PacketPlayOutMount(((CraftPlayer)npc).getHandle());
        for(Player player : npc.getWorld().getPlayers())
        {
            ((CraftPlayer)player).getHandle().playerConnection.sendPacket(packet);
        }
   */
}
