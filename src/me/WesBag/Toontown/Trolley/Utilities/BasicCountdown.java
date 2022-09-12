package me.WesBag.Toontown.Trolley.Utilities;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class BasicCountdown extends BukkitRunnable {
	
	private GameManager gameManager;
	private GameState newGameState;
	private List<Player> playersToMessage = new ArrayList<>();
	private int time;
	
	public BasicCountdown(GameManager gameManager, int time, GameState gState, List<UUID> players) {
		this.gameManager = gameManager;
		this.time = time;
		this.newGameState = gState;
		for (UUID pUUID : players) {
			this.playersToMessage.add(Bukkit.getPlayer(pUUID));
		}
		//this.playersToMessage = players;
	}
	
	@Override
	public void run() {
		time--;
		if (time <= 0) {
			cancel();
			this.gameManager.setGameState(newGameState);
			return;
		}
		for (Player p : playersToMessage)
			p.sendMessage(GamePrefixes.Trolley + time + " left!");
	}
}
