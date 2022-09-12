package me.WesBag.Toontown.Tasks.CustomEvents;

import java.util.UUID;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PlayerCatchItemEvent extends Event {
	
private static final HandlerList HANDLERS = new HandlerList();
	
	private UUID uuid;
	private String item;
	
	public static HandlerList getHandlerList() {
		return HANDLERS;
	}
	
	@Override
	public HandlerList getHandlers() {
		return HANDLERS;
	}
	
	public PlayerCatchItemEvent(UUID pUUID, String caughtItem) {
		this.uuid = pUUID;
		this.item = caughtItem;
	}
	
	public UUID getPlayerUUID() {
		return uuid;
	}
	
	public String getCaughtItem() {
		return item;
	}

}
