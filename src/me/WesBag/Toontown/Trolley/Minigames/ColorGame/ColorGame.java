package me.WesBag.Toontown.Trolley.Minigames.ColorGame;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Location;

import me.WesBag.Toontown.Trolley.Minigames.MinigameInterface;
import me.WesBag.Toontown.Trolley.Utilities.GameManager;

public class ColorGame implements MinigameInterface {
	
	public static List<List<Location>> arenas = new ArrayList<>();
	public static Map<Integer, Boolean> freeArenas = new HashMap<>();
	public static List<UUID> playersIn = new ArrayList<>();
	
	private GameManager gameManager;
	private int arenaIndex;
	
	public ColorGame(GameManager gameManager) {
		this.gameManager = gameManager;
		
	}

	@Override
	public void start() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void loadArena() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void unloadArena() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Location getArena() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void sendMessage(String msg) {
		// TODO Auto-generated method stub
		
	}

}
