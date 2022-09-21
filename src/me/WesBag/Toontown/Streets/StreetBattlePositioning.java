package me.WesBag.Toontown.Streets;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

public class StreetBattlePositioning {
	
	// Integer XYZZ
	// X: Playground
	// Y: Street
	// ZZ: Loc # on street
	//X -> TTC = 1, DD = 2, etc
	public static Map<Integer, Boolean> availableStreetLocs = new HashMap<>();
	public static Map<Integer, List<Double>> streetLocs = new HashMap<>();
	
	public static void loadAllStreetBattleLocations() {
		
		//---- LOOPY LANE ----
		List<Double> p1 = new ArrayList<Double>();
		//p1.add(null)
		
		
		//---- PUNCHLINE PLACE ----
		
		
		
		//---- SILLY STREET ----
	}
	
	public static Location intsToLoc(int streetLocID) {
		List<Double> tempList = streetLocs.get(streetLocID);
		double yaw = tempList.get(3);
		double pitch = tempList.get(4);
		
		World w = Bukkit.getWorld("world");
		Location l = new Location(w, tempList.get(0), tempList.get(1), tempList.get(2), (float)yaw, (float)pitch);
		return l;
	}
	
	public static Location intsToLoc(List<Double> ints) {
		double yaw = ints.get(3);
		double pitch = ints.get(4);
		
		World w = Bukkit.getWorld("world");
		Location l = new Location(w, ints.get(0), ints.get(1), ints.get(2), (float)yaw, (float)pitch);
		return l;
	}
	
	public static Location anchorStreetBattle(int regionLocID, Location curLoc) {
		List<List<Double>> tempList = new ArrayList<>();
		
		while (streetLocs.containsKey(regionLocID) && (availableStreetLocs.get(regionLocID) == false)) { //Formulates list of available spots on the same street
			tempList.add(streetLocs.get(regionLocID));
			regionLocID++;
		}
		
		Location anchoredBattle = null;
		
		for (int i = 0; i < tempList.size(); i++) { //Loop to find the nearest place to anchor the street battle
			double distance = curLoc.distance(intsToLoc(tempList.get(i)));
			if ( (distance < 15) && (anchoredBattle != null) && (distance < anchoredBattle.distance(curLoc))) {
				anchoredBattle = intsToLoc(tempList.get(i));
			}
		}
		
		
		return anchoredBattle;
	}
}
