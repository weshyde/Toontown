package me.WesBag.Toontown.BattleCore.Gags.Drop;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import me.WesBag.Toontown.Main;
import me.WesBag.Toontown.BattleCore.Gags.Gag;
import me.WesBag.Toontown.Files.BattleData;

public class Drop {
	
	
	
	/*
	public static void onUse(UUID player, Main main, String gag, int track) {
		int minDmg = getMinDmg(gag);
		int maxDmg = getMaxDmg(gag);
		int acc = getAcc(gag);
		List<UUID> npcs = new ArrayList<UUID>();
		Player p = Bukkit.getPlayer(player);
		BattleData battleData = main.battle.getBattleData(p.getLocation());
		npcs = battleData.getBattleNPCList();
		boolean missed = battleData.calcPlayerHit(acc, 0, maxDmg, acc);
		//npcs.add(main.battle.getBattleNPCList(player).get(0));
		//for (UUID uuid:npcs) {
		//	npcs
		//}
		//npcs.add(BattleData.getBattleNPCList());
		//main.battle.playerAttack(player, npcs, maxDmg, gag, missed);
		
	}
	*/
	public static Gag useDropGag(UUID playerUUID, Main main, String gag, int track, int numInTrack) {
		boolean hitAll = false;
		if (numInTrack == 7)
			hitAll = true;
		int maxDmg = getMaxDmg(gag.replace("-", ""));
		int acc = getAcc(gag.replace("-", ""));
		Sound snd = getSound(gag.replace("-", ""));
		Gag tempGag = new Gag(playerUUID, gag, maxDmg, acc, track, hitAll, numInTrack, snd);
		return tempGag;
	}
	
	
	
	public static int getMinDmg(String gag) {
		Class<?> gagc;
		try {
			gagc = Class.forName("me.WesBag.Toontown.BattleCore.Gags.Drop." + gag + ".Gag");
			return gagc.getField("minDamage").getInt(gagc.getDeclaredConstructor().newInstance());
		} catch (ClassNotFoundException | IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException | InvocationTargetException | InstantiationException | NoSuchMethodException e) {
			e.printStackTrace();
		return -1;
		}
	}
	
	public static int getMaxDmg(String gag) {
		Class<?> gagc;
		try {
			gagc = Class.forName("me.WesBag.Toontown.BattleCore.Gags.Drop." + gag + ".Gag");
			return gagc.getField("maxDamage").getInt(gagc.getDeclaredConstructor().newInstance());
		} catch (ClassNotFoundException | IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException | InvocationTargetException | InstantiationException | NoSuchMethodException e) {
			e.printStackTrace();
		return -1;
		}
	}
	
	public static int getAcc(String gag) {
		Class<?> gagc;
		try {
			gagc = Class.forName("me.WesBag.Toontown.BattleCore.Gags.Drop." + gag + ".Gag");
			return gagc.getField("acc").getInt(gagc.getDeclaredConstructor().newInstance());
		} catch (ClassNotFoundException | IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException | InvocationTargetException | InstantiationException | NoSuchMethodException e) {
			e.printStackTrace();
		}
		return -1;
	}
	
	public static Sound getSound(String gag) {
		Class<?> gagc;
		try {
			gagc = Class.forName("me.WesBag.Toontown.BattleCore.Gags.Drop." + gag + ".Gag");
			return (Sound) gagc.getField("snd").get(gagc.getDeclaredConstructor().newInstance());
		} catch (ClassNotFoundException | IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException | InvocationTargetException | InstantiationException | NoSuchMethodException e) {
			e.printStackTrace();
		}
		return null;
	}
}
