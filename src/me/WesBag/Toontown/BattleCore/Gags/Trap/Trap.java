package me.WesBag.TTCore.BattleMenu.Gags.Trap;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import me.WesBag.TTCore.Main;
import me.WesBag.TTCore.BattleMenu.BattleMenu;
import me.WesBag.TTCore.BattleMenu.Gags.Gag;
import me.WesBag.TTCore.Files.BattleData;

public class Trap {
	
	public static Gag useTrapGag(UUID playerUUID, Main main, String gag, int track, int numInTrack) {
		boolean hitAll = false;
		//boolean missed = false;
		//int minDmg = getMinDmg(gag);
		if (numInTrack == 7)
			hitAll = true;
		int maxDmg = getMaxDmg(gag.replace("-", ""));
		//int acc = getAcc(gag.replace("-", ""));
		Sound snd = getSound(gag.replace("-", ""));
		Gag tempGag = new Gag(playerUUID, gag, maxDmg, 100, track, hitAll, numInTrack, snd);
		return tempGag;
	}

	public static int getMinDmg(String gag) {
		Class<?> gagc;
		try {
			gagc = Class.forName("me.WesBag.TTCore.BattleMenu.Gags.Trap." + gag + ".Gag");
			return gagc.getField("minDamage").getInt(gagc.getDeclaredConstructor().newInstance());
		} catch (ClassNotFoundException | IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException | InvocationTargetException | InstantiationException | NoSuchMethodException e) {
			e.printStackTrace();
		return -1;
		}
	}
	
	public static int getMaxDmg(String gag) {
		Class<?> gagc;
		try {
			gagc = Class.forName("me.WesBag.TTCore.BattleMenu.Gags.Trap." + gag + ".Gag");
			return gagc.getField("maxDamage").getInt(gagc.getDeclaredConstructor().newInstance());
		} catch (ClassNotFoundException | IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException | InvocationTargetException | InstantiationException | NoSuchMethodException e) {
			e.printStackTrace();
		return -1;
		}
	}
	
	public static Sound getSound(String gag) {
		Class<?> gagc;
		try {
			gagc = Class.forName("me.WesBag.TTCore.BattleMenu.Gags.Trap." + gag + ".Gag");
			return (Sound) gagc.getField("snd").get(gagc.getDeclaredConstructor().newInstance());
		} catch (ClassNotFoundException | IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException | InvocationTargetException | InstantiationException | NoSuchMethodException e) {
			e.printStackTrace();
		}
		return null;
	}
}
