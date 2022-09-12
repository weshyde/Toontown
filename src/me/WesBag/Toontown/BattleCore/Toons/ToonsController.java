package me.WesBag.Toontown.BattleCore.Toons;

import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;
import java.util.UUID;

public class ToonsController {
	public static HashMap<UUID, Toon> allToons = new HashMap<>();
	
	public static Toon getToon(UUID playerUUID) {
		if (allToons.containsKey(playerUUID))
			return allToons.get(playerUUID);
		return null;
	}
	
	public static void unloadAll() {
		for (UUID pUUID : allToons.keySet()) {
			getToon(pUUID).unloadToon();
		}
	}
}
