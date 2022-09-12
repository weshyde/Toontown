package me.WesBag.Toontown.Files;

import java.util.HashMap;
import java.util.UUID;
import java.util.logging.Level;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import me.WesBag.Toontown.Main;

public class PlayerDataController implements Listener {
	public static HashMap<UUID, PlayerData> playerDatas = new HashMap<>();
	private static Main main;
	
	public PlayerDataController(Main main) {
		PlayerDataController.main = main;
	}
	
	@EventHandler
	public void onPlayerLogin(PlayerJoinEvent e) {
		PlayerData pData = new PlayerData(main, e.getPlayer().getUniqueId());
		playerDatas.put(e.getPlayer().getUniqueId(), pData);
		main.getLogger().log(Level.INFO, "Player Data successfully created!");
	}
	
	@EventHandler
	public void onPlayerLogout(PlayerQuitEvent e) {
		playerDatas.remove(e.getPlayer().getUniqueId());
		main.getLogger().log(Level.INFO, "Player Data succesfully removed!");
	}
	
	public static PlayerData getPlayerData(UUID keyUUID) {
		if (playerDatas.containsKey(keyUUID))
			return playerDatas.get(keyUUID);
		main.getLogger().log(Level.SEVERE, "Failed to get playerData for UUID " + keyUUID.toString());
		return null;
	}
}
