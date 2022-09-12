package me.WesBag.Toontown.Files;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.UUID;
import java.util.logging.Level;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import me.WesBag.Toontown.Main;

public class PlayerData {

	private Main plugin;
	private FileConfiguration dataConfig = null;
	private File playerFile = null;
	private UUID playerUUID;
	
	public PlayerData(Main plugin, UUID playerUUID) {
		this.plugin = plugin;
		this.playerUUID = playerUUID;
		createPlayerData();
	}
	
	public void reloadPlayerData() {
		if (this.playerFile == null) {
			this.playerFile = new File(this.plugin.getDataFolder() + "/players", playerUUID.toString() + ".yml");
			//this.playerFile = new File("players/" + playerUUID.toString() + ".yml");
		}
		this.dataConfig = YamlConfiguration.loadConfiguration(this.playerFile);
		
		InputStream defaultStream = this.plugin.getResource(playerUUID.toString() + ".yml");
		if (defaultStream != null) {
			YamlConfiguration defaultConfig = YamlConfiguration.loadConfiguration(new InputStreamReader(defaultStream));
			this.dataConfig.setDefaults(defaultConfig);
		}
	}
	
	public FileConfiguration getPlayerData() {
		if (this.dataConfig == null) {
			reloadPlayerData();
		}
		return this.dataConfig;
	}
	
	public void savePlayerData() {
		if (this.dataConfig == null || this.playerFile == null) {
			return;
		}
		try {
			this.getPlayerData().save(this.playerFile);
		} catch (IOException e) {
			plugin.getLogger().log(Level.SEVERE, "Could not save config to " + this.playerFile);
		}
	}
	
	public void createPlayerData() {
		if (this.playerFile == null) {
			this.playerFile = new File(this.plugin.getDataFolder() + "/players", playerUUID.toString() + ".yml");
			//this.playerFile = new File("players/" + playerUUID.toString() + ".yml");
		}
		
		if (!this.playerFile.exists()) {
			YamlConfiguration config = YamlConfiguration.loadConfiguration(playerFile);
			try {
				config.save(playerFile);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				this.plugin.getLogger().log(Level.SEVERE, "Could not create config " + this.playerFile);
			}
			//this.plugin.saveResource(this.plugin.getDataFolder() + "/players/" + playerUUID.toString() + ".yml", false);
			//this.plugin.getResource
			//try {
			//	FileUtils.copyToFile(this.plugin.getResource(playerUUID.toString() + ".yml"), new File(this.plugin.getDataFolder() + "/players/" + playerUUID.toString() + ".yml"));
			//	this.plugin.saveResource(null, false)
			//} catch (IOException e) {
			//	plugin.getLogger().log(Level.SEVERE, "Could not create config " + this.playerFile);
			//}
		}
	}
	
}
