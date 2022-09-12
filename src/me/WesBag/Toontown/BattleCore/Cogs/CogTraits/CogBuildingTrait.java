package me.WesBag.TTCore.BattleMenu.Cogs.CogTrait;

import org.bukkit.plugin.java.JavaPlugin;

import me.WesBag.TTCore.Main;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.api.persistence.Persist;
import net.citizensnpcs.api.trait.Trait;
import net.citizensnpcs.api.util.DataKey;

public class CogBuildingTrait extends Trait {

	public CogBuildingTrait() {
		super("CogBuilding");
		main = JavaPlugin.getPlugin(Main.class);
	}
	
	Main main = null;
	
	@Persist("oldName") String oldName = null;
	@Persist("fileName") String fileName = null;
	@Persist("difficulty") int difficulty = 0;
	@Persist int x = 0;
	@Persist int y = 0;
	@Persist int z = 0;
	@Persist int yR = 0;
	
	public void load(DataKey key) {
		oldName = key.getString("oldName");
		fileName = key.getString("fileName");
		difficulty = key.getInt("difficulty");
	}
	
	public void save(DataKey key) {
		key.setString("oldName", oldName);
		key.setString("fileName", fileName);
		key.setInt("difficulty", difficulty);
	}
	
	@Override
	public void onAttach() {
		main.getServer().getLogger().info(npc.getName() + " has been assigned the Cog Building trait!");
		oldName = npc.getFullName();
	}
	
	public void setDifficulty(int diff) {
		difficulty = diff;
	}
	
	public void setFileName(String fileStr) {
		fileName = fileStr;
	}
	public void setX(int inX) {
		x = inX;
	}
	
	public void setY(int inY) {
		y = inY;
	}
	
	public void setZ(int inZ) {
		z = inZ;
	}
	
	public void setYRotate(int inY) {
		yR = inY;
	}
	
	public int getDifficulty() {
		return difficulty;
	}
	
	public String getOldName() {
		return oldName;
	}
	
	public String getFileName() {
		return fileName;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public int getZ() {
		return z;
	}
	
	public int getYRotate() {
		return yR;
	}
}
