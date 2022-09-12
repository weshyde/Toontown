package me.WesBag.TTCore.BattleMenu.Gags;

import java.util.UUID;

import org.bukkit.Sound;

public class Gag {
	UUID gagOwnerUUID;
	String gagName;
	int damage;
	int rounds;
	int heal;
	int acc;
	int track;
	int numInTrack;
	int exp;
	Sound gagSound = Sound.AMBIENT_UNDERWATER_ENTER;
	boolean isLure = false;
	boolean isToonup = false;
	boolean hitAll;
	
	public Gag(UUID iOwnerUUID, String iGagName, int iDamage, int iAcc, int iTrack, boolean iHitAll, int iNumInTrack, Sound iSound) { //Trap, Throw, Sound, Squirt, Drop
		gagOwnerUUID = iOwnerUUID;
		gagName = iGagName;
		rounds = 0;
		heal = 0;
		damage = iDamage;
		acc = iAcc;
		track = iTrack;
		numInTrack = iNumInTrack;
		gagSound = iSound;
		hitAll = iHitAll;
		exp = numInTrack;
	}
	
	public Gag(UUID iOwnerUUID, String iGagName, int iRounds, int iAcc, int iTrack, boolean iHitAll, int iNumInTrack, Sound iSound, boolean iIsLure) { //Lure
		gagOwnerUUID = iOwnerUUID;
		gagName = iGagName;
		damage = 0;
		heal = 0;
		rounds = iRounds;
		acc = iAcc;
		track = iTrack;
		numInTrack = iNumInTrack;
		gagSound = iSound;
		hitAll = iHitAll;
		isLure = true;
		exp = numInTrack;
	}
	
	public Gag(UUID iOwnerUUID, String iGagName, int iDamage, int iTrack, boolean iHitAll, int iNumInTrack) { //Trap
		gagOwnerUUID = iOwnerUUID;
		gagName = iGagName;
		damage = iDamage;
		acc = 100;
		rounds = 0;
		heal = 0;
		track = iTrack;
		numInTrack = iNumInTrack;
		hitAll = iHitAll;
		exp = numInTrack;
	}
	
	public Gag(UUID iOwnerUUID, String iGagName, int iHeal, int iAcc, int iTrack, boolean iHitAll, boolean iIsToonup, int iNumInTrack, Sound iSound) { //Toonup
		gagOwnerUUID = iOwnerUUID;
		gagName = iGagName;
		heal = iHeal;
		damage = 0;
		rounds = 0;
		heal = iHeal;
		acc = iAcc;
		track = iTrack;
		numInTrack = iNumInTrack;
		gagSound = iSound;
		hitAll = iHitAll;
		isToonup = true;
		exp = numInTrack;
	}
	
	public UUID getOwner() {
		return gagOwnerUUID;
	}
	
	public String getGagName() {
		return gagName;
	}
	
	public int getDamage() {
		return damage;
	}
	
	public int getAcc() {
		return acc;
	}
	
	public int getTrack() {
		return track;
	}
	
	public int getNumInTrack() {
		return numInTrack;
	}
	
	public boolean getHitAll() {
		return hitAll;
	}
	
	public int getExp() {
		return exp;
	}
	
	public Sound getSound() {
		return gagSound;
	}
	
	public int getHeal() {
		return heal;
	}
	
	public boolean getIsLure() {
		return isLure;
	}
	
	public boolean getIsToounp() {
		return isToonup;
	}
	
	public int getRounds() {
		return rounds;
	}
}
