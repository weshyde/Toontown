package me.WesBag.Toontown.Streets;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import net.citizensnpcs.api.event.CitizensEnableEvent;

public class StreetsController implements Listener {
	
	//public static List<Map<UUID, Integer>> allCogLocationMaps = new ArrayList<Map<UUID, Integer>>();
	//public static List<List<Location>> allPlaygroundStreets = new ArrayList<>();
	
	//public static Map<UUID, Integer> loopyLaneLocationMap = new HashMap<>();
	//public static Map<UUID, Integer> punchlinePlaceLocationMap = new HashMap<>();
	//public static Map<UUID, Integer> sillyStreetLocationMap = new HashMap<>();
	public StreetsController() {
		//Run First
		StreetCogsLoader.loadStreetVariables();
		
		//Run Second
		//allCogLocationMaps.add(loopyLaneLocationMap);
		//allCogLocationMaps.add(punchlinePlaceLocationMap);
		//allCogLocationMaps.add(sillyStreetLocationMap);
		
		//allPlaygroundStreets.add(StreetCogsLoader.loopyLaneLocations);
		//allPlaygroundStreets.add(StreetCogsLoader.punchlinePlaceLocations);
		//allPlaygroundStreets.add(StreetCogsLoader.sillyStreetLocations);
		
		//Run Third
		//StreetCogsLoader.loadAllStreetCogs();
	}
	
	//public final static Map<Location, Integer> ttcLoopyLane = new HashMap<>(); //BuildingLocation, Shop Keeper NPC ID
	
	@EventHandler
	public void onCitizensEnabled(CitizensEnableEvent e) {
		//StreetCogsLoader.loadAllStreetPercentages();
		//StreetCogsLoader.loadStreetVariables();
		//StreetCogsLoader.loadAllStreets();
	}
	/*
	@EventHandler COMMENTED 2/21/22 2:30pm FOR TESTING
	public void onCogFinishWaypoint(NavigationCompleteEvent e) {
		NPC npc = e.getNPC();
		if (!npc.isSpawned()) return;
		Map<UUID, Integer> map = null;
		int looped = 0;
		for (Map<UUID, Integer> entry : allCogLocationMaps) {
			if (entry.containsKey(npc.getUniqueId())) {
				map = entry;
				break;
			}
			looped++;
		}
		if (map == null) {
			Main.getInstance().getLogger().log(Level.SEVERE, "Cog Completed Navigation but isn't in a map!");
			return;
		}
		List<Location> streetLocations = allPlaygroundStreets.get(looped);
		int nextWaypointInt = map.get(npc.getUniqueId()) + 1;
		if (nextWaypointInt >= streetLocations.size()) {
			nextWaypointInt = 0;
		}
		map.put(npc.getUniqueId(), nextWaypointInt);
		Location l = streetLocations.get(nextWaypointInt);
		
		((LinearWaypointProvider) npc.getOrAddTrait(Waypoints.class).getCurrentProvider()).addWaypoint(new Waypoint(l));
	}
	*/
}
