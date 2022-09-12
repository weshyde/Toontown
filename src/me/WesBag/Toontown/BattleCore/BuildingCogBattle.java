package me.WesBag.Toontown.BattleCore;

import java.util.List;
import java.util.UUID;

import org.bukkit.Location;

import me.WesBag.Toontown.Main;
import me.WesBag.Toontown.BattleCore.Cogs.CogBuildings.CogBuilding;

public class BuildingCogBattle extends CogBattle {

	private CogBuilding cogBuilding;
	
	public BuildingCogBattle(Main main, List<UUID> playersUUID, List<UUID> npcsUUID, Location key, CogBuilding cogBuilding) {
		super(main, playersUUID, npcsUUID, key);
		this.cogBuilding = cogBuilding;
		
		setSpecialMode(true);
	}
}
