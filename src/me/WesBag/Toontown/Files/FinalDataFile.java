package me.WesBag.Toontown.Files;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.logging.Level;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import me.WesBag.Toontown.Main;

public class FinalDataFile {

	private Main main;
	public FileConfiguration dataConfig = null;
	public File dataFile;
	
	public FinalDataFile(Main main) {
		this.main = main;
		createDataFile();
	}
	
	public void createDataFile() {
		if (this.dataFile == null) { //Or this.main.getDataFolder() + "/data.yml"
			this.dataFile = new File(this.main.getDataFolder(), "fData.yml");
		}
		
		if (!this.dataFile.exists()) {
			YamlConfiguration config = YamlConfiguration.loadConfiguration(dataFile);
			try {
				config.save(dataFile);
			} catch (IOException e) {
				this.main.getLogger().log(Level.SEVERE, "Could not create fData.yml!");
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
			main.getLogger().log(Level.SEVERE, "Could not save config file fData.yml");
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
			this.dataFile = new File(this.main.getDataFolder() + "/fData.yml");
		}
		this.dataConfig = YamlConfiguration.loadConfiguration(this.dataFile);
		
		InputStream defaultStream = this.main.getResource("fData.yml");
		if (defaultStream != null) {
			YamlConfiguration defaultConfig = YamlConfiguration.loadConfiguration(new InputStreamReader(defaultStream));
			this.dataConfig.setDefaults(defaultConfig);
		}
	}
}
