package me.WesBag.Toontown.BattleCore.Gags.Toonup;

import java.lang.reflect.InvocationTargetException;
import java.util.UUID;

import org.bukkit.Sound;

import me.WesBag.Toontown.Main;
import me.WesBag.Toontown.BattleCore.Gags.Gag;

public class Toonup {
	
	public static Gag useToonupGag(UUID playerUUID, Main main, String gag, int track, int numInTrack) {
		boolean hitAll = false;
		//boolean missed = false;
		//int minDmg = getMinDmg(gag);
		if (numInTrack == 7 || numInTrack == 2 || numInTrack == 4 || numInTrack == 6)
			hitAll = true;
		//int maxDmg = getMaxDmg(gag.replace("-", ""));
		//int rounds = getRounds(gag.replace("-", ""));
		int heal = getMaxHeal(gag.replace("-", ""));
		int acc = getAcc(gag.replace("-", ""));
		Sound snd = getSound(gag.replace("-", ""));
		Gag tempGag = new Gag(playerUUID, gag, heal, acc, track, hitAll, true, numInTrack, snd);
		return tempGag;
	}
	
	
	
	public static int getMinHeal(String gag) {
		Class<?> gagc;
		try {
			gagc = Class.forName("me.WesBag.Toontown.BattleCore.Gags.Toonup." + gag + ".Gag");
			return gagc.getField("minHeal").getInt(gagc.getDeclaredConstructor().newInstance());
		} catch (ClassNotFoundException | IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException | InvocationTargetException | InstantiationException | NoSuchMethodException e) {
			e.printStackTrace();
		return -1;
		}
	}
	
	public static int getMaxHeal(String gag) {
		Class<?> gagc;
		try {
			gagc = Class.forName("me.WesBag.Toontown.BattleCore.Gags.Toonup." + gag + ".Gag");
			return gagc.getField("maxHeal").getInt(gagc.getDeclaredConstructor().newInstance());
		} catch (ClassNotFoundException | IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException | InvocationTargetException | InstantiationException | NoSuchMethodException e) {
			e.printStackTrace();
		return -1;
		}
	}
	
	public static int getAcc(String gag) {
		Class<?> gagc;
		try {
			gagc = Class.forName("me.WesBag.Toontown.BattleCore.Gags.Toonup." + gag + ".Gag");
			return gagc.getField("acc").getInt(gagc.getDeclaredConstructor().newInstance());
		} catch (ClassNotFoundException | IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException | InvocationTargetException | InstantiationException | NoSuchMethodException e) {
			e.printStackTrace();
		return -1;
		}
	}
	
	public static Sound getSound(String gag) {
		Class<?> gagc;
		try {
			gagc = Class.forName("me.WesBag.Toontown.BattleCore.Gags.Toonup." + gag + ".Gag");
			return (Sound) gagc.getField("snd").get(gagc.getDeclaredConstructor().newInstance());
		} catch (ClassNotFoundException | IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException | InvocationTargetException | InstantiationException | NoSuchMethodException e) {
			e.printStackTrace();
		}
		return null;
	}
}
