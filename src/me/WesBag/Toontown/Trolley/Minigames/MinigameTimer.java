package me.WesBag.Toontown.Trolley.Minigames;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import me.WesBag.Toontown.Trolley.Utilities.GameManager;
import me.WesBag.Toontown.Trolley.Utilities.GamePrefixes;
import me.WesBag.Toontown.Trolley.Utilities.GameState;

public class MinigameTimer extends BukkitRunnable {
	
	private GameManager gameManager;
	private int timeLeft;
	private List<Player> playersToMessage = new ArrayList<>();
	
	public MinigameTimer(GameManager gameManager, List<UUID> players) {
		this.gameManager = gameManager;
		this.timeLeft = 90;
		
		for (UUID pUUID : players) {
			this.playersToMessage.add(Bukkit.getPlayer(pUUID));
		}
	}
	
	public int getTimeLeft() {
		return timeLeft;
	}
	
	@Override
	public void run() {
		timeLeft--;
		if (timeLeft <= 0) {
			cancel();
			gameManager.setGameState(GameState.REWARD);
			return;
		}
		
		else if (timeLeft % 10 == 0 || timeLeft <= 5) {
			for (Player p : playersToMessage)
				p.sendMessage(GamePrefixes.Trolley + " " + timeLeft + " left in this game!");
		} 
	}
}
