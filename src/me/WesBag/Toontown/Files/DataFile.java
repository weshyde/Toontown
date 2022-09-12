package me.WesBag.Toontown.Files;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import me.WesBag.Toontown.Main;

public class DataFile {
	
	private Main main;
	public FileConfiguration dataConfig = null;
	public File dataFile;
	
	public DataFile(Main main) {
		this.main = main;
		createDataFile();
	}
	
	public void createDataFile() {
		if (this.dataFile == null) { //Or this.main.getDataFolder() + "/data.yml"
			this.dataFile = new File(this.main.getDataFolder(), "data.yml");
		}
		
		if (!this.dataFile.exists()) {
			YamlConfiguration config = YamlConfiguration.loadConfiguration(dataFile);
			try {
				config.save(dataFile);
			} catch (IOException e) {
				this.main.getLogger().log(Level.SEVERE, "Could not create data.yml!");
			}
		}
	}
	
	public void saveDataFile() {
		if (this.dataConfig == null || this.dataFile == null) {
			return;
		}
		try {
			this.getData().save(this.dataFile);
			//this.main.saveResource("data.yml", false); <-- MAYBE?
		} catch (IOException e) {
			main.getLogger().log(Level.SEVERE, "Could not save config file data.yml");
		}
	}
	
	public FileConfiguration getData() {
		if (this.dataConfig == null) {
			reloadDataFile();
		}
		return this.dataConfig;
	}
	
	public void reloadDataFile() {
		if (this.dataFile == null) {
			this.dataFile = new File(this.main.getDataFolder() + "/data.yml");
		}
		this.dataConfig = YamlConfiguration.loadConfiguration(this.dataFile);
		
		InputStream defaultStream = this.main.getResource("data.yml");
		if (defaultStream != null) {
			YamlConfiguration defaultConfig = YamlConfiguration.loadConfiguration(new InputStreamReader(defaultStream));
			this.dataConfig.setDefaults(defaultConfig);
		}
	}
	
    public static String serializeLocation(final Location l) {
    	if (l == null) {
    		return "";
    	}
    	return l.getWorld().getName() + ":" + l.getBlockX() + ":" + l.getBlockY() + ":" + l.getBlockZ();
    }
    
    public static Location unserializeLocation(final String str) {
    	if (str != null) {
    		final String[] parts = str.split(":");
    		if (parts.length == 4) {
    			final World w = Bukkit.getServer().getWorld(parts[0]);
    			final int x = Integer.parseInt(parts[1]);
    			final int y = Integer.parseInt(parts[2]);
    			final int z = Integer.parseInt(parts[3]);
    			return new Location(w, x, y, z);
    		}
    	}
    	return null;
    }

}
