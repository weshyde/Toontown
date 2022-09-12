package me.WesBag.Toontown.Tasks.CustomEvents;

import java.util.UUID;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PlayerCatchFishEvent extends Event {
	
	private static final HandlerList HANDLERS = new HandlerList();
	
	private UUID uuid;
	
	public static HandlerList getHandlerList() {
		return HANDLERS;
	}
	
	@Override
	public HandlerList getHandlers() {
		return HANDLERS;
	}
	
	public PlayerCatchFishEvent(UUID pUUID) {
		this.uuid = pUUID;
	}
	
	public UUID getPlayerUUID() {
		return uuid;
	}

}
