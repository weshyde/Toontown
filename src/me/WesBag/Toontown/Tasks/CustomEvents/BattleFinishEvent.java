package me.WesBag.Toontown.Tasks.CustomEvents;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import me.WesBag.Toontown.BattleCore.Cogs.Cog;
import me.WesBag.Toontown.BattleCore.Toons.Toon;

public class BattleFinishEvent extends Event {
	
	private static final HandlerList HANDLERS = new HandlerList();
	
	private List<Toon> toons = new ArrayList<>();
	private List<Cog> cogs = new ArrayList<>();
	
	public static HandlerList getHandlerList() {
		return HANDLERS;
	}
	
	@Override
	public HandlerList getHandlers() {
		return HANDLERS;
	}
	
	public BattleFinishEvent(List<Toon> players, List<Cog> cogs) {
		this.toons = players;
		this.cogs = cogs;
	}
	
	public List<Toon> getPlayers() {
		return toons;
	}
	
	public List<Cog> getCogs() {
		return cogs;
	}

}
