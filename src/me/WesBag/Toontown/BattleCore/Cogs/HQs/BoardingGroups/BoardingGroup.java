package me.WesBag.Toontown.BattleCore.Cogs.HQs.BoardingGroups;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.sk89q.worldguard.protection.regions.ProtectedRegion;

import java.util.Map.Entry;

public class BoardingGroup {
	
	public static Map<UUID, BoardingGroup> allBoardingGroups = new HashMap<>();
	//public static List<UUID> allPlayersInBoardingGroups = new ArrayList<>();
	
	private List<UUID> bgPlayers = new ArrayList<>();
	private String bgLoc;
	
	
	public BoardingGroup(UUID uuid) {
		allBoardingGroups.put(uuid, this);
		bgPlayers.add(uuid);
	}
	
	public void addPlayer(UUID pUUID) {
		bgPlayers.add(pUUID);
	}
	
	 public void removePlayer(UUID pUUID) {
		 if (bgPlayers.contains(pUUID)) {
			 bgPlayers.remove(pUUID);
		 }
	 }
	 
	 public int getGroupSize() {
		 return bgPlayers.size();
	 }
	 
	 public static BoardingGroup getBoardingGroup(UUID leaderUUID) {
		 if (allBoardingGroups.containsKey(leaderUUID)) {
			 return allBoardingGroups.get(leaderUUID);
		 }
		 else {
			 return null;
		 }
	 }
	 
	 public static boolean isPlayerInBoardingGroup(UUID pUUID) {
		 boolean inBG = false;
		 for (BoardingGroup bg : allBoardingGroups.values()) {
			 if (bg.bgPlayers.contains(pUUID)) {
				 inBG = true;
				 break;
			 }
		 }
		 
		 return inBG;
	 }
}
