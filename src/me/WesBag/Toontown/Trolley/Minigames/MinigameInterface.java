package me.WesBag.Toontown.Trolley.Minigames;

import java.util.List;
import java.util.UUID;

import org.bukkit.Location;

public interface MinigameInterface {
	
	public void start();
	public void loadArena();
	public void unloadArena();
	@SuppressWarnings("unused")
	private List<Location> findArena() {
		return null;
	}
	public Location getArena();
	public void sendMessage(String msg);
	
	//public void freeArena(int index);
	
	//public boolean isPlaying(UUID uuid);

}
