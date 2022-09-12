package me.WesBag.Toontown.Files;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Level;

import me.WesBag.Toontown.Main;

public class CogTypeTracker {
	public static HashMap<UUID, EnumMap<CogType, Integer>> cogsDefeatedByPlayer = new HashMap<>();
	
	public CogTypeTracker() {
		//CogTypeTracker.pData = data;
	}
	
	
	public void saveCogTracking() {
		for (Map.Entry<UUID, EnumMap<CogType, Integer>> entry : cogsDefeatedByPlayer.entrySet()) {
			PlayerData pData = PlayerDataController.getPlayerData(entry.getKey());
			if (pData == null) {
				Main.getInstance().getLogger().log(Level.WARNING, "Error 1: Failed to save cog tracking data for player UUID: " + entry.getKey());
				continue;
			}
			if (pData.getPlayerData().contains("cogs.gallery")) {
				EnumMap<CogType, Integer> tempMap = entry.getValue();
				int index = 0;
				for (CogType cogType2 : CogType.values()) {
					
				}
				for (CogType cogType : tempMap.keySet()) {
					int cogAmount = tempMap.get(cogType);
					if (pData.getPlayerData().contains("cogs.gallery." + cogType)) {
						cogAmount += (int) pData.getPlayerData().get("cogs.gallery." + cogType);
					}
					pData.getPlayerData().set("cogs.gallery." + cogType, cogAmount);
				}
			}
			else {
				Main.getInstance().getLogger().log(Level.WARNING, "Error 2: Failed to cog tracking data for player UUID: " + entry.getKey());
			}
		}
	}
}
