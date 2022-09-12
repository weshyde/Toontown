package me.WesBag.Toontown.Files;

import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import me.WesBag.Toontown.Main;

public class BattleFile {
	
	private Main plugin;
	private FileConfiguration dataConfig = null;
	private File battleFile = null;
	private Location battleLocation;
	
	public static int battleCount;
	
	public BattleFile(Main plugin, Location battleLocation) {
		this.plugin = plugin;
		this.battleLocation = battleLocation;
	}
	public void reloadBattleFile() {
		if (this.battleFile == null) {
			this.battleFile = new File(this.plugin.getDataFolder() + "/battles", "battle_" + battleCount);
		}
		
		this.dataConfig = YamlConfiguration.loadConfiguration(this.battleFile);
		
		InputStream defaultStream = this.plugin.getResource("battle_" + battleCount);
		if (defaultStream != null) {
			YamlConfiguration defaultConfig = YamlConfiguration.loadConfiguration(new InputStreamReader(defaultStream));
			this.dataConfig.setDefaults(defaultConfig);
		}
	}
	
	public FileConfiguration getBattleFile() {
		if (this.dataConfig == null) {
			//TO ADD
		}
		return this.dataConfig;
	}
	
	public void saveBattleFile() {
		if (this.dataConfig == null || this.battleFile == null) {
			
		}
	}
}
