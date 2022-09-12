package me.WesBag.Toontown.Tasks.CustomEvents;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import me.WesBag.Toontown.BattleCore.Toons.Toon;

public class PlayerDestroyBuildingEvent extends Event {

	private static final HandlerList HANDLERS = new HandlerList();
	
	private List<Toon> destroyers = new ArrayList<>();
	private String buildingSuit;
	private int buildingLevels;
	
	public static HandlerList getHandlerList() {
		return HANDLERS;
	}
	
	@Override
	public HandlerList getHandlers() {
		return HANDLERS;
	}
	
	public PlayerDestroyBuildingEvent(List<Toon> players, String buildingSuit, int buildingLevels) {
		this.destroyers = players;
		//this.cogsDefeated = cogsDefeated;
		this.buildingSuit = buildingSuit;
		this.buildingLevels = buildingLevels;
	}
	
	public List<Toon> getDestroyers() {
		return destroyers;
	}
	
	//public List<Cog> getCogsDefeated() {
	//	return cogsDefeated;
	//}
	
	public String getBuildingSuit() {
		return buildingSuit;
	}
	
	public int getBuildingLevels() {
		return buildingLevels;
	}
	
}
